package VisualCards;

import Cards.CardEffect;
import Cards.Joints;
import Cards.VehicleCardData;
import VisualBoard.VisualElement;
import VisualDice.VisualDice;
import javafx.scene.Group;
import javafx.scene.Node;
import misc.Effect;

import java.util.Collection;
import java.util.LinkedList;

public class VisualVehicleCardController implements VisualElement{
    private Node node;
    private VisualCard card;

    public VisualVehicleCardController(VisualCard card, Node node){
        this.card=card;
        this.node=node;
    }

    private Node drawJoint(VisualJoint joint, VisualCard card){
        try{
        Node out = joint.draw();
        if(joint.getPosition()<2){ //left of right
            out.setTranslateY(node.getTranslateY()+(card.getHeight()/2)-joint.getHeigth()/2);
            if(joint.getPosition()==0) { //left
                out.setTranslateX(node.getTranslateX()-(joint.getWidth()-joint.getHeigth())/2);
            }
            else{ //right
                out.setTranslateX(node.getTranslateX()+card.getWidth()-2*joint.getHeigth()+(joint.getWidth()-joint.getHeigth())/2);
            }
        }
        else{ //up or down
            out.setTranslateX(node.getTranslateX()+card.getWidth()/2-joint.getWidth()/2);
            if(joint.getPosition()==2) { //up
                out.setTranslateY(node.getTranslateY());
            }
            else{ //down
                out.setTranslateY(node.getTranslateY()+card.getHeight()-joint.getHeigth());
            }
        }
        return out;

        }catch (Exception e){System.err.println("Error "+e.getClass().getName());}
        return null;
    }

    private Node drawName(VisualName name){
        Node out = name.draw();
        out.setTranslateX(node.getTranslateX()+20);
        out.setTranslateY(node.getTranslateY()+30);
        return out;
    }

    private Node drawCost( VisualSingleCost cost, int position){
        Node out = cost.draw();
        out.setTranslateX(node.getTranslateX()+196+position*21);
        out.setTranslateY(node.getTranslateY()+21);
        return out;
    }
    private Node drawSlash(Node slash, int position){
        slash.setTranslateX(node.getTranslateX()+196+position*21+5);
        slash.setTranslateY(node.getTranslateY()+21);
        return slash;
    }
    private Node drawSlots(){
        Group out = new Group();
        if(card.getSlots()==null)
            System.err.println("NULL");
        for(int i=0; i<card.getSlots().getSize(); i++){
            VisualDiceSlot visualSlot = new VisualDiceSlot(card.getColorSlot());
            Node temp = visualSlot.draw();
            temp.setTranslateX(node.getTranslateX()+50*(i%2)+40);
            temp.setTranslateY(node.getTranslateY()+50*(i/2)+65);
            out.getChildren().add(temp);
            try{
                VisualDice dice = new VisualDice(card.getSlots().getDice(i));
                Node diceVisual = dice.draw();
                diceVisual.setTranslateY(temp.getTranslateY()+5);
                diceVisual.setTranslateX(temp.getTranslateX()+5);
                out.getChildren().add(diceVisual);

            }catch (Exception e){
                //System.err.println("Slots error "+e.getClass().getName());
            }
        }
        return out;
    }
    private  Node drawPipCost(){
        Node cost = new VisualPipCost(card.getUsagePipCost()).draw();
        cost.setTranslateX(node.getTranslateX()+145);
        cost.setTranslateY(node.getTranslateY()+100);
        return cost;
    }
    private Node drawEffects(Collection<CardEffect> effectColl){
       Group group = new  Group();
        int slashes = 0;
        int width = 0;
        for(int i=0; i<effectColl.size(); i++){

           // group.getChildren().add(new VisualEffectPack(effectColl.get(i)));
        }
        return group;
    }
    @Override
    public Node draw() {
        Group group = new Group();
        int i=0;
        //boolean left, boolean right, boolean up, boolean down
        if(((VehicleCardData)card.getCard()).getJoints().isLeft()){
            group.getChildren().add(drawJoint(new VisualJoint(true, false, false, false), card));
        }
        if(((VehicleCardData)card.getCard()).getJoints().isRight()){
            group.getChildren().add(drawJoint(new VisualJoint(false, true, false, false), card));
        }
        if(((VehicleCardData)card.getCard()).getJoints().isUp()){
            group.getChildren().add(drawJoint(new VisualJoint(false, false, true, false), card));
        }
        if(((VehicleCardData)card.getCard()).getJoints().isDown()){
            group.getChildren().add(drawJoint(new VisualJoint(false, false, false, true), card));
        }
        //}
        group.getChildren().add(drawName( card.getName()));
        int position=0;
        VisualSingleCost singlecost=null;
        for(int j=0; j<card.getCard().getCost().getRed(); j++) {
            singlecost = new VisualSingleCost(0, card.getCard().getCost().getRed());
            group.getChildren().add(drawCost( new VisualSingleCost(0, card.getCard().getCost().getRed()), position));
            position++;
        }
        if(singlecost != null  && card.getCard().getCost().getYellow()>0){
            group.getChildren().add(drawSlash(  singlecost.getSlash(), position));
            position++;
            singlecost = null;
        }
        for(int j=0; j<card.getCard().getCost().getYellow(); j++) {
            singlecost = new VisualSingleCost(1, card.getCard().getCost().getYellow());
            group.getChildren().add(drawCost(singlecost ,position));
            position++;
        }
        if( singlecost != null && card.getCard().getCost().getBlue()>0){
            System.out.println("null? "+ (singlecost==null));
            group.getChildren().add(drawSlash( singlecost.getSlash(), position));
            position++;
            singlecost = null;
        }
        for(int j=0; j<card.getCard().getCost().getBlue(); j++) {
            singlecost = new VisualSingleCost(2, card.getCard().getCost().getBlue());
            group.getChildren().add(drawCost(singlecost ,position));
            position++;
        }
        if( singlecost != null && card.getCard().getCost().getCogs()>0){
            System.out.println("null? "+ (singlecost.getSlash()==null));
          //  group.getChildren().add(drawSlash( singlecost.getSlash(), position));
            position++;
            singlecost = null;
        }
        for(int j=0; j<card.getCard().getCost().getCogs(); j++) {
            group.getChildren().add(drawCost(new VisualSingleCost(3, card.getCard().getCost().getCogs()), position));
            position++;
        }
        group.getChildren().add(drawSlots());
        group.getChildren().add(drawPipCost());
//        group.getChildren().add(drawEffects(((VehicleCardData)card.getCard()).getEngine().getEffects()));
        return group;
    }
    @Override
    public void actualize() {
    }

}
