package VisualCards;

import Cards.Card;
import Cards.CardUsageCost;
import Cards.CardUsageDiceCost;
import Cards.CardUsagePipCost;
import Settings.Settings;
import VisualBoard.VisualElement;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;

public class VisualUsageCost implements VisualElement {

    private CardUsageCost cost;
    private static HashMap<Integer, Image> images = new HashMap<>();

    static{
        String resPref = Settings.getSettings().getResourcesPath();

        try {
            images.put(0, new Image(new FileInputStream(resPref+"VehicleCard/Arrows/arrow7.png")));
            images.put(1, new Image(new FileInputStream(resPref+"VehicleCard/Arrows/arrow1.png")));
            images.put(2, new Image(new FileInputStream(resPref+"VehicleCard/Arrows/arrow2.png")));
            images.put(3, new Image(new FileInputStream(resPref+"VehicleCard/Arrows/arrow3.png")));
            images.put(4, new Image(new FileInputStream(resPref+"VehicleCard/Arrows/arrow4.png")));
            images.put(5, new Image(new FileInputStream(resPref+"VehicleCard/Arrows/arrow5.png")));
            images.put(6, new Image(new FileInputStream(resPref+"VehicleCard/Arrows/arrow6.png")));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public VisualUsageCost(CardUsageCost cost){
        this.cost = cost;
    }

    public Node draw(CardUsagePipCost cost){
        return new ImageView(images.get(cost.getPipCost()));
    }

    public Node draw(CardUsageDiceCost cost){
        return new ImageView(images.get(0));
    }


    @Override
    public Node draw() {
        if(cost instanceof CardUsageDiceCost)
            return draw((CardUsageDiceCost) cost);
        if(cost instanceof CardUsagePipCost)
            return draw((CardUsagePipCost) cost);
        return null;
    }

    @Override
    public void actualize() {

    }
}
