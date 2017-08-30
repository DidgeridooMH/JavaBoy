/*
 * Load.java
 * 
 * Handles all calls of the "ld" opcode. This
 * loads information into/from memory, register,
 * and combined 16bit registers.
 */

package core.cpu;

import emulator.Utils;

public interface Load {
	
	static void LD(CPU r_cpu, byte ins)
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

			r_cpu.m_PC.Set(r_cpu.m_PC.get() + 1);
			
			break;
			
		case 0x1E:
			parameters[0] = r_cpu.m_memory.Read(r_cpu.m_PC.get() + 1);
			r_cpu.m_DE.SetLowByte(parameters[0]);
			
			Utils.PrintInstruciton("LD E, d8", ins, r_cpu.m_PC.get(), parameters, 1);

			r_cpu.m_PC.Set(r_cpu.m_PC.get() + 2);
			
			break;
			
		case 0x21:
			parameters[0] = r_cpu.m_memory.Read(r_cpu.m_PC.get() + 1);
			parameters[1] = r_cpu.m_memory.Read(r_cpu.m_PC.get() + 2);
			
			r_cpu.m_HL.SetHighByte(parameters[1]);
			r_cpu.m_HL.SetLowByte(parameters[0]);
			
			Utils.PrintInstruciton("LD HL, d16", ins, r_cpu.m_PC.get(), parameters, 2);

			r_cpu.m_PC.Set(r_cpu.m_PC.get() + 3);
			
			break;

		case 0x22:
			r_cpu.m_memory.Write((byte) r_cpu.m_AF.GetHighByte(), r_cpu.m_HL.get());
			r_cpu.m_HL.m_value++;
			
			Utils.PrintInstruciton("LD (HL+), A", ins, r_cpu.m_PC.get(), null, 0);

			r_cpu.m_PC.Set(r_cpu.m_PC.get() + 1);
			
			break;

		case 0x26:
			parameters[0] = r_cpu.m_memory.Read(r_cpu.m_PC.get() + 1);
			r_cpu.m_HL.SetHighByte(parameters[0]);
			
			Utils.PrintInstruciton("LD H, d8", ins, r_cpu.m_PC.get(), parameters, 1);

			r_cpu.m_PC.Set(r_cpu.m_PC.get() + 2);
			
			break;

		case 0x2A:
			parameters[0] = r_cpu.m_memory.Read(r_cpu.m_HL.get());
			r_cpu.m_HL.m_value++;
			r_cpu.m_AF.SetHighByte(parameters[0]);
			
			Utils.PrintInstruciton("LD A, (HL+)", ins, r_cpu.m_PC.get(), parameters, 1);

			r_cpu.m_PC.Set(r_cpu.m_PC.get() + 1);
			
			break;
		
		case 0x2E:
			parameters[0] = r_cpu.m_memory.Read(r_cpu.m_HL.get());
			r_cpu.m_HL.m_value++;
			r_cpu.m_AF.SetHighByte(parameters[0]);
			
			Utils.PrintInstruciton("LD L, d8", ins, r_cpu.m_PC.get(), parameters, 1);

			r_cpu.m_PC.Set(r_cpu.m_PC.get() + 2);
			
			break;

		case 0x31:
			parameters[0] = r_cpu.m_memory.Read(r_cpu.m_PC.get() + 1);
			parameters[1] = r_cpu.m_memory.Read(r_cpu.m_PC.get() + 2);
			
			r_cpu.m_SP.SetHighByte(parameters[1]);
			r_cpu.m_SP.SetLowByte(parameters[0]);
			
			Utils.PrintInstruciton("LD SP, d16", ins, r_cpu.m_PC.get(), parameters, 2);

			r_cpu.m_PC.Set(r_cpu.m_PC.get() + 3);
			
			break;
			
		case 0x32:
			r_cpu.m_memory.Write((byte) r_cpu.m_AF.GetHighByte(), r_cpu.m_HL.get());
			r_cpu.m_HL.m_value--;
			
