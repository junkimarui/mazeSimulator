package problemSet1;

import java.io.IOException;

public class Controller {
    public static void main(String[] args) {
        try {
            Map map1 = new Map("maze-9-9.txt");
            Robot robot = new Robot(map1);
            int loopCount = 0;
            Tuple<Object, Object> nextCoordinate = new Tuple<Object, Object> (Map.CHAR_EMPTY, Map.CHAR_EMPTY);
            while (!nextCoordinate.left.equals('G')) {
                robot.randomlyReorient();
                nextCoordinate = robot.moveAhead();

                //clearing prompt
                //for (int i = 0; i < 30; i++)
                //    System.out.println();

                //map1.printMapAndRobot(robot);
                loopCount++;
                //System.out.println("count:"+loopCount);
                //Thread.sleep(10);
            }
            System.out.println(loopCount);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
