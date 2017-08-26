/*
 * Load.java
 * 
 * Handles all calls of the "ld" opcode. This
 * loads information into/from memory, register,
 * and combined 16bit registers.
 */

package core.cpu;

import emulator.Utils;

public class Load {
	
	public static void LD(CPU r_cpu, byte ins)
	{
		byte parameters[] = { 0x0, 0x0 };
		int address = 0x0;
		
		switch(ins)
		{
		case 0x01:
			parameters[0] = r_cpu.m_memory.Read(r_cpu.m_PC.get()+1);
			parameters[1] = r_cpu.m_memory.Read(r_cpu.m_PC.get()+2);
			
			r_cpu.m_BC.SetLowByte(parameters[0]);
			r_cpu.m_BC.SetHighByte(parameters[1]);
			Utils.PrintInstruciton("LD BC, d16", ins, r_cpu.m_PC.get(), parameters, 2);
			
			r_cpu.m_PC.Set(r_cpu.m_PC.get() + 3);
			
			break;
			
		case 0x02:
			r_cpu.m_memory.Write((byte) r_cpu.m_AF.GetHighByte(), r_cpu.m_BC.get());
			
			Utils.PrintInstruciton("LD (BC) A", ins, r_cpu.m_PC.get(), null, 0);

			r_cpu.m_PC.Set(r_cpu.m_PC.get() + 1);
			
			break;
			
		case 0x06:
			parameters[0] = r_cpu.m_memory.Read(r_cpu.m_PC.get()+1);
			
			r_cpu.m_BC.SetHighByte(parameters[0]);
			
			Utils.PrintInstruciton("LD B, d8", ins, r_cpu.m_PC.get(), parameters, 1);

			r_cpu.m_PC.Set(r_cpu.m_PC.get() + 2);

			break;
			
		case 0x08:
			parameters[0] = r_cpu.m_memory.Read(r_cpu.m_PC.get()+1);
			parameters[1] = r_cpu.m_memory.Read(r_cpu.m_PC.get()+2);
			
			address = ((int) parameters[1] << 8) | (int) parameters[0];
			
			r_cpu.m_memory.Write((byte) r_cpu.m_SP.GetHighByte(), address);
			r_cpu.m_memory.Write((byte) r_cpu.m_SP.GetLowByte(), address + 1);

			Utils.PrintInstruciton("LD (a16), SP", ins, r_cpu.m_PC.get(), parameters, 2);

			r_cpu.m_PC.Set(r_cpu.m_PC.get() + 3);
			
			break;
			
		case 0x0A:
			parameters[0] = r_cpu.m_memory.Read(r_cpu.m_BC.get());
			
			r_cpu.m_AF.SetHighByte(parameters[0]);

			Utils.PrintInstruciton("LD A, (BC)", ins, r_cpu.m_PC.get(), parameters, 1);

			r_cpu.m_PC.Set(r_cpu.m_PC.get() + 1);
			
			break;
			
		case 0x0E:
			parameters[0] = r_cpu.m_memory.Read(r_cpu.m_PC.get() + 1);
			
			r_cpu.m_BC.SetLowByte(parameters[0]);

			Utils.PrintInstruciton("LD C, d8", ins, r_cpu.m_PC.get(), parameters, 1);

			r_cpu.m_PC.Set(r_cpu.m_PC.get() + 2);
			
			break;
			
		case 0x11:
			parameters[0] = r_cpu.m_memory.Read(r_cpu.m_PC.get() + 1);
			parameters[1] = r_cpu.m_memory.Read(r_cpu.m_PC.get() + 2);

			r_cpu.m_DE.SetLowByte(parameters[0]);
			r_cpu.m_DE.SetHighByte(parameters[1]);
			Utils.PrintInstruciton("LD DE, d16", ins, r_cpu.m_PC.get(), parameters, 2);

			r_cpu.m_PC.Set(r_cpu.m_PC.get() + 3);
			
			break;

		case 0x12:
			r_cpu.m_memory.Write((byte) r_cpu.m_AF.GetHighByte(), r_cpu.m_DE.get());
			
			Utils.PrintInstruciton("LD (DE),A", ins, r_cpu.m_PC.get(), null, 0);

			r_cpu.m_PC.Set(r_cpu.m_PC.get() + 1);
			
			break;
			
		case 0x16:
			parameters[0] = r_cpu.m_memory.Read(r_cpu.m_PC.get() + 1);
			
			r_cpu.m_DE.SetHighByte(parameters[0]);
			
			Utils.PrintInstruciton("LD D,d8", ins, r_cpu.m_PC.get(), parameters, 1);

			r_cpu.m_PC.Set(r_cpu.m_PC.get() + 2);
			
			break;
			
		case 0x1A:
			parameters[0] = r_cpu.m_memory.Read(r_cpu.m_DE.get());
			r_cpu.m_AF.SetHighByte(parameters[0]);
			
			Utils.PrintInstruciton("LD A,(DE)", ins, r_cpu.m_PC.get(), parameters, 1);

			r_cpu.m_PC.Set(r_cpu.m_PC.get() + 2);
			
			break;
			
		default:
			System.err.println("Unknown variant of the \"LD\" opcode: " + Utils.hex(ins));
			
			r_cpu.m_error = true;
			break;
		}
	}
	
}
