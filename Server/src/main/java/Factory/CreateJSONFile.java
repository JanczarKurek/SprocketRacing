package Factory;

import static Factory.PutBoardStructure.putBoardStructure;

public class CreateJSONFile {
    public static void putBoard(String fileName, MapServer.BoardStructure boardStructure){
        try (java.io.FileWriter fileWriter = new java.io.FileWriter(fileName)){
            putBoardStructure(fileWriter, boardStructure);
        } catch (java.io.IOException e) {
            System.out.println(e.getClass());
        }
    }
}
