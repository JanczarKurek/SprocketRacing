package VisualCards;

import Cards.*;
import Cards.Layout.CardInLayout;
import Cards.Layout.CardsLayout;
import InGameResources.Dice.Dice;
import InGameResources.Dice.DiceSlotsImpl;
import Settings.Settings;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Pair;
import misc.Cost;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class VisualVehicleTest extends Application {

    public static void main(String[] args){
        launch(args);
    }
    public void start(Stage primaryStage) {
        Settings settings = Settings.getSettings();
        String resPref = settings.getResourcesPath();
        Group group = new Group();
        File file = new File(resPref+"CardsDescription/gyrostat.txt");
        ReadCard read = new ReadCard();
        CardMap.StaticMap map = new CardMap.StaticMap();
        VisualCard vehicle = read.readCard(file, map);
        try {
            Dice dice = new Dice(Dice.Color.YELLOW);
            dice.roll();
            ((VehicleCardData)vehicle.getCard()).getDiceSlots().insert(dice);
        }catch (Exception e){
            System.err.println("Dice error");
        }
        CardInLayout cockpit = new CardInLayout((VehicleCardData)(vehicle.getCard()), 1, 1);
        file = new File(resPref+"CardsDescription/boiler.txt");
        CardInLayout boiler = new CardInLayout((VehicleCardData)(read.readCard(file, map).getCard()), 2, 1);
        file = new File(resPref+"CardsDescription/arachnolegs.txt");
        CardInLayout arachnolegs = new CardInLayout(((VehicleCardData)read.readCard(file, map).getCard()), 1, 0);
        file = new File(resPref+"CardsDescription/aerostat.txt");
        CardInLayout aerostat = new CardInLayout(((VehicleCardData)read.readCard(file, map).getCard()), 1, 2);
        LinkedList<CardInLayout> train = new LinkedList<>();
        train.add(boiler);
        train.add(arachnolegs);
        train.add(aerostat);
        CardsLayout layout = new CardsLayout(cockpit, train);
        VisualVehicle veh = new VisualVehicle(layout, this);
        veh.setMap(map);
        group.getChildren().add(veh.draw());
        primaryStage.setScene(new Scene(group));
        primaryStage.show();
    }
}
