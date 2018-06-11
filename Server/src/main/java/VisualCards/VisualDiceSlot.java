package VisualCards;

import InGameResources.Dice.Dice;
import Settings.Settings;
import VisualBoard.VisualElement;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;

public class VisualDiceSlot implements VisualElement {
    private Dice.Color color;
    private Image image;

    public VisualDiceSlot(Dice.Color color){
        try {
            Settings settings = Settings.getSettings();
            String resPref = settings.getResourcesPath();
            this.color = color;
            if (color == Dice.Color.RED) {
                image = new Image(new FileInputStream(resPref + "VehicleCard/FireDicePool.png"));
            }
            else if(color == Dice.Color.BLUE ){
                image = new Image(new FileInputStream(resPref + "VehicleCard/SteamDicePool.png"));
            }
            else{
                image = new Image(new FileInputStream(resPref + "VehicleCard/EletricityDicePool.png"));
            }
        }catch (Exception e){
            System.err.println("File not found!");
        }
    }

    public Node draw(){
        ImageView imageView = new ImageView(image);
        return imageView;
    }

    public void actualize(){

    }
}
