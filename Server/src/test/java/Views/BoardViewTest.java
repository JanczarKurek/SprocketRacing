package Views;

import MapServer.*;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.stage.Stage;
import org.junit.Test;

import java.util.Collection;

public class BoardViewTest{
    @Test
    public void testBasic() throws InterruptedException{
        Field field1 = new Field() {
            @Override
            public Collection<Effect> getEffects() {
                return null;
            }

            @Override
            public Collection<OnStayEffect> getOnStayEffects() {
                return null;
            }

            @Override
            public Collection<OnPassEffect> getOnPassEffects() {
                return null;
            }

            @Override
            public void addEffect(Effect effect) {

            }

            @Override
            public void clearOld() {

            }

            @Override
            public int getId() {
                return 0;
            }
        };

        Field field2 = new Field() {
            @Override
            public Collection<Effect> getEffects() {
                return null;
            }

            @Override
            public Collection<OnStayEffect> getOnStayEffects() {
                return null;
            }

            @Override
            public Collection<OnPassEffect> getOnPassEffects() {
                return null;
            }

            @Override
            public void addEffect(Effect effect) {

            }

            @Override
            public void clearOld() {

            }

            @Override
            public int getId() {
                return 1;
            }
        };
        Field field3 = new Field() {
            @Override
            public Collection<Effect> getEffects() {
                return null;
            }

            @Override
            public Collection<OnStayEffect> getOnStayEffects() {
                return null;
            }

            @Override
            public Collection<OnPassEffect> getOnPassEffects() {
                return null;
            }

            @Override
            public void addEffect(Effect effect) {

            }

            @Override
            public void clearOld() {

            }

            @Override
            public int getId() {
                return 2;
            }
        };
        Field field4 = new Field() {
            @Override
            public Collection<Effect> getEffects() {
                return null;
            }

            @Override
            public Collection<OnStayEffect> getOnStayEffects() {
                return null;
            }

            @Override
            public Collection<OnPassEffect> getOnPassEffects() {
                return null;
            }

            @Override
            public void addEffect(Effect effect) {

            }

            @Override
            public void clearOld() {

            }

            @Override
            public int getId() {
                return 3;
            }
        };
        BoardField boardField1 = new BoardField(field1);
        BoardField boardField2 = new BoardField(field2);
        BoardField boardField3 = new BoardField(field3);
        BoardField boardField4 = new BoardField(field4);
        boardField1.addNext(boardField2);
        boardField2.addPrev(boardField1);
        boardField1.addNext(boardField3);
        boardField3.addPrev(boardField1);
        boardField4.addPrev(boardField3);
        boardField4.addPrev(boardField2);
        boardField2.addNext(boardField4);
        boardField3.addNext(boardField4);
        BoardStructure struct = new BoardStructure();
        struct.setStart(boardField1);
        struct.setEnd(boardField4);

        BoardView View = new BoardView();
        View.setStructure(struct);

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