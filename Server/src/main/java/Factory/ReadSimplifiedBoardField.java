package Factory;

import org.json.simple.*;

import static MapServer.EffectsSet.getOnPassEffect;
import static MapServer.EffectsSet.getOnStayEffect;

public class ReadSimplifiedBoardField {
    static Factory.SimplifiedBoardFiled readSimplifiedBoardField(JSONObject jsonObject) {
        SimplifiedBoardFiled sbf = new SimplifiedBoardFiled();

        sbf.setId((Long) jsonObject.get("id"));

        JSONArray nextFields = (JSONArray) jsonObject.get("nextFields");
        for(int i=0; i<nextFields.size(); i++)
            sbf.add((Long) nextFields.get(i));

        JSONArray onStayEffects = (JSONArray) jsonObject.get("onStayEffects");
        for(int i=0; i<onStayEffects.size(); i++)
            sbf.add(getOnStayEffect((Long) onStayEffects.get(i)));

        JSONArray onPassEffects = (JSONArray) jsonObject.get("onPassEffects");
        for(int i=0; i<onPassEffects.size(); i++)
            sbf.add(getOnPassEffect((Long) onPassEffects.get(i)));

        return sbf;
    }
}
