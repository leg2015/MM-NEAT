cd ..
cd ..
java -jar target/MM-NEAT-0.0.1-SNAPSHOT.jar runNumber:%1 randomSeed:%1 base:torusprey teams:10 maxGens:500 mu:100 io:true netio:true mating:false fs:false task:edu.utexas.cs.nn.tasks.gridTorus.cooperative.CooperativePreyVsStaticPredatorsTask log:TorusPrey-CoOpDiffCCQ saveTo:CoOpDiffCCQ allowDoNothingActionForPredators:true torusPreys:2 torusPredators:3 staticPredatorController:edu.utexas.cs.nn.gridTorus.controllers.AggressivePredatorController preyRRM:false preyCoOpCCQ:true torusSenseByProximity:true torusSenseTeammates:true ea:edu.utexas.cs.nn.evolution.nsga2.CoevolutionNSGA2 experiment:edu.utexas.cs.nn.experiment.evolution.LimitedMultiplePopulationGenerationalEAExperiment teamLog:true bestTeamScore:false