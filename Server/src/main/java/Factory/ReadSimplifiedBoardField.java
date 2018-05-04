package Factory;

import org.json.simple.*;
public class ReadSimplifiedBoardField {
    public static Factory.SimplifiedBoardFiled readSimplifiedBoardField(JSONObject jsonObject) {
        return new Factory.SimplifiedBoardFiled();
    }
}
