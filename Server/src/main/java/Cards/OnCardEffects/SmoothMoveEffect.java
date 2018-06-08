package Cards.OnCardEffects;

import Players.Player;
import misc.Effect;

public class SmoothMoveEffect implements Effect {
    private int distance;

    public SmoothMoveEffect(int distance) {
        this.distance = distance;
    }

    @Override
    public void execute(Player who) {
        who.taskManager.putTask(new Player.PendingTask(Player.Task.MOVESMOOTH, distance));
    }

    @Override
    public String getDescription() {
        return "Move player by "+ distance +" without hurting.";
    }

    @Override
    public int duration() {
        return 0;
    }

    @Override
    public int getId() {
        return 2138;
    }
}
