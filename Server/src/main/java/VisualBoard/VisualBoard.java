package VisualBoard;

import MapServer.Board;
import SmallFunctionalFeaturesDamnYouJava.Functional;
import VisualCards.ViewManager;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Pair;
import javafx.scene.control.Button;
import java.awt.*;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

public class VisualBoard implements VisualElement {

    private Image boardSprite;
    private Application myApp;
    private Integer playerID;

    public void setMyApp(Application myApp){
        this.myApp = myApp;
    }

    public void setBoardSprite(Image boardSprite) {
        this.boardSprite = boardSprite;
    }

    public static class FieldRecord{
        VisualField field;
        Position position;

        public FieldRecord(VisualField field, Position position) {
            this.field = field;
            this.position = position;
        }

        public VisualField getField() {
            return field;
        }

        public void setField(VisualField field) {
            this.field = field;
        }

        public Position getPosition() {
            return position;
        }

        public void setPosition(Position position) {
            this.position = position;
        }
    }

    private TreeMap<Integer, FieldRecord> fields;

    private TreeMap<Integer, VisualPawn> PlayersToPawns;

    public void setBoard(Board board) {
        this.board = board;
    }

    private Board board;

    public VisualBoard(){
        fields = new TreeMap<>();
        PlayersToPawns = new TreeMap<>();
    }

    public void setPlayerID(int playerID){
        this.playerID=playerID;
    }

    public void insertPlayer(Integer player, VisualPawn pawn){
        PlayersToPawns.put(player, pawn);
    }

    public void insertField(Integer id, VisualField field, Position position){
        fields.put(id, new FieldRecord(field, position));
    }

    @Override
    public Node draw() {
        Group group = new Group();
        ImageView imageView = new ImageView();
        imageView.setImage(boardSprite);
        group.getChildren().add(imageView);
        for(FieldRecord fieldRecord : fields.values()){
            Node node = fieldRecord.getField().draw();
            node.setTranslateY(fieldRecord.getPosition().getY());
            node.setTranslateX(fieldRecord.getPosition().getX());
            group.getChildren().add(node);
        }
        Button button = new Button("DAMAGE PHASE");
        button.setOnAction(event -> {
            System.out.println("damage phase!!!");
            ((ViewManager)myApp).visualHp(playerID);
        });
        Random random = new Random();
        group.getChildren().add(button);
        /*if(random.nextBoolean()) {
            group.setScaleX(0.8);
            group.setScaleY(0.5);
        }*/
        return group;
    }

    @Override
    public void actualize() {
        for(VisualField field : Functional.map(fields.values(), FieldRecord::getField)){
            field.clear();
        }
        TreeMap<Integer, Integer> positions = board.getPlayersPositions();
        for(Map.Entry<Integer, Integer> pair : positions.entrySet()){
            fields.get(pair.getValue()).getField().putPawn(PlayersToPawns.get(pair.getKey())); //puts pawn
        }
    }
}
