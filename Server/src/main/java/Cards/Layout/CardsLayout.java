package Cards.Layout;

import java.util.*;
import Cards.*;
import javafx.util.*;
import ErrorsAndExceptions.*;


public class CardsLayout {

    private HashMap<Pair<Integer, Integer>, CardInLayout> myMap = new HashMap<>();
    private HashSet<CardInLayout> train = new java.util.HashSet<>();
    private CardInLayout cockpit;

    public void BakuretsuBakuretsuBakuretsuNaNa(){
        train.removeIf(x -> !cockpit.equals(x));
        myMap.entrySet().removeIf(x -> x.getValue().equals(cockpit));
    }

    public CardsLayout() {
        cockpit = null;
    }

    public CardsLayout(CardInLayout cockpit, Collection<CardInLayout> train) {
        this.cockpit = cockpit;
        this.train = new HashSet<>(train);

        myMap.put(cockpit.getCoordinates(), cockpit);
        for (CardInLayout cardInLayout : train)
            myMap.put(cardInLayout.getCoordinates(), cardInLayout);
    }

    public void setCockpit(CardInLayout cockpit) throws IllegalCardsLayoutException {
        if (train.contains(cockpit))
            train.remove(cockpit);
        if (this.cockpit != null)
            myMap.remove(this.cockpit.getCoordinates());
        this.cockpit = cockpit;
        myMap.put(cockpit.getCoordinates(), cockpit);
    }

    public void setCockpit(VehicleCardData card, Integer x, Integer y)
            throws IllegalCardsLayoutException {
        setCockpit(new CardInLayout(card, x, y));
    }

    public void add(CardInLayout card) throws IllegalCardsLayoutException {
        if (myMap.containsKey(card.getCoordinates()))
            throw new IllegalCardsLayoutException();
        train.add(card);
        myMap.put(card.getCoordinates(), card);
    }

    public void add(VehicleCardData card, Integer x, Integer y) throws IllegalCardsLayoutException {
        add(new CardInLayout(card, x, y));
    }

    public void add(VehicleCardData card, Pair<Integer, Integer> coordinates)
            throws IllegalCardsLayoutException {
        add(card, coordinates.getKey(), coordinates.getValue());
    }

    public void remove(VehicleCardData card, Integer x, Integer y) {
        train.remove(new CardInLayout(card, x, y));
        myMap.remove(new Pair<>(x, y));
    }

    public void remove(CardInLayout card) {
        train.remove(card);
        myMap.remove(card.getCoordinates());
    }

    public VehicleCardData remove(int x, int y){
        if(!myMap.containsKey(new Pair<>(x, y))){
            return null;
        }else{
            if(myMap.get(new Pair<>(x, y)).equals(cockpit)){
                return null;
            }
            CardInLayout card = myMap.get(new Pair<>(x, y));
            myMap.remove(new Pair<>(x, y));
            return card.getCard();
        }
    }

    public VehicleCardData getCardByCoordinates(int x, int y){
        if(!myMap.containsKey(new Pair<>(x, y))){
            return null;
        }else{
            CardInLayout card = myMap.get(new Pair<>(x, y));
            return card.getCard();
        }
    }

    public CardInLayout getCockpit() {
        return cockpit;
    }

    public LinkedList<CardInLayout> getTrain() {
        return new java.util.LinkedList<>(train);
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

        return !visited.containsValue(false);
    }

