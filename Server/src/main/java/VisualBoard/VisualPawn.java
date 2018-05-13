package VisualBoard;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;

public class VisualPawn implements VisualElement {
    private Image pawnImage;

    public VisualPawn(Image pawnImage) {
        this.pawnImage = pawnImage;
    }

    @Override
    public Node draw(){
        ImageView imageView = new ImageView();
        imageView.setImage(pawnImage);
        return imageView;
    }

    @Override
    public void actualize() {

    }
}
