import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class BeanCounterGUI {

	public static void showUsage() {
		System.out.println("Usage: java BeanCounterGUI <number of beans> <luck | skill>");
		System.out.println("Example: java BeanCounterGUI 400 luck");
	}

	/**
	 * Main method. Creates the main frame for the app.
	 * 
	 * @param args args[0] is an integer bean count, args[1] is a string which is
	 *             either luck or skill.
	 */

	public static void main(String[] args) {
		if (args.length != 2) {
			showUsage();
			return;
		}

		int beanCount;
		boolean luck = args[1].equals("luck") ? true : false;
		try {
			beanCount = Integer.parseInt(args[0]);
		} catch (NumberFormatException ne) {
			showUsage();
			return;
		}
		// Create the main frame for the app
		new MainFrame(beanCount, luck);
	}

}
