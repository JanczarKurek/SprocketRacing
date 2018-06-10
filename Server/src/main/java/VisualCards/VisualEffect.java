package VisualCards;

import Cards.OnCardEffects.*;
import InGameResources.Dice.Dice;
import MapServer.DamageEffect;
import Settings.Settings;
import VisualBoard.VisualElement;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import misc.Effect;

import java.io.FileInputStream;
import java.security.spec.ECField;
import java.util.HashMap;

public class VisualEffect implements VisualElement {
    static private HashMap<String, Image> images = new HashMap<>();
    static{
        try {
            Settings settings = Settings.getSettings();
            String resPref = settings.getResourcesPath();
            images.put("SilverWheel", new Image(new FileInputStream(resPref+"Effects/silverWheel.png")));
            images.put("GoldWheel", new Image(new FileInputStream(resPref+"Effects/goldWheel.png")));
            images.put("Cog", new Image(new FileInputStream(resPref+"Effects/cog.png")));
            images.put("Shield", new Image(new FileInputStream(resPref+"Effects/shield.png")));
            images.put("ShieldX", new Image(new FileInputStream(resPref+"Effects/shieldX.png")));
            images.put("RedCube", new Image(new FileInputStream(resPref+"Effects/redCube.png")));
            images.put("YellowCube", new Image(new FileInputStream(resPref+"Effects/yellowCube.png")));
            images.put("BlueCube", new Image(new FileInputStream(resPref+"Effects/blueCube.png")));
            images.put("RedCubeOut", new Image(new FileInputStream(resPref+"Effects/redCubeOut.png")));
            images.put("YellowCubeOut", new Image(new FileInputStream(resPref+"Effects/yellowCubeOut.png")));
            images.put("BlueCubeOut", new Image(new FileInputStream(resPref+"Effects/blueCubeOut.png")));
            images.put("Slash", new Image(new FileInputStream(resPref+"Effects/slash.png")));
            } catch (Exception e){
            throw new RuntimeException(e);
        }
    }
    private Effect effect;

    private double shift = 0.0;

    public VisualEffect(Effect effect){
        this.effect = effect;
    }

    private Group multiply(Image image, int i){
        Group group = new Group();
        double rightShift = 0.0;
        while(i-- != 0){
            ImageView view = new ImageView(image);
            view.setTranslateX(rightShift);
            rightShift += 25.0;
            group.getChildren().add(view);
        }
        shift += rightShift;
        return group;
    }

    private Node draw(Effect effect){
        throw new RuntimeException("Unknown Effect.");
    }

    private Group draw(MoveEffect effect){
        return multiply(images.get("SilverWheel"), effect.getDistance());
    }

    private Group draw(SmoothMoveEffect effect){
        return multiply(images.get("GoldWheel"), effect.getDistance());
    }

    private Group draw(VentEffect effect){
        switch (effect.getColor()){
            case RED:
                return multiply(images.get("RedCubeOut"), effect.getValue());
            case YELLOW:
                return multiply(images.get("YellowCubeOut"), effect.getValue());
            case BLUE:
                return multiply(images.get("BlueCubeOut"), effect.getValue());
        }
        //todo Add image for ANY vent
        return multiply(images.get("RedCubeOut"), effect.getValue());
    }

    private Group draw(HealEffect effect){
        return multiply(images.get("Shield"), effect.getValue());
    }

    private Group draw(DamageEffect effect){
        return multiply(images.get("ShieldX"), effect.getDamage());
    }

    private Node draw(GetResourceEffect effect){
        Group group = new Group();
        double shiftRight = 0.0;
        ImageView view;
        for(Dice dice : effect.getResourceWallet().getDices()){
            switch (dice.getColor()){
                case RED:
                    view = new ImageView(images.get("RedCube"));
                    view.setTranslateX(shiftRight);
                    shiftRight += 25.0;
                    group.getChildren().add(view);
                    break;
                case YELLOW:
                    view = new ImageView(images.get("YellowCube"));
                    view.setTranslateX(shiftRight);
                    shiftRight += 25.0;
                    group.getChildren().add(view);
                    break;
                case BLUE:
                    view = new ImageView(images.get("BlueCube"));
                    view.setTranslateX(shiftRight);
                    shiftRight += 25.0;
                    group.getChildren().add(view);
            }
        }
        shift += shiftRight;
        Group kek = multiply(images.get("Cog"), effect.getResourceWallet().getGears());
        kek.setTranslateX(shiftRight);
        group.getChildren().add(kek);
        return group;
    }

    @Override
    public Node draw() {
        shift = 0;
        if(effect instanceof MoveEffect)
            return draw((MoveEffect)effect);
        if(effect instanceof SmoothMoveEffect)
            return draw((SmoothMoveEffect)effect);
        if(effect instanceof VentEffect)
            return draw((VentEffect) effect);
        if(effect instanceof HealEffect)
            return draw((HealEffect) effect);
        if(effect instanceof DamageEffect)
            return draw((DamageEffect) effect);
        if(effect instanceof GetResourceEffect)
            return draw((GetResourceEffect) effect);
        return draw(effect);
    }

    @Override
    public void actualize() {

    }
}
