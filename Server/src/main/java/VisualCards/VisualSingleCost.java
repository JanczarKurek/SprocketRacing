package VisualCards;

import Settings.Settings;
import VisualBoard.VisualElement;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;

public class VisualSingleCost implements VisualElement {
    static private Image[] images = new Image[4];
    private int number;
    private int color;
    static private ImageView slash;

    static {
        try {
            Settings settings = Settings.getSettings();
            String resPref = settings.getResourcesPath();
            images[0] = new Image(new FileInputStream(resPref + "/VehicleCard/Cost/RedCube.png"));
            images[1] = new Image(new FileInputStream(resPref + "/VehicleCard/Cost/YellowCube.png"));
            images[2] = new Image(new FileInputStream(resPref + "/VehicleCard/Cost/BlueCube.png"));
            images[3] = new Image(new FileInputStream(resPref + "/VehicleCard/Cost/steam.png"));
            slash = new ImageView(new Image(new FileInputStream(resPref + "/VehicleCard/Cost/slash.png")));
        }catch (Exception e){
            System.err.println("File not found!"+e.getMessage());
        }
    }

    public int getNumber(){
        return number;
    }
    public Node getSlash(){
        return slash;
    }

    public VisualSingleCost(int color, int number){
        this.number=number;
        this.color = color;

    }

    public Node draw(){
        ImageView imageView = new ImageView(images[color]);
        return imageView;
    }

    public void actualize(){

    }
}