    private void dfs(CardInLayout currentCard, Map<CardInLayout, Boolean> visited,
                     Map<Pair<Integer, Integer>, CardInLayout> cards) {
        if (!visited.containsKey(currentCard) || visited.get(currentCard))
            return;
        visited.put(currentCard, true);

        if (currentCard.getCard().getJoints().isUp()) {
            Pair<Integer, Integer> pair = new Pair<>(currentCard.getCoordinates().getKey(),
                    currentCard.getCoordinates().getValue() + 1);

            if (cards.containsKey(pair) && cards.get(pair).getCard().getJoints().isDown())
                dfs(cards.get(pair), visited, cards);
        }

        if (currentCard.getCard().getJoints().isDown()) {
            Pair<Integer, Integer> pair = new Pair<>(currentCard.getCoordinates().getKey(),
                    currentCard.getCoordinates().getValue() - 1);

            if (cards.containsKey(pair) && cards.get(pair).getCard().getJoints().isUp())
                dfs(cards.get(pair), visited, cards);
        }

        if (currentCard.getCard().getJoints().isLeft()) {
            Pair<Integer, Integer> pair = new Pair<>(currentCard.getCoordinates().getKey() - 1,
                    currentCard.getCoordinates().getValue());

            if (cards.containsKey(pair) && cards.get(pair).getCard().getJoints().isRight())
                dfs(cards.get(pair), visited, cards);
        }

        if (currentCard.getCard().getJoints().isRight()) {
            Pair<Integer, Integer> pair = new Pair<>(currentCard.getCoordinates().getKey() + 1,
                    currentCard.getCoordinates().getValue());

            if (cards.containsKey(pair) && cards.get(pair).getCard().getJoints().isLeft())
                dfs(cards.get(pair), visited, cards);
        }

    }

    public HashMap<Pair<Integer, Integer>, VehicleCardData> getLayout()
            throws IllegalCardsLayoutException {
        if (!checkCorrectness())
            throw new ErrorsAndExceptions.IllegalCardsLayoutException();
        HashMap<Pair<Integer, Integer>, VehicleCardData> map = new HashMap<>();
        for (CardInLayout cardInLayout : train)
            map.put(cardInLayout.getCoordinates(), cardInLayout.getCard());
        map.put(cockpit.getCoordinates(), cockpit.getCard());
        return map;
    }

    public HashMap<Pair<Integer, Integer>, VehicleCardData> getLayout(Pair<Integer, Integer> referencePoint)
        throws IllegalCardsLayoutException {
        HashMap<Pair<Integer, Integer>, VehicleCardData> orginal = getLayout();
        HashMap<Pair<Integer, Integer>, VehicleCardData> result = new HashMap<>();

        for (Pair<Integer, Integer> pair : orginal.keySet())
            result.put(new Pair<>(pair.getKey() - referencePoint.getKey(),
                    pair.getValue() - referencePoint.getValue()), orginal.get(pair));

        return result;
    }

    public HashMap<Pair<Integer, Integer>, VehicleCardData> getLayout(Integer xOfReferencePoint,
                                                                      Integer yOfReferencePoint)
        throws IllegalCardsLayoutException {
        return getLayout(new Pair<>(xOfReferencePoint, yOfReferencePoint));
    }

    public HashSet<Pair<Integer, Integer>> possiblePositions(VehicleCardData adding)
            throws IllegalCardsLayoutException {
        if (!checkCorrectness())
            throw new IllegalCardsLayoutException();

        HashSet<Pair<Integer, Integer>> usedPositions = new HashSet<>();
        HashSet<Pair<Integer, Integer>> result = new HashSet<>();

        for (CardInLayout cardInLayout : train)
            usedPositions.add(cardInLayout.getCoordinates());
        usedPositions.add(cockpit.getCoordinates());

        for (CardInLayout cardInLayout : train)
            result.addAll(cardInLayout.getPossibleNeighbors());
        result.addAll(cockpit.getPossibleNeighbors());

        result.removeAll(usedPositions);

        return result;
    }

    public HashSet<Pair<Integer, Integer>> possiblePositions(VehicleCardData adding,
                                                             Pair<Integer, Integer> referencePoint)
        throws IllegalCardsLayoutException {
        HashSet<Pair<Integer, Integer>> orginal = possiblePositions(adding);
        HashSet<Pair<Integer, Integer>> result = new HashSet<>();

        for(Pair<Integer, Integer> pair : orginal)
            result.add(new Pair<>(pair.getKey() - referencePoint.getKey(),
                    pair.getValue() - referencePoint.getValue()));

        return result;
    }

    public HashSet<Pair<Integer, Integer>> possiblePositions(VehicleCardData adding,
                                                             Integer xOfReferencePoint,
                                                             Integer yOfReferencePoint)
        throws IllegalCardsLayoutException {
        return possiblePositions(adding, new Pair<>(xOfReferencePoint, yOfReferencePoint));
    }


}
