package InGameResources;

import ErrorsAndExceptions.NotEnoughResources;
import InGameResources.Dice.Dice;
import InGameResources.Dice.DiceBunch;

public class ResourceWallet{
    private DiceBunch dices = new DiceBunch();
    private int gears = 0;

    public Dice takeDice(int i){
        return dices.remove(i);
    }

    public void putDice(Dice dice){
        dices.add(dice);
    }

    public int takeGears(int amount) throws NotEnoughResources {
        if(gears < amount){
            throw new NotEnoughResources("Not enough gears, expected " + amount + " got " + gears);
        }
        gears -= amount;
        return amount;
    }

}
