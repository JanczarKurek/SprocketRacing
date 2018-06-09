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
        JSONObject o = new org.json.simple.JSONObject();
        o.put("size", new java.util.Random().nextInt(6) + 1);
        o.put("Color", new Random().nextInt(2) == 0 ? "red" :
                (new Random().nextBoolean() ? "blue" : "yellow"));
        jsonObject.put("DiceSlots", o);
    }

    static void putRandomCardEffect(JSONObject jsonObject) {
        int r = new java.util.Random().nextInt(6);
        JSONObject o = new org.json.simple.JSONObject();
        if (r == 0)
            o.put("effectType", "GetResourceEffect");
        else if (r == 1)
            o.put("effectType", "MoveEffect");
        else if (r == 2)
            o.put("effectType", "HealEffect");
        else if (r == 3)
            o.put("effectType", "SmoothMoveEffect");
        else if (r == 4)
            o.put("effectType", "VentEffect");
        else
            o.put("effectType", "DamageEffect");

        if (r > 0)
            o.put("value", new java.util.Random().nextInt(3));
        else {
            o.put("red", new java.util.Random().nextInt(3));
            o.put("blue", new java.util.Random().nextInt(3));
            o.put("yellow", new java.util.Random().nextInt(3));
            o.put("gears", new java.util.Random().nextInt(3));
        }

    }

    static void putRandomCardEffectArray(JSONObject jsonObject) {
        int r = new java.util.Random().nextInt(2);
        JSONArray jsonArray = new org.json.simple.JSONArray();
        for (int i = 0; i < r; i++) {
            int p = new java.util.Random().nextInt(1) + 1;
            JSONArray ja = new org.json.simple.JSONArray();
            for (int j = 0; j < p; j++) {
                JSONObject o = new org.json.simple.JSONObject();
                putRandomCardEffect(o);
                ja.add(o);
            }
            jsonArray.add(ja);
        }
        jsonObject.put("CardEffect", jsonArray);
    }

    static void putRandomCardUsageCost(JSONObject jsonObject) {
        JSONObject o = new org.json.simple.JSONObject();
        if (new java.util.Random().nextInt(10) < 8) {
            o.put("isCardUsagePipeCost", new Boolean(true));
            o.put("cost", new java.util.Random().nextInt(6) + 1);
        } else
            o.put("isCardUsagePipeCost", new Boolean(false));
        jsonObject.put("CardUsageCost", o);
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
