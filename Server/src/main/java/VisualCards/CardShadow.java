package VisualCards;

import VisualBoard.VisualElement;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import Settings.Settings;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class CardShadow implements VisualElement {
    private Image image;

    public CardShadow() {

        try{
            image = new Image(new FileInputStream(Settings.getSettings().getResourcesPath() + "/VehicleCard/cardShadow.png"));
        }catch (FileNotFoundException e){
            System.err.println("File not found!");
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