			Utils.PrintInstruciton("LD (HL-), A", ins, r_cpu.m_PC.get(), null, 0);

			r_cpu.m_PC.Set(r_cpu.m_PC.get() + 1);
			
			break;

		case 0x36:
			parameters[0] = r_cpu.m_memory.Read(r_cpu.m_PC.get() + 1);
			r_cpu.m_memory.Write(parameters[0], r_cpu.m_HL.get());
			
			Utils.PrintInstruciton("LD (HL), d8", ins, r_cpu.m_PC.get(), parameters, 1);

			r_cpu.m_PC.Set(r_cpu.m_PC.get() + 2);
			
			break;

		case 0x3A:
			parameters[0] = r_cpu.m_memory.Read(r_cpu.m_HL.get());
			r_cpu.m_AF.SetHighByte(parameters[0]);
			
			r_cpu.m_HL.m_value--;
			
			Utils.PrintInstruciton("LD A, (HL-)", ins, r_cpu.m_PC.get(), null, 0);

			r_cpu.m_PC.Set(r_cpu.m_PC.get() + 1);
			
			break;

		case 0x3E:
			parameters[0] = r_cpu.m_memory.Read(r_cpu.m_PC.get() + 1);
			
			r_cpu.m_AF.SetHighByte(parameters[0]);
			
			Utils.PrintInstruciton("LD A, d8", ins, r_cpu.m_PC.get(), parameters, 1);

			r_cpu.m_PC.Set(r_cpu.m_PC.get() + 2);
			
			break;
			
		case 0x40:
			r_cpu.m_BC.SetHighByte((byte) r_cpu.m_BC.GetHighByte());
			
			Utils.PrintInstruciton("LD B, B", ins, r_cpu.m_PC.get(), null, 0);

			r_cpu.m_PC.Set(r_cpu.m_PC.get() + 1);
			
			break;

		case 0x41:
			r_cpu.m_BC.SetHighByte((byte) r_cpu.m_BC.GetLowByte());
			
			Utils.PrintInstruciton("LD B, C", ins, r_cpu.m_PC.get(), null, 0);

			r_cpu.m_PC.Set(r_cpu.m_PC.get() + 1);
			
			break;

		case 0x42:
			r_cpu.m_BC.SetHighByte((byte) r_cpu.m_DE.GetHighByte());
			
			Utils.PrintInstruciton("LD B, D", ins, r_cpu.m_PC.get(), null, 0);

			r_cpu.m_PC.Set(r_cpu.m_PC.get() + 1);
			
			break;

		case 0x43:
			r_cpu.m_BC.SetHighByte((byte) r_cpu.m_DE.GetLowByte());
			
			Utils.PrintInstruciton("LD B, E", ins, r_cpu.m_PC.get(), null, 0);

			r_cpu.m_PC.Set(r_cpu.m_PC.get() + 1);
			
			break;

		case 0x44:
			r_cpu.m_BC.SetHighByte((byte) r_cpu.m_HL.GetHighByte());
			
			Utils.PrintInstruciton("LD B, H", ins, r_cpu.m_PC.get(), null, 0);

			r_cpu.m_PC.Set(r_cpu.m_PC.get() + 1);
			
			break;

		case 0x45:
			r_cpu.m_BC.SetHighByte((byte) r_cpu.m_HL.GetLowByte());
			
			Utils.PrintInstruciton("LD B, L", ins, r_cpu.m_PC.get(), null, 0);

			r_cpu.m_PC.Set(r_cpu.m_PC.get() + 1);
			
			break;

		case 0x46:
			parameters[0] = r_cpu.m_memory.Read(r_cpu.m_HL.get());
			
			r_cpu.m_BC.SetHighByte(parameters[0]);
			
			Utils.PrintInstruciton("LD B, (HL)", ins, r_cpu.m_PC.get(), null, 0);

			r_cpu.m_PC.Set(r_cpu.m_PC.get() + 1);
			
			break;
			
