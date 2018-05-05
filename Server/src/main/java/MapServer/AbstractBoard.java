package MapServer;

import ErrorsAndExceptions.NoSuchPlayer;

public interface AbstractBoard {
    PawnController getController(Integer playerId) throws NoSuchPlayer;
}
