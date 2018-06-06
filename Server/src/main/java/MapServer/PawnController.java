package MapServer;

import ErrorsAndExceptions.WrongMove;
import misc.Effect;

import java.util.Collection;

public interface PawnController {
    Collection<Effect> move(Path path) throws WrongMove;

    Collection<OnStayEffect> useEffectsOnField();
}
