package Cards;

/* It represents VehicleCard on use effect an it's cost */

import Dice.Dice;
import Dice.DiceSlots;
import ErrorsAndExceptions.WrongColor;
import ErrorsAndExceptions.WrongMove;
import SmallFunctionalFeaturesDamnYouJava.Functional;

import java.util.ArrayList;
import java.util.Collection;

public class VehicleCardEngine {
    private CardUsageCost cardUsageCost;
    private CardEffect possibilities;
    private DiceSlots diceSlots;

    public VehicleCardEngine(CardUsageCost cardUsageCost, CardEffect possibilities, DiceSlots diceSlots) {
        this.cardUsageCost = cardUsageCost;
        this.possibilities = possibilities;
        this.diceSlots = diceSlots;
    }

    Collection<CardEffect> run(Collection<Dice> dices) throws WrongMove, WrongColor {
        diceSlots.insertAll(dices);
        int times = cardUsageCost.countEffectPower(dices);
        Collection<CardEffect> ret = new ArrayList<>();
        for(int i : Functional.range(times)){
            ret.add(possibilities);
        }
        return ret;
    }
}
