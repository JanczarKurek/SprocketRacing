package MapServer;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Path implements Iterable<Integer>{ //represents path on board
    private LinkedList<Integer> path;
    public Path(List<Integer> path){
        this.path = new LinkedList<>(path);
    }
    Path(){
        path = new LinkedList<>();
    }
    Path(Path path){
        if(path == null) throw new IllegalArgumentException();
        this.path = new LinkedList<>(path.path);
    }
    public int length(){
        return path.size();
    }
    void append(Integer i){
        path.add(i);
    }

    @Override
    public Iterator<Integer> iterator() {
        return path.iterator();
    }

    public Integer start(){
        return path.getFirst();
    }

    public Integer end(){
        return path.getLast();
    }
}
