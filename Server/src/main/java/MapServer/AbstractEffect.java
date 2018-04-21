package MapServer;

public interface AbstractEffect {
    void execute(Object who);
    EffectTypes getTypeOfEffect();
    String getDescription();
    int duration(); // -1 means infinity
}
