package ErrorsAndExceptions;

public class WrongMove extends Exception {
    public WrongMove(){};
    public WrongMove(String msg){
        super(msg);
    }

}
