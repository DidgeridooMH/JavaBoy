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
import emulator.core.memory.Memory;

/**
 * Fetches an instruction from memory pointed to by
 * the Program Counter then parses and executes the
 * instruction.
 * 
 * @author Daniel Simpkins
 */
public class CPU {
	
	/** Indicates an error occurred in the CPU. */
	public boolean error = false;
	
	/** 16-bit representations of the CPU registers. */
	Register AF;
	Register BC;
	Register DE;
	Register HL;
	Register SP;
	Register PC;
	
	/** Flags used by CPU */
	public Flags flags;
	
	/** Memory with a size of 0xFFFF */
	Memory memory = null;
	
	/** Decodes opcodes */
	private Parser parser;

	private Opcodes opcodes;
	
	/**
	 * Handles all CPU emulation including registers,
	 * flags, fetch-execute cycles, and memory
	 * management.
	 * 
	 * @param memory Reference to the memory object
	 */
	public CPU(Memory memory) {
		/*
		 * Initialize 16-bit registers
		 * IO registers, and memory.
		 */
		AF = new Register(0x01B0, "AF");
		BC = new Register(0x0013, "BC");
		DE = new Register(0x00D8, "DE");
		HL = new Register(0x014D, "HL");
		SP = new Register(0xFFFE, "SP");
		PC = new Register(0x0000, "PC");
		
		this.memory = memory;
		
		/* Initializes hardware IO registers */
		memory.write((byte) 0x00, 0xFF05);
		memory.write((byte) 0x00, 0xFF06);
		memory.write((byte) 0x00, 0xFF07);
		memory.write((byte) 0x80, 0xFF10);
		memory.write((byte) 0xBF, 0xFF11);
		memory.write((byte) 0xF3, 0xFF12);
		memory.write((byte) 0xBF, 0xFF14);
		memory.write((byte) 0x3F, 0xFF16);
		memory.write((byte) 0x00, 0xFF17);
		memory.write((byte) 0xBF, 0xFF19);
		memory.write((byte) 0x7F, 0xFF1A);
		memory.write((byte) 0xFF, 0xFF1B);
		memory.write((byte) 0x9F, 0xFF1C);
		memory.write((byte) 0xBF, 0xFF1E);
		memory.write((byte) 0xFF, 0xFF20);
		memory.write((byte) 0x00, 0xFF21);
		memory.write((byte) 0x00, 0xFF22);
		memory.write((byte) 0xBF, 0xFF23);
		memory.write((byte) 0x77, 0xFF24);
		memory.write((byte) 0xF3, 0xFF25);
		memory.write((byte) 0xF1, 0xFF26);
		memory.write((byte) 0x00, 0xFF42);
		memory.write((byte) 0x00, 0xFF43);
		memory.write((byte) 0x00, 0xFF44);
		memory.write((byte) 0x00, 0xFF45);
		memory.write((byte) 0xFC, 0xFF47);
		memory.write((byte) 0xFF, 0xFF48);
		memory.write((byte) 0xFF, 0xFF49);
		memory.write((byte) 0x00, 0xFF4A);
		memory.write((byte) 0x00, 0xFF4B);
		memory.write((byte) 0x00, 0xFFFF);

		flags = new Flags();
		
		parser = new Parser();

		opcodes = new Opcodes(this);
	}
	
	/**
	 * Reads memory pointed to by Program Counter,
	 * parses the instruction, and executes it.
	 */
	public void execute() {
		int instruction = memory.read(PC.get());
		String decodedIns = parser.decodeIns(instruction);

		step((byte) instruction, decodedIns);
	}
	
	public boolean isError() {
		return error;
	}
	
	/**
	 * Executes an instruction.
	 * 
	 * @param ins
	 * @param decodedIns
	 */
	private void step(byte ins, String decodedIns) {
	        if(opcodes.isValid(ins)) {
	            opcodes.execute(ins);
            } else {
                switch (decodedIns) {
                    case "LD":
                    case "LDH":
                        Load.load(this, ins);
                        break;
                    case "SUB":
                        Subtract.subtract(this, ins);
                        break;
                    case "PREFIX":
                        stepPrefix();
                        break;
                    case "JR":
                        Jump.jumpSubroutine(this, ins);
                        break;
                    case "CALL":
                        Jump.call(this, ins);
                        break;
                    case "PUSH":
                        Stack.push(this, ins);
                        break;
                    case "POP":
                        Stack.pop(this, ins);
                        break;
                    case "RLA":
                        BitOperation.rotate(this, ins, false);
                        break;
                    case "INC":
                        Increment.increment(this, ins);
                        break;
                    case "RET":
                        Jump.returnFromCall(this, ins);
                        break;
                    case "NOP":
                        // No Operation
                        Utils.PrintInstruction("NOP", ins, PC.get(), null, 0);
                        PC.set(PC.get() + 1);
                        break;
                    case "JP":
                        Jump.jump(this, ins);
                        break;
                    case "DI":
                        flags.disableInterrupts();
                        Utils.PrintInstruction("DI", ins, PC.get(), null, 0);
                        PC.set(PC.get() + 1);
                        break;
                    default:
                        System.err.println("Unknown opcode: " +
                                decodedIns + " " +
                                Utils.hex(ins & 0xFF) +
                                " at " +
                                Utils.hex(PC.get())
                        );
                        error = true;
                }
            }
	}
	
	/** 
	 * Executes a prefixed instruction.
	 */
	public void stepPrefix() {
		byte instruction = memory.read(PC.get()+1);
		String decodedPrefix = parser.decodePrefix(instruction);
		
		switch(decodedPrefix) {
			case "BIT":
				BitOperation.bitTest(this, instruction);
				break;
				
			case "RL":
				BitOperation.rotate(this, instruction, true);
				break;

            case "RES":
                BitOperation.resetBit(this, instruction);
                break;

			default:
				System.err.println("Unknown prefix: " + 
									decodedPrefix + " " + 
									Utils.hex(instruction & 0xFF) + 
									" at " + 
									Utils.hex(PC.get()));
				error = true;
				break;
		}
	}
	
	/**
	 * Pushes a byte into the stack.
	 * 
	 * @param in Byte to store in stack.
	 */
	public void push(byte in) {
		memory.write(in, SP.get());
		SP.set(SP.get() - 1);
	}
	
	/**
	 * Pushes a Word into the stack.
	 * 
	 * @param in Word to store in stack.
	 */
	public void push16(short in) {
		push((byte) ((in >> 8) & 0xFF));
		push((byte) (in & 0xFF));
	}
	
	/**
	 * Pops a byte from  the stack.
	 * 
	 * @return Byte from stack.
	 */
	public byte pop() {
		SP.set(SP.get() + 1);
		return memory.read(SP.get());
	}
	
	/**
	 * Pops a Word from the stack.
	 * 
	 * @return Word from stack.
	 */
	public short pop16() {
		short lowByte = (short) (pop() & 0xFF);
		short highByte = pop();
		
		return (short) (((highByte & 0xFFFF) << 8) | (lowByte & 0xFF));
	}
	
}
