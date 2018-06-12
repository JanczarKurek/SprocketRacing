package VisualCards;

import Cards.Card;
import Cards.Layout.CardInLayout;
import Cards.Layout.CardsLayout;
import Cards.LoadedCard;
import Cards.VehicleCardData;
import VisualBoard.VisualElement;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.util.Pair;

import java.util.LinkedList;
import java.util.Set;

public class VisualCardInLayout implements VisualElement{
    private CardInLayout card;
    public VisualCardInLayout(CardInLayout card){
        this.card = card;
    }
    public Pair<Integer, Integer> getCoordinates() {
        return card.getCoordinates();
    }
    public Node draw(){
        Node node = (new LoadedCard((VehicleCardData)card.getCard()).getVisualCard()).draw();
        node.setTranslateX(50+getCoordinates().getKey()*(350/2));
        node.setTranslateY((double)(50 + getCoordinates().getValue() )* (223.5));
        node.setScaleY(0.5);
        node.setScaleX(0.5);
        return node;
    }
    public void actualize(){

    }
}

