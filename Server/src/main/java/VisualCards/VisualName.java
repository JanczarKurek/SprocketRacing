package VisualCards;

import VisualBoard.VisualElement;
import javafx.scene.Node;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class VisualName implements VisualElement {
    private String name;

    VisualName(String name){
        this.name=name;
    }
    public Node draw(){
        Text text=new Text();
        text.setFont(new Font(20));
        text.setText(name);
        return text;
    }

    public void actualize(){

    }
}
