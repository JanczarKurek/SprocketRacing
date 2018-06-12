

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class JarTest extends Application{

    @Override
    public void start(Stage primaryStage) throws Exception {
        Group group = new Group();
        Text text = new Text("Dupa biskupa!");
        text.setTranslateY(100);
        text.setTranslateX(100);
        group.getChildren().add(text);
        Stage stage = new Stage();
        stage.setScene(new Scene(group));
        stage.show();
    }

    public static void main(String args[]){
        launch(args);
    }
}
