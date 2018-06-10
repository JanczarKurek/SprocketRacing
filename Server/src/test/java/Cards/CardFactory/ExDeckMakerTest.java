package Cards.CardFactory;

import static org.junit.Assert.*;
import Cards.*;
import java.util.*;
import java.io.File;

public class ExDeckMakerTest {

    @org.junit.Test
    public void test1() {
        for (int i = 1; i < 5; i++) {

            Cards.LoadedDeck loadedDeck = new Cards.LoadedDeck("src/main/java/Files/Deck" + i);
            Deck deck = loadedDeck.getDeck();

            assertEquals(123, deck.size());

            for (Object card : deck) assertNotNull(card);
            for (Object o : deck) {
                VehicleCardData card = (VehicleCardData) o;
                System.out.println(card.getJoints().isDown());
                System.out.println(card.getJoints().isUp());
            }

            for (Object o : deck) {
                VehicleCardData card = (VehicleCardData) o;
                System.out.println(card.getID());
            }


        }
    }

}