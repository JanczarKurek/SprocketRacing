package MapServer;

import misc.IdGenerator;

//Klaps w dziąsło
public class DamageEffect implements OnPassEffect {

    private int damage = 0;
    private int duration = 0;
    private static IdGenerator idGenerator = new IdGenerator();
    private int id = idGenerator.genId();
    DamageEffect() {}
    DamageEffect(int damage){
        this.damage = damage;
    }

    @Override
    public void execute(Object who) {
        //Do damage
    }

    @Override
    public String getDescription() {
        return "Hit passing player for " + damage + " damage.";
    }

    @Override
    public int duration() {
        return duration;
    }

    @Override
    public int getId() {
        return id;
    }

}
