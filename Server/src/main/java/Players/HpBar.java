package Players;

import Cards.OnCardEffects.GetResourceEffect;
import InGameResources.ResourceWallet;
import misc.Effect;
import misc.EmptyEffect;

public class HpBar {
    private int hp;
    private final int UPPER_BOUND;
    private final int LOWER_BOUND;
    public HpBar(int ub, int lb){
        UPPER_BOUND = ub;
        LOWER_BOUND = lb;
    }

    public int getHp() {
        return hp;
    }

    public Effect hitFor(int dmg){
        if(hp - dmg < LOWER_BOUND){
            Effect ret = new PartBreak(LOWER_BOUND - hp + dmg);
            hp = LOWER_BOUND;
            return ret;
        }else{
            hp -= dmg;
            return new EmptyEffect();
        }
    }

    public Effect healFor(int h){
        if(hp + h > UPPER_BOUND){
            Effect ret = new GetResourceEffect(new ResourceWallet(0, 0, 0, hp + h - UPPER_BOUND));
            hp = UPPER_BOUND;
            return ret;
        }else{
            hp += h;
            return new EmptyEffect();
        }
    }

    public Effect refresh(){
        if(hp < 0){
            Effect ret = new PartBreak(-hp);
            hp = 0;
            return ret;
        }
        return new EmptyEffect();
    }
}
