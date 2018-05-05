package Cards;

import Dice.Dice;

import java.util.Collection;

public interface CardUsageCost {
    public int countEffectPower(Collection<Dice> dice);
}
