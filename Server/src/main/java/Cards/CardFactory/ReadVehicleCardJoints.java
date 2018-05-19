package Cards.CardFactory;

import org.json.simple.*;

public class ReadVehicleCardJoints {
    static Boolean[] readVehicleCardJoints(JSONObject jsonObject) {
        JSONArray jsonArray = (JSONArray) jsonObject.get("Joints");
        Boolean[] result = new Boolean[4];
        for (int i = 0; i < 4; i++)
            result[i] = (Boolean) jsonArray.get(i);
        return result;
    }
}
