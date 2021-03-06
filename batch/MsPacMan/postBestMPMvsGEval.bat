REM Usage:   postBestMPMvsGEval.bat <experiment directory> <log prefix> <run type> <run number> <number of trials per individual>
REM Example: postBestMPMvsGEval.bat onelifeconflict OneLifeConflict OneModule 0 5
cd ..
cd ..
java -jar "target/MM-NEAT-0.0.1-SNAPSHOT.jar" runNumber:%4 experiment:edu.southwestern.experiment.post.BestNetworkExperiment base:%1 log:%2-%3 saveTo:%3 trials:%5 watch:false showNetworks:false io:false netio:false onlyWatchPareto:true printFitness:false pacManGainsLives:true pacmanLives:3 animateNetwork:false monitorInputs:false logDeathLocations:false pacManLevelTimeLimit:3000 pacmanMaxLevel:16 pacmanFatalTimeLimit:false evalReport:true timedPacman:true getRemainingPills:true
