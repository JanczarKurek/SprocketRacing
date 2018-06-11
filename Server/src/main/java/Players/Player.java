package Players;

import Cards.*;
import Cards.Layout.CardsLayout;
import ErrorsAndExceptions.IllegalCardsLayoutException;
import ErrorsAndExceptions.NotEnoughResources;
import ErrorsAndExceptions.WrongColor;
import ErrorsAndExceptions.WrongMove;
import InGameResources.Dice.Dice;
import InGameResources.Dice.DiceBunch;
import InGameResources.ResourceWallet;
import MapServer.Path;
import MapServer.PawnController;
import Table.Table;
import Table.TableController;
import Table.Phase;
import javafx.util.Pair;
import misc.Cost;
import misc.Effect;

import java.util.*;

public class Player {

    public enum Task{
        IDLEDRAFT,
        IDLEVENT,
        IDLERACE,
        IDLEDAMAGE,
        AQUIREHAND,
        BREAKPART,
        BREAKONE,
        CHOOSECARD,
        CHANGEVEHICLE,
        USECARD,
        ACCEPTUSE,
        RUNEFFECTS,
        RUNATOMIC,
        MAKEMOVE,
        VENTONCE,
        VENT,
        MOVESMOOTH,
        SELLCARD,
        TAKECARD,
        REFUSEUSE,
        PUTCARD,
        TAKECARDFROMVEHICLE,
        REMOVECARD,
        ACCEPTCARDS,
        DRAFTEND,
        DAMAGEEND,
        VOTE,
        ROLL,
        REROLL,
        INCREASE
    }

    // Possible transitions:

    private static HashMap<Task, Set<Task> > transitions = new HashMap<>();

    static{
        TreeSet<Task> put = new TreeSet<>(Arrays.asList(Task.AQUIREHAND, Task.CHANGEVEHICLE));
        transitions.put(Task.IDLEDRAFT, put);
        put = new TreeSet<>(Arrays.asList(Task.VOTE, Task.CHANGEVEHICLE));
        transitions.put(Task.DRAFTEND, put);
        put = new TreeSet<>(Arrays.asList(Task.USECARD, Task.CHANGEVEHICLE, Task.VOTE, Task.ROLL, Task.REROLL, Task.INCREASE));
        transitions.put(Task.IDLERACE, put);
        put = new TreeSet<>(Arrays.asList(Task.CHANGEVEHICLE, Task.VENT, Task.VOTE));
        transitions.put(Task.IDLEVENT, put);
        put = new TreeSet<>(Arrays.asList(Task.BREAKPART));
        transitions.put(Task.IDLEDAMAGE, put);
        put = new TreeSet<>(Arrays.asList(Task.VOTE));
        transitions.put(Task.DAMAGEEND, put);
        put = new TreeSet<>(Arrays.asList(Task.CHOOSECARD));
        transitions.put(Task.AQUIREHAND, put);
        put = new TreeSet<>(Arrays.asList(Task.SELLCARD, Task.TAKECARD));
        transitions.put(Task.CHOOSECARD, put);
        put = new TreeSet<>(Arrays.asList(Task.REFUSEUSE, Task.ACCEPTUSE));
        transitions.put(Task.USECARD, put);
        put = new TreeSet<>(Arrays.asList(Task.RUNEFFECTS));
        transitions.put(Task.ACCEPTUSE, put);
        put = new TreeSet<>(Arrays.asList(Task.RUNATOMIC));
        transitions.put(Task.RUNEFFECTS, put);
        put = new TreeSet<>(Arrays.asList(Task.VENTONCE));
        transitions.put(Task.VENT, put);
        put = new TreeSet<>(Arrays.asList(Task.BREAKONE));
        transitions.put(Task.BREAKPART, put);
        put = new TreeSet<>(Arrays.asList(Task.PUTCARD, Task.TAKECARDFROMVEHICLE, Task.REMOVECARD, Task.ACCEPTCARDS));
        transitions.put(Task.CHANGEVEHICLE, put);
        put = new TreeSet<>(Arrays.asList(Task.MAKEMOVE));
        transitions.put(Task.MAKEMOVE, put);
        put = new TreeSet<>(Arrays.asList(Task.MAKEMOVE));
        transitions.put(Task.MOVESMOOTH, put);
    }

    private void checkAction(Task proposition) throws WrongMove {
        Task task = taskManager.getCurrentTask().type;
        if(!transitions.get(task).contains(proposition))
            //todo create new exception for illegal state instructions.
            throw new WrongMove("Player " + getId() + ": Illegal action during " + task + ", possible are " + transitions.get(task) + " got " + proposition);
    }

