package Dice;

import java.util.Random;

public class Dice {
    private int pips;
    public enum Color{
        RED,
        YELLOW,
        BLUE
    }
    private Color color;
    private static Random generator = new Random();
    int roll(){
        pips = generator.nextInt(6) + 1;
        return pips;
    }

    public int getValue(){
        return pips;
    }

    Dice(Dice.Color color){
        this.color = color;
    }

    int setValue(int value){
        pips = value;
        return pips;
    }

}
