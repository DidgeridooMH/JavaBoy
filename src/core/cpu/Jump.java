/*
 * Jump.java
 * 
 * Handles all jump-to-subroutine, jump-to-address,
 * and call functions.
 */

package core.cpu;

import emulator.Utils;

public interface Jump {

	static void Call(CPU r_cpu, byte ins)
	{
		int address = 0x0;
		byte parameters[] = { 	r_cpu.m_memory.Read(r_cpu.m_PC.get() + 1),
								r_cpu.m_memory.Read(r_cpu.m_PC.get() + 2) };
		boolean jump = false;
		
		System.out.print(Utils.hex(r_cpu.m_PC.get()) + ": CALL ");
		
		switch(ins)
		{
		case (byte) 0xC4:
			jump = (!r_cpu.m_flags.getZero());
			System.out.print("NZ, a16");
			break;
		case (byte) 0xCC:
			jump = (r_cpu.m_flags.getZero());
			System.out.print("Z, a16");
			break;
		case (byte) 0xD4:
			jump = (!r_cpu.m_flags.getCarry());
			System.out.print("NC, a16");
			break;
		case (byte) 0xDC:
			jump = (r_cpu.m_flags.getCarry());
			System.out.print("C, a16");
			break;
		case (byte) 0xCD:
			jump = true;
			break;
		default:
			System.err.println("Error parsing CALL instruction: " + 
							Utils.hex(ins & 0xFF) + 
							" at " + Utils.hex(r_cpu.m_PC.get()));
		}
		
		System.out.print(Utils.hex(ins & 0xFF));
		
		address = ((short) parameters[1] << 8) | (short) (parameters[0] & 0xFF);
		
		System.out.print(" " + Utils.hex(address) + "\n");
		
		if(jump)
		{
			r_cpu.Push16((short) (r_cpu.m_PC.get() + 3));
			r_cpu.m_PC.Set(address);
		}
		else
			r_cpu.m_PC.Set(r_cpu.m_PC.get() + 3);
	}
	
	static void JumpSubroutine(CPU r_cpu, byte ins)
	{
		byte parameter[] = {r_cpu.m_memory.Read(r_cpu.m_PC.get() + 1) };
		boolean jump = false;
		
		System.out.print(Utils.hex(r_cpu.m_PC.get()) + ": JR ");
		
		switch(ins)
		{
		case 0x18:
			jump = true;
			break;
		case 0x20:
			jump = (!r_cpu.m_flags.getZero());
			System.out.print("NZ ");
			break;
		case 0x28:
			jump = (r_cpu.m_flags.getZero());
			System.out.print("Z ");
			break;
		case 0x30:
			jump = (!r_cpu.m_flags.getCarry());
			System.out.print("NC ");
			break;
		case 0x38:
			jump = (r_cpu.m_flags.getCarry());
			System.out.print("C ");
			break;
		default:
			System.err.println("Unknown variant of JR: " + 
								Utils.hex(ins & 0xFF) + 
								" at " + 
								Utils.hex(r_cpu.m_PC.get()));
			r_cpu.m_error = true;
			return;
		}
		
		System.out.print(Utils.hex(r_cpu.m_PC.get() + parameter[0] + 2) + "(" + Utils.hex(ins) + ")\n");
		
		// Add 2 to account for the JR instruction size
		if(jump)
			r_cpu.m_PC.Set(r_cpu.m_PC.get() + parameter[0] + 2);
		else
			r_cpu.m_PC.Set(r_cpu.m_PC.get() + 2);
		
		return;
	}

	static void Return(CPU r_cpu, byte ins)
	{
		String condition = "";
		
		switch(ins)
		{
		case (byte) 0xC0:
			condition = "NZ";
			
			if(!r_cpu.m_flags.getZero())
				r_cpu.m_PC.Set(r_cpu.Pop16());
			else
				r_cpu.m_PC.Set(r_cpu.m_PC.get() + 2);
			
			break;
			
		case (byte) 0xC8:
			condition = "Z";
		
			if(r_cpu.m_flags.getZero())
				r_cpu.m_PC.Set(r_cpu.Pop16());
			else
				r_cpu.m_PC.Set(r_cpu.m_PC.get() + 2);
			
			break;
			
		case (byte) 0xD0:
			condition = "NC";
		
			if(!r_cpu.m_flags.getCarry())
				r_cpu.m_PC.Set(r_cpu.Pop16());
			else
				r_cpu.m_PC.Set(r_cpu.m_PC.get() + 2);
			
			break;
			
		case (byte) 0xD8:
			condition = "C";
		
			if(r_cpu.m_flags.getCarry())
				r_cpu.m_PC.Set(r_cpu.Pop16());
			else
				r_cpu.m_PC.Set(r_cpu.m_PC.get() + 2);
			
			break;
			
		case (byte) 0xC9:
			r_cpu.m_PC.Set(r_cpu.Pop16());
			break;
			
		default:
			System.err.println("Unknown variant of RET: " + 
								Utils.hex(ins & 0xFF) + 
								" at " + 
								Utils.hex(r_cpu.m_PC.get()));
			r_cpu.m_error = true;
			return;
		}
		
		Utils.PrintInstruction("RET " + condition, ins, r_cpu.m_PC.get(), null, 0);
		
		return;
	}
	
}
