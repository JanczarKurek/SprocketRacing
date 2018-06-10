package VisualCards;

import MapServer.DamageEffect;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.Test;

import static org.junit.Assert.*;

public class VisualEffectTest extends Application{

    @Override
    public void start(Stage primaryStage) throws Exception {
        VisualEffect effect = new VisualEffect(new DamageEffect(3));
        Group group = new Group();
        group.getChildren().add(effect.draw());
        primaryStage.setScene(new Scene(group));
        primaryStage.show();
    }

    public static void main(String[] args){
        launch(args);
    }
}