package Cards;

import misc.Cost;

public interface Card {
    Cost getCost();
    default String getDescription(){
        return "Card no "+getID() + " name="+getName();
    };
    int getID();
    String getName();
}
