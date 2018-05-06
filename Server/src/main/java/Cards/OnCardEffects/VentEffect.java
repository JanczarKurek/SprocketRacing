package Cards.OnCardEffects;

import misc.Effect;

public class VentEffect implements Effect{

    @Override
    public void execute(Object who) {

    }

    @Override
    public String getDescription() {
        return "Vent";
    }

    @Override
    public int duration() {
        return 0;
    }

    @Override
    public int getId() {
        return 2141;
    }
}
