package edu.utexas.cs.nn.networks;

import java.util.ArrayList;
import java.util.List;

import edu.utexas.cs.nn.MMNEAT.MMNEAT;
import edu.utexas.cs.nn.evolution.genotypes.HyperNEATCPPNGenotype;
import edu.utexas.cs.nn.networks.hyperneat.HyperNEATTask;
import edu.utexas.cs.nn.networks.hyperneat.Substrate;
import edu.utexas.cs.nn.parameters.CommonConstants;
import edu.utexas.cs.nn.util.PopulationUtil;
import edu.utexas.cs.nn.util.datastructures.Pair;
import edu.utexas.cs.nn.util.util2D.ILocated2D;
import edu.utexas.cs.nn.util.util2D.Tuple2D;
/**
 * Multi-Layer Perceptron class that has a generalized
 * number of in, hidden, and out layers that can be 
 * used as an alternative to TWEANNs, also will
 * hopefully speed up hyperNEAT considerably
 * @author Lauren Gillespie
 *
 */
public class SubstrateMLP implements Network {

	//private instance variables
	private List<double[][][][]> connections;
	private List<Pair<double[][], Integer>> neurons;
	private int numInputs = 0;
	private int numOutputs = 0;
	private int numHiddenLayers = 0;
	private int ftype;//TODO

	/**
	 * Constructor
	 * @param subs list of substrates provided by task
	 * @param connections connections of substrates provided by task
	 * @param network cppn used to process coordinates to produce weight of links
	 */
	public SubstrateMLP(List<Substrate> subs,  List<Pair<String, String>> connections, Network network) {
		assert network.numInputs() == HyperNEATTask.NUM_CPPN_INPUTS:"Number of inputs to network = " + network.numInputs() + " not " + HyperNEATTask.NUM_CPPN_INPUTS;
		int i = 0;
		for(Pair<String, String> connection : connections) {
			i++;
			Substrate sourceSub = subs.get(subs.indexOf(connection.t1));
			Substrate targetSub = subs.get(subs.indexOf(connection.t2));
			double[][][][] connect = new double[sourceSub.size.t1][sourceSub.size.t2][targetSub.size.t1][targetSub.size.t2];
			for(int X1 = 0; X1 < sourceSub.size.t1; X1++) {
				for(int Y1 = 0; Y1 < sourceSub.size.t2; Y1++) {
					for(int X2 = 0; X2 < targetSub.size.t1; X2++) {
						for(int Y2 = 0; Y2 < targetSub.size.t2; Y2++) {
							// CPPN inputs need to be centered and scaled
							ILocated2D scaledSourceCoordinates = MMNEAT.substrateMapping.transformCoordinates(new Tuple2D(X1, Y1), sourceSub.size.t1, sourceSub.size.t2);
							ILocated2D scaledTargetCoordinates = MMNEAT.substrateMapping.transformCoordinates(new Tuple2D(X2, Y2), targetSub.size.t1, targetSub.size.t2);
							// inputs to CPPN 
							double[] inputs = { scaledSourceCoordinates.getX(), scaledSourceCoordinates.getY(), scaledTargetCoordinates.getX(), scaledTargetCoordinates.getY(), HyperNEATCPPNGenotype.BIAS}; 
							double[] outputs = network.process(inputs);
							boolean expressLink = Math.abs(outputs[i]) > CommonConstants.linkExpressionThreshold;
							//whether or not to place a link in location
							if (expressLink) {
								connect[X1][Y1][X2][Y2] = PopulationUtil.calculateWeight(outputs[i]);
							} else {//if not, make weight 0, synonymous to no link in first place
								connect[X1][Y1][X2][Y2] = 0;
							}
						}
					}
				}
			}
			this.connections.add(connect);
		}
		addNeurons(subs, neurons);
	}

