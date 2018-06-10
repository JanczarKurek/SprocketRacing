package InGameResources;

import ErrorsAndExceptions.NotEnoughResources;
import InGameResources.Dice.Dice;
import InGameResources.Dice.DiceBunch;
import SmallFunctionalFeaturesDamnYouJava.Functional;

import java.util.Collection;

public class ResourceWallet{
    public DiceBunch getDices() {
        return dices;
    }

    private DiceBunch dices = new DiceBunch();

    public int getGears() {
        return gears;
    }

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

    public void rollUnrolled(){
        for(Dice d : dices)
            if(d.getValue() == 0)
                d.roll();
    }

    public void reroll(int idx){
        dices.get(idx).roll();
    }

    public void increase(int idx){
        dices.get(idx).increase();
    }

    public DiceBunch takeSome(Collection<Integer> indices){
        DiceBunch bunch = new DiceBunch();
        for(Integer index : indices){
            bunch.add(dices.get(index));
        }
        dices.removeIf(bunch::contains);
        return bunch;
    }

    public void putDices(DiceBunch bunch){
        dices.addAll(bunch);
        bunch.clear();
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
