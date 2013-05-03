package problemSet1;

import java.io.IOException;

public class Controller {
    public static void main(String[] args) {
        try {
            Map map1 = new Map("maze-9-9.txt");
            Robot robot = new Robot(map1);
            Tuple<Object, Object> nextCoordinate = new Tuple<Object, Object> (Map.CHAR_EMPTY, Map.CHAR_EMPTY);
            while (!nextCoordinate.left.equals('G')) {
                double rand = Math.random();
                if (rand < 0.5) {
                    robot.moveAhead();
                }
                else {
                    robot.randomlyReorient();
                }
                map1.printMapAndRobot(robot);
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
