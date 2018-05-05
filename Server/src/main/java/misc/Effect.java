package misc;

public interface Effect {
    void execute(Object who);
    String getDescription();
    int duration(); // -1 means infinity
    int getId();
}
