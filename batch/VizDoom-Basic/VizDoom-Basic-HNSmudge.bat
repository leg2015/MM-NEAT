cd ..
cd ..
java -jar target/MM-NEAT-0.0.1-SNAPSHOT.jar runNumber:%1 randomSeed:%1 base:vizdoombasic trials:5 maxGens:500 mu:10 io:true netio:true mating:true task:edu.utexas.cs.nn.tasks.vizdoom.VizDoomBasicShootTask cleanOldNetworks:true fs:false noisyTaskStat:edu.utexas.cs.nn.util.stats.Average log:Basic-HNSmudge saveTo:HNSmudge gameWad:freedoom2.wad watch:false doomInputPixelSmudge:5 doomSmudgeStat:edu.utexas.cs.nn.util.stats.MostExtreme hyperNEAT:true genotype:edu.utexas.cs.nn.evolution.genotypes.HyperNEATCPPNGenotype allowMultipleFunctions:true ftype:1 netChangeActivationRate:0.3 doomInputStartX:0 doomInputStartY:60 doomInputHeight:48 doomInputWidth:200 printFitness:true