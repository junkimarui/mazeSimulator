package problemSet1;

import java.io.*;
import java.util.*;

public class Map {
    public static final int WALL = 0;
    public static final int EMPTY = 1;
    public static final int START = 2;
    public static final int GOAL = 3;
    public static final char CHAR_WALL = '\u2588';
    public static final char CHAR_EMPTY = '\u0020';
    public static final char CHAR_START = '\u0053';
    public static final char CHAR_GOAL = '\u0047';
    
    private int[][] data;
    private State start;
    private State goal;

    public int get(int x, int y) {
        if (x < 0 || x >= data[0].length || y < 0 || y >= data.length) {
            return WALL;
        }
        else {
            return data[y][x];
        }
    }
    
    public Map (String fileName) throws IOException {
        FileReader fr = new FileReader(fileName);
        BufferedReader br = new BufferedReader(fr);
        String line;
        ArrayList<ArrayList<Integer>> dataArray = new ArrayList<ArrayList<Integer>>();
        while ((line = br.readLine()) != null) {
            ArrayList<Integer> lineArray = new ArrayList<Integer>();
            for (int i = 0; i < line.length(); i++) {
                char c = line.charAt(i);
                int val;
                switch (c) {
                case CHAR_WALL:
                    val = WALL;
                case CHAR_START:
                    val = START;
                case CHAR_GOAL:
                    val = GOAL;
                default:
                    val = EMPTY;
                }
                lineArray.add(val);
            }
            dataArray.add(lineArray);
        }
        if (dataArray.size() == 0) {
            System.err.println("Illegal File Content");
            System.exit(-1);
        }
        data = new int[dataArray.size()][dataArray.get(0).size()];
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[0].length; j++) {
                int val = dataArray.get(i).get(j);
                data[i][j] = val;
                if (val == GOAL)
                    goal = new State(j,i,0);
                else if (val == START)
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

    public Tuple<Integer, Integer> tellRobotForward(Robot robot) {
        State robotState = robot.getState();
        int direction = robotState.direction;
        int diff_x = (direction % 2) * (2 - direction);
        int diff_y = (1 - (direction % 2)) * (direction - 1);
        int mapState1 = this.get(robotState.x + diff_x, robotState.y + diff_y);
        if (mapState1 == WALL || mapState1 == GOAL) {
            return new Tuple<Integer, Integer> (mapState1,WALL);
        }
        int mapState2 = this.get(robotState.x + diff_x*2, robotState.y + diff_y*2);
        return new Tuple<Integer, Integer> (mapState1,mapState2);
    }

    public boolean moveRobotAhead(Robot robot) {
        State robotState = robot.getState();
        int direction = robotState.direction;
        int diff_x = (direction % 2) * (2 - direction);
        int diff_y = (1 - (direction % 2)) * (direction - 1);
        int mapState = this.get(robotState.x + diff_x, robotState.y + diff_y);
        if (mapState == WALL)
            return false;
        else {
            robot.getState().change(new State(diff_x, diff_y, 0));
            return true;
        }
    }
    
    public boolean isRobotOnGoal(Robot robot) {
        return robot.getState().equalsPosition(goal);
    }
    
    public void placeRobot(Robot robot) {
        robot.getState().x = start.x;
        robot.getState().y = start.y;
    }
}
