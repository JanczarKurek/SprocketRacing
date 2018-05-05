package Cards;

import Dice.Dice;

import java.util.Collection;

public class CardUsageDiceCost implements CardUsageCost {
    @Override
    public int countEffectPower(Collection<Dice> dice) {
        return dice.size();
    }
}
