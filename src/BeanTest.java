import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sun.security.krb5.internal.KDCOptions;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Random;

public class BeanTest {
	// TODO: implement
	// Be sure to mock Random if you don't want randomness during testing!

	Bean bean;
	Bean bean1;
	Bean bean2;
	
	@Before
	public void setup()
	{
		
	}

	@Test
	/**
	 * check if the constructor correctly assigns the variables to Bean object
	 * @throws NoSuchFieldException
	 * @throws SecurityException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public void testBean1() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException
	{
		Random rand1=new Random();
		Random rand2=new Random();
		Field modeField=Bean.class.getDeclaredField("_mode");
		Field randField=Bean.class.getDeclaredField("_rand");	
		modeField.setAccessible(true);
		randField.setAccessible(true);
		
		bean=new Bean(true,rand1);
		assertTrue((boolean)modeField.get(bean));
		assertTrue(randField.get(bean)==rand1);

		bean=new Bean(false,rand2);
		assertFalse((boolean)modeField.get(bean));
		assertTrue(randField.get(bean)==rand2);
	}

	@Test
	/**
	 *  check if the constructor assigns _skill a valid value
	 */
	public void testBean2() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException
	{
			
		Field skillField=Bean.class.getDeclaredField("_skill");
		skillField.setAccessible(true);
		bean=new Bean(false,new Random());
		
		int skill=(int)skillField.getInt(bean);
		assertTrue(skill>=0 && skill<=9);
	}


	@Test 
	/**
	 * check if the correct sequence of directions can be made in skill mode
	 */
	public void testMove1() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		
		Field skillField=Bean.class.getDeclaredField("_skill");
		skillField.setAccessible(true);
		bean=new Bean(false,new Random());
		int skill=(int)skillField.getInt(bean);
		for(int i=0;i<10;i++)
		{
			if(i>=skill)
				assertTrue(bean.move());
			else
				assertFalse(bean.move());
		}
	}

	@Test 
	/**
	 * check that luck mode direction is not affected by skill value 
	 */
	public void testMove2() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		
		Field skillField=Bean.class.getDeclaredField("_skill");
		skillField.setAccessible(true);
		bean=new Bean(true,new Random()); //set mode to luck 
		skillField.setInt(bean, 0); //set bean skill level to 0, which makes it go left every time

		int count=0;
		while(count<10000)
		{
			count++;
			if(!bean.move()) //extremely likely to be executed if bean is operated by luck, otherwise never
				break;
		}
		
		assertTrue(count<10000);
	}

	@Test
	/**
	 * check that move() will place the bean in the correct position
	 */
	public void testMove3() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		Field skillField=Bean.class.getDeclaredField("_skill");
		Field xField=Bean.class.getDeclaredField("_x");
		Field yField=Bean.class.getDeclaredField("_y");

		skillField.setAccessible(true);
		xField.setAccessible(true);
		yField.setAccessible(true);

		bean=new Bean(false,new Random()); //set mode to skill
		int skill=skillField.getInt(bean); //will move right until skill number of times

		for(int i=0;i<9;i++){
			
			bean.move();
			if(i<skill)
				assertTrue(xField.getInt(bean)==i+1); //x coordinate is always 0
			else
				assertTrue(xField.getInt(bean)==skill);
			assertTrue(yField.getInt(bean)==i+1); //y coordinate increments by 1 each time
		}
	}

	@Test
	/**
	 * check getX corrects return the x coordinate
	 */
	public void testGetX() throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
		bean=new Bean(true, new Random());
		Field xField=Bean.class.getDeclaredField("_x");
		xField.setAccessible(true);
		xField.setInt(bean,5);
		assertTrue(bean.getX()==5);
	}

	@Test
	/**
	 * check getX corrects return the x coordinate
	 */
	public void testGetY() throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
		bean=new Bean(true, new Random());
		Field yField=Bean.class.getDeclaredField("_y");
		yField.setAccessible(true);
		yField.setInt(bean,7);
		assertTrue(bean.getY()==7);
	}


	@After
	public void teardown(){
		bean=null;
		bean1=null;
		bean2=null;
	}
}