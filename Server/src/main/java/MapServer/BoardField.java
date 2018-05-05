package MapServer;

import misc.Effect;

import java.util.Collection;
import java.util.LinkedList;

public class BoardField implements Field{

    private Field field;

    public LinkedList<BoardField> getNextFields() {
        return nextFields;
    }

    public LinkedList<BoardField> getPrevFields() {
        return prevFields;
    }

    private LinkedList<BoardField> nextFields = new LinkedList<>();
    private LinkedList<BoardField> prevFields = new LinkedList<>();

    public void addNext(BoardField next){
        nextFields.push(next);
    }

    public void addPrev(BoardField prev){
        prevFields.push(prev);
    }

    public BoardField(Field field){
        this.field = field;
    }

    @Override
    public Collection<Effect> getEffects() {
        return field.getEffects();
    }

    @Override
    public Collection<OnStayEffect> getOnStayEffects() {
        return field.getOnStayEffects();
    }

    @Override
    public Collection<OnPassEffect> getOnPassEffects() {
        return field.getOnPassEffects();
    }

    @Override
    public BoardField addEffect(Effect effect) {
        field.addEffect(effect);
        return this;
    }

    @Override
    public void clearOld() {
        field.clearOld();
    }

    @Override
    public int getId() {
        return field.getId();
    }
}
