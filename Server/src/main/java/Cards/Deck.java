package Cards;

import ErrorsAndExceptions.EmptyDeck;

import java.util.ArrayList;
import java.util.Collections;

public class Deck{
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
}
