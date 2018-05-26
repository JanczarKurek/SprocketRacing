package Cards;

import ErrorsAndExceptions.EmptyDeck;

import java.util.ArrayList;
import java.util.Collections;

public class Deck<T extends Card> {
    private int id;

    private String description = "";

    private ArrayList<T> cards = new ArrayList<>();

    public Deck(int id){
        this.id = id;
    }

    public Deck(int id, String description) {
        this.id = id;
        this.description = description;
    }

    void put(T card){
        cards.add(card);
    }

    void shuffle(){
        Collections.shuffle(cards);
    }

    int size(){
        return cards.size();
    }

    T take() throws EmptyDeck {
        if(cards.size() == 0)
            throw new EmptyDeck();
        return cards.remove(cards.size() - 1);
    }

    public String getDescription() {
        return description;
    }
}
