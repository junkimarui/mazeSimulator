package problemSet1;

import java.io.IOException;

public class Controller {
    public static String defaultFileName = "maze-61-21.txt";
    public static int defaultProblemNumber = 4;
    public static int defaultTrialNumber = 100;

    public static void main(String[] args) {
        // if argument of this program exists  
        // e.g. java -cp bin problemSet1/Controller [problem number(2-4)] [file name(optional)] [trial number for q learning(optional)]
        // default: `java -cp bin problemSet1/Controller 4 maze-61-21.txt`
        String fileName = defaultFileName;
        int problemNumber = defaultProblemNumber;
        int trialNumber = defaultTrialNumber;
        if (args.length > 0) {
            problemNumber = Integer.parseInt(args[0]);
            if (args.length > 1) {
                fileName = args[1];
                if (args.length > 2)
                    trialNumber = Integer.parseInt(args[2]);
            }
        }
        switch(problemNumber) {
        case 2:
            problem2(fileName);
            break;
        case 3:
            problem3(fileName);
            break;
        case 4:
            problem4(fileName,trialNumber);
            break;
        default:
            System.err.println("Please choose 2,3,4 for problem number");
        }
    } 

    private static void problem2(String fileName) {
        try {
            Maze maze = new Maze(fileName);
            Robot robot = new Robot(maze);
            int loopCount = 0;
            Tuple<Object, Object> nextCoordinate = new Tuple<Object, Object> (Maze.CHAR_EMPTY, Maze.CHAR_EMPTY);
            while (!nextCoordinate.left.equals('G')) {
                robot.randomlyReorient();
                nextCoordinate = robot.moveAhead();

                //clearing prompt
                for (int i = 0; i < 30; i++)
                    System.out.println();

                maze.printMapAndRobot(robot);
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
                Maze maze = new Maze(fileName);
                Robot robot = new Robot(maze);
                int loopCount = 0;
                Tuple<Object, Object> nextCoordinate = new Tuple<Object, Object> (Maze.CHAR_EMPTY, Maze.CHAR_EMPTY);
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

    private static void problem4(String fileName, int trialNumber) {
        try {
            Maze maze = new Maze(fileName);
            QLearner qLearner = new QLearner(maze,trialNumber);
            qLearner.learn();

            Robot robot = new Robot(maze);
            int loopCount = 0;
            Tuple<Object, Object> nextCoordinate = null;
            while (nextCoordinate == null || !nextCoordinate.left.equals('G')) {
                int actionID = qLearner.getMaxQAction(robot.getState().clone());
                nextCoordinate = robot.action(actionID);
                loopCount++;
                //map.printMapAndRobot(robot);
            }
            System.out.println(loopCount);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