    static public class PendingTask{
        public PendingTask(Task task, int value){
            this.type = task;
            this.value = value;
        }

        VehicleCardEngine.Proposition actualProposition;
        Iterator<CardEffect> processedEffects;
        Iterator<Effect> atomicEffect;
        VehicleArrangementManager manager;
        public Dice.Color color;

        Task type;
        int value;
    }

    public class TaskManager{
        TaskManager(){
            pendingTasks.add(new PendingTask(Task.IDLEDRAFT, 0));
        }

        private LinkedList<PendingTask> pendingTasks = new LinkedList<>();
        public void putTask(PendingTask task){
            pendingTasks.push(task);
        }

        PendingTask getCurrentTask(){
            return pendingTasks.peek();
        }

        void finalizeTask(){
            pendingTasks.pop();
        }
    }

    class VehicleArrangementManager{

        /** Very important, one can not move cockpit **/

        HashMap<Integer, VehicleCardData> cardsById = new HashMap<>();
        void putCard(int cardId, int x, int y) throws WrongMove{
            try{
                myVehicle.add(cardsById.get(cardId), x, y);
                cardsById.remove(cardId);
            }catch(IllegalCardsLayoutException exception){
                throw new WrongMove("Player " + getId() + ": can not put cards on coordinates (" + x + ", " + y + ")");
            }
        }
        void takeCard(int x, int y) throws WrongMove {
            VehicleCardData card = myVehicle.remove(x, y);
            if(card == null){
                throw new WrongMove("Player " + getId() + ": tried to get card from (" + x  + ", " + y + ") found nothing");
            }else{
                cardsById.put(card.getID(), card);
            }
        }

        void removeCard(int x, int y) throws WrongMove {
            VehicleCardData card = myVehicle.remove(x, y);
            if(card == null)
                throw new WrongMove("Player " + getId() + ": tried to remove card from (" + x  + ", " + y + ") found nothing");
            tableController.discard(card);
        }

        void acceptArrangement() throws WrongMove{
            if(!myVehicle.checkCorrectness())
                throw new WrongMove("Player " + getId() + ": tried to use invalid arrangement of vehicle");
            for(Card card : cardsById.values())
                tableController.discard(card);
        }
    }

    private HpBar hpBar = new HpBar(3, -6);
    private int id;
    private TableController tableController;
    private Hand myHand;
    private Card chosenCard;
    private ResourceWallet myWallet = new ResourceWallet();
    private CardsLayout myVehicle = new CardsLayout();
    private PawnController pawnController;
    public TaskManager taskManager = new TaskManager();

    public Player(Table table, int id, VehicleCardData cockpit){
        this.id = id;
        Pair<TableController, PawnController> p = table.sitDown(this);
        tableController = p.getKey();
        pawnController = p.getValue();
        myVehicle.setCockpit(cockpit, 0, 0);
    }

    /** Getters for getting info **/

    public Card getChosenCard() {
        return chosenCard;
    }

    public int getId(){
        return id;
    }

    public Hand getMyHand() {
        return myHand;
    }

    public int getHandSize(){
        return myHand.getHandSize();
    }

    public CardsLayout getMyVehicle() {
        return myVehicle;
    }

    public ResourceWallet getMyWallet() {
        return myWallet;
    }

    public HpBar getHpBar() {
        return hpBar;
    }

    /******************************/

    /** Draw phase functions **/

    public void aquireHand() throws WrongMove {
        checkAction(Task.AQUIREHAND);

        try {
            myHand = tableController.getHand();
            if(myHand.getHandSize() == 1){
                taskManager.finalizeTask();
                taskManager.putTask(new PendingTask(Task.DRAFTEND, 0));
            }
            taskManager.putTask(new PendingTask(Task.AQUIREHAND, 0));
        } catch (WrongMove wrongMove) {
            throw new WrongMove("Player " + getId() +": " +  wrongMove.getMessage());
        }
    }

    public void chooseCard(int id) throws WrongMove {
        checkAction(Task.CHOOSECARD);
        boolean x = true;
        for(int i = 0; i < myHand.getHandSize(); ++i){
            if(myHand.getCards().get(i).getID() == id){
                chosenCard = myHand.take(i);
                x = false;
                break;
            }
        }
        if(x){
            throw new WrongMove("Player " + getId() + ": wrong id, no such a card in hand " + id);
        }
        taskManager.finalizeTask();
        tableController.passHand(myHand);
        taskManager.putTask(new PendingTask(Task.CHOOSECARD, 0));

    }

