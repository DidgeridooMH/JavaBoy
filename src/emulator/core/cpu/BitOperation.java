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
 * Bitwise operation OPCodes
 * 
 * @author Daniel Simpkins
 *
 */
public class BitOperation {

    static void and(CPU cpu, byte instruction) {
        byte parameter[] = { 0x0 };

        switch(instruction) {
            case (byte) 0xA0:
                cpu.AF.setHighByte((byte) (cpu.AF.getHighByte() & cpu.BC.getHighByte()));
                cpu.flags.setFlags(0, cpu.AF.getHighByte(), false, Flags.ZERO);
                cpu.flags.setCarry(false);
                cpu.flags.setHalfCarry(false);
                cpu.flags.setSubtract(false);

                Utils.PrintInstruction("AND B", instruction, cpu.PC.get(), null, 0);

                cpu.PC.set(cpu.PC.get() + 1);

                break;
            case (byte) 0xA1:
                cpu.AF.setHighByte((byte) (cpu.AF.getHighByte() & cpu.BC.getLowByte()));
                cpu.flags.setFlags(0, cpu.AF.getHighByte(), false, Flags.ZERO);
                cpu.flags.setCarry(false);
                cpu.flags.setHalfCarry(false);
                cpu.flags.setSubtract(false);

                Utils.PrintInstruction("AND C", instruction, cpu.PC.get(), null, 0);

                cpu.PC.set(cpu.PC.get() + 1);

                break;
            case (byte) 0xA2:
                cpu.AF.setHighByte((byte) (cpu.AF.getHighByte() & cpu.DE.getHighByte()));
                cpu.flags.setFlags(0, cpu.AF.getHighByte(), false, Flags.ZERO);
                cpu.flags.setCarry(false);
                cpu.flags.setHalfCarry(false);
                cpu.flags.setSubtract(false);

                Utils.PrintInstruction("AND D", instruction, cpu.PC.get(), null, 0);

                cpu.PC.set(cpu.PC.get() + 1);

                break;
            case (byte) 0xA3:
                cpu.AF.setHighByte((byte) (cpu.AF.getHighByte() & cpu.DE.getLowByte()));
                cpu.flags.setFlags(0, cpu.AF.getHighByte(), false, Flags.ZERO);
                cpu.flags.setCarry(false);
                cpu.flags.setHalfCarry(false);
                cpu.flags.setSubtract(false);

                Utils.PrintInstruction("AND E", instruction, cpu.PC.get(), null, 0);

                cpu.PC.set(cpu.PC.get() + 1);

                break;
            case (byte) 0xA4:
                cpu.AF.setHighByte((byte) (cpu.AF.getHighByte() & cpu.HL.getHighByte()));
                cpu.flags.setFlags(0, cpu.AF.getHighByte(), false, Flags.ZERO);
                cpu.flags.setCarry(false);
                cpu.flags.setHalfCarry(false);
                cpu.flags.setSubtract(false);

                Utils.PrintInstruction("AND H", instruction, cpu.PC.get(), null, 0);

                cpu.PC.set(cpu.PC.get() + 1);

                break;
            case (byte) 0xA5:
                cpu.AF.setHighByte((byte) (cpu.AF.getHighByte() & cpu.HL.getLowByte()));
                cpu.flags.setFlags(0, cpu.AF.getHighByte(), false, Flags.ZERO);
                cpu.flags.setCarry(false);
                cpu.flags.setHalfCarry(false);
                cpu.flags.setSubtract(false);

                Utils.PrintInstruction("AND L", instruction, cpu.PC.get(), null, 0);

                cpu.PC.set(cpu.PC.get() + 1);

                break;
            case (byte) 0xA6:
                parameter[0] = cpu.memory.read(cpu.HL.get());

                cpu.AF.setHighByte((byte) (cpu.AF.getHighByte() & parameter[0]));
                cpu.flags.setFlags(0, cpu.AF.getHighByte(), false, Flags.ZERO);
                cpu.flags.setCarry(false);
                cpu.flags.setHalfCarry(false);
                cpu.flags.setSubtract(false);

                Utils.PrintInstruction("AND (HL)", instruction, cpu.PC.get(), parameter, 1);

                cpu.PC.set(cpu.PC.get() + 1);

                break;
            case (byte) 0xA7:
                cpu.AF.setHighByte((byte) (cpu.AF.getHighByte() & cpu.AF.getHighByte()));
                cpu.flags.setFlags(0, cpu.AF.getHighByte(), false, Flags.ZERO);
                cpu.flags.setCarry(false);
                cpu.flags.setHalfCarry(false);
                cpu.flags.setSubtract(false);

                Utils.PrintInstruction("AND A", instruction, cpu.PC.get(), null, 0);

                cpu.PC.set(cpu.PC.get() + 1);

                break;
            case (byte) 0xE6:
                cpu.AF.setHighByte((byte) (cpu.AF.getHighByte() & cpu.memory.read(cpu.PC.get() + 1)));
                cpu.flags.setFlags(0, cpu.AF.getHighByte(), false, Flags.ZERO);
                cpu.flags.setCarry(false);
                cpu.flags.setHalfCarry(false);
                cpu.flags.setSubtract(false);

                Utils.PrintInstruction("AND d8", instruction, cpu.PC.get(), null, 0);

                cpu.PC.set(cpu.PC.get() + 1);

                break;
            default:
                System.err.println("Unknown variant of AND: " + Utils.hex(instruction & 0xFF));
                cpu.error = true;
        }
    }

