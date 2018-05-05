package MapServer;

import misc.Effect;

import java.util.Collection;

public interface Field {
    Collection<Effect> getEffects();
    Collection<OnStayEffect> getOnStayEffects();
    Collection<OnPassEffect> getOnPassEffects();
    Field addEffect(Effect effect);
    void clearOld();
    int getId();
}