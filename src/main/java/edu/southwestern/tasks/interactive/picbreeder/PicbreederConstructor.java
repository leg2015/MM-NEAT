package edu.southwestern.tasks.interactive.picbreeder;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import edu.southwestern.MMNEAT.MMNEAT;
import edu.southwestern.evolution.EvolutionaryHistory;
import edu.southwestern.evolution.genotypes.TWEANNGenotype;
import edu.southwestern.evolution.genotypes.TWEANNGenotype.NodeGene;
import edu.southwestern.networks.ActivationFunctions;
import edu.southwestern.networks.TWEANN;
import edu.southwestern.parameters.Parameters;
import edu.southwestern.util.MiscUtil;
import edu.southwestern.util.graphics.DrawingPanel;
import edu.southwestern.util.graphics.GraphicsUtil;

/**
 * I got some genomes from:
 * https://github.com/Evolving-AI-Lab/cppnx/tree/master/CanalizationPicbreederGenomes
 * which I believe were transferred from the original Picbreeder.
 * In any case, they represent some of the most prominent images
 * from that site. This class loads those genomes into my CPPN format
 * and then displays the pictures.
 * 
 * @author Jacob Schrum
 */
public class PicbreederConstructor {
	public static final int SIZE = 500;
	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {		
		Parameters.initializeParameterCollections(new String[] {"io:false","netio:false","allowMultipleFunctions:true", "fs:true","netChangeActivationRate:0.3",
				"recurrency:false",
				"includeSigmoidFunction:true", // In original Innovation Engine
				"includeTanhFunction:false",
				"includeIdFunction:false",
				"includeFullApproxFunction:false",
				"includeApproxFunction:false",
				"includeGaussFunction:true", // In original Innovation Engine
				"includeSineFunction:true", // In original Innovation Engine
				"includeSawtoothFunction:true", // Added 
				"includeAbsValFunction:true", // Added
				"includeHalfLinearPiecewiseFunction:true", // In original Innovation Engine
				"includeStretchedTanhFunction:false",
				"includeReLUFunction:false",
				"includeSoftplusFunction:false",
				"includeLeakyReLUFunction:false",
				"includeFullSawtoothFunction:false",
				"includeTriangleWaveFunction:false", 
				"includeSquareWaveFunction:false"});
		MMNEAT.loadClasses();
		ActivationFunctions.resetFunctionSet();
		TWEANNGenotype tg = new TWEANNGenotype(PicbreederTask.CPPN_NUM_INPUTS, PicbreederTask.CPPN_NUM_OUTPUTS, 0);
		EvolutionaryHistory.initArchetype(0, null, (TWEANNGenotype) tg.copy());
		
		// Start with activation functions as identity
		for(NodeGene n : tg.nodes) {
			n.ftype = ActivationFunctions.FTYPE_ID;
		}
		
		tg.nodes.get(4).ftype = ActivationFunctions.FTYPE_ABSVAL; // output (hue?)
		tg.nodes.get(5).ftype = ActivationFunctions.FTYPE_GAUSS; // output (saturation?)
		tg.nodes.get(6).ftype = ActivationFunctions.FTYPE_SINE; // output (value/brightness?)
		
		tg.links.clear(); // erase links
		tg.addLink(-3, -7, 1, 1);
		tg.addLink(-2, -6, 1, 2);
		tg.addLink(-1, -5, 1, 3);
		
		tg.spliceNode(ActivationFunctions.FTYPE_SIGMOID, 4, -2, -6, 1.0, 1.0, 5, 6);
		tg.addLink(4, -7, 1.0, 7);
		tg.addLink(-4, -7, 1.0, 8);
		tg.addLink(-4, -5, 1.0, 9);

		tg.spliceNode(ActivationFunctions.FTYPE_SINE, 10, -1, -5, 1.0, 1.0, 10, 11);
		tg.addLink(10, -6, 1.0, 12);
		tg.addLink(10, -7, 1.0, 13);

		tg.spliceNode(ActivationFunctions.FTYPE_ABSVAL, 14, -2, 4, 1.0, 1.0, 15, 16);
		tg.addLink(14, -6, 1.0, 17);
		tg.addLink(-2, -7, 1.0, 18);

		tg.spliceNode(ActivationFunctions.FTYPE_GAUSS, 19, -3, -7, 1.0, 1.0, 19, 20);
		tg.addLink(19, -5, -1.0, 21);
		tg.addLink(14, -5, 1.0, 22);

		tg.spliceNode(ActivationFunctions.FTYPE_SIGMOID, 23, 19, -7, 1.0, 1.0, 24, 25);
		tg.addLink(-3, 23, 1.0, 26);
		
		for(String f : tg.getFunction()) {
			System.out.println(f);
		}
		
		DrawingPanel panel = new DrawingPanel(SIZE, SIZE, "Network");
		TWEANN network = tg.getPhenotype();
		network.draw(panel, true, false);

		// Now show the image
		BufferedImage image = GraphicsUtil.imageFromCPPN(network, SIZE, SIZE);
		DrawingPanel picture = GraphicsUtil.drawImage(image, "Image", SIZE, SIZE);

		picture.save("ComplexHSV.jpg");
		panel.save("ComplexHSVNet.jpg");
		// Wait for user
		MiscUtil.waitForReadStringAndEnterKeyPress();
		picture.dispose();
		panel.dispose();


	}	
}
