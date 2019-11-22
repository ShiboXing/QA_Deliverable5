import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.lang.reflect.Field;
import java.util.Random;

public class BeanCounterLogicTest {
	// TODO: implement
	// Be sure to mock your beans!

	BeanCounterLogic BCL;
	Bean[] beans1; //Bean array that contains the mock beans
	

	@Before
	public void setup()
	{
		BCL=new BeanCounterLogic(9);
		beans1=new Bean[15];
		
		for (int i =0;i<beans1.length;i++)
		{
			beans1[i]=Mockito.mock(Bean.class);
			Mockito.when(beans1[i].move()).thenReturn(true); //let every bean go left in every step
		}

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

	@Test
	/**
	 * checks if BCL's counter increments correctly upon calling advanceStep()
	 */
	public void testAdvanceStep1() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {

		
		Field counterField=BeanCounterLogic.class.getDeclaredField("_counter");
		counterField.setAccessible(true);
		
		
		
		for (int i=0;i<beans1.length;i++)
		{
			BCL.advanceStep();
			assertTrue(counterField.getInt(BCL)==i+1); //checks if the counter increments 
		}

		for(int i =0;i<100;i++) BCL.advanceStep();

		assertTrue(counterField.getInt(BCL)==beans1.length); //BCL's counter won't increment after reaching the beans array length
	}

	@Test
	/**
	 * checks if advanceStep calls Bean's move()
	 */
	public void testAdvanceStep2() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {

		Field beansField=BeanCounterLogic.class.getDeclaredField("_beans");
		beansField.setAccessible(true);
		beansField.set(BCL,beans1);

		for (int i=0;i<beans1.length;i++)
		{
			BCL.advanceStep();	
			Mockito.verify(beans1[i],Mockito.times(1)).move();
		}
	

	}

	public void testGetRemainingBeanCount(){

	}

	@After
	public void tearDown()
	{
		BCL=null;
		beans1=null;
		
	}
}
