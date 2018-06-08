package MapServer;

import Players.Player;
import misc.IdGenerator;

//Klaps w dziąsło
public class DamageEffect implements OnPassEffect {

    private int damage;
    private int duration = 0;
    private static IdGenerator idGenerator = new IdGenerator();
    private int id = idGenerator.genId();
    public DamageEffect(int damage){
        this.damage = damage;
    }

    @Override
    public void execute(Player who) {
        who.getHpBar().hitFor(damage);

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

    public int getDamage() {
        return damage;
    }

}

