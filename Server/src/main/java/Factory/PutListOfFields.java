package Factory;


import java.util.*;
import org.json.simple.*;

import static Factory.PutSimplifieBoardField.putSimplifiedBoardField;

public class PutListOfFields {
    static void putListOfFields(JSONObject jsonObject, Collection<Factory.SimplifiedBoardFiled> collection) {
        JSONArray jsonArray = new JSONArray();
        for(SimplifiedBoardFiled sbf : collection){
            JSONObject o = new JSONObject();
            putSimplifiedBoardField(o, sbf);
            jsonArray.add(o);
        }


        jsonObject.put("BoardFields", jsonArray);
    }
}
