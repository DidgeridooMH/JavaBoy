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
 * Shifts/Rotates register or byte of memory.
 * 
 * @author Daniel Simpkins
 */
public interface BitShift {
	
	/**
	 * Parses instruction and rotates/shifts register or memory.
	 * 
	 * @param cpu Reference to the CPU object.
	 * @param instruction Instruction opcode.
	 * @param isPrefix States whether the opcode is prefix or standard.
	 */
	public static void rotate(CPU cpu, byte instruction, boolean isPrefix) {
		byte highByte = (byte) (instruction >> 4);
		byte lowByte = (byte) (instruction & 0xF);
		byte regByte = (byte) (lowByte & 0x07);
		
		/*
		 * Stores which type of instruction to execute
		 * and which direction to rotate the bits.
		 */
		boolean fromCarry = highByte > 0;
		boolean direction = lowByte > 7;
		
		String instructionName = "";
		String register = "";
		
		instructionName = (direction) ? "RR" : "RL";
		
		/*
		 *  Parses the register to be used and which direction to rotate
		 */
		switch(regByte) {
			case 0x00:
				if(direction) {
					rotateRight(cpu.BC, cpu.flags, true, fromCarry, true);
				} else {
					rotateLeft(cpu.BC, cpu.flags, true, fromCarry, true);
				}
				register = "B";
				break;
			case 0x01:
				if(direction) {
					rotateRight(cpu.BC, cpu.flags, false, fromCarry, true);
				} else {
					rotateLeft(cpu.BC, cpu.flags, false, fromCarry, true);
				}
				register = "C";
				break;
			case 0x02:
				if(direction) {
					rotateRight(cpu.DE, cpu.flags, true, fromCarry, true);
				} else {
					rotateLeft(cpu.DE, cpu.flags, true, fromCarry, true);
				}
				register = "D";
				break;
			case 0x03:
				if(direction) {
					rotateRight(cpu.DE, cpu.flags, false, fromCarry, true);
				} else {
					rotateLeft(cpu.DE, cpu.flags, false, fromCarry, true);
				}
				register = "E";
				break;
			case 0x04:
				if(direction) {
					rotateRight(cpu.HL, cpu.flags, true, fromCarry, true);
				} else {
					rotateLeft(cpu.HL, cpu.flags, true, fromCarry, true);
				}
				register = "H";
				break;
			case 0x05:
				if(direction) {
					rotateRight(cpu.HL, cpu.flags, false, fromCarry, true);
				} else {
					rotateLeft(cpu.HL, cpu.flags, false, fromCarry, true);
				}
				register = "L";
				break;
				
			/*
			 * Case 0x06 will be for
			 * (HL). Overload rotate
			 * functions?
			 */
				
			case 0x07:
				if(direction) {
					rotateRight(cpu.AF, cpu.flags, true, fromCarry, isPrefix);
				} else {
					rotateLeft(cpu.AF, cpu.flags, true, fromCarry, isPrefix);
				}
				register = "A";
				break;
			default:
				System.err.println("Error while parsing Rotate instruction: " +
									Utils.hex(instruction) + " at " +
									Utils.hex(cpu.PC.get())
				);
				cpu.error = true;
				return;
		}
		
		cpu.flags.setSubtract(false);
		cpu.flags.setHalfCarry(false);
		
		Utils.PrintInstruction(instructionName + " " + register, 
				instruction, cpu.PC.get(), null, 0
		);
		
		/*
		 * Increments the program counter based on if
		 * the instruction was prefixed or standard.
		 */
		if(isPrefix) {
			cpu.PC.set(cpu.PC.get() + 2);
		} else {
			cpu.PC.set(cpu.PC.get() + 1);
		}
		
	}
	
	/**
	 * Rotates a register or byte from memory one bit to the left.
	 * 
	 * @param register Reference to the 16-bit register object to rotate.
	 * @param flags Reference to the flags object to affect.
	 * @param highLow True for high byte, false for low byte
	 * @param useCarry Set if carry is to be stored into bit 0
	 * @param setZero Set if the zero flag is to be set
	 */
	public static void rotateLeft(	Register register, 
									Flags flags, 
									boolean highLow, 
									boolean useCarry, 
									boolean setZero) {
		
		byte regValue = (byte) ((highLow) ? register.getHighByte() : register.getLowByte());
		
		byte bit7 = (byte) (((regValue & 0x80) > 0) ? 1 : 0);
		byte value = (byte) (regValue << 1);
		
		/*
		 * If set, the carry flag will be stored in
		 * bit 0 of the register. Otherwise, store
		 * bit 7 into bit 0.
		 */
		if(useCarry) {
			if(flags.getCarry()) {
				value += 1;
			}
			
			if(bit7 > 0) {
				flags.setCarry(true);
			} else {
				flags.setCarry(false);
			}
		} else {
			if(bit7 > 0) {
				value += 1;
				flags.setCarry(true);
			} else {
				flags.setCarry(false);
			}
		}

		if(setZero) {
			flags.setZero(value == 0);
		}
		
		if(highLow) {
			register.setHighByte(value);
		} else {
			register.setLowByte(value);
		}
	}

	/**
	 * Rotates a register or byte from memory one bit to the right.
	 * 
	 * @param register Reference to the 16-bit register object to rotate.
	 * @param flags Reference to the flags object to affect.
	 * @param highLow True for high byte, false for low byte.
	 * @param useCarry Set if carry is to be stored into bit 0.
	 * @param setZero Set if the zero flag is to be set.
	 */
	public static void rotateRight(	Register register, 
									Flags flags, 
									boolean highLow, 
									boolean useCarry, 
									boolean setZero ) {
		
		byte regValue = (byte) ((highLow) ? register.getHighByte() : register.getLowByte());
		
		byte bit0 = (byte) (((regValue & 0x01) > 0x0) ? 1 : 0);
		byte value = (byte) (regValue >> 1);
		
		/*
		 * If set, the carry flag will be stored in
		 * bit 0 of the register. Otherwise, store
		 * bit 7 into bit 0.
		 */
		if(useCarry) {
			if(flags.getCarry()) {
				value += 0x80;
			}
			if(bit0 > 0) {
				flags.setCarry(true);
			} else {
				flags.setCarry(false);
			}
		} else {
			if(bit0 > 0) {
				value += 0x80;
				flags.setCarry(true);
			} else {
				flags.setCarry(false);
			}
		}

		if(setZero) {
			flags.setZero(value == 0);
		}
		
		if(highLow) {
			register.setHighByte(value);
		} else {
			register.setLowByte(value);
		}
	}
}
