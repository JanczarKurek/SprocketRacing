package VisualDice;

import InGameResources.Dice.Dice;
import VisualBoard.VisualElement;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.ImageView;

import java.util.ArrayList;

public class VisualRolledDice implements VisualElement {
    private ArrayList< VisualDice> dices;

    public VisualRolledDice(){
        dices = new ArrayList<>();
    }

    public void insert(Dice dice){
        dices.add(new VisualDice(dice));
    }

    public Node draw(){
        Group group = new Group();
        int i=0;
        for(VisualDice dice : dices){
            Node node = dice.draw();
            node.setTranslateX(50*i);
            node.setTranslateY(50);
            node.setOnMouseClicked(event -> {
                System.out.println("kliknieta jestem");
            });
            group.getChildren().add(node);
            i++;
        }
        return group;
    }

    public void actualize(){

    }
}
