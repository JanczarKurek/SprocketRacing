package Table;

import Cards.Card;
import Cards.Hand;
import ErrorsAndExceptions.WrongMove;

public interface TableController {
    public void voteEndPhase() throws WrongMove;

    //Actions during DRAW
    Hand getHand() throws WrongMove;
    void passHand(Hand playersHand) throws WrongMove;
    void discard(Card card);
}
