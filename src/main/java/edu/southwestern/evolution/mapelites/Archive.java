package edu.southwestern.evolution.mapelites;

import java.util.*;

import edu.southwestern.scores.Score;
import edu.southwestern.util.random.RandomNumbers;

public class Archive<T> {
	
	/**
	 * Stores all individuals in bins (looked up by hash keys) where each bin is
	 * sorted so that the first individual (index 0) is the fittest (elite) individual.
	 */
	private Map<String,ArrayList<Score<T>>> archive;
	private BinMapping<T> mapping;

	public Archive() {
		archive = new HashMap<String,ArrayList<Score<T>>>();
		// Initialize mapping
		
		// TODO:
	}
	
	/**
	 * Given an ArchivedOrganism (which contains some evaluation information about the genotype),
	 * figure out which bin it belongs in and add it at the front if it is a new elite.
	 * Otherwise, add it at the end.
	 * @param candidate Organism containing genotype and eval information
	 * @return Whether organism was a new elite
	 */
	public boolean add(Score<T> candidate) {
		String binLabel = mapping.binForScore(candidate);
		boolean newElite = true; // Will the candidate be a new elite?
		ArrayList<Score<T>> bin;
		if(!archive.containsKey(binLabel)) { // empty bin
			bin = new ArrayList<Score<T>>();
			bin.add(candidate); // now the elite of the bin
		} else { // bin not empty
			bin = archive.get(binLabel);
			Score<T> elite = bin.get(0); // best is always at front
			if(candidate.isAtLeastAsGood(elite)) { // candidate is as good or better than elite
				bin.add(0, candidate); // Add at front as new elite
			} else { // candidate is worse than elite
				bin.add(candidate); // Add at end: not part of standard MAP-Elites
				newElite = false; // Was not a new elite
			}
		}
		archive.put(binLabel, bin); // put bin back
		return newElite;
	}
	
	/**
	 * Return current elite organism from specified bin
	 * @param binLabel String label identifying bin
	 * @return ArchivedOrganism with best fitness in that bin, or null if bin is empty/not present
	 */
	public Score<T> getElite(String binLabel) {
		ArrayList<Score<T>> bin = archive.get(binLabel);
		return bin == null ? null : bin.get(0); // elite is at front (index 0)
	}
	
	/**
	 * Select random label across bins that are occupied
	 * @return String label of a random occupied bin
	 */
	public String randomBinLabel() {
		return RandomNumbers.randomElement(archive.keySet().toArray(new String[0]));
	}
	
	/**
	 * Save the whole archive contents to the specified file path
	 * @param path
	 */
	public void saveArchive(String path) {
		// TODO
	}
}