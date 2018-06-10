package Cards.CardFactory;

import org.json.simple.*;
import misc.*;
import Cards.OnCardEffects.*;
import java.util.*;
import InGameResources.*;

import static java.lang.Math.toIntExact;

public class ReadCardEffect {
    static Cards.CardEffect readCardEffect(JSONArray jsonArray) {
        ArrayList<ArrayList<Effect>> all = new ArrayList<>();
        for (Object object: jsonArray) {
            JSONArray jArray = (JSONArray) object;
            ArrayList<Effect> effects = new ArrayList<>();
            for (Object o : jArray)
                effects.add(readEffect((JSONObject) o));
            all.add(effects);
        }
        return new Cards.CardEffect(all);
    }

    static Effect readEffect(JSONObject jsonObject) {
        String effectType = (String) jsonObject.get("effectType");
        if (effectType.equals("GetResourceEffect"))
            return readGetResourceEffects(jsonObject);
        if (effectType.equals("HealEffect"))
            return readHealEffect(jsonObject);
        if (effectType.equals("MoveEffect"))
            return readMoveEffect(jsonObject);
        if (effectType.equals("SmoothMoveEffect"))
            return readSmoothMoveEffect(jsonObject);
        if (effectType.equals("VentEffect"))
            return readVentEffect(jsonObject);
        return readDamageEffect(jsonObject);
    }

    static GetResourceEffect readGetResourceEffects(JSONObject jsonObject) {
        return new GetResourceEffect(new ResourceWallet(
                toIntExact((Long) jsonObject.get("red")),
                toIntExact((Long) jsonObject.get("yellow")),
                toIntExact((Long) jsonObject.get("blue")),
                toIntExact((Long) jsonObject.get("gears"))
        ));
    }

    static HealEffect readHealEffect(JSONObject jsonObject) {
        return new HealEffect(toIntExact((Long) jsonObject.get("value")));
    }

    static MoveEffect readMoveEffect(JSONObject jsonObject) {
        return new MoveEffect(toIntExact((Long) jsonObject.get("value")));
    }

    static SmoothMoveEffect readSmoothMoveEffect(JSONObject jsonObject) {
        return new SmoothMoveEffect(toIntExact((Long) jsonObject.get("value")));
    }

    static VentEffect readVentEffect(JSONObject jsonObject) {
        String s =(String) jsonObject.get("color");
        InGameResources.Dice.Dice.Color color;
        if (s.equals("red"))
            color = InGameResources.Dice.Dice.Color.RED;
        else if (s.equals("blue"))
            color = InGameResources.Dice.Dice.Color.BLUE;
        else
            color = InGameResources.Dice.Dice.Color.YELLOW;

        return new VentEffect(toIntExact((Long) jsonObject.get("value")), color);
    }

    static MapServer.DamageEffect readDamageEffect(JSONObject jsonObject) {
        return new MapServer.DamageEffect(toIntExact((Long) jsonObject.get("value")));
    }
}
