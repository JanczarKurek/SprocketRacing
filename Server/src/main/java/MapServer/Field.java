package MapServer;

import java.util.Collection;

public interface Field {
    Collection<Effect> getEffects();
    Collection<Effect> getOnStayEffects();
    Collection<Effect> getOnPassEffects();
    void addEffect(Effect effect);
    void clearOld();
    int getId();
}