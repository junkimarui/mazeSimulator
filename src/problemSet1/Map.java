package problemSet1;

import java.io.*;
import java.util.*;

public class Map {
    public static final char CHAR_WALL = '\u2588'; //'█'
    public static final char CHAR_EMPTY = '\u0020'; //' '
    public static final char CHAR_START = '\u0053'; //'S'
    public static final char CHAR_GOAL = '\u0047'; //'G'

    private char[][] data;
    private State start;
    private State goal;

    public char get(int x, int y) {
        if (x < 0 || x >= data[0].length || y < 0 || y >= data.length) {
            return CHAR_WALL;
        }
        else {
            return data[y][x];
        }
    }

    public Map (String fileName) throws IOException {
        FileReader fr = new FileReader(fileName);
        BufferedReader br = new BufferedReader(fr);
        String line;
        ArrayList<ArrayList<Character>> dataArray = new ArrayList<ArrayList<Character>>();
        while ((line = br.readLine()) != null) {
            ArrayList<Character> lineArray = new ArrayList<Character>();
            for (int i = 0; i < line.length(); i++) {
                char c = line.charAt(i);
                lineArray.add(c);
            }
            dataArray.add(lineArray);
        }
        if (dataArray.size() == 0) {
            System.err.println("Illegal File Content");
            System.exit(-1);
        }
        data = new char[dataArray.size()][dataArray.get(0).size()];
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[0].length; j++) {
                char val = dataArray.get(i).get(j);
                data[i][j] = val;
                if (val == CHAR_GOAL)
                    goal = new State(j,i,0);
                else if (val == CHAR_START)
                    start = new State(j,i,0);
            }
        }
    }

    public void turnRobotLeft(Robot robot) {
        robot.getState().change(new State(0,0,State.LEFT));
    }

    public void turnRobotRight(Robot robot) {
        robot.getState().change(new State(0,0,State.RIGHT));
    }

    public Tuple<Object, Object> tellRobotForward(Robot robot) {
        State robotState = robot.getState();
        int direction = robotState.orientation;
        int diff_x = (direction % 2) * (2 - direction);
        int diff_y = (1 - (direction % 2)) * (direction - 1);
        char mapState1 = this.get(robotState.x + diff_x, robotState.y + diff_y);
        if (mapState1 == CHAR_WALL) {
            return new Tuple<Object, Object> (mapState1,CHAR_WALL);
        }
        char mapState2 = this.get(robotState.x + diff_x*2, robotState.y + diff_y*2);
        return new Tuple<Object, Object> (mapState1,mapState2);
    }

    public Tuple<Object, Object> moveRobotAhead(Robot robot) {
        State robotState = robot.getState();
        int direction = robotState.orientation;
        int diff_x = (direction % 2) * (2 - direction);
        int diff_y = (1 - (direction % 2)) * (direction - 1);
        int mapState = this.get(robotState.x + diff_x, robotState.y + diff_y);
        if (mapState == CHAR_WALL)
            return new Tuple<Object, Object>('B','U');
        else {
            robotState.change(new State(diff_x, diff_y, 0));
            if (robotState.equalsPosition(goal))
                return new Tuple<Object, Object>('G','G');
            else
                return new Tuple<Object, Object>(robotState.x,robotState.y);
        }
    }

    public void placeRobot(Robot robot) {
        robot.getState().x = start.x;
        robot.getState().y = start.y;
    }

    public void printMapAndRobot(Robot robot) {
        State robotState = robot.getState();
        State currentState = new State(0,0,0);
        for (int i = 0; i < data.length; i++) {
            currentState.y = i;
            for (int j = 0; j < data[i].length; j++) {
                currentState.x = j;
                if (robotState.equalsPosition(currentState))
                    System.out.print(State.CHAR_ORIENTATION[robotState.orientation]);
                else
                    System.out.print(data[i][j]);
            }
            System.out.println();
        }
    }

    public int manhattanDistance(Robot robot) {
        return Math.abs(goal.x - robot.getState().x)
        + Math.abs(goal.y - robot.getState().y);
    }
}
