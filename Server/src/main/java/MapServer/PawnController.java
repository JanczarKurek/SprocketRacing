package MapServer;

import ErrorsAndExceptions.WrongMove;

import java.util.Collection;

public interface PawnController {
    public Collection<OnPassEffect> move(Path path) throws WrongMove;

    public Collection<OnStayEffect> useEffectsOnField();
}
