package Cards.Layout;

import java.util.*;
import Cards.*;
import javafx.util.*;

public class CardsLayout {
    LinkedList<CardInLayout> train;
    CardInLayout cockpit;

    public CardsLayout(CardInLayout cockpit, Collection<CardInLayout> train) {
        this.cockpit = cockpit;
        this.train = new LinkedList<>(train);
    }

    public void setCockpit(CardInLayout cockpit) {
        this.cockpit = cockpit;
    }

    public void setCockpit(VehicleCardData card, Integer x, Integer y) {
        cockpit = new CardInLayout(card, x, y);
    }

    public void addComparment(CardInLayout card) {
        train.add(card);
    }

    public void addComparament(VehicleCardData card, Integer x, Integer y) {
        train.add(new CardInLayout(card, x, y));
    }

    public CardInLayout getCockpit() {
        return cockpit;
    }

    public LinkedList<CardInLayout> getTrain() {
        return train;
    }

    public boolean checkCorrectness() {
        /*Set<Pair<Integer, Integer>> coordinatesSet = new java.util.TreeSet<>((i, j) -> {
            if (i.getKey() < j.getKey())
                return -1;
            if (i.getKey() > j.getKey())
                return 1;
            if (i.getValue() < j.getValue())
                return -1;
            if (i.getValue() > j.getValue())
                return 1;
            return 0;
        });*/

        Set<Pair<Integer, Integer>> coordinatesSet = new HashSet<>();

        coordinatesSet.add(cockpit.getCoordinates());
        for (CardInLayout card : train) {
            if (coordinatesSet.contains(card.getCoordinates()))
                return false;
            coordinatesSet.add(card.getCoordinates());
        }

        Map<CardInLayout, Boolean> visited = new HashMap<>();
        for (CardInLayout card : train)
            visited.put(card, false);
        visited.put(cockpit, false);

        Map<Pair<Integer, Integer>, CardInLayout> cards = new HashMap<>();
        for (CardInLayout card : train)
            cards.put(card.getCoordinates(), card);
        cards.put(cockpit.getCoordinates(), cockpit);

        dfs(cockpit, visited, cards);

        return !visited.containsKey(true);
    }

    private void dfs(CardInLayout currentCard, Map<CardInLayout, Boolean> visited,
                     Map<Pair<Integer, Integer>, CardInLayout> cards) {
        if (!visited.containsKey(currentCard) || visited.get(currentCard))
            return;
        visited.put(currentCard, true);

        if (currentCard.getCard().getJoints().isUp())
            dfs(cards.get(new Pair<>(currentCard.getCoordinates().getKey(),
                    currentCard.getCoordinates().getValue() + 1)), visited, cards);

        if (currentCard.getCard().getJoints().isDown())
            dfs(cards.get(new Pair<>(currentCard.getCoordinates().getKey(),
                    currentCard.getCoordinates().getValue() - 1)), visited, cards);

        if (currentCard.getCard().getJoints().isLeft())
            dfs(cards.get(new Pair<>(currentCard.getCoordinates().getKey() - 1,
                    currentCard.getCoordinates().getValue())), visited, cards);

        if (currentCard.getCard().getJoints().isRight())
            dfs(cards.get(new Pair<>(currentCard.getCoordinates().getKey() + 1,
                    currentCard.getCoordinates().getValue())), visited, cards);
    }


}
