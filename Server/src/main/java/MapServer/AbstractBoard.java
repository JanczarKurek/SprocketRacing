package MapServer;

import ErrorsAndExceptions.NoSuchPlayer;

import java.util.TreeMap;

public interface AbstractBoard {
    PawnController getController(Integer playerId) throws NoSuchPlayer;

    TreeMap<Integer, Integer> getPlayersPositions();
}
