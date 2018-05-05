package Factory;

import static Factory.CreateJSONFile.putBoardToFile;
import static Factory.ReadBoardStructure.readBoardStructure;
import static org.junit.Assert.*;
import MapServer.*;
import java.util.*;

public class FactoryTest1 {

    @org.junit.Test
    public void test1() {
        SimpleField simpleField1 = new SimpleField(1);
        SimpleField simpleField2 = new SimpleField(2);
        SimpleField simpleField3 = new SimpleField(3);

        BoardField boardField1 = new BoardField(simpleField1);
        BoardField boardField2 = new BoardField(simpleField2);
        BoardField boardField3 = new BoardField(simpleField3);

        boardField1.addNext(boardField2);
        boardField2.addPrev(boardField1);

        boardField2.addNext(boardField3);
        boardField3.addPrev(boardField2);

        TreeMap<Integer, BoardField> treeMap = new TreeMap<>();
        treeMap.put(1, boardField1);
        treeMap.put(2, boardField2);
        treeMap.put(3, boardField3);

        MapServer.BoardStructure boardStructure = new MapServer.BoardStructure();
        try {
            java.lang.reflect.Field field = BoardStructure.class.getDeclaredField("fields");
            field.setAccessible(true);
            field.set(boardStructure, treeMap);

            boardStructure.setStart(boardField1);
            boardStructure.setEnd(boardField3);

            String fileName = "example123.json";
            putBoardToFile(fileName, boardStructure);

            BoardStructure result = readBoardStructure(fileName);
            assertEquals(boardStructure.getStart(), result.getStart());
            assertEquals(boardStructure.getEnd(), result.getEnd());

        } catch (NoSuchFieldException e) {
            fail();
        } catch (IllegalAccessException e) {
            fail();
        }
    }
}