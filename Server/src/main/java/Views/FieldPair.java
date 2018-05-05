package Views;

public class FieldPair {
    private int X;
    private int Y;
    private int numberOfPlayers;

    FieldPair(int x, int y){
        X=x;
        Y=y;
        numberOfPlayers=0;
    }

    public int getX(){
        return X;
    }

    public int getY() {
        return Y;
    }

    public int getPlayers(){
        return numberOfPlayers;
    }
    public void addPlayer(){
        numberOfPlayers++;
    }
}
