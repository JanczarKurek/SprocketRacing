package VisualDice;

import InGameResources.Dice.Dice;
import Settings.Settings;
import VisualBoard.VisualElement;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;

public class VisualDice implements VisualElement {
    private int number;
    private Dice.Color color;
    private Dice dice;
    private Image imageColor;
    private Image imageNumber;

    public VisualDice(Dice dice){
        try {
            Settings settings = Settings.getSettings();
            String resPref = settings.getResourcesPath();
            this.dice = dice;
            this.color = dice.getColor();
            this.number = dice.getValue();
            if (color == Dice.Color.RED) {
                imageColor = new Image(new FileInputStream("Server/src/test/resources/VehicleCard/Dice/redBackgroud.png"));
            }
            else if(color == Dice.Color.BLUE ){
                imageColor = new Image(new FileInputStream("Server/src/test/resources/VehicleCard/Dice/blueBackground.png"));
            }
            else{
                imageColor = new Image(new FileInputStream("Server/src/test/resources/VehicleCard/Dice/yellowBackground.png"));
            }
            if(number == 1){
                imageNumber = new Image(new FileInputStream("Server/src/test/resources/VehicleCard/Dice/dice1.png"));
            }
            else if(number ==2){
                imageNumber = new Image(new FileInputStream("Server/src/test/resources/VehicleCard/Dice/dice2.png"));
            }
            else if(number == 3){
                imageNumber = new Image(new FileInputStream("Server/src/test/resources/VehicleCard/Dice/dice3.png"));
            }
            else if(number == 4){
                imageNumber = new Image(new FileInputStream("Server/src/test/resources/VehicleCard/Dice/dice4.png"));
            }
            else if(number == 5){
                imageNumber = new Image(new FileInputStream("Server/src/test/resources/VehicleCard/Dice/dice5.png"));
            }
            else if(number ==6){
                imageNumber = new Image(new FileInputStream("Server/src/test/resources/VehicleCard/Dice/dice6.png"));
            }
        }catch (Exception e){
            System.err.println("File not found!");
        }
    }

    public Dice getDice(){
        return dice;
    }

    public Node draw(){
        Group group = new Group();
        group.getChildren().add(new ImageView(imageColor));
        group.getChildren().add(new ImageView(imageNumber));
        return group;
    }

   public void actualize(){

        Settings settings = Settings.getSettings();
        String resPref = settings.getResourcesPath();
        try {
            this.dice = dice;
            this.number = dice.getValue();
            if(number == 1){
                imageNumber = new Image(new FileInputStream("Server/src/test/resources/VehicleCard/Dice/dice1.png"));
            }
            else if(number ==2){
                imageNumber = new Image(new FileInputStream("Server/src/test/resources/VehicleCard/Dice/dice2.png"));
            }
            else if(number == 3){
                imageNumber = new Image(new FileInputStream("Server/src/test/resources/VehicleCard/Dice/dice3.png"));
            }
            else if(number == 4){
                imageNumber = new Image(new FileInputStream("Server/src/test/resources/VehicleCard/Dice/dice4.png"));
            }
            else if(number == 5){
                imageNumber = new Image(new FileInputStream("Server/src/test/resources/VehicleCard/Dice/dice5.png"));
            }
            else if(number ==6){
                imageNumber = new Image(new FileInputStream("Server/src/test/resources/VehicleCard/Dice/dice6.png"));
            }
        }catch (Exception e){
            System.err.println("File not found!");
        }
    }
}
