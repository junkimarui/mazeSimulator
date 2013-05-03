package problemSet1;

import java.io.IOException;
import java.io.PrintStream;

public class Controller {
    public static void main(String[] args) {
        try {
            Map map1 = new Map("maze-9-9.txt");
            Robot robot = new Robot(map1);
            int loopCount = 0;
            Tuple<Object, Object> nextCoordinate = new Tuple<Object, Object> (Map.CHAR_EMPTY, Map.CHAR_EMPTY);
            while (!nextCoordinate.left.equals('G')) {
                double rand = Math.random();
                if (rand < 0.5) {
                    nextCoordinate = robot.moveAhead();
                }
                else {
                    robot.randomlyReorient();
                }
                Runtime.getRuntime().exec("clear");
                map1.printMapAndRobot(robot);
                loopCount++;
                System.out.println("count:"+loopCount);
                Thread.sleep(10);
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
