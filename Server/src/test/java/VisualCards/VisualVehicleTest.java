package VisualCards;

import Cards.CardUsagePipCost;
import Cards.Layout.CardInLayout;
import Cards.Layout.CardsLayout;
import Cards.VehicleCard;
import Cards.VehicleCardData;
import Cards.VehicleCardEngine;
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

    public void start(Stage primaryStage) {
        Settings settings = Settings.getSettings();
        String resPref = settings.getResourcesPath();
        Group group = new Group();
        File file = new File(resPref+"CardsDescription/gyrostat.txt");
        VisualCard vehicle = readCard(file);
        try {
            Dice dice = new Dice(Dice.Color.YELLOW);
            dice.roll();
            ((VehicleCardData)vehicle.getCard()).getDiceSlots().insert(dice);
        }catch (Exception e){
            System.err.println("Dice error");
        }
        VisualLayout layout = new VisualLayout(new Pair<>(1,1), vehicle);
        layout.insertCard(new Pair<>(1,1), vehicle);
        file = new File(resPref+"CardsDescription/boiler.txt");
        layout.insertCard(new Pair<>(2, 1), readCard(file));
        file = new File(resPref+"CardsDescription/arachnolegs.txt");
        layout.insertCard(new Pair<>(1, 0), readCard(file));
        file = new File(resPref+"CardsDescription/aerostat.txt");
        layout.insertCard(new Pair<>(1, 2), readCard(file));
        group.getChildren().add(new VisualVehicle(layout).draw());
        primaryStage.setScene(new Scene(group));
        primaryStage.show();
    }
    public static void main(String[] args){
        launch(args);
    }

    private VisualCard readCard(File file){
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
            if(color==0)
                engine = new VehicleCardEngine(null, null, new DiceSlotsImpl(size, Dice.Color.RED));
            else if(color==1)
                engine = new VehicleCardEngine(null, null, new DiceSlotsImpl(size, Dice.Color.YELLOW));
            else
                engine = new VehicleCardEngine(null, null, new DiceSlotsImpl(size, Dice.Color.BLUE));
            vehicle.setEngine(engine);
            VisualCard visualCard = new VisualCard(vehicle);
            if(color==0)
                visualCard.setColorSlot(Dice.Color.RED);
            else if(color==1)
                visualCard.setColorSlot(Dice.Color.YELLOW);
            else
                visualCard.setColorSlot(Dice.Color.BLUE);
            visualCard.setNumberSlots(size);
            visualCard.setUsagePipCost(fileReader.nextInt());
            int effectsNumber = fileReader.nextInt();
            for(int i=0; i<effectsNumber; i++){
                int temp = fileReader.nextInt();
                visualCard.addEffect(temp);
            }
            return visualCard;
        }catch (FileNotFoundException e) {
            System.err.println("Input file not found " +file.getPath());
        }catch (NoSuchElementException e2){
            System.err.println("No such element");
        }
        return null;
    }
}
