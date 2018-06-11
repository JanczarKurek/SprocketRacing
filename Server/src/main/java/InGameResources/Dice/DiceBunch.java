package InGameResources.Dice;

import SmallFunctionalFeaturesDamnYouJava.Functional;

import java.util.ArrayList;
import java.util.Collection;

public class DiceBunch extends ArrayList<Dice>{
    public DiceBunch(){
        super();
    }
    public DiceBunch(Collection<Dice> dice){
        super(dice);
    }
    public DiceBunch(int n, Dice.Color color){
        super();
        for(int i : Functional.range(n)){
            add(new Dice(color));
        }
    }
    public void roll(){
        for(Dice dice : this){
            dice.roll();
        }
    }
}
