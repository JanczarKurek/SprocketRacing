package Cards;

import InGameResources.Dice.DiceSlots;
import misc.Cost;

public class VehicleCardData implements VehicleCard {
    private Cost cost;
    private int id;
    private String name;
    private VehicleCardEngine engine;

    public VehicleCardData(Cost cost, int id, String name, VehicleCardEngine engine, Joints joints) {
        this.cost = cost;
        this.id = id;
        this.name = name;
        this.engine = engine;
        this.joints = joints;
    }

    public VehicleCardData(){};

    public VehicleCardEngine getEngine() {
        return engine;
    }

    public void setCost(Cost cost) {
        this.cost = cost;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEngine(VehicleCardEngine engine) {
        this.engine = engine;
    }

    public Joints getJoints() {
        return joints;
    }

    static public class Joints{

        private boolean left;
        private boolean right;
        private boolean up;
        private boolean down;

        public Joints(boolean left, boolean right, boolean up, boolean down) {
            this.left = left;
            this.right = right;
            this.up = up;
            this.down = down;
        }

        public boolean isLeft() {
            return left;
        }

        public void setLeft(boolean left) {
            this.left = left;
        }

        public boolean isRight() {
            return right;
        }

        public void setRight(boolean right) {
            this.right = right;
        }

        public boolean isUp() {
            return up;
        }

        public void setUp(boolean up) {
            this.up = up;
        }

        public boolean isDown() {
            return down;
        }

        public void setDown(boolean down) {
            this.down = down;
        }
    }

    public void setJoints(boolean left, boolean right, boolean up, boolean down) {
        joints = new Joints(left, right, up, down);
    }

    private Joints joints;

    @Override
    public Cost getCost() {
        return cost;
    }

    @Override
    public int getID() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    public DiceSlots getDiceSlots(){
        return engine.getDiceSlots();
    }
}
