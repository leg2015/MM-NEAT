cd ..
cd ..
java -jar target/MM-NEAT-0.0.1-SNAPSHOT.jar runNumber:%1 randomSeed:%1 base:microRTS trials:3 maxGens:500 mu:25 io:true netio:true mating:true task:edu.utexas.cs.nn.tasks.microrts.MicroRTSTask cleanOldNetworks:true fs:false log:MicroRTS-MLPSMCTSvsRandBiased saveTo:MLPSMCTSvsRandBiased watch:false microRTSAgent:micro.ai.mcts.mlps.MLPSMCTS map:12x12/basesWorkers12x12.xml  microRTSEvaluationFunction:edu.utexas.cs.nn.tasks.microrts.evaluation.NN2DEvaluationFunction microRTSFitnessFunction:edu.utexas.cs.nn.tasks.microrts.fitness.WinLossFitnessFunction hyperNEAT:true genotype:edu.utexas.cs.nn.evolution.genotypes.HyperNEATCPPNGenotype allowMultipleFunctions:true ftype:1 netChangeActivationRate:0.3 extraHNLinks:true