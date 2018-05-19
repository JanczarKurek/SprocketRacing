package Cards.CardFactory;

import org.json.simple.*;

import static Cards.CardFactory.ReadVehicleCardCost.readVehicleCardCost;
import static Cards.CardFactory.ReadVehicleCardEngine.readVehicleCardEngine;
import static Cards.CardFactory.ReadVehicleCardId.readVehicleCardId;
import static Cards.CardFactory.ReadVehicleCardJoints.readVehicleCardJoints;
import static Cards.CardFactory.ReadVehicleCardName.readVehicleCardName;

public class ReadSingleVehicleCardData {
    public static Cards.VehicleCardData readSingleVehicleCardData(JSONObject jsonObject) {
        Cards.VehicleCardData result = new Cards.VehicleCardData();

        result.setCost(readVehicleCardCost((JSONObject) jsonObject.get("Cost")));

        result.setEngine(readVehicleCardEngine((JSONObject) jsonObject.get("VehicleCardEngine")));

        Boolean[] joints = readVehicleCardJoints((JSONObject) jsonObject.get("Joints"));
        result.setJoints(joints[0], joints[1], joints[2], joints[3]);

        result.setId(readVehicleCardId((JSONObject) jsonObject.get("Id")));

        result.setName(readVehicleCardName((JSONObject) jsonObject.get("Name")));

        return result;
    }
}
