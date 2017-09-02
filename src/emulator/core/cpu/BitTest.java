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
 * Tests if a bit is set in a byte.
 * 
 * @author Daniel Simpkins
 *
 */
public interface BitTest {
	
	/**
	 * Tests if a bit is set in a register or byte of memory.
	 * 
	 * @param cpu Reference to CPU object.
	 * @param instruction Instruction opcode.
	 */
	static void bitTest(CPU cpu, byte instruction) {
		int regValue = 0x0;
		byte bitValue = 0x0;
		byte regNibble = 0x0;
		byte bitNibble = 0x0;
		String regName = "";
		String testBit = "";
		
		/*
		 * High nibble(4 bits) is the register
		 * to test. Low nibble is the bit to
		 * test for.
		 */
		regNibble = (byte) (instruction & 0x0F);
		bitNibble = (byte) ((instruction >> 4) & 0xF);
		
		switch(regNibble) {
			case 0x0:
				// fall through
			case 0x8:
				regValue = (byte) cpu.BC.getHighByte();
				regName = "B";
				break;
			case 0x1:
				// fall through
			case 0x9:
				regValue = (byte) cpu.BC.getLowByte();
				regName = "C";
				break;				
			case 0x2:
				// fall through
			case 0xA:
				regValue = (byte) cpu.DE.getHighByte();
				regName = "D";
				break;
			case 0x3:
				// fall through
			case 0xB:
				regValue = (byte) cpu.DE.getLowByte();
				regName = "E";
				break;
			case 0x4:
				// fall through
			case 0xC:
				regValue = cpu.HL.getHighByte() & 0x00FF;
				regName = "H";
				break;
			case 0x5:
				// fall through
			case 0xD:
				regValue = (byte) cpu.HL.getLowByte();
				regName = "L";
				break;
			case 0x6:
				// fall through
			case 0xE:
				regValue = (byte) cpu.memory.Read(cpu.HL.get());
				regName = "(HL)";
				break;
			case 0x7:
				// fall through
			case 0xF:
				regValue = (byte) cpu.AF.getHighByte();
				regName = "A";
				break;
			default:
				System.err.println(	"Error parsing BIT instruction at: " + 
									Utils.hex(cpu.PC.get())
				);
				cpu.error = true;
				return;
		}
		
		switch(bitNibble) {
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
				System.err.println("Error parsing BIT instruction at: " + 
									Utils.hex(cpu.PC.get())
				);
				cpu.error = true;
				return;
		}
		
		cpu.flags.setZero(!((regValue & (int)(bitValue & 0xFF)) > 0));
		cpu.flags.setSubtract(false);
		cpu.flags.setHalfCarry(true);
		
		Utils.PrintInstruction("BIT " + 
								testBit + ", " + 
								regName, instruction, 
								cpu.PC.get(), 
								null, 0
		);
		
		cpu.PC.set(cpu.PC.get() + 2);
		
	}
	
}
