import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

import net.sf.javaml.classification.Classifier;
import net.sf.javaml.classification.KNearestNeighbors;
import net.sf.javaml.classification.bayes.NaiveBayesClassifier;
import net.sf.javaml.classification.evaluation.EvaluateDataset;
import net.sf.javaml.classification.evaluation.PerformanceMeasure;
import net.sf.javaml.core.Dataset;
import net.sf.javaml.core.Instance;
import net.sf.javaml.featureselection.ranking.RecursiveFeatureEliminationSVM;
import net.sf.javaml.featureselection.scoring.GainRatio;
import net.sf.javaml.tools.data.FileHandler;

/**
 * Test class for SafeRoad classifier Tests accuracy for Naive Bayes and
 * K-Nearest Neighbors classifiers
 * 
 * @author Matthew Paradiso (matt74@vt.edu)
 *
 */
public class PredictionsTest {

	public static void main(String[] args) throws SQLException, IOException {

		System.out.println("Testing Naive Bayes Classification...");

		// Load training set
		Dataset data = FileHandler.loadDataset(new File("Training_Set.txt"), 9, ",");

		// Construct a Naive Bayes Classifier and apply training set
		Classifier nb = new NaiveBayesClassifier(true, true, true);
		nb.buildClassifier(data);

		// load data for classification
		Dataset dataForClassification = FileHandler.loadDataset(new File("Training_Set.txt"), 9, ",");
		float correctPos = 0;
		float expectedPos = 0;
		float predictedPos = 0;

		// Classify all instances
		for (Instance inst : dataForClassification) {
			Object predictedClassValue = nb.classify(inst);
			Object realClassValue = inst.classValue();

			if (predictedClassValue.toString().compareTo("R") == 0) {
				predictedPos++;
			}
			if (realClassValue.toString().compareTo("R") == 0) {
				expectedPos++;
			}
			if (realClassValue.toString().compareTo("R") == 0 && predictedClassValue.toString().compareTo("R") == 0) {
				correctPos++;
			} 
			
			//System.out.println("real: " + realClassValue + "  predicted: " + predictedClassValue);
			
		}

		float precision = correctPos / predictedPos;
		float recall = correctPos / expectedPos;
		float fscore = 2 * ((precision * recall) / (precision + recall));
		System.out.println("Naive Bayes Classifier Accuracy: " + fscore);
		
		System.out.println();
		System.out.println("Naive Bayes Attribute Scores: ");
		// Create a feature scoring algorithm
		GainRatio ga = new GainRatio();
		// Apply the algorithm to the data set
		ga.build(data);
		// Print out the score of each attribute
		System.out.println("Year: " + ga.score(0));
		System.out.println("Make: " + ga.score(1));
		System.out.println("Model: " + ga.score(2));
		System.out.println("Component: " + ga.score(3));
		System.out.println("Crash: " + ga.score(4));
		System.out.println("Fire: " + ga.score(5));
		System.out.println("Injuries: " + ga.score(6));
		System.out.println("Deaths: " + ga.score(7));
		
		System.out.println();
		System.out.println("Testing Naive Bayes Classification on short dataset...");

		// Load training set
		data = FileHandler.loadDataset(new File("Training_Set_Short.txt"), 5, ",");

		// apply training set
		nb.buildClassifier(data);

		// load data for classification
		dataForClassification = FileHandler.loadDataset(new File("Training_Set_Short.txt"), 5, ",");

		correctPos = 0;
		expectedPos = 0;
		predictedPos = 0;

		// Classify all instances
		for (Instance inst : dataForClassification) {
			Object predictedClassValue = nb.classify(inst);
			Object realClassValue = inst.classValue();

			if (predictedClassValue.toString().compareTo("R") == 0) {
				predictedPos++;
			}
			if (realClassValue.toString().compareTo("R") == 0) {
				expectedPos++;
			}
			if (realClassValue.toString().compareTo("R") == 0 && predictedClassValue.toString().compareTo("R") == 0) {
				correctPos++;
			} 
			
		}

		precision = correctPos / predictedPos;
		recall = correctPos / expectedPos;
		fscore = 2 * ((precision * recall) / (precision + recall));
		System.out.println("Naive Bayes Classifier Accuracy: " + fscore);
		
		
		System.out.println();
		System.out.println("Naive Bayes Attribute Scores (Short Dataset): ");
		// Create a feature scoring algorithm
		ga = new GainRatio();
		// Apply the algorithm to the data set
		ga.build(data);
		// Print out the score of each attribute
		System.out.println("Year: " + ga.score(0));
		System.out.println("Make: " + ga.score(1));
		System.out.println("Model: " + ga.score(2));
		System.out.println("Component: " + ga.score(3));
		
	
		System.out.println();
		System.out.println("Testing K-Nearest Neighbors Classification...");

		// Load training set, does not work with text values
		data = FileHandler.loadDataset(new File("Training_Set_No_Text.txt"), 4, ",");
		
		// Build a K-Nearest Neighbors classifier
		Classifier knn = new KNearestNeighbors(10);
		knn.buildClassifier(data);

		// load data for classification
		dataForClassification = FileHandler.loadDataset(new File("Training_Set_No_Text.txt"), 4, ",");

		correctPos = 0;
		expectedPos = 0;
		predictedPos = 0;

		// Classify all instances
		for (Instance inst : dataForClassification) {
			Object predictedClassValue = knn.classify(inst);
			Object realClassValue = inst.classValue();

			if (predictedClassValue.toString().compareTo("R") == 0) {
				predictedPos++;
			}
			if (realClassValue.toString().compareTo("R") == 0) {
				expectedPos++;
			}
			if (realClassValue.toString().compareTo("R") == 0 && predictedClassValue.toString().compareTo("R") == 0) {
				correctPos++;
			} 
			
		}

		precision = correctPos / predictedPos;
		recall = correctPos / expectedPos;
		fscore = 2 * ((precision * recall) / (precision + recall));
		System.out.println("K-Nearest Neighbors Classifier Accuracy: " + fscore);
		
		System.out.println();
		System.out.println("K-Nearest Neighbors Attribute Scores: ");
		// Create a feature scoring algorithm
		ga = new GainRatio();
		// Apply the algorithm to the data set
		ga.build(data);
		// Print out the score of each attribute
		System.out.println("Year: " + ga.score(0));
		System.out.println("Make: " + ga.score(1));
		System.out.println("Model: " + ga.score(2));
		System.out.println("Component: " + ga.score(3));
	}
}
