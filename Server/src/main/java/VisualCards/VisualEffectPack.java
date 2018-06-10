package VisualCards;

import Cards.CardEffect;
import VisualBoard.VisualElement;
import javafx.scene.Group;
import javafx.scene.Node;
import misc.Effect;

import java.util.List;

public class VisualEffectPack implements VisualElement{

    private CardEffect effects;

    VisualEffectPack(CardEffect effects){
        this.effects = effects;
    }

    @Override
    public Node draw() {
        double yShift = 0.0;
        Group ret = new Group();
        for(List<Effect> possibility : effects){
            Group pack = new Group();
            double xShift = 0.0;
            for(Effect effect : possibility){
                VisualEffect e = new VisualEffect(effect);
                Node node = e.draw();
                node.setTranslateX(xShift);
                xShift += e.getShift();
                pack.getChildren().add(node);
            }
            pack.setTranslateY(yShift);
            yShift += 50;
            ret.getChildren().add(pack);
        }
        return ret;
    }

    @Override
    public void actualize() {

    }
}
