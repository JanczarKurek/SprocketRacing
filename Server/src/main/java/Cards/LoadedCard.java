package Cards;

import VisualCards.*;
import InGameResources.Dice.*;
public class LoadedCard {
    Cards.VehicleCardData vehicleCardData;
    VisualCard visualCard;

    public LoadedCard(Cards.VehicleCardData vehicleCardData) {
        this.vehicleCardData = vehicleCardData;
        try {
            this.visualCard = new VisualCard(vehicleCardData);
        } catch (java.io.FileNotFoundException ignored) {

        }
    }

    public VisualCards.VisualCard getVisualCard() {
        return visualCard;
    }

    public Cards.VehicleCardData getVehicleCardData() {
        return vehicleCardData;
    }
}
