package MapServer;

public class LoadedBoardStructure {
    MapServer.BoardStructure boardStructure;
    String pathToImage;

    public LoadedBoardStructure(BoardStructure boardStructure, String pathToImage) {
        this.pathToImage = pathToImage;
        this.boardStructure = boardStructure;
    }

    public String getPathToImage() {
        return pathToImage;
    }

    public MapServer.BoardStructure getBoardStructure() {
        return boardStructure;
    }

    public void setPathToImage(String pathToImage) {
        this.pathToImage = pathToImage;
    }

    public void setBoardStructure(MapServer.BoardStructure boardStructure) {
        this.boardStructure = boardStructure;
    }
}
