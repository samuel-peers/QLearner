import java.util.Random;

/*******************************************************************************
 Epsilon greedy Q-learning Example
*******************************************************************************/

class EpsilonLearner extends QLearner{

    private double epsilon; //The agent performs random actions with probability epsilon

    public EpsilonLearner(double epsilon){
        super();
        this.epsilon = epsilon;
    }
    
    /***************************************************************************
     Start in a random state and choose a random or 'high value' action
     (according to the Q-table). A random action is performed with probability
     epsilon. 
    ***************************************************************************/
    public void perform(){

        int curr_state;
        int action;
        String route_taken; //We will print the route taken every 100th episode
        int max_qval;
        int qval;
        int iterations; //With the epsilon greedy alg., the number of episodes 
                        //is arbitary, so we just go with 10000
                            
        curr_state = 0;
        action = 0;
        route_taken = "";
        max_qval = 0;
        qval = 0;
        iterations = 10000;

        for(int i = 1; i <= iterations; i++){

            //The starting state is random. An agent thrown into a hurricane
            curr_state = (int)(Math.random() * (NUM_ACTIONS));
            
            route_taken = "Route taken from "+curr_state+" to "+goal_state+": ";
            
            // The episode loop. Perform random or 'high reward' actions until
            // the goal state is reached
            do{
            
                //Choose a random action with probability epsilon
                if(Math.random() < epsilon){
                    action = getValidAction(curr_state);
                }
                else{ //Else choose the action that yields the highest Q-value
                    action = getMaxAction(curr_state);
                }
                
                //Update the QTABLE
                max_qval = getMaxQval(action);   
                qval = q_table[curr_state][action];
                  
                q_table[curr_state][action] =
                qFormula(curr_state, action, max_qval, qval);
        
                route_taken += curr_state+", "; 
        
                curr_state = action;

            }while(curr_state != goal_state);
            
            // We're only printing every 100th iteration to avoid spam
            if(iterations%100 == 0){
                System.out.println(route_taken + goal_state);
            }

            route_taken = "";
        }
    }

    /***************************************************************************
     MAIN
    ***************************************************************************/
    public static void main(String[] args){

        double epsilon = 0.1; //Default epsilon value
        
        if(args.length > 0){ epsilon = Double.parseDouble(args[0]); }

        if(0.0 <= epsilon && epsilon <= 1.0){

            EpsilonLearner my_learner = new EpsilonLearner(epsilon);
            my_learner.perform();
            System.out.println("\nQ-table:");
            my_learner.printQtable();
        }
        else{ System.out.println("\nEpsilon must be between [0,1]"); }
    }
}
