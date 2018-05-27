package Players;

import Cards.Card;
import Cards.Hand;
import Cards.Layout.CardsLayout;
import ErrorsAndExceptions.WrongMove;
import InGameResources.Dice.Dice;
import InGameResources.ResourceWallet;
import Table.Table;
import Table.TableController;
import misc.Cost;

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

    public int getHandSize(){
        return myHand.getHandSize();
    }

    public CardsLayout getMyVehicle() {
        return myVehicle;
    }

    public ResourceWallet getMyWallet() {
        return myWallet;
    }
}
