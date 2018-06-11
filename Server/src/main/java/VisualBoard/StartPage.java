package VisualBoard;

import Cards.*;
import Cards.OnCardEffects.MoveEffect;
import InGameResources.Dice.Dice;
import InGameResources.Dice.DiceSlotsImpl;
import Players.Player;
import VisualCards.ViewManager;
import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import Table.Table;
import misc.Cost;
import misc.Effect;

public class StartPage implements VisualElement {
    private Application myApp;
    public StartPage(Application myApp){
        this.myApp = myApp;
    }
    public Node draw(){
        ListView<String> list = new ListView<String>();
        ObservableList<String> items = FXCollections.observableArrayList (
                "1", "2", "3", "4");
        list.setItems(items);
        list.getSelectionModel().selectedItemProperty().addListener(
                (ObservableValue<? extends String> ov, String old_val,
                 String new_val) -> {
                    Table temp = ((ViewManager)myApp).getTable();
                    for(int i = 0; i < new_val.hashCode()-48; i++) {
                        Cost cost = new Cost(1, 1, 0, 1);
                        CardUsageDiceCost c = new CardUsageDiceCost();
                        DiceSlotsImpl slots = new DiceSlotsImpl(1, Dice.Color.YELLOW);
                        MoveEffect moveOnce = new MoveEffect(1);
                        MoveEffect moveTwice= new MoveEffect(2);
                        Effect[][] kek5 = {{moveOnce}, {moveOnce, moveOnce}};
                        CardEffect e = new CardEffect(kek5);
                        VehicleCardEngine engine = new VehicleCardEngine(c, e, slots);
                        VehicleCardData card = new VehicleCardData(cost, i, "cockpit "+ i, engine, new Joints(true, false, true, false));
                        new Player(temp, i, card);
                        ((ViewManager)myApp).addPlayer();
                    }
                    ((ViewManager)myApp).showStages();

                });
        return list;
    }
    public void actualize(){

    }
}
