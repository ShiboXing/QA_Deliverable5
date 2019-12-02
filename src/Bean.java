
import gov.nasa.jpf.annotation.FilterField;
import java.util.Random;

/**
 * Code by @author Wonsun Ahn
 * 
 * <p>Bean: Each bean is assigned a skill level from 0-9 on creation according to
 * a normal distribution with average SKILL_AVERAGE and standard deviation
 * SKILL_STDEV. A skill level of 9 means it always makes the "right" choices
 * (pun intended) when the machine is operating in skill mode ("skill" passed on
 * command kine). That means the bean will always go right when a peg is
 * encountered, resulting it falling into slot 9. A skill evel of 0 means that
 * the bean will always go left, resulting it falling into slot 0. For the
 * in-between skill levels, the bean will first go right then left. For example,
 * for a skill level of 7, the bean will go right 7 times then go left twice.
 * 
 * <p>Skill levels are irrelevant when the machine operates in luck mode. In that
 * case, the bean will have a 50/50 chance of going right or left, regardless of
 * skill level.
 */

public class Bean {
	// TODO: Add member methods and variables as needed 
	// MainPanel.SLOT_COUNT * 0.5;
	@FilterField private static final double SKILL_AVERAGE = 4.5;
	// Math.sqrt(SLOT_COUNT * 0.5 * (1 - 0.5));
	@FilterField private static final double SKILL_STDEV = 1.5;		
	@FilterField private static final int BOUND = (int)(SKILL_AVERAGE * 2) + 1;
	
	@FilterField private final Random _rand;
	@FilterField
	private int _skill = -1; // will be assigned if skill mode is chosen

	private final boolean _mode; // 1 if luck, 0 if skill
	private int _times = 0; // the number of falls the bean has taken, going left if less than skill

	private int _x;
	private int _y;

	/**
	 * Constructor - creates a bean in either luck mode or skill mode.
	 * 
	 * @param isLuck whether the bean is in luck mode
	 * @param rand   xthe random number generator
	 */
	Bean(final boolean isLuck, final Random rand) {
		// TODO: Implement
		_mode = isLuck;
		_rand = rand;	
		_skill = (int) Math.round(rand.nextGaussian() * SKILL_STDEV + SKILL_AVERAGE);
		_x = 0;
		_y = 0;
	}  


	
	/**
	 * move the bean to the next position
	 * returns whether the bean moved left.
	 */
	public boolean move() {
		boolean res = false;

		if (_mode) { //luck
			if (_rand.nextInt(BOUND) < 5) {
				res = true;
			}
		} else { //skill
			if (_times++ >= _skill) {
				res = true;
			}
		}
		if (!res) {
			_x++;
		}
		_y++;

		return res;
		
	}

	/**
	 * return the x coordinate
	 */
	public int getX() {
		return _x;
	}

	/**
	 * return the y coordinate
	 */
	public int getY() {
		return _y;
	}

	/**
	 * resets the bean to the initial position
	 */
	public void reset() {
		_x = 0;
		_y = -1;
		_times = 0;
	}

}
