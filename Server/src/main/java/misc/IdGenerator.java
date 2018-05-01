package misc;

public class IdGenerator {
    private static int maxId = 0;
    public int genId(){
        return maxId++;
    }
}