		case 0x47:
			r_cpu.m_BC.SetHighByte((byte) r_cpu.m_AF.GetHighByte());
			
			Utils.PrintInstruciton("LD B, A", ins, r_cpu.m_PC.get(), null, 0);

			r_cpu.m_PC.Set(r_cpu.m_PC.get() + 1);
			
			break;
			
		case 0x48:
			r_cpu.m_BC.SetLowByte((byte) r_cpu.m_BC.GetHighByte());
			
			Utils.PrintInstruciton("LD C, B", ins, r_cpu.m_PC.get(), null, 0);

			r_cpu.m_PC.Set(r_cpu.m_PC.get() + 1);
			
			break;

		case 0x49:
			r_cpu.m_BC.SetLowByte((byte) r_cpu.m_BC.GetLowByte());
			
			Utils.PrintInstruciton("LD C, C", ins, r_cpu.m_PC.get(), null, 0);

			r_cpu.m_PC.Set(r_cpu.m_PC.get() + 1);
			
			break;

		case 0x4A:
			r_cpu.m_BC.SetLowByte((byte) r_cpu.m_DE.GetHighByte());
			
			Utils.PrintInstruciton("LD C, D", ins, r_cpu.m_PC.get(), null, 0);

			r_cpu.m_PC.Set(r_cpu.m_PC.get() + 1);
			
			break;

		case 0x4B:
			r_cpu.m_BC.SetLowByte((byte) r_cpu.m_DE.GetLowByte());
			
			Utils.PrintInstruciton("LD C, E", ins, r_cpu.m_PC.get(), null, 0);

			r_cpu.m_PC.Set(r_cpu.m_PC.get() + 1);
			
			break;

		case 0x4C:
			r_cpu.m_BC.SetLowByte((byte) r_cpu.m_HL.GetHighByte());
			
			Utils.PrintInstruciton("LD C, H", ins, r_cpu.m_PC.get(), null, 0);

			r_cpu.m_PC.Set(r_cpu.m_PC.get() + 1);
			
			break;

		case 0x4D:
			r_cpu.m_BC.SetLowByte((byte) r_cpu.m_HL.GetLowByte());
			
			Utils.PrintInstruciton("LD C, L", ins, r_cpu.m_PC.get(), null, 0);

			r_cpu.m_PC.Set(r_cpu.m_PC.get() + 1);
			
			break;

		case 0x4E:
			parameters[0] = r_cpu.m_memory.Read(r_cpu.m_HL.get());
			
			r_cpu.m_BC.SetLowByte(parameters[0]);
			
			Utils.PrintInstruciton("LD C, (HL)", ins, r_cpu.m_PC.get(), null, 0);

			r_cpu.m_PC.Set(r_cpu.m_PC.get() + 1);
			
			break;
			
		case 0x4F:
			r_cpu.m_BC.SetLowByte((byte) r_cpu.m_AF.GetHighByte());
			
			Utils.PrintInstruciton("LD C, A", ins, r_cpu.m_PC.get(), null, 0);

			r_cpu.m_PC.Set(r_cpu.m_PC.get() + 1);
			
			break;
			
		case 0x50:
			r_cpu.m_DE.SetHighByte((byte) r_cpu.m_BC.GetHighByte());
			
			Utils.PrintInstruciton("LD D, B", ins, r_cpu.m_PC.get(), null, 0);

			r_cpu.m_PC.Set(r_cpu.m_PC.get() + 1);
			
			break;

		case 0x51:
			r_cpu.m_DE.SetHighByte((byte) r_cpu.m_BC.GetLowByte());
			
			Utils.PrintInstruciton("LD D, C", ins, r_cpu.m_PC.get(), null, 0);

			r_cpu.m_PC.Set(r_cpu.m_PC.get() + 1);
			
			break;

		case 0x52:
			r_cpu.m_DE.SetHighByte((byte) r_cpu.m_DE.GetHighByte());
			
