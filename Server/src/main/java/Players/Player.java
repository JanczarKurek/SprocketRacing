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
        put = new TreeSet<>(Arrays.asList(Task.VOTE));
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
        put = new TreeSet<>(Arrays.asList(Task.VENT, Task.MOVESMOOTH, Task.MAKEMOVE));
        transitions.put(Task.RUNATOMIC, put);
        put = new TreeSet<>(Arrays.asList(Task.VENTONCE));
        transitions.put(Task.VENT, put);
        put = new TreeSet<>(Arrays.asList(Task.BREAKONE));
        transitions.put(Task.BREAKPART, put);
        put = new TreeSet<>(Arrays.asList(Task.PUTCARD, Task.TAKECARDFROMVEHICLE, Task.REMOVECARD, Task.ACCEPTCARDS));
        transitions.put(Task.CHANGEVEHICLE, put);
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

        Task type;
        int value;
    }

    public class TaskManager{
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

        ArrayList<VehicleCardData> cardsToUse = new ArrayList<>();
        void putCard(int cardIdx, int x, int y) throws WrongMove{
            try{
                myVehicle.add(cardsToUse.get(cardIdx), x, y);
                cardsToUse.remove(cardIdx);
            }catch(IllegalCardsLayoutException exception){
                throw new WrongMove("Player " + getId() + ": can not put cards on coordinates (" + x + ", " + y + ")");
            }
        }
        void takeCard(int x, int y) throws WrongMove {
            VehicleCardData card = myVehicle.remove(x, y);
            if(card == null){
                throw new WrongMove("Player " + getId() + ": tried to get card from (" + x  + ", " + y + ") found nothing");
            }else{
                cardsToUse.add(card);
            }
        }

        void removeCard(int x, int y) throws WrongMove {
            VehicleCardData card = myVehicle.remove(x, y);
            if(card == null)
                throw new WrongMove("Player " + getId() + ": tried to remove card from (" + x  + ", " + y + ") found nothing");
        }

        void acceptArrangement() throws WrongMove{
            if(!myVehicle.checkCorrectness())
                throw new WrongMove("Player " + getId() + ": tried to use invalid arrangement of vehicle");
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
        Pair<TableController, PawnController> p = table.sitDown(this);
        tableController = p.getKey();
        pawnController = p.getValue();
        myVehicle.setCockpit(cockpit, 0, 0);
        this.id = id;
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
        taskManager.putTask(new PendingTask(Task.AQUIREHAND, 0));
        try {
            myHand = tableController.getHand();
        } catch (WrongMove wrongMove) {
            throw new WrongMove("Player " + getId() + wrongMove.getMessage());
        }
    }

    public void chooseCard(int idx) throws WrongMove {
        checkAction(Task.CHOOSECARD);
        if(chosenCard != null)
            throw new WrongMove("Player " + getId() + ": Card already chosen from this hand");
        Card card = myHand.take(idx);
        try {
            tableController.passHand(myHand);
            chosenCard = card;
            taskManager.finalizeTask();
            taskManager.putTask(new PendingTask(Task.CHOOSECARD, 0));
        } catch (WrongMove wrongMove) {
            taskManager.finalizeTask();
            myHand.putCard(card);
            throw new WrongMove("Player " + getId() + ": " + wrongMove.getMessage());
        }
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
        tableController.passHand(myHand);
        myHand = null;
        chosenCard = null;
        taskManager.finalizeTask(); // After sell -> IDLEDRAFT
    }

    public void sellCard() throws WrongMove{
        checkAction(Task.SELLCARD);
        Cost cost = chosenCard.getCost();
        myWallet.putGears(cost.getCogs());
        tableController.passHand(myHand);
        myHand = null;
        chosenCard = null;
        taskManager.finalizeTask();
    }

    public void takeCard() throws WrongMove{
        checkAction(Task.TAKECARD);
        PendingTask task = new PendingTask(Task.CHANGEVEHICLE, 0);
        task.manager = new VehicleArrangementManager();
        task.manager.cardsToUse.add((VehicleCardData)chosenCard);
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
        checkAction(Task.BREAKONE);
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
    }

    public void makeMove(Path path) throws WrongMove {
        checkAction(Task.MAKEMOVE);
        if(path.length() != taskManager.getCurrentTask().value)
            throw  new WrongMove("Player " + getId() + ": malformed path, expecting size " +taskManager.getCurrentTask().value + " got " + path.length());
        try {
            PendingTask actual = taskManager.getCurrentTask();
            PendingTask newTask = new PendingTask(Task.RUNEFFECTS, 0);
            Collection<Effect> effects = pawnController.move(path);
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
            taskManager.putTask(new PendingTask(Task.VENT, times));
        } catch (NotEnoughResources notEnoughResources) {
            throw new NotEnoughResources("Player " + getId() + ": " + notEnoughResources);
        }
    }

    /**********/

    public void vote() throws WrongMove {
        checkAction(Task.VOTE);
        taskManager.finalizeTask();
        taskManager.putTask(new PendingTask(Task.VOTE, 0));
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
