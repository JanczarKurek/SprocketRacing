package MapServer;

import ErrorsAndExceptions.WrongMove;

import java.util.Collection;

public class Board {
    private BoardStructure boardStructure;
    private BoardState boardState;

    public Board(BoardStructure boardStructure, Collection<Integer> players, int start){
        this.boardStructure = boardStructure;
        this.boardState = new BoardState(players, start);
    }

    public Collection<OnPassEffect> movePlayer(Integer player, Path path) throws WrongMove{
        if(!path.start().equals(boardState.getPlayerPosition(player)))
            throw new WrongMove("Logical error, path start does not match player's position.");
        if(!boardStructure.checkPath(path))
            throw new WrongMove("Invalid path.");
        //todo how to get to account global effects?
        return boardStructure.effectsOnPath(path);
    }
}
