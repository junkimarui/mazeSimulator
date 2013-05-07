package problemSet1;

public class Robot implements Cloneable {
    private Maze maze;
    private State state;
    public int randomStart;
    public static int ACTION_NUM = 4;
    public State getState() {
        return state;
    }
    public Robot (Maze maze) {
        this(maze, 0);
    }
    public Robot (Maze maze, int randomStart) {
        this.maze = maze;
        this.state = new State(0, 0, State.NORTH);
        this.randomStart = randomStart;
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

    public Tuple<Object, Object> moveStraightAhead() {
        Tuple<Object, Object> nextBlocks = this.lookForward();
        if (nextBlocks.right.equals(Maze.CHAR_WALL))
            return new Tuple<Object, Object>('B','U');
        moveAhead();
        return moveAhead();
    }
    /*
    public Tuple<Object, Object> moveStraightAhead() {
        Tuple<Object, Object> nextBlocks = lookForward();
        if (nextBlocks.right.equals(Map.CHAR_WALL))
            return new Tuple<Object, Object>('B','U');
        while (!nextBlocks.right.equals(Map.CHAR_WALL)) {
            moveAhead();
            nextBlocks = lookForward();
        }
        return moveAhead();
    }
     */
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
        case 3:
            return moveStraightAhead();
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