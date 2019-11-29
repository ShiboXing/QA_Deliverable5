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
	

    /**
     * initialize the tests
     */
    @Before
	public void setup() {
		BCL = new BeanCounterLogic(9);
		beans1 = new Bean[15];
		
		for (int i = 0;i < beans1.length;i++) {
			beans1[i] = Mockito.mock(Bean.class);
			Mockito.when(beans1[i].move()).thenReturn(true); //let every bean go left in every step
		}

	}

	@Test
	/**
	 * Test the constructor, check if the parameter is assigned correctly.
	 */
    public void testBeanCounterLogic() throws NoSuchFieldException, 
        SecurityException, IllegalArgumentException, IllegalAccessException {
		final Field numOfSlotsField = BeanCounterLogic.class.getDeclaredField("_numOfSlots");
		
        final Field counter = BeanCounterLogic
            .class.getDeclaredField("_counter");
		counter.setAccessible(true);
		numOfSlotsField.setAccessible(true);	
		
        final Field slotsField = BeanCounterLogic
            .class.getDeclaredField("_slots");
		slotsField.setAccessible(true);

		
		BCL = new BeanCounterLogic(524);


		assertTrue((int)numOfSlotsField.get(BCL) == 524);
		final int[] slots = (int[])slotsField.get(BCL);
		assertTrue(slots.length == 524);
		for (final int i:slots) {
            assertTrue(i == 0);
        }
		assertTrue((int)counter.get(BCL) == 0);
	}

	@Test
	/**
	 * check if getRemainingBenaCount returns the correct value
	 *
	 */
    public void testGetRemainingBeanCount() throws NoSuchFieldException, 
            SecurityException, IllegalArgumentException, IllegalAccessException {
        final Field beansField = BeanCounterLogic
            .class.getDeclaredField("_beans");
		beansField.setAccessible(true);
		beansField.set(BCL,beans1);
		
		for (int i = 0;i < beans1.length - 5;i++) {
            BCL.advanceStep();
        }
		
		assertTrue(BCL.getRemainingBeanCount() == 5);
	}

	@Test
	/**
	 * checks if the BCL returns the correct X value of a bean
	 */
    public void testGetInFlightBeanXPos() throws NoSuchFieldException,
            SecurityException, IllegalArgumentException, IllegalAccessException {
		Mockito.when(beans1[0].getX()).thenReturn(4);
		Mockito.when(beans1[1].getX()).thenReturn(7);
        final Field beansField = BeanCounterLogic
            .class.getDeclaredField("_beans");
		beansField.setAccessible(true);
		beansField.set(BCL,beans1);

        final Field counter = BeanCounterLogic
            .class.getDeclaredField("_counter");
		counter.setAccessible(true); 
		counter.set(BCL, 4);//beans[1] should have a y coordinate at 3, beans[0] at 4
		
		//System.out.println(BCL.getInFlightBeanXPos(3)+" "+BCL.getInFlightBeanXPos(4));
		assertTrue(BCL.getInFlightBeanXPos(3) == 7);
		assertTrue(BCL.getInFlightBeanXPos(4) == 4);
	}

	@Test
	/**
	 * check if getSlotBeanCount returns the correct bean number in a slot
	 */
    public void testGetSlotBeanCount() throws NoSuchFieldException,
         SecurityException, IllegalArgumentException, IllegalAccessException {
        final Field slotsField = BeanCounterLogic
            .class.getDeclaredField("_slots");
		slotsField.setAccessible(true);
		final int[] slots = new int[20];
		slots[13] = 6666;
		slotsField.set(BCL,slots);
		
		assertTrue(BCL.getSlotBeanCount(13) == 6666);
	}

	@Test
	/**
	 * check if AverageSlotBeanCount returns the correct average bean number
	 */
    public void testAverageSlotBeanCount() throws NoSuchFieldException,
             SecurityException, IllegalArgumentException, IllegalAccessException {
        final Field slotsField = BeanCounterLogic
            .class.getDeclaredField("_slots");
        final Field beanCountField = BeanCounterLogic
            .class.getDeclaredField("_beanCount");
		slotsField.setAccessible(true);
		beanCountField.setAccessible(true);
		final int[] slots = new int[66];
		slotsField.set(BCL,slots);
		for (int i = 0;i < slots.length;i++) {
            slots[i] = 4;
        }
		beanCountField.set(BCL,4 * slots.length);

		assertTrue(BCL.getAverageSlotBeanCount() == 32.5);

		for (int i = 0;i < slots.length;i++) {
            slots[i] = 0;
        }
		slots[41] = 123;
		assertTrue(BCL.getAverageSlotBeanCount() == 123 * 41.0 / (4 * slots.length));
	 }
	 
	 @Test
	 /**
	  * check if upperHalf() resets the lower half of beanCount array properly 
	  */
     public void testUpperHalf() throws NoSuchFieldException,
            SecurityException, IllegalArgumentException, IllegalAccessException {
        final Field slotsField = BeanCounterLogic
            .class.getDeclaredField("_slots");
		slotsField.setAccessible(true);
        final int[] slots = {1,23,24,14,45,25,98,57,
            400,343,97,535,13,24,53,5,352,52,42,424};
		slotsField.set(BCL,slots);
		
		BCL.upperHalf();
		for (int i = 0;i < slots.length / 2;i++) {
            assertTrue(slots[i] == 0);
        }

	 }

	 @Test
	 /**
	  * check if the lowerHalf() reset the higher half of beanCount array properly
	  */
     public void testLowerHalf() throws NoSuchFieldException,
             SecurityException, IllegalArgumentException, IllegalAccessException {
        final Field slotsField = BeanCounterLogic
            .class.getDeclaredField("_slots");
		slotsField.setAccessible(true);
        final int[] slots = {1,23,24,14,45,25,98,57,
            400,343,97,535,13,24,53,5,352,52,42,424};
		slotsField.set(BCL,slots);
		BCL.lowerHalf();
		
		for (int i = slots.length / 2;i < slots.length;i++) {  
                assertTrue(slots[i] == 0);
        }
		
	 }



	@Test
	/**
	 * check if reset() sets the beans array correctly
	 */
    public void testReset1() throws IllegalArgumentException, 
            IllegalAccessException, NoSuchFieldException, SecurityException {
        final Field beansField = BeanCounterLogic
            .class.getDeclaredField("_beans");
		beansField.setAccessible(true);
		beansField.set(BCL,beans1);

		final Bean[] testBeans = new Bean[24];
		BCL.reset(testBeans);
		
		assertTrue(beansField.get(BCL) == testBeans);
		
	}

	@Test
	/**
	 * check if reset() set _counter to 0
	 */
    public void testReset2() throws IllegalArgumentException,
             IllegalAccessException, NoSuchFieldException, SecurityException {
        final Field beansField = BeanCounterLogic
            .class.getDeclaredField("_beans");
        final Field counterField = BeanCounterLogic
            .class.getDeclaredField("_counter");
        final Field beanCountField = BeanCounterLogic
            .class.getDeclaredField("_beanCount");
		beansField.setAccessible(true);
		counterField.setAccessible(true);
		beanCountField.setAccessible(true);
		beansField.set(BCL,beans1);
		beanCountField.set(BCL,100);

		for (int i = 0; i < beans1.length; i++) {
			BCL.advanceStep();
		}

		BCL.reset(null);
		
		assertTrue(beanCountField.getInt(BCL) == 0);
		assertTrue(counterField.getInt(BCL) == 0);
		
	}


	@Test
	/**
	 * check if repeat() recovers the counter, count of beans per slot
	 */
    public void testRepeat1() throws NoSuchFieldException,
             SecurityException, IllegalArgumentException, IllegalAccessException {
        final Field slotsField = BeanCounterLogic
            .class.getDeclaredField("_slots");
        final Field numberOfSlotsField = BeanCounterLogic
            .class.getDeclaredField("_numOfSlots");
        final Field beanCountField = BeanCounterLogic
            .class.getDeclaredField("_beanCount");
        final Field counterField = BeanCounterLogic
            .class.getDeclaredField("_counter");
        final Field BeansField = BeanCounterLogic
            .class.getDeclaredField("_beans");
		slotsField.setAccessible(true);
		numberOfSlotsField.setAccessible(true);
		counterField.setAccessible(true);
		BeansField.setAccessible(true);
		beanCountField.setAccessible(true);

		

		BeansField.set(BCL,beans1);
		final int[] slots = {2,3,4,1,5,2,4,4}; //insert the _slots array
		slotsField.set(BCL,slots);
		numberOfSlotsField.set(BCL,slots.length);
		counterField.set(BCL,5);
		beanCountField.set(BCL,100);

		BCL.repeat();

		assertTrue(counterField.getInt(BCL) == 0);
		assertTrue(beanCountField.getInt(BCL) == 0);
		for (final int i:slots) {
            assertTrue(i == 0);
        }

	}

	@Test
	/**
	 * check if the repeat() resets all beans their initial positions
	 */
    public void testRepeat2() throws NoSuchFieldException, 
            SecurityException, IllegalArgumentException, IllegalAccessException {
        final Field BeansField = BeanCounterLogic
            .class.getDeclaredField("_beans");
        final Field counterField = BeanCounterLogic
            .class.getDeclaredField("_counter");
		BeansField.setAccessible(true);
		counterField.setAccessible(true);
		BeansField.set(BCL,beans1);
		final int counter = 5;
		counterField.set(BCL,counter);

		BCL.repeat();
		for (int i = 0;i < counter;i++) {

			Mockito.verify(beans1[i],Mockito.times(1)).reset();
		}
		
		
	}

	@Test
	/**
	 * checks if BCL's counter increments correctly upon calling advanceStep()
	 * and advanceStep() stops after all beans have been released
	 */
    public void testAdvanceStep1() throws NoSuchFieldException, 
            SecurityException, IllegalArgumentException, IllegalAccessException {

        final Field counterField = BeanCounterLogic
            .class.getDeclaredField("_counter");
        final Field beansField = BeanCounterLogic
            .class.getDeclaredField("_beans");
        final Field slotsField = BeanCounterLogic
            .class.getDeclaredField("_slots");
		beansField.setAccessible(true);
		counterField.setAccessible(true);
		slotsField.setAccessible(true);
		final int[] slots = (int[])slotsField.get(BCL);
		beansField.set(BCL,beans1);
		
		for (int i = 0;i < beans1.length + slots.length;i++) {
			BCL.advanceStep();
			assertTrue(counterField.getInt(BCL) == i + 1); //checks if the counter increments 
		}

		for (int i = 0;i < 100;i++) {
            assertFalse(BCL.advanceStep());
        }

		//BCL's counter won't increment after reaching the beans array length
		assertTrue(counterField.getInt(BCL) == beans1.length + slots.length); 
	}

	@Test
	/**
	 * checks if advanceStep calls Bean's move()
	 */
    public void testAdvanceStep2() throws NoSuchFieldException, 
        SecurityException, IllegalArgumentException, IllegalAccessException {

        final Field beansField = BeanCounterLogic
            .class.getDeclaredField("_beans");
		beansField.setAccessible(true);
		beansField.set(BCL,beans1);

		for (int i = 0;i < beans1.length;i++) {
			BCL.advanceStep();	
			Mockito.verify(beans1[i],Mockito.times(1)).move();
		}
	}

	@Test
	/**
	 * check if advanceStep correctly places bean inside a slot at the bottom level
	 */
    public void testAdvanceStep3() throws NoSuchFieldException, 
            SecurityException, IllegalArgumentException, IllegalAccessException {
        final Field beansField = BeanCounterLogic
            .class.getDeclaredField("_beans");
		beansField.setAccessible(true);
		beansField.set(BCL,beans1);
        final Field SlotNumField = BeanCounterLogic
            .class.getDeclaredField("_numOfSlots");
		SlotNumField.setAccessible(true);
		final int slotNum = SlotNumField.getInt(BCL);
		
		//create a stubbing on beans1[0] that puts this bean one level above the slots
		Mockito.when(beans1[0].getY()).thenReturn(slotNum - 1); 
		Mockito.when(beans1[0].getX()).thenReturn(4); //make it go to slot 4

        final Field slotsField = BeanCounterLogic
            .class.getDeclaredField("_slots");
		slotsField.setAccessible(true);
		final int[] slots = (int[]) slotsField.get(BCL); //retrieve the slots array


		assertTrue(slots[4] == 0);
		BCL.advanceStep(); 
		Mockito.verify(beans1[0],Mockito.times(1)).move();
		assertTrue(slots[4] == 1);
	
	}
	
    @Test
    /**
    * test for the main method's luck mode
    */
	public void testMainLuck() {
		final String[] args = new String[2];
		args[0] = "100";
		args[1] = "luck";
		
		assertTrue(BCL.runGame(args));
	}
	
    @Test
    /**
     * test for the main method's skill mode
     */
	public void testMainSkill() {
		final String[] args = new String[2];
		args[0] = "100";
		args[1] = "luck";
		
		assertTrue(BCL.runGame(args));
	}
	
    @Test
    /**
     * test for the main method's test mode
     */
	public void testMainTest() {
		final String[] args = new String[1];
		args[0] = "test";
		assertTrue(BCL.runGame(args));
	}

    @After
    /**
     * deconstruct the test
     */
	public void tearDown() {
		BCL = null;
		beans1 = null;
	}
}
