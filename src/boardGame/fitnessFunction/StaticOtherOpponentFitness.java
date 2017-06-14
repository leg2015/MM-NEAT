package boardGame.fitnessFunction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import boardGame.BoardGameState;
import boardGame.agents.BoardGamePlayer;
import boardGame.agents.HeuristicBoardGamePlayer;
import boardGame.heuristics.BoardGameHeuristic;
import boardGame.heuristics.NNBoardGameHeuristic;
import edu.utexas.cs.nn.MMNEAT.MMNEAT;
import edu.utexas.cs.nn.tasks.boardGame.BoardGameUtil;
import edu.utexas.cs.nn.util.ClassCreation;
import edu.utexas.cs.nn.util.datastructures.Pair;

public class StaticOtherOpponentFitness<T extends BoardGameState> implements BoardGameFitnessFunction<T> {
	
	BoardGamePlayer<T> opponent;
	BoardGameFitnessFunction<T> selectionFunction;
	int currentGen = -1;
	List<BoardGameFitnessFunction<T>> fitFunctions = new ArrayList<BoardGameFitnessFunction<T>>();
	
	Map<Long, Double> evaluated = new HashMap<Long, Double>();

	@SuppressWarnings("unchecked")
	public StaticOtherOpponentFitness(){
		try {
			opponent = (BoardGamePlayer<T>) ClassCreation.createObject("boardGameOpponent");
			selectionFunction = new SimpleWinLoseDrawBoardGameFitness<T>();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
			System.out.println("BoardGame instance could not be loaded");
			System.exit(1);
		}
		
		fitFunctions.add(selectionFunction);
	}
	
	@Override
	public double getFitness(BoardGamePlayer<T> player) {
		
		long genotypeID = -1;
		BoardGameHeuristic bgh;
		
		if(player instanceof HeuristicBoardGamePlayer){
			bgh = ((HeuristicBoardGamePlayer) player).getHeuristic();
			if(bgh instanceof NNBoardGameHeuristic){
				genotypeID = ((NNBoardGameHeuristic) bgh).getID();
			}
		}
		
		if(evaluated.containsKey(genotypeID)){
			return evaluated.get(genotypeID);
		}else{
			BoardGamePlayer<T>[] players = new BoardGamePlayer[]{player, opponent};
			
			ArrayList<Pair<double[], double[]>> game = BoardGameUtil.playGame(MMNEAT.boardGame, players, fitFunctions);
			Double score = game.get(0).t1[0];
			
			evaluated.put(genotypeID, score);
			
			return score;
		}
	}

	@Override
	public void updateFitness(BoardGameState bgs, int index) {
		// Doesn't update until getFitness
	}

	@Override
	public String getFitnessName() {
		return "Static Opponent Fitness";
	}

	@Override
	public void reset() {
		 int testGen = MMNEAT.ea.currentGeneration();
		 
		 if(currentGen != testGen){
			evaluated.clear(); 
			currentGen = testGen;
		 }
	}

}