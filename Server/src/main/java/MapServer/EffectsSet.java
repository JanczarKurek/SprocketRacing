package MapServer;

import Players.Player;

import static java.lang.Math.toIntExact;

public class EffectsSet {

    public static MapServer.OnStayEffect getOnStayEffect(Long id) {
        return getOnStayEffect(toIntExact(id));
    }

    public static MapServer.OnStayEffect getOnStayEffect(int id) {
        return new MapServer.OnStayEffect() {
            @Override
            public void execute(Player who) {

            }

            @Override
            public String getDescription() {
                return null;
            }

            @Override
            public int duration() {
                return 0;
            }

            @Override
            public int getId() {
                return 0;
            }
        };
    }

    public static MapServer.OnPassEffect getOnPassEffect(Long id) {
        return getOnPassEffect(toIntExact(id));
    }

    public static MapServer.OnPassEffect getOnPassEffect(int id) {
        if (id < 100)
            return new DamageEffect(id);
        return null;
    }

    public static misc.Effect getEffect(int id) {
        if (id < 100)
            return getOnPassEffect(id);
        return getOnStayEffect(id);
    }

    public static int idOfEffect (misc.Effect effect) {
        if (effect instanceof MapServer.DamageEffect)
            return ((DamageEffect) effect).getDamage();
        return -1;
    }
}

