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
import core.cpu.Load;

import core.Memory.*;

public class CPU 
{
	boolean m_error = false;
	
	Register m_AF;
	Register m_BC;
	Register m_DE;
	Register m_HL;
	Register m_SP;
	Register m_PC;
	
	Flags m_flags;
	
	Memory m_memory;
	
	Parser m_parser;
	
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
		DumpRegisters();
		
		m_memory = new Memory(biosFile, romName);
		m_parser = new Parser();
	}
	
	public void DumpRegisters()
	{
		System.out.println("m_AF: " + Utils.hex(m_AF.get()));
		System.out.println("m_BC: " + Utils.hex(m_BC.get()));
		System.out.println("m_DE: " + Utils.hex(m_DE.get()));
		System.out.println("m_HL: " + Utils.hex(m_HL.get()));
		System.out.println("m_SP: " + Utils.hex(m_SP.get()));
		System.out.println("m_PC: " + Utils.hex(m_PC.get()));
		System.out.println("");
		
		return;
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
		
		byte instruction = m_memory.Read(m_PC.get());
		String decodedIns = m_parser.DecodeIns(instruction);
		
		Step(instruction, decodedIns);
		
		m_error = true;
	}
	
	public boolean IsError()
	{
		return m_error;
	}
	
	public void Step(byte ins, String decodedIns)
	{
		switch(decodedIns)
		{
		case "LD":
			Load.LD(this, ins);
			break;
		default:
			System.out.println("Unknown opcode: " + decodedIns + " " + Utils.hex(ins));
			break;
		}
	}
	
}
