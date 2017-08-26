/*
 * JavaBoy.java
 * 
 * The main file for the program. Handles the machine's execution
 * according to it's state. Also, handles acquisition of the bios
 * file path and rom path.
 * 
 * @author Daniel Simpkins
 * @version 0.0.1a
 * 
 */

package emulator;

import emulator.Machine;

public class JavaBoy
{
	static Machine m_machine = null;
	
	public static void main(String[] args)
	{
		String m_files[] = {"", "" };
		
		if(args.length > 2)
			m_files = ParseArguments(args);
		else
		{
			// Take this away in the future but for debugging
			// I'm gonna leave this
			m_files[0] = "BIOS/bios.bin";
			m_files[1] = "ROMS/pokemon.gb";
		}
		
		m_machine = new Machine(m_files[0], m_files[1]);
		
		while(m_machine.m_state != 0)
		{
			m_machine.Execute();
		}
	}
	
	/*
	 * Parse the two files that should be passed through the command line
	 * and give a help menu if those arguments aren't found.
	 */
	
	public static String[] ParseArguments(String args[])
	{
		String files[] = {"", ""};
		
		for(int i = 0; i < args.length; i++)
		{
			switch(args[i])
			{
			case "-b":
				files[0] = args[i+1];
				i++;
				break;
			case "-r":
				files[1] = args[i+1];
				i++;
				break;
			default:
				System.err.println("Try: JavaBoy [-r RomName] [-b Biosfile]");
				System.exit(1);
				break;
			}
		}
		
		return files;
	}

}