			Utils.PrintInstruciton("LD D, D", ins, r_cpu.m_PC.get(), null, 0);

			r_cpu.m_PC.Set(r_cpu.m_PC.get() + 1);
			
			break;

		case 0x53:
			r_cpu.m_DE.SetHighByte((byte) r_cpu.m_DE.GetLowByte());
			
			Utils.PrintInstruciton("LD D, E", ins, r_cpu.m_PC.get(), null, 0);

			r_cpu.m_PC.Set(r_cpu.m_PC.get() + 1);
			
			break;

		case 0x54:
			r_cpu.m_DE.SetHighByte((byte) r_cpu.m_HL.GetHighByte());
			
			Utils.PrintInstruciton("LD D, H", ins, r_cpu.m_PC.get(), null, 0);

			r_cpu.m_PC.Set(r_cpu.m_PC.get() + 1);
			
			break;

		case 0x55:
			r_cpu.m_DE.SetHighByte((byte) r_cpu.m_HL.GetLowByte());
			
			Utils.PrintInstruciton("LD D, L", ins, r_cpu.m_PC.get(), null, 0);

			r_cpu.m_PC.Set(r_cpu.m_PC.get() + 1);
			
			break;

		case 0x56:
			parameters[0] = r_cpu.m_memory.Read(r_cpu.m_HL.get());
			
			r_cpu.m_DE.SetHighByte(parameters[0]);
			
			Utils.PrintInstruciton("LD D, (HL)", ins, r_cpu.m_PC.get(), null, 0);

			r_cpu.m_PC.Set(r_cpu.m_PC.get() + 1);
			
			break;
			
		case 0x57:
			r_cpu.m_DE.SetHighByte((byte) r_cpu.m_AF.GetHighByte());
			
			Utils.PrintInstruciton("LD D, A", ins, r_cpu.m_PC.get(), null, 0);

			r_cpu.m_PC.Set(r_cpu.m_PC.get() + 1);
			
			break;
			
		case 0x58:
			r_cpu.m_DE.SetLowByte((byte) r_cpu.m_BC.GetHighByte());
			
			Utils.PrintInstruciton("LD E, B", ins, r_cpu.m_PC.get(), null, 0);

			r_cpu.m_PC.Set(r_cpu.m_PC.get() + 1);
			
			break;

		case 0x59:
			r_cpu.m_DE.SetLowByte((byte) r_cpu.m_BC.GetLowByte());
			
			Utils.PrintInstruciton("LD E, C", ins, r_cpu.m_PC.get(), null, 0);

			r_cpu.m_PC.Set(r_cpu.m_PC.get() + 1);
			
			break;

		case 0x5A:
			r_cpu.m_DE.SetLowByte((byte) r_cpu.m_DE.GetHighByte());
			
			Utils.PrintInstruciton("LD E, D", ins, r_cpu.m_PC.get(), null, 0);

			r_cpu.m_PC.Set(r_cpu.m_PC.get() + 1);
			
			break;

		case 0x5B:
			r_cpu.m_DE.SetLowByte((byte) r_cpu.m_DE.GetLowByte());
			
			Utils.PrintInstruciton("LD E, E", ins, r_cpu.m_PC.get(), null, 0);

			r_cpu.m_PC.Set(r_cpu.m_PC.get() + 1);
			
			break;

		case 0x5C:
			r_cpu.m_DE.SetLowByte((byte) r_cpu.m_HL.GetHighByte());
			
			Utils.PrintInstruciton("LD E, H", ins, r_cpu.m_PC.get(), null, 0);

			r_cpu.m_PC.Set(r_cpu.m_PC.get() + 1);
			
			break;

		case 0x5D:
			r_cpu.m_DE.SetLowByte((byte) r_cpu.m_HL.GetLowByte());
			
			Utils.PrintInstruciton("LD E, L", ins, r_cpu.m_PC.get(), null, 0);

			r_cpu.m_PC.Set(r_cpu.m_PC.get() + 1);
			
			break;

