package Views;

import javafx.scene.text.Font;
import javafx.scene.text.Text;

class PlayerView extends Text {
    PlayerView(int x, int y, Integer id){
        super(x, y, id.toString());
        this.setFont(new Font(20));
    }
}
