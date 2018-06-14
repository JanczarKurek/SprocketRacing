package Cards.Layout;

import java.util.*;
import Cards.*;
import javafx.util.*;
import ErrorsAndExceptions.*;


public class CardsLayout {


    private java.util.HashMap<Pair<Integer, Integer>, Cards.Layout.CardInLayout> map = new java.util.HashMap<>();
    private Cards.Layout.CardInLayout cockpit;



    public void BakuretsuBakuretsuBakuretsuNaNa() {
        map.entrySet().removeIf(c -> c.getKey().equals(cockpit.getCoordinates()));
    }

    public boolean justCockpit() {
        return map.size() == 1;
    }

    public CardsLayout () {

    }

    public CardsLayout (Cards.Layout.CardInLayout cockpit, Collection<CardInLayout> cards) {
        this.cockpit = cockpit;
        for (CardInLayout c : cards)
            map.put(c.getCoordinates(), c);
        map.put(cockpit.getCoordinates(), cockpit);
    }

    public void setCockpit(Cards.Layout.CardInLayout cockpit) throws ErrorsAndExceptions.IllegalCardsLayoutException {
        if (this.cockpit != null)
            map.remove(this.cockpit.getCoordinates());
        this.cockpit = cockpit;
        map.put(cockpit.getCoordinates(), cockpit);
    }

    public void setCockpit(Cards.VehicleCardData card, Integer x, Integer y)
            throws ErrorsAndExceptions.IllegalCardsLayoutException {
        setCockpit(new CardInLayout(card, x, y));
    }

    public void add(Cards.Layout.CardInLayout card) throws ErrorsAndExceptions.IllegalCardsLayoutException {
        if (map.containsKey(card.getCoordinates()))
            throw new ErrorsAndExceptions.IllegalCardsLayoutException();
        map.put(card.getCoordinates(), card);
    }

    public void add(Cards.VehicleCardData card, Integer x, Integer y) throws ErrorsAndExceptions.IllegalCardsLayoutException {
        add(new CardInLayout(card, x, y));
    }

    public void add(Cards.VehicleCardData card, Pair<Integer, Integer> coordinates)
            throws ErrorsAndExceptions.IllegalCardsLayoutException {
        add(card, coordinates.getKey(), coordinates.getValue());
    }

    public void remove(Cards.VehicleCardData card, Integer x, Integer y ) {
        map.remove(new Pair<>(x, y));
    }

    public void remove(Cards.Layout.CardInLayout card) {
        map.remove(card.getCoordinates(), card);
    }

    public Cards.VehicleCardData remove(Integer x, Integer y) {
        CardInLayout card = map.get(new Pair<>(x, y));
        if (card == null)
            return null;
        if (cockpit.getCoordinates().equals(new Pair<>(x, y)))
            return null;

        map.remove(new Pair<>(x, y));
        return card.getCard();
    }

    public Cards.VehicleCardData getCardByCoordinates(int x, int y) {
        if (!map.containsKey(new Pair<>(x, y)))
            return null;
        return map.get(new Pair<>(x, y)).getCard();
    }

    public CardInLayout getCockpit() {
        return cockpit;
    }

    public java.util.LinkedList<Cards.Layout.CardInLayout> getTrain() {
        LinkedList<Cards.Layout.CardInLayout> l = new java.util.LinkedList<>();
        l.addAll(map.values());
        l.removeFirstOccurrence(cockpit);
        return l;
    }

    public void xd(Cards.Layout.CardInLayout card, HashSet<Pair<Integer, Integer>> set) {
        if (card == null || set.contains(card.getCoordinates()))
            return;

        set.add(card.getCoordinates());

        Pair<Integer, Integer> a = new javafx.util.Pair<>(card.getCoordinates().getKey() + 1,
                card.getCoordinates().getValue());
        Pair<Integer, Integer> b = new javafx.util.Pair<>(card.getCoordinates().getKey() - 1,
                card.getCoordinates().getValue());
        Pair<Integer, Integer> c = new javafx.util.Pair<>(card.getCoordinates().getKey(),
                card.getCoordinates().getValue() + 1);
        Pair<Integer, Integer> d = new javafx.util.Pair<>(card.getCoordinates().getKey(),
                card.getCoordinates().getValue() - 1);

        if (card.getCard().getJoints().isRight() && map.get(a) != null && map.get(a).getCard().getJoints().isLeft())
            xd(map.get(a), set);
        if (card.getCard().getJoints().isLeft() && map.get(b) != null && map.get(b).getCard().getJoints().isRight())
            xd(map.get(b), set);
        if (card.getCard().getJoints().isUp() && map.get(c) != null && map.get(c).getCard().getJoints().isDown())
            xd(map.get(c), set);
        if (card.getCard().getJoints().isDown() && map.get(d) != null && map.get(d).getCard().getJoints().isUp())
            xd(map.get(d), set);
    }

    public boolean checkCorrectness () {
        HashSet<Pair<Integer, Integer>> set = new HashSet<>();

        xd(cockpit, set);

        return set.containsAll(map.keySet());
    }

