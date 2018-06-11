package VisualCards;

import Cards.CardUsageCost;
import Cards.CardUsageDiceCost;
import Cards.CardUsagePipCost;
import Cards.OnCardEffects.GetResourceEffect;
import InGameResources.ResourceWallet;
import MapServer.DamageEffect;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

import static org.junit.Assert.*;

public class VisualUsageCostTest extends Application{
    @Override
    public void start(Stage primaryStage) throws Exception {
        VisualUsageCost c1 = new VisualUsageCost(new CardUsageDiceCost());
        VisualUsageCost c2 = new VisualUsageCost(new CardUsagePipCost(5));
        Group group = new Group();
        group.getChildren().add(c1.draw());
        Node node = c2.draw();
        node.setTranslateY(50.0);
        group.getChildren().add(node);
        primaryStage.setScene(new Scene(group));
        primaryStage.show();
    }

    public static void main(String[] args){
        launch(args);
    }

}