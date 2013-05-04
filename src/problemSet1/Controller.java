package problemSet1;

import java.io.IOException;

public class Controller {
    public static String defaultFileName = "maze-61-21.txt";
    public static int defaultProblemNumber = 3;

    public static void main(String[] args) {
        // if argument of this program exists  
        // e.g. java -cp bin problemSet1/Controller [problem number(2-4)] [file name(optional)]
        // default: `java -cp bin problemSet1/Controller 4 maze-61-21.txt`
        String fileName = defaultFileName;
        int problemNumber = defaultProblemNumber;
        if (args.length > 0) {
            defaultProblemNumber = Integer.parseInt(args[0]);
            if (args.length > 1)
                fileName = args[1];
        }
        switch(problemNumber) {
        case 2:
            problem2(fileName);
            break;
        case 3:
            problem3(fileName);
            break;
        case 4:
            problem4(fileName);
            break;
        default:
            System.err.println("Please choose 2,3,4 for problem number");
        }
    } 

    private static void problem2(String fileName) {
        try {
            Map map = new Map(fileName);
            Robot robot = new Robot(map);
            int loopCount = 0;
            Tuple<Object, Object> nextCoordinate = new Tuple<Object, Object> (Map.CHAR_EMPTY, Map.CHAR_EMPTY);
            while (!nextCoordinate.left.equals('G')) {
                robot.randomlyReorient();
                nextCoordinate = robot.moveAhead();

                //clearing prompt
                for (int i = 0; i < 30; i++)
                    System.out.println();

                map.printMapAndRobot(robot);
                loopCount++;
                System.out.println("count:"+loopCount);
                Thread.sleep(10);
            }
            System.out.println(loopCount);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void problem3(String fileName) {
        {
            try {
                Map map = new Map(fileName);
                Robot robot = new Robot(map);
                System.out.println(((Robot)robot.clone()).getState() == robot.getState());
                System.out.println(((Robot)robot.clone()).getState().equals(robot.getState()));
                int loopCount = 0;
                Tuple<Object, Object> nextCoordinate = new Tuple<Object, Object> (Map.CHAR_EMPTY, Map.CHAR_EMPTY);
                while (!nextCoordinate.left.equals('G')) {
                    robot.randomlyReorient();
                    nextCoordinate = robot.moveAhead();
                    loopCount++;
                }
                System.out.println(loopCount);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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
                int actionID = qLearner.getMaxQAction(robot);
                nextCoordinate = robot.action(actionID);
                loopCount++;
            }
            System.out.println(loopCount);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
