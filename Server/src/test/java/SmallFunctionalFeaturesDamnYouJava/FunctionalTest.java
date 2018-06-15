package SmallFunctionalFeaturesDamnYouJava;

import org.junit.Test;

import java.util.Arrays;
import java.util.Iterator;

import static org.junit.Assert.*;

public class FunctionalTest {

    @Test
    public void changetoPeek() {
        Iterator<Integer> it = Arrays.asList(0, 1, 2, 3, 4, 5).iterator();
        PeekIterator<Integer> test = Functional.changetoPeek(it);
        assertEquals(test.peek(), Integer.valueOf(0));

        assertEquals(test.peek(), Integer.valueOf(0));
        assertEquals(test.next(), Integer.valueOf(0));
        test.next();
        test.next();
        test.next();
        assertEquals(test.peek(), Integer.valueOf(4));
        assertEquals(test.hasNext(), true);
        assertEquals(test.next(), Integer.valueOf(4));
        assertEquals(test.peek(), Integer.valueOf(5));
        assertTrue(test.hasNext());
    }
}