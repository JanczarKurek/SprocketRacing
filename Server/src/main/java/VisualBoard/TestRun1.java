package VisualBoard;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class TestRun1 extends Application{

    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage primaryStage){
        File file = new File("/home/janczar/.tajnyLab/MaciekMateuszowski/SprocketRacing/Server/src/test/resources/pionek1.jpg");
        Image pawnSprite;
        try {
            pawnSprite = new Image(new FileInputStream(file));
            VisualPawn pawn = new VisualPawn(pawnSprite);
            Group group = new Group();
            group.getChildren().add(pawn.draw());
            primaryStage.setScene(new Scene(group));
            primaryStage.show();
        } catch (FileNotFoundException e) {
            System.err.println("File not found!");
        }
    }
}
