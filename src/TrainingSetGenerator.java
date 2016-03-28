import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;

public class TrainingSetGenerator {

	public static void main(String[] args) throws IOException, SQLException {
		FileWriter writer = new FileWriter("TrainingSet.txt", false);
		PrintWriter printer = new PrintWriter(writer);
		
		Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1/sys", "root", "matt");
		Statement s = con.createStatement();
		
		System.out.print("Generating training set...");

		// Run query to count most common makes in complaint database
		ResultSet rs = s.executeQuery(
				"select * from flat_cmpl where MAKETXT = 'Honda' or MAKETXT = 'Toyota' or MAKETXT = 'Nissan' or MAKETXT = 'Subaru' or MAKETXT = 'Mitsubishi'");

		int count = 1;
		int cap = getRowCount(rs);
		while (count <= cap) {
			printer.println(rs.getString(6) + "," + rs.getString(4) + "," + rs.getString(5) + ","
					+ rs.getString(12).split(":|\\,")[0] + "," + rs.getString(7) + "," + rs.getString(9) + ","
					+ rs.getString(10) + "," + rs.getString(11) + "," + rs.getString(20).replace(",","") + ", ,");
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
