package Players;

import Cards.Hand;
import Cards.Layout.CardsLayout;
import InGameResources.Dice.DiceBunch;
import MapServer.PawnController;
import Table.TableController;
import Table.Table;

public class Player {
    private int id;
    public int getId(){
        return id;
    }
    private TableController tableController;
    private Hand myHand;
    private CardsLayout myVehicle = new CardsLayout();
    private DiceBunch dices = new DiceBunch();
    public Player(Table table, int id){
        tableController = table.sitDown(this);
        this.id = id;
    }

    public Hand getMyHand() {
        return myHand;
    }

    public CardsLayout getMyVehicle() {
        return myVehicle;
    }

    public DiceBunch getDices() {
        return dices;
    }
}
