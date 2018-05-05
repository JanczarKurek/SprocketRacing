package Cards;

import misc.Effect;

import java.util.ArrayList;

public class CardEffect {
    ArrayList<ArrayList<Effect>> effects;
    ArrayList<Effect> chooseEffect(int i){
        return effects.get(i);
    }
}
