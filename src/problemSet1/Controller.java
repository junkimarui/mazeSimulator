package problemSet1;

import java.io.IOException;

public class Controller {
    public static String defaultFileName = "maze-9-9.txt";
    public static int defaultProblemNumber = 4;
    public static int defaultTrailNumber = 100;
    public static int defaultThreadNumber = 1;
    public static long defaultTimeOut = Long.MAX_VALUE;
    public static int defaultLearnCount = 1;
    public static long maxLoopToFindGoal = 100000000L;

    public static void main(String[] args) {
        // if argument of this program exists  
        // e.g. java -cp bin problemSet1/Controller [file name(optional)] [problem number(2-4)] [trial number for q learning(optional)] [parallel number(optional)]
        // default: `java -cp bin problemSet1/Controller 4 maze-61-21.txt`
        String fileName = defaultFileName;
        int problemNumber = defaultProblemNumber;
        int trailNumber = defaultTrailNumber;
        int threadNumber = defaultThreadNumber;
        long timeOut = defaultTimeOut;
        int learnCount = defaultLearnCount;
        long maxLoop = maxLoopToFindGoal;

        switch (args.length) {
        case 7:
            maxLoop = Long.parseLong(args[6]);
        case 6:
            learnCount = Integer.parseInt(args[5]);
        case 5:
            timeOut = Long.parseLong(args[4]);
        case 4:
            threadNumber =  Integer.parseInt(args[3]);
        case 3:
            trailNumber = Integer.parseInt(args[2]);
        case 2:
            problemNumber = Integer.parseInt(args[1]);
        case 1:
            fileName = args[0];
        }

        switch(problemNumber) {
        case 2:
            problem2(fileName);
            break;
        case 3:
            problem3(fileName);
            break;
        case 4:
            problem4(fileName,trailNumber,threadNumber,timeOut,learnCount,maxLoop);
            break;
        default:
            System.err.println("Please choose 2,3,4 for problem number");
        }
    } 

    private static void problem2(String fileName) {
        try {
            Maze maze = new Maze(fileName);
            Robot robot = new Robot(maze);
            long loopCount = 0;
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
                long loopCount = 0;
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

    private static void problem4(String fileName, int trailNumber, int threads, long timeout, int learnCount, long maxLoop) {
        try {
            Maze maze = new Maze(fileName);
            QLearner qLearner = new QLearner(maze,trailNumber);
            for (int i = 0; i < learnCount; i++) {
                System.err.println("q-learning-set:"+(i+1));
                boolean randomizedStart = (learnCount > 1) ? true:false;
                qLearner.learn(threads,timeout,randomizedStart);
            }
            Robot robot = new Robot(maze);
            long loopCount = 0;
            Tuple<Object, Object> nextCoordinate = null;
            while (nextCoordinate == null || !nextCoordinate.left.equals('G')) {
                int actionID = qLearner.getMaxQAction(robot.getState().clone());
                nextCoordinate = robot.action(actionID);
                loopCount++;
                //comment out when the route learned should be printed
                //maze.printMapAndRobot(robot); 
                if (loopCount % 10000000 == 0)
                    System.err.println("loop:"+loopCount);
                if (loopCount >= maxLoop) {
                    robot = new Robot(maze);
                    loopCount = 0;
                }
            }
            if (loopCount >= maxLoop)
                System.out.println("> "+maxLoop);
            else
                System.out.println(loopCount);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
