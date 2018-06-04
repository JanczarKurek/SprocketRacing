package VisualCards;

import Cards.Card;
import Cards.VehicleCard;
import Cards.VehicleCardData;
import Cards.VehicleCardEngine;
import InGameResources.Dice.Dice;
import InGameResources.Dice.DiceSlots;
import VisualBoard.VisualElement;
import Settings.Settings;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.transform.Scale;
import misc.Cost;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

public class VisualCard implements VisualElement {

    private Image background;
    private Card card;
    private int height;
    private int width;
    private ArrayList<VisualJoint> joints = new ArrayList<>();
    private VisualName name;
    private Cost cost;
    private DiceSlots slots;
    private VehicleCardEngine engine;
    private int usagePipCost;
    private LinkedList<Integer> effects;
    private Dice.Color color;
    private int slotsNumber;

    VisualCard(Card card) throws FileNotFoundException{
        this.card = card;
        if(card instanceof VehicleCard) {
            Settings settings = Settings.getSettings();
            String resPref = settings.getResourcesPath();
            background = new Image(new FileInputStream(resPref+"VehicleCard/CardVehicle.png"));
            File file = new File(resPref+"VehicleCard/VehicleCardDescription.txt");
            FileInputStream stream = new FileInputStream(file);
            Scanner fileReader = new Scanner(stream);
            setXY(fileReader.nextInt(), fileReader.nextInt());
            slots = ((VehicleCardData)card).getDiceSlots();
            effects = new LinkedList<>();
        }
        name = new VisualName(card.getName());
        cost = card.getCost();

    }

    private void setXY(int x, int y){
        width = x;
        height = y;
    }

    public void setUsagePipCost(int cost){
        usagePipCost = cost;
    }

    public void addEffect(int effect){
        effects.add(effect);
    }

    public void setColorSlot(Dice.Color color){
        this.color = color;
    }

    public void setNumberSlots(int numberSlots){
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

    public int redCost(){
        return cost.getRed();
    }

    public int yellowCost(){
        return cost.getYellow();
    }

    public int blueCost(){
        return cost.getBlue();
    }

    public int cogsCost(){
        return cost.getCogs();
    }

    public DiceSlots getSlots(){
        return slots;
    }

    public int getUsagePipCost(){
        return usagePipCost;
    }

    public Dice.Color getColorSlot(){
        return color;
    }

    public int getSlotsNumber(){
        return slotsNumber;
    }

    public Card getCard(){
        return card;
    }
    public ArrayList<VisualJoint> getJoints(){
        return joints;
    }

    public VisualName getName(){
        return name;
    }
    @Override
    public Node draw(){
        Group group = new Group();
        ImageView imageView = new ImageView();
        imageView.setImage(background);
        group.getChildren().add(imageView);
        if(card instanceof VehicleCard) {
            VehicleCardData cardData = (VehicleCardData) card;
            //setCardVehicleEngine???
            VehicleCardData.Joints joint = cardData.getJoints();
            if (joint.isLeft()) {
                joints.add(new VisualJoint(true, false, false, false));
            }
            if (joint.isRight()) {
                joints.add(new VisualJoint(false, true, false, false));
            }
            if (joint.isUp()) {
                joints.add(new VisualJoint(false, false, true, false));
            }
            if (joint.isDown()) {
                joints.add(new VisualJoint(false, false, false, true));
            }
        }
        return group;
    }

    @Override
    public void actualize() {
    }
}
