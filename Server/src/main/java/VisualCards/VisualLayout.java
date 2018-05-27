package VisualCards;

import Cards.Card;
import Cards.Layout.CardInLayout;
import Cards.Layout.CardsLayout;
import Cards.VehicleCardData;
import javafx.util.Pair;

import java.util.LinkedList;
import java.util.Set;

public class VisualLayout {
    private LinkedList<Pair<Pair<Integer, Integer>, VisualCard>> visualCards;
    private Pair<Pair<Integer, Integer>, VisualCard> visualCockpit;
    public VisualLayout(Pair<Integer, Integer> pair, VisualCard card){
        visualCards = new LinkedList<>();
        visualCockpit = new Pair(pair, card);
    }
    public void insertCard(Pair<Integer, Integer> pair, VisualCard card){
        visualCards.add(new Pair(pair, card));
    }
    public CardsLayout getLayout(){
        CardInLayout cockpit = new CardInLayout((VehicleCardData)visualCockpit.getValue().getCard(), visualCockpit.getKey().getKey(), visualCockpit.getKey().getValue());
        LinkedList<CardInLayout> list = new LinkedList<>();
        for(Pair<Pair<Integer, Integer>, VisualCard> pair : visualCards){
            list.add(new CardInLayout((VehicleCardData)pair.getValue().getCard(), visualCockpit.getKey().getKey(), visualCockpit.getKey().getValue()));
        }
        return new CardsLayout(cockpit, list);
    }
    public Pair<Integer, Integer> getCoordinates(VisualCard card) {
        for (Pair<Pair<Integer, Integer>, VisualCard> vc : visualCards) {
            if (vc.getValue() == card)
                return vc.getKey();
        }
        return null;
    }
    public LinkedList<VisualCard> getTrain(){
        LinkedList<VisualCard> out = new LinkedList<>();
        out.add(visualCockpit.getValue());
        for(Pair<Pair<Integer, Integer>, VisualCard> vc : visualCards){
            out.add(vc.getValue());
        }
        return out;
    }
}
