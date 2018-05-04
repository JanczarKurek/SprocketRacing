package Views;

import MapServer.BoardField;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class FieldView extends Rectangle {
    private BoardField field;
    FieldView(int x, int y, BoardField field){
        this.field=field;
        setWidth(BoardView.FIELD_WIDTH);
        setHeight(BoardView.FIELD_HEIGHT);
        relocate(x,y);
        setFill(Color.LIGHTGRAY);
    }
    private BoardField getField(){
        return field;
    }
    public boolean equals(Object o){
        try{
            FieldView fieldView = (FieldView) o;
            return field.getId() == fieldView.getField().getId();
        }catch (Exception e){
            return false;
        }

    }
}
