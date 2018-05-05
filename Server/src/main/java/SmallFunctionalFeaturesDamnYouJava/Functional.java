package SmallFunctionalFeaturesDamnYouJava;

import java.util.Collection;
import java.util.Iterator;

public class Functional {
    @FunctionalInterface
    public interface Consume<V, E>{
        V consume(V acc, E e);
    }
    static public <V, E> V foldl(Collection<E> collection, V accumulator, Consume<V, E> consume){
        V v = accumulator;
        for(E e : collection){
            v = consume.consume(v, e);
        }
        return v;
    }

    public static Iterable<Integer> range(int end){
        return () -> new Iterator<Integer>() {
            int i = 0;
            @Override
            public boolean hasNext() {
                return i < end;
            }

            @Override
            public Integer next() {
                return i++;
            }
        };
    }
}
