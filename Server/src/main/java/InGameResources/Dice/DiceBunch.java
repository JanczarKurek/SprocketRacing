package InGameResources.Dice;

import SmallFunctionalFeaturesDamnYouJava.Functional;

import java.util.ArrayList;

public class DiceBunch extends ArrayList<Dice>{
    public DiceBunch(){
        super();
    }
    public DiceBunch(int n, Dice.Color color){
        super();
        for(int i : Functional.range(n)){
            add(new Dice(color));
        }
    }
}
