package problemSet1;

import java.io.IOException;

public class Controller {
    public static String defaultFileName = "maze-61-21.txt";

    public static void main(String[] args) {
        String fileName = null;
        // if argument of this program exists  
        // e.g. java -cp bin problemSet1/Controller [file name]
        if (args.length > 0) {
            fileName = args[0];
        }
        else {
            //otherwise uses defaultFileName
            fileName = defaultFileName;
        }
        //problem3(fileName);
        problem4(fileName);
    } 

    private static void problem4(String fileName) {
        try {
            Map map = new Map(fileName);
            QLearner qLearner = new QLearner(map,100);
            qLearner.learn();

            Robot robot = new Robot(map);
            int loopCount = 0;
            Tuple<Object, Object> nextCoordinate = null;
            while (nextCoordinate == null || !nextCoordinate.left.equals('G')) {
                int actionID = qLearner.getMaxQAction(robot.getState());
                nextCoordinate = robot.action(actionID);
                loopCount++;
            }
            System.out.println(loopCount);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    private static void problem3(String fileName) {
        {
            try {
                Map map1 = new Map(fileName);
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
}
