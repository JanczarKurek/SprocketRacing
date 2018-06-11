package VisualCards;

import Cards.Card;
import Cards.LoadedCard;
import Cards.VehicleCardData;
import ErrorsAndExceptions.WrongMove;
import InGameResources.Dice.Dice;
import Players.Player;
import VisualCards.ViewManager;
import VisualBoard.VisualElement;
import VisualDice.VisualDice;
import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.TreeMap;

public class VisualHand implements VisualElement {


    private Application myApp;
    private Player player;
    public VisualHand(Application app, Player player){
        myApp = app;
        this.player = player;
        try {
            player.aquireHand();
        }catch (Exception e){
            System.err.println("aquire exception " + e.getClass().getName());
        }
    }

    private Node drawCost( VisualSingleCost cost, int position){
        Node out = cost.draw();
        out.setTranslateX(820+position*21);
        out.setTranslateY(300);
        return out;
    }

    private Node drawSlash(Node slash, int position){
        slash.setTranslateX(820+position*21+5);
        slash.setTranslateY(300);
        return slash;
    }

    private LinkedList<Pair<Node,Integer>> drawCostManager(VisualCard card, Group group){
        int position=0;
        LinkedList<Pair<Node,Integer>> costs= new LinkedList<>();
        VisualSingleCost singlecost=null;
        for(int j=0; j<card.redCost(); j++) {
            singlecost = new VisualSingleCost(0, card.redCost());
            //group.getChildren().add(drawCost( singlecost, position));
            costs.add(new Pair(drawCost(singlecost,position), 0));
            position++;
        }
        if(card.redCost()>0){
            group.getChildren().add(drawSlash(  singlecost.getSlash(), position));
            position++;
        }
        for(int j=0; j<card.yellowCost(); j++) {
            singlecost = new VisualSingleCost(1, card.yellowCost());
            //group.getChildren().add(drawCost(singlecost ,position));
            costs.add(new Pair(drawCost(singlecost,position), 1));
            position++;
        }
        if(card.yellowCost()>0){
            group.getChildren().add(drawSlash( singlecost.getSlash(), position));
            position++;
        }
        for(int j=0; j<card.blueCost(); j++) {
            singlecost = new VisualSingleCost(2, card.blueCost());
            //group.getChildren().add(drawCost(singlecost ,position));
            costs.add(new Pair(drawCost(singlecost,position), 2));
            position++;
        }
        if(card.blueCost()>0){
            group.getChildren().add(drawSlash(singlecost.getSlash(), position));
            position++;
        }
        for(int j=0; j<card.cogsCost(); j++) {
            singlecost = new VisualSingleCost(3, card.blueCost());
            //group.getChildren().add(drawCost(singlecost ,position));
            costs.add(new Pair(drawCost(singlecost,position), 3));
            position++;
        }
        return costs;
    }
    private void buttons(VisualCard card, Node node, Group group){
        EventHandler handler = new EventHandler() {
            @Override
            public void handle(Event event) {
                Button sell = new Button("SELL");
                sell.setTranslateX(card.getWidth()*(2.5));
                sell.setTranslateY(card.getHeight());
                Button take = new Button("TAKE");
                take.setTranslateX(card.getWidth()*(2.5)+50);
                take.setTranslateY(card.getHeight());
                group.getChildren().add(sell);
                group.getChildren().add(take);
                sell.setOnAction(value ->  {
                    try {
                        player.chooseCard(0);
                    }catch (WrongMove mv){
                        System.err.println("wrong mv");
                    }
                    LinkedList<Pair<Node, Integer>> costs = new LinkedList<>();
                    costs.addAll(drawCostManager(card, group));
                    for(Pair<Node,Integer> pair : costs){

                        group.getChildren().add(pair.getKey());
                        pair.getKey().setOnMouseClicked(val -> {
                            if(pair.getValue()==0) {
                                /*for(int j=0; j<((VehicleCardData)card.getCard()).getCost().getRed(); j++){
                                    ((ViewManager) myApp).addDice(new VisualDice(new Dice(Dice.Color.RED)));
                                    System.out.println("RED");
                                }*/
                                try {
                                    player.sellCard(Dice.Color.RED);
                                }catch (Exception e){
                                    System.err.println("sell exception: "+e.getClass().getName());
                                }
                            }
                            else if(pair.getValue()==1) {
                                /*for(int j=0; j<((VehicleCardData)card.getCard()).getCost().getYellow(); j++){
                                    ((ViewManager) myApp).addDice(new VisualDice(new Dice(Dice.Color.YELLOW)));
                                    System.out.println("YELLOW");
                                }*/
                                try {
                                    player.sellCard(Dice.Color.YELLOW);
                                }catch (Exception e){
                                    System.err.println("sell exception: "+e.getClass().getName());
                                }
                            }
                            else if(pair.getValue()==2) {
                                /*for(int j=0; j<((VehicleCardData)card.getCard()).getCost().getBlue(); j++){
                                    ((ViewManager) myApp).addDice(new VisualDice(new Dice(Dice.Color.BLUE)));
                                    System.out.println("BLUE");
                                }*/
                                try {
                                    player.sellCard(Dice.Color.BLUE);
                                }catch (Exception e){
                                    System.err.println("sell exception: "+e.getClass().getName());
                                }
                            }
                            else  if(pair.getValue()==3) {
                              /*  System.out.println("COG");
                                for(int j=0; j<((VehicleCardData)card.getCard()).getCost().getCogs(); j++){
                                    ((ViewManager) myApp).addDice(new VisualDice(new Dice(Dice.Color.BLUE)));
                                    System.out.println("BLUE");
                                }*/
                                try {
                                    player.sellCard();
                                }catch (Exception e){
                                    System.err.println("sell exception: "+e.getClass().getName());
                                }
                            }
                            //if(player.getHandSize()>1){
                                try {
                                    player.aquireHand();
                                    ((ViewManager) myApp).visualHand();
                                }catch(Exception e){
                                if(e.getMessage().contains("prev players should pass hand first")){
                                        ((ViewManager) myApp).waitForPrevPlayer(player.getId());
                                }
                                else {
                                    try {
                                        player.vote();
                                    } catch (Exception e2) {
                                        System.err.println(e2.getClass().getName());
                                    }
                                    ((ViewManager) myApp).visualVehicle(player.getId());
                                }
                                }
                           // }
                        });
                    }

                });
                take.setOnAction(value ->  {
                    try {
                        player.chooseCard(0);
                        player.takeCard();
                    }catch (Exception e){
                        System.err.println("Choose card : Wrong move!"+e.getClass().getName()+" id: "+0);
                    }
                    ((ViewManager)myApp).addCard(card);
                    if(player.getHandSize()>1){
                        try{
                            player.aquireHand();
                        }
                        catch(Exception e){
                            System.err.println(e.getClass().getName());
                        }
                        ((ViewManager) myApp).visualHand();
                        System.out.println(card.getCard().getID());
                    }
                    else{
                        try {
                            player.vote();
                        }catch(Exception e){
                            System.err.println(e.getClass().getName());
                        }
                        ((ViewManager) myApp).visualVehicle(player.getId());
                    }

                });
            }
        };
        node.setOnMouseClicked(handler);
    }

    @Override
    public Node draw() {
        Group group = new Group();
        int i=0;
        for(Card cardData : player.getMyHand().getCards()){
            VisualCard card = (new LoadedCard((VehicleCardData)cardData)).getVisualCard();
            System.out.println(card==null);
            Node node = card.draw();
            if(i<2) {
                node.setTranslateY(20);
                node.setTranslateX(i*(card.getWidth())+(i+1)*20);

            }
            else{
                node.setTranslateY(card.getHeight()+40);
                node.setTranslateX(((i%2)*card.getWidth())+((i%2)+1)*20);
            }
            group.getChildren().add(node);
            buttons(card, node, group);
            i++;
        }
        return group;
    }

    @Override
    public void actualize() {
    }
}
