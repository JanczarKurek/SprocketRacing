package Cards.CardFactory;

import java.io.*;
import org.json.simple.*;
import java.util.*;

public class ExDeckMaker {

    static int id = 0;

    static void putRandomNameToJson(JSONObject jsonObject) {
        String[] tab = {"vanitas", "vanitatum", "et", "omnia", "vanitas"};
        jsonObject.put("Name", tab[new java.util.Random().nextInt(5)]);
    }

    static void putIdToJson(JSONObject jsonObject) {
        jsonObject.put("Id", id++);
    }

    static void putRandomJointsToJson(JSONObject jsonObject) {
        int r = new java.util.Random().nextInt(100);
        JSONArray jsonArray = new JSONArray();
        if (r < 60)
            for (int i = 0; i < 4; i++)
                jsonArray.add(new Boolean(true));
        else if (r < 70) {
            jsonArray.add(new Boolean(false));
            jsonArray.add(new Boolean(true));
            jsonArray.add(new Boolean(true));
            jsonArray.add(new Boolean(true));
        } else if (r < 80) {
            jsonArray.add(new Boolean(true));
            jsonArray.add(new Boolean(false));
            jsonArray.add(new Boolean(true));
            jsonArray.add(new Boolean(true));
        } else if (r < 90) {
            jsonArray.add(new Boolean(true));
            jsonArray.add(new Boolean(true));
            jsonArray.add(new Boolean(false));
            jsonArray.add(new Boolean(true));
        } else {
            jsonArray.add(new Boolean(true));
            jsonArray.add(new Boolean(true));
            jsonArray.add(new Boolean(true));
            jsonArray.add(new Boolean(false));
        }

        jsonObject.put("Joints", jsonArray);
    }

    static void putRandomDiceSlots(JSONObject jsonObject) {

    }

    static void putRandomCardEffect(JSONObject jsonObject) {

    }

    static void putRandomCardUsageCost(JSONObject jsonObject) {

    }

    static void putRandomVehicleCardEngineToJson(JSONObject jsonObject) {
        JSONObject o = new org.json.simple.JSONObject();
        putRandomDiceSlots(o);
        putRandomCardEffect(o);
        putRandomCardUsageCost(o);
        jsonObject.put("VehicleCardEngine", o);
    }

    static void putRandomCostToJson(JSONObject jsonObject) {
        int r = new Random().nextInt(3);
        JSONObject o = new org.json.simple.JSONObject();
        o.put("red", r == 0 ? 2 : 0);
        o.put("blue", r == 1 ? 2 : 0);
        o.put("yellow", r == 2 ? 2 : 0);
        jsonObject.put("Name", o);
    }

    static void putRandomVehicleCardDataToJson(JSONObject jsonObject) {
        putRandomNameToJson(jsonObject);
        putRandomCostToJson(jsonObject);
        putIdToJson(jsonObject);
        putRandomJointsToJson(jsonObject);
        putRandomVehicleCardEngineToJson(jsonObject);
    }

    static void deckMaker(String deckName) {
        for (int cardIndex = 0; cardIndex < 123; cardIndex++) {
            try (FileWriter fileWriter = new FileWriter(deckName + "/card" + cardIndex + ".json")) {
                JSONObject jsonObject = new JSONObject();
                putRandomVehicleCardDataToJson(jsonObject);
                fileWriter.write(jsonObject.toString());
                fileWriter.flush();
            } catch (java.io.IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] Args) {
        deckMaker("src/main/java/Files/Deck1");
        deckMaker("src/main/java/Files/Deck2");
        deckMaker("src/main/java/Files/Deck3");
        deckMaker("src/main/java/Files/Deck4");
    }
}
