package VisualCards;

import Cards.Layout.CardInLayout;
import Cards.Layout.CardsLayout;
import VisualBoard.VisualElement;
import javafx.scene.Group;
import javafx.scene.Node;

import java.util.LinkedList;

public class VisualVehicle implements VisualElement {
    private CardsLayout layout;
    private LinkedList<CardInLayout> vehicle;
    public VisualVehicle(CardsLayout layout){
        this.layout = layout;
         vehicle = layout.getTrain();
    }
    public Node draw(){
        Group group = new Group();
        for(CardInLayout card : vehicle){
            try {
                VisualCard visualCard = new VisualCard(card.getCard());
                Node node = visualCard.draw();
                node.setTranslateY(50+card.getCoordinates().getValue()*223);
                node.setTranslateX(50+card.getCoordinates().getKey()*350);
                VisualVehicleCardController controller = new VisualVehicleCardController(visualCard, node);
                group.getChildren().add(node);
                group.getChildren().add(controller.draw());
            }catch (Exception e){
                System.err.println("File not found!");
            }
        }
        return group;
    }
    public void actualize(){
        vehicle.clear();
        vehicle = layout.getTrain();
    }
}
