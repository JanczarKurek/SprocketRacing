package VisualCards;

import Settings.Settings;
import VisualBoard.VisualElement;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Set;

public class VisualJoint implements VisualElement {

    static private Image image;
    private int rotation;
    private int position; //0-left, 1-right, 2-up, 3-down
    static private int heigth;
    static private int width;
   /* static {
        try {
            image = new Image(new FileInputStream(Settings.getSettings().getResourcesPath() + "VehicleCard/Joint.png"));
            File file = new File(Settings.getSettings().getResourcesPath() + "VehicleCard/JointDescription.txt");
            FileInputStream stream = new FileInputStream(file);
            Scanner fileReader = new Scanner(stream);
            width = fileReader.nextInt();
            heigth = fileReader.nextInt();
        }catch (Exception e){
            System.err.println(e.getMessage());
        }
    }*/

    static {
        try {
            String pref = Settings.getSettings().getResourcesPath();
            image = new Image(new FileInputStream(pref + "/VehicleCard/Joint.png"));
            File file = new File(pref + "/VehicleCard/JointDescription.txt");
            FileInputStream stream = new FileInputStream(file);
            Scanner fileReader = new Scanner(stream);
            width = fileReader.nextInt();
            heigth = fileReader.nextInt();
        }catch (Exception e){
            System.err.println(e.getMessage());
        }
    }

    public VisualJoint(boolean left, boolean right, boolean up, boolean down ) {
            if (left) {
                position = 0;
                rotation = 270;
            }
            if (right) {
                position = 1;
                rotation = 90;
            }
            if (up) {
                position = 2;
                rotation = 0;
            }
            if (down) {
                position = 3;
                rotation = 180;
            }

    }

    public int getHeigth(){
        return heigth;
    }
    public int getWidth(){
        return width;
    }
    public int getPosition(){
        return position;
    }

    public Node draw(){
        ImageView imageView = new ImageView();
        imageView.setImage(image);
        imageView.setRotate(rotation);
        return imageView;
    }

    public void actualize(){

    }
}
