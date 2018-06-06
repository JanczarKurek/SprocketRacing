package MapServer;

import ErrorsAndExceptions.NoSuchPlayer;

import java.util.TreeMap;

public interface AbstractBoard {
    PawnController getController(Integer playerId);

    TreeMap<Integer, Integer> getPlayersPositions();
}