		case 0x5E:
			parameters[0] = r_cpu.m_memory.Read(r_cpu.m_HL.get());
			
			r_cpu.m_DE.SetLowByte(parameters[0]);
			
			Utils.PrintInstruciton("LD E, (HL)", ins, r_cpu.m_PC.get(), null, 0);

			r_cpu.m_PC.Set(r_cpu.m_PC.get() + 1);
			
			break;
			
		case 0x5F:
			r_cpu.m_DE.SetLowByte((byte) r_cpu.m_AF.GetHighByte());
			
			Utils.PrintInstruciton("LD E, A", ins, r_cpu.m_PC.get(), null, 0);

			r_cpu.m_PC.Set(r_cpu.m_PC.get() + 1);
			
			break;

		case 0x60:
			r_cpu.m_HL.SetHighByte((byte) r_cpu.m_BC.GetHighByte());
			
			Utils.PrintInstruciton("LD H, B", ins, r_cpu.m_PC.get(), null, 0);

			r_cpu.m_PC.Set(r_cpu.m_PC.get() + 1);
			
			break;

		case 0x61:
			r_cpu.m_HL.SetHighByte((byte) r_cpu.m_BC.GetLowByte());
			
			Utils.PrintInstruciton("LD H, C", ins, r_cpu.m_PC.get(), null, 0);

			r_cpu.m_PC.Set(r_cpu.m_PC.get() + 1);
			
			break;

		case 0x62:
			r_cpu.m_HL.SetHighByte((byte) r_cpu.m_DE.GetHighByte());
			
			Utils.PrintInstruciton("LD H, D", ins, r_cpu.m_PC.get(), null, 0);

			r_cpu.m_PC.Set(r_cpu.m_PC.get() + 1);
			
			break;

		case 0x63:
			r_cpu.m_HL.SetHighByte((byte) r_cpu.m_DE.GetLowByte());
			
			Utils.PrintInstruciton("LD H, E", ins, r_cpu.m_PC.get(), null, 0);

			r_cpu.m_PC.Set(r_cpu.m_PC.get() + 1);
			
			break;

		case 0x64:
			r_cpu.m_HL.SetHighByte((byte) r_cpu.m_HL.GetHighByte());
			
			Utils.PrintInstruciton("LD H, H", ins, r_cpu.m_PC.get(), null, 0);

			r_cpu.m_PC.Set(r_cpu.m_PC.get() + 1);
			
			break;

		case 0x65:
			r_cpu.m_HL.SetHighByte((byte) r_cpu.m_HL.GetLowByte());
			
			Utils.PrintInstruciton("LD H, L", ins, r_cpu.m_PC.get(), null, 0);

			r_cpu.m_PC.Set(r_cpu.m_PC.get() + 1);
			
			break;

		case 0x66:
			parameters[0] = r_cpu.m_memory.Read(r_cpu.m_HL.get());
			
			r_cpu.m_HL.SetHighByte(parameters[0]);
			
			Utils.PrintInstruciton("LD H, (HL)", ins, r_cpu.m_PC.get(), null, 0);

			r_cpu.m_PC.Set(r_cpu.m_PC.get() + 1);
			
			break;
			
		case 0x67:
			r_cpu.m_HL.SetHighByte((byte) r_cpu.m_AF.GetHighByte());
			
			Utils.PrintInstruciton("LD H, A", ins, r_cpu.m_PC.get(), null, 0);

			r_cpu.m_PC.Set(r_cpu.m_PC.get() + 1);
			
			break;
			
		case 0x68:
			r_cpu.m_HL.SetLowByte((byte) r_cpu.m_BC.GetHighByte());
			
			Utils.PrintInstruciton("LD L, B", ins, r_cpu.m_PC.get(), null, 0);

			r_cpu.m_PC.Set(r_cpu.m_PC.get() + 1);
			
			break;

		case 0x69:
			r_cpu.m_HL.SetLowByte((byte) r_cpu.m_BC.GetLowByte());
			
