cd ..
cd ..
java -jar dist/MM-NEATv2.jar runNumber:%1 randomSeed:%1 base:checkers trials:10 maxGens:500 mu:100 io:true netio:true mating:true task:edu.utexas.cs.nn.tasks.boardGame.StaticOpponentBoardGameTask cleanOldNetworks:true fs:false log:Checkers-OneStep-StaticOpponent-OneStep-PieceDiff saveTo:StaticOpponent-OneStep-PieceDiff boardGame:boardGame.checkers.Checkers boardGameOpponent:boardGame.BoardGamePlayerMinimax boardGameOpponentHeuristic:boardGame.heuristics.PieceDifferentialBoardGameHeuristic