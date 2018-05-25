package VisualCards;

import Cards.VehicleCardData;
import Settings.Settings;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import misc.Cost;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class VehicleCardTest extends Application {

    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage primaryStage){
        try {
            Settings settings = Settings.getSettings();
            String resPref = settings.getResourcesPath();
            Group group = new Group();
            VisualHand hand = new VisualHand();
            File file = new File(resPref + "CardsDescription/arachnolegs.txt");
            VehicleCardData vehicle = readCard(file);
            hand.insertCard(0, new VisualCard(vehicle));
            vehicle = readCard(new File(resPref+"CardsDescription/boiler.txt"));
            hand.insertCard(1, new VisualCard(vehicle));
            vehicle = readCard(new File(resPref+"CardsDescription/gyrostat.txt"));
            hand.insertCard(2, new VisualCard(vehicle));
            vehicle = readCard(new File(resPref+"CardsDescription/aerostat.txt"));
            hand.insertCard(3, new VisualCard(vehicle));
            group.getChildren().add(hand.draw());
            primaryStage.setScene(new Scene(group));
            primaryStage.show();
        } catch (FileNotFoundException e) {
            System.err.println("File not found!" + e.toString());
        }
    }
    private VehicleCardData readCard(File file){
        try{
            FileInputStream stream = new FileInputStream(file);
            Scanner fileReader = new Scanner(stream);
            VehicleCardData vehicle = new VehicleCardData();
            vehicle.setName(fileReader.nextLine());
            vehicle.setJoints(fileReader.nextBoolean(), fileReader.nextBoolean(), fileReader.nextBoolean(), fileReader.nextBoolean());
            Cost cost = new Cost(fileReader.nextInt(), fileReader.nextInt(), fileReader.nextInt(), fileReader.nextInt());
            vehicle.setCost(cost);
            return vehicle;
        }catch (Exception e) {
            System.err.println("File not found");
        }
        return null;
    }
    private VehicleCardData readSpecialCard(File file){
        try{
            FileInputStream stream = new FileInputStream(file);
            Scanner fileReader = new Scanner(stream);
            VehicleCardData vehicle = new VehicleCardData();
            vehicle.setName(fileReader.nextLine());
            vehicle.setJoints(fileReader.nextBoolean(), fileReader.nextBoolean(), fileReader.nextBoolean(), fileReader.nextBoolean());
            Cost cost = new Cost(fileReader.nextInt(), fileReader.nextInt(), fileReader.nextInt(), fileReader.nextInt());
            vehicle.setCost(cost);
            return vehicle;
        }catch (Exception e) {
            System.err.println("File not found");
        }
        return null;
    }
}