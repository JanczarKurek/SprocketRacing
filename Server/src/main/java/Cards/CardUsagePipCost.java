package Cards;

import InGameResources.Dice.Dice;
import SmallFunctionalFeaturesDamnYouJava.Functional;

import java.util.Collection;

public class CardUsagePipCost implements CardUsageCost {
    private int cost;
    public CardUsagePipCost(int cost){
        this.cost = cost;
    }
    @Override
    public int countEffectPower(Collection<Dice> dice) {
        int sum = Functional.foldl(dice, 0, (Integer acc, Dice d) -> acc + d.getValue());
        return sum / cost;
    }
    public int getPipCost(){
        return cost;
    }
}
