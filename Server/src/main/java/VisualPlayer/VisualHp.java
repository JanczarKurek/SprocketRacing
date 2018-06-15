package VisualPlayer;

import Players.HpBar;
import Settings.Settings;
import VisualBoard.VisualElement;
import VisualCards.ViewManager;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;

public class VisualHp implements VisualElement{
    private HpBar hpBar;
    private Application app;
    private int player;

    public void setApp(Application app){
        this.app = app;
    }
    public void setPlayer(int player){
        this.player = player;
    }
    private static HashMap<String, Image> images = new HashMap<>();
    static {
        String pref = Settings.getSettings().getResourcesPath();
        try {
            images.put("Shield", new Image(new FileInputStream(pref + "Effects/shield.png")));
            images.put("ShieldX", new Image(new FileInputStream(pref + "Effects/shieldX.png")));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public VisualHp(HpBar hpBar){
        this.hpBar = hpBar;
    }


    @Override
    public Node draw() {
        Group ret = new Group();
        Node base;
        ImageView image;
        if(hpBar.getHp() < 0){
            base = new Rectangle(80, 42, Color.RED);
            ret.getChildren().add(base);
            image = new ImageView(images.get("ShieldX"));
        }else{
            base = new Rectangle(80, 42, Color.BLUE);
            ret.getChildren().add(base);
            image = new ImageView(images.get("Shield"));
        }
        ret.getChildren().add(image);
        Text text = new Text(String.valueOf(Math.abs(hpBar.getHp())));
        if(hpBar.getHp()==0)
            text.setText(String.valueOf(Math.abs(hpBar.getHp())) + "\n You shoud remove "+ hpBar.getHp() +" cards");
        text.setFont(new Font(20));
        text.setTranslateX(55);
        text.setTranslateY(30);
        ret.getChildren().add(text);
        Button damage = new Button("DAMAGE");
        damage.setOnMouseClicked(event -> {
            ((ViewManager)app).visualVehicle(player);
        });
        damage.setTranslateY(70);
        damage.setTranslateX(55);
        ret.getChildren().add(damage);
        return ret;
    }

    @Override
    public void actualize() {

    }
}
