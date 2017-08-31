package core.cpu;

import emulator.Utils;

public interface Increment {
	
	public static void Inc(CPU r_cpu, byte ins)
	{
		int initial = 0;
		String reg = "";
		
		switch(ins)
		{
		case 0x04:
			initial = r_cpu.m_BC.GetHighByte();
			
			r_cpu.m_BC.SetHighByte((byte) (initial + 1));
			r_cpu.m_flags.setFlags(initial, r_cpu.m_BC.GetHighByte(), false, Flags.ZERO | Flags.HALFC);
			
			reg = "B";
			
			break;
			
		case 0x0C:
			initial = (byte) r_cpu.m_BC.GetLowByte();
			r_cpu.m_BC.SetLowByte((byte) (initial + 1));
			r_cpu.m_flags.setFlags(initial, r_cpu.m_BC.GetLowByte(), false, Flags.ZERO | Flags.HALFC);
			
			reg = "C";
			
			break;

		case 0x14:
			initial = (byte) r_cpu.m_DE.GetHighByte();
			r_cpu.m_DE.SetHighByte((byte) (initial + 1));
			r_cpu.m_flags.setFlags(initial, r_cpu.m_DE.GetHighByte(), false, Flags.ZERO | Flags.HALFC);
			
			reg = "D";
			
			break;
			
		case 0x1C:
			initial = (byte) r_cpu.m_DE.GetLowByte();
			r_cpu.m_DE.SetLowByte((byte) (initial + 1));
			r_cpu.m_flags.setFlags(initial, r_cpu.m_DE.GetLowByte(), false, Flags.ZERO | Flags.HALFC);
			
			reg = "E";
			
			break;
		case 0x24:
			initial = (byte) r_cpu.m_HL.GetHighByte();
			r_cpu.m_HL.SetHighByte((byte) (initial + 1));
			r_cpu.m_flags.setFlags(initial, r_cpu.m_HL.GetHighByte(), false, Flags.ZERO | Flags.HALFC);
			
			reg = "H";
			
			break;
			
		case 0x2C:
			initial = (byte) r_cpu.m_HL.GetLowByte();
			r_cpu.m_HL.SetLowByte((byte) (initial + 1));
			r_cpu.m_flags.setFlags(initial, r_cpu.m_HL.GetLowByte(), false, Flags.ZERO | Flags.HALFC);
			
			reg = "L";
			
			break;
			
		case 0x34:
			initial = (byte) r_cpu.m_memory.Read(r_cpu.m_HL.get());
			byte result = (byte) initial++;
			r_cpu.m_flags.setFlags(initial, result, false, Flags.ZERO | Flags.HALFC);
			
			reg = "(HL)";
			
			break;
			
		case 0x3C:
			initial = (byte) r_cpu.m_AF.GetHighByte();
			r_cpu.m_AF.SetHighByte((byte) (initial + 1));
			r_cpu.m_flags.setFlags(initial, r_cpu.m_AF.GetHighByte(), false, Flags.ZERO | Flags.HALFC);
			
			reg = "A";
			
			break;
			
		case 0x03:
			r_cpu.m_BC.Set(r_cpu.m_BC.get() + 1);
			
			reg = "BC";
			
			break;

		case 0x13:
			r_cpu.m_DE.Set(r_cpu.m_DE.get() + 1);
			
			reg = "DE";
			
			break;

		case 0x23:
			r_cpu.m_HL.Set(r_cpu.m_HL.get() + 1);
			
			reg = "HL";
			
			break;

		case 0x33:
			r_cpu.m_SP.Set(r_cpu.m_SP.get() + 1);
			
			reg = "SP";
			
			break;
		}

		Utils.PrintInstruction("INC " + reg, ins, r_cpu.m_PC.get(), null, 0);
		
		r_cpu.m_PC.Set(r_cpu.m_PC.get() + 1);
		
		return;
	}
	
}
