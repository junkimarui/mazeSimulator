package problemSet1;

import java.util.*;

public class QLearner {
    private HashMap<Tuple<State,Integer>,Double> q;
    private int trialNumber;
    private Map map;
    private Random rand;
    public static double alpha = 0.80;
    public static double gamma = 0.50;
    public static double reward = 100.0;
    public static double punishment = -1;//must be negative

    public QLearner (Map map, int trials) {
        trialNumber = trials;
        q = new HashMap<Tuple<State,Integer>,Double>();
        this.map = map;
        rand = new Random();
    }

    public void learn() {
        for (int i = 0; i < trialNumber; i++) {
            Robot robot = new Robot(map);
            Tuple<Object, Object> signal = null;
            while (signal == null || !signal.left.equals('G')) {
                double r = rand.nextDouble();//pick up random double [0.0 1.0)
                int actionID = 0;
                if (r < 0.2) {
                    // move randomly in 20%
                    actionID = (int)(rand.nextDouble()*Robot.ACTION_NUM);
                }
                else {
                    // pick up action that maximizes q-value
                    actionID = getMaxQAction(robot);
                }
                State prevState = robot.getState().clone();
                signal = robot.action(actionID);
                State currentState = robot.getState().clone();

                final Tuple<State,Integer> stateWithAction = new Tuple<State,Integer>(currentState,actionID);
                if (signal != null && signal.left.equals('G')) {
                    // reached goal, give reward
                    double qGoal = 0.0;
                    if (q.containsKey(stateWithAction))
                        qGoal = q.get(stateWithAction);
                    q.put(stateWithAction, qGoal + reward);
                }
                else if (signal != null && signal.left.equals('B')) {
                    //bumped, give punishment (assign the val of punishment)
                    q.put(stateWithAction, punishment);
                }
                updateQValue(prevState,currentState,actionID);
            }
        }
    }

    public int getMaxQAction(Robot robot) {
        int optimumAID = 2;
        double qMaxVal = -Double.MAX_VALUE;
        for (int a = 0; a < Robot.ACTION_NUM; a++) {
            final Tuple<State,Integer> stateWithAction = new Tuple<State,Integer>(robot.getState().clone(),a);
            //get q value
            double qVal = 0.0;//not yet implemented
            if (qVal > qMaxVal) {
                qMaxVal = qVal;
                optimumAID = a;
            }
        }
        return optimumAID;
    }

    private void updateQValue(State prevState, State currentState, int actionID) {
        //not yet implemented
    }

}