    public void sellCard(Dice.Color type) throws WrongMove {
        checkAction(Task.SELLCARD);
        Cost cost = chosenCard.getCost();
        if(type == Dice.Color.RED){
            myWallet.putDice(cost.getRed(), type);
        }else if(type == Dice.Color.YELLOW){
            myWallet.putDice(cost.getYellow(), type);
        }else if(type == Dice.Color.BLUE){
            myWallet.putDice(cost.getBlue(), type);
        }
        myHand = null;
        tableController.discard(chosenCard);
        chosenCard = null;
        taskManager.finalizeTask(); // After sell -> IDLEDRAFT
    }

    public void sellCard() throws WrongMove{
        checkAction(Task.SELLCARD);
        Cost cost = chosenCard.getCost();
        myWallet.putGears(cost.getCogs());
        myHand = null;
        tableController.discard(chosenCard);
        chosenCard = null;
        taskManager.finalizeTask();
    }

    public void takeCard() throws WrongMove{
        checkAction(Task.TAKECARD);
        PendingTask task = new PendingTask(Task.CHANGEVEHICLE, 0);
        task.manager = new VehicleArrangementManager();
        task.manager.cardsById.put(chosenCard.getID(), (VehicleCardData)chosenCard);
        taskManager.finalizeTask();
        taskManager.putTask(task);
    }

    /**************************/

    /** Arranging vehicle functions **/

    public void arrangeVehicle() throws WrongMove {
        checkAction(Task.CHANGEVEHICLE);
        PendingTask task = new PendingTask(Task.CHANGEVEHICLE, 0);
        task.manager = new VehicleArrangementManager();
        taskManager.putTask(task);

    }

    public void obligatoryRemoveOne(int x, int y) throws WrongMove {
        checkAction(Task.BREAKPART);
        if(myVehicle.justCockpit()){
            //todo Explosion
            taskManager.finalizeTask();
        }else{
            taskManager.getCurrentTask().manager.removeCard(x, y); //Throws if wrong, it's ok.
            taskManager.getCurrentTask().value--;
            if(taskManager.getCurrentTask().value == 0){
                taskManager.finalizeTask();
                taskManager.putTask(new PendingTask(Task.CHANGEVEHICLE, 0)); //Make your vehicle working after break.
            }
        }
    }

    public void putCard(int cardIdx, int x, int y) throws WrongMove {
        checkAction(Task.PUTCARD);
        taskManager.getCurrentTask().manager.putCard(cardIdx, x, y);
    }

    public void takeCard(int x, int y) throws WrongMove {
        checkAction(Task.TAKECARDFROMVEHICLE);
        taskManager.getCurrentTask().manager.takeCard(x, y); //Throws if wrong and that's gut.
    }

    public void removeCard(int x, int y) throws WrongMove {
        checkAction(Task.REMOVECARD);
        taskManager.getCurrentTask().manager.removeCard(x, y); //Throws if wrong and that's gut.
    }

    public void acceptVehicleLayout() throws WrongMove {
        checkAction(Task.ACCEPTCARDS);
        taskManager.getCurrentTask().manager.acceptArrangement();
        taskManager.finalizeTask(); //Pop arrangeVehicle task.
    }

    public Collection<VehicleCardData> getUnusedCards(){
        return taskManager.getCurrentTask().manager.cardsById.values();
    }

    /*********************************/

    /** Using of dices in hand **/

    public void roll() throws WrongMove {
        checkAction(Task.ROLL);
        myWallet.rollUnrolled();
    }

    public void reroll(int idx) throws WrongMove, NotEnoughResources {
        checkAction(Task.REROLL);
        myWallet.takeGears(2);
        myWallet.reroll(idx);
    }

    public void increase(int idx) throws WrongMove, NotEnoughResources {
        checkAction(Task.INCREASE);
        myWallet.takeGears(1);
        myWallet.increase(idx);
    }

    /****************************/

    /** Race **/

    public void useCard(int x, int y, Collection<Integer> indices) throws WrongMove{
        checkAction(Task.USECARD);
        DiceBunch dice = new DiceBunch();
        VehicleCardData card = myVehicle.getLayout(x, y).get(new Pair<>(x, y));
        try {
            dice = myWallet.takeSome(indices);
            PendingTask pendingTask = new PendingTask(Task.USECARD, 0);
            pendingTask.actualProposition = card.getEngine().getProposition(dice);
            taskManager.putTask(pendingTask);
        } catch (WrongColor wrongColor) {
            myWallet.putDices(dice);
            throw new WrongMove("Player " + getId() + ":" + wrongColor);
        }
    }

