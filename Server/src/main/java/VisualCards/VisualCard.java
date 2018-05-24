package VisualCards;

import Cards.Card;
import Cards.VehicleCard;
import Cards.VehicleCardData;
import VisualBoard.VisualElement;
import VisualCards.VisualJoint;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import misc.Cost;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class VisualCard implements VisualElement {

    private Image background;
    private Card card;
    private int height;
    private int width;
    private ArrayList<VisualJoint> joints = new ArrayList<>();
    private VisualName name;
    private Cost cost;

    VisualCard(Card card) throws FileNotFoundException{
        this.card = card;
        if(card instanceof VehicleCard) {
            background = new Image(new FileInputStream("SprocketRacing/Server/src/test/resources/VehicleCard/CardVehicle.png"));
            File file = new File("SprocketRacing/Server/src/test/resources/VehicleCard/VehicleCardDescription.txt");
            FileInputStream stream = new FileInputStream(file);
            Scanner fileReader = new Scanner(stream);
            setXY(fileReader.nextInt(), fileReader.nextInt());
        }
        name = new VisualName(card.getName());
        cost = card.getCost();

    }

    private void setXY(int x, int y){
        width = x;
        height = y;
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
        if(card instanceof VehicleCard){
            VehicleCardData cardData= (VehicleCardData) card;
            //setCardVehicleEngine???
           VehicleCardData.Joints joint = cardData.getJoints();
            if(joint.isLeft()) {
                joints.add(new VisualJoint(true, false, false, false));
            }
            if(joint.isRight()) {
                joints.add(new VisualJoint(false, true, false, false));
            }
            if(joint.isUp()) {
                joints.add(new VisualJoint(false, false, true, false));
            }
            if(joint.isDown()) {
                joints.add(new VisualJoint(false, false, false, true));
            }
        }
        return group;
    }

    @Override
    public void actualize() {
    }
}
