package Views;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.stage.Stage;
import org.junit.Test;
import java.io.File;

public class BoardViewTest{
    @Test
    public void testBasic() throws InterruptedException{
        BoardView View = new BoardView();
        String imagePath = "https://github.com/JanczarKurek/SprocketRacing/blob/Board/Server/src/test/java/Views/GraphicDescription/board.jpg";
        File description = new File("https://github.com/JanczarKurek/SprocketRacing/blob/Board/Server/src/test/java/Views/GraphicDescription/description.txt");
        View.setDescription( imagePath, description);

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