			Utils.PrintInstruciton("LD L, C", ins, r_cpu.m_PC.get(), null, 0);

			r_cpu.m_PC.Set(r_cpu.m_PC.get() + 1);
			
			break;

		case 0x6A:
			r_cpu.m_HL.SetLowByte((byte) r_cpu.m_DE.GetHighByte());
			
			Utils.PrintInstruciton("LD L, D", ins, r_cpu.m_PC.get(), null, 0);

			r_cpu.m_PC.Set(r_cpu.m_PC.get() + 1);
			
			break;

		case 0x6B:
			r_cpu.m_HL.SetLowByte((byte) r_cpu.m_DE.GetLowByte());
			
			Utils.PrintInstruciton("LD L, E", ins, r_cpu.m_PC.get(), null, 0);

			r_cpu.m_PC.Set(r_cpu.m_PC.get() + 1);
			
			break;

		case 0x6C:
			r_cpu.m_HL.SetLowByte((byte) r_cpu.m_HL.GetHighByte());
			
			Utils.PrintInstruciton("LD L, H", ins, r_cpu.m_PC.get(), null, 0);

			r_cpu.m_PC.Set(r_cpu.m_PC.get() + 1);
			
			break;

		case 0x6D:
			r_cpu.m_HL.SetLowByte((byte) r_cpu.m_HL.GetLowByte());
			
			Utils.PrintInstruciton("LD L, L", ins, r_cpu.m_PC.get(), null, 0);

			r_cpu.m_PC.Set(r_cpu.m_PC.get() + 1);
			
			break;

		case 0x6E:
			parameters[0] = r_cpu.m_memory.Read(r_cpu.m_HL.get());
			
			r_cpu.m_HL.SetLowByte(parameters[0]);
			
			Utils.PrintInstruciton("LD L, (HL)", ins, r_cpu.m_PC.get(), null, 0);

			r_cpu.m_PC.Set(r_cpu.m_PC.get() + 1);
			
			break;
			
		case 0x6F:
			r_cpu.m_HL.SetLowByte((byte) r_cpu.m_AF.GetHighByte());
			
			Utils.PrintInstruciton("LD L, A", ins, r_cpu.m_PC.get(), null, 0);

			r_cpu.m_PC.Set(r_cpu.m_PC.get() + 1);
			
			break;
			
		case 0x70:
			r_cpu.m_memory.Write((byte) r_cpu.m_BC.GetHighByte(), r_cpu.m_HL.get());
			
			Utils.PrintInstruciton("LD (HL), B", ins, r_cpu.m_PC.get(), null, 0);

			r_cpu.m_PC.Set(r_cpu.m_PC.get() + 1);
			
			break;
			
		case 0x71:
			r_cpu.m_memory.Write((byte) r_cpu.m_BC.GetLowByte(), r_cpu.m_HL.get());
			
			Utils.PrintInstruciton("LD (HL), C", ins, r_cpu.m_PC.get(), null, 0);

			r_cpu.m_PC.Set(r_cpu.m_PC.get() + 1);
			
			break;
			
		case 0x72:
			r_cpu.m_memory.Write((byte) r_cpu.m_DE.GetHighByte(), r_cpu.m_HL.get());
			
			Utils.PrintInstruciton("LD (HL), D", ins, r_cpu.m_PC.get(), null, 0);

			r_cpu.m_PC.Set(r_cpu.m_PC.get() + 1);
			
			break;
			
		case 0x73:
			r_cpu.m_memory.Write((byte) r_cpu.m_DE.GetLowByte(), r_cpu.m_HL.get());
			
			Utils.PrintInstruciton("LD (HL), E", ins, r_cpu.m_PC.get(), null, 0);

			r_cpu.m_PC.Set(r_cpu.m_PC.get() + 1);
			
			break;

		case 0x74:
			r_cpu.m_memory.Write((byte) r_cpu.m_HL.GetHighByte(), r_cpu.m_HL.get());
			
