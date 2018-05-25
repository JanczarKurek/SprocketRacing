package InGameResources.Dice;

import ErrorsAndExceptions.WrongColor;
import ErrorsAndExceptions.WrongMove;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;

public class DiceSlotsImpl implements DiceSlots {
    DiceSlotsImpl(int size, Dice.Color color){
        this.size = size;
        this.color = color;
    }
    private LinkedList<Dice> dice = new LinkedList<>();
    private int size;
    private Dice.Color color;
    @Override
    public boolean isFull() {
        return dice.size() == size;
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public ArrayList<Dice> getDice() {
        return new ArrayList<>(dice);
    }

    @Override
    public Dice getDice(int pos) throws WrongMove {
        try {
            return dice.get(pos);
        }catch(IndexOutOfBoundsException ex){
            throw new WrongMove();
        }
    }

    @Override
    public int decrement(int pos) throws WrongMove {
        try{
            if(dice.get(pos).getValue() == 1){
                dice.remove(pos);
                return 0;
            }else{
                dice.get(pos).setValue(dice.get(pos).getValue() - 1);
            }
        }catch(IndexOutOfBoundsException ex){
            throw new WrongMove();
        }
        return dice.get(pos).getValue();
    }

    @Override
    public void insert(Dice dice) throws WrongMove, WrongColor {
        tryInsert(dice);
        this.dice.push(dice);
    }

    @Override
    public void insertAll(Collection<Dice> dice) throws WrongMove, WrongColor {
        tryInsertAll(dice);
        this.dice.addAll(dice);
    }

    @Override
    public void tryInsert(Dice dice) throws WrongColor, WrongMove {
        if(dice.getColor() != color){
            throw new WrongColor("Wrong color, expecting " + color + " got " + dice.getColor());
        }
        if(isFull()){
            throw new WrongMove("Tried to put dice into full dice slot.");
        }
    }

    @Override
    public void tryInsertAll(Collection<Dice> dice) throws WrongMove, WrongColor {
        for(Dice d : dice)
            tryInsert(d);
        if(this.dice.size() + dice.size() > size)
            throw new WrongMove("Tried to put " + dice.size() + " dices into slots of current capacity " + (size - this.dice.size()));
    }
}
