package VisualDice;

import Cards.VehicleCardData;
import InGameResources.Dice.Dice;
import VisualCards.VisualCard;
import VisualCards.VisualHand;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import misc.Cost;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class VisualDiceTest extends Application{

    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage primaryStage){
        Dice dice1 = new Dice(Dice.Color.BLUE);
        Dice dice2 = new Dice(Dice.Color.RED);
        Dice dice3 = new Dice(Dice.Color.YELLOW);
        dice1.roll();
        dice2.roll();
        dice3.roll();
        VisualRolledDice rolledDice = new VisualRolledDice();
        rolledDice.insert(dice1);
        rolledDice.insert(dice2);
        rolledDice.insert(dice3);
        Group group = new Group();
        group.getChildren().add(rolledDice.draw());
        primaryStage.setScene(new Scene(group));
        primaryStage.show();
    }
}
