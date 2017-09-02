/*
 * 
 * MIT License
 * 
 * Copyright (c) 2017 Daniel Simpkins
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 * 
 */

package emulator.core.cpu;

import emulator.Utils;

/**
 * Subtracts registers and byte of memory.
 * 
 * @author Daniel Simpkins
 *
 */
public interface Subtract {
	
	/**
	 * Subtracts registers and byte of memory.
	 * 
	 * @param cpu Reference to CPU object.
	 * @param instruction Instruction opcode.
	 */
	static void subtract(CPU cpu, byte instruction) {
		int value = 0x0;
		int initial = cpu.AF.getHighByte();
		
		switch(instruction) {
			case (byte) 0x90:
				value = cpu.BC.getHighByte();
				break;
			case (byte) 0x91:
				value = cpu.BC.getLowByte();
				break;
			case (byte) 0x92:
				value = cpu.DE.getHighByte();
				break;
			case (byte) 0x93:
				value = cpu.DE.getLowByte();
				break;
			case (byte) 0x94:
				value = cpu.HL.getHighByte();
				break;
			case (byte) 0x95:
				value = cpu.HL.getLowByte();
				break;
			case (byte) 0x96:
				value = cpu.memory.Read(cpu.PC.get());
				break;
			case (byte) 0x97:
				value = cpu.AF.getHighByte();
				break;
			default:
				System.err.println("Error parsing SUB instruction: " + 
								Utils.hex(instruction) + 
								" at " + 
								Utils.hex(cpu.PC.get())
				);
				cpu.error = true;
				return;
		}
		
		cpu.AF.SetHighByte((byte) (cpu.AF.getHighByte() - value));
		cpu.flags.setFlags(initial, 
							cpu.AF.getHighByte(), 
							false, 
							Flags.ZERO | 
							Flags.HALFC | 
							Flags.CARRY
		);
		cpu.flags.setSubtract(true);
		
		cpu.PC.set(cpu.PC.get() + 1);
	}
}
