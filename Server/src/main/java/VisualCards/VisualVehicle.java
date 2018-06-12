package VisualCards;

import Cards.*;
import Cards.Layout.CardInLayout;
import Cards.Layout.CardsLayout;
import Players.Player;
import VisualBoard.VisualElement;
import VisualDice.VisualDice;
import VisualPlayer.VisualWallet;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.util.Pair;

import java.util.HashMap;
import java.util.LinkedList;

public class VisualVehicle implements VisualElement {
    private CardsLayout layout;
    private Application myApp;
    private VisualCard waitingCard;
    private HashMap<CardInLayout, Node> nodeMap = new HashMap<>();
    private VisualDice waitingDice;
    private Player player;
    private boolean rollButton=false;

    public VisualVehicle(CardsLayout layout, Application app, Player player){
        myApp = app;
        this.layout = layout;
        this.player = player;
    }


    private void drawPlaces(Pane group, VehicleCardData data, int positionOnHand){
        System.out.println("drawPlaces");
        CardShadow shadow = new CardShadow();
        for(Pair<Integer, Integer> pair: player.getMyVehicle().possiblePositions(data)){
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
        }
    }
    public Node draw(){
        VBox box = new VBox();
        box.setPrefHeight(599);
        box.setPrefWidth(1003);
        HBox up = new HBox();
        up.setPrefHeight(50);
        up.setPrefWidth(1003);
        up.setBackground(new Background(new BackgroundFill(Color.LIGHTGREY, CornerRadii.EMPTY, Insets.EMPTY)));
        StackPane down = new StackPane();
        //button
        if(rollButton==true) {
            Button roll = new Button("ROLL");
            roll.setOnAction(event -> {
                rollButton=false;
                try {
                    player.roll();
                }catch (Exception e){
                    System.err.println(e.getMessage());
                }
            });
            roll.setTranslateY(30);
            roll.setTranslateX(10);
            up.getChildren().add(roll);
        }

        Button finishPut = new Button("FINISH PUT");
        finishPut.setOnAction(event -> {
            try {
                player.acceptVehicleLayout();
                ((ViewManager)myApp).visualHand();
            }catch (Exception e){
                System.err.println(e.getMessage());
            }
        });

        up.getChildren().add(finishPut);

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
        Node nodeWallet = new VisualWallet(player.getMyWallet()).draw();
        nodeWallet.setScaleX(0.5);
        nodeWallet.setScaleY(0.5);
        nodeWallet.setTranslateX(0);
        nodeWallet.setTranslateY(0);
        up.getChildren().add(nodeWallet);


        //names
        int i = 0;
        for(Card chosenCard : player.getUnusedCards()) {
            VisualCard visualCard = new LoadedCard((VehicleCardData) chosenCard).getVisualCard();
            Node node2 = visualCard.draw();
            node2.setScaleX(0.5);
            node2.setScaleY(0.5);
            node2.setTranslateX(0 + (233/2)*i);
            node2.setTranslateY(0);
            up.getChildren().add(node2);
            node2.setOnMouseClicked(event2 -> {
                waitingCard = visualCard;
                drawPlaces(down, (VehicleCardData) visualCard.getCard(), visualCard.getCard().getID());
            });
        }
        //vehicle

        Node cockpit = (new LoadedCard(player.getMyVehicle().getCockpit().getCard()).getVisualCard().draw());
        cockpit.setScaleX(0.5);
        cockpit.setScaleY(0.5);
        cockpit.setTranslateX(50+player.getMyVehicle().getCockpit().getCoordinates().getKey()*(350/2));
        cockpit.setTranslateY(50+player.getMyVehicle().getCockpit().getCoordinates().getValue()*(233/2));
        down.getChildren().add(cockpit);
        for(CardInLayout card : player.getMyVehicle().getTrain()) {
            try {
                System.out.println(card.getCard().getID() + " " + card.getCoordinates().getKey() + " " + card.getCoordinates().getValue());
                Node node = new VisualCardInLayout(card).draw();
                node.setTranslateY(50 + card.getCoordinates().getValue() * (223/2));
                node.setTranslateX(50 + card.getCoordinates().getKey() * (350/2));
                VisualVehicleCardController controller = new VisualVehicleCardController(((new LoadedCard(card.getCard())).getVisualCard()), node);
                down.getChildren().add(node);
               /* node.setOnMouseClicked(event3 -> {
                    System.out.println("card click");
                    try {
                        card.getCard().getDiceSlots().insert(waitingDice.getDice());
                    } catch (Exception e) {
                        System.err.println("Dice error " + e.getClass().getName());
                    }
                    System.out.println("add dice");
                    actualize();
                });
                nodeMap.put(card, node);*/
            } catch (Exception e) {
                System.err.println("Controller error " + e.getClass().getName());
            }

        }

        box.getChildren().addAll(up, down);
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
}