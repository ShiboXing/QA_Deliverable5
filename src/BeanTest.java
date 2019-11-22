import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sun.security.krb5.internal.KDCOptions;

import java.lang.reflect.Field;
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
		assertTrue(skill<=0 && skill<=9);
	}


	@Test 
	/**
	 * In the luck mode, two beans with the same sequence of pseudo-random numbers should generate the exact same sequence of directions
	 */
	public void testGetDir(){
		Random rand=new Random();
		bean1=new Bean(true,rand);
		bean2=new Bean(true,rand);
		
		for(int i=0;i<10;i++)
			assertTrue(bean1.getDir()==bean2.getDir());
		
	}


	@After
	public void teardown(){
		bean=null;
		bean1=null;
		bean2=null;
	}
}