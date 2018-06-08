package Table;

import Cards.Card;
import Cards.Deck;
import Cards.Hand;
import ErrorsAndExceptions.EmptyDeck;
import ErrorsAndExceptions.WrongMove;
import MapServer.AbstractBoard;
import MapServer.PawnController;
import Players.Player;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.TreeMap;

/** Represents game table - board with decks and players sitting **/

public class Table {

    @FunctionalInterface
    interface PhasePrep{
        void run();
    }

    private static TreeMap<Phase, Phase> phaseOrder = new TreeMap<>();
    static {
        phaseOrder.put(Phase.DRAW, Phase.VENT);
        phaseOrder.put(Phase.VENT, Phase.RACE);
        phaseOrder.put(Phase.RACE, Phase.DAMAGE);
        phaseOrder.put(Phase.DAMAGE, Phase.DRAW);
    }



    private void checkPhase(Phase expecting, String dsc){
        if(expecting != currentPhase)
            throw new WrongPhaseException(dsc + ": not in appropriate phase, should be " + expecting + " got " + currentPhase);
    }

    public static class WrongPhaseException extends RuntimeException{
        WrongPhaseException(){}
        WrongPhaseException(String msg){
            super(msg);
        }
    }

    public Phase getCurrentPhase() {
        return currentPhase;
    }

    private Phase currentPhase;
    private AbstractBoard board;
    private ArrayList<Deck> decks;
    private ArrayList<Deck> discards = new ArrayList<>();
    private ArrayList<Player> players = new ArrayList<>();
    private HashMap<Player, Boolean> hasNextHand = new HashMap<>();
    private ArrayList<TableControllerImpl> controllers = new ArrayList<>();
    private HashMap<Player, Hand> passedHands = new HashMap<>();

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
        boolean firstHand = true;
        boolean passedLast = false;
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
            firstHand = true;
            gotHand = false;
            voted = false;
            passedLast = false;
            passedHands = new HashMap<>();
            passedHands.put(player, null);
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
            phasePreps.get(currentPhase).run();
        }

        //Actions during DRAW
        public Hand getHand() throws WrongMove {
            checkPhase(Phase.DRAW, "getHand");
            if(voted)
                throw new WrongMove("Player " + player.getId() + " already voted end phase, no actions allowed");
            if(gotHand)
                throw new WrongMove("Hand already taken by player " + player.getId());
            if(!firstHand) {
                int pos = players.indexOf(player);
                pos = (pos - 1 + players.size()) % players.size();
                Player myPred = players.get(pos);
                if(passedHands.get(myPred) == null){
                    throw new WrongMove("No hand to take, prev players should pass hand first");
                }
                gotHand = true;
                return passedHands.remove(myPred);
            }
            gotHand = true;
            firstHand = false;
            return Table.this.getHand();
        }

        public Boolean hasNextHand(){
            return !passedLast;
        }

        public void passHand(Hand playersHand) throws WrongMove {
            if(playersHand == null)
                throw new NullPointerException();
            checkPhase(Phase.DRAW, "passHand");
            if(passedHands.get(player) != null){
                throw new WrongMove("Hand already passed in this phase");
            }
            if(playersHand.getHandSize() == 1){
                passedLast = true;
            }
            gotHand = false;
            passedHands.put(player, playersHand);
        }
    }

    public Pair<TableController, PawnController> sitDown(Player player){
        if(players.contains(player))
            throw new IllegalStateException("Player " + player.getId() + " already registered!");
        players.add(player);
        TableControllerImpl ret = new TableControllerImpl(player);
        controllers.add(ret);
        return new Pair<>(ret, board.getController(player.getId()));
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
            currentPhase = phaseOrder.get(currentPhase);
            for(Player player : players)
                player.nextPhase(currentPhase);
        }
    }
}
