package VisualCards;

import MapServer.Board;
import VisualBoard.VisualElement;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;

import java.util.TreeMap;

public class VisualHand implements VisualElement {

    /*private Image handSprite;

    public void setHandSprite(Image handSprite) {
        this.handSprite = handSprite;
    }*/
    private TreeMap<Integer, VisualCard> CardsOnHand;

    public VisualHand(){
        CardsOnHand = new TreeMap<>();
    }

    public void insertCard(Integer number, VisualCard card){
        CardsOnHand.put(number, card);
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

            i++;
        }
        return group;
    }

    @Override
    public void actualize() {
    }
}
