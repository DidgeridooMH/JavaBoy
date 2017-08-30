package core.cpu;

import emulator.Utils;

public interface Subtract {
	
	static void SUB(CPU r_cpu, byte ins) {
		int value = 0x0;
		int initial = r_cpu.m_AF.GetHighByte();
		
		switch(ins)
		{
		case (byte) 0x90:
			value = r_cpu.m_BC.GetHighByte();
			break;
		case (byte) 0x91:
			value = r_cpu.m_BC.GetLowByte();
			break;
		case (byte) 0x92:
			value = r_cpu.m_DE.GetHighByte();
			break;
		case (byte) 0x93:
			value = r_cpu.m_DE.GetLowByte();
			break;
		case (byte) 0x94:
			value = r_cpu.m_HL.GetHighByte();
			break;
		case (byte) 0x95:
			value = r_cpu.m_HL.GetLowByte();
			break;
		case (byte) 0x96:
			value = r_cpu.m_memory.Read(r_cpu.m_PC.get());
			break;
		case (byte) 0x97:
			value = r_cpu.m_AF.GetHighByte();
			break;
		default:
			System.err.println("Error parsing SUB instruction: " + 
							Utils.hex(ins) + " at " + 
							Utils.hex(r_cpu.m_PC.get()));
		}
		
		r_cpu.m_AF.SetHighByte((byte) (r_cpu.m_AF.GetHighByte() - value));
		r_cpu.m_flags.setFlags(initial, r_cpu.m_AF.GetHighByte(), false, Flags.ZERO | Flags.HALFC | Flags.CARRY);
		r_cpu.m_flags.setSubtract(true);
		
		r_cpu.m_PC.Set(r_cpu.m_PC.get() + 1);
	}
	
}
