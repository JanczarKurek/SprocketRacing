package Factory;

import static Factory.ReadBoardStructure.readBoardStructure;
import static org.junit.Assert.*;
import MapServer.*;
import java.util.*;

public class CreateBoardFromStdInDataTest {
    static boolean isAfter(TreeMap<Integer, BoardField> map, int a, int b) {
        return map.get(a).getNextFields().contains(map.get(b));
    }

    @org.junit.Test
    public void test() {
        BoardStructure boardStructure = readBoardStructure("ExBoardStructure.json");
        try {
            java.lang.reflect.Field BSfield = BoardStructure.class.getDeclaredField("fields");
            BSfield.setAccessible(true);
            TreeMap<Integer, BoardField> treeMap = (TreeMap<Integer, BoardField>) BSfield.get(boardStructure);

            assertTrue(isAfter(treeMap,0, 1));
            assertTrue(isAfter(treeMap,3, 5));
            assertTrue(isAfter(treeMap,3, 6));
            assertTrue(isAfter(treeMap,3, 7));
            assertTrue(isAfter(treeMap,7, 9));
            assertTrue(isAfter(treeMap,2, 4));
            assertTrue(isAfter(treeMap,0, 2));
            assertTrue(treeMap.get(9).getNextFields().isEmpty());

            assertFalse(isAfter(treeMap, 7, 4));
            assertFalse(isAfter(treeMap, 1, 0));
            assertFalse(isAfter(treeMap, 5, 5));
            assertFalse(isAfter(treeMap, 7, 1));

        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }

    }

}