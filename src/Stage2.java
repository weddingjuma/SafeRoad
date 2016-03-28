import net.sf.javaml.classification.Classifier;
import net.sf.javaml.classification.KNearestNeighbors;
import net.sf.javaml.core.Dataset;
import net.sf.javaml.core.Instance;
import net.sf.javaml.tools.data.FileHandler;
import java.io.File;
import java.io.IOException;

public class Stage2 {

	public static void main(String[] args) throws IOException {
		
		/* Load a data set */
		Dataset data = FileHandler.loadDataset(new File("RecallSet.txt"), 5, ",");
		
		/*
		 * Construct a KNN classifier that uses 10 neighbors to make a decision.
		 */
		Classifier knn = new KNearestNeighbors(100);
		knn.buildClassifier(data);

		Dataset dataForClassification = FileHandler.loadDataset(new File("RecallSet.txt"), 5, ",");
		
		/* Counters for correct and wrong predictions. */
		//int correct = 0, wrong = 0;
		
		/* Classify all instances and check with the correct class values */
		for (Instance inst : dataForClassification) {
			Object predictedClassValue = knn.classify(inst);
			Object realClassValue = inst.classValue();
			System.out.println("Real: " + realClassValue + " Predicted: " + predictedClassValue);
			if (predictedClassValue != null)
				System.out.println(predictedClassValue.toString());
		}

		//System.out.println("Score: " + correct + "/" + (correct + wrong));
	}
}
