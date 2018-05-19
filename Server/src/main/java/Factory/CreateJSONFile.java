package Factory;

import static Factory.PutBoardStructure.putBoardStructure;
import org.json.simple.*;
public class CreateJSONFile {
    public static void putBordToFile(String fileName, MapServer.Board board) {
        try {
            java.lang.reflect.Field field = MapServer.Board.class.getDeclaredField("boardStructure");
            field.setAccessible(true);
            putBoardToFile(fileName, (MapServer.BoardStructure) field.get(board));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    public static void putBoardToFile(String fileName, MapServer.BoardStructure boardStructure){
        try (java.io.FileWriter fileWriter = new java.io.FileWriter(fileName)){
            JSONObject jsonObject = new JSONObject();
            putBoardStructure(jsonObject, boardStructure);
            fileWriter.write(jsonObject.toString());
            fileWriter.flush();
        } catch (java.io.IOException e) {
            System.out.println(e.getClass());
        }
    }
}
