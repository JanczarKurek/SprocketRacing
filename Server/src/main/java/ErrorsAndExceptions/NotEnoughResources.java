package ErrorsAndExceptions;

public class NotEnoughResources extends Exception {
    NotEnoughResources(){};
    public NotEnoughResources(String msg){
        super(msg);
    }
}
