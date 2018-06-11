package VisualDice;

import Cards.CardUsageDiceCost;
import Cards.CardUsagePipCost;
import InGameResources.Dice.Dice;
import InGameResources.Dice.DiceBunch;
import VisualCards.VisualUsageCost;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Arrays;

import static org.junit.Assert.*;

public class VisualDiceBunchTest extends Application{
    @Override
    public void start(Stage primaryStage) throws Exception {
        DiceBunch bunch = new DiceBunch(Arrays.asList(new Dice(Dice.Color.YELLOW), new Dice(Dice.Color.BLUE), new Dice(Dice.Color.RED)));
        bunch.roll();
        VisualDiceBunch vb = new VisualDiceBunch(bunch);
        Group group = new Group();
        group.getChildren().add(vb.draw());
        primaryStage.setScene(new Scene(group));
        primaryStage.show();
    }

    public static void main(String[] args){
        launch(args);
    }
}