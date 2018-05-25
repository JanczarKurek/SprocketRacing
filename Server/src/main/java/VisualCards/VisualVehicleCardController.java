package VisualCards;

import Cards.VehicleCardData;
import VisualBoard.VisualElement;
import javafx.scene.Group;
import javafx.scene.Node;

public class VisualVehicleCardController implements VisualElement{
    private Node node;
    private VisualCard card;

    public VisualVehicleCardController(VisualCard card, Node node){
        this.card=card;
        this.node=node;
    }

    private Node drawJoint(VisualJoint joint, VisualCard card){
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
    @Override
    public Node draw() {
        Group group = new Group();
        /*ImageView imageView = new ImageView();
        imageView.setImage(handSprite);
        group.getChildren().add(imageView);*/
        int i=0;
            for(VisualJoint joint : card.getJoints()) {
                group.getChildren().add(drawJoint( joint, card));
            }
            group.getChildren().add(drawName( card.getName()));
            int position=0;
            VisualSingleCost singlecost=null;
            for(int j=0; j<card.redCost(); j++) {
                singlecost = new VisualSingleCost(0, card.redCost());
                group.getChildren().add(drawCost( new VisualSingleCost(0, card.redCost()), position));
                position++;
            }
            if(card.redCost()>0){
                group.getChildren().add(drawSlash(  singlecost.getSlash(), position));
                position++;
            }
            for(int j=0; j<card.yellowCost(); j++) {
                singlecost = new VisualSingleCost(1, card.yellowCost());
                group.getChildren().add(drawCost(singlecost ,position));
                position++;
            }
            if(card.yellowCost()>0){
                group.getChildren().add(drawSlash( singlecost.getSlash(), position));
                position++;
            }
            for(int j=0; j<card.blueCost(); j++) {
                singlecost = new VisualSingleCost(2, card.blueCost());
                group.getChildren().add(drawCost(new VisualSingleCost(2, card.blueCost()), position));
                position++;
            }
            if(card.blueCost()>0){
                group.getChildren().add(drawSlash(singlecost.getSlash(), position));
                position++;
            }
            for(int j=0; j<card.cogsCost(); j++) {
                group.getChildren().add(drawCost(new VisualSingleCost(3, card.cogsCost()), position));
                position++;
            }
        return group;
    }
    @Override
    public void actualize() {
    }

}
