/*
 * BitTest.java
 * 
 * Runs a bit test on registers, memory locations
 * or combined 16bit registers.
 */

package core.cpu;

import emulator.Utils;

public class BitTest {

	public static void BIT(CPU r_cpu, byte ins)
	{
		switch(ins)
		{
		case (byte) 0x40:
			if(r_cpu.m_BC.GetHighByte() > 0x0)
				r_cpu.m_flags.setZero(true);
			else
				r_cpu.m_flags.setZero(false);
		
			r_cpu.m_flags.setSubtract(false);
			r_cpu.m_flags.setHalfCarry(true);
			
			Utils.PrintInstruciton("BIT 0, B", ins, r_cpu.m_PC.get(), null, 0);
			
			r_cpu.m_PC.Set(r_cpu.m_PC.get() + 2);
			
			break;
			
		case (byte) 0x7C:
			if(r_cpu.m_HL.GetHighByte() >= 0x80)
				r_cpu.m_flags.setZero(true);
			else
				r_cpu.m_flags.setZero(false);
		
			r_cpu.m_flags.setSubtract(false);
			r_cpu.m_flags.setHalfCarry(true);
			
			Utils.PrintInstruciton("BIT 7, H", ins, r_cpu.m_PC.get(), null, 0);
			
			r_cpu.m_PC.Set(r_cpu.m_PC.get() + 2);
			
			break;
			
		default:
			System.out.println("Unknown variant of BIT: " + Utils.hex(ins & 0xFF));
			r_cpu.m_error = true;
			break;
		}
	}
	
}
