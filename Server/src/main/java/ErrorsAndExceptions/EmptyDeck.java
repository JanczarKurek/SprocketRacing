package ErrorsAndExceptions;

public class EmptyDeck extends Exception{
    public EmptyDeck(){};

    public EmptyDeck(String msg){
        super(msg);
    }
}
