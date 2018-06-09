package Cards;

import misc.Effect;

import java.util.*;

public class CardEffect {
    private ArrayList<ArrayList<Effect>> effects;
    public ArrayList<Effect> chooseEffect(int i){
        return effects.get(i);
    }

    public CardEffect(Collection<ArrayList<misc.Effect>> collection) {
        effects = new java.util.ArrayList<>();
        for (ArrayList<misc.Effect> a : collection)
            effects.add(new java.util.ArrayList<>(a));
    }

    public CardEffect(Effect e[][]){
        for(Effect[] k : e){
            effects.add(new ArrayList<>(Arrays.asList(k)));
        }
    }
}