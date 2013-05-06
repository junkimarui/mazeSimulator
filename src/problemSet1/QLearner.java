package problemSet1;

import java.util.*;

public class QLearner {
    public Map<Tuple<State,Integer>,Double> q;
    private int trialNumber;
    private Maze map;
    private Random rand; //maybe not thread safe
    private ArrayList<Integer> qMaxIdx; //not thread safe
    public static double alpha = 0.80;
    public static double gamma = 0.50;
    public static double reward = 100.0;
    public static double punishment = -1;//must be negative


    public QLearner (Maze map, int trials) {
        trialNumber = trials;
        q = new HashMap<Tuple<State,Integer>,Double>();
        this.map = map;
        rand = new Random();
        qMaxIdx = new ArrayList<Integer>(Robot.ACTION_NUM);
    }

    public void learn() {
        for (int i = 0; i < trialNumber; i++) {
            System.err.println("q-learning:"+(i+1));
            Robot robot = new Robot(map);
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
                    currentAction = getMaxQAction(prevState);
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
                    nextAction = getMaxQAction(currentState);
                    final Tuple<State,Integer> currentSA = new Tuple<State,Integer>(currentState,nextAction);
                    updateQValue(prevSA,currentSA,signal);
                }

                loopNum++;
                /*if (loopNum % 100000 == 0)
                            System.err.println("qloop:"+loopNum);
                 */
            }
        }
    }

    public int getMaxQAction(final State state) {
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
                qMaxIdx.clear();
                qMaxIdx.add(a);
            }
            else if (qVal == qMaxVal) {
                qMaxIdx.add(a);
            }
        }
        int optID = qMaxIdx.get(rand.nextInt(qMaxIdx.size()));
        qMaxIdx.clear();
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
