mazeSimulator
=============

Program for problem set 1 in #rs13qa

#Usage
`java -jar mazeSimulator.jar [maze file name] [problem number] [trails] [threads] [time out] [stages] [loop max]`
`java -jar mazeSimulatorPrint.jar [maze file name] [problem number] [trails] [threads] [time out] [stages] [loop max]`

mazeSimulatorPrint.jar prints route which is learned by Q-Learning. mazeSimulator.jar shows just a number of steps. Other functions are the same.

##Options
* maze file name: file path to maze file (default) "maze-9-9.txt"
* problem number: 2:random walk, visualised 3:random walk, shows steps only 4:execute Q learning (default) 4
* trails: the number of trails. how many trails should be calculated in Q learning. (default) 100
* threads: the number of threads. trails moves parallel to the number of threads. if threads is 0, the number of threads is automatically chosen (default) 1
* time out: each stage can have timeout to cancel too long trail. (default)Long.MAX_VALUE
* stages: when the number of stages is 1, robots start from starting point. when it is more than 1, robots start from random points during learning. (default) 1
* loop max: max of loop count in learned path to avoid infinite loop. when a loop count reaches this number, learned path starts from the beginning again. (default) 100000000L

#Files
* opt9.txt : the optimal result of mazeSimulatorPrint.jar in maze-9-9 (15 steps)
* opt61.txt : the optimal result of mazeSimulatorPrint.jar in maze-61-21 (298 steps)
* best201.tar.gz : a compressed file containing the best result of mazeSimulatorPrint.jar in maze-201-43 (1330 steps)

