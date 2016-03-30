import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import net.sf.javaml.classification.Classifier;
import net.sf.javaml.classification.bayes.NaiveBayesClassifier;
import net.sf.javaml.core.Dataset;
import net.sf.javaml.core.Instance;
import net.sf.javaml.tools.data.FileHandler;

public class Stage2 {

	public static void main(String[] args) throws IOException {
		
		/* set file to print results to */
		FileWriter writer = new FileWriter("PredictedRecalls.txt", false);
		PrintWriter printer = new PrintWriter(writer);
		
		/* Load a data set */
		Dataset data = FileHandler.loadDataset(new File("TrainingSetSmall.txt"), 9, ",");
		
		/*
		 * Construct a Naive Bayes Classifier
		 */
		Classifier nb = new NaiveBayesClassifier(true, true, true);
		nb.buildClassifier(data);

		Dataset dataForClassification = FileHandler.loadDataset(new File("TrainingSetSmallEmpty.txt"), 8, ",");
				
		/* Classify all instances */
		for (Instance inst : dataForClassification) {
			Object predictedClassValue = nb.classify(inst);
			//Object realClassValue = inst.classValue();
			if (predictedClassValue.toString().compareTo("R") == 0)
				printer.println(inst);
		}

		System.out.println("Predicted recalls printed to file \"PredictedRecalls.txt\"");
		printer.close();
	}
}