			Utils.PrintInstruciton("LD (HL), H", ins, r_cpu.m_PC.get(), null, 0);

			r_cpu.m_PC.Set(r_cpu.m_PC.get() + 1);
			
			break;

		case 0x75:
			r_cpu.m_memory.Write((byte) r_cpu.m_HL.GetLowByte(), r_cpu.m_HL.get());
			
			Utils.PrintInstruciton("LD (HL), L", ins, r_cpu.m_PC.get(), null, 0);

			r_cpu.m_PC.Set(r_cpu.m_PC.get() + 1);
			
			break;

		case 0x77:
			r_cpu.m_memory.Write((byte) r_cpu.m_AF.GetHighByte(), r_cpu.m_HL.get());
			
			Utils.PrintInstruciton("LD (HL), A", ins, r_cpu.m_PC.get(), null, 0);

			r_cpu.m_PC.Set(r_cpu.m_PC.get() + 1);
			
			break;
			
		case 0x78:
			r_cpu.m_AF.SetHighByte((byte) r_cpu.m_BC.GetHighByte());
			
			Utils.PrintInstruciton("LD A, B", ins, r_cpu.m_PC.get(), null, 0);

			r_cpu.m_PC.Set(r_cpu.m_PC.get() + 1);
			
			break;

		case 0x79:
			r_cpu.m_AF.SetHighByte((byte) r_cpu.m_BC.GetLowByte());
			
			Utils.PrintInstruciton("LD A, C", ins, r_cpu.m_PC.get(), null, 0);

			r_cpu.m_PC.Set(r_cpu.m_PC.get() + 1);
			
			break;

		case 0x7A:
			r_cpu.m_AF.SetHighByte((byte) r_cpu.m_DE.GetHighByte());
			
			Utils.PrintInstruciton("LD A, D", ins, r_cpu.m_PC.get(), null, 0);

			r_cpu.m_PC.Set(r_cpu.m_PC.get() + 1);
			
			break;

		case 0x7B:
			r_cpu.m_AF.SetHighByte((byte) r_cpu.m_DE.GetLowByte());
			
			Utils.PrintInstruciton("LD A, E", ins, r_cpu.m_PC.get(), null, 0);

			r_cpu.m_PC.Set(r_cpu.m_PC.get() + 1);
			
			break;

		case 0x7C:
			r_cpu.m_AF.SetHighByte((byte) r_cpu.m_HL.GetHighByte());
			
			Utils.PrintInstruciton("LD A, H", ins, r_cpu.m_PC.get(), null, 0);

			r_cpu.m_PC.Set(r_cpu.m_PC.get() + 1);
			
			break;

		case 0x7D:
			r_cpu.m_AF.SetHighByte((byte) r_cpu.m_HL.GetLowByte());
			
			Utils.PrintInstruciton("LD A, L", ins, r_cpu.m_PC.get(), null, 0);

			r_cpu.m_PC.Set(r_cpu.m_PC.get() + 1);
			
			break;

		case 0x7E:
			parameters[0] = r_cpu.m_memory.Read(r_cpu.m_HL.get());
			
			r_cpu.m_AF.SetHighByte(parameters[0]);
			
			Utils.PrintInstruciton("LD A, (HL)", ins, r_cpu.m_PC.get(), null, 0);

			r_cpu.m_PC.Set(r_cpu.m_PC.get() + 1);
			
			break;
			
		case 0x7F:
			r_cpu.m_AF.SetHighByte((byte) r_cpu.m_AF.GetHighByte());
			
			Utils.PrintInstruciton("LD A, A", ins, r_cpu.m_PC.get(), null, 0);

			r_cpu.m_PC.Set(r_cpu.m_PC.get() + 1);
			
			break;
			
		case (byte) 0xE0:
			parameters[0] = r_cpu.m_memory.Read(r_cpu.m_PC.get() + 1);
			
			r_cpu.m_memory.Write((byte) r_cpu.m_AF.GetHighByte(), (int) (parameters[0] & 0xFF) | 0xFF00);
			
