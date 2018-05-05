package Factory;

import java.util.*;
public class SimplifiedBoardFiled implements MapServer.Field {
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

    @Override
    public void addEffect(MapServer.Effect effect) {
        if (effect instanceof MapServer.OnPassEffect)
            add((MapServer.OnPassEffect) effect);
        else if (effect instanceof MapServer.OnStayEffect)
            add((MapServer.OnStayEffect) effect);
    }

    @Override
    public Collection<MapServer.Effect> getEffects() {
        LinkedList<MapServer.Effect> list = new LinkedList<>(onStayEffects);
        list.addAll(onStayEffects);
        return list;
    }

    @Override
    public int getId(){
        return id;
    }

    @Override
    public void clearOld() {
        onPassEffects.removeIf(now -> now.duration() == 0);
        onStayEffects.removeIf(now -> now.duration() == 0);
    }
}
