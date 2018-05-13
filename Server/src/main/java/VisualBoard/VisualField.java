package VisualBoard;

import SmallFunctionalFeaturesDamnYouJava.Functional;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class VisualField implements VisualElement{

    private int xAxisSize, yAxisSize;
    private ArrayList<Position> slots;
    private ArrayList<VisualPawn> pawns;

    public VisualField(int xAxisSize, int yAxisSize) {
        this.xAxisSize = xAxisSize;
        this.yAxisSize = yAxisSize;
        slots = new ArrayList<>();
        for(int i : Functional.range(3)){
            for(int j  : Functional.range(3)){
                slots.add(new Position((xAxisSize* i) / 3, (yAxisSize * j) / 3));
            }
        }
        this.pawns = new ArrayList<>();
    }


    public void clear(){
        pawns.clear();
    }

    public void putPawn(VisualPawn pawn){
        pawns.add(pawn);
    }

    @Override
    public Node draw() {
        Group group = new Group();
        for(Pair<VisualPawn, Position> toDraw : Functional.finZip(pawns, slots)){
            Node node = toDraw.getKey().draw();
            node.setTranslateX(node.getTranslateX() + toDraw.getValue().getX());
            node.setTranslateY(node.getTranslateY() + toDraw.getValue().getY());
            group.getChildren().add(node);
        }
        return group;
    }

    @Override
    public void actualize() {

    }
}
