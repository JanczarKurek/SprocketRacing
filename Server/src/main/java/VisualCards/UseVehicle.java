package VisualCards;

import Cards.Layout.CardInLayout;
import Cards.Layout.CardsLayout;
import Cards.VehicleCardData;
import ErrorsAndExceptions.WrongMove;
import InGameResources.Dice.Dice;
import MapServer.Path;
import MapServer.PawnController;
import VisualBoard.VisualElement;
import VisualDice.VisualDice;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import InGameResources.Dice.Dice;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.TreeMap;

public class UseVehicle implements VisualElement {
    private CardsLayout layout;
    private LinkedList<CardInLayout> vehicle;
    private CardMap.StaticMap map;
    private Application myApp;
    TreeMap<Integer, PawnController> controllers;

    public UseVehicle(CardsLayout layout, Application app){
        myApp = app;
        vehicle = new LinkedList<>();
        this.layout = layout;
        vehicle.addAll(layout.getTrain());
        controllers = new TreeMap<>();
        try {
            controllers.put(0, ((ViewManager) myApp).getBoard().getController(0));
        }catch (Exception e){
            System.err.println("Controllers exception");
        }
        LinkedList<Integer> path = new LinkedList<>();
        path.add(0);
        path.add(1);
    }

    public void  setMap(CardMap.StaticMap map){
        this.map=map;
    }

       public Node draw(){
        Group group = new Group();
        //button

        Button board = new Button("RACE!");
        board.setOnAction(event -> {
            ((ViewManager)myApp).visualBoard();
        });
        group.getChildren().add(board);
        board.setTranslateY(40);
        board.setTranslateX(10);

        System.out.println("veh size"+vehicle.size());
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
                    VisualCard visCard = map.get(card.getCard().getID());
                    LinkedList<Integer> effects = visCard.getEffects();
                    int usagePipCost = visCard.getUsagePipCost();
                    int sumPips=0;
                    for(Dice dice : visCard.getSlots().getDice())
                        sumPips+=dice.getValue();
                    int moveLength=0;
                    for(Integer i : effects){
                        if(i == 0 || i == 1){
                            System.out.println("moveLength++");
                            moveLength++;
                        }
                    }
                    if(usagePipCost==7)
                        moveLength *=  visCard.getSlots().getDice().size();
                    else{
                        moveLength *= (sumPips/usagePipCost);
                    }
                    LinkedList<Integer> path = new LinkedList<>();
                    int j=0;
                    for(int i=((ViewManager)myApp).getPosition(); j<=moveLength; i++){
                        j++;
                        path.add(i);
                    }
                    System.out.println(path.toString());
                    ((ViewManager)myApp).setPosition(path.getLast());
                    try {
                        controllers.get(0).move(new Path(path));
                    } catch (WrongMove wrongMove) {
                        System.err.println("Wrong move!");
                    }
                });
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
}
