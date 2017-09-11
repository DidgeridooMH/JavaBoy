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
 * Handles Pushing and Popping registers,
 * 16-bit combined registers, and bytes of
 * memory to and from the stack.
 * 
 * @author Daniel Simpkins
 *
 */
public interface Stack {
	
	/**
	 * Stores a 16-bit register in
	 * the stack.
	 * 
	 * @param cpu Reference to CPU object.
	 * @param instruction Instruction opcode.
	 */
	public static void push(CPU cpu, byte instruction) {	
		String register = "";
		
		switch(instruction) {
			case (byte) 0xC5:
				cpu.push16((short) cpu.BC.get());
				register = "BC";
				break;
			case (byte) 0xD5:
				cpu.push16((short) cpu.DE.get());
				register = "DE";
				break;
			case (byte) 0xE5:
				cpu.push16((short) cpu.HL.get());
				register = "HL";
				break;
			case (byte) 0xF5:
				cpu.push((byte) cpu.AF.getHighByte());
				cpu.push(cpu.flags.generateReg());
				register = "AF";
				break;
			default:
				System.err.println("Error parsing push instruction: " +
									Utils.hex(instruction) + 
									" at " + 
									Utils.hex(cpu.PC.get())
				);
				cpu.error = true;
				return;
		}
		
		Utils.PrintInstruction("push " + register, 
								instruction, 
								cpu.PC.get(), 
								null, 0
		);
		
		cpu.PC.set(cpu.PC.get() + 1);
	}
	
	/**
	 * Retrieves a 16-bit value from
	 * the stack and stores it in a
	 * register.
	 * 
	 * @param cpu Reference to CPU object.
	 * @param instruction Instruction opcode.
	 */
	public static void pop(CPU cpu, byte instruction) {	
		String register = "";
		
		switch(instruction) {
			case (byte) 0xC1:
				cpu.BC.set(cpu.pop16());
				register = "BC";
				break;
			case (byte) 0xD1:
				cpu.DE.set(cpu.pop16());
				register = "DE";
				break;
			case (byte) 0xE1:
				cpu.HL.set(cpu.pop16());
				register = "HL";
				break;
			case (byte) 0xF1:
				cpu.flags.byteToFlags(cpu.pop());
				cpu.AF.SetHighByte(cpu.pop());
				register = "AF";
				break;
			default:
				System.err.println("Error parsing pop instruction: " +
									Utils.hex(instruction) + 
									" at " + 
									Utils.hex(cpu.PC.get())
				);
				cpu.error = true;
				return;
		}
		
		Utils.PrintInstruction("pop " + register, 
								instruction, 
								cpu.PC.get(), 
								null, 0
		);
		
		cpu.PC.set(cpu.PC.get() + 1);
	}
}
