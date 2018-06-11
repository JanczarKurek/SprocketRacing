package VisualCards;

import Settings.Settings;
import VisualBoard.VisualElement;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;

public class VisualSingleCost implements VisualElement {
    private Image image;
    private int number;
    private int color;
    private ImageView slash;

    public int getNumber(){
        return number;
    }
    public Node getSlash(){
        return slash;
    }
    public VisualSingleCost(int color, int number){
        Settings settings = Settings.getSettings();
        String resPref = settings.getResourcesPath();
        this.number=number;
        this.color = color;
        try {
            if (color == 0) {
                image = new Image(new FileInputStream("Server/src/test/resources/VehicleCard/Cost/RedCube.png"));
            }
            else if(color ==1 ){
                image = new Image(new FileInputStream("Server/src/test/resources/VehicleCard/Cost/YellowCube.png"));
            }
            else if(color ==2){
                image = new Image(new FileInputStream("Server/src/test/resources/VehicleCard/Cost/BlueCube.png"));
            }
            else{
                image = new Image(new FileInputStream("Server/src/test/resources/VehicleCard/Cost/steam.png"));
            }
            slash = new ImageView(new Image(new FileInputStream("Server/src/test/resources/VehicleCard/Cost/slash.png")));
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
