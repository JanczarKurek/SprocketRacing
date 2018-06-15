package VisualPlayer;

import InGameResources.ResourceWallet;
import Players.Player;
import Settings.Settings;
import VisualBoard.VisualElement;
import VisualCards.VisualVehicle;
import VisualDice.VisualDiceBunch;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class VisualWallet implements VisualElement{
    private ResourceWallet wallet;
    static Image cogImage;

    private VisualVehicle veh;

    public void setVehicle(VisualVehicle veh){
        this.veh = veh;
    }

    static {
        try {
            cogImage = new Image(new FileInputStream(Settings.getSettings().getResourcesPath() + "Effects/cog.png"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public VisualWallet(ResourceWallet wallet){
        this.wallet = wallet;
    }


    @Override
    public Node draw() {
        Group ret = new Group();
        ret.getChildren().add(new ImageView(cogImage));
        Text text = new Text(String.valueOf(wallet.getGears()));
        text.setFont(new Font(40));
        text.setTranslateX(50);
        text.setTranslateY(30);
        text.setFill(Color.RED);
        ret.getChildren().add(text);
        VisualDiceBunch visualDiceBunch = new VisualDiceBunch(wallet.getDices());
        visualDiceBunch.setVehicle(veh);
        Node bunch = visualDiceBunch.draw();
        bunch.setTranslateX(100);
        ret.getChildren().add(bunch);
        return ret;
    }

    @Override
    public void actualize() {

    }
}
