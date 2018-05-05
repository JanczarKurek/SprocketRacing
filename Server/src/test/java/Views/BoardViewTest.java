package Views;

import MapServer.*;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.stage.Stage;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import java.util.Arrays;

public class BoardViewTest{

    private  BoardView View = new BoardView();
    private ArrayList<Integer> PlayersList = new ArrayList<>();
    private  Board board;

    @Before
    public void beforeTests(){
        String imagePath = "https://github.com/JanczarKurek/SprocketRacing/blob/Board/Server/src/test/resources/GraphicDescription/board.jpg?raw=true";
        String descriptionPath = "https://raw.githubusercontent.com/JanczarKurek/SprocketRacing/Board/Server/src/test/resources/GraphicDescription/description.txt";
        View.setDescription(imagePath, descriptionPath);

        for (int i = 0; i < 4; i++) {
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

        board = new Board(boardStructure, PlayersList, 0);
        View.setBoard(board);
    }

    @Test
    //Czterech zawodników na pozycji startowej
    public void testBasic() throws InterruptedException{
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                new JFXPanel();
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        View.start(new Stage());
                    }
                });
            }
        });
        thread.start();
        thread.sleep(10000);
    }
    //przemieszczenie pionków
    @Test
    public void test2() throws InterruptedException{
        Path path1 = new Path(Arrays.asList(0, 1));
        Path path2 = new Path(Arrays.asList(0, 1, 2, 3));

        try {
            PawnController pawn0 = board.getController(1);
            pawn0.move(path2);

            PawnController pawn1 = board.getController(0);
            pawn1.move(path2);

            PawnController pawn2 = board.getController(3);
            pawn2.move(path1);

        }catch(Exception e){
            System.out.println("error "+e.getClass());
        }

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                new JFXPanel();
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        View.start(new Stage());
                    }
                });
            }
        });
        thread.start();
        thread.sleep(10000);
    }
}