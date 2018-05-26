package Cards.CardFactory;

import org.json.simple.*;

import static java.lang.Math.toIntExact;

public class ReadCardUsageCost {
    static Cards.CardUsageCost readCardUsageCost(JSONObject jsonObject) {
        if ((Boolean) jsonObject.get("isCardUsagePipeCost"))
            return new Cards.CardUsagePipCost(toIntExact((Long) jsonObject.get("cost")));
        return new Cards.CardUsageDiceCost();
    }
}
