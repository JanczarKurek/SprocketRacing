package Factory;

import org.json.simple.*;
import org.json.simple.parser.*;
import Factory.SimplifiedBoardFiled;
import java.util.*;

import static Factory.ReadSimplifiedBoardField.readSimplifiedBoardField;

public class ReadTreeMap {
    public static java.util.TreeMap<Integer, MapServer.BoardField> readTreeMap(JSONObject jsonObject) {
        JSONArray jsonArray = (JSONArray) jsonObject.get("BoardFields");
        TreeMap<Integer, SimplifiedBoardFiled> assistantTreeMap = new TreeMap<>();
        for(int i=0; i<jsonArray.size(); i++){
            JSONObject o = (JSONObject) jsonArray.get(i);
            SimplifiedBoardFiled sbf = readSimplifiedBoardField(o);
            assistantTreeMap.put(sbf.getId(), sbf);
        }
        // jeszcze zamienic na normalne pola
        return new java.util.TreeMap<>();
    }
}
