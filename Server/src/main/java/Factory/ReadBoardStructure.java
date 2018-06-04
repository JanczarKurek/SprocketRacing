package Factory;

import org.json.simple.parser.*;
import org.json.simple.*;
import java.util.*;

import static Factory.ReadTreeMap.readTreeMap;
import MapServer.*;
public class ReadBoardStructure {
    public static MapServer.BoardStructure readBoardStructure(String fileName){
        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(new java.io.FileReader(fileName));
            JSONObject jsonObject = (JSONObject) obj;
            BoardStructure boardStructure = new BoardStructure();
            boardStructure.setPathToImage((String) jsonObject.get("pathToImage"));
            try {
                java.lang.reflect.Field field = BoardStructure.class.getDeclaredField("fields");
                field.setAccessible(true);
                TreeMap<Integer, MapServer.BoardField> treeMap = readTreeMap(jsonObject);
                field.set(boardStructure, treeMap);

                for (BoardField boardField : treeMap.values()) {
                    if (boardField.getNextFields().size() == 0)
                        boardStructure.setEnd(boardField);
                    if (boardField.getPrevFields().size() == 0)
                        boardStructure.setStart(boardField);
                }

                return boardStructure;
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
        } catch (java.io.IOException | org.json.simple.parser.ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static LoadedBoardStructure readLoadedBoardStructure(String fileName) {
        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(new java.io.FileReader(fileName));
            JSONObject jsonObject = (JSONObject) obj;
            BoardStructure boardStructure = new BoardStructure();
            boardStructure.setPathToImage((String) jsonObject.get("pathToImage"));
            try {
                java.lang.reflect.Field field = BoardStructure.class.getDeclaredField("fields");
                field.setAccessible(true);
                TreeMap<Integer, MapServer.BoardField> treeMap = readTreeMap(jsonObject);
                field.set(boardStructure, treeMap);

                for (BoardField boardField : treeMap.values()) {
                    if (boardField.getNextFields().size() == 0)
                        boardStructure.setEnd(boardField);
                    if (boardField.getPrevFields().size() == 0)
                        boardStructure.setStart(boardField);
                }

                return new LoadedBoardStructure(boardStructure,
                        (String) jsonObject.get("pathToImage"));
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
        } catch (java.io.IOException | org.json.simple.parser.ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
