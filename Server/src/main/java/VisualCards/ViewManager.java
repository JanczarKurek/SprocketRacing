package VisualCards;

import Cards.Deck;
import Cards.VehicleCardData;
import MapServer.Board;
import MapServer.BoardField;
import MapServer.BoardStructure;
import MapServer.SimpleField;
import Cards.LoadedDeck;
import Settings.Settings;
import Table.Table;
import VisualBoard.VisualBoardCreator;
import VisualBoard.VisualBoard;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import VisualBoard.StartPage;
import javafx.scene.control.ScrollPane;
import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;
import java.util.TreeMap;

public class ViewManager extends Application {
    private TreeMap<Integer, VehicleCardData> cardsData = new TreeMap<>();
    private ArrayList<Boolean> useCard = new ArrayList<>();
    private ArrayList<Stage> stages = new ArrayList<>();
    private static Random generator = new Random();
    private VisualBoard visualBoard;
    private UseVehicle use;
    private Stage stage;
    private Table table;
    private Board board;
    private int position=1;

    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage primaryStage){
        stage = primaryStage;
        board = gameSetup();
        String pref = Settings.getSettings().getResourcesPath();
        ArrayList<Deck> list = new ArrayList();
        list.add(new LoadedDeck(pref + "/Files/Deck1").getDeck());
        list.add(new LoadedDeck(pref + "/Files/Deck2").getDeck());
        list.add(new LoadedDeck(pref + "/Files/Deck3").getDeck());
        list.add(new LoadedDeck(pref + "/Files/Deck4").getDeck());
        table = new Table(board, list);
        System.out.println("size deck from list " +list.get(0).size());
        System.out.println(table.getCurrentPhase().getClass().getName());
        //plansza - do zmiany
        try {
            VisualBoardCreator boardCreator = new VisualBoardCreator(board);
            boardCreator.setBoardSprite(pref + "/GraphicDescription/board.jpg");
            boardCreator.getInfoFromFile(pref + "/GraphicDescription/description.txt");
            boardCreator.setPawn(0, pref + "/pionek3.jpg");
            boardCreator.setPawn(1, pref + "/pionek4.jpg");
            visualBoard = boardCreator.getVisualBoard();
        }catch (Exception e){
            System.out.println("file not found "+e.getMessage());
        }
        startPage();
        stage.show();
    }

    public void addPlayer(){
        stages.add(new Stage());
    }


    void startPage(){
        Group group = new Group();
        group.getChildren().add(new StartPage(this).draw());
        stage.setScene(new Scene(group));
    }

    public void showStages(){
        int playerID = 0;
        visualHand();
        for(Stage stagePlayer : stages) {
            playerID++;
            stagePlayer.show();
        }
    }

    void visualHand(){
        for(int i=0; i<table.numberOfPlayers(); i++) {
            Group group =  new Group();
            group.getChildren().add(new VisualHand(this, table.getPlayer(i)).draw());
            stages.get(i).setScene(new Scene(group, 1003, 599));
        }
    }

    void visualVehicle(int playerID){
        Group group = new Group();
        ScrollPane scroll = new ScrollPane();
        group.getChildren().add(new VisualVehicle(table.getPlayer(playerID).getMyVehicle(), this, table.getPlayer(playerID)).draw());
        scroll.setContent(group);
        stages.get(playerID).setScene(new Scene(scroll, 1003, 599));
    }

    void visualBoard(int playerID){
        visualBoard.setMyApp(this);
        Group group = new Group();
        visualBoard.actualize();
        group.getChildren().add(visualBoard.draw());
        stage.setScene(new Scene(group, 1003, 599));
    }

    void waitForPrevPlayer(int playerID){
        Group group = new Group();
        stages.get(playerID).setScene(new Scene(group, 1003, 599));
    }

    public void useVehicle(){
        /*Group group = new Group();
        use = new UseVehicle(vehicle.getLayout(), this);
        use.setMap(cardsVisual);
        group.getChildren().add(use.draw());
        stage.setScene(new Scene(group, 1003, 599));*/
    }

    public void setPosition(int i){
        position += i;
    }

    public Table getTable(){
        return table;
    }
    public int getPosition(){
        return position;
    }


    void addCard(VisualCard card){
        // vehicle.addCard(card);
    }

    ArrayList<Boolean> getUseCard(){
        return useCard;
    }

    public Board getBoard(){
        return board;
    }

    private static Board gameSetup(){
        LinkedList<Integer> PlayersList = new LinkedList<>();
        /*for (int i = 0; i < 2; i++) {
            PlayersList.add(i);
        }*/
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