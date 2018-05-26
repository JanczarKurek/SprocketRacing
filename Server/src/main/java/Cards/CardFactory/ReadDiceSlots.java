package Cards.CardFactory;

import org.json.simple.*;
import InGameResources.Dice.*;

import static java.lang.Math.toIntExact;


public class ReadDiceSlots {
    static DiceSlots readDiceSlots(JSONObject jsonObject) {
        return new InGameResources.Dice.DiceSlotsImpl(
                toIntExact((Long) jsonObject.get("size")),
                readDiceColor(jsonObject));
    }

    static Dice.Color readDiceColor (JSONObject jsonObject) {
        String color = (String) jsonObject.get("Color");
        if (color.equals("red"))
            return InGameResources.Dice.Dice.Color.RED;
        if (color.equals("blue"))
            return InGameResources.Dice.Dice.Color.BLUE;
        return InGameResources.Dice.Dice.Color.YELLOW;
    }
}
