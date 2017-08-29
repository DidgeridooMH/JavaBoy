/*
 * Jump.java
 * 
 * Handles all jump-to-subroutine, jump-to-address,
 * and call functions.
 */

package core.cpu;

import emulator.Utils;

public interface Jump {

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
	
}
