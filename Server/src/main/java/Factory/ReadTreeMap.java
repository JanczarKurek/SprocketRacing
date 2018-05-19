package Factory;

import org.json.simple.*;
import org.json.simple.parser.*;
import Factory.SimplifiedBoardFiled;
import java.util.*;

import static Factory.ReadSimplifiedBoardField.readSimplifiedBoardField;

public class ReadTreeMap {
    public static java.util.TreeMap<Integer, MapServer.BoardField> readTreeMap(JSONObject jsonObject) {
        JSONArray jsonArray = (JSONArray) jsonObject.get("BoardFields");
        LinkedList<SimplifiedBoardFiled> list = new LinkedList<>();
        for(int i = 0; i < jsonArray.size(); i++) {
            JSONObject o = (JSONObject) jsonArray.get(i);
            SimplifiedBoardFiled sbf = readSimplifiedBoardField(o);
            list.add(sbf);
        }

        TreeMap<Integer, MapServer.BoardField> treeMap = new TreeMap<>();
        for(SimplifiedBoardFiled sbf : list)
            treeMap.put(sbf.getId(), new MapServer.BoardField(sbf));

        for(SimplifiedBoardFiled sbf : list) {
            MapServer.BoardField current = treeMap.get(sbf.getId());
            for(Integer i : sbf.getNextFields()) {
                MapServer.BoardField next = treeMap.get(i);
                current.addNext(next);
                next.addPrev(current);
            }
        }

        return treeMap;
    }
}
