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
    }

    public VehicleCardData getCard() {
        return myCard;
    }

    @Override
    public int hashCode() {
        return coordinates.hashCode() + myCard.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof CardInLayout && coordinates.equals(((CardInLayout) o).getCoordinates()) &&
                myCard.equals(((CardInLayout) o).getCard()))
            return true;
        return false;
    }
}
