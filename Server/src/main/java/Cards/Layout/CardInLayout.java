package Cards.Layout;

import javafx.util.*;
import Cards.*;

public class CardInLayout {
    private Pair<Integer, Integer> coordinates;
    private VehicleCardData myCard;

    public CardInLayout() {}

    public CardInLayout (VehicleCardData card, Integer x, Integer y) {
        coordinates = new Pair<>(x, y);
        myCard = card;
    }

    public void setCoordinates(Integer x, Integer y) {
        coordinates = new Pair<>(x, y);
    }

    public void setCard(VehicleCardData card) {
        myCard = card;
    }

    public Pair<Integer, Integer> getCoordinates() {
        return coordinates;
        //return new Pair<>(coordinates.getKey(), coordinates.getValue());
    }

    public VehicleCardData getCard() {
        return myCard;
    }
}
