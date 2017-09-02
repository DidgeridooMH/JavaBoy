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
 * Compares register or byte in memory.
 * 
 * @author Daniel Simpkins
 *
 */
public interface Compare {

	/**
	 * Subtracts a register or byte in memory from register A
	 * then sets the flags accordingly. Does not affect A.
	 * 
	 * @param cpu Reference to the CPU object.
	 * @param instruction Instruction opcode
	 */
	public static void compare(CPU cpu, byte instruction) {
		byte value = 0x0;
		byte initial = (byte) cpu.AF.getHighByte();
		byte result = 0x0;
		
		String reg = "";
		
		switch(instruction) {
			case (byte) 0xB8:
				value = (byte) cpu.BC.getHighByte();
				reg = "B";
				break;
			case (byte) 0xB9:
				value = (byte) cpu.BC.getLowByte();
				reg = "C";
				break;
			case (byte) 0xBA:
				value = (byte) cpu.DE.getHighByte();
				reg = "D";
				break;
			case (byte) 0xBB:
				value = (byte) cpu.DE.getLowByte();
				reg = "E";
				break;
			case (byte) 0xBC:
				value = (byte) cpu.HL.getHighByte();
				reg = "H";
				break;
			case (byte) 0xBD:
				value = (byte) cpu.HL.getLowByte();
				reg = "L";
				break;
			case (byte) 0xBE:
				value = cpu.memory.Read(cpu.HL.get());
				reg = "(HL)";
				break;
			case (byte) 0xBF:
				value = (byte) cpu.AF.getHighByte();
				reg = "A";
				break;
			case (byte) 0xFE:
				value = (byte) cpu.memory.Read(cpu.PC.get() + 1);
				reg = "d8";
				break;
			default:
				System.err.println("Error while parsing CP command: " + 
									Utils.hex(instruction) + 
									" at " +
									Utils.hex(cpu.PC.get())
				);
				cpu.error = true;
				return;
		}
		
		result = (byte) (initial - value);
		
		cpu.flags.setFlags(initial, result, 
							false, 
							Flags.CARRY | 
							Flags.ZERO | 
							Flags.HALFC | 
							Flags.SUBTRACT
		);
		
		Utils.PrintInstruction("CP " + reg, instruction, 
								cpu.PC.get(), null, 0
		);
		
		if(instruction == 0xFE) {
			cpu.PC.set(cpu.PC.get() + 2);
		} else {
			cpu.PC.set(cpu.PC.get() + 1);
		}
		
		return;
	}
	
}
