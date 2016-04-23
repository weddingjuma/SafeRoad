import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;

public class TrainingSetGenerator {

	public static void main(String[] args) throws IOException, SQLException {
		FileWriter writer = new FileWriter("TrainingSetE2.csv", false);
		PrintWriter printer = new PrintWriter(writer);
		
		Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1/sys", "root", "matt");
		Statement s = con.createStatement();
		
		System.out.print("Generating training set...");

		// Run query to count most common makes in complaint databaset
		// set criteria for training set here
		ResultSet rs = s.executeQuery(
				"select * from flat_cmpl where YEARTXT >= '2008' and MAKETXT = 'BMW' OR MAKETXT = 'VOLKSWAGEN' OR MAKETXT = 'AUDI' OR MAKETXT = 'PORSCHE' OR MAKETXT = 'MINI' OR MAKETXT = 'MERCEDES BENZ'");

		int count = 1;
		int cap = getRowCount(rs);
		while (count <= cap) {
			int fire;
			int crash;
			if (rs.getString(7).compareTo("Y") == 0)
				crash = 1;
			else 
				crash = 0;
			if (rs.getString(9).compareTo("Y") == 0)
				fire = 1;
			else 
				fire = 0;
			printer.println(rs.getString(6) + "," + rs.getString(4) + "," + rs.getString(5) + ","
					+ rs.getString(12).split(":|\\,")[0] + "," + crash + "," + fire + ","
					+ rs.getString(10) + "," + rs.getString(11) + ",\"" + rs.getString(20).replace(",","") + "\",U");
			count++;
			rs.next();
		}
		
		System.out.print(" done\n");
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