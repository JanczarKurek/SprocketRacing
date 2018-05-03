package MapServer;

import java.util.Collection;
import java.util.LinkedList;

public class SimpleField implements Field {

    private LinkedList<OnStayEffect> onStayEffects = new LinkedList<>();
    private LinkedList<OnPassEffect> onPassEffects = new LinkedList<>();

    private int id;

    public SimpleField(int id){
        this.id = id;
    }

    @Override
    public Collection<Effect> getEffects() {
        LinkedList<Effect> effects = new LinkedList<>(onPassEffects);
        effects.addAll(onStayEffects);
        return effects;
    }

    @Override
    public Collection<OnStayEffect> getOnStayEffects() {
        return new LinkedList<>(onStayEffects);
    }

    @Override
    public Collection<OnPassEffect> getOnPassEffects() {
        return new LinkedList<>(onPassEffects);
    }

    @Override
    public void addEffect(Effect effect) {
        if(effect instanceof OnStayEffect){
            onStayEffects.add((OnStayEffect) effect);
        }else if(effect instanceof OnPassEffect){
            onPassEffects.add((OnPassEffect) effect);
        }
    }


    @Override
    public void clearOld() {
        onPassEffects.removeIf(now -> now.duration() == 0);
        onStayEffects.removeIf(now -> now.duration() == 0);
    }

    @Override
    public int getId() {
        return id;
    }
}
