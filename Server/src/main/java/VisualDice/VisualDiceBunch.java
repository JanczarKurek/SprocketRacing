package VisualDice;

import InGameResources.Dice.Dice;
import InGameResources.Dice.DiceBunch;
import VisualBoard.VisualElement;
import VisualCards.VisualVehicle;
import javafx.scene.Group;
import javafx.scene.Node;

public class VisualDiceBunch implements VisualElement{

    private DiceBunch bunch;

    public VisualDiceBunch(DiceBunch bunch){
        this.bunch = bunch;
    }


    private VisualVehicle veh;

    public void setVehicle(VisualVehicle veh){
        this.veh = veh;
    }

    @Override
    public Node draw() {
        double xShift = 0.0;
        Group group = new Group();
        int pos = 0;
        for(Dice dice : bunch){
            VisualDice visualDice = new VisualDice(dice);
            visualDice.setPositionOnHand(pos);
            visualDice.setPlayer(veh);
            Node node = visualDice.draw();
            node.setTranslateX(xShift);
            xShift += 50;
            pos++;
            group.getChildren().add(node);
        }
        return group;
    }

    @Override
    public void actualize() {

    }
}
