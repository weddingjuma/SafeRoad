import static org.junit.Assert.*;

import org.junit.Test;

/**
 * 
 */

/**
 * @author Matt
 *
 */
public class ComplaintTest {

	@Test
	public void test1() {
		Complaint common = new Complaint("Ford", "Brakes", "Air Bags: Front", "Power Train: Transmission: TCM");
		System.out.println(common.make);
		System.out.println(common.comp1);
		System.out.println(common.comp2);
		System.out.println(common.comp3);
		
		assertEquals(common.make, "Ford");
		assertEquals(common.comp1, "Brakes");
		assertEquals(common.comp2, "Air Bags");
		assertEquals(common.comp3, "Power Train");		
	}

}
