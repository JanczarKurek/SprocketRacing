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
import java.util.TreeMap;

/** Represents game table - board with decks **/

public class Table {

    @FunctionalInterface
    interface PhasePrep{
        void run();
    }

    enum Phase{
        DRAW,
        VENT,
        RACE,
        DAMAGE
    }

    static TreeMap<Phase, Phase> phaseOrder = new TreeMap<>();
    static {
        phaseOrder.put(Phase.DRAW, Phase.VENT);
        phaseOrder.put(Phase.VENT, Phase.RACE);
        phaseOrder.put(Phase.RACE, Phase.DAMAGE);
        phaseOrder.put(Phase.DAMAGE, Phase.DRAW);
    }

    private void checkPhase(Phase expecting, String dsc){
        if(expecting != actualPhase)
            throw new WrongPhaseException(dsc + ": not in appropriate phase, should be " + expecting + " got " + actualPhase);
    }

    public static class WrongPhaseException extends RuntimeException{
        WrongPhaseException(){}
        WrongPhaseException(String msg){
            super(msg);
        }
    }

    private Phase actualPhase;
    private AbstractBoard board;
    private ArrayList<Deck> decks;
    private ArrayList<Deck> discards = new ArrayList<>();
    private ArrayList<Player> players = new ArrayList<>();
    private ArrayList<TableControllerImpl> controllers = new ArrayList<>();

    private int actualPhaseVotes = 0;

    public Table(AbstractBoard board, Collection<Deck> decks){
        this.board = board;
        this.decks = new ArrayList<>(decks);
        for(Deck d : decks){
            discards.add(new Deck(-1));
        }
    }

    class TableControllerImpl implements TableController{
        Player player;
        boolean gotHand = false;
        boolean voted = false;
        TreeMap<Phase, PhasePrep> phasePreps = new TreeMap<>();
        {
            phasePreps.put(Phase.DRAW, this::prepareDraw);
            phasePreps.put(Phase.VENT, this::prepareVent);
            phasePreps.put(Phase.RACE, this::prepareRace);
            phasePreps.put(Phase.DAMAGE, this::prepareDamage);
        }
        TableControllerImpl(Player player) {
            this.player = player;
        }
        private void prepareDraw(){
            gotHand = false;
            voted = false;
        }

        private void prepareVent(){
            voted = false;
        }

        private void prepareRace(){
            voted = false;
        }

        private void prepareDamage(){
            voted = false;
        }

        //During any phase
        public void voteEndPhase() throws WrongMove {
            if(voted){
                throw new WrongMove("Player " + player.getId() + " already voted end phase, could not vote again");
            }
            Table.this.voteEndPhase();
        }

        void prepNextPhase(){
            phasePreps.get(actualPhase).run();
        }

        //Actions during DRAW
        public Hand getHand() throws WrongMove {
            checkPhase(Phase.DRAW, "getHand");
            if(voted)
                throw new WrongMove("Player " + player.getId() + " already voted end phase, no actions allowed");
            if(gotHand)
                throw new WrongMove("Hand already taken by player " + player.getId());
            gotHand = true;
            return Table.this.getHand();
        }


    }

    public TableController sitDown(Player player){
        if(players.contains(player))
            throw new IllegalStateException("Player already registered!");
        players.add(player);
        TableControllerImpl ret = new TableControllerImpl(player);
        controllers.add(ret);
        return ret;
    }

    private Hand getHand(){
            ArrayList<Card> cards = new ArrayList<>();
            for(Deck deck : decks){
                try {
                    cards.add(deck.take());
                } catch (EmptyDeck emptyDeck) {
                    //todo add reshuffling.
                }
            }
            return new Hand(cards);
    }

    private void voteEndPhase(){
        actualPhaseVotes++;
        if(actualPhaseVotes == players.size()){
            actualPhaseVotes = 0;
            for(TableControllerImpl controller : controllers)
                controller.prepNextPhase();
            actualPhase = phaseOrder.get(actualPhase);
        }
    }
}
