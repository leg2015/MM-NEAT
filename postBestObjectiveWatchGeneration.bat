REM NOTE: Can only be used with saveAllChampions set to true
REM Usage:   postBestObjectiveWatchGeneration.bat <experiment directory> <log prefix> <run type> <run number> <generation> <number of trials per team>
REM Example: postBestObjectiveWatchGeneration.bat toruspred TorusPred CoOpMultiCCQ 0 3 10
java -jar "target/MM-NEAT-0.0.1-SNAPSHOT.jar" runNumber:%4 parallelEvaluations:false base:%1 log:%2-%3 saveTo:%3 trials:%5 watch:true showNetworks:true io:false netio:false onlyWatchPareto:true printFitness:true animateNetwork:false ucb1Evaluation:false showSubnetAnalysis:true monitorInputs:true experiment:edu.utexas.cs.nn.experiment.post.ObjectiveBestTeamsExperiment logLock:true watchLastBestOfTeams:true lastSavedGeneration:%5 showVizDoomInputs:true showCPPN:true