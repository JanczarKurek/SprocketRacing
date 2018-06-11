package VisualCards;


import Cards.CardUsagePipCost;
import Cards.VehicleCardData;
import Cards.VehicleCardEngine;
import InGameResources.Dice.Dice;
import InGameResources.Dice.DiceSlotsImpl;
import Settings.Settings;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import misc.Cost;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class VehicleCardTest extends Application {

    public static void main(String[] args){
        launch(args);
    }


    @Override
    public void start(Stage primaryStage){
       /* Settings settings = Settings.getSettings();
        String resPref = settings.getResourcesPath();
        Group group = new Group();
        ReadCard read = new ReadCard();
        VisualHand hand = new VisualHand(this);
        CardMap.StaticMap map = new CardMap.StaticMap();
        File file = new File(resPref+"CardsDescription/arachnolegs.txt");
        VisualCard vehicle = read.readCard(file, map);
        hand.insertCard(0, vehicle);
        vehicle = read.readCard(new File(resPref+"CardsDescription/boiler.txt"), map);
        hand.insertCard(1, vehicle);
        vehicle = read.readCard(new File(resPref+"CardsDescription/gyrostat.txt"), map);
        hand.insertCard(2, vehicle);
        vehicle = read.readCard(new File(resPref+"CardsDescription/aerostat.txt"), map);
        hand.insertCard(3, vehicle);
        group.getChildren().add(hand.draw());
        primaryStage.setScene(new Scene(group, 1003, 599));
        primaryStage.show();*/
    }
    /*private VehicleCardData readCard(File file){
        try{
            FileInputStream stream = new FileInputStream(file);
            Scanner fileReader = new Scanner(stream);
            VehicleCardData vehicle = new VehicleCardData();
            vehicle.setName(fileReader.nextLine());
            vehicle.setJoints(fileReader.nextBoolean(), fileReader.nextBoolean(), fileReader.nextBoolean(), fileReader.nextBoolean());
            Cost cost = new Cost(fileReader.nextInt(), fileReader.nextInt(), fileReader.nextInt(), fileReader.nextInt());
            vehicle.setCost(cost);
            int size = fileReader.nextInt();
            int color = fileReader.nextInt(); // 0-red, 1-yellow, 2-blue
            VehicleCardEngine engine;
            CardUsagePipCost pipCost = new CardUsagePipCost(fileReader.nextInt());
            if(color==0)
                engine = new VehicleCardEngine(pipCost, null, new DiceSlotsImpl(size, Dice.Color.RED));
            else if(color==1)
                engine = new VehicleCardEngine(pipCost, null, new DiceSlotsImpl(size, Dice.Color.YELLOW));
            else
                engine = new VehicleCardEngine(pipCost, null, new DiceSlotsImpl(size, Dice.Color.BLUE));
            vehicle.setEngine(engine);
            return vehicle;
        }catch (Exception e) {
            System.err.println("File not found");
        }
        return null;
    }*/
}