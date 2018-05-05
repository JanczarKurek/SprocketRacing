package Views;

import MapServer.BoardState;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.stage.Stage;
import org.junit.Test;
import java.util.ArrayList;

public class BoardViewTest{
    @Test
    //Czterech zawodników na pozycji startowej
    public void testBasic() throws InterruptedException{
        BoardView View = new BoardView();
        String imagePath = "https://github.com/JanczarKurek/SprocketRacing/blob/Board/Server/src/test/java/Views/GraphicDescription/board.jpg?raw=true";
        String descriptionPath = "https://raw.githubusercontent.com/JanczarKurek/SprocketRacing/Board/Server/src/test/java/Views/GraphicDescription/description.txt";
        View.setDescription( imagePath, descriptionPath);

        ArrayList<Integer> PlayersList= new ArrayList<>();
        for(int i=0; i<4; i++){
            PlayersList.add(i);
        }
        BoardState state = new BoardState(PlayersList, 0);
        View.setState(state);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                new JFXPanel();
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        View.start(new Stage());
                    }
                });
            }
        });
        thread.start();
        thread.sleep(10000);
    }
}