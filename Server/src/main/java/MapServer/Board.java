package MapServer;

import ErrorsAndExceptions.NoSuchPlayer;
import ErrorsAndExceptions.WrongMove;

import java.util.Collection;
import java.util.TreeMap;

public class Board implements AbstractBoard {
    private BoardStructure boardStructure;
    private BoardState boardState;

    public Board(BoardStructure boardStructure, Collection<Integer> players, int start){
        this.boardStructure = boardStructure;
        this.boardState = new BoardState(players, start);
    }

    private Collection<OnPassEffect> movePlayer(Integer player, Path path) throws WrongMove{
        if(!path.start().equals(boardState.getPlayerPosition(player)))
            throw new WrongMove("Logical error, path start does not match player's position.");
        if(!boardStructure.checkPath(path))
            throw new WrongMove("Invalid path.");
        //todo how to get to account global effects?
        boardState.setPlayerPosition(player, path.end());
        return boardStructure.effectsOnPath(path);
    }

    private Collection<OnStayEffect> useEffect(Integer player){
        int localization = boardState.getPlayerPosition(player);
        return boardStructure.getOnStayEffectsFromField(localization);
    }

    public class PawnControllerImpl implements PawnController{
        private int playerId;

        PawnControllerImpl(int playerId) {
            this.playerId = playerId;
        }

        @Override
        public Collection<OnPassEffect> move(Path path) throws WrongMove {
            return movePlayer(playerId, path);
        }

        @Override
        public Collection<OnStayEffect> useEffectsOnField(){
            return useEffect(playerId);
        }
    }

    @Override
    public PawnController getController(Integer playerId) throws NoSuchPlayer {
        if(!boardState.getPlayers().contains(playerId)){
            throw new NoSuchPlayer();
        }
        return new PawnControllerImpl(playerId);
    }

    @Override
    public TreeMap<Integer, Integer> getPlayersPositions() {
        return boardState.getPlayersPositions();
    }


    public int getNumberOfPlayers(){
        return boardState.getNumberOfPlayers();
    }

    public Integer getPlayerPosition(Integer playerId){
        return boardState.getPlayerPosition(playerId);
    }

}
