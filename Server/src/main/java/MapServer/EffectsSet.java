package MapServer;

public class EffectsSet {

    public static MapServer.OnStayEffect getOnStayEffect(Long id) {
        return getOnStayEffect(Math.toIntExact(id));
    }

    public static MapServer.OnStayEffect getOnStayEffect(int id) {
        return new MapServer.OnStayEffect() {
            @Override
            public void execute(Object who) {

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
        return getOnPassEffect(Math.toIntExact(id));
    }

    public static MapServer.OnPassEffect getOnPassEffect(int id) {
        return new MapServer.OnPassEffect() {
            @Override
            public void execute(Object who) {

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

    public static int idOfEffect (misc.Effect effect) {
        return -1;
    }
}

