package Run;

import MapServer.*;
import Views.BoardView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.Scanner;

public class Game extends Application {
    public void start(Stage primaryStage) {
        BoardView View = new BoardView();
        ArrayList<Integer> PlayersList = new ArrayList<>();
        String imagePath = "https://github.com/JanczarKurek/SprocketRacing/blob/Board/Server/src/test/resources/GraphicDescription/board.jpg?raw=true";
        String descriptionPath = "https://raw.githubusercontent.com/JanczarKurek/SprocketRacing/Board/Server/src/test/resources/GraphicDescription/description.txt";
        View.setDescription(imagePath, descriptionPath);
        System.out.println("How many players? (max 9)");
        Scanner scannerIn = new Scanner(System.in);
        int number = scannerIn.nextInt();

        for (int i = 0; i < number; i++) {
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

        Board board = new Board(boardStructure, PlayersList, 0);
        View.setBoard(board);

        Scene scene = new Scene(View.create(), View.getWidth(), View.getHeight());
        primaryStage.setScene(scene);
        primaryStage.show();
        while (true) {
            System.out.println("Move player number: (Or exit)");
            int player = -1;
            scannerIn = new Scanner(System.in);
            try {
                player = scannerIn.nextInt();
            } catch (Exception e) {
                System.exit(0);
            }
            System.out.println("Length of path: ");
            int length = scannerIn.nextInt();
            System.out.println("Path: ");
            ArrayList<Integer> pathList = new ArrayList<>();
            for (int i = 0; i < length; i++) {
                pathList.add(scannerIn.nextInt());
            }

            try {
                PawnController pawn = board.getController(player);
                pawn.move(new Path(pathList));

            } catch (Exception e) {
                System.out.println("error " + e.getClass());
            }
            scene = new Scene(View.create(), View.getWidth(), View.getHeight());
            primaryStage.setScene(scene);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}

