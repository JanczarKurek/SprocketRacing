package InGameResources;

import ErrorsAndExceptions.NotEnoughResources;
import InGameResources.Dice.Dice;
import InGameResources.Dice.DiceBunch;
import SmallFunctionalFeaturesDamnYouJava.Functional;

import java.util.Collection;

public class ResourceWallet{
    private DiceBunch dices = new DiceBunch();
    private int gears = 0;

    public ResourceWallet(){}

    public ResourceWallet(int r, int y, int b, int g){
        dices = new DiceBunch(r, Dice.Color.RED);
        dices.addAll(new DiceBunch(y, Dice.Color.YELLOW));
        dices.addAll(new DiceBunch(b, Dice.Color.RED));
        gears = g;
    }

    public Dice takeDice(int i){
        return dices.remove(i);
    }

    private void putDice(Dice dice){
        dices.add(dice);
    }

    public void putDice(int t, Dice.Color color){
        for(int i = 0; i < t; ++i)
            putDice(new Dice(color));
    }

    public int takeGears(int amount) throws NotEnoughResources {
        if(gears < amount){
            throw new NotEnoughResources("Not enough gears, expected " + amount + " got " + gears);
        }
        gears -= amount;
        return amount;
    }

    public void putGears(int amount){
        gears += amount;
    }

    public void transferFrom(ResourceWallet other){
        dices.addAll(other.dices);
        other.dices.clear();
        gears += other.gears;
        other.gears = 0;
    }

    public void transferFrom(Collection<Dice> dice){
        dices.addAll(dice);
        dice.clear();
    }

    public void transferTo(ResourceWallet other){
        other.transferFrom(this);
    }
}
