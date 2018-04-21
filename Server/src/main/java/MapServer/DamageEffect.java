package MapServer;

public class DamageEffect implements AbstractEffect {

    private int damage = 0;
    private int duration = 0;
    DamageEffect() {}
    DamageEffect(int damage){
        this.damage = damage;
    }

    @Override
    public void execute(Object who) {
        //Do damage
    }

    @Override
    public EffectTypes getTypeOfEffect() {
        return EffectTypes.ON_PASS;
    }

    @Override
    public String getDescription() {
        return "Hit passing player for " + damage + " damage.";
    }

    @Override
    public int duration() {
        return duration;
    }
}
