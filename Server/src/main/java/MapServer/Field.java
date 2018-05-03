package MapServer;

import java.util.Collection;

public interface Field {
    Collection<Effect> getEffects();
    Collection<OnStayEffect> getOnStayEffects();
    Collection<OnPassEffect> getOnPassEffects();
    void addEffect(Effect effect);
    void clearOld();
    int getId();
}