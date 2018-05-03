package misc;

public class Cost {
    Cost(){};
    Cost(int r, int y, int b){
        red = r;
        yellow = y;
        blue = b;
    }
    private int red;
    private int yellow;
    private int blue;

    public int getRed() {
        return red;
    }

    public int getYellow() {
        return yellow;
    }

    public int getBlue() {
        return blue;
    }
}
