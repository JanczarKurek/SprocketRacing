package InGameResources.Dice;

import InGameResources.InGameResource;

import java.util.Random;

public class Dice implements InGameResource{
    private int pips;
    public enum Color{
        RED,
        YELLOW,
        BLUE,
        ANY
    }
    private Color color;
    private static Random generator = new Random();
    public int roll(){
        pips = generator.nextInt(6) + 1;
        return pips;
    }

    public void increase(){
        if(pips < 6 && pips != 0)
            pips++;
    }

    public int getValue(){
        return pips;
    }

    public Color getColor() {return color;}

    public Dice(Dice.Color color){
        this.color = color;
    }

    int setValue(int value){
        pips = value;
        return pips;
    }

}
