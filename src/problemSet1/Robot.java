package problemSet1;

public class Robot {
    private Map map;
    private State state;
    public State getState() {
        return state;
    }
    public Robot (Map map) {
        this.map = map;
        this.state = new State(0, 0, State.NORTH);
    }
    public void turnLeft() {
        map.turnRobotLeft(this);
    }
    public void turnRight() {
        map.turnRobotRight(this);
    }
    public Tuple<Integer,Integer> lookForward() {
        return map.tellRobotForward(this);
    }
    public boolean moveAhead() {
        return map.moveRobotAhead(this);
    }
}
