/*
 * BitTest.java
 * 
 * Runs a bit test on registers, memory locations
 * or combined 16bit registers.
 */

package core.cpu;

import emulator.Utils;

public interface BitTest {
	
	static void BIT(CPU r_cpu, byte ins)
	{
		byte regValue = 0x0;
		byte bitValue = 0x0;
		byte regNibble = 0x0;
		byte bitNibble = 0x0;
		String regName = "";
		String testBit = "";
		
		regNibble = (byte) (ins & 0x0F);
		bitNibble = (byte) ((ins >> 4) & 0xF);
		
		switch(regNibble)
		{
		case 0x0:
		case 0x8:
			regValue = (byte) r_cpu.m_BC.GetHighByte();
			regName = "B";
			break;
			
		case 0x1:
		case 0x9:
			regValue = (byte) r_cpu.m_BC.GetLowByte();
			regName = "C";
			break;
			
		case 0x2:
		case 0xA:
			regValue = (byte) r_cpu.m_DE.GetHighByte();
			regName = "D";
			break;
			
		case 0x3:
		case 0xB:
			regValue = (byte) r_cpu.m_DE.GetLowByte();
			regName = "E";
			break;
			
		case 0x4:
		case 0xC:
			regValue = (byte) r_cpu.m_HL.GetHighByte();
			regName = "H";
			break;
			
		case 0x5:
		case 0xD:
			regValue = (byte) r_cpu.m_HL.GetLowByte();
			regName = "L";
			break;
			
		case 0x6:
		case 0xE:
			regValue = (byte) r_cpu.m_memory.Read(r_cpu.m_HL.get());
			regName = "(HL)";
			break;
			
		case 0x7:
		case 0xF:
			regValue = (byte) r_cpu.m_AF.GetHighByte();
			regName = "A";
			break;
			
		default:
			System.err.println("Error parsing BIT instruction at: " + Utils.hex(r_cpu.m_PC.get()));
			r_cpu.m_error = true;
			return;
		}
		
		switch(bitNibble)
		{
		case 0x4:
			bitValue = (regNibble > 0x07) ? (byte) 0x02 : 0x00;
			testBit = (regNibble > 0x07) ? "1" : "0";
			break;
		case 0x5:
			bitValue = (regNibble > 0x07) ? (byte) 0x08 : 0x04;
			testBit = (regNibble > 0x07) ? "3" : "2";
			break;
		case 0x6:
			bitValue = (regNibble > 0x07) ? (byte) 0x20 : 0x10;
			testBit = (regNibble > 0x07) ? "5" : "4";
			break;
		case 0x7:
			bitValue = (regNibble > 0x07) ? (byte) 0x80 : 0x40;
			testBit = (regNibble > 0x07) ? "7" : "6";
			break;
		default:
			System.err.println("Error parsing BIT instruction at: " + Utils.hex(r_cpu.m_PC.get()));
			r_cpu.m_error = true;
			return;
		}
		
		r_cpu.m_flags.setZero((regValue & bitValue) > 0);
		r_cpu.m_flags.setSubtract(false);
		r_cpu.m_flags.setHalfCarry(true);
		
		Utils.PrintInstruciton("BIT " + testBit + ", " + regName, ins, r_cpu.m_PC.get(), null, 0);
		
		r_cpu.m_PC.Set(r_cpu.m_PC.get() + 2);
		
	}
	
}
