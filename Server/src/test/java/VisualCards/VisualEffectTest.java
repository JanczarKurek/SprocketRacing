package VisualCards;

import Cards.OnCardEffects.GetResourceEffect;
import InGameResources.ResourceWallet;
import MapServer.DamageEffect;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.Test;

import static org.junit.Assert.*;

public class VisualEffectTest extends Application{

    @Override
    public void start(Stage primaryStage) throws Exception {
        VisualEffect effect = new VisualEffect(new DamageEffect(3));
        VisualEffect effect1= new VisualEffect(new GetResourceEffect(new ResourceWallet(2, 2, 1, 4)));
        Group group = new Group();
        Node node = effect1.draw();
        node.setTranslateY(50.0);
        group.getChildren().add(effect.draw());
        group.getChildren().add(node);
        primaryStage.setScene(new Scene(group));
        primaryStage.show();
    }

    public static void main(String[] args){
        launch(args);
    }
}