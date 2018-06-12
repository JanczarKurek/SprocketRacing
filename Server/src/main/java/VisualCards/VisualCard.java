package VisualCards;

import Cards.*;
import InGameResources.Dice.Dice;
import InGameResources.Dice.DiceSlots;
import VisualBoard.VisualElement;
import Settings.Settings;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import misc.Cost;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

public class VisualCard implements VisualElement {

    static private Image background;
    private Card card;
    static private int height;
    static private int width;
    private VisualName name;
    private LinkedList<Integer> effects;
    private Dice.Color color;
    private int usagePipCost;
    private int slotsNumber;

    static {
        Settings settings = Settings.getSettings();
        String resPref = settings.getResourcesPath();
        try {
            background = new Image(new FileInputStream(resPref + "VehicleCard/CardVehicle.png"));
        }catch (Exception e){
            System.out.println("tu łapię " + e.getMessage());
        }
        try {
            File file = new File(resPref + "VehicleCard/VehicleCardDescription.txt");
            FileInputStream stream = new FileInputStream(file);
            Scanner fileReader = new Scanner(stream);
            setXY(fileReader.nextInt(), fileReader.nextInt());
        }catch (Exception e){
            System.out.println("tu też");
        }
    }
    public VisualCard(Card card){
        this.card = card;
        if(card instanceof VehicleCard) {
            try {

                effects = new LinkedList<>();
            } catch (Exception e) {
                System.out.println("tu też");
            }

            name = new VisualName(card.getName());
            setColorSlot(((VehicleCardData) card).getEngine().getDiceSlots().getColor());
            if (((VehicleCardData) card).getEngine().getUsageCost() instanceof CardUsageDiceCost)
                setUsagePipCost(7);
            else
                setUsagePipCost(((CardUsagePipCost) ((VehicleCardData) card).getEngine().getUsageCost()).getPipCost());
            setNumberSlots(((VehicleCardData) card).getEngine().getDiceSlots().getSize());

        }

    }

    static private void setXY(int x, int y){
        width = x;
        height = y;
    }

    private void setUsagePipCost(int cost){
        usagePipCost = cost;
    }


    private void setColorSlot(Dice.Color color){
        this.color = color;
    }

    private void setNumberSlots(int numberSlots){
        this.slotsNumber = numberSlots;
    }

    public LinkedList<Integer> getEffects(){
        return effects;
    }
    public int getHeight(){
        return height;
    }

    public int getWidth() {
        return width;
    }

    public DiceSlots getSlots(){
        return ((VehicleCardData)card).getDiceSlots();
    }

    public int getUsagePipCost(){
        return usagePipCost;
    }

    public Dice.Color getColorSlot(){
        return color;
    }


    public Card getCard(){
        return card;
    }

    public VisualName getName(){
        return name;
    }
    @Override
    public Node draw(){
        System.out.println("ID "+card.getID());
        Group group = new Group();
        ImageView imageView = new ImageView();
        imageView.setImage(background);
        group.getChildren().add(imageView);
        group.getChildren().add(new VisualVehicleCardController(this, imageView).draw());
        return group;
    }

    @Override
    public void actualize() {
    }
}
