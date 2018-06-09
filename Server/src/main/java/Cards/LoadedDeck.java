package Cards;

import java.util.*;
import java.io.*;

import static Cards.CardFactory.ReadVehicleCardDataFromFile.readLoadedCard;
import VisualCards.*;

public class LoadedDeck {
    Deck deck;
    LinkedList<Cards.LoadedCard> loadedCards = new LinkedList<>();
    HashMap<Cards.VehicleCardData, VisualCard> map = new HashMap<>();

    static int idGen = 0;
    static String[] names = {"tempus", "fugit", "aeternitas", "manet"};

    private static boolean isOk(String fileName) {
        int s = fileName.length();
        if (s > 5 && fileName.substring(s - 5, s - 1).equals(".json"))
            return true;
        return false;
    }

    public LoadedDeck(String pathToFolder) {
        File folder = new File(pathToFolder);
        File[] files = folder.listFiles();
        ArrayList<String> fileNames = new ArrayList<>();

        for (File f : files)
            if (isOk(f.getName()))
                loadedCards.add(readLoadedCard(f.getName()));

        deck = new Deck(idGen, names[idGen % 4]);
        idGen++;
        for (LoadedCard loadedCard : loadedCards) {
            deck.put(loadedCard.getVehicleCardData());
            map.put(loadedCard.getVehicleCardData(), loadedCard.getVisualCard());
        }
    }

    public VisualCard getVisualCard(Cards.VehicleCardData vehicleCardData) {
        return map.get(vehicleCardData);
    }

    public Cards.Deck getDeck() {
        return deck;
    }
}
