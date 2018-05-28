package VisualBoard;

import MapServer.Board;
import SmallFunctionalFeaturesDamnYouJava.Functional;
import javafx.scene.image.Image;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class VisualBoardCreator {
    private VisualBoard visualBoard;
    public VisualBoardCreator(Board logicalBoard){
        visualBoard = new VisualBoard();
        visualBoard.setBoard(logicalBoard);
    }
    public void setBoardSprite(String path) throws FileNotFoundException {
        File file = new File(path);
        visualBoard.setBoardSprite(new Image(new FileInputStream(file)));
    }
    public void setPawn(Integer player, String spritePath) throws FileNotFoundException {
        visualBoard.insertPlayer(player, new VisualPawn(new Image(new FileInputStream(new File(spritePath)))));
    }
    public void insertField(Integer id, int xPos, int yPos, int xSize, int ySize){
        visualBoard.insertField(id, new VisualField(xSize, ySize), new Position(xPos, yPos));
    }

    public void getInfoFromFile(String path) throws FileNotFoundException {
        File file = new File(path);
        FileInputStream stream = new FileInputStream(file);
        Scanner fileReader = new Scanner(stream);
        fileReader.nextInt();
        fileReader.nextInt();
        int fieldSize = fileReader.nextInt();
        int fieldNumber = fileReader.nextInt();
        for(int i : Functional.range(fieldNumber)){
            visualBoard.insertField(i,new VisualField(fieldSize, fieldSize), new Position(fileReader.nextInt(), fileReader.nextInt()));
        }
    }

    public VisualBoard getVisualBoard() {
        return visualBoard;
    }
}
