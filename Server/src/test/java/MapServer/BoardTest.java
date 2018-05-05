package MapServer;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class BoardTest {
    BoardStructure boardStructure = new BoardStructure();
    DamageEffect d1 = new DamageEffect(1);
    DamageEffect d2 = new DamageEffect(2);
    {
        BoardField boardField1 = new BoardField(new SimpleField(0));
        BoardField boardField2 = new BoardField(new SimpleField(1));
        BoardField boardField3 = new BoardField(new SimpleField(2));
        boardField1.addNext(boardField2);
        boardField2.addPrev(boardField1);
        boardField2.addNext(boardField3);
        boardField3.addPrev(boardField2);
        boardField2.addEffect(d1);
        boardField3.addEffect(d2);
        boardStructure.setField(boardField1);
        boardStructure.setField(boardField2);
        boardStructure.setField(boardField3);
        boardStructure.setStart(boardField1);
        boardStructure.setEnd(boardField3);
    }

    @Test
    public void ConstructionGetSetTest(){
        assertEquals(0, boardStructure.getStart());
        assertEquals(2, boardStructure.getEnd());
    }

    @Test
    public void PathValidationTest(){
        Path path = new Path(Arrays.asList(0, 1, 2));
        assertTrue(boardStructure.checkPath(path));
        path.append(4);
        assertFalse(boardStructure.checkPath(path));
    }

}