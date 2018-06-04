package Cards.OnCardEffects;

import Players.Player;
import misc.Effect;

public class MoveEffect implements Effect {
    private int distance;

    public MoveEffect(int distance) {
        this.distance = distance;
    }

    @Override
    public void execute(Player who) {
        who.value = distance;
        who.task = Player.Task.MOVE;
    }

    @Override
    public String getDescription() {
        return "Move player by " + distance;
    }

    @Override
    public int duration() {
        return 0;
    }

    @Override
    public int getId() {
        return 2137;
    }
}
