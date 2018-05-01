package MapServer;

import misc.IdGenerator;

import java.util.Collection;
import java.util.LinkedList;
import java.util.ListIterator;

public class SimpleField implements Field {

    LinkedList<Effect> effects = new LinkedList<>();

    static IdGenerator idGenerator = new IdGenerator();
    int id = idGenerator.genId();

    @Override
    public Collection<Effect> getEffects() {
        return new LinkedList<>(effects);
    }

    @Override
    public Collection<Effect> getOnStayEffects() {
        LinkedList<Effect> ret = new LinkedList<>(effects);
        ret.removeIf(e -> !(e instanceof OnStayEffect));
        return ret;
    }

    @Override
    public Collection<Effect> getOnPassEffects() {
        LinkedList<Effect> ret = new LinkedList<>(effects);
        ret.removeIf(e -> !(e instanceof OnPassEffect));
        return ret;
    }

    @Override
    public void addEffect(Effect effect) {
        effects.push(effect);
    }

    @Override
    public void clearOld() {
        effects.removeIf(now -> now.duration() == 0);
    }

    @Override
    public int getId() {
        return id;
    }
}
