package VisualCards;

import InGameResources.Dice.Dice;
import Settings.Settings;
import VisualBoard.VisualElement;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.util.HashMap;

public class VisualDiceSlot implements VisualElement {
    private Dice.Color color;
    static private HashMap<Dice.Color, Image> images = new HashMap();

    static{
       try {
            Settings settings = Settings.getSettings();
            String resPref = settings.getResourcesPath();
            images.put(Dice.Color.RED,new Image(new FileInputStream(resPref + "VehicleCard/FireDicePool.png")));
            images.put(Dice.Color.BLUE,new Image(new FileInputStream(resPref + "VehicleCard/SteamDicePool.png")));
            images.put(Dice.Color.YELLOW, new Image(new FileInputStream(resPref + "VehicleCard/EletricityDicePool.png")));
        }catch (Exception e){
            System.err.println("File not found!");
        }

    }

    public VisualDiceSlot(Dice.Color color){
            this.color = color;
    }

    public Node draw(){
        ImageView imageView = new ImageView(images.get(color));
        return imageView;
    }

    public void actualize(){

    }
}