    public void acceptProposition() throws WrongMove {
        checkAction(Task.ACCEPTUSE);
        VehicleCardEngine.Proposition actualProposition = taskManager.getCurrentTask().actualProposition;
        taskManager.finalizeTask();
        PendingTask pendingTask = new PendingTask(Task.ACCEPTUSE, 0);
        pendingTask.processedEffects = actualProposition.accept().iterator();
        taskManager.putTask(pendingTask);
    }

    public void refuseProposition() throws WrongMove {
        checkAction(Task.REFUSEUSE);
        myWallet.transferFrom(taskManager.getCurrentTask().actualProposition.decline());
        taskManager.finalizeTask();
    }

    public void runEffects(int idx) throws WrongMove{
        checkAction(Task.RUNEFFECTS);
        PendingTask actual = taskManager.getCurrentTask();
        PendingTask newTask = new PendingTask(Task.RUNEFFECTS, 0);
        newTask.atomicEffect = actual.processedEffects.next().chooseEffect(idx).iterator();
        if(!actual.processedEffects.hasNext() && actual.type == Task.ACCEPTUSE) {
            taskManager.finalizeTask();
        }
        taskManager.putTask(newTask);
    }

    public void runAtomicEffect() throws WrongMove {
        checkAction(Task.RUNATOMIC);
        Effect e = taskManager.getCurrentTask().atomicEffect.next();
        if(!taskManager.getCurrentTask().atomicEffect.hasNext()) {
            taskManager.finalizeTask();
        }
        taskManager.putTask(new PendingTask(Task.RUNATOMIC, 0));
        e.execute(this);
    }

    public void makeMove(Path path) throws WrongMove {
        checkAction(Task.MAKEMOVE);
        if(path.length() - 1 != taskManager.getCurrentTask().value)
            throw  new WrongMove("Player " + getId() + ": malformed path, expecting size " + taskManager.getCurrentTask().value + " got " + (path.length() - 1));
        try {
            PendingTask actual = taskManager.getCurrentTask();
            PendingTask newTask = new PendingTask(Task.RUNEFFECTS, 0);
            Collection<Effect> effects = pawnController.move(path);
            taskManager.finalizeTask();
            taskManager.finalizeTask();
            if(effects.size() != 0 && actual.type != Task.MOVESMOOTH) {
                newTask.atomicEffect = pawnController.move(path).iterator();
                taskManager.putTask(newTask);
            }
        } catch (WrongMove wrongMove) {
            throw new WrongMove("Player " + getId() + ": tried to move, wrong move " + wrongMove);
        }
    }

    public void ventOnce(int x, int y, int diceIdx) throws WrongMove {
        checkAction(Task.VENTONCE);
        VehicleCardData card = myVehicle.getLayout(x, y).get(new Pair<>(x, y));
        try {
            if(taskManager.getCurrentTask().color != Dice.Color.ANY)
                if(card.getDiceSlots().getDice(diceIdx).getColor() != taskManager.getCurrentTask().color)
                    throw new WrongMove("Wrong color, expecting " + taskManager.getCurrentTask().color + " got " + card.getDiceSlots().getDice(diceIdx).getColor());
            card.getDiceSlots().decrement(diceIdx);
            taskManager.getCurrentTask().value--;
            if(taskManager.getCurrentTask().value == 0)
                taskManager.finalizeTask();
        } catch (WrongMove wrongMove) {
            throw new WrongMove("Player " + getId() + ": " + wrongMove);
        }
    }

    public void vent(int times) throws NotEnoughResources, WrongMove {
        checkAction(Task.VENT);
        try {
            myWallet.takeGears(times * 2);
            PendingTask task = new PendingTask(Task.VENT, times);
            task.color = Dice.Color.ANY;
            taskManager.putTask(task);
        } catch (NotEnoughResources notEnoughResources) {
            throw new NotEnoughResources("Player " + getId() + ": " + notEnoughResources);
        }
    }

    /**********/

    public void vote() throws WrongMove {
        checkAction(Task.VOTE);
        taskManager.finalizeTask();
        taskManager.putTask(new PendingTask(Task.VOTE, 0));
        tableController.voteEndPhase();
    }

    public void nextPhase(Phase nextPhase){ //DO NOT USE, only table can use it.
        taskManager.finalizeTask();
        switch (nextPhase){
            case DRAW:
                taskManager.putTask(new PendingTask(Task.IDLEDRAFT, 0));
                break;
            case RACE:
                taskManager.putTask(new PendingTask(Task.IDLERACE, 0));
                break;
            case VENT:
                taskManager.putTask(new PendingTask(Task.IDLEVENT, 0));
                break;
            case DAMAGE:
                taskManager.putTask(new PendingTask(Task.IDLEDAMAGE, 0));
        }
    }

}
