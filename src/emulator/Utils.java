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
	
	public static void PrintInstruciton(String decodedIns, byte ins, int location, byte parameters[], int paramSize)
	{
		System.out.print(hex(location));
		System.out.print(": ");
		System.out.print(decodedIns);
		System.out.print("(" + hex(ins) + ") ");
		
		for(int i = 0; i < paramSize; i++)
			System.out.print(hex(parameters[i] & 0xFF) + " ");
		
		System.out.print("\n");
		
		return;
	}
}
