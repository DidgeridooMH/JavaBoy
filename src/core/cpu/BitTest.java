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

		case (byte) 0x41:
			if(r_cpu.m_BC.GetLowByte() > 0x0)
				r_cpu.m_flags.setZero(true);
			else
				r_cpu.m_flags.setZero(false);
		
			r_cpu.m_flags.setSubtract(false);
			r_cpu.m_flags.setHalfCarry(true);
			
			Utils.PrintInstruciton("BIT 0, C", ins, r_cpu.m_PC.get(), null, 0);
			
			r_cpu.m_PC.Set(r_cpu.m_PC.get() + 2);
			
			break;

		case (byte) 0x42:
			if(r_cpu.m_DE.GetHighByte() > 0x0)
				r_cpu.m_flags.setZero(true);
			else
				r_cpu.m_flags.setZero(false);
		
			r_cpu.m_flags.setSubtract(false);
			r_cpu.m_flags.setHalfCarry(true);
			
			Utils.PrintInstruciton("BIT 0, D", ins, r_cpu.m_PC.get(), null, 0);
			
			r_cpu.m_PC.Set(r_cpu.m_PC.get() + 2);
			
			break;

		case (byte) 0x43:
			if(r_cpu.m_DE.GetLowByte() > 0x0)
				r_cpu.m_flags.setZero(true);
			else
				r_cpu.m_flags.setZero(false);
		
			r_cpu.m_flags.setSubtract(false);
			r_cpu.m_flags.setHalfCarry(true);
			
			Utils.PrintInstruciton("BIT 0, E", ins, r_cpu.m_PC.get(), null, 0);
			
			r_cpu.m_PC.Set(r_cpu.m_PC.get() + 2);
			
			break;

		case (byte) 0x44:
			if(r_cpu.m_HL.GetHighByte() > 0x0)
				r_cpu.m_flags.setZero(true);
			else
				r_cpu.m_flags.setZero(false);
		
			r_cpu.m_flags.setSubtract(false);
			r_cpu.m_flags.setHalfCarry(true);
			
			Utils.PrintInstruciton("BIT 0, H", ins, r_cpu.m_PC.get(), null, 0);
			
			r_cpu.m_PC.Set(r_cpu.m_PC.get() + 2);
			
			break;

		case (byte) 0x45:
			if(r_cpu.m_HL.GetLowByte() > 0x0)
				r_cpu.m_flags.setZero(true);
			else
				r_cpu.m_flags.setZero(false);
		
			r_cpu.m_flags.setSubtract(false);
			r_cpu.m_flags.setHalfCarry(true);
			
			Utils.PrintInstruciton("BIT 0, L", ins, r_cpu.m_PC.get(), null, 0);
			
			r_cpu.m_PC.Set(r_cpu.m_PC.get() + 2);
			
			break;

		case (byte) 0x46:
			if(r_cpu.m_memory.Read(r_cpu.m_HL.get()) > 0x0)
				r_cpu.m_flags.setZero(true);
			else
				r_cpu.m_flags.setZero(false);
		
			r_cpu.m_flags.setSubtract(false);
			r_cpu.m_flags.setHalfCarry(true);
			
			Utils.PrintInstruciton("BIT 0, (HL)", ins, r_cpu.m_PC.get(), null, 0);
			
			r_cpu.m_PC.Set(r_cpu.m_PC.get() + 2);
			
			break;

		case (byte) 0x47:
			if(r_cpu.m_AF.GetHighByte() > 0x0)
				r_cpu.m_flags.setZero(true);
			else
				r_cpu.m_flags.setZero(false);
		
			r_cpu.m_flags.setSubtract(false);
			r_cpu.m_flags.setHalfCarry(true);
			
			Utils.PrintInstruciton("BIT 0, A", ins, r_cpu.m_PC.get(), null, 0);
			
			r_cpu.m_PC.Set(r_cpu.m_PC.get() + 2);
			
			break;
			
		case (byte) 0x48:
			if(r_cpu.m_BC.GetHighByte() >= 0x02)
				r_cpu.m_flags.setZero(true);
			else
				r_cpu.m_flags.setZero(false);
		
			r_cpu.m_flags.setSubtract(false);
			r_cpu.m_flags.setHalfCarry(true);
			
			Utils.PrintInstruciton("BIT 1, B", ins, r_cpu.m_PC.get(), null, 0);
			
			r_cpu.m_PC.Set(r_cpu.m_PC.get() + 2);
			
			break;

		case (byte) 0x49:
			if(r_cpu.m_BC.GetLowByte() >= 0x02)
				r_cpu.m_flags.setZero(true);
			else
				r_cpu.m_flags.setZero(false);
		
			r_cpu.m_flags.setSubtract(false);
			r_cpu.m_flags.setHalfCarry(true);
			
			Utils.PrintInstruciton("BIT 1, C", ins, r_cpu.m_PC.get(), null, 0);
			
			r_cpu.m_PC.Set(r_cpu.m_PC.get() + 2);
			
			break;

		case (byte) 0x4A:
			if(r_cpu.m_DE.GetHighByte() >= 0x02)
				r_cpu.m_flags.setZero(true);
			else
				r_cpu.m_flags.setZero(false);
		
			r_cpu.m_flags.setSubtract(false);
			r_cpu.m_flags.setHalfCarry(true);
			
			Utils.PrintInstruciton("BIT 1, D", ins, r_cpu.m_PC.get(), null, 0);
			
			r_cpu.m_PC.Set(r_cpu.m_PC.get() + 2);
			
			break;

		case (byte) 0x4B:
			if(r_cpu.m_DE.GetLowByte() >= 0x02)
				r_cpu.m_flags.setZero(true);
			else
				r_cpu.m_flags.setZero(false);
		
			r_cpu.m_flags.setSubtract(false);
			r_cpu.m_flags.setHalfCarry(true);
			
			Utils.PrintInstruciton("BIT 1, E", ins, r_cpu.m_PC.get(), null, 0);
			
			r_cpu.m_PC.Set(r_cpu.m_PC.get() + 2);
			
			break;

		case (byte) 0x4C:
			if(r_cpu.m_HL.GetHighByte() >= 0x02)
				r_cpu.m_flags.setZero(true);
			else
				r_cpu.m_flags.setZero(false);
		
			r_cpu.m_flags.setSubtract(false);
			r_cpu.m_flags.setHalfCarry(true);
			
			Utils.PrintInstruciton("BIT 1, H", ins, r_cpu.m_PC.get(), null, 0);
			
			r_cpu.m_PC.Set(r_cpu.m_PC.get() + 2);
			
			break;

		case (byte) 0x4D:
			if(r_cpu.m_HL.GetLowByte() >= 0x02)
				r_cpu.m_flags.setZero(true);
			else
				r_cpu.m_flags.setZero(false);
		
			r_cpu.m_flags.setSubtract(false);
			r_cpu.m_flags.setHalfCarry(true);
			
			Utils.PrintInstruciton("BIT 1, L", ins, r_cpu.m_PC.get(), null, 0);
			
			r_cpu.m_PC.Set(r_cpu.m_PC.get() + 2);
			
			break;

		case (byte) 0x4E:
			if(r_cpu.m_memory.Read(r_cpu.m_HL.get()) >= 0x02)
				r_cpu.m_flags.setZero(true);
			else
				r_cpu.m_flags.setZero(false);
		
			r_cpu.m_flags.setSubtract(false);
			r_cpu.m_flags.setHalfCarry(true);
			
			Utils.PrintInstruciton("BIT 1, (HL)", ins, r_cpu.m_PC.get(), null, 0);
			
			r_cpu.m_PC.Set(r_cpu.m_PC.get() + 2);
			
			break;

		case (byte) 0x4F:
			if(r_cpu.m_AF.GetHighByte() >= 0x02)
				r_cpu.m_flags.setZero(true);
			else
				r_cpu.m_flags.setZero(false);
		
			r_cpu.m_flags.setSubtract(false);
			r_cpu.m_flags.setHalfCarry(true);
			
			Utils.PrintInstruciton("BIT 1, A", ins, r_cpu.m_PC.get(), null, 0);
			
			r_cpu.m_PC.Set(r_cpu.m_PC.get() + 2);
			
			break;
			
		case (byte) 0x50:
			if(r_cpu.m_BC.GetHighByte() >= 0x04)
				r_cpu.m_flags.setZero(true);
			else
				r_cpu.m_flags.setZero(false);
		
			r_cpu.m_flags.setSubtract(false);
			r_cpu.m_flags.setHalfCarry(true);
			
			Utils.PrintInstruciton("BIT 2, B", ins, r_cpu.m_PC.get(), null, 0);
			
			r_cpu.m_PC.Set(r_cpu.m_PC.get() + 2);
			
			break;

		case (byte) 0x51:
			if(r_cpu.m_BC.GetLowByte() >= 0x04)
				r_cpu.m_flags.setZero(true);
			else
				r_cpu.m_flags.setZero(false);
		
			r_cpu.m_flags.setSubtract(false);
			r_cpu.m_flags.setHalfCarry(true);
			
			Utils.PrintInstruciton("BIT 2, C", ins, r_cpu.m_PC.get(), null, 0);
			
			r_cpu.m_PC.Set(r_cpu.m_PC.get() + 2);
			
			break;

		case (byte) 0x52:
			if(r_cpu.m_DE.GetHighByte() >= 0x04)
				r_cpu.m_flags.setZero(true);
			else
				r_cpu.m_flags.setZero(false);
		
			r_cpu.m_flags.setSubtract(false);
			r_cpu.m_flags.setHalfCarry(true);
			
			Utils.PrintInstruciton("BIT 2, D", ins, r_cpu.m_PC.get(), null, 0);
			
			r_cpu.m_PC.Set(r_cpu.m_PC.get() + 2);
			
			break;

		case (byte) 0x53:
			if(r_cpu.m_DE.GetLowByte() >= 0x04)
				r_cpu.m_flags.setZero(true);
			else
				r_cpu.m_flags.setZero(false);
		
			r_cpu.m_flags.setSubtract(false);
			r_cpu.m_flags.setHalfCarry(true);
			
			Utils.PrintInstruciton("BIT 2, E", ins, r_cpu.m_PC.get(), null, 0);
			
			r_cpu.m_PC.Set(r_cpu.m_PC.get() + 2);
			
			break;

		case (byte) 0x54:
			if(r_cpu.m_HL.GetHighByte() >= 0x04)
				r_cpu.m_flags.setZero(true);
			else
				r_cpu.m_flags.setZero(false);
		
			r_cpu.m_flags.setSubtract(false);
			r_cpu.m_flags.setHalfCarry(true);
			
			Utils.PrintInstruciton("BIT 2, H", ins, r_cpu.m_PC.get(), null, 0);
			
			r_cpu.m_PC.Set(r_cpu.m_PC.get() + 2);
			
			break;

		case (byte) 0x55:
			if(r_cpu.m_HL.GetLowByte() >= 0x04)
				r_cpu.m_flags.setZero(true);
			else
				r_cpu.m_flags.setZero(false);
		
			r_cpu.m_flags.setSubtract(false);
			r_cpu.m_flags.setHalfCarry(true);
			
			Utils.PrintInstruciton("BIT 2, L", ins, r_cpu.m_PC.get(), null, 0);
			
			r_cpu.m_PC.Set(r_cpu.m_PC.get() + 2);
			
			break;

		case (byte) 0x56:
			if(r_cpu.m_memory.Read(r_cpu.m_HL.get()) >= 0x04)
				r_cpu.m_flags.setZero(true);
			else
				r_cpu.m_flags.setZero(false);
		
			r_cpu.m_flags.setSubtract(false);
			r_cpu.m_flags.setHalfCarry(true);
			
			Utils.PrintInstruciton("BIT 2, (HL)", ins, r_cpu.m_PC.get(), null, 0);
			
			r_cpu.m_PC.Set(r_cpu.m_PC.get() + 2);
			
			break;

		case (byte) 0x57:
			if(r_cpu.m_AF.GetHighByte() >= 0x04)
				r_cpu.m_flags.setZero(true);
			else
				r_cpu.m_flags.setZero(false);
		
			r_cpu.m_flags.setSubtract(false);
			r_cpu.m_flags.setHalfCarry(true);
			
			Utils.PrintInstruciton("BIT 2, A", ins, r_cpu.m_PC.get(), null, 0);
			
			r_cpu.m_PC.Set(r_cpu.m_PC.get() + 2);
			
			break;
			
		case (byte) 0x58:
			if(r_cpu.m_BC.GetHighByte() >= 0x08)
				r_cpu.m_flags.setZero(true);
			else
				r_cpu.m_flags.setZero(false);
		
			r_cpu.m_flags.setSubtract(false);
			r_cpu.m_flags.setHalfCarry(true);
			
			Utils.PrintInstruciton("BIT 3, B", ins, r_cpu.m_PC.get(), null, 0);
			
			r_cpu.m_PC.Set(r_cpu.m_PC.get() + 2);
			
			break;

		case (byte) 0x59:
			if(r_cpu.m_BC.GetLowByte() >= 0x08)
				r_cpu.m_flags.setZero(true);
			else
				r_cpu.m_flags.setZero(false);
		
			r_cpu.m_flags.setSubtract(false);
			r_cpu.m_flags.setHalfCarry(true);
			
			Utils.PrintInstruciton("BIT 3, C", ins, r_cpu.m_PC.get(), null, 0);
			
			r_cpu.m_PC.Set(r_cpu.m_PC.get() + 2);
			
			break;

		case (byte) 0x5A:
			if(r_cpu.m_DE.GetHighByte() >= 0x08)
				r_cpu.m_flags.setZero(true);
			else
				r_cpu.m_flags.setZero(false);
		
			r_cpu.m_flags.setSubtract(false);
			r_cpu.m_flags.setHalfCarry(true);
			
			Utils.PrintInstruciton("BIT 3, D", ins, r_cpu.m_PC.get(), null, 0);
			
			r_cpu.m_PC.Set(r_cpu.m_PC.get() + 2);
			
			break;

		case (byte) 0x5B:
			if(r_cpu.m_DE.GetLowByte() >= 0x08)
				r_cpu.m_flags.setZero(true);
			else
				r_cpu.m_flags.setZero(false);
		
			r_cpu.m_flags.setSubtract(false);
			r_cpu.m_flags.setHalfCarry(true);
			
			Utils.PrintInstruciton("BIT 3, E", ins, r_cpu.m_PC.get(), null, 0);
			
			r_cpu.m_PC.Set(r_cpu.m_PC.get() + 2);
			
			break;

		case (byte) 0x5C:
			if(r_cpu.m_HL.GetHighByte() >= 0x08)
				r_cpu.m_flags.setZero(true);
			else
				r_cpu.m_flags.setZero(false);
		
			r_cpu.m_flags.setSubtract(false);
			r_cpu.m_flags.setHalfCarry(true);
			
			Utils.PrintInstruciton("BIT 3, H", ins, r_cpu.m_PC.get(), null, 0);
			
			r_cpu.m_PC.Set(r_cpu.m_PC.get() + 2);
			
			break;

		case (byte) 0x5D:
			if(r_cpu.m_HL.GetLowByte() >= 0x08)
				r_cpu.m_flags.setZero(true);
			else
				r_cpu.m_flags.setZero(false);
		
			r_cpu.m_flags.setSubtract(false);
			r_cpu.m_flags.setHalfCarry(true);
			
			Utils.PrintInstruciton("BIT 3, L", ins, r_cpu.m_PC.get(), null, 0);
			
			r_cpu.m_PC.Set(r_cpu.m_PC.get() + 2);
			
			break;

		case (byte) 0x5E:
			if(r_cpu.m_memory.Read(r_cpu.m_HL.get()) >= 0x08)
				r_cpu.m_flags.setZero(true);
			else
				r_cpu.m_flags.setZero(false);
		
			r_cpu.m_flags.setSubtract(false);
			r_cpu.m_flags.setHalfCarry(true);
			
			Utils.PrintInstruciton("BIT 3, (HL)", ins, r_cpu.m_PC.get(), null, 0);
			
			r_cpu.m_PC.Set(r_cpu.m_PC.get() + 2);
			
			break;

		case (byte) 0x5F:
			if(r_cpu.m_AF.GetHighByte() >= 0x08)
				r_cpu.m_flags.setZero(true);
			else
				r_cpu.m_flags.setZero(false);
		
			r_cpu.m_flags.setSubtract(false);
			r_cpu.m_flags.setHalfCarry(true);
			
			Utils.PrintInstruciton("BIT 3, A", ins, r_cpu.m_PC.get(), null, 0);
			
			r_cpu.m_PC.Set(r_cpu.m_PC.get() + 2);
			
			break;

		case (byte) 0x60:
			if(r_cpu.m_BC.GetHighByte() >= 0x10)
				r_cpu.m_flags.setZero(true);
			else
				r_cpu.m_flags.setZero(false);
		
			r_cpu.m_flags.setSubtract(false);
			r_cpu.m_flags.setHalfCarry(true);
			
			Utils.PrintInstruciton("BIT 4, B", ins, r_cpu.m_PC.get(), null, 0);
			
			r_cpu.m_PC.Set(r_cpu.m_PC.get() + 2);
			
			break;

		case (byte) 0x61:
			if(r_cpu.m_BC.GetLowByte() >= 0x10)
				r_cpu.m_flags.setZero(true);
			else
				r_cpu.m_flags.setZero(false);
		
			r_cpu.m_flags.setSubtract(false);
			r_cpu.m_flags.setHalfCarry(true);
			
			Utils.PrintInstruciton("BIT 4, C", ins, r_cpu.m_PC.get(), null, 0);
			
			r_cpu.m_PC.Set(r_cpu.m_PC.get() + 2);
			
			break;

		case (byte) 0x62:
			if(r_cpu.m_DE.GetHighByte() >= 0x10)
				r_cpu.m_flags.setZero(true);
			else
				r_cpu.m_flags.setZero(false);
		
			r_cpu.m_flags.setSubtract(false);
			r_cpu.m_flags.setHalfCarry(true);
			
			Utils.PrintInstruciton("BIT 4, D", ins, r_cpu.m_PC.get(), null, 0);
			
			r_cpu.m_PC.Set(r_cpu.m_PC.get() + 2);
			
			break;

		case (byte) 0x63:
			if(r_cpu.m_DE.GetLowByte() >= 0x10)
				r_cpu.m_flags.setZero(true);
			else
				r_cpu.m_flags.setZero(false);
		
			r_cpu.m_flags.setSubtract(false);
			r_cpu.m_flags.setHalfCarry(true);
			
			Utils.PrintInstruciton("BIT 4, E", ins, r_cpu.m_PC.get(), null, 0);
			
			r_cpu.m_PC.Set(r_cpu.m_PC.get() + 2);
			
			break;

		case (byte) 0x64:
			if(r_cpu.m_HL.GetHighByte() >= 0x10)
				r_cpu.m_flags.setZero(true);
			else
				r_cpu.m_flags.setZero(false);
		
			r_cpu.m_flags.setSubtract(false);
			r_cpu.m_flags.setHalfCarry(true);
			
			Utils.PrintInstruciton("BIT 4, H", ins, r_cpu.m_PC.get(), null, 0);
			
			r_cpu.m_PC.Set(r_cpu.m_PC.get() + 2);
			
			break;

		case (byte) 0x65:
			if(r_cpu.m_HL.GetLowByte() >= 0x10)
				r_cpu.m_flags.setZero(true);
			else
				r_cpu.m_flags.setZero(false);
		
			r_cpu.m_flags.setSubtract(false);
			r_cpu.m_flags.setHalfCarry(true);
			
			Utils.PrintInstruciton("BIT 4, L", ins, r_cpu.m_PC.get(), null, 0);
			
			r_cpu.m_PC.Set(r_cpu.m_PC.get() + 2);
			
			break;

		case (byte) 0x66:
			if(r_cpu.m_memory.Read(r_cpu.m_HL.get()) >= 0x10)
				r_cpu.m_flags.setZero(true);
			else
				r_cpu.m_flags.setZero(false);
		
			r_cpu.m_flags.setSubtract(false);
			r_cpu.m_flags.setHalfCarry(true);
			
			Utils.PrintInstruciton("BIT 4, (HL)", ins, r_cpu.m_PC.get(), null, 0);
			
			r_cpu.m_PC.Set(r_cpu.m_PC.get() + 2);
			
			break;

		case (byte) 0x67:
			if(r_cpu.m_AF.GetHighByte() >= 0x10)
				r_cpu.m_flags.setZero(true);
			else
				r_cpu.m_flags.setZero(false);
		
			r_cpu.m_flags.setSubtract(false);
			r_cpu.m_flags.setHalfCarry(true);
			
			Utils.PrintInstruciton("BIT 4, A", ins, r_cpu.m_PC.get(), null, 0);
			
			r_cpu.m_PC.Set(r_cpu.m_PC.get() + 2);
			
			break;
			
		case (byte) 0x68:
			if(r_cpu.m_BC.GetHighByte() >= 0x20)
				r_cpu.m_flags.setZero(true);
			else
				r_cpu.m_flags.setZero(false);
		
			r_cpu.m_flags.setSubtract(false);
			r_cpu.m_flags.setHalfCarry(true);
			
			Utils.PrintInstruciton("BIT 5, B", ins, r_cpu.m_PC.get(), null, 0);
			
			r_cpu.m_PC.Set(r_cpu.m_PC.get() + 2);
			
			break;

		case (byte) 0x69:
			if(r_cpu.m_BC.GetLowByte() >= 0x20)
				r_cpu.m_flags.setZero(true);
			else
				r_cpu.m_flags.setZero(false);
		
			r_cpu.m_flags.setSubtract(false);
			r_cpu.m_flags.setHalfCarry(true);
			
			Utils.PrintInstruciton("BIT 5, C", ins, r_cpu.m_PC.get(), null, 0);
			
			r_cpu.m_PC.Set(r_cpu.m_PC.get() + 2);
			
			break;

		case (byte) 0x6A:
			if(r_cpu.m_DE.GetHighByte() >= 0x20)
				r_cpu.m_flags.setZero(true);
			else
				r_cpu.m_flags.setZero(false);
		
			r_cpu.m_flags.setSubtract(false);
			r_cpu.m_flags.setHalfCarry(true);
			
			Utils.PrintInstruciton("BIT 5, D", ins, r_cpu.m_PC.get(), null, 0);
			
			r_cpu.m_PC.Set(r_cpu.m_PC.get() + 2);
			
			break;

		case (byte) 0x6B:
			if(r_cpu.m_DE.GetLowByte() >= 0x20)
				r_cpu.m_flags.setZero(true);
			else
				r_cpu.m_flags.setZero(false);
		
			r_cpu.m_flags.setSubtract(false);
			r_cpu.m_flags.setHalfCarry(true);
			
			Utils.PrintInstruciton("BIT 5, E", ins, r_cpu.m_PC.get(), null, 0);
			
			r_cpu.m_PC.Set(r_cpu.m_PC.get() + 2);
			
			break;

		case (byte) 0x6C:
			if(r_cpu.m_HL.GetHighByte() >= 0x20)
				r_cpu.m_flags.setZero(true);
			else
				r_cpu.m_flags.setZero(false);
		
			r_cpu.m_flags.setSubtract(false);
			r_cpu.m_flags.setHalfCarry(true);
			
			Utils.PrintInstruciton("BIT 5, H", ins, r_cpu.m_PC.get(), null, 0);
			
			r_cpu.m_PC.Set(r_cpu.m_PC.get() + 2);
			
			break;

		case (byte) 0x6D:
			if(r_cpu.m_HL.GetLowByte() >= 0x20)
				r_cpu.m_flags.setZero(true);
			else
				r_cpu.m_flags.setZero(false);
		
			r_cpu.m_flags.setSubtract(false);
			r_cpu.m_flags.setHalfCarry(true);
			
			Utils.PrintInstruciton("BIT 5, L", ins, r_cpu.m_PC.get(), null, 0);
			
			r_cpu.m_PC.Set(r_cpu.m_PC.get() + 2);
			
			break;

		case (byte) 0x6E:
			if(r_cpu.m_memory.Read(r_cpu.m_HL.get()) >= 0x20)
				r_cpu.m_flags.setZero(true);
			else
				r_cpu.m_flags.setZero(false);
		
			r_cpu.m_flags.setSubtract(false);
			r_cpu.m_flags.setHalfCarry(true);
			
			Utils.PrintInstruciton("BIT 5, (HL)", ins, r_cpu.m_PC.get(), null, 0);
			
			r_cpu.m_PC.Set(r_cpu.m_PC.get() + 2);
			
			break;

		case (byte) 0x6F:
			if(r_cpu.m_AF.GetHighByte() >= 0x20)
				r_cpu.m_flags.setZero(true);
			else
				r_cpu.m_flags.setZero(false);
		
			r_cpu.m_flags.setSubtract(false);
			r_cpu.m_flags.setHalfCarry(true);
			
			Utils.PrintInstruciton("BIT 5, A", ins, r_cpu.m_PC.get(), null, 0);
			
			r_cpu.m_PC.Set(r_cpu.m_PC.get() + 2);
			
			break;

		case (byte) 0x70:
			if(r_cpu.m_BC.GetHighByte() >= 0x40)
				r_cpu.m_flags.setZero(true);
			else
				r_cpu.m_flags.setZero(false);
		
			r_cpu.m_flags.setSubtract(false);
			r_cpu.m_flags.setHalfCarry(true);
			
			Utils.PrintInstruciton("BIT 6, B", ins, r_cpu.m_PC.get(), null, 0);
			
			r_cpu.m_PC.Set(r_cpu.m_PC.get() + 2);
			
			break;

		case (byte) 0x71:
			if(r_cpu.m_BC.GetLowByte() >= 0x40)
				r_cpu.m_flags.setZero(true);
			else
				r_cpu.m_flags.setZero(false);
		
			r_cpu.m_flags.setSubtract(false);
			r_cpu.m_flags.setHalfCarry(true);
			
			Utils.PrintInstruciton("BIT 6, C", ins, r_cpu.m_PC.get(), null, 0);
			
			r_cpu.m_PC.Set(r_cpu.m_PC.get() + 2);
			
			break;

		case (byte) 0x72:
			if(r_cpu.m_DE.GetHighByte() >= 0x40)
				r_cpu.m_flags.setZero(true);
			else
				r_cpu.m_flags.setZero(false);
		
			r_cpu.m_flags.setSubtract(false);
			r_cpu.m_flags.setHalfCarry(true);
			
			Utils.PrintInstruciton("BIT 6, D", ins, r_cpu.m_PC.get(), null, 0);
			
			r_cpu.m_PC.Set(r_cpu.m_PC.get() + 2);
			
			break;

		case (byte) 0x73:
			if(r_cpu.m_DE.GetLowByte() >= 0x40)
				r_cpu.m_flags.setZero(true);
			else
				r_cpu.m_flags.setZero(false);
		
			r_cpu.m_flags.setSubtract(false);
			r_cpu.m_flags.setHalfCarry(true);
			
			Utils.PrintInstruciton("BIT 6, E", ins, r_cpu.m_PC.get(), null, 0);
			
			r_cpu.m_PC.Set(r_cpu.m_PC.get() + 2);
			
			break;

		case (byte) 0x74:
			if(r_cpu.m_HL.GetHighByte() >= 0x40)
				r_cpu.m_flags.setZero(true);
			else
				r_cpu.m_flags.setZero(false);
		
			r_cpu.m_flags.setSubtract(false);
			r_cpu.m_flags.setHalfCarry(true);
			
			Utils.PrintInstruciton("BIT 6, H", ins, r_cpu.m_PC.get(), null, 0);
			
			r_cpu.m_PC.Set(r_cpu.m_PC.get() + 2);
			
			break;

		case (byte) 0x75:
			if(r_cpu.m_HL.GetLowByte() >= 0x40)
				r_cpu.m_flags.setZero(true);
			else
				r_cpu.m_flags.setZero(false);
		
			r_cpu.m_flags.setSubtract(false);
			r_cpu.m_flags.setHalfCarry(true);
			
			Utils.PrintInstruciton("BIT 6, L", ins, r_cpu.m_PC.get(), null, 0);
			
			r_cpu.m_PC.Set(r_cpu.m_PC.get() + 2);
			
			break;

		case (byte) 0x76:
			if(r_cpu.m_memory.Read(r_cpu.m_HL.get()) >= 0x40)
				r_cpu.m_flags.setZero(true);
			else
				r_cpu.m_flags.setZero(false);
		
			r_cpu.m_flags.setSubtract(false);
			r_cpu.m_flags.setHalfCarry(true);
			
			Utils.PrintInstruciton("BIT 6, (HL)", ins, r_cpu.m_PC.get(), null, 0);
			
			r_cpu.m_PC.Set(r_cpu.m_PC.get() + 2);
			
			break;

		case (byte) 0x77:
			if(r_cpu.m_AF.GetHighByte() >= 0x40)
				r_cpu.m_flags.setZero(true);
			else
				r_cpu.m_flags.setZero(false);
		
			r_cpu.m_flags.setSubtract(false);
			r_cpu.m_flags.setHalfCarry(true);
			
			Utils.PrintInstruciton("BIT 6, A", ins, r_cpu.m_PC.get(), null, 0);
			
			r_cpu.m_PC.Set(r_cpu.m_PC.get() + 2);
			
			break;
			
		case (byte) 0x78:
			if(r_cpu.m_BC.GetHighByte() >= 0x80)
				r_cpu.m_flags.setZero(true);
			else
				r_cpu.m_flags.setZero(false);
		
			r_cpu.m_flags.setSubtract(false);
			r_cpu.m_flags.setHalfCarry(true);
			
			Utils.PrintInstruciton("BIT 7, B", ins, r_cpu.m_PC.get(), null, 0);
			
			r_cpu.m_PC.Set(r_cpu.m_PC.get() + 2);
			
			break;

		case (byte) 0x79:
			if(r_cpu.m_BC.GetLowByte() >= 0x80)
				r_cpu.m_flags.setZero(true);
			else
				r_cpu.m_flags.setZero(false);
		
			r_cpu.m_flags.setSubtract(false);
			r_cpu.m_flags.setHalfCarry(true);
			
			Utils.PrintInstruciton("BIT 7, C", ins, r_cpu.m_PC.get(), null, 0);
			
			r_cpu.m_PC.Set(r_cpu.m_PC.get() + 2);
			
			break;

		case (byte) 0x7A:
			if(r_cpu.m_DE.GetHighByte() >= 0x80)
				r_cpu.m_flags.setZero(true);
			else
				r_cpu.m_flags.setZero(false);
		
			r_cpu.m_flags.setSubtract(false);
			r_cpu.m_flags.setHalfCarry(true);
			
			Utils.PrintInstruciton("BIT 7, D", ins, r_cpu.m_PC.get(), null, 0);
			
			r_cpu.m_PC.Set(r_cpu.m_PC.get() + 2);
			
			break;

		case (byte) 0x7B:
			if(r_cpu.m_DE.GetLowByte() >= 0x80)
				r_cpu.m_flags.setZero(true);
			else
				r_cpu.m_flags.setZero(false);
		
			r_cpu.m_flags.setSubtract(false);
			r_cpu.m_flags.setHalfCarry(true);
			
			Utils.PrintInstruciton("BIT 7, E", ins, r_cpu.m_PC.get(), null, 0);
			
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
			
		case (byte) 0x7D:
			if(r_cpu.m_HL.GetLowByte() >= 0x80)
				r_cpu.m_flags.setZero(true);
			else
				r_cpu.m_flags.setZero(false);
		
			r_cpu.m_flags.setSubtract(false);
			r_cpu.m_flags.setHalfCarry(true);
			
			Utils.PrintInstruciton("BIT 7, L", ins, r_cpu.m_PC.get(), null, 0);
			
			r_cpu.m_PC.Set(r_cpu.m_PC.get() + 2);
			
			break;

		case (byte) 0x7E:
			if(r_cpu.m_memory.Read(r_cpu.m_HL.get()) >= 0x80)
				r_cpu.m_flags.setZero(true);
			else
				r_cpu.m_flags.setZero(false);
		
			r_cpu.m_flags.setSubtract(false);
			r_cpu.m_flags.setHalfCarry(true);
			
			Utils.PrintInstruciton("BIT 7, (HL)", ins, r_cpu.m_PC.get(), null, 0);
			
			r_cpu.m_PC.Set(r_cpu.m_PC.get() + 2);
			
			break;

		case (byte) 0x7F:
			if(r_cpu.m_AF.GetHighByte() >= 0x80)
				r_cpu.m_flags.setZero(true);
			else
				r_cpu.m_flags.setZero(false);
		
			r_cpu.m_flags.setSubtract(false);
			r_cpu.m_flags.setHalfCarry(true);
			
			Utils.PrintInstruciton("BIT 7, A", ins, r_cpu.m_PC.get(), null, 0);
			
			r_cpu.m_PC.Set(r_cpu.m_PC.get() + 2);
			
			break;
			
		default:
			System.out.println("Unknown variant of BIT: " + Utils.hex(ins & 0xFF));
			r_cpu.m_error = true;
			break;
		}
	}
	
}
