package edu.utexas.cs.nn.tasks.vizdoom;

import java.util.List;

import edu.utexas.cs.nn.MMNEAT.MMNEAT;
import edu.utexas.cs.nn.evolution.genotypes.TWEANNGenotype;
import edu.utexas.cs.nn.networks.Network;
import edu.utexas.cs.nn.networks.TWEANN;
import edu.utexas.cs.nn.networks.hyperneat.Substrate;
import edu.utexas.cs.nn.parameters.Parameters;
import edu.utexas.cs.nn.util.datastructures.Pair;
import edu.utexas.cs.nn.util.datastructures.Triple;
import vizdoom.Button;
import vizdoom.GameState;

public class VizDoomPredictPositionTask<T extends Network> extends VizDoomTask<T> {

	public VizDoomPredictPositionTask() {
		super();
		//Register the 1 fitness
		MMNEAT.registerFitnessFunction("Doom Reward");
	}

	@Override
	public void taskSpecificInit() {
		game.loadConfig("vizdoom/examples/config/predict_position.cfg");
		game.setDoomScenarioPath("vizdoom/scenarios/predict_position.wad");
		game.setDoomMap("map01");
	}

	@Override
	public String[] sensorLabels() {
		return getSensorLabels(Parameters.parameters.integerParameter("doomInputStartX"), 
				Parameters.parameters.integerParameter("doomInputStartY"), 
				(Parameters.parameters.integerParameter("doomInputWidth") / Parameters.parameters.integerParameter("doomInputPixelSmudge")), 
				(Parameters.parameters.integerParameter("doomInputHeight") / Parameters.parameters.integerParameter("doomInputPixelSmudge")), 
				Parameters.parameters.integerParameter("doomInputColorVal"));
	}

	@Override
	public void setDoomActions() {
		game.addAvailableButton(Button.TURN_LEFT);
		game.addAvailableButton(Button.TURN_RIGHT);	
		
		game.addAvailableButton(Button.ATTACK);	
	
		addAction(new int[] { 1, 0, 0 }, "Turn Left");
		addAction(new int[] { 0, 1, 0 }, "Turn Right");
		
		addAction(new int[] { 0, 0, 1 }, "Attack");
	}

	@Override
	public void setDoomStateVariables() {
	}

	@Override
	public double[] getInputs(GameState s) {
		double[] inputs = getInputs(s, Parameters.parameters.integerParameter("doomInputStartX"), 
				Parameters.parameters.integerParameter("doomInputStartY"), 
				Parameters.parameters.integerParameter("doomInputWidth"), 
				Parameters.parameters.integerParameter("doomInputHeight"), 
				Parameters.parameters.integerParameter("doomInputColorVal"));
		if(Parameters.parameters.integerParameter("doomInputPixelSmudge") > 1){
			return smudgeInputs(inputs, Parameters.parameters.integerParameter("doomInputWidth"), 
					Parameters.parameters.integerParameter("doomInputHeight"), 
					Parameters.parameters.integerParameter("doomInputColorVal"), 
					Parameters.parameters.integerParameter("doomInputPixelSmudge"));
		}else{
			return inputs;
		}
	}

	@Override
	public void setRewards() {

		game.setLivingReward(-0.001);
	}

	@Override
	public int numInputs() {
		int smudge = Parameters.parameters.integerParameter("doomInputPixelSmudge");
		int width = Parameters.parameters.integerParameter("doomInputWidth") / smudge;
		int height = Parameters.parameters.integerParameter("doomInputHeight") / smudge;
		
		if(Parameters.parameters.integerParameter("doomInputColorVal") == 3){
			return (width * height * 3);
		}
		return (width * height);	
	}

	public static void main(String[] args) {
		Parameters.initializeParameterCollections(new String[] { "watch:false", "io:false", "netio:false", "doomEpisodeLength:300",
				"task:edu.utexas.cs.nn.tasks.vizdoom.VizDoomPredictPositionTask", "trials:8", "printFitness:true"});
		MMNEAT.loadClasses();
		VizDoomPredictPositionTask<TWEANN> vd = new VizDoomPredictPositionTask<TWEANN>();
		TWEANNGenotype individual = new TWEANNGenotype();
		System.out.println(vd.evaluate(individual));
		System.out.println(vd.evaluate(individual));
		vd.finalCleanup();
	}

	@Override
	public double[] interpretOutputs(double[] rawOutputs) {
		double[] action = new double[3];
		action[0] = rawOutputs[0]; // Turn Left
		action[1] = rawOutputs[1]; // Turn Right
		action[2] = rawOutputs[2]; // Attack
		return null;
	}

	@Override
	public void addOutputSubstrates(List<Substrate> subs) {
		Substrate cstick = new Substrate(new Pair<Integer, Integer>(2, 1), 
				Substrate.OUTPUT_SUBSTRATE, new Triple<Integer, Integer, Integer>(0, Substrate.OUTPUT_SUBSTRATE, 0), "C-Stick Outputs");
		subs.add(cstick);
		Substrate button = new Substrate(new Pair<Integer, Integer>(1, 1), 
				Substrate.OUTPUT_SUBSTRATE, new Triple<Integer, Integer, Integer>(0, Substrate.OUTPUT_SUBSTRATE, 0), "Button Output");
		subs.add(button);
	}

	@Override
	public void addOutputConnections(List<Triple<String, String, Boolean>> conn) {
		conn.add(new Triple<String, String, Boolean>("Processing", "C-Stick Outputs", Boolean.FALSE));
		conn.add(new Triple<String, String, Boolean>("Processing", "Button Output", Boolean.FALSE));
	}
}