    public HashMap<Pair<Integer, Integer>, Cards.VehicleCardData> getLayout()
            throws ErrorsAndExceptions.IllegalCardsLayoutException {
        if (!checkCorrectness())
            throw new ErrorsAndExceptions.IllegalCardsLayoutException();
        HashMap<Pair<Integer, Integer>, Cards.VehicleCardData> r = new java.util.HashMap<>();
        for (Pair<Integer, Integer> p : map.keySet())
            r.put(p, map.get(p).getCard());
        return r;
    }


    public HashMap<Pair<Integer, Integer>, Cards.VehicleCardData> getLayout(Pair<Integer, Integer> referencePoint)
            throws ErrorsAndExceptions.IllegalCardsLayoutException {
        HashMap<Pair<Integer, Integer>, Cards.VehicleCardData> orginal = getLayout();
        HashMap<Pair<Integer, Integer>, Cards.VehicleCardData> result = new java.util.HashMap<>();

        for (Pair<Integer, Integer> pair : orginal.keySet())
            result.put(new Pair<>(pair.getKey() - referencePoint.getKey(),
                    pair.getValue() - referencePoint.getValue()), orginal.get(pair));

        return result;

    }

    public HashMap<Pair<Integer, Integer>, Cards.VehicleCardData> getLayout(Integer xOfReferencePoint,
                                                                            Integer yOfReferencePoint)
            throws ErrorsAndExceptions.IllegalCardsLayoutException {
        return getLayout(new Pair<>(xOfReferencePoint, yOfReferencePoint));
    }

    public HashSet<Pair<Integer, Integer>> possiblePositions(Cards.VehicleCardData card)
            throws ErrorsAndExceptions.IllegalCardsLayoutException {
        HashSet<Pair<Integer, Integer>> result = new java.util.HashSet<>();

        for (Pair<Integer, Integer> p : map.keySet()) {
            if (card.getJoints().isDown() && map.get(p).getCard().getJoints().isUp() &&
                    ! map.containsKey(new Pair<>(p.getKey(), p.getValue() + 1)))
                result.add(new Pair<>(p.getKey(), p.getValue() + 1));

            if (card.getJoints().isUp() && map.get(p).getCard().getJoints().isDown() &&
                    ! map.containsKey(new Pair<>(p.getKey(), p.getValue() - 1)))
                result.add(new Pair<>(p.getKey(), p.getValue() - 1));

            if (card.getJoints().isLeft() && map.get(p).getCard().getJoints().isRight() &&
                    ! map.containsKey(new Pair<>(p.getKey() + 1, p.getValue())))
                result.add(new Pair<>(p.getKey() + 1, p.getValue()));

            if (card.getJoints().isRight() && map.get(p).getCard().getJoints().isLeft() &&
                    ! map.containsKey(new Pair<>(p.getKey() - 1, p.getValue())))
                result.add(new Pair<>(p.getKey() - 1, p.getValue()));
        }
        return result;
    }

    public boolean jacekRatujeSwieta(){
        LinkedList<CardInLayout> stack = new LinkedList<>();
        System.err.println("We have cards on " + map.keySet());
        stack.add(cockpit);
        HashSet<CardInLayout> visited = new HashSet<>();
        visited.add(cockpit);
        while(!stack.isEmpty()){
            CardInLayout actual = stack.pop();
            VehicleCardData card = actual.getCard();
            if(card.getJoints().isDown()){
                CardInLayout nei = map.get(new Pair<>(actual.getCoordinates().getKey(), actual.getCoordinates().getValue() + 1));
                if(nei != null && nei.getCard().getJoints().isUp() && !visited.contains(nei)){
                    //System.err.println("Im in card " + actual.getCoordinates() + " adding " + nei.getCoordinates());
                    stack.add(nei);
                    visited.add(nei);
                }
            }
            if(card.getJoints().isUp()){
                CardInLayout nei = map.get(new Pair<>(actual.getCoordinates().getKey(), actual.getCoordinates().getValue() - 1));
                if(nei != null && nei.getCard().getJoints().isDown() && !visited.contains(nei)){
                    stack.add(nei);
                    visited.add(nei);
                }
            }
            if(card.getJoints().isLeft()){
                CardInLayout nei = map.get(new Pair<>(actual.getCoordinates().getKey() - 1, actual.getCoordinates().getValue()));
                if(nei != null && nei.getCard().getJoints().isRight() && !visited.contains(nei)){
                    stack.add(nei);
                    visited.add(nei);
                }
            }
            if(card.getJoints().isRight()){
                CardInLayout nei = map.get(new Pair<>(actual.getCoordinates().getKey() + 1, actual.getCoordinates().getValue()));
                if(nei != null && nei.getCard().getJoints().isLeft() && !visited.contains(nei)){
                    stack.add(nei);
                    visited.add(nei);
                }
            }
        }
        for(CardInLayout card : map.values()){
            if(!visited.contains(card))
                return false;
        }
        return true;
    }
}
