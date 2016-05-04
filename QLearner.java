public class QLearner{

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

	protected final int NUM_STATES = 16;
    protected final int NUM_ACTIONS = 16;
    protected final double GAMMA = 0.8; //Discount factor
    protected final double BETA = 1.0; //Learning rate
    protected final int GRID[][]; 
    protected int q_table[][];
    protected int goal_state;

	public QLearner(){

		q_table = new int[NUM_STATES][NUM_ACTIONS];
        goal_state = 12;
                          
        // We need a better way to implement a grid; this is error prone     
        GRID = new int[][] {{-1, 0,-1,-1,-1,-1,-1, 0,-1,-1,-1,-1,-1,-1,-1,-1},
                            { 0,-1, 0,-1,-1,-1, 0,-1,-1,-1,-1,-1,-1,-1,-1,-1},
                            {-1, 0,-1, 0,-1, 0,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},
                            {-1,-1, 0,-1, 0,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},
                            {-1,-1,-1, 0,-1, 0,-1,-1,-1,-1,-1, 0,-1,-1,-1,-1},
                            {-1,-1, 0,-1, 0,-1, 0,-1,-1,-1, 0,-1,-1,-1,-1,-1},
                            {-1, 0,-1,-1,-1, 0,-1, 0,-1, 0,-1,-1,-1,-1,-1,-1},
                            { 0,-1,-1,-1,-1,-1, 0,-1, 0,-1,-1,-1,-1,-1,-1,-1},
                            {-1,-1,-1,-1,-1,-1,-1, 0,-1, 0,-1,-1,-1,-1,-1, 0},
                            {-1,-1,-1,-1,-1,-1, 0,-1, 0,-1, 0,-1,-1,-1, 0,-1},
                            {-1,-1,-1,-1,-1, 0,-1,-1,-1, 0,-1, 0,-1, 0,-1,-1},
                            {-1,-1,-1,-1, 0,-1,-1,-1,-1,-1, 0,-1,10,-1,-1,-1},
                            {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1, 0,-1, 0,-1,-1},
                            {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1, 0,-1,10,-1, 0,-1},
                            {-1,-1,-1,-1,-1,-1,-1,-1,-1, 0,-1,-1,-1, 0,-1, 0},
                            {-1,-1,-1,-1,-1,-1,-1,-1, 0,-1,-1,-1,-1,-1, 0,-1}};  
	}

	/***************************************************************************
     Print out the values of the Q-Table
    /**************************************************************************/
	public void printQtable(){

        for(int row = 0; row < NUM_STATES; row++){

            for(int col = 0; col < NUM_STATES; col++){
                System.out.print(q_table[row][col]+",\t");
            }
            System.out.println("");
        } 
    }

	/***************************************************************************
     The almighty Q-formula. Called in the explore method
    /**************************************************************************/
    protected int qFormula(int curr_state, int action, int max_qval, int qval){
        double r;
        r = (qval + BETA*(GRID[curr_state][action] + GAMMA*(max_qval) - qval));
        return (int)r;
    }

    /***************************************************************************
     Returns the highest Qvalue of states reachable from curr_state
    ***************************************************************************/
    protected int getMaxQval(int state){

        int qval;
        int max;
    
        qval = 0;
        max = 0;
        
        for(int i = 0; i < NUM_ACTIONS; i++){

            if(GRID[state][i] >= 0){
                qval = q_table[state][i];
                if(qval > max){ max = qval; }
            }
        }
        return max;
    }

    /***************************************************************************
     Returns the action that yields the highest Qvalue. It can be the case
     that all Q-values for each action is equal to 0. In such a case we 
     pick an action at random
    ***************************************************************************/
    protected int getMaxAction(int curr_state){

        int qval;
        int max_action;
        int max_qval;
        
        qval = 0;
        max_action = 0;
        max_qval = 0;
        
        for(int i = 0; i < NUM_ACTIONS; i++){

            if(GRID[curr_state][i] >= 0){

                qval = q_table[curr_state][i];

                if(qval > max_qval){
                    max_qval = qval;
                    max_action = i;
                }
            }
        }
        
        if(max_qval == 0){ max_action = getValidAction(curr_state); }

        return max_action;
    }

    /***************************************************************************
     Randomly choose a valid action
    ***************************************************************************/
    protected int getValidAction(int curr_state){

        boolean valid;
        int action;

        valid = false;
        action = 0;
        while(!valid){

            //Follows the formula: Min + (int)(Math.random() * ((Max - Min) + 1))
            action = (int)(Math.random() * (NUM_ACTIONS));
            if(GRID[curr_state][action] > -1){ valid = true; }
        }
        
        return action;
    }    

}
