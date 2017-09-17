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
 * Increments a register or 16-bit
 * combined register.
 * 
 * @author Daniel Simpkins
 *
 */
public interface Increment {
	
	/**
	 * Increments a register or 16-bit
	 * combined register.
	 * 
	 * @param cpu A reference to the CPU object.
	 * @param instruction Instruction opcode
	 */
	public static void increment(CPU cpu, byte instruction) {
		int initial = 0;
		String reg = "";
		
		switch(instruction) {
			case 0x04:
				initial = cpu.BC.getHighByte();
				
				cpu.BC.setHighByte((byte) (initial + 1));
				cpu.flags.setFlags(initial, cpu.BC.getHighByte(), false, Flags.ZERO | Flags.HALFC);
				
				reg = "B";
				
				break;
			case 0x0C:
				initial = (byte) cpu.BC.getLowByte();
				cpu.BC.setLowByte((byte) (initial + 1));
				cpu.flags.setFlags(initial, cpu.BC.getLowByte(), false, Flags.ZERO | Flags.HALFC);
				
				reg = "C";
				
				break;
			case 0x14:
				initial = (byte) cpu.DE.getHighByte();
				cpu.DE.setHighByte((byte) (initial + 1));
				cpu.flags.setFlags(initial, cpu.DE.getHighByte(), false, Flags.ZERO | Flags.HALFC);
				
				reg = "D";
				
				break;
			case 0x1C:
				initial = (byte) cpu.DE.getLowByte();
				cpu.DE.setLowByte((byte) (initial + 1));
				cpu.flags.setFlags(initial, cpu.DE.getLowByte(), false, Flags.ZERO | Flags.HALFC);
				
				reg = "E";
				
				break;
			case 0x24:
				initial = (byte) cpu.HL.getHighByte();
				cpu.HL.setHighByte((byte) (initial + 1));
				cpu.flags.setFlags(initial, cpu.HL.getHighByte(), false, Flags.ZERO | Flags.HALFC);
				
				reg = "H";
				
				break;
			case 0x2C:
				initial = (byte) cpu.HL.getLowByte();
				cpu.HL.setLowByte((byte) (initial + 1));
				cpu.flags.setFlags(initial, cpu.HL.getLowByte(), false, Flags.ZERO | Flags.HALFC);
				
				reg = "L";
				
				break;
			case 0x34:
				initial = (byte) cpu.memory.Read(cpu.HL.get());
				byte result = (byte) initial++;
				cpu.flags.setFlags(initial, result, false, Flags.ZERO | Flags.HALFC);
				
				reg = "(HL)";
				
				break;
			case 0x3C:
				initial = (byte) cpu.AF.getHighByte();
				cpu.AF.setHighByte((byte) (initial + 1));
				cpu.flags.setFlags(initial, cpu.AF.getHighByte(), false, Flags.ZERO | Flags.HALFC);
				
				reg = "A";
				
				break;
			case 0x03:
				cpu.BC.set(cpu.BC.get() + 1);
				
				reg = "BC";
				
				break;
			case 0x13:
				cpu.DE.set(cpu.DE.get() + 1);
				
				reg = "DE";
				
				break;
			case 0x23:
				cpu.HL.set(cpu.HL.get() + 1);
				
				reg = "HL";
				
				break;
			case 0x33:
				cpu.SP.set(cpu.SP.get() + 1);
				
				reg = "SP";
				
				break;
			default:
				System.err.println("Unknown variant of INC: " +
									Utils.hex(instruction) +
									" at " +
									Utils.hex(cpu.PC.get())
				);
				cpu.error = true;
				return;
		}
		
		Utils.PrintInstruction("INC " + reg, 
								instruction, 
								cpu.PC.get(), 
								null, 0
		);
		
		cpu.PC.set(cpu.PC.get() + 1);
	}
}
