cd ..
cd ..
java -jar target/MM-NEAT-0.0.1-SNAPSHOT.jar runNumber:%1 randomSeed:%1 base:tetris trials:5 maxGens:500 mu:50 io:true netio:true mating:true task:edu.utexas.cs.nn.tasks.rlglue.tetris.HyperNEATTetrisTask cleanOldNetworks:true noisyTaskStat:edu.utexas.cs.nn.util.stats.Average log:RL-moHyperNEATBottomSplitInputs saveTo:moHyperNEATBottomSplitInputs rlGlueEnvironment:org.rlcommunity.environments.tetris.Tetris rlGlueExtractor:edu.utexas.cs.nn.tasks.rlglue.featureextractors.tetris.RawTetrisStateExtractor tetrisTimeSteps:true rlGlueAgent:edu.utexas.cs.nn.tasks.rlglue.tetris.TetrisAfterStateAgent hyperNEAT:true genotype:edu.utexas.cs.nn.evolution.genotypes.HyperNEATCPPNGenotype allowMultipleFunctions:true ftype:1 netChangeActivationRate:0.3 senseHolesDifferently:true  substrateMapping:edu.utexas.cs.nn.networks.hyperneat.BottomSubstrateMapping leo:true splitRawTetrisInputs:true
