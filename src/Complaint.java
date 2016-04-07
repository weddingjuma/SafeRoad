
public class Complaint {
	String make;
	String comp1;
	String comp2;
	String comp3;
	
	public Complaint() {
		make = null;
		comp1 = null;
		comp2 = null;
		comp3 = null;
	}

	public Complaint(String m) {
		make = m;
		comp1 = null;
		comp2 = null;
		comp3 = null;
	}
	
	public Complaint(String m, String c1, String c2, String c3) {
		make = m;
		comp1 = c1;
		comp2 = c2;
		comp3 = c3;
		
		if (comp1.contains(":") || comp1.contains("/"))
			comp1 = comp1.split(":|/")[0];
		if (comp2.contains(":") || comp2.contains("/"))
			comp2 = comp2.split(":|/")[0];
		if (comp3.contains(":") || comp3.contains("/"))
			comp3 = comp3.split(":|/")[0];
	}
	
	public void setComp1(String c1) {
		comp1 = c1;
		if (comp1.contains(":") || comp1.contains("/"))
			comp1 = comp1.split(":|/")[0];
	}
	
	public void setComp2(String c2) {
		comp2 = c2;
		if (comp2.contains(":") || comp2.contains("/"))
			comp2 = comp2.split(":|/")[0];
	}
	
	public void setComp3(String c3) {
		comp3 = c3;
		if (comp3.contains(":") || comp3.contains("/"))
			comp3 = comp3.split(":|/")[0];
	}
	
}
