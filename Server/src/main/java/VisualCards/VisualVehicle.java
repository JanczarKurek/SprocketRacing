package VisualCards;

import Cards.Joints;
import Cards.Layout.CardInLayout;
import Cards.Layout.CardsLayout;
import Cards.VehicleCardData;
import InGameResources.Dice.Dice;
import VisualBoard.VisualElement;
import VisualDice.VisualDice;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;

import java.util.HashMap;
import java.util.LinkedList;

public class VisualVehicle implements VisualElement {
    private CardsLayout layout;
    private LinkedList<CardInLayout> vehicle;
    private CardMap.StaticMap map;
    private LinkedList<VisualDice> dices;
    private LinkedList<VisualCard> cards;
    private Application myApp;
    private VisualCard waitingCard;
    private HashMap<CardInLayout, Node> nodeMap = new HashMap<>();
    private VisualDice waitingDice;

    public VisualVehicle(CardsLayout layout, Application app){
        myApp = app;
        dices = new LinkedList<>();
        cards = new LinkedList<>();
        vehicle = new LinkedList<>();
        this.layout = layout;
        vehicle.addAll(layout.getTrain());
    }

    void addDice(VisualDice dice){
        dices.add(dice);
    }

    void addCard(VisualCard card){
        cards.add(card);
    }

    public void  setMap(CardMap.StaticMap map){
        this.map=map;
    }


    private void drawPlaces(Group group, VehicleCardData data){
        System.out.println("drawPlaces");
        Joints joints = data.getJoints();
        CardShadow shadow;
        for(CardInLayout card : nodeMap.keySet()) {
            Node node = nodeMap.get(card);
            Joints joints2 = card.getCard().getJoints();
            if (joints2.isLeft() && joints.isRight()) {
                System.out.println("n1");
                shadow = new CardShadow();
                Node node1 = shadow.draw();
                node1.setTranslateY(node.getTranslateY());
                node1.setTranslateX(node.getTranslateX() - 350);
                group.getChildren().add(node1);
                node1.setOnMouseClicked(event -> {
                    System.out.println("click");
                    CardInLayout newCard = new CardInLayout((VehicleCardData) waitingCard.getCard(), card.getCoordinates().getKey() - 1, card.getCoordinates().getValue());
                    layout.add(newCard);
                    waitingCard = null;
                    actualize();
                });
            }
            if (joints2.isRight() && joints.isLeft()) {

                System.out.println("n2");
                shadow = new CardShadow();
                Node node1 = shadow.draw();
                node1.setTranslateY(node.getTranslateY());
                node1.setTranslateX(node.getTranslateX() + 350);
                group.getChildren().add(node1);
                node1.setOnMouseClicked(event -> {
                    System.out.println("click");
                    CardInLayout newCard = new CardInLayout((VehicleCardData) waitingCard.getCard(), card.getCoordinates().getKey() + 1, card.getCoordinates().getValue());
                    layout.add(newCard);
                    waitingCard = null;
                    actualize();
                });
            }
            if (joints2.isUp() && joints.isDown()) {
                System.out.println("n3");
                shadow = new CardShadow();
                Node node1 = shadow.draw();
                node1.setTranslateY(node.getTranslateY() - 233);
                node1.setTranslateX(node.getTranslateX());
                group.getChildren().add(node1);
                node1.setOnMouseClicked(event -> {
                    System.out.println("click");
                    CardInLayout newCard = new CardInLayout((VehicleCardData) waitingCard.getCard(), card.getCoordinates().getKey(), card.getCoordinates().getValue() - 1);
                    layout.add(newCard);
                    waitingCard = null;
                    actualize();
                });
            }
            if (joints.isDown() && joints2.isUp()) {
                System.out.println("n4");
                shadow = new CardShadow();
                Node node1 = shadow.draw();
                node1.setTranslateY(node.getTranslateY() + 233);
                node1.setTranslateX(node.getTranslateX());
                group.getChildren().add(node1);
                node1.setOnMouseClicked(event -> {
                    System.out.println("click");
                    CardInLayout newCard = new CardInLayout((VehicleCardData) waitingCard.getCard(), card.getCoordinates().getKey(), card.getCoordinates().getValue() + 1);
                    layout.add(newCard);
                    waitingCard = null;
                    actualize();
                });
            }
        }
    }
    public Node draw(){
        Group group = new Group();
        System.out.println("Number of dices "+dices.size());
        //button
        if(dices.getFirst().getDice().getValue()==0) {
            Button roll = new Button("ROLL");
            roll.setOnAction(event -> {
                for (VisualDice visualDice : dices) {
                    Dice dice = visualDice.getDice();
                    dice.roll();
                    group.getChildren().remove(roll);
                }
                int j = 1;
                for (VisualDice visualDice : dices) {
                    visualDice.actualize();
                    Node node = visualDice.draw();
                    node.setTranslateX(10 + 60 * j);
                    node.setTranslateY(30);
                    node.setOnMouseClicked(event1 -> {
                        System.out.println("dice click");
                        waitingDice = visualDice;
                    });
                    group.getChildren().add(node);
                    j++;
                }
            });
            roll.setTranslateY(30);
            roll.setTranslateX(10);
            group.getChildren().add(roll);
        }

        Button board = new Button("RACE!");
        board.setOnAction(event -> {
            ((ViewManager)myApp).visualBoard();
        });
        group.getChildren().add(board);
        board.setTranslateY(60);
        board.setTranslateX(10);

        //dices
        int i=1;
        for(VisualDice visualDice : dices){
            Node node = visualDice.draw();
            node.setTranslateX(10+60*i);
            node.setTranslateY(30);
            group.getChildren().add(node);
            node.setOnMouseClicked(event -> {
                System.out.println("dice click");
                waitingDice = visualDice;
            });
            i++;
        }
        //names
        i=0;
        for(VisualCard visualCard : cards){
            Node node2 = visualCard.getName().draw();
            node2.setTranslateX(40+150*i);
            node2.setTranslateY(20);
            node2.prefWidth(100);
            node2.prefHeight(100);
            group.getChildren().add(node2);
            node2.setOnMouseClicked(event2 -> {
                if(vehicle.size()==0) {
                    layout.add((VehicleCardData) visualCard.getCard(), 1, 1);
                    actualize();
                }
                else{
                    waitingCard = visualCard;
                    drawPlaces(group, (VehicleCardData) visualCard.getCard());
                }
                cards.remove(visualCard);
                //actualize();
            });
            i++;
        }
        //vehicle
        for(CardInLayout card : vehicle){
            try {
                Node node = map.get(card.getCard().getID()).draw();
                node.setTranslateY(50+card.getCoordinates().getValue()*223);
                node.setTranslateX(50+card.getCoordinates().getKey()*350);
                VisualVehicleCardController controller = new VisualVehicleCardController(map.get(card.getCard().getID()), node);
                group.getChildren().add(node);
                group.getChildren().add(controller.draw());
                node.setOnMouseClicked(event3 -> {
                    System.out.println("card click");
                    try {
                        card.getCard().getDiceSlots().insert(waitingDice.getDice());
                        dices.remove(waitingDice);
                    }catch (Exception e){
                        System.err.println("Dice error "+e.getClass().getName());
                    }
                    System.out.println("add dice");
                    actualize();
                });
                nodeMap.put(card, node);
            }catch (Exception e){
                System.err.println("Controller error "+ e.getClass().getName());
            }
        }
        return group;
    }
    public void actualize(){
        vehicle = layout.getTrain();
        ((ViewManager) myApp).visualVehicle();
    }
    public CardsLayout getLayout(){
        return layout;
    }
}
