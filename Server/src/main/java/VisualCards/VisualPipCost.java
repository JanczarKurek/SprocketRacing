package VisualCards;

import Settings.Settings;
import VisualBoard.VisualElement;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.io.FileInputStream;

public class VisualPipCost implements VisualElement {
    static private Image[] images = new Image[7];
    private int pips;
    static {
        /*Settings settings = Settings.getSettings();
        String resPref = settings.getResourcesPath();
        try {
            images[0] = new Image(new FileInputStream(resPref + "/VehicleCard/Arrows/arrow1.png"));
            images[1] = new Image(new FileInputStream(resPref + "/VehicleCard/Arrows/arrow2.png"));
            images[2] = new Image(new FileInputStream(resPref + "/VehicleCard/Arrows/arrow3.png"));
            images[3] = new Image(new FileInputStream(resPref + "/VehicleCard/Arrows/arrow4.png"));
            images[4] = new Image(new FileInputStream(resPref + "/VehicleCard/Arrows/arrow5.png"));
            images[5] = new Image(new FileInputStream(resPref + "/VehicleCard/Arrows/arrow6.png"));
            images[6] = new Image(new FileInputStream(resPref + "/VehicleCard/Arrows/arrow7.png"));
        }catch (Exception e){
            System.err.println(e.getMessage());
        }*/
        Settings settings = Settings.getSettings();
        String resPref = settings.getResourcesPath();
        try {
            images[0] = new Image(new FileInputStream("Server/src/test/resources/" + "/VehicleCard/Arrows/arrow1.png"));
            images[1] = new Image(new FileInputStream("Server/src/test/resources/" + "/VehicleCard/Arrows/arrow2.png"));
            images[2] = new Image(new FileInputStream("Server/src/test/resources/"+ "/VehicleCard/Arrows/arrow3.png"));
            images[3] = new Image(new FileInputStream("Server/src/test/resources/" + "/VehicleCard/Arrows/arrow4.png"));
            images[4] = new Image(new FileInputStream("Server/src/test/resources/" + "/VehicleCard/Arrows/arrow5.png"));
            images[5] = new Image(new FileInputStream("Server/src/test/resources/" + "/VehicleCard/Arrows/arrow6.png"));
            images[6] = new Image(new FileInputStream("Server/src/test/resources/"+ "/VehicleCard/Arrows/arrow7.png"));
        }catch (Exception e){
            System.err.println(e.getMessage());
        }
    }

    public VisualPipCost(int pips){
        this.pips=pips;
    }

    public Node draw(){
        ImageView imageView = new ImageView();
        imageView.setImage(images[pips-1]);
        return imageView;
    }

    public void actualize(){

    }
}
