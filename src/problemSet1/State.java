package problemSet1;

public class State implements Cloneable {
    public static final int NORTH = 0;
    public static final int EAST = 1;
    public static final int SOUTH = 2;
    public static final int WEST = 3;
    public static final int LEFT = -1;
    public static final int RIGHT = 1;
    /*  '↑','→','↓','←' */
    public static final char[] CHAR_ORIENTATION = {'\u2191','\u2192','\u2193','\u2190'};
    public static final int[] DIFF_X = {0,1,0,-1};
    private static final int[] DIFF_Y = {-1,0,1,0};
    
    public int x;
    public int y;
    public int orientation;
    public State (int x, int y, int direction) {
        this.x = x;
        this.y = y;
        this.orientation = direction;
    }
    public State change(State diff) {
        this.x += diff.x;
        this.y += diff.y;
        this.orientation += diff.orientation;
        if (this.orientation < 0)
            this.orientation += 4;
        else
            this.orientation = this.orientation % 4;
        return this;
    }
    
    public int getHeadingDiffX() {
        return DIFF_X[this.orientation];
    }
    
    public int getHeadingDiffY() {
        return DIFF_Y[this.orientation];
    }
    
    public boolean equalsPosition(State s) {
        return this.x == s.x && this.y == s.y;
    }
    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        else if (!(obj instanceof State)) return false;
        final State o = (State)obj;
        return this.x == o.x && this.y == o.y && this.orientation == o.orientation;
    }
    @Override
    public int hashCode() {
        return this.x * 1000 + this.y * 10 + this.orientation;
    }
    @Override
    public State clone() {  
        try {  
            return (State)super.clone();  
        } catch (CloneNotSupportedException e) {
            return null;  
        }  
    }  
}
