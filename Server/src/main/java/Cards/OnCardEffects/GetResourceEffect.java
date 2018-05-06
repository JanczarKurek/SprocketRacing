package Cards.OnCardEffects;

import InGameResources.ResourceWallet;
import misc.Effect;

public class GetResourceEffect implements Effect {

    ResourceWallet resourceWallet;

    public GetResourceEffect(ResourceWallet resourceWallet) {
        this.resourceWallet = resourceWallet;
    }

    @Override
    public void execute(Object who) {

    }

    @Override
    public String getDescription() {
        return "Get resources "+ resourceWallet;
    }

    @Override
    public int duration() {
        return 0;
    }

    @Override
    public int getId() {
        return 2140;
    }
}
