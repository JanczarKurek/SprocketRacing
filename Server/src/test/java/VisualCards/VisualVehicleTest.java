package VisualCards;

import Cards.Layout.CardInLayout;
import Cards.Layout.CardsLayout;
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
import java.util.LinkedList;
import java.util.Scanner;

public class VisualVehicleTest extends Application {
    public void start(Stage primaryStage) {
            Settings settings = Settings.getSettings();
            String resPref = settings.getResourcesPath();
            Group group = new Group();
            File file = new File(resPref+"CardsDescription/gyrostat.txt");
            CardInLayout cockpit = new CardInLayout(readCard(file), 1, 1);
            file = new File(resPref + "CardsDescription/boiler.txt");
            CardInLayout right = new CardInLayout(readCard(file), 2, 1);
            file = new File(resPref+"CardsDescription/arachnolegs.txt");
            CardInLayout down= new CardInLayout(readCard(file), 1, 0);
            file = new File(resPref+"CardsDescription/aerostat.txt");
            CardInLayout up = new CardInLayout(readCard(file), 1, 2);
            LinkedList<CardInLayout> list= new LinkedList<>();
            list.add(cockpit);
            list.add(right);
            list.add(up);
            list.add(down);
            CardsLayout layout = new CardsLayout(cockpit,  list);
            group.getChildren().add(new VisualVehicle(layout).draw());
            primaryStage.setScene(new Scene(group));
            primaryStage.show();
    }
    public static void main(String[] args){
        launch(args);
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
}
