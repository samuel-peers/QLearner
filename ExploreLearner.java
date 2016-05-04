import java.util.Random;

/*******************************************************************************
 Explore - Exploit Phase Q-learning Example

 A super fun Q-Learner that finds the most efficient path to a cell in a grid!
 Seriously though this is really cool, I think so at least. The Q-Leaner
 acheives its goal by first exploring the grid randomly, updating the reward
 for a given action-state pair as it goes using the famous Q-formula.
 TO RUN:
 > javac ExploreExploit.java
 > java ExploreExploit [n]
 Where n is the number of episodes performed in the explore phase. The default
 is 10,000 episodes.
 Have fun!
*******************************************************************************/

/* The 2D array GRID is a graph representation of: 
             ___________________
            | 3  | 4  | 11 | 12 |
            |____|____|____|____|
            | 2  | 5  | 10 | 13 |
            |____|____|____|____|
            | 1  | 6  | 9  | 14 |
            |____|____|____|____|
            | 0  | 7  | 8  | 15 |
            |____|____|____|____|
             
    The goal state, 12, has a reward of 10. So getting to state 12 gives
    the agent a reward of 10.
    In the 2D array -1 represents states that are not connected while 0
    represents an edge between states.
    See that the edge between state 11 and state 12, and between state 13
    and 12, is 10, which is the reward. 
*/

class ExploreLearner extends QLearner{

    private int explore_val; // The number of episodes to do in the explore
                             // phase. After trying out different explore_vals,
                             // we notice that the higher the number of episodes
                             // ,the more optimal the agent. Compare the results
                             // with low explore_val (6ish) and high explore_val
                             // (10ish)  

    public ExploreLearner(int explore_val){
    	super(); 
        this.explore_val = explore_val;      
    }
    
    /***************************************************************************
     Start from random states, and move randomly until we find a goal state, 
     updating the QTABLE as we go. The value explore_val is how many "runs"
     there are, where a run is agent going from the start state to the goal
     state. 
    ***************************************************************************/
    public void explore(){

        int curr_state;
        int action;
        int max_qval;
        int qval;
        
        curr_state = 0;
        action = 0;
        max_qval = 0;
        qval = 0;

        for(int j = 0; j < explore_val; j++){

            //The starting state is random. An agent thrown into a hurricane
            curr_state = (int)(Math.random() * (NUM_ACTIONS));
            
            //Episode loop. Perform actions until the goal state is reached
            do{

                action = getValidAction(curr_state);
                
                //Update the QTABLE
                max_qval = getMaxQval(action);   
                qval = q_table[curr_state][action];
                  
                q_table[curr_state][action] =
                qFormula(curr_state, action, max_qval, qval);
            
                curr_state = action;

            }while(curr_state != goal_state);
        }
    }
    
    /***************************************************************************
     For use after exploring. Performs actions that yield highest q-value.
    ***************************************************************************/
    public void exploit(){

        int curr_state;
        
        curr_state = 0;
        
        //Start from every state. Perform actions until goal state is reached
        for(int start_state = 0; start_state < NUM_STATES; start_state++){

            System.out.print
            ("Route taken from "+start_state+" to "+goal_state+": ");

            curr_state = start_state;
            
            while(curr_state != goal_state){

                System.out.print(curr_state+", ");
                curr_state = getMaxAction(curr_state);
            }
            
            System.out.println(goal_state);
        }
    }

    /***************************************************************************
     MAIN
    ***************************************************************************/
    public static void main(String[] args){

        int explore_val = 10000; //Default explore val

        if(args.length > 0){ explore_val = Integer.parseInt(args[0]); }
    
        ExploreLearner my_learner = new ExploreLearner(explore_val);
        my_learner.explore();
        System.out.println("\nQ values after exploration phase:");
        my_learner.printQtable();
        System.out.println("\nRoutes taken during exploitation phase:");
        my_learner.exploit();

    }
}
