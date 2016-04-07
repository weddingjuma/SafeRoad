import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.Test;

public class LookupTableTest {
	
	@Test
	public void test() throws SQLException {
		LookupTable lt = new LookupTable();
		assertTrue(lt.getStrToInt().get("ELEMENT") == 201);
		for (int i = 1; i < lt.getIntToStr().size(); i++)
			System.out.println(i + " " + lt.getIntToStr().get(i));
	}
}
