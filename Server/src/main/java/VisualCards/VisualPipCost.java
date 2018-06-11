package VisualCards;

import Settings.Settings;
import VisualBoard.VisualElement;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.io.FileInputStream;

public class VisualPipCost implements VisualElement {
    Image image;

    public VisualPipCost(int pips){
        try {
            Settings settings = Settings.getSettings();
            String resPref = settings.getResourcesPath();
            switch (pips) {

                case 1:
                    image = new Image(new FileInputStream("Server/src/test/resources/VehicleCard/Arrows/arrow1.png"));
                    break;
                case 2:
                    image = new Image(new FileInputStream("Server/src/test/resources/VehicleCard/Arrows/arrow2.png"));
                    break;
                case 3:
                    image = new Image(new FileInputStream("Server/src/test/resources/VehicleCard/Arrows/arrow3.png"));
                    break;
                case 4:
                    image = new Image(new FileInputStream("Server/src/test/resources/VehicleCard/Arrows/arrow4.png"));
                    break;
                case 5:
                    image = new Image(new FileInputStream("Server/src/test/resources/VehicleCard/Arrows/arrow5.png"));
                    break;
                case 6:
                    image = new Image(new FileInputStream("Server/src/test/resources/VehicleCard/Arrows/arrow6.png"));
                    break;
                case 7:
                    image = new Image(new FileInputStream("Server/src/test/resources/VehicleCard/Arrows/arrow7.png"));
                    break;
            }
        }catch (Exception e){
            System.err.println("Image not found! ");
        }
    }

    public Node draw(){
        ImageView imageView = new ImageView();
        imageView.setImage(image);
        return imageView;
    }

    public void actualize(){

    }
}
