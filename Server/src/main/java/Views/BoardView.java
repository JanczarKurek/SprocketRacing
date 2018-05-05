package Views;

import MapServer.Board;
import javafx.application.*;
import javafx.scene.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.*;

import java.net.URL;
import java.net.URLConnection;
import java.util.*;

public class BoardView extends Application {
    private String image;
    private String description;
    private int sceneWidth;
    private int sceneHeight;
    private int fieldSize;
    private int numberOfFileds;
    private HashMap<Integer,FieldPair> fieldsCenter= new HashMap<>();
    private Board board;

    public void setDescription(String image, String description){
        this.image=image;
        this.description=description;
    }

    public void setBoard(Board state){
        board=state;
    }
    private Parent create(){
        Pane root= new Pane();
        try {
            URLConnection url = new URL(description).openConnection();
            Scanner scanner = new Scanner(url.getInputStream());
            sceneWidth =scanner.nextInt();
            sceneHeight= scanner.nextInt();
            fieldSize = scanner.nextInt();
            numberOfFileds=scanner.nextInt();
            for(int i=0; i<numberOfFileds; i++){
                fieldsCenter.put(i,new FieldPair(scanner.nextInt(), scanner.nextInt()));
            }

        }catch (Exception e){System.out.println("error "+e.getClass());}
        Image background=new Image(image, true);
        ImageView imgView = new ImageView(background);
        root.getChildren().add(imgView);
        addPlayers(root);
        return root;
    }

    private void addPlayers(Pane root){
        for(int i=0; i<board.getNumberOfPlayers(); i++){
            int idField = board.getPlayerPosition(i);
            FieldPair field = fieldsCenter.get(idField);
            int x =field.getX()+((field.getPlayers()%3)*(fieldSize/3));
            int y =field.getY()+((field.getPlayers()/3)*(fieldSize/3));
            PlayerView player = new PlayerView(x, y, i);
            field.addPlayer();
            root.getChildren().add(player);
        }

    }
    public void start(Stage primaryStage){
        Scene scene=new Scene(create(), sceneWidth, sceneHeight);
        primaryStage.setTitle("Sprocket Racing Board");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