	/**
	 * adds nodes from substrates to neurons list
	 * @param subs substrate list
	 * @param neurons node array to add to 
	 */
	private final void addNeurons(List<Substrate> subs, List<Pair<double[][], Integer>> neurons) { 
		for(Substrate sub: subs) {
			neurons = new ArrayList<Pair<double[][], Integer>>();
			Pair<double[][], Integer> neuron = new Pair<double[][], Integer>(new double[sub.size.t1][sub.size.t2], sub.stype);
			neurons.add(neuron);
			if(sub.stype == Substrate.INPUT_SUBSTRATE){ numInputs += sub.size.t1 * sub.size.t2;
			} else if(sub.stype == Substrate.PROCCESS_SUBSTRATE) { numHiddenLayers++;
			}else if(sub.stype == Substrate.OUTPUT_SUBSTRATE){ numOutputs += sub.size.t1 * sub.size.t2;}
		}
	}
	
	/**
	 * Fills neurons with correct inputs
	 * @param neurons list of neurons
	 * @param inputs inputs 
	 */
	private void fillNeurons(List<Pair<double[][], Integer>> neurons, double[] inputs) { 
		int x = 0;
		for(Pair<double[][], Integer> sub : neurons) {
			for(int i = 0; i < sub.t1.length; i++) {
				for(int j = 0; j< sub.t1[0].length; j++) {
					if(sub.t2 == Substrate.INPUT_SUBSTRATE) {
						sub.t1[i][j] = inputs[x++];
					}else {
						sub.t1[i][j] = 0;
					}
				}
			}
		}
	}
	/**
	 * Returns number of inputs
	 */
	@Override
	public int numInputs() {
		return numInputs;
	}

	/**
	 * Returns number of outputs
	 */
	@Override
	public int numOutputs() {
		return numOutputs;
	}

	/**
	 * Processes inputs through network
	 */
	@Override
	public double[] process(double[] inputs) {
		assert numInputs == inputs.length: "number of inputs " + numInputs + " does not match size of inputs given: " + inputs.length;
		double[] outputs = new double[numOutputs];
		flush();
		fillNeurons(neurons, inputs);
		outputs = propagateOneStep(inputs);//sends inputs to hidden layers
		for(int i = 0; i < numHiddenLayers; i++) {//process through rest of network
			outputs = propagateOneStep(outputs);
		}
		return outputs;
	}

	/**
	 * Propagates one step through network
	 * @param inputs inputs to layer
	 * @return outputs from layer
	 */
	private double[] propagateOneStep(double[] inputs) {//TODO
		double[] outputs = new double[0];
		/*
		 * add code here
		 */
		for(double a : outputs) {
			activate(a, ftype);
		}
		return outputs;
	}

	/**
	 * Returns the activation of a node
	 * @param input input to node
	 * @param ftype activation function of node
	 * @return activation
	 */
	protected double activate(double input, int ftype) {
		return ActivationFunctions.activation(ftype, input);
	}
	
	/**
	 * Clears out all previous activations in nodes
	 */
	@Override
	public void flush() {
		for(Pair<double[][], Integer> toClear : neurons) {
			clear(toClear.t1);
		}

	}

	/**
	 * Clears given double array
	 * @param toClear array to clear
	 */
	private void clear(double[][] toClear) {
		for(int x = 0; x < toClear.length; x++) {
			for(int y = 0; y < toClear[0].length; y++) {
				toClear[x][y] = 0;
			}
		}
	}
	@Override
	/**
	 * Will always return false
	 * unless multiobjective
	 * behavior developted for
	 * substrateMLP
	 */
	public boolean isMultitask() {
		return false;
	}

	@Override
	/**
	 * not supported yet
	 */
	public int effectiveNumOutputs() {
		throw new UnsupportedOperationException("Not supported yet.");
	}
	@Override
	/**
	 * not supported yet
	 */
	public void chooseMode(int mode) {
		throw new UnsupportedOperationException("Not supported yet.");

	}

	@Override
	/**
	 * not supported yet
	 */
	public int lastModule() {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	/**
	 * not supported yet
	 */
	public double[] moduleOutput(int mode) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	/**
	 * not supported yet
	 */
	public int numModules() {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	/**
	 * not supported yet
	 */
	public int[] getModuleUsage() {
		throw new UnsupportedOperationException("Not supported yet.");
	}

}