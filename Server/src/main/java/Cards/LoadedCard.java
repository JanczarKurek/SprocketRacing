package Cards;

import VisualCards.*;
import InGameResources.Dice.*;
public class LoadedCard {
    Cards.VehicleCardData vehicleCardData;
    VisualCard visualCard;

    public LoadedCard(Cards.VehicleCardData vehicleCardData) {
        this.vehicleCardData = vehicleCardData;
       /* this.visualCard = new VisualCards.VisualCard(vehicleCardData,
                ((DiceSlotsImpl) vehicleCardData.getDiceSlots()).getColor(),
                ((DiceSlotsImpl) vehicleCardData.getDiceSlots()).getSize(),
                vehicleCardData.getEngine().
        )*/
    }

    public VisualCards.VisualCard getVisualCard() {
        return visualCard;
    }

    public Cards.VehicleCardData getVehicleCardData() {
        return vehicleCardData;
    }
}
