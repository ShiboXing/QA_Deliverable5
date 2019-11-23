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
		Field numOfSlotsField=BeanCounterLogic.class.getDeclaredField("_numOfSlots");
		
		Field counter=BeanCounterLogic.class.getDeclaredField("_counter");
		counter.setAccessible(true);
		numOfSlotsField.setAccessible(true);	
		
		Field slotsField=BeanCounterLogic.class.getDeclaredField("_slots");
		slotsField.setAccessible(true);

		
		BCL=new BeanCounterLogic(524);


		assertTrue((int)numOfSlotsField.get(BCL)==524);
		int[] slots=(int[])slotsField.get(BCL);
		assertTrue(slots.length==524);
		for(int i:slots) assertTrue(i==0);
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

		Field counter=BeanCounterLogic.class.getDeclaredField("_counter");
		counter.setAccessible(true); 
		counter.set(BCL, 4);//beans[1] should have a y coordinate at 3, beans[0] at 4
		
		//System.out.println(BCL.getInFlightBeanXPos(3)+" "+BCL.getInFlightBeanXPos(4));
		assertTrue(BCL.getInFlightBeanXPos(3)==7);
		assertTrue(BCL.getInFlightBeanXPos(4)==4);
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

	@Test
	/**
	 * check if advanceStep correctly places bean inside a slot at the bottom level
	 */
	public void testAdvanceStep3() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		Field beansField=BeanCounterLogic.class.getDeclaredField("_beans");
		beansField.setAccessible(true);
		beansField.set(BCL,beans1);
		Field SlotNumField=BeanCounterLogic.class.getDeclaredField("_numOfSlots");
		SlotNumField.setAccessible(true);
		int slotNum=SlotNumField.getInt(BCL);
		Mockito.when(beans1[0].getY()).thenReturn(slotNum-1); //at the slot level
		Mockito.when(beans1[0].getX()).thenReturn(4);

		Field slotsField=BeanCounterLogic.class.getDeclaredField("_slots");
		slotsField.setAccessible(true);
		int[] slots=(int[]) slotsField.get(BCL); //retrieve the slots array


		assertTrue(slots[0]==0);
		BCL.advanceStep(); //increment the slot count
		assertTrue(slots[0]==1);
	
	}


	@After
	public void tearDown()
	{
		BCL=null;
		beans1=null;
		
	}
}
