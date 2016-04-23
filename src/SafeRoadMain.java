import java.io.File;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import net.sf.javaml.classification.Classifier;
import net.sf.javaml.classification.bayes.NaiveBayesClassifier;
import net.sf.javaml.core.Dataset;
import net.sf.javaml.core.Instance;
import net.sf.javaml.tools.data.FileHandler;

/*
 * Main program for SafeRoad. 
 * Runs functionality of stage1 and stage2 together.
 */
public class SafeRoadMain {

	public static void main(String[] args) throws IOException, SQLException {
		
		System.out.println("Initializing...");
		
		// file for common complaints to be written to
		FileWriter writer = new FileWriter("CommonComplaints.csv", false);
		PrintWriter printer = new PrintWriter(writer);

		// connect to local database. Can be changed to connect to remote
		// database.
		// getConncetion parameter 1 is the address of the database
		// getConnection parameter 2 is the user name for the database
		// getConnection parameter 3 is the password for the database
		Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1/sys", "root", "matt");
		Statement s = con.createStatement();

		// Build lookup table
		LookupTable lt = new LookupTable();

		System.out.print("Determining most common complaint makes... ");

		// Run query to count most common makes in complaint database
		ResultSet makeCount = s.executeQuery(
				"select distinct MAKETXT, count(*) from flat_cmpl group by MAKETXT ORDER BY count(*) DESC");

		// set cap to the number of makes to include in complaints
		// ex: cap = 5 will include top 5 most complained about makes
		int count = 0;
		int cap = 5;
		ArrayList<Complaint> complaints = new ArrayList<Complaint>();
		makeCount.next();
		while (count < cap) {
			Complaint complaint = new Complaint(makeCount.getString(1).toString());
			complaints.add(complaint);
			count++;
			makeCount.next();
		}

		System.out.print("done\n");

		// find the top 3 most common component complaints for each make
		System.out.print("Determining most common complaint components... ");

		for (Complaint complaint : complaints) {
			ResultSet compCount = s.executeQuery("select distinct COMPDESC, count(*) from flat_cmpl WHERE MAKETXT = '"
					+ complaint.make + "' group by COMPDESC ORDER BY count(*) DESC");
			compCount.first();
			complaint.setComp1(compCount.getString(1).toString());
			compCount.next();
			complaint.setComp2(compCount.getString(1).toString());
			compCount.next();
			complaint.setComp3(compCount.getString(1).toString());
		}

		System.out.print("done\n");

		// build SQL query
		String filter = "";
		boolean first = true;
		for (Complaint complaint : complaints) {
			if (first) {
				filter += "(MAKETXT = '" + complaint.make + "' && (COMPDESC = '" + complaint.comp1 + "' || COMPDESC = '"
						+ complaint.comp2 + "' || COMPDESC = '" + complaint.comp3 + "'))";
				first = false;
			} else
				filter += " || (MAKETXT = '" + complaint.make + "' && (COMPDESC = '" + complaint.comp1
						+ "' || COMPDESC = '" + complaint.comp2 + "' || COMPDESC = '" + complaint.comp3 + "'))";
		}

		System.out.print("Determining most common complaints... ");

		ResultSet common = s.executeQuery("SELECT * FROM flat_cmpl WHERE " + filter + " ORDER BY YEARTXT ASC");

		// convert boolean values to 0 (false) and 1 (true)
		count = 1;
		cap = getRowCount(common);
		while (count <= cap) {
			int fire;
			int crash;
			if (common.getString(7).compareTo("Y") == 0)
				crash = 1;
			else
				crash = 0;
			if (common.getString(9).compareTo("Y") == 0)
				fire = 1;
			else
				fire = 0;

			// print to file, convert remaining text to mapped numerical values
			printer.println(common.getString(6) + "," + lt.getInt(common.getString(4)) + ","
					+ lt.getInt(common.getString(5)) + "," + lt.getInt(common.getString(12)) + "," + crash + "," + fire
					+ "," + common.getString(10) + "," + common.getString(11) + ",\""
					+ common.getString(20).replace(",", "") + "\",U");
			count++;
			common.next();
		}

		System.out.print("done\n");
		System.out.println("Results printed to file \"CommonComplaints.csv\"");
		System.out.println();
		System.out.print("Classifying complaints from CommonComplaints.csv...");

		// set file to print results to
		FileWriter writer2 = new FileWriter("PredictedRecalls.csv", false);
		PrintWriter printer2 = new PrintWriter(writer2);

		// Load training set
		Dataset data = FileHandler.loadDataset(new File("Training_Set.csv"), 9, ",");

		// Construct a Naive Bayes Classifier and apply training set
		Classifier nb = new NaiveBayesClassifier(true, true, true);
		nb.buildClassifier(data);

		// load unclassified data for classification
		Dataset dataForClassification = FileHandler.loadDataset(new File("CommonComplaints.csv"), 8, ",");

		// Classify all instances
		for (Instance inst : dataForClassification) {
			Object predictedClassValue = nb.classify(inst);
			if (predictedClassValue.toString().compareTo("R") == 0)
				printer2.println((int) inst.value(0) + "," + lt.getStr((int) inst.value(1)) + ","
						+ lt.getStr((int) inst.value(2)) + "," + lt.getStr((int) inst.value(3)) + ","
						+ inst.toString().split(";")[1]);
		}

		System.out.print(" done\n");
		System.out.println("Predicted recalls printed to file \"PredictedRecalls.csv\"");

		printer.close();
		printer2.close();
	}

	/**
	 * returns the number of rows in ResultSet
	 */
	public static int getRowCount(ResultSet rs) throws SQLException {
		try {
			rs.last();
			int rows = rs.getRow();
			rs.first();
			return rows;
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}
}
