package VisualPlayer;

import Cards.OnCardEffects.GetResourceEffect;
import InGameResources.ResourceWallet;
import MapServer.DamageEffect;
import VisualCards.VisualEffect;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

import static org.junit.Assert.*;

public class VisualWalletTest extends Application{

    @Override
    public void start(Stage primaryStage) throws Exception {
        VisualWallet wallet = new VisualWallet(new ResourceWallet(2, 1, 2, 5));
        Group group = new Group();
        group.getChildren().add(wallet.draw());
        primaryStage.setScene(new Scene(group));
        primaryStage.show();
    }

    public static void main(String[] args){
        launch(args);
    }
}