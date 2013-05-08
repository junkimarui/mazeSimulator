mazeSimulator
=============

Program for problem set 1 in #rs13qa

#Usage
`java -jar mazeSimulator.jar [maze file name] [problem number] [trails] [threads] [time out] [stages] [loop max]`
`java -jar mazeSimulatorPrint.jar [maze file name] [problem number] [trails] [threads] [time out] [stages] [loop max]`

mazeSimulatorPrint.jar prints a route which is learned by Q-Learning.  
mazeSimulator.jar shows just a number of steps. Other functions are the same.

##Options
* _[maze file name]_ : file path to maze file  
(default) "maze-9-9.txt"
* _[problem number]_ : 2:random walk, visualised 3:random walk,   
shows steps only 4:execute Q learning  
(default) 4
* _[trails]_ : the number of trails. how many trails should be calculated in Q learning.  
(default) 100
* _[threads]_ : the number of threads. trails moves parallel to the number of threads.  
if threads is 0, the number of threads is automatically chosen  
(default) 1
* _[time out]_ : each stage can have timeout to cancel too long trail.  
(default)Long.MAX_VALUE
* _[stages]_ : when the number of stages is 1, robots start from starting point.  
when it is more than 1, robots start from random points during learning.  
(default) 1
* _[loop max]_ : max of loop count in learned path to avoid infinite loop.  
when a loop count reaches this number, learned path starts from the beginning again.  
(default) 100000000L

#Files
* _opt9.txt_ : the optimal result of mazeSimulatorPrint.jar in maze-9-9 (15 steps)
* _opt61.txt_ : the optimal result of mazeSimulatorPrint.jar in maze-61-21 (298 steps)
* _best201.tar.gz_ : a compressed file containing the best result of  
mazeSimulatorPrint.jar in maze-201-43 (1330 steps)

