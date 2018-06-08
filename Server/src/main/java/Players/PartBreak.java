package Players;

import misc.Effect;

public class PartBreak implements Effect {

    private int no;

    PartBreak(int i){
        no = i;
    }

    @Override
    public void execute(Player who) {
        who.taskManager.putTask(new Player.PendingTask(Player.Task.BREAKPART, no));
    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public int duration() {
        return 0;
    }

    @Override
    public int getId() {
        return 0;
    }
}
