package Cards.CardFactory;


import misc.*;
import org.json.simple.*;

import static java.lang.Math.toIntExact;

public class ReadVehicleCardCost {
    static Cost readVehicleCardCost(JSONObject jsonObject) {
        int r = toIntExact((Long) jsonObject.get("red"));
        int y = toIntExact((Long) jsonObject.get("yellow"));
        int b = toIntExact((Long) jsonObject.get("blue"));
        return new misc.Cost(r, y, b);
    }
}
