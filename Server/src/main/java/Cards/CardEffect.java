package Cards;

import misc.Effect;

import java.util.ArrayList;

public class CardEffect {
    private ArrayList<ArrayList<Effect>> effects;
    public ArrayList<Effect> chooseEffect(int i){
        return effects.get(i);
    }
}
