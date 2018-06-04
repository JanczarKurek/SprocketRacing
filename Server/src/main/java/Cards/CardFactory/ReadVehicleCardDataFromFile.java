package Cards.CardFactory;

import java.util.*;
import org.json.simple.*;
import org.json.simple.parser.*;

import static Cards.CardFactory.ReadSingleVehicleCardData.readSingleVehicleCardData;
import Cards.*;
import java.io.*;

public class ReadVehicleCardDataFromFile {
    public static LinkedList<Cards.VehicleCardData> readVehicleCardDataFromFile(String fileName) {
        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(new java.io.FileReader(fileName));
            JSONObject jsonObject = (JSONObject) obj;
            LinkedList<Cards.VehicleCardData> vehicleCards = new LinkedList<>();
            JSONArray jsonArray = (JSONArray) jsonObject.get("VehicleCards");
            for (Object o : jsonArray)
                vehicleCards.add(readSingleVehicleCardData((JSONObject) o));
            return vehicleCards;
        } catch (java.io.IOException | ParseException e) {
            e.printStackTrace();
        }
        return null; // zmien to potem
    }

    public static VehicleCardData readSingleVehicleCardDataFromFile(String fileName) {
        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse((new FileReader(fileName)));
            JSONObject jsonObject = (JSONObject) obj;
            return readSingleVehicleCardData(jsonObject);
        } catch (java.io.IOException | ParseException e ) {
            e.printStackTrace();
        }
        return null;
    }
}
