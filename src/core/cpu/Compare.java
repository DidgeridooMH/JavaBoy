/*
 * Compare.java
 * 
 * Subtracts a register or a byte from memory
 * from A then sets the flags according to the
 * result. A is never affected though.
 */

package core.cpu;

import emulator.Utils;

public interface Compare {

	public static void CP(CPU r_cpu, byte ins)
	{
		byte value = 0x0;
		byte initial = (byte) r_cpu.m_AF.GetHighByte();
		byte result = 0x0;
		
		String reg = "";
		
		switch(ins)
		{
		case (byte) 0xB8:
			value = (byte) r_cpu.m_BC.GetHighByte();
		
			reg = "B";
		
			break;

		case (byte) 0xB9:
			value = (byte) r_cpu.m_BC.GetLowByte();
		
			reg = "C";
		
			break;

		case (byte) 0xBA:
			value = (byte) r_cpu.m_DE.GetHighByte();
		
			reg = "D";
		
			break;

		case (byte) 0xBB:
			value = (byte) r_cpu.m_DE.GetLowByte();
		
			reg = "E";
		
			break;

		case (byte) 0xBC:
			value = (byte) r_cpu.m_HL.GetHighByte();
		
			reg = "H";
		
			break;

		case (byte) 0xBD:
			value = (byte) r_cpu.m_HL.GetLowByte();
		
			reg = "L";
		
			break;

		case (byte) 0xBE:
			value = r_cpu.m_memory.Read(r_cpu.m_HL.get());
			
			reg = "(HL)";
			
			break;

		case (byte) 0xBF:
			value = (byte) r_cpu.m_AF.GetHighByte();
		
			reg = "A";
		
			break;
			
		case (byte) 0xFE:
			value = (byte) r_cpu.m_memory.Read(r_cpu.m_PC.get() + 1);
		
			reg = "d8";
			
			break;
			
		default:
			System.err.println("Error while parsing CP command: " + 
								Utils.hex(ins) + " at " +
								Utils.hex(r_cpu.m_PC.get()));
			r_cpu.m_error = true;
			return;
		}
		
		result = (byte) (initial - value);
		
		r_cpu.m_flags.setFlags(initial, result, false, Flags.CARRY | Flags.ZERO | Flags.HALFC | Flags.SUBTRACT);
		
		Utils.PrintInstruction("CP " + reg, ins, r_cpu.m_PC.get(), null, 0);
		
		if(ins == 0xFE)
			r_cpu.m_PC.Set(r_cpu.m_PC.get() + 2);
		else
			r_cpu.m_PC.Set(r_cpu.m_PC.get() + 1);
		
		return;
	}
	
}
