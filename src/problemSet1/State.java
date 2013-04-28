package problemSet1;

public class State {
    public static final int NORTH = 0;
    public static final int EAST = 1;
    public static final int SOUTH = 2;
    public static final int WEST = 3;
    public static final int LEFT = -1;
    public static final int RIGHT = 1;
    
    public int x;
    public int y;
    public int direction;
    public State (int x, int y, int direction) {
        this.x = x;
        this.y = y;
        this.direction = direction;
    }
    public State change(State diff) {
        this.x += diff.x;
        this.y += diff.y;
        this.direction += diff.direction;
        if (this.direction < 0)
            this.direction += 4;
        else
            this.direction = this.direction % 4;
        return this;
    }
    
    public boolean equalsPosition(State s) {
        return this.x == s.x && this.y == s.y;
    }
}
