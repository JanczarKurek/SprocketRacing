package Cards;

import misc.Effect;

import java.util.Collection;

public interface  VehicleCard extends Card {

    @Override
    default String getDescription() {
        return "Vehicle card no " + getID()+ " name="+getName();
    }

}
