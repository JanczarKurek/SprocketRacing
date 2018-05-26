package Cards;

/* It represents VehicleCard on use effect an it's cost */

import InGameResources.Dice.Dice;
import InGameResources.Dice.DiceSlots;
import ErrorsAndExceptions.WrongColor;
import ErrorsAndExceptions.WrongMove;
import SmallFunctionalFeaturesDamnYouJava.Functional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class VehicleCardEngine {
    private CardUsageCost cardUsageCost;
    private CardEffect possibilities;
    private Proposition actualProposition;
    class Proposition{
        List<CardEffect> effects = new ArrayList<>();
        Collection<Dice> dices;
        Proposition(Collection<Dice> dices) throws WrongMove, WrongColor {
            if(actualProposition != null)
                throw new IllegalStateException("Tried to create proposition with another going on...");
            this.dices = dices;
            diceSlots.tryInsertAll(dices);
            int times = cardUsageCost.countEffectPower(dices);
            for(int i : Functional.range(times)){
                effects.add(possibilities);
            }
            actualProposition = this;
        }

        public List<CardEffect> getEffects() {
            return effects;
        }

        public void accept(){
            if(actualProposition != this){
                throw new IllegalStateException("Error, trying to accept second time or similar");
            }
            try {
                diceSlots.insertAll(dices);
            } catch (Exception e){
                throw new RuntimeException("Should not occurred, but caused by " + e);
            }
            actualProposition = null;
        }

        public Collection<Dice> decline(){
            if(actualProposition != this){
                throw new IllegalStateException("Error, trying to accept second time or similar");
            }
            actualProposition = null;
            return dices;
        }
    }

    public DiceSlots getDiceSlots() {
        return diceSlots;
    }

    private DiceSlots diceSlots;


    public VehicleCardEngine(CardUsageCost cardUsageCost, CardEffect possibilities, DiceSlots diceSlots) {
        this.cardUsageCost = cardUsageCost;
        this.possibilities = possibilities;
        this.diceSlots = diceSlots;
    }

    Proposition getProposition(Collection<Dice> dices) throws WrongMove, WrongColor {
        return new Proposition(dices);
    }


}
