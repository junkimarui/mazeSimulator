package problemSet1;

public class Robot implements Cloneable {
    private Maze maze;
    private State state;
    public static int ACTION_NUM = 3;
    public State getState() {
        return state;
    }
    public Robot (Maze maze) {
        this.maze = maze;
        this.state = new State(0, 0, State.NORTH);
        maze.placeRobot(this);
    }
    public void turnLeft() {
        maze.turnRobotLeft(this);
    }
    public void turnRight() {
        maze.turnRobotRight(this);
    }
    public void randomlyReorient() {
        int rand = (int)(Math.random()*4);
        for (int i = 0; i< rand; i++) {
            turnLeft();
        }
    }
    public Tuple<Object,Object> lookForward() {
        return maze.tellRobotForward(this);
    }
    public Tuple<Object, Object> moveAhead() {
        return maze.moveRobotAhead(this);
    }
    
    public Tuple<Object,Object> action(int actionID) {
        switch(actionID) {
        case 0:
            turnLeft();
            return null;
        case 1:
            turnRight();
            return null;
        case 2:
            return moveAhead();
        default:
            return null;
        }
    }

    @Override
    public Robot clone() {  
        try {  
            Robot clonedRobot = (Robot)super.clone();  
            clonedRobot.state = this.state.clone();
            return clonedRobot;
        } catch (CloneNotSupportedException e) {
            return null;  
        }  
    } 
}