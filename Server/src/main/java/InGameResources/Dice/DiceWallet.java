package InGameResources.Dice;

import ErrorsAndExceptions.WrongColor;
import ErrorsAndExceptions.WrongMove;

import java.util.ArrayList;
import java.util.Collection;

public interface DiceWallet {
    ArrayList<Dice> getDice();
    Dice getDice(int pos) throws WrongMove;
    int decrement(int pos) throws WrongMove;
    void insert(Dice dice) throws WrongMove, WrongColor;
    void insertAll(Collection<Dice> dice) throws WrongMove, WrongColor;
}
