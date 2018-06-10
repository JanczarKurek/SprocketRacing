package VisualCards;

import Cards.CardEffect;
import Cards.OnCardEffects.GetResourceEffect;
import Cards.OnCardEffects.HealEffect;
import Cards.OnCardEffects.MoveEffect;
import Cards.OnCardEffects.VentEffect;
import InGameResources.Dice.Dice;
import InGameResources.ResourceWallet;
import MapServer.DamageEffect;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;
import misc.Effect;

import static org.junit.Assert.*;

public class VisualEffectPackTest extends Application{
    @Override
    public void start(Stage primaryStage) throws Exception {
        Effect e1 = new GetResourceEffect(new ResourceWallet(1, 2, 3, 4));
        Effect e2 = new DamageEffect(3);
        Effect e3 = new VentEffect(2, Dice.Color.YELLOW);
        Effect e4 = new HealEffect(5);
        Effect e5 = new MoveEffect(3);
        Effect[][] test = {{e1}, {e2, e3}, {e4, e5}};
        VisualEffectPack pack = new VisualEffectPack(new CardEffect(test));
        Group group = new Group();
        Node node = pack.draw();
        group.getChildren().add(node);
        primaryStage.setScene(new Scene(group));
        primaryStage.show();
    }

    public static void main(String[] args){
        launch(args);
    }
}