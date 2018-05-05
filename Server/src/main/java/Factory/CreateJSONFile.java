package Factory;

import static Factory.PutBoardStructure.putBoardStructure;
import org.json.simple.*;
public class CreateJSONFile {
    public static void putBoard(String fileName, MapServer.BoardStructure boardStructure){
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
