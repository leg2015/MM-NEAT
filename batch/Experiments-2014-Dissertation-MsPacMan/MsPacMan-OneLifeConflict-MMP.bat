cd ..
cd ..
java -jar target/MM-NEAT-0.0.1-SNAPSHOT.jar runNumber:%1 randomSeed:%1 base:onelifeconflict maxGens:200 mu:100 io:true netio:true mating:true task:edu.utexas.cs.nn.tasks.mspacman.MsPacManTask highLevel:true infiniteEdibleTime:false imprisonedWhileEdible:false pacManLevelTimeLimit:8000 pacmanInputOutputMediator:edu.utexas.cs.nn.tasks.mspacman.sensors.mediators.IICheckEachDirectionMediator trials:10 log:OneLifeConflict-MMP saveTo:MMP fs:false edibleTime:200 trapped:true mmpRate:0.1 perLinkMutateRate:0.05 netLinkRate:0.4 netSpliceRate:0.2 crossoverRate:0.5
