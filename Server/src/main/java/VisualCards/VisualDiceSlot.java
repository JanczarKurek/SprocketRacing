package VisualCards;

import VisualBoard.VisualElement;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;

public class VisualDiceSlot implements VisualElement {
    private int color;
    private Image image;

    public VisualDiceSlot(int color){ //0 - red, 1 - yellow, 2 - blue
        try {
            this.color = color;
            if (color == 0) {
                image = new Image(new FileInputStream("SprocketRacing/Server/src/test/resources/VehicleCard/FireDicePool.png"));
            }
            else if(color ==2 ){
                image = new Image(new FileInputStream("SprocketRacing/Server/src/test/resources/VehicleCard/SteamDicePool.png"));
            }
            else{
                image = new Image(new FileInputStream("SprocketRacing/Server/src/test/resources/VehicleCard/EletricityDicePool.png"));
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
