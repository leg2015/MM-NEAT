cd ..
cd ..
java -jar target/MM-NEAT-0.0.1-SNAPSHOT.jar runNumber:%1 randomSeed:%1 base:othello trials:10 maxGens:200 mu:50 io:true netio:true mating:true task:edu.utexas.cs.nn.tasks.boardGame.StaticOpponentBoardGameTask cleanOldNetworks:true fs:false log:Othello-HNStaticWPCDeterministicD2Random4MovesMOConv saveTo:HNStaticWPCDeterministicD2Random4MovesMOConv boardGame:boardGame.othello.Othello boardGameOpponent:boardGame.agents.treesearch.BoardGamePlayerMinimaxAlphaBetaPruning boardGameOpponentHeuristic:boardGame.heuristics.StaticOthelloWPCHeuristic boardGamePlayer:boardGame.agents.treesearch.BoardGamePlayerMinimaxAlphaBetaPruning minimaxSearchDepth:2 genotype:edu.utexas.cs.nn.evolution.genotypes.HyperNEATCPPNGenotype hyperNEAT:true minimaxRandomRate:0.0 boardGameOthelloFitness:true boardGameSimpleFitness:true randomArgMaxTieBreak:false boardGameOpeningRandomMoves:4 evalReport:true convolution:true HNProcessWidth:3