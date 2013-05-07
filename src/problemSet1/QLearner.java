package problemSet1;

import java.util.*;
import java.util.concurrent.*;

public class QLearner {
    public Map<Tuple<State,Integer>,Double> q;
    private int trailNumber;
    private Maze maze;
    private Random rand;
    private ArrayList<Integer> qMaxIdx; //not thread safe
    private Boolean terminateFlag;
    public static double alpha = 0.80;
    public static double gamma = 0.50;
    public static double reward = 100.0;
    public static double punishment = -1;//must be negative

    public QLearner (Maze maze, int trails) {
        trailNumber = trails;
        q = new ConcurrentHashMap<Tuple<State,Integer>,Double>();
        this.maze = maze;
        rand = new Random();
        qMaxIdx = new ArrayList<Integer>(Robot.ACTION_NUM);
        terminateFlag = false;
    }

    public void learn(final int threads, final long timeout, final int randomStart) {
        ExecutorService exec;
        terminateFlag = false;
        switch(threads) {
        case 0:
            exec = Executors.newCachedThreadPool();
            break;
        default:
            exec = Executors.newFixedThreadPool(threads);
        }
        for (int i = 0; i < trailNumber; i++) {
            final int learnCount = i;
            exec.execute(new Runnable(){
                private ArrayList<Integer> qMaxLocal = new ArrayList<Integer>();
                public void run() {
                    System.err.println("q-learning:"+(learnCount+1));
                    Robot robot = new Robot(maze,randomStart);
                    Tuple<Object, Object> signal = null;
                    int loopNum = 0;
                    while (signal == null || !signal.left.equals('G')) {
                        double r = rand.nextDouble();//pick up random double [0.0 1.0)
                        int nextAction,currentAction;
                        State prevState = robot.getState().clone();
                        if (r < 0.2) {
                            // move randomly in 20%
                            currentAction = (int)(rand.nextDouble()*Robot.ACTION_NUM);
                        }
                        else {
                            // pick up action that maximizes q-value
                            currentAction = getMaxQAction(prevState,qMaxLocal);
                        }
                        final Tuple<State,Integer> prevSA = new Tuple<State,Integer>(prevState,currentAction);
                        signal = robot.action(currentAction);
                        //map.printMapAndRobot(robot);
                        //System.out.println("action:"+currentAction+" q:"+q.get(prevSA));
                        if (signal != null && signal.left.equals('B')) {
                            //bumped, give punishment (assign the val of punishment)
                            q.put(prevSA, punishment);
                        }
                        else {
                            State currentState = robot.getState().clone();
                            nextAction = getMaxQAction(currentState,qMaxLocal);
                            final Tuple<State,Integer> currentSA = new Tuple<State,Integer>(currentState,nextAction);
                            updateQValue(prevSA,currentSA,signal);
                        }

                        loopNum++;
                        /*if (loopNum % 100000 == 0)
                            System.err.println("qloop:"+loopNum);
                         */
                        if(terminateFlag)
                            break;
                    }
                    if (!terminateFlag) {
                        System.err.println("q-learning(done):"+(learnCount+1));
                    }
                    else {
                        System.err.println("q-learning(cancelled):"+(learnCount+1));
                    }
                }
            });
        }
        exec.shutdown();
        try {
            exec.awaitTermination(timeout, TimeUnit.SECONDS);
            int attemptCount = 0;
            while (!exec.isTerminated()) {
                attemptCount++;
                System.err.println("attempt to terminate threads:"+attemptCount);
                terminateFlag = true;
                exec.shutdownNow();
                Thread.sleep(1000);
                if (attemptCount > 10)
                    break;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public int getMaxQAction(final State state) {
        return getMaxQAction(state, qMaxIdx);
    }

    public int getMaxQAction(final State state, ArrayList<Integer> qMaxIdxLocal) {
        double qMaxVal = -Double.MAX_VALUE;
        for (int a = 0; a < Robot.ACTION_NUM; a++) {
            final Tuple<State,Integer> stateWithAction = new Tuple<State,Integer>(state,a);
            //get q value
            double qVal;
            if (q.containsKey(stateWithAction))
                qVal = q.get(stateWithAction);
            else {
                qVal = 0.0;
                q.put(stateWithAction, qVal);
            }
            if (qVal > qMaxVal) {
                qMaxVal = qVal;
                qMaxIdxLocal.clear();
                qMaxIdxLocal.add(a);
            }
            else if (qVal == qMaxVal) {
                qMaxIdxLocal.add(a);
            }
        }
        int optID = qMaxIdxLocal.get(rand.nextInt(qMaxIdxLocal.size()));
        qMaxIdxLocal.clear();
        return optID;
    }

    private void updateQValue(Tuple<State, Integer> prevSA,
            Tuple<State, Integer> currentSA, Tuple<Object, Object> signal) {
        double qPrevVal,qNextVal;

        if (q.containsKey(prevSA))
            qPrevVal = q.get(prevSA);
        else
            qPrevVal = 0.0;
        if (q.containsKey(currentSA))
            qNextVal = q.get(currentSA);
        else {
            qNextVal = 0.0;
            q.put(currentSA, qNextVal);
        }
        qPrevVal += alpha*(gamma*qNextVal - qPrevVal);
        if (signal != null && signal.left.equals('G')) {
            // reached goal, give reward
            qPrevVal += alpha*reward;
        }
        q.put(prevSA, qPrevVal);
    }
}
