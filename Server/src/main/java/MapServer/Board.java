package MapServer;

import ErrorsAndExceptions.NoSuchPlayer;
import ErrorsAndExceptions.WrongMove;
import misc.Effect;

import java.util.Collection;
import java.util.TreeMap;

public class Board implements AbstractBoard {
    private BoardStructure boardStructure;
    private BoardState boardState;

    public Board(BoardStructure boardStructure, Collection<Integer> players, int start){
        this.boardStructure = boardStructure;
        this.boardState = new BoardState(players, start);
    }

    private Collection<Effect> movePlayer(Integer player, Path path) throws WrongMove{
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
        public Collection<Effect> move(Path path) throws WrongMove {
            return movePlayer(playerId, path);
        }

        @Override
        public Collection<OnStayEffect> useEffectsOnField(){
            return useEffect(playerId);
        }
    }

    @Override
    public PawnController getController(Integer playerId){
        if(!boardState.getPlayers().contains(playerId)){
            boardState.putPlayer(playerId);
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
