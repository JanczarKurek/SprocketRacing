package Cards;

import InGameResources.Dice.Dice;

import java.util.Collection;

public interface CardUsageCost {
    int countEffectPower(Collection<Dice> dice);
}
