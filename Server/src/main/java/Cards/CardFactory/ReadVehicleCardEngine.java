package Cards.CardFactory;

import org.json.simple.*;

import static Cards.CardFactory.ReadCardEffect.readCardEffect;
import static Cards.CardFactory.ReadDiceSlots.readDiceSlots;
import static java.lang.Math.toIntExact;

public class ReadVehicleCardEngine {
    static Cards.VehicleCardEngine readVehicleCardEngine(JSONObject jsonObject) {
        return new Cards.VehicleCardEngine(
                readCardUsageCost((JSONObject) jsonObject.get("CardUsageCost")),
                readCardEffect((JSONArray) jsonObject.get("CardEffect")),
                readDiceSlots((JSONObject) jsonObject.get("DiceSlots")));
    }
    static Cards.CardUsageCost readCardUsageCost(JSONObject jsonObject) {
        if ((Boolean) jsonObject.get("isCardUsagePipeCost"))
            return new Cards.CardUsagePipCost(toIntExact((Long) jsonObject.get("cost")));
        return new Cards.CardUsageDiceCost();
    }
}
