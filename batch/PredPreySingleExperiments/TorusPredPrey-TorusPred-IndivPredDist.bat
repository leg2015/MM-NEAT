cd ..
cd ..
java -jar target/MM-NEAT-0.0.1-SNAPSHOT.jar runNumber:%1 randomSeed:%1 base:toruspred trials:10 maxGens:500 mu:100 io:true netio:true mating:false fs:false task:edu.utexas.cs.nn.tasks.gridTorus.TorusEvolvedPredatorsVsStaticPreyTask log:TorusPred-IndivPredDist saveTo:IndivPredDist allowDoNothingActionForPredators:true torusPreys:2 torusPredators:3 staticPreyController:edu.utexas.cs.nn.gridTorus.controllers.PreyFleeClosestPredatorController predatorCatchClose:false indivPredMinDist:true torusSenseByProximity:true torusSenseTeammates:true