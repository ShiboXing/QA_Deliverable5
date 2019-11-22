import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.Random;

public class BeanCounterLogicTest {
	// TODO: implement
	// Be sure to mock your beans!

	BeanCounterLogic BCL;

	@Before
	public void setup()
	{
		
	}

	@Test
	/**
	 * Test the constructor, check if the parameter is assigned correctly.
	 */
	public void TestBeanCounterLogic() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException{
		Field numOfBeansField=BeanCounterLogic.class.getDeclaredField("_numOfSlots");
		Field counter=BeanCounterLogic.class.getDeclaredField("_counter");
		counter.setAccessible(true);
		numOfBeansField.setAccessible(true);
		
		BCL=new BeanCounterLogic(524);
		assertTrue((int)numOfBeansField.get(BCL)==524);
		assertTrue((int)counter.get(BCL)==0);
	}

	public void testGetRemainingBeanCount(){
		
	}

	@After
	public void tearDown()
	{

	}
}
