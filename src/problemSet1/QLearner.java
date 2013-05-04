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
                    actionID = getMaxQAction(robot.getState());
                }
                signal = robot.action(actionID);
                final Tuple<State,Integer> stateWithAction = new Tuple<State,Integer>((State)robot.getState().clone(),actionID);

                updateQValue();

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
            }
        }
    }

    public int getMaxQAction(State state) {
        //not yet implemented
        return (int)(rand.nextDouble()*Robot.ACTION_NUM);
    }

    private void updateQValue() {
        //not yet implemented
    }

}
