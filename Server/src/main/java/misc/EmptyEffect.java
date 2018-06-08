package misc;

import Players.Player;

public class EmptyEffect implements Effect {
    @Override
    public void execute(Player who) {

    }

    @Override
    public String getDescription() {
        return "Empty effect, do nothing.";
    }

    @Override
    public int duration() {
        return 0;
    }

    @Override
    public int getId() {
        return 10;
    }
}
