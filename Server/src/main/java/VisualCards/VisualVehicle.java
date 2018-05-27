package VisualCards;

import VisualBoard.VisualElement;
import javafx.scene.Group;
import javafx.scene.Node;

import java.util.LinkedList;

public class VisualVehicle implements VisualElement {
    private VisualLayout layout;
    private LinkedList<VisualCard> vehicle;
    public VisualVehicle(VisualLayout layout){
        this.layout = layout;
        vehicle = layout.getTrain();
    }
    public Node draw(){
        Group group = new Group();
        for(VisualCard card : vehicle){
            try {
                Node node = card.draw();
                node.setTranslateY(50+layout.getCoordinates(card).getValue()*223);
                node.setTranslateX(50+layout.getCoordinates(card).getKey()*350);
                VisualVehicleCardController controller = new VisualVehicleCardController(card, node);
                group.getChildren().add(node);
                group.getChildren().add(controller.draw());
            }catch (Exception e){
                System.err.println("Controller error "+ e.getClass().getName());
            }
        }
        return group;
    }
    public void actualize(){
    }
}
