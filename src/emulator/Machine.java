/*
 * Machine.java
 * 
 * This handles all hardware components and checks
 * for errors in the each components and then updates
 * the state of the machine accordingly.
 * 
 * TODO:
 * - Add state save support
 * 
 */


package emulator;
import core.cpu.CPU;

public class Machine {

	CPU m_cpu;
	
	// 1 if on; 0 if off;
	int m_state = 1;
	
	/*
	 * @param Path to the rom and bios files
	 */
	
	public Machine(String bios, String rom)
	{
		System.out.println("Machine Initialized!");
		m_cpu = new CPU(bios, rom);
	}
	
	public void Execute()
	{
		m_cpu.Execute();
		
		if(m_cpu.IsError())
			m_state = 0;
	}
	
}