	static void or(CPU cpu, byte instruction) {
		byte parameter[] = { 0x0 };
		
		switch(instruction) {
			case (byte) 0xB0:
				cpu.AF.setHighByte((byte) (cpu.AF.getHighByte() | cpu.BC.getHighByte()));
				cpu.flags.setFlags(0, cpu.AF.getHighByte(), false, Flags.ZERO);
				cpu.flags.setCarry(false);
				cpu.flags.setHalfCarry(false);
				cpu.flags.setSubtract(false);
				
				Utils.PrintInstruction("OR B", instruction, cpu.PC.get(), null, 0);
				
				cpu.PC.set(cpu.PC.get() + 1);
				
				break;
			case (byte) 0xB1:
				cpu.AF.setHighByte((byte) (cpu.AF.getHighByte() | cpu.BC.getLowByte()));
				cpu.flags.setFlags(0, cpu.AF.getHighByte(), false, Flags.ZERO);
				cpu.flags.setCarry(false);
				cpu.flags.setHalfCarry(false);
				cpu.flags.setSubtract(false);
				
				Utils.PrintInstruction("OR C", instruction, cpu.PC.get(), null, 0);
				
				cpu.PC.set(cpu.PC.get() + 1);
				
				break;
			case (byte) 0xB2:
				cpu.AF.setHighByte((byte) (cpu.AF.getHighByte() | cpu.DE.getHighByte()));
				cpu.flags.setFlags(0, cpu.AF.getHighByte(), false, Flags.ZERO);
				cpu.flags.setCarry(false);
				cpu.flags.setHalfCarry(false);
				cpu.flags.setSubtract(false);
				
				Utils.PrintInstruction("OR D", instruction, cpu.PC.get(), null, 0);
				
				cpu.PC.set(cpu.PC.get() + 1);
				
				break;
			case (byte) 0xB3:
				cpu.AF.setHighByte((byte) (cpu.AF.getHighByte() | cpu.DE.getLowByte()));
				cpu.flags.setFlags(0, cpu.AF.getHighByte(), false, Flags.ZERO);
				cpu.flags.setCarry(false);
				cpu.flags.setHalfCarry(false);
				cpu.flags.setSubtract(false);
				
				Utils.PrintInstruction("OR E", instruction, cpu.PC.get(), null, 0);
				
				cpu.PC.set(cpu.PC.get() + 1);
				
				break;
			case (byte) 0xB4:
				cpu.AF.setHighByte((byte) (cpu.AF.getHighByte() | cpu.HL.getHighByte()));
				cpu.flags.setFlags(0, cpu.AF.getHighByte(), false, Flags.ZERO);
				cpu.flags.setCarry(false);
				cpu.flags.setHalfCarry(false);
				cpu.flags.setSubtract(false);
				
				Utils.PrintInstruction("OR H", instruction, cpu.PC.get(), null, 0);
				
				cpu.PC.set(cpu.PC.get() + 1);
				
				break;
			case (byte) 0xB5:
				cpu.AF.setHighByte((byte) (cpu.AF.getHighByte() | cpu.HL.getLowByte()));
				cpu.flags.setFlags(0, cpu.AF.getHighByte(), false, Flags.ZERO);
				cpu.flags.setCarry(false);
				cpu.flags.setHalfCarry(false);
				cpu.flags.setSubtract(false);
				
				Utils.PrintInstruction("OR L", instruction, cpu.PC.get(), null, 0);
				
				cpu.PC.set(cpu.PC.get() + 1);
				
				break;
			case (byte) 0xB6:
				parameter[0] = cpu.memory.read(cpu.HL.get());
				
				cpu.AF.setHighByte((byte) (cpu.AF.getHighByte() | parameter[0]));
				cpu.flags.setFlags(0, cpu.AF.getHighByte(), false, Flags.ZERO);
				cpu.flags.setCarry(false);
				cpu.flags.setHalfCarry(false);
				cpu.flags.setSubtract(false);
				
				Utils.PrintInstruction("OR (HL)", instruction, cpu.PC.get(), parameter, 1);
				
				cpu.PC.set(cpu.PC.get() + 1);
				
				break;
			case (byte) 0xB7:
				cpu.AF.setHighByte((byte) (cpu.AF.getHighByte() | cpu.AF.getHighByte()));
				cpu.flags.setFlags(0, cpu.AF.getHighByte(), false, Flags.ZERO);
				cpu.flags.setCarry(false);
				cpu.flags.setHalfCarry(false);
				cpu.flags.setSubtract(false);
				
				Utils.PrintInstruction("OR A", instruction, cpu.PC.get(), null, 0);
				
				cpu.PC.set(cpu.PC.get() + 1);
				
				break;
			default:
				System.err.println("Unknown variant of OR: " + Utils.hex(instruction & 0xFF));
				cpu.error = true;
		}
	}
	
