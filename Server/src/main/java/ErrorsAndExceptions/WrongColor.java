package ErrorsAndExceptions;

public class WrongColor extends Exception{
    public WrongColor(){};
    public WrongColor(String msg){
        super(msg);
    }
}
