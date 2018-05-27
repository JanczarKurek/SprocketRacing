package Cards;

import java.util.ArrayList;
import java.util.Collection;

public class Hand {
    private ArrayList<Card> cards;
    public Hand(Collection<Card> cards){
        this.cards = new ArrayList<>(cards);
    }
    public Card take(int no){
        return cards.remove(no);
    }

    public ArrayList<Card> getCards(){
        return new ArrayList<>(cards);
    }

    public int getHandSize(){
        return cards.size();
    }

    public void putCard(Card card){
        cards.add(card);
    }
}
