package Dice;

import ErrorsAndExceptions.WrongColor;
import ErrorsAndExceptions.WrongMove;

import java.util.ArrayList;
import java.util.Collection;

public interface DiceSlots{
    boolean isFull();
    int getSize();
    ArrayList<Dice> getDice();
    Dice getDice(int pos) throws WrongMove;
    int decrement(int pos) throws WrongMove;
    Dice extract(int pos) throws WrongMove;
    void insert(Dice dice) throws WrongMove, WrongColor;
    void insertAll(Collection<Dice> dice) throws WrongMove, WrongColor;
}
