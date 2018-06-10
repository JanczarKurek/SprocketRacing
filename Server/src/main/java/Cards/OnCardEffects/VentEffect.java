package Cards.OnCardEffects;

import Cards.VehicleCard;
import Cards.VehicleCardData;
import ErrorsAndExceptions.WrongMove;
import Players.Player;
import misc.Effect;

public class VentEffect implements Effect{

    private int value;

    public VentEffect(int v){
        value = v;
    }

    @Override
    public void execute(Player who) {
        who.taskManager.putTask(new Player.PendingTask(Player.Task.VENT, value));
    }

    @Override
    public String getDescription() {
        return "Vent for " + value;
    }

    @Override
    public int duration() {
        return 0;
    }

    @Override
    public int getId() {
        return 2141;
    }
}
