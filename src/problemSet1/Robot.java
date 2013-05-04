package problemSet1;

public class Robot {
    private Map map;
    private State state;
    public static int ACTION_NUM = 3;
    public State getState() {
        return state;
    }
    public Robot (Map map) {
        this.map = map;
        this.state = new State(0, 0, State.NORTH);
        map.placeRobot(this);
    }
    public void turnLeft() {
        map.turnRobotLeft(this);
    }
    public void turnRight() {
        map.turnRobotRight(this);
    }
    public void randomlyReorient() {
        int rand = (int)(Math.random()*4);
        for (int i = 0; i< rand; i++) {
            turnLeft();
        }
    }
    public Tuple<Object,Object> lookForward() {
        return map.tellRobotForward(this);
    }
    public Tuple<Object, Object> moveAhead() {
        return map.moveRobotAhead(this);
    }
    
    public Tuple<Object,Object> action(int actionID) {
        if (actionID == 0) {
            turnLeft();
            return null;
        }
        else if (actionID == 1) {
            turnRight();
            return null;
        }
        else if (actionID == 2) {
            return moveAhead();
        }
        return null;
    }

}