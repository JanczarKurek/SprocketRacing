package Players;

import Cards.Card;
import Cards.Hand;
import Cards.Layout.CardsLayout;
import ErrorsAndExceptions.WrongMove;
import InGameResources.Dice.DiceBunch;
import InGameResources.ResourceWallet;
import Table.Table;
import Table.TableController;

public class Player {

    private int id;
    public int getId(){
        return id;
    }
    private TableController tableController;
    private Hand myHand;
    private Card chosenCard;
    private ResourceWallet myWallet = new ResourceWallet();
    private CardsLayout myVehicle = new CardsLayout();
    private DiceBunch dices = new DiceBunch();
    public Player(Table table, int id){
        tableController = table.sitDown(this);
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

    public int getHandSize(){
        return myHand.getHandSize();
    }

    public CardsLayout getMyVehicle() {
        return myVehicle;
    }

    public DiceBunch getDices() {
        return dices;
    }
}
