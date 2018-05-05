package SmallFunctionalFeaturesDamnYouJava;

import javafx.util.Pair;

import java.util.Collection;
import java.util.Iterator;
import java.util.function.Function;

public class Functional {
    @FunctionalInterface
    public interface Consume<V, E>{
        V consume(V acc, E e);
    }
    static public <V, E> V foldl(Iterable<E> collection, V accumulator, Consume<V, E> consume){
        V v = accumulator;
        for(E e : collection){
            v = consume.consume(v, e);
        }
        return v;
    }

    public static Iterable<Integer> range(int end){
        return () -> new Iterator<>() {
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

    public static <V, E> Iterable<Pair<V, E>> zip(Iterable<V> vIterable, Iterable<E> eIterable){
        return () -> new Iterator<>() {
            Iterator<V> vIterator = vIterable.iterator();
            Iterator<E> eIterator = eIterable.iterator();
            @Override
            public boolean hasNext() {
                return vIterator.hasNext() || eIterator.hasNext();
            }

            @Override
            public Pair<V, E> next() {
                return new Pair<>(vIterator.hasNext() ? vIterator.next() : null, eIterator.hasNext() ? eIterator.next() : null);
            }
        };
    }

    public static <V, E> Iterable<E> map(Iterable<V> vIterable, Function<V, E> function){
        return () -> new Iterator<>() {
            Iterator<V> it = vIterable.iterator();
            @Override
            public boolean hasNext() {
                return it.hasNext();
            }

            @Override
            public E next() {
                return function.apply(it.next());
            }
        };
    }
    public static <E> void unlazy(Collection<E> collection, Iterable<E> iterable){
        for(E e : iterable){
            collection.add(e);
        }
    }

}
