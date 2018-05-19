package Factory;

import org.json.simple.*;
import MapServer.*;

import static MapServer.EffectsSet.idOfEffect;

public class PutSimplifieBoardField {
    static void putSimplifiedBoardField(JSONObject jsonObject, Factory.SimplifiedBoardFiled sbf){
        jsonObject.put("id", sbf.getId());

        JSONArray nextFields = new JSONArray();
        for(Integer i : sbf.getNextFields())
            nextFields.add(i);
        jsonObject.put("nextFields", nextFields);

        JSONArray onStayEffects = new JSONArray();
        for(OnStayEffect onStayEffect : sbf.getOnStayEffects())
            onStayEffects.add(idOfEffect(onStayEffect));
        jsonObject.put("onStayEffects", onStayEffects);

        JSONArray onPassEffects = new org.json.simple.JSONArray();
        for(OnPassEffect onPassEffect : sbf.getOnPassEffects())
            onPassEffects.add(idOfEffect(onPassEffect));
        jsonObject.put("onPassEffects", onPassEffects);
    }
}