	/**
	 * Performs a bitwise Exclusive OR operation
	 * on a register or byte in memory.
	 * 
	 * @param cpu Reference to CPU object.
	 * @param instruction Instruction opcode.
	 */
	static void exclusiveOR(CPU cpu, byte instruction) {
		byte parameter[] = { 0x0 };
		
		switch(instruction) {
			case (byte) 0xA8:
				cpu.AF.setHighByte((byte) (cpu.AF.getHighByte() ^ cpu.BC.getHighByte()));
				cpu.flags.setFlags(0, cpu.AF.getHighByte(), false, Flags.ZERO);
				cpu.flags.setCarry(false);
				cpu.flags.setHalfCarry(false);
				cpu.flags.setSubtract(false);
				
				Utils.PrintInstruction("XOR B", instruction, cpu.PC.get(), null, 0);
				
				cpu.PC.set(cpu.PC.get() + 1);
				
				break;
			case (byte) 0xA9:
				cpu.AF.setHighByte((byte) (cpu.AF.getHighByte() ^ cpu.BC.getLowByte()));
				cpu.flags.setFlags(0, cpu.AF.getHighByte(), false, Flags.ZERO);
				cpu.flags.setCarry(false);
				cpu.flags.setHalfCarry(false);
				cpu.flags.setSubtract(false);
				
				Utils.PrintInstruction("XOR C", instruction, cpu.PC.get(), null, 0);
				
				cpu.PC.set(cpu.PC.get() + 1);
				
				break;
			case (byte) 0xAA:
				cpu.AF.setHighByte((byte) (cpu.AF.getHighByte() ^ cpu.DE.getHighByte()));
				cpu.flags.setFlags(0, cpu.AF.getHighByte(), false, Flags.ZERO);
				cpu.flags.setCarry(false);
				cpu.flags.setHalfCarry(false);
				cpu.flags.setSubtract(false);
				
				Utils.PrintInstruction("XOR D", instruction, cpu.PC.get(), null, 0);
				
				cpu.PC.set(cpu.PC.get() + 1);
				
				break;
			case (byte) 0xAB:
				cpu.AF.setHighByte((byte) (cpu.AF.getHighByte() ^ cpu.DE.getLowByte()));
				cpu.flags.setFlags(0, cpu.AF.getHighByte(), false, Flags.ZERO);
				cpu.flags.setCarry(false);
				cpu.flags.setHalfCarry(false);
				cpu.flags.setSubtract(false);
				
				Utils.PrintInstruction("XOR E", instruction, cpu.PC.get(), null, 0);
				
				cpu.PC.set(cpu.PC.get() + 1);
				
				break;
			case (byte) 0xAC:
				cpu.AF.setHighByte((byte) (cpu.AF.getHighByte() ^ cpu.HL.getHighByte()));
				cpu.flags.setFlags(0, cpu.AF.getHighByte(), false, Flags.ZERO);
				cpu.flags.setCarry(false);
				cpu.flags.setHalfCarry(false);
				cpu.flags.setSubtract(false);
				
				Utils.PrintInstruction("XOR H", instruction, cpu.PC.get(), null, 0);
				
				cpu.PC.set(cpu.PC.get() + 1);
				
				break;
			case (byte) 0xAD:
				cpu.AF.setHighByte((byte) (cpu.AF.getHighByte() ^ cpu.HL.getLowByte()));
				cpu.flags.setFlags(0, cpu.AF.getHighByte(), false, Flags.ZERO);
				cpu.flags.setCarry(false);
				cpu.flags.setHalfCarry(false);
				cpu.flags.setSubtract(false);
				
				Utils.PrintInstruction("XOR L", instruction, cpu.PC.get(), null, 0);
				
				cpu.PC.set(cpu.PC.get() + 1);
				
				break;
			case (byte) 0xAE:
				parameter[0] = cpu.memory.read(cpu.HL.get());
				
				cpu.AF.setHighByte((byte) (cpu.AF.getHighByte() ^ parameter[0]));
				cpu.flags.setFlags(0, cpu.AF.getHighByte(), false, Flags.ZERO);
				cpu.flags.setCarry(false);
				cpu.flags.setHalfCarry(false);
				cpu.flags.setSubtract(false);
				
				Utils.PrintInstruction("XOR (HL)", instruction, cpu.PC.get(), parameter, 1);
				
				cpu.PC.set(cpu.PC.get() + 1);
				
				break;
			case (byte) 0xAF:
				cpu.AF.setHighByte((byte) (cpu.AF.getHighByte() ^ cpu.AF.getHighByte()));
				cpu.flags.setFlags(0, cpu.AF.getHighByte(), false, Flags.ZERO);
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

	/**
	 * Parses instruction and rotates/shifts register or memory.
	 * 
	 * @param cpu Reference to the CPU object.
	 * @param instruction Instruction opcode.
	 * @param isPrefix States whether the opcode is prefix or standard.
	 */
	static void rotate(CPU cpu, byte instruction, boolean isPrefix) {
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
	static void rotateLeft(Register register,
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
	static void rotateRight(	Register register,
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
	
	/** Tests if a bit is set in a register or byte of memory.
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
				regValue = cpu.memory.read(cpu.HL.get());
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
		
		cpu.flags.setZero(!((regValue & (bitValue & 0xFF)) > 0));
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

	static void resetBit(CPU cpu, byte instruction) {
        switch(instruction) {
            case (byte)0x87:
                cpu.AF.setHighByte((byte)(cpu.AF.getHighByte() & 0xFE));

                cpu.PC.set(cpu.PC.get() + 2);

                break;
            default:
                System.err.println("Unimplemented RES instruction!!!");
                break;
        }
    }
}
