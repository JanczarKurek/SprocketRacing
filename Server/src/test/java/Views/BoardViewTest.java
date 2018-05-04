package Views;

import MapServer.*;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.junit.Test;

import java.io.File;
import java.util.Collection;

public class BoardViewTest{
    @Test
    public void testBasic() throws InterruptedException{
        BoardView View = new BoardView();
        String imagePath = "";
        File description = new File("");
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