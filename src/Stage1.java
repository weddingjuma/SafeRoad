import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

public class Stage1 {

	public static void main(String[] args) throws SQLException, IOException {

		FileWriter writer = new FileWriter("CommonComplaints.txt", false);
		PrintWriter printer = new PrintWriter(writer);

		Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1/sys", "root", "matt");
		Statement s = con.createStatement();
		System.out.print("Determining most common complaint makes... ");

		// Run query to count most common makes in complaint database
		ResultSet makeCount = s.executeQuery(
				"select distinct MAKETXT, count(*) from flat_cmpl group by MAKETXT ORDER BY count(*) DESC");

		// Add most commmon 1% of makes to common complaint database
		int count = 0;
		int cap = (int) (0.01 * getRowCount(makeCount));
		ArrayList<Complaint> complaints = new ArrayList<Complaint>();
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
			printer.println(common.getString(6) + "," + common.getString(4) + "," + common.getString(5) + ","
					+ common.getString(12) + "," + crash + "," + fire + ","
					+ common.getString(10) + "," + common.getString(11) + ",\"" + common.getString(20).replace(",","") + "\",U");
			count++;
			common.next();
		}

		System.out.print("done\n");
		System.out.println("Results printed to file \"CommonComplaints.txt\"");
		printer.close();
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