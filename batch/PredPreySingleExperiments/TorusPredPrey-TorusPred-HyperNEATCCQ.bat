cd ..
cd ..
java -jar target/MM-NEAT-0.0.1-SNAPSHOT.jar runNumber:%1 randomSeed:%1 base:toruspred trials:1 maxGens:500 mu:50 io:true netio:true mating:false fs:false task:edu.utexas.cs.nn.tasks.gridTorus.TorusEvolvedPredatorsVsStaticPreyTask log:TorusPred-HyperNEATCCQ saveTo:HyperNEATCCQ allowDoNothingActionForPredators:true torusPreys:2 torusPredators:3 staticPreyController:edu.utexas.cs.nn.gridTorus.controllers.PreyFleeClosestPredatorController predatorCatchClose:false predatorCatch:true predatorMinimizeDistance:true predatorsEatQuick:true torusSenseByProximity:false torusSenseTeammates:true hyperNEAT:true genotype:edu.utexas.cs.nn.evolution.genotypes.HyperNEATCPPNGenotype allowMultipleFunctions:true ftype:0 netChangeActivationRate:0.3 torusXDimensions:20 torusYDimensions:20