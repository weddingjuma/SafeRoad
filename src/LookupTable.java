import java.util.HashMap;
import java.sql.*;


public class LookupTable {
	public HashMap<String, Integer> strToInt;
	public HashMap<Integer, String> intToStr;
	
	public LookupTable() throws SQLException {
		strToInt = new HashMap<String, Integer>();
		intToStr = new HashMap<Integer, String>();
		
		//add makes to table
		strToInt.put("FORD", 1);
		intToStr.put(1,  "FORD");
		strToInt.put("CHEVROLET", 2);
		intToStr.put(2, "CHEVROLET");
		strToInt.put("TOYOTA", 3);
		intToStr.put(3,  "TOYOTA");
		strToInt.put("DODGE", 4);
		intToStr.put(4, "DODGE");
		strToInt.put("JEEP", 5);
		intToStr.put(5, "JEEP");
		strToInt.put("NISSAN", 6);
		intToStr.put(6, "NISSAN");
		strToInt.put("HONDA", 7);
		intToStr.put(7, "HONDA");
		strToInt.put("CHRYSLER", 8);
		intToStr.put(8, "CHRYSLER");
		strToInt.put("HYUNDAI", 9);
		intToStr.put(9, "HYUNDAI");
		strToInt.put("GMC", 10);
		intToStr.put(10, "GMC");
		strToInt.put("PONTIAC", 11);
		intToStr.put(11, "PONTIAC");
		strToInt.put("VOLKSWAGEN", 12);
		intToStr.put(12, "VOLKSWAGEN");
		strToInt.put("BMW", 13);
		intToStr.put(13, "BMW");
		strToInt.put("SATURN", 14);
		intToStr.put(14, "SATURN");
		strToInt.put("KIA", 15);
		intToStr.put(15, "KIA");
		strToInt.put("MERCEDES BENZ", 16);
		intToStr.put(16, "MERCEDES BENZ");
		strToInt.put("MAZDA", 17);
		intToStr.put(17, "MAZDA");
		strToInt.put("BUICK", 18);
		intToStr.put(18, "BUICK");
		strToInt.put("MERCURY", 19);
		intToStr.put(19, "MERCURY");
		strToInt.put("SUBARU", 20);
		intToStr.put(20, "SUBARU");
		strToInt.put("CADILLAC", 21);
		intToStr.put(21, "CADILLAC");
		strToInt.put("LEXUS", 22);
		intToStr.put(22, "LEXUS");
		strToInt.put("VOLVO", 23);
		intToStr.put(23, "VOLVO");
		strToInt.put("ACURA", 24);
		intToStr.put(24, "ACURA");
		strToInt.put("SUZUKI", 25);
		intToStr.put(25, "SUZUKI");
		strToInt.put("MINI", 26);
		intToStr.put(26, "MINI");
		strToInt.put("MITSUBISHI", 27);
		intToStr.put(27, "MITSUBISHI");
		strToInt.put("INFINITI", 28);
		intToStr.put(28, "INFINITI");
		strToInt.put("LINCOLN", 29);
		intToStr.put(29, "LINCOLN");
		strToInt.put("AUDI", 30);
		intToStr.put(30, "AUDI");
		strToInt.put("RAM", 31);
		intToStr.put(31, "RAM");
		strToInt.put("OLDSMOBILE", 32);
		intToStr.put(32, "OLDSMOBILE");
		strToInt.put("LAND ROVER", 33);
		intToStr.put(33, "LAND ROVER");
		strToInt.put("SAAB", 34);
		intToStr.put(34, "SAAB");
		strToInt.put("SCION", 35);
		intToStr.put(35, "SCION");
		strToInt.put("ISUZU", 36);
		intToStr.put(36, "ISUZU");
		strToInt.put("YAMAHA", 37);
		intToStr.put(37, "YAMAHA");
		strToInt.put("JAGUAR", 38);
		intToStr.put(38, "JAGUAR");
		strToInt.put("HUMMER", 39);
		intToStr.put(39, "HUMMER");
		strToInt.put("DAEWOO", 40);
		intToStr.put(40, "DAEWOO");
		
		//add component systems to tables
		strToInt.put("WHEELS", 101);
		intToStr.put(101, "WHEELS");
		strToInt.put("EXTERIOR LIGHTING", 102);
		intToStr.put(102, "EXTERIOR LIGHTING");
		strToInt.put("SERVICE BRAKES", 103);
		intToStr.put(103, "SERVICE BRAKES");
		strToInt.put("SUSPENSION", 104);
		intToStr.put(104, "SUSPENSION");
		strToInt.put("AIR BAGS", 105);
		intToStr.put(105, "AIR BAGS");
		strToInt.put("STRUCTURE", 106);
		intToStr.put(106, "STRUCTURE");
		strToInt.put("VISIBILITY", 107);
		intToStr.put(107, "VISIBILITY");
		strToInt.put("ENGINE AND ENGINE COOLING", 108);
		intToStr.put(108, "ENGINE AND ENGINE COOLING");
		strToInt.put("FUEL SYSTEM", 109);
		intToStr.put(109, "FUEL SYSTEM");
		strToInt.put("SEAT BELTS", 110);
		intToStr.put(110, "SEAT BELTS");
		strToInt.put("POWER TRAIN", 111);
		intToStr.put(111, "POWER TRAIN");
		strToInt.put("VEHICLE SPEED CONTROL", 112);
		intToStr.put(112, "VEHICLE SPEED CONTROL");
		strToInt.put("TIRES", 113);
		intToStr.put(113, "TIRES");
		strToInt.put("ELECTRICAL SYSTEM", 114);
		intToStr.put(114, "ELECTRICAL SYSTEM");
		strToInt.put("STEERING", 115);
		intToStr.put(115, "STEERING");
		strToInt.put("PARKING BRAKE", 116);
		intToStr.put(116, "PARKING BRAKE");
		strToInt.put("SEATS", 117);
		intToStr.put(117, "SEATS");
		strToInt.put("UNKNOWN OR OTHER", 118);
		intToStr.put(118, "UNKNOWN OR OTHER");
		strToInt.put("ELECTRONIC STABILITY CONTROL", 119);
		intToStr.put(119, "ELECTRONIC STABILITY CONTROL");
		strToInt.put("ENGINE", 120);
		intToStr.put(120, "ENGINE");
		strToInt.put("EQUIPMENT", 121);
		intToStr.put(121, "EQUIPMENT");
		strToInt.put("LATCHES", 122);
		intToStr.put(122, "LATCHES");
		strToInt.put("TRANSMISSION", 123);
		intToStr.put(123, "TRANSMISSION");
		
		
		//add all vehicle makes to table
		Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1/sys", "root", "matt");
		Statement s = con.createStatement();
		ResultSet models = s.executeQuery(
				"select distinct MODELTXT from flat_cmpl");
		int modelCount = 201;
		while (models.next()) {
			strToInt.put(models.getString(1).toString(), modelCount);
			intToStr.put(modelCount, models.getString(1).toString());
			modelCount++;
		}
		
	}
	
	public HashMap<String, Integer> getStrToInt() {
		return strToInt;
	}
	
	public HashMap<Integer, String> getIntToStr() {
		return intToStr;
	}
	
	public int getInt(String str) {
		return strToInt.get(str);			
	}
	
	public String getStr(int i) {
		String str = intToStr.get(i);
		if (str == null)
			return "";
		else 
			return str;
	}

}
