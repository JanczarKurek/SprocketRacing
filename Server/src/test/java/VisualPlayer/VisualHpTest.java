package VisualPlayer;

import Players.HpBar;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

import static org.junit.Assert.*;

public class VisualHpTest extends Application{

    @Override
    public void start(Stage primaryStage) throws Exception {
        HpBar hpBar = new HpBar(5, -5);
        hpBar.hitFor(2);
        Group group = new Group();
        group.getChildren().add(new VisualHp(hpBar).draw());
        Stage stage = new Stage();
        stage.setScene(new Scene(group));
        stage.show();
    }

    public static void main(String[] args){
        launch(args);
    }
}