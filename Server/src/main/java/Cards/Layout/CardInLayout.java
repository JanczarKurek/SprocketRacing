package Cards.Layout;

import javafx.util.*;
import Cards.*;
import java.util.*;

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

    public HashSet<Pair<Integer, Integer>> getPossibleNeighbors() {
        HashSet<Pair<Integer, Integer>> result = new HashSet<>();
        if (myCard.getJoints().isLeft())
            result.add(new Pair<>(coordinates.getKey() - 1, coordinates.getValue()));
        if (myCard.getJoints().isRight())
            result.add(new Pair<>(coordinates.getKey() + 1, coordinates.getValue()));
        if (myCard.getJoints().isUp())
            result.add(new Pair<>(coordinates.getKey(), coordinates.getValue() + 1));
        if (myCard.getJoints().isDown())
            result.add(new Pair<>(coordinates.getKey(), coordinates.getValue() - 1));
        return result;
    }
}
