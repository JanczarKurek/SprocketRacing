package Cards.CardFactory;

import org.json.simple.*;

public class ReadVehicleCardJoints {
    static Boolean[] readVehicleCardJoints(JSONArray jsonArray) {
        Boolean[] result = new Boolean[4];
        for (int i = 0; i < 4; i++)
            result[i] = (Boolean) jsonArray.get(i);
        return result;
    }
}
