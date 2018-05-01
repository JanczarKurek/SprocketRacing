package MapServer;

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

    BoardField(Field field){
        this.field = field;
    }

    @Override
    public Collection<Effect> getEffects() {
        return field.getEffects();
    }

    @Override
    public Collection<Effect> getOnStayEffects() {
        return field.getOnStayEffects();
    }

    @Override
    public Collection<Effect> getOnPassEffects() {
        return field.getOnPassEffects();
    }

    @Override
    public void addEffect(Effect effect) {
        field.addEffect(effect);
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
