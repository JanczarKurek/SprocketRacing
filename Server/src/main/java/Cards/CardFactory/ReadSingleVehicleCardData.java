package Cards.CardFactory;

import org.json.simple.*;

import static Cards.CardFactory.ReadVehicleCardCost.readVehicleCardCost;
import static Cards.CardFactory.ReadVehicleCardEngine.readVehicleCardEngine;
import static Cards.CardFactory.ReadVehicleCardJoints.readVehicleCardJoints;
import static java.lang.Math.toIntExact;

public class ReadSingleVehicleCardData {
    static Cards.VehicleCardData readSingleVehicleCardData(JSONObject jsonObject) {
        Cards.VehicleCardData result = new Cards.VehicleCardData();

        result.setCost(readVehicleCardCost((JSONObject) jsonObject.get("Cost")));

        result.setEngine(readVehicleCardEngine((JSONObject) jsonObject.get("VehicleCardEngine")));

        Boolean[] joints = readVehicleCardJoints((JSONArray) jsonObject.get("Joints"));
        result.setJoints(joints[0], joints[1], joints[2], joints[3]);

        result.setId(toIntExact((Long) jsonObject.get("Id")));

        result.setName((String) jsonObject.get("Name"));

        return result;
    }
}
