package MapServer;

public interface GlobalEffect extends Effect {
    @Override
    default String getDescription() {
        return "Global effect of id="+getId()+".";
    }
}
