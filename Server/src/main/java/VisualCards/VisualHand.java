package VisualCards;

import MapServer.Board;
import VisualBoard.VisualElement;
import javafx.scene.Group;
import javafx.scene.Node;

import java.util.TreeMap;

public class VisualHand implements VisualElement {

    /*private Image handSprite;

    public void setHandSprite(Image handSprite) {
        this.handSprite = handSprite;
    }*/
    private TreeMap<Integer, VisualCard> CardsOnHand;

    private VisualHand(){
        CardsOnHand = new TreeMap<>();
    }

    public void insertCard(Integer number, VisualCard card){
        CardsOnHand.put(number, card);
    }

    private Node drawJoint(Node node, VisualJoint joint, VisualCard card){
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
    }
    private Node drawName(Node node, VisualName name){
        Node out = name.draw();
        out.setTranslateX(node.getTranslateX()+20);
        out.setTranslateY(node.getTranslateY()+30);
        return out;
    }
    private Node drawCost(Node node, VisualSingleCost cost, int position){
        Node out = cost.draw();
        out.setTranslateX(node.getTranslateX()+200+position*27);
        out.setTranslateY(node.getTranslateY()+15);
        return out;
    }
    private Node drawSlash(Node node, Node slash, int position){
        slash.setTranslateX(node.getTranslateX()+200+position*27);
        slash.setTranslateY(node.getTranslateY()+13);
        return slash;
    }
    @Override
    public Node draw() {
        Group group = new Group();
        /*ImageView imageView = new ImageView();
        imageView.setImage(handSprite);
        group.getChildren().add(imageView);*/
        int i=0;
        for(VisualCard card : CardsOnHand.values()){
            System.out.println("one card");
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
            for(VisualJoint joint : card.getJoints()) {
                group.getChildren().add(drawJoint(node, joint, card));
            }
            group.getChildren().add(drawName(node, card.getName()));
            int position=0;
            VisualSingleCost singlecost=null;
            for(int j=0; j<card.redCost(); j++) {
                singlecost = new VisualSingleCost(0, card.redCost());
                group.getChildren().add(drawCost(node, new VisualSingleCost(0, card.redCost()), position));
                position++;
            }
            if(card.redCost()>0){
                group.getChildren().add(drawSlash(node,  singlecost.getSlash(), position));
                position++;
            }
            for(int j=0; j<card.yellowCost(); j++) {
                singlecost = new VisualSingleCost(1, card.yellowCost());
                group.getChildren().add(drawCost(node, singlecost ,position));
                position++;
            }
            if(card.yellowCost()>0){
                group.getChildren().add(drawSlash(node,  singlecost.getSlash(), position));
                position++;
            }
            for(int j=0; j<card.blueCost(); j++) {
                singlecost = new VisualSingleCost(2, card.blueCost());
                group.getChildren().add(drawCost(node, new VisualSingleCost(2, card.blueCost()), position));
                position++;
            }
            if(card.blueCost()>0){
                group.getChildren().add(drawSlash(node, singlecost.getSlash(), position));
                position++;
            }
            for(int j=0; j<card.cogsCost(); j++) {
                group.getChildren().add(drawCost(node, new VisualSingleCost(3, card.cogsCost()), position));
                position++;
            }
            i++;
        }
        return group;
    }

    @Override
    public void actualize() {
    }
}
