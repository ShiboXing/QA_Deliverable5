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
	 * check if getRemainingBenaCount returns the correct value
	 *
	 */
	public void testGetRemainingBeanCount() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		Field beansField=BeanCounterLogic.class.getDeclaredField("_beans");
		beansField.setAccessible(true);
		beansField.set(BCL,beans1);
		
		for (int i=0;i<beans1.length-5;i++)
			BCL.advanceStep();
		
		assertTrue(BCL.getRemainingBeanCount()==5);
	}

	@Test
	/**
	 * checks if the BCL returns the correct X value of a bean
	 */
	public void testGetInFlightBeanXPos() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		Mockito.when(beans1[0].getX()).thenReturn(4);
		Mockito.when(beans1[1].getX()).thenReturn(7);
		Field beansField=BeanCounterLogic.class.getDeclaredField("_beans");
		beansField.setAccessible(true);
		beansField.set(BCL,beans1);
		for(int i=0;i<4;i++) //beans[0] should have a y coordinate at 3, beans[0] 7
			BCL.advanceStep();
	
		assertTrue(BCL.getInFlightBeanXPos(3)==4);
		assertTrue(BCL.getInFlightBeanXPos(4)==7);
		
	}

	@Test
	/**
	 * check if reset() sets the beans array correctly
	 */
	public void testReset1() throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
		Field beansField=BeanCounterLogic.class.getDeclaredField("_beans");
		beansField.setAccessible(true);
		beansField.set(BCL,beans1);

		Bean[] testBeans=new Bean[24];
		BCL.reset(testBeans);
		
		assertTrue(beansField.get(BCL)==testBeans);
		
	}

	@Test
	/**
	 * check if reset() set _counter to 0
	 */
	public void testReset2() throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
		Field beansField=BeanCounterLogic.class.getDeclaredField("_beans");
		Field counterField=BeanCounterLogic.class.getDeclaredField("_counter");
		beansField.setAccessible(true);
		counterField.setAccessible(true);
		beansField.set(BCL,beans1);

		for(int i=0;i<beans1.length;i++)
			BCL.advanceStep();

		BCL.reset(null);

		assertTrue(counterField.getInt(BCL)==0);
		
	}


	@Test
	/**
	 * checks if BCL's counter increments correctly upon calling advanceStep()
	 * and advanceStep() stops after all beans have been released
	 */
	public void testAdvanceStep1() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {

		Field counterField=BeanCounterLogic.class.getDeclaredField("_counter");
		Field beansField=BeanCounterLogic.class.getDeclaredField("_beans");
		beansField.setAccessible(true);
		counterField.setAccessible(true);
		beansField.set(BCL,beans1);
		
		for (int i=0;i<beans1.length;i++)
		{
			BCL.advanceStep();
			assertTrue(counterField.getInt(BCL)==i+1); //checks if the counter increments 
		}

		for(int i =0;i<100;i++) 
			assertFalse(BCL.advanceStep());

		
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

	


	@After
	public void tearDown()
	{
		BCL=null;
		beans1=null;
		
	}
}
