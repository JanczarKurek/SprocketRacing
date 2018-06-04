package Factory;

import java.util.*;
import org.json.simple.*;
import MapServer.*;

import static Factory.PutListOfFields.putListOfFields;

public class PutBoardStructure {
    static void putBoardStructure(JSONObject jsonObject,
                                         MapServer.BoardStructure boardStructure){

        LinkedList<Factory.SimplifiedBoardFiled> list = new LinkedList<>();

        if (boardStructure.getPathToImage() != null)
            jsonObject.put("pathToImage", boardStructure.getPathToImage());

        //tworzy list sbf na podstawie tree mapy wewnatrz boardStructure oraz umieszcza jej zawartosc w jsonObjecct
        try {
            java.lang.reflect.Field field = BoardStructure.class.getDeclaredField("fields");
            field.setAccessible(true);
            TreeMap<Integer, BoardField> treeMap = (TreeMap<Integer, BoardField>) field.get(boardStructure);

            for(BoardField boardField : treeMap.values()) {
                SimplifiedBoardFiled sbf = new SimplifiedBoardFiled();

                sbf.setId(boardField.getId());

                for(BoardField nextField : boardField.getNextFields())
                    sbf.add(nextField.getId());

                for(OnStayEffect onStayEffect : boardField.getOnStayEffects())
                    sbf.add(onStayEffect);

                for(OnPassEffect onPassEffect : boardField.getOnPassEffects())
                    sbf.add(onPassEffect);

                list.add(sbf);
            }
        } catch (IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }

        putListOfFields(jsonObject, list);
    }
}
