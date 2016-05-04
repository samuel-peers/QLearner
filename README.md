# QLearner
An example of Epsilon Greedy and Explore-Exploit Q-Learning.

**Still to be done:**
1. Add a method for creating a specified grid, somehow...

The learner must find the most efficient path to a goal cell in a grid.

The ExploreLearner randomly explores the grid until it finds the goal state (it will start over and do this again and again n times), and then exploits its knowledge and heads for the goal state optimally from any state.

The EpsilonLearner will choose a random action with probability Epsilon and choose an optimal action (according to what it currently knows) otherwise. At first it will not perform optimally, but eventually it does.

###To Run ExploreLearner:
1. `javac ExploreLearner.java`
2. `java ExploreLearner [n]

Where `n` is the number of episodes done in the explore phase. Try really small numbers (1-5) and bigger numbers (10+) and compare how well the Learner performs (it performs optimally for values 10+)

###To Run EpsilonLearner:
1. `javac EpsilonLearner.java`
2. `java EpsilonLeaner [e]`

Where `e` is the probability of the learner performing a random action
