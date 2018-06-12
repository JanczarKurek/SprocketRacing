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
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
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
    }

    private Node drawCost( VisualSingleCost cost, int position){
        Node out = cost.draw();
        out.setTranslateX(500+position*21);
        out.setTranslateY(150);
        return out;
    }

    private Node drawSlash(Node slash, int position){
        slash.setTranslateX(500+position*21+5);
        slash.setTranslateY(150);
        return slash;
    }

    private LinkedList<Pair<Node,Integer>> drawCostManager(VisualCard card, Group group){
        int position=0;
        LinkedList<Pair<Node,Integer>> costs= new LinkedList<>();
        VisualSingleCost singlecost=null;
        for(int j=0; j<card.getCard().getCost().getRed(); j++) {
            singlecost = new VisualSingleCost(0, card.getCard().getCost().getRed());
            costs.add(new Pair(drawCost(singlecost,position), 0));
            position++;
        }
        if(singlecost != null && card.getCard().getCost().getRed()>0)
            singlecost = null;
        if(card.getCard().getCost().getYellow()>0){
//            group.getChildren().add(drawSlash(  singlecost.getSlash(), position));
            position++;
        }
        for(int j=0; j<card.getCard().getCost().getYellow(); j++) {
            singlecost = new VisualSingleCost(1, card.getCard().getCost().getYellow());
            costs.add(new Pair(drawCost(singlecost,position), 1));
            position++;
        }
        if(card.getCard().getCost().getYellow()>0)
            singlecost = null;
        if(singlecost != null && card.getCard().getCost().getBlue()>0){
            group.getChildren().add(drawSlash( singlecost.getSlash(), position));
            position++;
        }
        for(int j=0; j<card.getCard().getCost().getBlue(); j++) {
            singlecost = new VisualSingleCost(2, card.getCard().getCost().getBlue());
            costs.add(new Pair(drawCost(singlecost,position), 2));
            position++;
        }
        if(card.getCard().getCost().getBlue()>0)
            singlecost = null;
        if(singlecost != null && card.getCard().getCost().getCogs()>0){
            group.getChildren().add(drawSlash(singlecost.getSlash(), position));
            position++;
        }
        for(int j=0; j<card.getCard().getCost().getCogs(); j++) {
            singlecost = new VisualSingleCost(3, card.getCard().getCost().getCogs());
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
                sell.setTranslateX((card.getWidth()/2)*(3));
                sell.setTranslateY((card.getHeight()/2));
                Button take = new Button("TAKE");
                take.setTranslateX((card.getWidth()/2)*(3)+50);
                take.setTranslateY(card.getHeight()/2);
                group.getChildren().add(sell);
                group.getChildren().add(take);
                sell.setOnAction(value ->  {
                    try {
                        player.chooseCard(card.getCard().getID());
                    }catch (WrongMove mv){
                        System.err.println("wrong mv " + mv.getMessage());
                    }
                    LinkedList<Pair<Node, Integer>> costs = new LinkedList<>();
                    costs.addAll(drawCostManager(card, group));
                    for(Pair<Node,Integer> pair : costs){

                        group.getChildren().add(pair.getKey());
                        pair.getKey().setOnMouseClicked(val -> {
                            if(pair.getValue()==0) {
                                try {
                                    player.sellCard(Dice.Color.RED);
                                }catch (Exception e){
                                    System.err.println("sell exception: "+e.getMessage());
                                }
                            }
                            else if(pair.getValue()==1) {
                                try {
                                    player.sellCard(Dice.Color.YELLOW);
                                }catch (Exception e){
                                    System.err.println("sell exception: "+e.getMessage());
                                }
                            }
                            else if(pair.getValue()==2) {
                                try {
                                    player.sellCard(Dice.Color.BLUE);
                                }catch (Exception e){
                                    System.err.println("sell exception: "+e.getMessage());
                                }
                            }
                            else  if(pair.getValue()==3) {
                                try {
                                    player.sellCard();
                                }catch (Exception e){
                                    System.err.println("sell exception: "+e.getMessage());
                                }
                            }
                            try {
                                ((ViewManager) myApp).visualHand(player.getId());
                            }catch (Exception e){}
                            finally {
                                return;
                            }
                        });
                    }

                });
                take.setOnAction(value ->  {
                    try {
                        player.chooseCard(card.getCard().getID());
                        player.takeCard();
                        ((ViewManager) myApp).visualVehicle(player.getId());
                        return;
                    }catch (Exception e){
                        System.err.println("Choose card : Wrong move!"+e.getMessage());
                    }
                });
            }};
        node.setOnMouseClicked(handler);
    }


    @Override
    public Node draw() {
        try{
            player.vote();
            ((ViewManager) myApp).visualVehicle(player.getId());
            return null;
        }catch (WrongMove e){
            System.out.println("vote " + e.getMessage() + e.getClass().getName());
        }
        Group group = new Group();
        Button aquire = new Button("AQUIRE HAND");
        aquire.setTranslateX((new VisualCard(null).getWidth()/2)*(3));
        aquire.setTranslateY((new VisualCard(null).getHeight()/2+50));
        group.getChildren().add(aquire);
        aquire.setOnMouseClicked(event -> {
            try {
                player.aquireHand();
                int i=0;
                for(Card cardData : player.getMyHand().getCards()){
                    VisualCard card = (new LoadedCard((VehicleCardData)cardData)).getVisualCard();
                    System.out.println(card==null);
                    Node node = card.draw();
                    node.setScaleY(0.5);
                    node.setScaleX(0.5);
                    if(i<2) {
                        node.setTranslateY(10);
                        node.setTranslateX(i*(card.getWidth()/2)+(i+1)*10);

                    }
                    else{
                        node.setTranslateY(card.getHeight()/2+30);
                        node.setTranslateX((i%2)*(card.getWidth()/2)+((i%2)+1)*10);
                    }
                    group.getChildren().add(node);
                    buttons(card, node, group);
                    i++;
                }
                group.getChildren().remove(aquire);
            }catch (Exception e){
                System.out.println("Constructor error "+ e.getMessage());
                System.out.println("I am waiting");
                ((ViewManager) myApp).visualHand(player.getId());
                return;
            }
        });
        return group;
    }

    @Override
    public void actualize() {
    }
}
