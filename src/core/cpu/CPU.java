/*
 * CPU.java
 * 
 * This segment of the program will handle
 * the execution of OP codes. It will be given
 * a z80 instruction, parse it, then execute it.
 * 
 */

package core.cpu;

import emulator.Utils;

import core.Memory.*;

public class CPU 
{
	protected boolean m_error = false;
	
	private Register m_AF;
	private Register m_BC;
	private Register m_DE;
	private Register m_HL;
	private Register m_SP;
	private Register m_PC;
	
	private Flags m_flags;
	
	private Memory m_memory;
	
	private Parser m_parser;
	
	/*
	 * Initialize flags and set the register to
	 * the default values provided by "pandocs."
	 */
	
	public CPU(String biosFile, String romName)
	{
		m_AF = new Register(0x01B0);
		m_BC = new Register(0x0013);
		m_DE = new Register(0x00D8);
		m_HL = new Register(0x014D);
		m_SP = new Register(0xFFFE);
		m_PC = new Register(0x0000);
		
		m_flags = new Flags();
		
		// For diagnostic reasons
		System.out.println("m_AF: " + Utils.hex(m_AF.get()));
		System.out.println("m_BC: " + Utils.hex(m_BC.get()));
		System.out.println("m_DE: " + Utils.hex(m_DE.get()));
		System.out.println("m_HL: " + Utils.hex(m_HL.get()));
		System.out.println("m_SP: " + Utils.hex(m_SP.get()));
		System.out.println("m_PC: " + Utils.hex(m_PC.get()));
		System.out.println("");
		
		m_memory = new Memory(biosFile, romName);
		m_parser = new Parser();
	}
	
	/*
	 * Intakes an instruction and sends it to the parser.
	 */
	
	public void Execute()
	{
		/*
		 * - Retrieves instruction from memory
		 * - Translate instruction from an OPCode table
		 * - Executes correct CPU function
		 */
		
		/*
		byte instruction = m_memory.Read(m_PC.get());
		String decodedIns = Parse(instruction);
		
		Step(instruction, decodedIns);
		*/
		
		m_error = true;
	}
	
	public boolean IsError()
	{
		return m_error;
	}
	
}
