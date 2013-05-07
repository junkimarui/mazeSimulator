package problemSet1;

public class Robot implements Cloneable {
    private Maze maze;
    private State state;
    public boolean randomStart;
    public static int ACTION_NUM = 5;
    public State getState() {
        return state;
    }
    public Robot (Maze maze) {
        this(maze, false);
    }
    public Robot (Maze maze, boolean randomStart) {
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
    public Tuple<Object, Object> turnLeftAndGo() {
        turnLeft();
        return moveAhead();
    }
    public Tuple<Object, Object> turnRightAndGo() {
        turnRight();
        return moveAhead();
    }
    public Tuple<Object, Object> turnBackAndGo() {
        turnLeft(); turnLeft();
        return moveAhead();
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
        if (nextBlocks.right.equals(Maze.CHAR_WALL))
            return new Tuple<Object, Object>('B','U');
        while (!nextBlocks.right.equals(Maze.CHAR_WALL)) {
            moveAhead();
            nextBlocks = lookForward();
        }
        return moveAhead();
    }
     */
    public Tuple<Object,Object> action(int actionID) {
        switch(actionID) {
        case 0:
            return turnLeftAndGo();
        case 1:
            return turnRightAndGo();
        case 2:
            return turnBackAndGo();
        case 3:
            return moveAhead();
        case 4:
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