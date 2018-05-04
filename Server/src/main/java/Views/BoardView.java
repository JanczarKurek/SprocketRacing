package Views;

import MapServer.*;
import javafx.application.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.*;

import java.util.Collection;

public class BoardView extends Application {
    public static final int FIELD_WIDTH = 50;
    public static final int FIELD_HEIGHT = 50;
    private static final int FIELD_SPACE = 10;


    public void setStructure(BoardStructure structure) {
        this.structure = structure;
    }

    private Group fieldGroup= new Group();
    private int widthLastField;
    private BoardStructure structure;

    private void viewFields(int x, int y, BoardField field, BoardField last){
        int i=0;
        for(BoardField nextField : field.getNextFields()) {
            viewFields(x+FIELD_SPACE+FIELD_WIDTH, y+(FIELD_SPACE+FIELD_HEIGHT)*i, nextField, last);
            i++;
        }
        FieldView fieldView = new FieldView(x, y, field);
        if(field==last)
            widthLastField=x;
        if(!fieldGroup.getChildren().contains(fieldView))
            fieldGroup.getChildren().add(fieldView);
    }

    private Parent create(){
        Pane root = new Pane();
        viewFields(20, 20, structure.getStart(), structure.getEnd());
        Text startText=new Text(25, 15, "START");
        startText.setFont(new Font(15));
        root.getChildren().add(startText);
        root.getChildren().addAll(fieldGroup);
        Text endText=new Text(widthLastField+5, 15, "META");
        endText.setFont(new Font(15));
        root.getChildren().add(endText);
        return root;
    }

    public void start(Stage primaryStage){
        Scene scene=new Scene(create());
        primaryStage.setTitle("Sprocket Racing Board");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

}
