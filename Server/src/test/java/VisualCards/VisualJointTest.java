package VisualCards;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

import static org.junit.Assert.*;

public class VisualJointTest extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Group group = new Group();
        VisualJoint joint4 = new VisualJoint(false, true, false, false);
        Node node = joint4.draw();
        node.setTranslateX(600);
        group.getChildren().add(joint4.draw());
        primaryStage.setScene(new Scene(group));
        primaryStage.show();
    }
}