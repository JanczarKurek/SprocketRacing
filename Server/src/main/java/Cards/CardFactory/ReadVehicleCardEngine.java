package Cards.CardFactory;

import org.json.simple.*;

import static Cards.CardFactory.ReadCardEffect.readCardEffect;
import static Cards.CardFactory.ReadCardUsageCost.readCardUsageCost;
import static Cards.CardFactory.ReadDiceSlots.readDiceSlots;

public class ReadVehicleCardEngine {
    static Cards.VehicleCardEngine readVehicleCardEngine(JSONObject jsonObject) {
        return new Cards.VehicleCardEngine(
                readCardUsageCost((JSONObject) jsonObject.get("CardUsageCost")),
                readCardEffect((JSONArray) jsonObject.get("CardEffect")),
                readDiceSlots((JSONObject) jsonObject.get("DiceSlots")));
    }
}
