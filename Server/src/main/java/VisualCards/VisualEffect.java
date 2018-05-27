package VisualCards;

import Settings.Settings;
import VisualBoard.VisualElement;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.security.spec.ECField;

public class VisualEffect implements VisualElement {
    private int effect;
    private Image image;

    public VisualEffect(int effect){
        this.effect = effect;
        try {
            Settings settings = Settings.getSettings();
            String resPref = settings.getResourcesPath();
            switch (effect) {
                case 0:
                    image = new Image(new FileInputStream(resPref+"Effects/silverWheel.png"));
                    break;
                case 1:
                    image = new Image(new FileInputStream(resPref+"Effects/goldWheel.png"));
                    break;
                case 2:
                    image = new Image(new FileInputStream(resPref+"Effects/cog.png"));
                    break;
                case 3:
                    image = new Image(new FileInputStream(resPref+"Effects/shield.png"));
                    break;
                case 4:
                    image = new Image(new FileInputStream(resPref+"Effects/shieldX.png"));
                    break;
                case 5:
                    image = new Image(new FileInputStream(resPref+"Effects/redCube.png"));
                    break;
                case 6:
                    image = new Image(new FileInputStream(resPref+"Effects/yellowCube.png"));
                    break;
                case 7:
                    image = new Image(new FileInputStream(resPref+"Effects/blueCube.png"));
                    break;
                case 8:
                    image = new Image(new FileInputStream(resPref+"Effects/redCubeOut.png"));
                    break;
                case 9:
                    image = new Image(new FileInputStream(resPref+"Effects/yellowCubeOut.png"));
                    break;
                case 10:
                    image = new Image(new FileInputStream(resPref+"Effects/blueCubeOut.png"));
                    break;
                case 11:
                    image = new Image(new FileInputStream(resPref+"Effects/slash.png"));
                    break;

            }
        }catch (Exception e){
            System.err.println("Image not found ");
        }
    }
    @Override
    public Node draw() {
        Node node = new ImageView(image);
        return node;
    }

    @Override
    public void actualize() {

    }
}
