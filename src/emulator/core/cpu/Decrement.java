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
 * Decrements a register or 16-bit
 * combined register.
 * 
 * @author Daniel Simpkins
 *
 */
public interface Decrement {

	/**
	 * Decrements a register or 16-bit
	 * combined register.
	 * 
	 * @param cpu Reference to CPU object
	 * @param instruction Instruction opcode
	 */
	public static void decrement(CPU cpu, byte instruction) {
		int initial = 0;
		String register = "";
	 
		switch(instruction) {
			case 0x05:
				initial = cpu.BC.getHighByte();
				
				cpu.BC.SetHighByte((byte) (initial - 1));
				cpu.flags.setFlags(initial, 
									cpu.BC.getHighByte(), 
									false, 
									Flags.ZERO | 
									Flags.SUBTRACT | 
									Flags.HALFC
				);
				register = "B";
				break;
			case 0x0D:
				initial = (byte) cpu.BC.getLowByte();
				cpu.BC.SetLowByte((byte) (initial - 1));
				cpu.flags.setFlags(initial, 
									cpu.BC.getLowByte(), 
									false, 
									Flags.ZERO | 
									Flags.SUBTRACT | 
									Flags.HALFC
				);
				register = "C";			
				break;
			case 0x15:
				initial = (byte) cpu.DE.getHighByte();
				cpu.DE.SetHighByte((byte) (initial - 1));
				cpu.flags.setFlags(initial, 
									cpu.DE.getHighByte(), 
									false, 
									Flags.ZERO | 
									Flags.SUBTRACT | 
									Flags.HALFC
				);
				register = "D";
				break;
			case 0x1D:
				initial = (byte) cpu.DE.getLowByte();
				cpu.DE.SetLowByte((byte) (initial - 1));
				cpu.flags.setFlags(initial, 
									cpu.DE.getLowByte(), 
									false, 
									Flags.ZERO | 
									Flags.SUBTRACT | 
									Flags.HALFC
				);
				register = "E";
				break;
			case 0x25:
				initial = (byte) cpu.HL.getHighByte();
				cpu.HL.SetHighByte((byte) (initial - 1));
				cpu.flags.setFlags(initial, 
									cpu.HL.getHighByte(), 
									false, 
									Flags.ZERO | 
									Flags.SUBTRACT | 
									Flags.HALFC
				);
				register = "H";
				break;
			case 0x2D:
				initial = (byte) cpu.HL.getLowByte();
				cpu.HL.SetLowByte((byte) (initial - 1));
				cpu.flags.setFlags(initial, 
									cpu.HL.getLowByte(), 
									false, 
									Flags.ZERO | 
									Flags.SUBTRACT | 
									Flags.HALFC
				);
				register = "L";
				break;
			case 0x35:
				initial = (byte) cpu.memory.Read(cpu.HL.get());
				byte result = (byte) initial--;
				cpu.flags.setFlags(initial, 
									result, 
									false, 
									Flags.ZERO | 
									Flags.SUBTRACT | 
									Flags.HALFC
				);
				register = "(HL)";
				break;
			case 0x3D:
				initial = (byte) cpu.AF.getHighByte();
				cpu.AF.SetHighByte((byte) (initial - 1));
				cpu.flags.setFlags(initial, 
									cpu.AF.getHighByte(), 
									false, 
									Flags.ZERO | 
									Flags.SUBTRACT | 
									Flags.HALFC
				);
				register = "A";
				break;
			case 0x0B:
				cpu.BC.set(cpu.BC.get() - 1);
				register = "BC";
				break;
			case 0x1B:
				cpu.DE.set(cpu.DE.get() - 1);
				register = "DE";
				break;
			case 0x2B:
				cpu.HL.set(cpu.HL.get() - 1);
				register = "HL";
				break;
			case 0x3B:
				cpu.SP.set(cpu.SP.get() - 1);
				register = "SP";
				break;
			default:
				System.err.println("Unknown variant of DEC: " +
									Utils.hex(instruction) + 
									" at " +
									Utils.hex(cpu.PC.get())
				);
				cpu.error = true;
				return;
		}

		Utils.PrintInstruction("DEC " + register, 
								instruction, 
								cpu.PC.get(), 
								null, 0
		);
		cpu.PC.set(cpu.PC.get() + 1);
	}
	
}
