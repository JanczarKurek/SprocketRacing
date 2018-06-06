package MapServer;

import java.util.Collection;
import java.util.LinkedList;
import java.util.TreeMap;

public class BoardState {
    private TreeMap<Integer, Integer> playersOnBoard = new TreeMap<>(); //Maps players to fields (by ID)
    private LinkedList<GlobalEffect> globalEffects = new LinkedList<>();

    private int start;

    BoardState(Collection<Integer> players, int start){
        this.start = start;
        for(Integer player : players){
            playersOnBoard.put(player, start);
        }
    }

    public void putPlayer(Integer playerId){
        if(playersOnBoard.containsKey(playerId))
            throw new RuntimeException("Add every player only once");
        playersOnBoard.put(playerId, start);
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

    public Collection<Integer> getPlayers(){
        return playersOnBoard.keySet();
    }

    public void setPlayerPosition(int id, int newPosition){
        playersOnBoard.remove(id);
        playersOnBoard.put(id, newPosition);
    }
}