			Utils.PrintInstruciton("LDH (a8), A", ins, r_cpu.m_PC.get(), parameters, 1);

			r_cpu.m_PC.Set(r_cpu.m_PC.get() + 2);
			
			break;

		case (byte) 0xE2:
			r_cpu.m_memory.Write((byte) r_cpu.m_AF.GetHighByte(), (int) (r_cpu.m_BC.GetLowByte() & 0xFF) | 0xFF00);
			
			Utils.PrintInstruciton("LDH (C), A", ins, r_cpu.m_PC.get(), null, 0);

			r_cpu.m_PC.Set(r_cpu.m_PC.get() + 2);
			
			break;

		case (byte) 0xEA:
			parameters[0] = r_cpu.m_memory.Read(r_cpu.m_PC.get() + 1);
			parameters[1] = r_cpu.m_memory.Read(r_cpu.m_PC.get() + 2);
			
			r_cpu.m_memory.Write((byte) r_cpu.m_AF.GetHighByte(), (int) (parameters[1] << 8) | (int) parameters[0]);
			
			Utils.PrintInstruciton("LDH (a16), A", ins, r_cpu.m_PC.get(), parameters, 2);

			r_cpu.m_PC.Set(r_cpu.m_PC.get() + 3);
			
			break;

		case (byte) 0xF0:
			parameters[0] = r_cpu.m_memory.Read(r_cpu.m_PC.get() + 1);
			parameters[1] = r_cpu.m_memory.Read((int) (parameters[0] & 0xFF) | 0xFF00);
			
			r_cpu.m_AF.SetHighByte(parameters[1]);
			
			Utils.PrintInstruciton("LDH A, (a8)", ins, r_cpu.m_PC.get(), parameters, 2);

			r_cpu.m_PC.Set(r_cpu.m_PC.get() + 2);
			
			break;
			
		case (byte) 0xF2:
			parameters[0] = r_cpu.m_memory.Read((int) (r_cpu.m_BC.GetLowByte() & 0xFF) | 0xFF00);
			
			r_cpu.m_AF.SetHighByte(parameters[0]);
			
			Utils.PrintInstruciton("LDH A, (C)", ins, r_cpu.m_PC.get(), parameters, 1);

			r_cpu.m_PC.Set(r_cpu.m_PC.get() + 2);
			
			break;
			
		case (byte) 0xF8:
			parameters[0] = r_cpu.m_memory.Read(r_cpu.m_PC.get() + 1);
			
			r_cpu.m_HL.Set(r_cpu.m_SP.get() + parameters[0]);
			
			Utils.PrintInstruciton("LD HL, SP+r8", ins, r_cpu.m_PC.get(), parameters, 1);

			r_cpu.m_PC.Set(r_cpu.m_PC.get() + 2);
		
			break;
		
		case (byte) 0xF9:
			r_cpu.m_SP.Set(r_cpu.m_HL.get());
		
			Utils.PrintInstruciton("LD SP, HL", ins, r_cpu.m_PC.get(), null, 0);
	
			r_cpu.m_PC.Set(r_cpu.m_PC.get() + 1);
		
			break;

		case (byte) 0xFA:
			parameters[0] = r_cpu.m_memory.Read(r_cpu.m_PC.get() + 1);
			parameters[1] = r_cpu.m_memory.Read(r_cpu.m_PC.get() + 2);
			address = (int) (parameters[1] << 8) | (int) parameters[0];
			
			r_cpu.m_AF.SetHighByte(r_cpu.m_memory.Read(address));
			
			Utils.PrintInstruciton("LDH A, (a16)", ins, r_cpu.m_PC.get(), parameters, 2);

			r_cpu.m_PC.Set(r_cpu.m_PC.get() + 3);
			
			break;
			
		default:
			System.err.println("Unknown variant of the \"LD\" opcode: " + Utils.hex(ins & 0xFF));
			
			r_cpu.m_error = true;
			break;
		}
	}
	
}
