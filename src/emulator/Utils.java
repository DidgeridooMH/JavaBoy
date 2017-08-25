/*
 * Utils.java
 * 
 * Common functions used various times throughout
 * the program.
 * 
 */

package emulator;

public class Utils {

	public static String hex(int num)
	{	
		return ("$" + Integer.toHexString(0x10000 | num).substring(1));
	}
	
}
