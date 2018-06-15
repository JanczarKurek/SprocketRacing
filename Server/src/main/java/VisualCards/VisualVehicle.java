package VisualCards;

import Cards.*;
import Cards.Layout.CardInLayout;
import Cards.Layout.CardsLayout;
import MapServer.Board;
import MapServer.Path;
import Players.Player;
import Table.Table;
import VisualBoard.VisualElement;
import VisualDice.VisualDice;
import VisualDice.VisualDiceBunch;
import VisualPlayer.VisualWallet;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class VisualVehicle implements VisualElement {
    private CardsLayout layout;
    private Application myApp;
    private VisualCard waitingCard;
    private HashMap<CardInLayout, Node> nodeMap = new HashMap<>();
    public HashSet<Integer> waitingDices = new HashSet<>();
    private Player player;
    private boolean rollButton=false;
    private boolean ventPhase = false;
    private int waitingCardPipes = 0;
    private int waitingVentpipes = 0;
    private boolean removeBool = false;
    private Node visualWalletNode;

    public void setBoard(Board board) {
        this.board = board;
    }

    private Board board;
    public VisualVehicle(CardsLayout layout, Application app, Player player){
        myApp = app;
        this.layout = layout;
        this.player = player;

    }

    private void drawWallet(HBox up){
        if(visualWalletNode!=null)
            up.getChildren().remove(visualWalletNode);
        VisualWallet visualWallet = new VisualWallet(player.getMyWallet());
        visualWallet.setVehicle(this);
        Node nodeWallet = visualWallet.draw();
        nodeWallet.setScaleX(0.5);
        nodeWallet.setScaleY(0.5);
        nodeWallet.setTranslateX(0);
        nodeWallet.setTranslateY(0);
        visualWalletNode = nodeWallet;
        up.getChildren().add(nodeWallet);

    }


    private void drawPlaces(Pane group, VehicleCardData data, int positionOnHand){
        System.out.println("drawPlaces");
        CardShadow shadow = new CardShadow();
        /*for(Pair<Integer, Integer> pair: player.getMyVehicle().possiblePositions(data)){
            Node node = shadow.draw();
            node.setScaleX(0.5);
            node.setScaleY(0.5);
            node.setTranslateX(50+pair.getKey()*(350/2));
            node.setTranslateY(50 + pair.getValue()* (223/2));
            group.getChildren().add(node);
            node.setOnMouseClicked(event -> {
                System.out.println("shadow click");
                try {
                    player.putCard(positionOnHand, pair.getKey(), pair.getValue());
                    actualize();

                }catch (Exception e){
                    System.err.println(e.getMessage());
                }
            });
        }*/
        Set set = player.getMyVehicle().getLayout().keySet();
        for(int i = -10; i < 10; ++i) {
            for (int j = -10; j < 10; ++j) {
                Pair<Integer, Integer> pair = new Pair<>(i, j);
                if(!set.contains(pair)) {
                    Node node = shadow.draw();
                    node.setScaleX(0.5);
                    node.setScaleY(0.5);
                    node.setTranslateX(50 + pair.getKey() * (350 / 2));
                    node.setTranslateY(50 + pair.getValue() * (223 / 2));
                    group.getChildren().add(node);
                    node.setOnMouseClicked(event -> {
                        System.out.println("shadow click");
                        try {
                            player.putCard(positionOnHand, pair.getKey(), pair.getValue());
                            actualize();

                        } catch (Exception e) {
                            System.err.println(e.getMessage());
                        }
                    });
                }
            }
        }
    }

    public Node draw(){
        try {
            player.putCard(0,0,0);
        }catch (Exception e){

            System.out.println(e.getMessage());
        }

        VBox box = new VBox();
        box.setPrefHeight(700);
        box.setPrefWidth(650);
        HBox up = new HBox();
        up.setPrefHeight(250);
        up.setPrefWidth(650);
        up.setBackground(new Background(new BackgroundFill(Color.LIGHTGREY, CornerRadii.EMPTY, Insets.EMPTY)));
        StackPane down = new StackPane();
        down.setPrefHeight(400);
        down.setPrefWidth(650);
        //button
        Button finishPut;
        if(player.taskManager.getCurrentTask().type == Player.Task.IDLERACE || player.taskManager.getCurrentTask().type == Player.Task.IDLEDAMAGE)
             finishPut = new Button("VOTE");
        else
            finishPut = new Button("ACCEPT");
        finishPut.setOnAction(event -> {
            try {
                if(player.taskManager.getCurrentTask().type == Player.Task.IDLERACE ){
                    player.vote();
                    ((ViewManager) myApp).visualBoard(player.getId());
                }
                else if(player.taskManager.getCurrentTask().type == Player.Task.IDLEDAMAGE){
                    player.vote();
                    ((ViewManager) myApp).visualHand(player.getId());
                }
                else if(player.taskManager.getCurrentTask().type == Player.Task.IDLEVENT){
                    player.vote();
                    ((ViewManager)myApp).waitForPrevPlayer(player.getId());
                }
                else {
                    player.acceptVehicleLayout();
                    ((ViewManager) myApp).visualHand(player.getId());
                }
            }catch (Exception e){
                System.err.println(e.getMessage());
            }
        });
        //finishPut.setPrefWidth(150);
        up.getChildren().add(finishPut);
        if(player.taskManager.getCurrentTask().type != Player.Task.IDLERACE) {
            Button remove = new Button("TAKE");
            remove.setOnAction(event -> {
                removeBool = true;
            });
            //finishPut.setPrefWidth(150);
            up.getChildren().add(remove);
        }

        if(player.taskManager.getCurrentTask().type == Player.Task.IDLEDAMAGE) {
            Button remove = new Button("REMOVE");
            remove.setOnAction(event -> {
                removeBool = true;
            });
            //finishPut.setPrefWidth(150);
            up.getChildren().add(remove);
        }

        if(rollButton==true) {
            Button roll = new Button("ROLL");
            roll.setOnAction(event -> {
                rollButton=false;
                try {
                    player.roll();
                    drawWallet(up);
                    up.getChildren().remove(up);
                }catch (Exception e){
                    System.err.println(e.getMessage());
                }
            });
            up.getChildren().add(roll);
        }

        if(ventPhase==true) {
            MenuBar menuBar = new MenuBar();
            Menu menuVent = new Menu("VENT ");

            for(Integer i=0; i<player.getMyWallet().getGears(); i+=2) {
                MenuItem number = new MenuItem(i.toString());
                number.setOnAction(new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent event) {
                        System.out.println((number.getText().hashCode())-48 + " usuń");
                        waitingCardPipes += (number.getText().hashCode())-48;
                        try {
                            player.vent(((number.getText().hashCode()) - 48) / 2);
                        }catch (Exception e){
                            System.err.println("vent exception "+ e.getMessage());
                        }

                    }
                });
                menuVent.getItems().add(number);
            }
            menuBar.getMenus().add(menuVent);
            menuBar.setPrefWidth(80);
            up.getChildren().add(menuBar);
        }


        /*Button board = new Button("ACCEPT VEHICLE");
        board.setOnAction(event -> {
            try {
                player.acceptVehicleLayout();
            }catch (Exception e){
                System.err.println(e.getMessage());
            }
            ((ViewManager)myApp).visualHand();
        });
        up.getChildren().add(board);
        board.setTranslateY(60);
        board.setTranslateX(10);*/

        //wallet
        drawWallet(up);

        //names
        int i = 0;
        try {
            for (Card chosenCard : player.getUnusedCards()) {
                VisualCard visualCard = new LoadedCard((VehicleCardData) chosenCard).getVisualCard();
                Node node2 = visualCard.draw();
                node2.setScaleX(0.5);
                node2.setScaleY(0.5);
                node2.setTranslateX(0 + (233 / 2) * i);
                node2.setTranslateY(0);
                up.getChildren().add(node2);
                node2.setOnMouseClicked(event2 -> {
                    waitingCard = visualCard;
                    drawPlaces(down, (VehicleCardData) visualCard.getCard(), visualCard.getCard().getID());
                });
            }
        }catch (Exception e){
            System.out.println("Unused card error "+ e.getMessage());
        }
        //vehicle

        Node cockpit = (new LoadedCard(player.getMyVehicle().getCockpit().getCard()).getVisualCard().draw());
        cockpit.setScaleX(0.5);
        cockpit.setScaleY(0.5);
        cockpit.setTranslateX(50+player.getMyVehicle().getCockpit().getCoordinates().getKey()*(350/2));
        cockpit.setTranslateY(50+player.getMyVehicle().getCockpit().getCoordinates().getValue()*(233/2));
        CardInLayout cardC = player.getMyVehicle().getCockpit();
        cockpit.setOnMouseClicked( event -> {
            if(waitingDices.size()>0 && player.taskManager.getCurrentTask().type == Player.Task.IDLERACE){
                System.out.println("dodaje kosc");
                try {
                    player.useCard(cardC.getCoordinates().getKey(), cardC.getCoordinates().getValue(), waitingDices);
                    player.acceptProposition();
                    //player.runEffects(0);
                    while(player.taskManager.getCurrentTask().type != Player.Task.IDLERACE) {
                        player.runAtomicEffect();
                        if(player.taskManager.getCurrentTask().type == Player.Task.VENT)
                            player.ventOnce(1,1, 0);
                        else if(player.taskManager.getCurrentTask().type == Player.Task.MAKEMOVE) {
                            ArrayList<Integer> path = new ArrayList<>();
                            path.add(board.getPlayerPosition(player.getId()));
                            path.add(board.getPlayerPosition(player.getId())+1);
                            player.makeMove(new Path(path));
                        }
                        if(player.taskManager.getCurrentTask().type == Player.Task.MOVESMOOTH) {
                            ArrayList<Integer> path = new ArrayList<>();
                            path.add(board.getPlayerPosition(player.getId()));
                            path.add(board.getPlayerPosition(player.getId())+1);
                            player.makeMove(new Path(path));
                        }
                        System.out.println("nazwa " + player.taskManager.getCurrentTask().type.name());
                    }
                    System.out.println("po " + player.taskManager.getCurrentTask().type.name());
                    actualize();
                }catch (Exception e){
                    System.err.println("wyjatek " + e.getMessage());
                    waitingDices.clear();
                }
            }
        });
        down.getChildren().add(cockpit);
        for(CardInLayout card : player.getMyVehicle().getTrain()) {
            try {
                System.out.println(card.getCard().getID() + " " + card.getCoordinates().getKey() + " " + card.getCoordinates().getValue());
                VisualCardInLayout temp = new VisualCardInLayout(card);
                Node node = temp.draw();
                node.setTranslateY(50 + card.getCoordinates().getValue() * (223/2));
                node.setTranslateX(50 + card.getCoordinates().getKey() * (350/2));

                down.getChildren().add(node);
                node.setOnMouseClicked(event3 -> {
                    if(waitingDices.size()>0 && player.taskManager.getCurrentTask().type == Player.Task.IDLERACE){
                        System.out.println("dodaje kosc");
                        try {
                            System.out.println(card.getCoordinates().getKey()+" "+card.getCoordinates().getValue());
                            System.out.println(waitingDices.size());
                            player.useCard(card.getCoordinates().getKey(), card.getCoordinates().getValue(), waitingDices);
                            player.acceptProposition();
                            //player.runEffects(0);
                            while(player.taskManager.getCurrentTask().type != Player.Task.IDLERACE) {
                                try{
                                    player.runEffects(0);
                                }catch (Exception e){

                                }
                                player.runAtomicEffect();
                                if(player.taskManager.getCurrentTask().type == Player.Task.VENT)
                                    player.ventOnce(1,1, 0);
                                else if(player.taskManager.getCurrentTask().type == Player.Task.MAKEMOVE) {
                                    ArrayList<Integer> path = new ArrayList<>();
                                    path.add(board.getPlayerPosition(player.getId()));
                                    path.add(board.getPlayerPosition(player.getId())+1);
                                    path.add(board.getPlayerPosition(player.getId())+2);
                                    player.makeMove(new Path(path));
                                }
                                else if(player.taskManager.getCurrentTask().type == Player.Task.MOVESMOOTH) {
                                    ArrayList<Integer> path = new ArrayList<>();
                                    path.add(board.getPlayerPosition(player.getId()));
                                    path.add(board.getPlayerPosition(player.getId())+1);
                                    path.add(board.getPlayerPosition(player.getId())+2);
                                    player.makeMove(new Path(path));
                                }
                                System.out.println("nazwa " + player.taskManager.getCurrentTask().type.name());
                            }
                            System.out.println("po " + player.taskManager.getCurrentTask().type.name());
                            actualize();
                        }catch (Exception e){
                            e.printStackTrace();
                            waitingDices.clear();
                        }
                    }
                    else if(removeBool){
                        System.out.println("click "+card.getCoordinates().getKey()+" "+card.getCoordinates().getValue());
                        try {
                            if(player.taskManager.getCurrentTask().type == Player.Task.IDLEDAMAGE)
                                player.obligatoryRemoveOne(card.getCoordinates().getKey(), card.getCoordinates().getValue());
                            else
                                 player.takeCard(card.getCoordinates().getKey(), card.getCoordinates().getValue());
                            removeBool = false;
                            actualize();
                        }catch (Exception e){
                            System.err.println(e.getMessage());
                        }

                    }
                   else if(waitingCardPipes>0){

                       MenuBar menuBar = new MenuBar();
                       Menu menuVent = new Menu("DICE NO. ");

                       for(Integer j=0; j<card.getCard().getDiceSlots().getSize(); j++) {
                           try {
                               card.getCard().getDiceSlots().getDice(j);
                               MenuItem number = new MenuItem(j.toString());
                               number.setOnAction(new EventHandler<ActionEvent>() {
                                   public void handle(ActionEvent event) {
                                       System.out.println((number.getText().hashCode()) - 48 +" --");
                                       up.getChildren().remove(menuBar);
                                       try {
                                           card.getCoordinates().getKey();
                                           card.getCoordinates().getValue();
                                           number.getText().hashCode();
                                           player.ventOnce(card.getCoordinates().getKey(), card.getCoordinates().getValue(), (number.getText().hashCode()) - 48);
                                       }catch (Exception e){
                                           System.err.println("Wrong vent "+e.getMessage());
                                       }
                                       up.getChildren().remove(menuBar);
                                   }
                               });
                               menuVent.getItems().add(number);
                           }catch (Exception e){
                               System.out.println(e.getMessage());
                           }
                       }
                       menuBar.getMenus().add(menuVent);
                       menuBar.setTranslateX(0);
                       menuBar.setTranslateY(0);
                       menuBar.setPrefWidth(100);
                       up.getChildren().add(menuBar);
                       waitingCardPipes--;


                   }
                });
               // nodeMap.put(card, node);
            } catch (Exception e) {
                System.err.println("Controller error " + e.getClass().getName());
            }

        }
        ScrollPane scrollUp = new ScrollPane();
        scrollUp.setContent(up);
        ScrollPane scrollDown = new ScrollPane();
        scrollDown.setContent(down);
        box.getChildren().addAll(scrollUp, scrollDown);

        return box;
    }
    public void actualize(){
        ((ViewManager) myApp).visualVehicle(player.getId());
    }
    public CardsLayout getLayout(){
        return layout;
    }

    public void setRollButton(boolean rollButton) {
        this.rollButton = rollButton;
    }

    public void setVentPhase(boolean phase){
        ventPhase = phase;
    }
}