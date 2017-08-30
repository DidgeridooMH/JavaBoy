/*
 * BitShift.java
 * 
 * Shifts/rotates the bits of a register or
 * byte in memory.
 */

package core.cpu;

import emulator.Utils;

public class BitShift {
	
	public static void Rotate(CPU r_cpu, byte ins)
	{
		byte highByte = (byte) (ins >> 8);
		byte lowByte = (byte) (ins & 0xFF);
		byte regByte = (byte) (lowByte & 0x07);
		boolean fromCarry = highByte > 0;
		boolean direction = lowByte > 7;
		
		String instruction = "";
		String register = "";
		
		instruction = (direction) ? "RR" : "RL";
		
		switch(regByte)
		{
		case 0x00:
			if(direction)
				RotateRight(r_cpu.m_BC, r_cpu.m_flags, true, fromCarry);
			else
				RotateLeft(r_cpu.m_BC, r_cpu.m_flags, true, fromCarry);
			register = "B";
			break;
		case 0x01:
			if(direction)
				RotateRight(r_cpu.m_BC, r_cpu.m_flags, false, fromCarry);
			else
				RotateLeft(r_cpu.m_BC, r_cpu.m_flags, false, fromCarry);
			register = "C";
			break;
		case 0x02:
			if(direction)
				RotateRight(r_cpu.m_DE, r_cpu.m_flags, true, fromCarry);
			else
				RotateLeft(r_cpu.m_DE, r_cpu.m_flags, true, fromCarry);
			register = "D";
			break;
		case 0x03:
			if(direction)
				RotateRight(r_cpu.m_DE, r_cpu.m_flags, false, fromCarry);
			else
				RotateLeft(r_cpu.m_DE, r_cpu.m_flags, false, fromCarry);
			register = "E";
			break;
		case 0x04:
			if(direction)
				RotateRight(r_cpu.m_HL, r_cpu.m_flags, true, fromCarry);
			else
				RotateLeft(r_cpu.m_HL, r_cpu.m_flags, true, fromCarry);
			register = "H";
			break;
		case 0x05:
			if(direction)
				RotateRight(r_cpu.m_HL, r_cpu.m_flags, false, fromCarry);
			else
				RotateLeft(r_cpu.m_HL, r_cpu.m_flags, false, fromCarry);
			register = "L";
			break;
		case 0x06:
			// Make overloaded functions
			register = "(HL)";
			break;
		case 0x07:
			if(direction)
				RotateRight(r_cpu.m_AF, r_cpu.m_flags, true, fromCarry);
			else
				RotateLeft(r_cpu.m_AF, r_cpu.m_flags, true, fromCarry);
			register = "A";
			break;
		default:
			System.err.println("Error while parsing Rotate instruciton: " +
								Utils.hex(ins) + " at " +
								Utils.hex(r_cpu.m_PC.get()));
			r_cpu.m_error = true;
			return;
		}
		
		r_cpu.m_flags.setSubtract(false);
		r_cpu.m_flags.setHalfCarry(false);
		
		Utils.PrintInstruciton(instruction + " " + register, ins, r_cpu.m_PC.get(), null, 0);
		
		r_cpu.m_PC.Set(r_cpu.m_PC.get() + 2);
		
	}
	
	public static void RotateLeft(Register r_reg, Flags r_flags, boolean highLow, boolean carry)
	{
		byte bit7 = (byte) ((r_reg.get() > 0x80) ? 1 : 0);
		byte value = (byte) (r_reg.get() << 1);
		
		if(carry)
		{
			if(r_flags.getCarry())
				value += 1;
			if(bit7 > 0)
				r_flags.setCarry(true);
			else
				r_flags.setCarry(false);
		}
		else
		{
			if(bit7 > 0)
			{
				value += 1;
				r_flags.setCarry(true);
			}
			else
				r_flags.setCarry(false);
		}
		
		r_reg.Set(value);
	}

	public static void RotateRight(Register r_reg, Flags r_flags, boolean highLow, boolean carry)
	{
		byte bit0 = (byte) (((r_reg.get() & 0x01) > 0x0) ? 1 : 0);
		byte value = (byte) (r_reg.get() >> 1);
		
		if(carry)
		{
			if(r_flags.getCarry())
				value += 0x80;
			if(bit0 > 0)
				r_flags.setCarry(true);
			else
				r_flags.setCarry(false);
		}
		else
		{
			if(bit0 > 0)
			{
				value += 0x80;
				r_flags.setCarry(true);
			}
			else
				r_flags.setCarry(false);
		}
		
		r_reg.Set(value);
	}
}
