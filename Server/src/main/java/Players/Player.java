package Players;

import Cards.*;
import Cards.Layout.CardsLayout;
import ErrorsAndExceptions.WrongColor;
import ErrorsAndExceptions.WrongMove;
import InGameResources.Dice.Dice;
import InGameResources.Dice.DiceBunch;
import InGameResources.ResourceWallet;
import MapServer.OnPassEffect;
import MapServer.Path;
import MapServer.PawnController;
import Table.Table;
import Table.TableController;
import javafx.util.Pair;
import misc.Cost;
import misc.Effect;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

public class Player {

    public enum Task{
        MOVE,
        SMOOTH,
        VENT,
        PARTBREAK
    }

    public class PendingTask{
        PendingTask(Task task, int value){
            this.task = task;
            this.value = value;
        }
        Task task;
        int value;
    }

    public class TaskManager{
        private Stack<PendingTask> pendingTasks = new Stack<>();
        public void putTask(PendingTask task){
            pendingTasks.push(task);
        }

        public PendingTask getCurrentTask(){
            return pendingTasks.peek();
        }

        public void finalizeTask(){
            pendingTasks.pop();
        }
    }

    private HpBar hpBar = new HpBar(3, -6);
    private int id;
    public int getId(){
        return id;
    }
    private TableController tableController;
    private Hand myHand;
    private Card chosenCard;
    private ResourceWallet myWallet = new ResourceWallet();
    private CardsLayout myVehicle = new CardsLayout();
    private VehicleCardEngine.Proposition actualProposition;
    private Iterator<CardEffect> processedEffects;
    private PawnController pawnController;

    private TaskManager taskManager = new TaskManager();

    private Iterator<Effect> atomicEffect;

    public int value;

    public Task task;

    public Player(Table table, int id){
        Pair<TableController, PawnController> p = table.sitDown(this);
        tableController = p.getKey();
        pawnController = p.getValue();
        this.id = id;
    }

    public void aquireHand() throws WrongMove {
        try {
            myHand = tableController.getHand();
        } catch (WrongMove wrongMove) {
            throw new WrongMove("Player " + getId() + wrongMove.getMessage());
        }
    }

    public Hand getMyHand() {
        return myHand;
    }

    public Card chooseCard(int idx) throws WrongMove {
        if(chosenCard != null)
            throw new WrongMove("Player " + getId() + ": Card already chosen from this hand");
        Card card = myHand.take(idx);
        try {
            tableController.passHand(myHand);
            return card;
        } catch (WrongMove wrongMove) {
            myHand.putCard(card);
            throw new WrongMove("Player " + getId() + ": " + wrongMove.getMessage());
        }
    }

    public void sellCard(Dice.Color type) throws WrongMove {
        if(chosenCard == null)
            throw new WrongMove("Player " + getId() + ": No card to sell");
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
    }

    public void sellCard() throws WrongMove{
        if(chosenCard == null)
            throw new WrongMove("Player " + getId() + ": No card to sell");
        Cost cost = chosenCard.getCost();
        myWallet.putGears(cost.getCogs());
        tableController.passHand(myHand);
        myHand = null;
        chosenCard = null;
    }

    public void useCard(int x, int y, DiceBunch dice) throws WrongMove{
        if(actualProposition != null)
            throw new WrongMove("Player " + getId() + ": another proposition in progress");
        VehicleCardData card = myVehicle.getLayout(x, y).get(new Pair<>(x, y));
        try {
            actualProposition = card.getEngine().getProposition(dice);

        } catch (WrongColor wrongColor) {
            throw new WrongMove("Player " + getId() + ":" + wrongColor);
        }
    }

    public void acceptProposition(){
        processedEffects = actualProposition.accept().iterator();

    }

    public void refuseProposition(){
        myWallet.transferFrom(actualProposition.decline());
        actualProposition = null;
    }

    public void runEffects(int idx) throws WrongMove{
        if(processedEffects == null)
            throw new WrongMove("Player " + getId() + ": no effect to process");
        atomicEffect = processedEffects.next().chooseEffect(idx).iterator();
        if(!processedEffects.hasNext()) {
            processedEffects = null;
        }
    }

    public void runAtomicEffect() throws WrongMove {
        if(atomicEffect == null)
            throw new WrongMove("Player " + getId() + ": no atomic effect to run");
        Effect e = atomicEffect.next();
        e.execute(this);
        if(!atomicEffect.hasNext()) {
            atomicEffect = null;
            if(task == null)
                actualProposition = null;
        }
    }

    public void makeMove(Path path) throws WrongMove {
        if(task != Task.MOVE && task != Task.SMOOTH)
            throw new WrongMove("Player " + getId() + ": should not move right now");
        if(path.length() != value)
            throw  new WrongMove("Player " + getId() + ": malformed path, expecting size " + value + " got " + path.length());
        try {
            atomicEffect = pawnController.move(path).iterator();
            if(task == Task.SMOOTH)
                atomicEffect = null;
        } catch (WrongMove wrongMove) {
            throw new WrongMove("Player " + getId() + ": tried to move, wrong move " + wrongMove);
        }
    }

    public void ventOnce(int x, int y, int diceIdx) throws WrongMove {
        VehicleCardData card = myVehicle.getLayout(x, y).get(new Pair<>(x, y));
        try {
            card.getDiceSlots().decrement(diceIdx);
            value--;
            if(value == 0)
                task = null;
        } catch (WrongMove wrongMove) {
            throw new WrongMove("Player " + getId() + ": " + wrongMove);
        }
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
}
