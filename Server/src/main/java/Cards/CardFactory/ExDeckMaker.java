package Cards.CardFactory;

import java.io.*;

public class ExDeckMaker {
    static void deckMaker(String deckName) {
        File folder = new File(deckName);
        for (int cardIndex = 0; cardIndex < 123; cardIndex++) {
            //File
        }
    }

    public static void main(String[] Args) {
        deckMaker("src/main/java/Files/Deck1");
        deckMaker("src/main/java/Files/Deck2");
        deckMaker("src/main/java/Files/Deck3");
        deckMaker("src/main/java/Files/Deck4");
    }
}
