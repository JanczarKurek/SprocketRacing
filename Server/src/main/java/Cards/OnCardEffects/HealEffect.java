package Cards.OnCardEffects;

import misc.Effect;

public class HealEffect implements Effect {

    private int value;

    public HealEffect(int value) {
        this.value = value;
    }

    @Override
    public void execute(Object who) {

    }

    @Override
    public String getDescription() {
        return "Heals player for "+ value +" hitpoints";
    }

    @Override
    public int duration() {
        return 0;
    }

    @Override
    public int getId() {
        return 2139;
    }
}
