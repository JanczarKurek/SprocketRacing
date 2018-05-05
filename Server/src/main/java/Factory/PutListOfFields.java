package Factory;


import java.util.*;
import org.json.simple.*;

import static Factory.PutSimplifieBoardField.putSimplifiedBoardField;

public class PutListOfFields {
    public static void putListOfFields(JSONObject jsonObject, Collection<Factory.SimplifiedBoardFiled> collection) {
        JSONArray jsonArray = new JSONArray();
        for(SimplifiedBoardFiled sbf : collection)
            jsonArray.add(putSimplifiedBoardField(sbf));
        jsonObject.put("BoardFields", jsonArray);
    }
}
