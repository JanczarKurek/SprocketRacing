package Cards.OnCardEffects;

import InGameResources.ResourceWallet;
import Players.Player;
import misc.Effect;

public class GetResourceEffect implements Effect {

    private ResourceWallet resourceWallet;

    public GetResourceEffect(ResourceWallet resourceWallet) {
        this.resourceWallet = resourceWallet;
    }

    @Override
    public void execute(Player who) {
        who.getMyWallet().transferFrom(resourceWallet);
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
