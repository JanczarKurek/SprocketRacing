package VisualCards;

import InGameResources.Dice.Dice;
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

    /*private Image handSprite;

    public void setHandSprite(Image handSprite) {
        this.handSprite = handSprite;
    }*/

    private Application myApp;
    private TreeMap<Integer, VisualCard> CardsOnHand;

    public VisualHand(Application app){
        myApp = app;
        CardsOnHand = new TreeMap<>();
    }

    public void insertCard(Integer number, VisualCard card){
        CardsOnHand.put(number, card);
    }

    public void clear(){
        CardsOnHand.clear();
    }

    public int size(){
        return CardsOnHand.size();
    }

    void selectUseCard(ArrayList<Boolean> list, int i){
        list.remove(i);
        list.add(i, true);
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
                    //card.getCard().getCost();
                    LinkedList<Pair<Node, Integer>> costs = new LinkedList<>();
                    costs.addAll(drawCostManager(card, group));
                    for(Pair<Node,Integer> pair : costs){

                        group.getChildren().add(pair.getKey());
                        pair.getKey().setOnMouseClicked(val -> {
                            if(pair.getValue()==0) {
                                for(int j=0; j<card.redCost(); j++){
                                ((ViewManager) myApp).addDice(new VisualDice(new Dice(Dice.Color.RED)));
                                System.out.println("RED");}
                            }
                            else if(pair.getValue()==1) {
                                for(int j=0; j<card.yellowCost(); j++){
                                    ((ViewManager) myApp).addDice(new VisualDice(new Dice(Dice.Color.YELLOW)));
                                System.out.println("YELLOW");}
                            }
                            else if(pair.getValue()==2) {
                                for(int j=0; j<card.blueCost(); j++){
                                ((ViewManager) myApp).addDice(new VisualDice(new Dice(Dice.Color.BLUE)));
                                System.out.println("BLUE");}
                            }
                            else  if(pair.getValue()==3) {
                                System.out.println("COG");
                            }
                             if(CardsOnHand.size()>1){
                                ((ViewManager) myApp).visualHand(CardsOnHand.size()-1);
                            }
                            else{
                                 ((ViewManager) myApp).visualVehicle();
                             }
                        });
                    }

                });
                take.setOnAction(value ->  {
                    selectUseCard(((ViewManager) myApp).getUseCard(), card.getCard().getID());
                    ((ViewManager)myApp).addCard(card);
                    if(CardsOnHand.size()>1){
                        ((ViewManager) myApp).visualHand(CardsOnHand.size()-1);
                        System.out.println(card.getCard().getID());
                    }else{
                        ((ViewManager) myApp).visualVehicle();
                    }

                });
                //sell.setOnMouseClicked();
                //co maja robic przyciski???
            }
        };
        node.setOnMouseClicked(handler);
    }

    @Override
    public Node draw() {
        Group group = new Group();
        /*ImageView imageView = new ImageView();
        imageView.setImage(handSprite);
        group.getChildren().add(imageView);*/
        int i=0;
        for(VisualCard card : CardsOnHand.values()){
            Node node = card.draw();
            if(i<2) {
                node.setTranslateY(20);
                node.setTranslateX(i*(card.getWidth())+(i+1)*20);

            }
            else{
                node.setTranslateY(card.getHeight()+40);
                node.setTranslateX(((i%2)*card.getWidth())+((i%2)+1)*20);
            }
            VisualVehicleCardController controller = new VisualVehicleCardController(card, node);
            group.getChildren().add(node);
            group.getChildren().add(controller.draw());
            buttons(card, node, group);
            i++;
        }
        return group;
    }

    @Override
    public void actualize() {
    }
}
