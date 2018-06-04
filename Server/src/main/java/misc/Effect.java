package misc;

import Players.Player;

public interface Effect {
    void execute(Player who);
    String getDescription();
    int duration(); // -1 means infinity
    int getId();
}
