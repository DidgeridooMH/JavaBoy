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
		
		// Initialize some registers
		m_memory.Write((byte) 0x00, 0xFF05);
		m_memory.Write((byte) 0x00, 0xFF06);
		m_memory.Write((byte) 0x00, 0xFF07);
		m_memory.Write((byte) 0x80, 0xFF10);
		m_memory.Write((byte) 0xBF, 0xFF11);
		m_memory.Write((byte) 0xF3, 0xFF12);
		m_memory.Write((byte) 0xBF, 0xFF14);
		m_memory.Write((byte) 0x3F, 0xFF16);
		m_memory.Write((byte) 0x00, 0xFF17);
		m_memory.Write((byte) 0xBF, 0xFF19);
		m_memory.Write((byte) 0x7F, 0xFF1A);
		m_memory.Write((byte) 0xFF, 0xFF1B);
		m_memory.Write((byte) 0x9F, 0xFF1C);
		m_memory.Write((byte) 0xBF, 0xFF1E);
		m_memory.Write((byte) 0xFF, 0xFF20);
		m_memory.Write((byte) 0x00, 0xFF21);
		m_memory.Write((byte) 0x00, 0xFF22);
		m_memory.Write((byte) 0xBF, 0xFF23);
		m_memory.Write((byte) 0x77, 0xFF24);
		m_memory.Write((byte) 0xF3, 0xFF25);
		m_memory.Write((byte) 0xF1, 0xFF26);
		m_memory.Write((byte) 0x91, 0xFF40);
		m_memory.Write((byte) 0x00, 0xFF42);
		m_memory.Write((byte) 0x00, 0xFF43);
		m_memory.Write((byte) 0x00, 0xFF45);
		m_memory.Write((byte) 0xFC, 0xFF47);
		m_memory.Write((byte) 0xFF, 0xFF48);
		m_memory.Write((byte) 0xFF, 0xFF49);
		m_memory.Write((byte) 0x00, 0xFF4A);
		m_memory.Write((byte) 0x00, 0xFF4B);
		m_memory.Write((byte) 0x00, 0xFFFF);
		
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
		int instruction = m_memory.Read(m_PC.get());
		String decodedIns = m_parser.DecodeIns(instruction);
		
		Step((byte) instruction, decodedIns);
		
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
		case "LDH":
			Load.LD(this, ins);
			break;
			
		case "XOR":
			ExclusiveOR.XOR(this, ins);
			break;
			
		case "SUB":
			Subtract.SUB(this, ins);
			break;
			
		case "PREFIX":
			StepPrefix();
			break;
			
		case "JR":
			Jump.JumpSubroutine(this, ins);
			break;
			
		case "CALL":
			Jump.Call(this, ins);
			break;
			
		case "PUSH":
			Stack.Push(this, ins);
			break;
			
		case "POP":
			Stack.Pop(this, ins);
			break;
			
		case "RLA":
			BitShift.Rotate(this, ins, false);
			break;
			
		case "DEC":
			Decrement.Dec(this, ins);
			break;
			
		case "INC":
			Increment.Inc(this, ins);
			break;
			
		case "RET":
			Jump.Return(this, ins);
			break;
			
		case "CP":
			Compare.CP(this, ins);
			break;
			
		default:
			System.err.println("Unknown opcode: " + 
								decodedIns + " " + 
								Utils.hex(ins & 0xFF) + 
								" at " + Utils.hex(m_PC.get()) );
			m_error = true;
			break;
		}
	}
	
	public void StepPrefix()
	{
		byte instruction = m_memory.Read(m_PC.get()+1);
		String decodedPrefix = m_parser.DecodePrefix(instruction);
		
		switch(decodedPrefix)
		{
		case "BIT":
			BitTest.BIT(this, instruction);
			break;
			
		case "RL":
			BitShift.Rotate(this, instruction, true);
			break;
		
		default:
			System.err.println("Unknown prefix: " + 
								decodedPrefix + " " + 
								Utils.hex(instruction & 0xFF) + 
								" at " + Utils.hex(m_PC.get()));
			m_error = true;
			break;
		}
	}
	
	public void Push(byte in)
	{
		m_memory.Write(in, m_SP.get());
		m_SP.Set(m_SP.get() - 1);
	}
	
	public void Push16(short in)
	{
		Push((byte) ((in >> 8) & 0xFF));
		Push((byte) (in & 0xFF));
	}
	
	public byte Pop()
	{
		byte out = 0x0;
		
		m_SP.Set(m_SP.get() + 1);
		out = m_memory.Read(m_SP.get());
		
		return out;
	}
	
	public short Pop16()
	{
		short highByte = 0x0;
		short lowByte = 0x0;
		
		lowByte = (short) (Pop() & 0xFF);
		highByte = Pop();
		
		return (short) (((highByte & 0xFFFF) << 8) | (lowByte & 0xFF));
	}
	
}
