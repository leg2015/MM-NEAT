cd ..
cd ..
java -jar target/MM-NEAT-0.0.1-SNAPSHOT.jar runNumber:%1 randomSeed:%1 base:breedesizer trials:1 mu:16 maxGens:15 io:true netio:true mating:true fs:false task:edu.utexas.cs.nn.tasks.interactive.breedesizer.BreedesizerTask log:Breedesizer-Control saveTo:Control allowMultipleFunctions:true ftype:0 watch:false netChangeActivationRate:0.3 cleanFrequency:-1 recurrency:false saveInteractiveSelections:true simplifiedInteractiveInterface:true saveAllChampions:true cleanOldNetworks:false logTWEANNData:false logMutationAndLineage:true ea:edu.utexas.cs.nn.evolution.selectiveBreeding.SelectiveBreedingEA imageWidth:2000 imageHeight:2000 imageSize:200
