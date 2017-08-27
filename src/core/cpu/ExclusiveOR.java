/*
 * ExclusiveOR.java
 * 
 * Handles all XOR instructions.
 */

package core.cpu;

import emulator.Utils;

public class ExclusiveOR {

	public static void XOR(CPU r_cpu, byte ins)
	{
		byte parameter[] = { 0x0 };
		
		switch(ins)
		{
		case (byte) 0xA8:
			r_cpu.m_AF.SetHighByte((byte) (r_cpu.m_AF.GetHighByte() ^ r_cpu.m_BC.GetHighByte()));
			r_cpu.m_flags.setFlags(0, r_cpu.m_AF.GetHighByte(), false, r_cpu.m_flags.ZERO);
			r_cpu.m_flags.setCarry(false);
			r_cpu.m_flags.setHalfCarry(false);
			r_cpu.m_flags.setSubtract(false);
			
			Utils.PrintInstruciton("XOR B", ins, r_cpu.m_PC.get(), null, 0);
			
			r_cpu.m_PC.Set(r_cpu.m_PC.get() + 1);
			
			break;
			
		case (byte) 0xA9:
			r_cpu.m_AF.SetHighByte((byte) (r_cpu.m_AF.GetHighByte() ^ r_cpu.m_BC.GetLowByte()));
			r_cpu.m_flags.setFlags(0, r_cpu.m_AF.GetHighByte(), false, r_cpu.m_flags.ZERO);
			r_cpu.m_flags.setCarry(false);
			r_cpu.m_flags.setHalfCarry(false);
			r_cpu.m_flags.setSubtract(false);
			
			Utils.PrintInstruciton("XOR C", ins, r_cpu.m_PC.get(), null, 0);
			
			r_cpu.m_PC.Set(r_cpu.m_PC.get() + 1);
			
			break;

		case (byte) 0xAA:
			r_cpu.m_AF.SetHighByte((byte) (r_cpu.m_AF.GetHighByte() ^ r_cpu.m_DE.GetHighByte()));
			r_cpu.m_flags.setFlags(0, r_cpu.m_AF.GetHighByte(), false, r_cpu.m_flags.ZERO);
			r_cpu.m_flags.setCarry(false);
			r_cpu.m_flags.setHalfCarry(false);
			r_cpu.m_flags.setSubtract(false);
			
			Utils.PrintInstruciton("XOR D", ins, r_cpu.m_PC.get(), null, 0);
			
			r_cpu.m_PC.Set(r_cpu.m_PC.get() + 1);
			
			break;

		case (byte) 0xAB:
			r_cpu.m_AF.SetHighByte((byte) (r_cpu.m_AF.GetHighByte() ^ r_cpu.m_DE.GetLowByte()));
			r_cpu.m_flags.setFlags(0, r_cpu.m_AF.GetHighByte(), false, r_cpu.m_flags.ZERO);
			r_cpu.m_flags.setCarry(false);
			r_cpu.m_flags.setHalfCarry(false);
			r_cpu.m_flags.setSubtract(false);
			
			Utils.PrintInstruciton("XOR E", ins, r_cpu.m_PC.get(), null, 0);
			
			r_cpu.m_PC.Set(r_cpu.m_PC.get() + 1);
			
			break;

		case (byte) 0xAC:
			r_cpu.m_AF.SetHighByte((byte) (r_cpu.m_AF.GetHighByte() ^ r_cpu.m_HL.GetHighByte()));
			r_cpu.m_flags.setFlags(0, r_cpu.m_AF.GetHighByte(), false, r_cpu.m_flags.ZERO);
			r_cpu.m_flags.setCarry(false);
			r_cpu.m_flags.setHalfCarry(false);
			r_cpu.m_flags.setSubtract(false);
			
			Utils.PrintInstruciton("XOR H", ins, r_cpu.m_PC.get(), null, 0);
			
			r_cpu.m_PC.Set(r_cpu.m_PC.get() + 1);
			
			break;

		case (byte) 0xAD:
			r_cpu.m_AF.SetHighByte((byte) (r_cpu.m_AF.GetHighByte() ^ r_cpu.m_HL.GetLowByte()));
			r_cpu.m_flags.setFlags(0, r_cpu.m_AF.GetHighByte(), false, r_cpu.m_flags.ZERO);
			r_cpu.m_flags.setCarry(false);
			r_cpu.m_flags.setHalfCarry(false);
			r_cpu.m_flags.setSubtract(false);
			
			Utils.PrintInstruciton("XOR L", ins, r_cpu.m_PC.get(), null, 0);
			
			r_cpu.m_PC.Set(r_cpu.m_PC.get() + 1);
			
			break;

		case (byte) 0xAE:
			parameter[0] = r_cpu.m_memory.Read(r_cpu.m_HL.get());
			
			r_cpu.m_AF.SetHighByte((byte) (r_cpu.m_AF.GetHighByte() ^ parameter[0]));
			r_cpu.m_flags.setFlags(0, r_cpu.m_AF.GetHighByte(), false, r_cpu.m_flags.ZERO);
			r_cpu.m_flags.setCarry(false);
			r_cpu.m_flags.setHalfCarry(false);
			r_cpu.m_flags.setSubtract(false);
			
			Utils.PrintInstruciton("XOR (HL)", ins, r_cpu.m_PC.get(), parameter, 1);
			
			r_cpu.m_PC.Set(r_cpu.m_PC.get() + 1);
			
			break;

		case (byte) 0xAF:
			r_cpu.m_AF.SetHighByte((byte) (r_cpu.m_AF.GetHighByte() ^ r_cpu.m_AF.GetHighByte()));
			r_cpu.m_flags.setFlags(0, r_cpu.m_AF.GetHighByte(), false, r_cpu.m_flags.ZERO);
			r_cpu.m_flags.setCarry(false);
			r_cpu.m_flags.setHalfCarry(false);
			r_cpu.m_flags.setSubtract(false);
			
			Utils.PrintInstruciton("XOR A", ins, r_cpu.m_PC.get(), null, 0);
			
			r_cpu.m_PC.Set(r_cpu.m_PC.get() + 1);
			
			break;
			
		default:
			System.err.println("Unknown variant of XOR: " + Utils.hex(ins & 0xFF));
			r_cpu.m_error = true;
			break;
		}
	}
}
