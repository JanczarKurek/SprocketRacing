package MapServer;

import java.util.Collection;
import java.util.LinkedList;
import java.util.TreeMap;

public class BoardState {
    private TreeMap<Integer, Integer> playersOnBoard = new TreeMap<>(); //Maps players to fields (by ID)
    private LinkedList<GlobalEffect> globalEffects = new LinkedList<>();

    public BoardState(Collection<Integer> players, int start){
        for(Integer player : players){
            playersOnBoard.put(player, start);
        }
    }

    public Integer getPlayerPosition(Integer playerId){
        return playersOnBoard.get(playerId);
    }
    public TreeMap<Integer, Integer> getPlayersPositions(){
        return new TreeMap<>(playersOnBoard);
    }

    public int getNumberOfPlayers(){
        return playersOnBoard.size();
    }
}
