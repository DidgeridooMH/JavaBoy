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
 * Performs a bitwise Exclusive OR operation
 * on a register or byte in memory.
 * 
 * @author Daniel Simpkins
 *
 */
public interface ExclusiveOR {
	
	/**
	 * Performs a bitwise Exclusive OR operation
	 * on a register or byte in memory.
	 * 
	 * @param cpu Reference to CPU object.
	 * @param instruction Instruction opcode.
	 */
	@SuppressWarnings("static-access")
	static void exclusiveOR(CPU cpu, byte instruction) {
		byte parameter[] = { 0x0 };
		
		switch(instruction) {
			case (byte) 0xA8:
				cpu.AF.SetHighByte((byte) (cpu.AF.getHighByte() ^ cpu.BC.getHighByte()));
				cpu.flags.setFlags(0, cpu.AF.getHighByte(), false, cpu.flags.ZERO);
				cpu.flags.setCarry(false);
				cpu.flags.setHalfCarry(false);
				cpu.flags.setSubtract(false);
				
				Utils.PrintInstruction("XOR B", instruction, cpu.PC.get(), null, 0);
				
				cpu.PC.set(cpu.PC.get() + 1);
				
				break;
			case (byte) 0xA9:
				cpu.AF.SetHighByte((byte) (cpu.AF.getHighByte() ^ cpu.BC.getLowByte()));
				cpu.flags.setFlags(0, cpu.AF.getHighByte(), false, cpu.flags.ZERO);
				cpu.flags.setCarry(false);
				cpu.flags.setHalfCarry(false);
				cpu.flags.setSubtract(false);
				
				Utils.PrintInstruction("XOR C", instruction, cpu.PC.get(), null, 0);
				
				cpu.PC.set(cpu.PC.get() + 1);
				
				break;
			case (byte) 0xAA:
				cpu.AF.SetHighByte((byte) (cpu.AF.getHighByte() ^ cpu.DE.getHighByte()));
				cpu.flags.setFlags(0, cpu.AF.getHighByte(), false, cpu.flags.ZERO);
				cpu.flags.setCarry(false);
				cpu.flags.setHalfCarry(false);
				cpu.flags.setSubtract(false);
				
				Utils.PrintInstruction("XOR D", instruction, cpu.PC.get(), null, 0);
				
				cpu.PC.set(cpu.PC.get() + 1);
				
				break;
			case (byte) 0xAB:
				cpu.AF.SetHighByte((byte) (cpu.AF.getHighByte() ^ cpu.DE.getLowByte()));
				cpu.flags.setFlags(0, cpu.AF.getHighByte(), false, cpu.flags.ZERO);
				cpu.flags.setCarry(false);
				cpu.flags.setHalfCarry(false);
				cpu.flags.setSubtract(false);
				
				Utils.PrintInstruction("XOR E", instruction, cpu.PC.get(), null, 0);
				
				cpu.PC.set(cpu.PC.get() + 1);
				
				break;
			case (byte) 0xAC:
				cpu.AF.SetHighByte((byte) (cpu.AF.getHighByte() ^ cpu.HL.getHighByte()));
				cpu.flags.setFlags(0, cpu.AF.getHighByte(), false, cpu.flags.ZERO);
				cpu.flags.setCarry(false);
				cpu.flags.setHalfCarry(false);
				cpu.flags.setSubtract(false);
				
				Utils.PrintInstruction("XOR H", instruction, cpu.PC.get(), null, 0);
				
				cpu.PC.set(cpu.PC.get() + 1);
				
				break;
			case (byte) 0xAD:
				cpu.AF.SetHighByte((byte) (cpu.AF.getHighByte() ^ cpu.HL.getLowByte()));
				cpu.flags.setFlags(0, cpu.AF.getHighByte(), false, cpu.flags.ZERO);
				cpu.flags.setCarry(false);
				cpu.flags.setHalfCarry(false);
				cpu.flags.setSubtract(false);
				
				Utils.PrintInstruction("XOR L", instruction, cpu.PC.get(), null, 0);
				
				cpu.PC.set(cpu.PC.get() + 1);
				
				break;
			case (byte) 0xAE:
				parameter[0] = cpu.memory.Read(cpu.HL.get());
				
				cpu.AF.SetHighByte((byte) (cpu.AF.getHighByte() ^ parameter[0]));
				cpu.flags.setFlags(0, cpu.AF.getHighByte(), false, cpu.flags.ZERO);
				cpu.flags.setCarry(false);
				cpu.flags.setHalfCarry(false);
				cpu.flags.setSubtract(false);
				
				Utils.PrintInstruction("XOR (HL)", instruction, cpu.PC.get(), parameter, 1);
				
				cpu.PC.set(cpu.PC.get() + 1);
				
				break;
			case (byte) 0xAF:
				cpu.AF.SetHighByte((byte) (cpu.AF.getHighByte() ^ cpu.AF.getHighByte()));
				cpu.flags.setFlags(0, cpu.AF.getHighByte(), false, cpu.flags.ZERO);
				cpu.flags.setCarry(false);
				cpu.flags.setHalfCarry(false);
				cpu.flags.setSubtract(false);
				
				Utils.PrintInstruction("XOR A", instruction, cpu.PC.get(), null, 0);
				
				cpu.PC.set(cpu.PC.get() + 1);
				
				break;
			default:
				System.err.println("Unknown variant of XOR: " + Utils.hex(instruction & 0xFF));
				cpu.error = true;
		}
	}
}
