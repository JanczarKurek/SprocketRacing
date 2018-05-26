package Table;

import Cards.Card;
import Cards.Deck;
import Cards.Hand;
import ErrorsAndExceptions.EmptyDeck;
import ErrorsAndExceptions.WrongMove;
import MapServer.AbstractBoard;
import Players.Player;

import java.util.ArrayList;
import java.util.Collection;

/** Represents game table - board with decks **/

public class Table {
    enum Phase{
        DRAW,
        VENT,
        RACE,
        DAMAGE
    }

    public static class WrongPhaseException extends RuntimeException{
        WrongPhaseException(){};
        WrongPhaseException(String msg){
            super(msg);
        }
    }

    private Phase actualPhase;
    private AbstractBoard board;
    private ArrayList<Deck> decks;
    private ArrayList<Deck> discards = new ArrayList<>();
    private ArrayList<Player> players = new ArrayList<>();

    public Table(AbstractBoard board, Collection<Deck> decks){
        this.board = board;
        this.decks = new ArrayList<>(decks);
        for(Deck d : decks){
            discards.add(new Deck(-1));
        }
    }

    class TableControllerImpl implements TableController{
        Player player;
        public TableControllerImpl(Player player) {
            this.player = player;
        }
    }

    public TableController sitDown(Player player){
        if(players.contains(player))
            throw new IllegalStateException("Player already registered!");
        players.add(player);
        return new TableControllerImpl(player);
    }

    private Hand getHand(){
        if(actualPhase == Phase.DRAW){
            ArrayList<Card> cards = new ArrayList<>();
            for(Deck deck : decks){
                try {
                    cards.add(deck.take());
                } catch (EmptyDeck emptyDeck) {
                    //todo add reshuffling.
                }
            }
            return new Hand(cards);
        }else{
            throw new WrongPhaseException("Get hand not in appropriate phase, should be " + Phase.DRAW + " got " + actualPhase);
        }
    }


}
