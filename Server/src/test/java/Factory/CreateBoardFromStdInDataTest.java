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
    public void test1() {
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

            assertEquals(treeMap.get(0).getNextFields().size(), 2);
            assertEquals(treeMap.get(8).getNextFields().size(), 1);
            assertEquals(treeMap.get(3).getNextFields().size(), 3);

        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }

    }


    @org.junit.Test
    public void test2() {
        BoardStructure boardStructure = readBoardStructure("ExBoardStructure(1).json");
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

            assertEquals(treeMap.get(0).getNextFields().size(), 2);
            assertEquals(treeMap.get(8).getNextFields().size(), 1);
            assertEquals(treeMap.get(3).getNextFields().size(), 3);

            assertEquals(2,((DamageEffect) treeMap.get(7).getEffects().iterator().next()).getDamage());
            assertEquals(1,((DamageEffect) treeMap.get(3).getEffects().iterator().next()).getDamage());

            assertTrue(treeMap.get(1).getEffects().isEmpty());

        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }

    }


    @org.junit.Test
    public void test3() {
        BoardStructure boardStructure = readBoardStructure("ExBoardStructure(2).json");
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

            assertFalse(isAfter(treeMap, 7, 4));
            assertFalse(isAfter(treeMap, 1, 0));
            assertFalse(isAfter(treeMap, 5, 5));
            assertFalse(isAfter(treeMap, 7, 1));

            assertEquals(treeMap.get(0).getNextFields().size(), 2);
            assertEquals(treeMap.get(8).getNextFields().size(), 1);
            assertEquals(treeMap.get(3).getNextFields().size(), 3);

            assertEquals(2,((DamageEffect) treeMap.get(7).getEffects().iterator().next()).getDamage());
            assertEquals(1,((DamageEffect) treeMap.get(3).getEffects().iterator().next()).getDamage());

            assertTrue(treeMap.get(1).getEffects().isEmpty());

            assertEquals(14, boardStructure.getEnd());

            assertTrue(isAfter(treeMap, 9, 10));
            assertTrue(isAfter(treeMap, 10, 11));

            assertFalse(isAfter(treeMap, 9, 11));

            assertTrue(treeMap.get(14).getNextFields().isEmpty());

        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }

    }


}