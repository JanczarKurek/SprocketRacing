package InGameResources.Dice;

import ErrorsAndExceptions.WrongColor;
import ErrorsAndExceptions.WrongMove;

import java.util.Collection;

public interface DiceSlots extends DiceWallet{
    boolean isFull();
    int getSize();
    void tryInsert(Dice dice) throws WrongMove, WrongColor;
    void tryInsertAll(Collection<Dice> dice) throws WrongMove, WrongColor;
    Dice.Color getColor();
}
