package Cards;

import misc.Cost;

public class VehicleCardData implements VehicleCard {
    private Cost cost;
    private int id;
    private String name;
    private class Joints{
        private boolean left;
        private boolean right;
        private boolean up;
        private boolean down;

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
    public Joints joints;

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

}
