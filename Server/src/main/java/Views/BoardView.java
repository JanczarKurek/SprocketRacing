package Views;

import javafx.application.*;
import javafx.scene.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.*;

import java.io.File;
import java.util.*;

public class BoardView extends Application {
    private String image;
    private File description;
    private int sceneWidth;
    private  int sceneHeight;
    private int numberOfFileds;
    private ArrayList<FieldPair> fieldsCenter= new ArrayList<>();

    public void setDescription(String image, File description){
        this.image=image;
        this.description=description;
    }

    private Parent create(){
        Pane root= new Pane();
        try {
            Scanner scanner = new Scanner(description);
            sceneWidth =scanner.nextInt();
            sceneWidth= scanner.nextInt();
            numberOfFileds=scanner.nextInt();
            for(int i=0; i<numberOfFileds; i++){
                fieldsCenter.add(new FieldPair(scanner.nextInt(), scanner.nextInt()));
            }

        }catch (Exception e){System.out.println("error");}
        Image background=new Image(image,true);
        ImageView imgView = new ImageView(background);
        root.getChildren().add(imgView);
        return root;
    }
    public void start(Stage primaryStage){
        Scene scene=new Scene(create(), sceneWidth, sceneHeight);
        primaryStage.setTitle("Sprocket Racing Board");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
