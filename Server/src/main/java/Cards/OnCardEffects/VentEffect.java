package Cards.OnCardEffects;

import Cards.VehicleCard;
import Cards.VehicleCardData;
import ErrorsAndExceptions.WrongMove;
import InGameResources.Dice.Dice;
import Players.Player;
import misc.Effect;

public class VentEffect implements Effect{

    public int getValue() {
        return value;
    }

    private int value;

    public Dice.Color getColor() {
        return color;
    }

    private Dice.Color color = Dice.Color.ANY;

    public VentEffect(int v){
        value = v;
    }

    public VentEffect(int v, Dice.Color color){
        value = v;
        this.color = color;
    }

    @Override
    public void execute(Player who) {
        Player.PendingTask task = new Player.PendingTask(Player.Task.VENT, value);
        task.color = this.color;
        who.taskManager.putTask(task);
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
