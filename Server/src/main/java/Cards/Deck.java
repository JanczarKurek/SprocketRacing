package Cards;

import ErrorsAndExceptions.EmptyDeck;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

public class Deck implements Iterable<Card> {
    private int id;

    private String description = "";

    private ArrayList<Card> cards = new ArrayList<>();

    public Deck(int id){
        this.id = id;
    }

    public Deck(int id, String description) {
        this.id = id;
        this.description = description;
    }

    public Deck(int id, Deck other){
        this.id = id;
        this.description = "Copy of " + other.description;
        this.cards = new ArrayList<>(other.cards);
    }

    public boolean contains(Object o){
        return cards.contains(o);
    }

    public void put(Card card){
        cards.add(card);
    }

    public void shuffle(){
        Collections.shuffle(cards);
    }

    public int size(){
        return cards.size();
    }

    public Card take() throws EmptyDeck {
        if(cards.size() == 0)
            throw new EmptyDeck();
        return cards.remove(cards.size() - 1);
    }

    public String getDescription() {
        return description;
    }

    public int getId() {
        return id;
    }

    public void clear(){
        cards.clear();
    }

    @Override
    public Iterator<Card> iterator() {
        return cards.iterator();
    }

    public Collection<Card> getCards(){
        return cards;
    }
}
