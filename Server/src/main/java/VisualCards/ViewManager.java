package VisualCards;

import Cards.Hand;
import Cards.Layout.CardsLayout;
import Cards.VehicleCardData;
import MapServer.Board;
import MapServer.BoardField;
import MapServer.BoardStructure;
import MapServer.SimpleField;
import Players.Player;
import Settings.Settings;
import Table.Table;
import VisualBoard.VisualBoardCreator;
import VisualBoard.VisualBoard;
import VisualDice.VisualDice;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;
import java.util.TreeMap;

public class ViewManager extends Application {
    private TreeMap<Integer, VehicleCardData> cardsData = new TreeMap<>();
    private CardMap.StaticMap cardsVisual = new CardMap.StaticMap();
    private ArrayList<Boolean> useCard = new ArrayList<>();
    private static Random generator = new Random();
    private VisualHand hand;
    private VisualVehicle vehicle;
    private VisualBoard visualBoard;
    private UseVehicle use;
    private Stage stage;
    private Player player;
    private Board board;
    private int position=0;

    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage primaryStage){
        board = gameSetup();
        try {
            Settings settings = Settings.getSettings();
            String resPref = settings.getResourcesPath();
           VisualBoardCreator boardCreator = new VisualBoardCreator(board);
            boardCreator.setBoardSprite(resPref+"GraphicDescription/board.jpg");
            boardCreator.getInfoFromFile(resPref+"GraphicDescription/description.txt");
            boardCreator.setPawn(0, resPref+"pionek3.jpg");
            boardCreator.setPawn(1, resPref+"pionek4.jpg");
            visualBoard = boardCreator.getVisualBoard();
        }catch (Exception e){}
        stage = primaryStage;
        readResorces();
        CardsLayout layout = new CardsLayout();
        vehicle = new VisualVehicle(layout, this);
        hand = new VisualHand(this);
        vehicle.setMap(cardsVisual);
        visualHand(4);
        stage.show();
    }

    private void randomHand(int i){
        ArrayList<Integer> tempUse = new ArrayList<>();
        for(int j=0; j<i; j++){
            int random = generator.nextInt(6);
            while(useCard.get(random)==true || tempUse.contains(random)){
                random = generator.nextInt(6);
            }
            System.out.print(random+" ");
            hand.insertCard(random, cardsVisual.get(random));
            tempUse.add(random);
        }System.out.println();
    }

    void visualHand(int i){
        hand.clear();
        randomHand(i);
        Group group = new Group();
        group.getChildren().add(hand.draw());
        stage.setScene(new Scene(group, 1003, 599));
    }

    void visualVehicle(){
        Group group = new Group();
        group.getChildren().add(vehicle.draw());
        stage.setScene(new Scene(group, 1003, 599));
    }

    void visualBoard(){
        visualBoard.setMyApp(this);
        Group group = new Group();
        visualBoard.actualize();
        group.getChildren().add(visualBoard.draw());
        stage.setScene(new Scene(group, 1003, 599));
    }

    public void useVehicle(){
        Group group = new Group();
        use = new UseVehicle(vehicle.getLayout(), this);
        use.setMap(cardsVisual);
        group.getChildren().add(use.draw());
        stage.setScene(new Scene(group, 1003, 599));
    }

    public void setPosition(int i){
        position += i;
    }

    public int getPosition(){
        return position;
    }

    void addDice(VisualDice dice){
        vehicle.addDice(dice);
    }

    void addCard(VisualCard card){
        vehicle.addCard(card);
    }

    ArrayList<Boolean> getUseCard(){
        return useCard;
    }

    public Board getBoard(){
        return board;
    }

    private void readResorces(){
        Settings settings = Settings.getSettings();
        String resPref = settings.getResourcesPath();
        ReadCard read = new ReadCard();
        File file = new File(resPref+"CardsDescription/arachnolegs.txt");
        VisualCard vehicle = read.readCard(file, cardsVisual);
        cardsData.put(vehicle.getCard().getID(), (VehicleCardData)vehicle.getCard());
        useCard.add(false);
        vehicle = read.readCard(new File(resPref+"CardsDescription/boiler.txt"), cardsVisual);
        cardsData.put(vehicle.getCard().getID(), (VehicleCardData)vehicle.getCard());
        useCard.add(false);
        vehicle = read.readCard(new File(resPref +"CardsDescription/gyrostat.txt"), cardsVisual);
        cardsData.put(vehicle.getCard().getID(), (VehicleCardData)vehicle.getCard());
        useCard.add(false);
        vehicle = read.readCard(new File(resPref+"CardsDescription/aerostat.txt"), cardsVisual);
        cardsData.put(vehicle.getCard().getID(), (VehicleCardData)vehicle.getCard());
        useCard.add(false);
        vehicle = read.readCard(new File(resPref+"CardsDescription/blastPipe.txt"), cardsVisual);
        cardsData.put(vehicle.getCard().getID(), (VehicleCardData)vehicle.getCard());
        useCard.add(false);
        vehicle = read.readCard(new File(resPref+"CardsDescription/heatSink.txt"), cardsVisual);
        cardsData.put(vehicle.getCard().getID(), (VehicleCardData)vehicle.getCard());
        useCard.add(false);
        vehicle = read.readCard(new File(resPref+"CardsDescription/hoverJets.txt"), cardsVisual);
        cardsData.put(vehicle.getCard().getID(), (VehicleCardData)vehicle.getCard());
        useCard.add(false);
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
