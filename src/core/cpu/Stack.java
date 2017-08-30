/*
 * Stack.java
 * 
 * Handles pushing and popping registers,
 * 16bit combined registers, and memory.
 */

package core.cpu;

import emulator.Utils;

public interface Stack {
	
	public static void Push(CPU r_cpu, byte ins)
	{	
		String register = "";
		
		switch(ins)
		{
		case (byte) 0xC5:
			r_cpu.Push16((short) r_cpu.m_BC.get());
			register = "BC";
			break;
		case (byte) 0xD5:
			r_cpu.Push16((short) r_cpu.m_DE.get());
			register = "DE";
			break;
		case (byte) 0xE5:
			r_cpu.Push16((short) r_cpu.m_HL.get());
			register = "HL";
			break;
		case (byte) 0xF5:
			r_cpu.Push((byte) r_cpu.m_AF.GetHighByte());
			r_cpu.Push(r_cpu.m_flags.GenerateReg());
			register = "AF";
			break;
		default:
			System.err.println("Error parsing Push instruction: " +
								Utils.hex(ins) + " at " + Utils.hex(r_cpu.m_PC.get()));
			break;
		}
		
		Utils.PrintInstruction("PUSH " + register, ins, r_cpu.m_PC.get(), null, 0);
		
		r_cpu.m_PC.Set(r_cpu.m_PC.get() + 1);
	}
	
	public static void Pop(CPU r_cpu, byte ins)
	{	
		String register = "";
		
		switch(ins)
		{
		case (byte) 0xC1:
			r_cpu.m_BC.Set(r_cpu.Pop16());
			register = "BC";
			break;
		case (byte) 0xD1:
			r_cpu.m_DE.Set(r_cpu.Pop16());
			register = "DE";
			break;
		case (byte) 0xE1:
			r_cpu.m_HL.Set(r_cpu.Pop16());
			register = "HL";
			break;
		case (byte) 0xF1:
			r_cpu.m_flags.ByteToFlags(r_cpu.Pop());
			r_cpu.m_AF.SetHighByte(r_cpu.Pop());
			register = "AF";
			break;
		default:
			System.err.println("Error parsing Pop instruction: " +
								Utils.hex(ins) + " at " + Utils.hex(r_cpu.m_PC.get()));
			break;
		}
		
		Utils.PrintInstruction("POP " + register, ins, r_cpu.m_PC.get(), null, 0);
		
		r_cpu.m_PC.Set(r_cpu.m_PC.get() + 1);
	}
}
