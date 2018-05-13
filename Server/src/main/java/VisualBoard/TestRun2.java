package VisualBoard;

import MapServer.Board;
import MapServer.BoardField;
import MapServer.BoardStructure;
import MapServer.SimpleField;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.util.LinkedList;

public class TestRun2 extends Application {

    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage primaryStage){
        try {
            Board board = gameSetup();
            VisualBoardCreator boardCreator = new VisualBoardCreator(board);
            boardCreator.setBoardSprite("/home/janczar/.tajnyLab/MaciekMateuszowski/SprocketRacing/Server/src/test/resources/GraphicDescription/board.jpg");
            boardCreator.getInfoFromFile("/home/janczar/.tajnyLab/MaciekMateuszowski/SprocketRacing/Server/src/test/resources/GraphicDescription/description.txt");
            boardCreator.setPawn(0, "/home/janczar/.tajnyLab/MaciekMateuszowski/SprocketRacing/Server/src/test/resources/pionek3.jpg");
            boardCreator.setPawn(1, "/home/janczar/.tajnyLab/MaciekMateuszowski/SprocketRacing/Server/src/test/resources/pionek4.jpg");
            VisualBoard visualBoard = boardCreator.getVisualBoard();
            TextGameController controller = new TextGameController(board);
            System.out.println("Board created...");
            Group group = new Group();
            visualBoard.actualize();
            System.out.println("Board actualised...");
            Recaller<Node> nodeBoard = new Recaller<>();
            nodeBoard.set(visualBoard.draw());
            group.getChildren().add(nodeBoard.get());
            TextField console = new TextField();
            console.setMinWidth(600);
            group.getChildren().add(console);
            console.setOnKeyPressed(new EventHandler<KeyEvent>()
            {
                @Override
                public void handle(KeyEvent ke)
                {
                    if (ke.getCode().equals(KeyCode.ENTER))
                    {
                        controller.parseCommand(console.getText());
                        visualBoard.actualize();
                        group.getChildren().removeAll(nodeBoard.get());
                        nodeBoard.set(visualBoard.draw());
                        group.getChildren().add(nodeBoard.get());
                        console.clear();
                        console.toFront();
                    }
                }
            });
            System.out.println("Board drawn...");
            primaryStage.setScene(new Scene(group));
            primaryStage.show();
        } catch (FileNotFoundException e) {
            System.err.println("File not found!" + e.toString());
        }
    }

    private static Board gameSetup(){
        LinkedList<Integer> PlayersList = new LinkedList<>();
        for (int i = 0; i < 2; i++) {
            PlayersList.add(i);
        }
        BoardStructure boardStructure = new BoardStructure();

        BoardField boardField1 = new BoardField(new SimpleField(0));
        BoardField boardField2 = new BoardField(new SimpleField(1));
        BoardField boardField3 = new BoardField(new SimpleField(2));
        BoardField boardField4 = new BoardField(new SimpleField(3));
        BoardField boardField5 = new BoardField(new SimpleField(4));
        BoardField boardField6 = new BoardField(new SimpleField(5));
        BoardField boardField7 = new BoardField(new SimpleField(6));

        boardField1.addNext(boardField2);
        boardField2.addNext(boardField3);
        boardField3.addNext(boardField4);
        boardField4.addNext(boardField5);
        boardField5.addNext(boardField6);
        boardField6.addNext(boardField7);

        boardField7.addPrev(boardField6);
        boardField6.addPrev(boardField5);
        boardField5.addPrev(boardField4);
        boardField4.addPrev(boardField3);
        boardField3.addPrev(boardField2);
        boardField2.addPrev(boardField1);

        boardStructure.setField(boardField1);
        boardStructure.setField(boardField2);
        boardStructure.setField(boardField3);
        boardStructure.setField(boardField4);
        boardStructure.setField(boardField5);
        boardStructure.setField(boardField6);
        boardStructure.setField(boardField7);

        boardStructure.setStart(boardField1);
        boardStructure.setEnd(boardField7);

        return new Board(boardStructure, PlayersList, 0);
    }
}