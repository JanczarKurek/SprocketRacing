package Factory;

import java.util.*;
public class SimplifiedBoardFiled {
    private LinkedList<Integer> nextFields = new LinkedList<>();
    private int id;
    private LinkedList<MapServer.OnStayEffect> onStayEffects = new LinkedList<>();
    private LinkedList<MapServer.OnPassEffect> onPassEffects = new LinkedList<>();

    public SimplifiedBoardFiled() {}

    public SimplifiedBoardFiled(int id) {
        this.id = id;
    }

    public void add(Integer nextField) {
        nextFields.add(nextField);
    }

    public void add(MapServer.OnStayEffect onStayEffect) {
        onStayEffects.add(onStayEffect);
    }

    public void add(MapServer.OnPassEffect onPassEffect) {
        onPassEffects.add(onPassEffect);
    }

    public void setId(int id) {
        this.id = id;
    }

    public Collection<Integer> getNextFields() {
        return new LinkedList<>(nextFields);
    }

    public Collection<MapServer.OnStayEffect> getOnStayEffects() {
        return new LinkedList<>(onStayEffects);
    }

    public Collection<MapServer.OnPassEffect> getOnPassEffects() {
        return new LinkedList<>(onPassEffects);
    }

    public int getId(){
        return id;
    }
}
