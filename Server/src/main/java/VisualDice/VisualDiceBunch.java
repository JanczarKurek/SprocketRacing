package VisualDice;

import InGameResources.Dice.Dice;
import InGameResources.Dice.DiceBunch;
import VisualBoard.VisualElement;
import javafx.scene.Group;
import javafx.scene.Node;

public class VisualDiceBunch implements VisualElement{

    private DiceBunch bunch;

    public VisualDiceBunch(DiceBunch bunch){
        this.bunch = bunch;
    }

    @Override
    public Node draw() {
        double xShift = 0.0;
        Group group = new Group();
        for(Dice dice : bunch){
            Node node = new VisualDice(dice).draw();
            node.setTranslateX(xShift);
            xShift += 50;
            group.getChildren().add(node);
        }
        return group;
    }

    @Override
    public void actualize() {

    }
}
