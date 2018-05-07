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
 * Transfers memory from ram, registers,
 * and 16-bit registers.
 * 
 * @author Daniel Simpkins
 *
 */
public class Load {
	
	/**
	 * Transfers memory from ram, registers,
	 * and 16-bit registers.
	 * 
	 * @param cpu Reference to CPU object.
	 * @param instruction Instruction opcode.
	 */
	static void load(CPU cpu, byte instruction) {
		byte parameters[] = { 0x0, 0x0 };
		int address = 0x0;
		
		switch(instruction) {
			case 0x01:
				parameters[0] = cpu.memory.read(cpu.PC.get()+1);
				parameters[1] = cpu.memory.read(cpu.PC.get()+2);
				
				cpu.BC.setLowByte(parameters[0]);
				cpu.BC.setHighByte(parameters[1]);
				Utils.PrintInstruction("LD BC, d16", 
										instruction, 
										cpu.PC.get(), 
										parameters, 2
				);
				
				cpu.PC.set(cpu.PC.get() + 3);
				
				break;			case 0x02:
				cpu.memory.write((byte) cpu.AF.getHighByte(), cpu.BC.get());
				
				Utils.PrintInstruction("LD (BC) A", 
										instruction, 
										cpu.PC.get(), 
										null, 0);
	
				cpu.PC.set(cpu.PC.get() + 1);
				
				break;			
			case 0x06:
				parameters[0] = cpu.memory.read(cpu.PC.get()+1);
				
				cpu.BC.setHighByte(parameters[0]);
				
				Utils.PrintInstruction("LD B, d8", 
										instruction, 
										cpu.PC.get(), 
										parameters, 1
				);
	
				cpu.PC.set(cpu.PC.get() + 2);
	
				break;			
			case 0x08:
				parameters[0] = cpu.memory.read(cpu.PC.get()+1);
				parameters[1] = cpu.memory.read(cpu.PC.get()+2);
				
				address = ((int) parameters[1] << 8) | (int) parameters[0];
				
				cpu.memory.write((byte) cpu.SP.getHighByte(), address);
				cpu.memory.write((byte) cpu.SP.getLowByte(), address + 1);
	
				Utils.PrintInstruction("LD (a16), SP", 
										instruction, 
										cpu.PC.get(), 
										parameters, 2
				);
	
				cpu.PC.set(cpu.PC.get() + 3);
				
				break;			
			case 0x0A:
				parameters[0] = cpu.memory.read(cpu.BC.get());
				
				cpu.AF.setHighByte(parameters[0]);
	
				Utils.PrintInstruction("LD A, (BC)", 
										instruction, 
										cpu.PC.get(), 
										parameters, 1
				);
	
				cpu.PC.set(cpu.PC.get() + 1);
				
				break;			
			case 0x0E:
				parameters[0] = cpu.memory.read(cpu.PC.get() + 1);
				
				cpu.BC.setLowByte(parameters[0]);
	
				Utils.PrintInstruction("LD C, d8", 
										instruction, 
										cpu.PC.get(), 
										parameters, 1
				);
				cpu.PC.set(cpu.PC.get() + 2);
				
				break;			
			case 0x11:
				parameters[0] = cpu.memory.read(cpu.PC.get() + 1);
				parameters[1] = cpu.memory.read(cpu.PC.get() + 2);
	
				cpu.DE.setLowByte(parameters[0]);
				cpu.DE.setHighByte(parameters[1]);
				Utils.PrintInstruction("LD DE, d16", 
										instruction, 
										cpu.PC.get(), 
										parameters, 2
				);
	
				cpu.PC.set(cpu.PC.get() + 3);
				
				break;			case 0x12:
				cpu.memory.write((byte) cpu.AF.getHighByte(), cpu.DE.get());
				
				Utils.PrintInstruction("LD (DE),A", 
										instruction, 
										cpu.PC.get(), 
										null, 0
				);
	
				cpu.PC.set(cpu.PC.get() + 1);
				
				break;			
			case 0x16:
				parameters[0] = cpu.memory.read(cpu.PC.get() + 1);
				
				cpu.DE.setHighByte(parameters[0]);
				
				Utils.PrintInstruction("LD D,d8", 
										instruction, 
										cpu.PC.get(), 
										parameters, 1
				);
	
				cpu.PC.set(cpu.PC.get() + 2);
				
				break;			case 0x1A:
				parameters[0] = cpu.memory.read(cpu.DE.get());
				cpu.AF.setHighByte(parameters[0]);
				
				Utils.PrintInstruction("LD A,(DE)", 
										instruction, 
										cpu.PC.get(), 
										parameters, 1
				);
	
				cpu.PC.set(cpu.PC.get() + 1);
				
				break;			
			case 0x1E:
				parameters[0] = cpu.memory.read(cpu.PC.get() + 1);
				cpu.DE.setLowByte(parameters[0]);
				
				Utils.PrintInstruction("LD E, d8", 
										instruction, 
										cpu.PC.get(), 
										parameters, 1
				);
	
				cpu.PC.set(cpu.PC.get() + 2);
				
				break;			
			case 0x21:
				parameters[0] = cpu.memory.read(cpu.PC.get() + 1);
				parameters[1] = cpu.memory.read(cpu.PC.get() + 2);
				
				cpu.HL.setHighByte(parameters[1]);
				cpu.HL.setLowByte(parameters[0]);
				
				Utils.PrintInstruction("LD HL, d16", 
										instruction, 
										cpu.PC.get(), 
										parameters, 2
				);
	
				cpu.PC.set(cpu.PC.get() + 3);
				
				break;			
			case 0x22:
				cpu.memory.write((byte) cpu.AF.getHighByte(), cpu.HL.get());
				cpu.HL.set(cpu.HL.get() + 1);
				
				Utils.PrintInstruction("LD (HL+), A", 
										instruction, 
										cpu.PC.get(), 
										null, 0
				);
	
				cpu.PC.set(cpu.PC.get() + 1);
				
				break;			
			case 0x26:
				parameters[0] = cpu.memory.read(cpu.PC.get() + 1);
				cpu.HL.setHighByte(parameters[0]);
				
				Utils.PrintInstruction("LD H, d8", 
										instruction, 
										cpu.PC.get(), 
										parameters, 1
				);
	
				cpu.PC.set(cpu.PC.get() + 2);
				
				break;			
			case 0x2A:
				parameters[0] = cpu.memory.read(cpu.HL.get());
                cpu.HL.set(cpu.HL.get() + 1);
				cpu.AF.setHighByte(parameters[0]);
				
				Utils.PrintInstruction("LD A, (HL+)", 
										instruction, 
										cpu.PC.get(), 
										parameters, 1
				);
	
				cpu.PC.set(cpu.PC.get() + 1);
				
				break;			
			case 0x2E:
				parameters[0] = cpu.memory.read(cpu.PC.get()+1);
				cpu.HL.setLowByte(parameters[0]);
				
				Utils.PrintInstruction("LD L, d8", 
										instruction, 
										cpu.PC.get(), 
										parameters, 1
				);
	
				cpu.PC.set(cpu.PC.get() + 2);
				
				break;			
			case 0x31:
				parameters[0] = cpu.memory.read(cpu.PC.get() + 1);
				parameters[1] = cpu.memory.read(cpu.PC.get() + 2);
				
				cpu.SP.setHighByte(parameters[1]);
				cpu.SP.setLowByte(parameters[0]);
				
				Utils.PrintInstruction("LD SP, d16", 
										instruction, 
										cpu.PC.get(), 
										parameters, 2
				);
	
				cpu.PC.set(cpu.PC.get() + 3);
				
				break;				
			case 0x32:
				cpu.memory.write((byte) cpu.AF.getHighByte(), cpu.HL.get());
                cpu.HL.set(cpu.HL.get() - 1);
				
				Utils.PrintInstruction("LD (HL-), A", 
										instruction, 
										cpu.PC.get(), 
										null, 0
				);
	
				cpu.PC.set(cpu.PC.get() + 1);
				
				break;			case 0x36:
				parameters[0] = cpu.memory.read(cpu.PC.get() + 1);
				cpu.memory.write(parameters[0], cpu.HL.get());
				
				Utils.PrintInstruction("LD (HL), d8", 
										instruction, 
										cpu.PC.get(), 
										parameters, 1
				);
	
				cpu.PC.set(cpu.PC.get() + 2);
				
				break;	
			case 0x3A:
				parameters[0] = cpu.memory.read(cpu.HL.get());
				cpu.AF.setHighByte(parameters[0]);

                cpu.HL.set(cpu.HL.get() - 1);
				
				Utils.PrintInstruction("LD A, (HL-)",
										instruction, 
										cpu.PC.get(), 
										null, 0
				);
	
				cpu.PC.set(cpu.PC.get() + 1);
				
				break;	
			case 0x3E:
				parameters[0] = cpu.memory.read(cpu.PC.get() + 1);
				
				cpu.AF.setHighByte(parameters[0]);
				
				Utils.PrintInstruction("LD A, d8",
										instruction, 
										cpu.PC.get(), 
										parameters, 1
				);
	
				cpu.PC.set(cpu.PC.get() + 2);
				
				break;				
			case 0x40:
				cpu.BC.setHighByte((byte) cpu.BC.getHighByte());
				
				Utils.PrintInstruction("LD B, B",
										instruction, 
										cpu.PC.get(),
										null, 0
				);
	
				cpu.PC.set(cpu.PC.get() + 1);
				
				break;	
			case 0x41:
				cpu.BC.setHighByte((byte) cpu.BC.getLowByte());
				
				Utils.PrintInstruction("LD B, C",
										instruction, 
										cpu.PC.get(), 
										null, 0
				);
	
				cpu.PC.set(cpu.PC.get() + 1);
				
				break;	
			case 0x42:
				cpu.BC.setHighByte((byte) cpu.DE.getHighByte());
				
				Utils.PrintInstruction("LD B, D",
										instruction, 
										cpu.PC.get(), 
										null, 0
				);
	
				cpu.PC.set(cpu.PC.get() + 1);
				
				break;	
			case 0x43:
				cpu.BC.setHighByte((byte) cpu.DE.getLowByte());
				
				Utils.PrintInstruction("LD B, E",
										instruction, 
										cpu.PC.get(), 
										null, 0
				);
	
				cpu.PC.set(cpu.PC.get() + 1);
				
				break;	
			case 0x44:
				cpu.BC.setHighByte((byte) cpu.HL.getHighByte());
				
				Utils.PrintInstruction("LD B, H",
										instruction, 
										cpu.PC.get(), 
										null, 0
				);
	
				cpu.PC.set(cpu.PC.get() + 1);
				
				break;	
			case 0x45:
				cpu.BC.setHighByte((byte) cpu.HL.getLowByte());
				
				Utils.PrintInstruction("LD B, L",
										instruction, 
										cpu.PC.get(), 
										null, 0
				);
	
				cpu.PC.set(cpu.PC.get() + 1);
				
				break;	
			case 0x46:
				parameters[0] = cpu.memory.read(cpu.HL.get());
				
				cpu.BC.setHighByte(parameters[0]);
				
				Utils.PrintInstruction("LD B, (HL)",
										instruction, 
										cpu.PC.get(), 
										null, 0
				);
	
				cpu.PC.set(cpu.PC.get() + 1);
				
				break;				
			case 0x47:
				cpu.BC.setHighByte((byte) cpu.AF.getHighByte());
				
				Utils.PrintInstruction("LD B, A",
										instruction, 
										cpu.PC.get(), 
										null, 0
				);
	
				cpu.PC.set(cpu.PC.get() + 1);
				
				break;				
			case 0x48:
				cpu.BC.setLowByte((byte) cpu.BC.getHighByte());
				
				Utils.PrintInstruction("LD C, B",
										instruction, 
										cpu.PC.get(), 
										null, 0
				);
	
				cpu.PC.set(cpu.PC.get() + 1);
				
				break;	
			case 0x49:
				cpu.BC.setLowByte((byte) cpu.BC.getLowByte());
				
				Utils.PrintInstruction("LD C, C",
										instruction, 
										cpu.PC.get(), 
										null, 0
				);
	
				cpu.PC.set(cpu.PC.get() + 1);
				
				break;	
			case 0x4A:
				cpu.BC.setLowByte((byte) cpu.DE.getHighByte());
				
				Utils.PrintInstruction("LD C, D",
										instruction, 
										cpu.PC.get(), 
										null, 0
				);
	
				cpu.PC.set(cpu.PC.get() + 1);
				
				break;	
			case 0x4B:
				cpu.BC.setLowByte((byte) cpu.DE.getLowByte());
				
				Utils.PrintInstruction("LD C, E",
										instruction, 
										cpu.PC.get(), 
										null, 0
				);
	
				cpu.PC.set(cpu.PC.get() + 1);
				
				break;	
			case 0x4C:
				cpu.BC.setLowByte((byte) cpu.HL.getHighByte());
				
				Utils.PrintInstruction("LD C, H",
										instruction, 
										cpu.PC.get(), 
										null, 0
				);
	
				cpu.PC.set(cpu.PC.get() + 1);
				
				break;	
			case 0x4D:
				cpu.BC.setLowByte((byte) cpu.HL.getLowByte());
				
				Utils.PrintInstruction("LD C, L",
										instruction, 
										cpu.PC.get(), 
										null, 0
				);
	
				cpu.PC.set(cpu.PC.get() + 1);
				
				break;	
			case 0x4E:
				parameters[0] = cpu.memory.read(cpu.HL.get());
				
				cpu.BC.setLowByte(parameters[0]);
				
				Utils.PrintInstruction("LD C, (HL)",
										instruction, 
										cpu.PC.get(), 
										null, 0
				);
	
				cpu.PC.set(cpu.PC.get() + 1);
				
				break;				
			case 0x4F:
				cpu.BC.setLowByte((byte) cpu.AF.getHighByte());
				
				Utils.PrintInstruction("LD C, A",
										instruction, 
										cpu.PC.get(), 
										null, 0
				);
	
				cpu.PC.set(cpu.PC.get() + 1);
				
				break;				
			case 0x50:
				cpu.DE.setHighByte((byte) cpu.BC.getHighByte());
				
				Utils.PrintInstruction("LD D, B",
										instruction, 
										cpu.PC.get(), 
										null, 0
				);
	
				cpu.PC.set(cpu.PC.get() + 1);
				
				break;	
			case 0x51:
				cpu.DE.setHighByte((byte) cpu.BC.getLowByte());
				
				Utils.PrintInstruction("LD D, C",
										instruction, 
										cpu.PC.get(), 
										null, 0
				);
	
				cpu.PC.set(cpu.PC.get() + 1);
				
				break;	
			case 0x52:
				cpu.DE.setHighByte((byte) cpu.DE.getHighByte());
				
				Utils.PrintInstruction("LD D, D",
										instruction, 
										cpu.PC.get(), 
										null, 0
				);
	
				cpu.PC.set(cpu.PC.get() + 1);
				
				break;	
			case 0x53:
				cpu.DE.setHighByte((byte) cpu.DE.getLowByte());
				
				Utils.PrintInstruction("LD D, E",
										instruction, 
										cpu.PC.get(), 
										null, 0
				);
	
				cpu.PC.set(cpu.PC.get() + 1);
				
				break;	
			case 0x54:
				cpu.DE.setHighByte((byte) cpu.HL.getHighByte());
				
				Utils.PrintInstruction("LD D, H",
										instruction, 
										cpu.PC.get(), 
										null, 0
				);
	
				cpu.PC.set(cpu.PC.get() + 1);
				
				break;	
			case 0x55:
				cpu.DE.setHighByte((byte) cpu.HL.getLowByte());
				
				Utils.PrintInstruction("LD D, L",
										instruction, 
										cpu.PC.get(), 
										null, 0
				);
	
				cpu.PC.set(cpu.PC.get() + 1);
				
				break;	
			case 0x56:
				parameters[0] = cpu.memory.read(cpu.HL.get());
				
				cpu.DE.setHighByte(parameters[0]);
				
				Utils.PrintInstruction("LD D, (HL)",
										instruction, 
										cpu.PC.get(), 
										null, 0
				);
	
				cpu.PC.set(cpu.PC.get() + 1);
				
				break;				
			case 0x57:
				cpu.DE.setHighByte((byte) cpu.AF.getHighByte());
				
				Utils.PrintInstruction("LD D, A",
										instruction, 
										cpu.PC.get(), 
										null, 0
				);
	
				cpu.PC.set(cpu.PC.get() + 1);
				
				break;				
			case 0x58:
				cpu.DE.setLowByte((byte) cpu.BC.getHighByte());
				
				Utils.PrintInstruction("LD E, B",
										instruction, 
										cpu.PC.get(), 
										null, 0
				);
	
				cpu.PC.set(cpu.PC.get() + 1);
				
				break;	
			case 0x59:
				cpu.DE.setLowByte((byte) cpu.BC.getLowByte());
				
				Utils.PrintInstruction("LD E, C",
										instruction, 
										cpu.PC.get(), 
										null, 0
				);
	
				cpu.PC.set(cpu.PC.get() + 1);
				
				break;	
			case 0x5A:
				cpu.DE.setLowByte((byte) cpu.DE.getHighByte());
				
				Utils.PrintInstruction("LD E, D",
										instruction, 
										cpu.PC.get(), 
										null, 0
				);
	
				cpu.PC.set(cpu.PC.get() + 1);
				
				break;	
			case 0x5B:
				cpu.DE.setLowByte((byte) cpu.DE.getLowByte());
				
				Utils.PrintInstruction("LD E, E",
										instruction, 
										cpu.PC.get(), 
										null, 0
				);
	
				cpu.PC.set(cpu.PC.get() + 1);
				
				break;	
			case 0x5C:
				cpu.DE.setLowByte((byte) cpu.HL.getHighByte());
				
				Utils.PrintInstruction("LD E, H",
										instruction, 
										cpu.PC.get(), 
										null, 0
				);
	
				cpu.PC.set(cpu.PC.get() + 1);
				
				break;	
			case 0x5D:
				cpu.DE.setLowByte((byte) cpu.HL.getLowByte());
				
				Utils.PrintInstruction("LD E, L",
										instruction, 
										cpu.PC.get(), 
										null, 0
				);
	
				cpu.PC.set(cpu.PC.get() + 1);
				
				break;	
			case 0x5E:
				parameters[0] = cpu.memory.read(cpu.HL.get());
				
				cpu.DE.setLowByte(parameters[0]);
				
				Utils.PrintInstruction("LD E, (HL)",
										instruction, 
										cpu.PC.get(), 
										null, 0
				);
	
				cpu.PC.set(cpu.PC.get() + 1);
				
				break;				
			case 0x5F:
				cpu.DE.setLowByte((byte) cpu.AF.getHighByte());
				
				Utils.PrintInstruction("LD E, A",
										instruction,
										cpu.PC.get(),
										null, 0
				);
	
				cpu.PC.set(cpu.PC.get() + 1);
				
				break;	
			case 0x60:
				cpu.HL.setHighByte((byte) cpu.BC.getHighByte());
				
				Utils.PrintInstruction("LD H, B",
										instruction, 
										cpu.PC.get(), 
										null, 0
				);
	
				cpu.PC.set(cpu.PC.get() + 1);
				
				break;	
			case 0x61:
				cpu.HL.setHighByte((byte) cpu.BC.getLowByte());
				
				Utils.PrintInstruction("LD H, C",
										instruction, 
										cpu.PC.get(), 
										null, 0
				);
	
				cpu.PC.set(cpu.PC.get() + 1);
				
				break;	
			case 0x62:
				cpu.HL.setHighByte((byte) cpu.DE.getHighByte());
				
				Utils.PrintInstruction("LD H, D",
										instruction, 
										cpu.PC.get(), 
										null, 0
				);
	
				cpu.PC.set(cpu.PC.get() + 1);
				
				break;	
			case 0x63:
				cpu.HL.setHighByte((byte) cpu.DE.getLowByte());
				
				Utils.PrintInstruction("LD H, E",
										instruction, 
										cpu.PC.get(), 
										null, 0
				);
	
				cpu.PC.set(cpu.PC.get() + 1);
				
				break;	
			case 0x64:
				cpu.HL.setHighByte((byte) cpu.HL.getHighByte());
				
				Utils.PrintInstruction("LD H, H",
										instruction, 
										cpu.PC.get(), 
										null, 0
				);
	
				cpu.PC.set(cpu.PC.get() + 1);
				
				break;	
			case 0x65:
				cpu.HL.setHighByte((byte) cpu.HL.getLowByte());
				
				Utils.PrintInstruction("LD H, L",
										instruction, 
										cpu.PC.get(), 
										null, 0
				);
	
				cpu.PC.set(cpu.PC.get() + 1);
				
				break;	
			case 0x66:
				parameters[0] = cpu.memory.read(cpu.HL.get());
				
				cpu.HL.setHighByte(parameters[0]);
				
				Utils.PrintInstruction("LD H, (HL)",
										instruction, 
										cpu.PC.get(), 
										null, 0
				);
	
				cpu.PC.set(cpu.PC.get() + 1);
				
				break;				
			case 0x67:
				cpu.HL.setHighByte((byte) cpu.AF.getHighByte());
				
				Utils.PrintInstruction("LD H, A",
										instruction, 
										cpu.PC.get(), 
										null, 0
				);
	
				cpu.PC.set(cpu.PC.get() + 1);
				
				break;				
			case 0x68:
				cpu.HL.setLowByte((byte) cpu.BC.getHighByte());
				
				Utils.PrintInstruction("LD L, B",
										instruction, 
										cpu.PC.get(), 
										null, 0
				);
	
				cpu.PC.set(cpu.PC.get() + 1);
				
				break;	
			case 0x69:
				cpu.HL.setLowByte((byte) cpu.BC.getLowByte());
				
				Utils.PrintInstruction("LD L, C",
										instruction, 
										cpu.PC.get(), 
										null, 0
				);
	
				cpu.PC.set(cpu.PC.get() + 1);
				
				break;	
			case 0x6A:
				cpu.HL.setLowByte((byte) cpu.DE.getHighByte());
				
				Utils.PrintInstruction("LD L, D",
										instruction, 
										cpu.PC.get(), 
										null, 0
				);
	
				cpu.PC.set(cpu.PC.get() + 1);
				
				break;	
			case 0x6B:
				cpu.HL.setLowByte((byte) cpu.DE.getLowByte());
				
				Utils.PrintInstruction("LD L, E",
										instruction, 
										cpu.PC.get(), 
										null, 0
				);
	
				cpu.PC.set(cpu.PC.get() + 1);
				
				break;	
			case 0x6C:
				cpu.HL.setLowByte((byte) cpu.HL.getHighByte());
				
				Utils.PrintInstruction("LD L, H",
										instruction, 
										cpu.PC.get(), 
										null, 0
				);
	
				cpu.PC.set(cpu.PC.get() + 1);
				
				break;	
			case 0x6D:
				cpu.HL.setLowByte((byte) cpu.HL.getLowByte());
				
				Utils.PrintInstruction("LD L, L",
										instruction, 
										cpu.PC.get(), 
										null, 0
				);
	
				cpu.PC.set(cpu.PC.get() + 1);
				
				break;	
			case 0x6E:
				parameters[0] = cpu.memory.read(cpu.HL.get());
				
				cpu.HL.setLowByte(parameters[0]);
				
				Utils.PrintInstruction("LD L, (HL)",
										instruction, 
										cpu.PC.get(),
										null, 0
				);
	
				cpu.PC.set(cpu.PC.get() + 1);
				
				break;				
			case 0x6F:
				cpu.HL.setLowByte((byte) cpu.AF.getHighByte());
				
				Utils.PrintInstruction("LD L, A",
										instruction, 
										cpu.PC.get(), 
										null, 0
				);
	
				cpu.PC.set(cpu.PC.get() + 1);
				
				break;				
			case 0x70:
				cpu.memory.write((byte) cpu.BC.getHighByte(), cpu.HL.get());
				
				Utils.PrintInstruction("LD (HL), B",
										instruction, 
										cpu.PC.get(), 
										null, 0
				);
	
				cpu.PC.set(cpu.PC.get() + 1);
				
				break;				
			case 0x71:
				cpu.memory.write((byte) cpu.BC.getLowByte(), cpu.HL.get());
				
				Utils.PrintInstruction("LD (HL), C",
										instruction, 
										cpu.PC.get(), 
										null, 0
				);
	
				cpu.PC.set(cpu.PC.get() + 1);
				
				break;				
			case 0x72:
				cpu.memory.write((byte) cpu.DE.getHighByte(), cpu.HL.get());
				
				Utils.PrintInstruction("LD (HL), D",
										instruction,
										cpu.PC.get(), 
										null, 0
				);
	
				cpu.PC.set(cpu.PC.get() + 1);
				
				break;				
			case 0x73:
				cpu.memory.write((byte) cpu.DE.getLowByte(), cpu.HL.get());
				
				Utils.PrintInstruction("LD (HL), E",
										instruction, 
										cpu.PC.get(), 
										null, 0
				);
	
				cpu.PC.set(cpu.PC.get() + 1);
				
				break;	
			case 0x74:
				cpu.memory.write((byte) cpu.HL.getHighByte(), cpu.HL.get());
				
				Utils.PrintInstruction("LD (HL), H",
										instruction, 
										cpu.PC.get(), 
										null, 0
				);
	
				cpu.PC.set(cpu.PC.get() + 1);
				
				break;	
			case 0x75:
				cpu.memory.write((byte) cpu.HL.getLowByte(), cpu.HL.get());
				
				Utils.PrintInstruction("LD (HL), L",
										instruction, 
										cpu.PC.get(), 
										null, 0
				);
	
				cpu.PC.set(cpu.PC.get() + 1);
				
				break;	
			case 0x77:
				cpu.memory.write((byte) cpu.AF.getHighByte(), cpu.HL.get());
				
				Utils.PrintInstruction("LD (HL), A",
										instruction, 
										cpu.PC.get(), 
										null, 0
				);
	
				cpu.PC.set(cpu.PC.get() + 1);
				
				break;				
			case 0x78:
				cpu.AF.setHighByte((byte) cpu.BC.getHighByte());
				
				Utils.PrintInstruction("LD A, B",
										instruction, 
										cpu.PC.get(), 
										null, 0
				);
	
				cpu.PC.set(cpu.PC.get() + 1);
				
				break;	
			case 0x79:
				cpu.AF.setHighByte((byte) cpu.BC.getLowByte());
				
				Utils.PrintInstruction("LD A, C",
										instruction, 
										cpu.PC.get(), 
										null, 0
				);
	
				cpu.PC.set(cpu.PC.get() + 1);
				
				break;	
			case 0x7A:
				cpu.AF.setHighByte((byte) cpu.DE.getHighByte());
				
				Utils.PrintInstruction("LD A, D",
										instruction,
										cpu.PC.get(), 
										null, 0
				);
	
				cpu.PC.set(cpu.PC.get() + 1);
				
				break;	
			case 0x7B:
				cpu.AF.setHighByte((byte) cpu.DE.getLowByte());
				
				Utils.PrintInstruction("LD A, E",
										instruction, 
										cpu.PC.get(), 
										null, 0
				);
	
				cpu.PC.set(cpu.PC.get() + 1);
				
				break;	
			case 0x7C:
				cpu.AF.setHighByte((byte) cpu.HL.getHighByte());
				
				Utils.PrintInstruction("LD A, H",
										instruction, 
										cpu.PC.get(), 
										null, 0
				);
	
				cpu.PC.set(cpu.PC.get() + 1);
				
				break;	
			case 0x7D:
				cpu.AF.setHighByte((byte) cpu.HL.getLowByte());
				
				Utils.PrintInstruction("LD A, L",
										instruction, 
										cpu.PC.get(), 
										null, 0
				);
	
				cpu.PC.set(cpu.PC.get() + 1);
				
				break;	
			case 0x7E:
				parameters[0] = cpu.memory.read(cpu.HL.get());
				
				cpu.AF.setHighByte(parameters[0]);
				
				Utils.PrintInstruction("LD A, (HL)",
										instruction, 
										cpu.PC.get(), 
										null, 0
				);
	
				cpu.PC.set(cpu.PC.get() + 1);
				
				break;				
			case 0x7F:
				cpu.AF.setHighByte((byte) cpu.AF.getHighByte());
				
				Utils.PrintInstruction("LD A, A",
										instruction, 
										cpu.PC.get(), 
										null, 0
				);
	
				cpu.PC.set(cpu.PC.get() + 1);
				
				break;				
			case (byte) 0xE0:
				parameters[0] = cpu.memory.read(cpu.PC.get() + 1);
				
				cpu.memory.write((byte) cpu.AF.getHighByte(),
						parameters[0] & 0xFF | 0xFF00);
				
				Utils.PrintInstruction("LDH (a8), A",
										instruction, 
										cpu.PC.get(), 
										parameters, 1
				);
	
				cpu.PC.set(cpu.PC.get() + 2);
				
				break;	
			case (byte) 0xE2:
				cpu.memory.write((byte) cpu.AF.getHighByte(),
						cpu.BC.getLowByte() & 0xFF | 0xFF00);
				
				Utils.PrintInstruction("LDH (C), A",
										instruction, 
										cpu.PC.get(), 
										null, 0
				);
	
				cpu.PC.set(cpu.PC.get() + 1);
				
				break;	
			case (byte) 0xEA:
				parameters[0] = cpu.memory.read(cpu.PC.get() + 1);
				parameters[1] = cpu.memory.read(cpu.PC.get() + 2);
				
				cpu.memory.write((byte) cpu.AF.getHighByte(),
						parameters[1] << 8 | (int) parameters[0]);
				
				Utils.PrintInstruction("LDH (a16), A",
										instruction, 
										cpu.PC.get(), 
										parameters, 2
				);
	
				cpu.PC.set(cpu.PC.get() + 3);
				
				break;	
			case (byte) 0xF0:
				parameters[0] = cpu.memory.read(cpu.PC.get() + 1);
				parameters[1] = cpu.memory.read(
						parameters[0] & 0xFF | 0xFF00
				);
				
				cpu.AF.setHighByte(parameters[1]);
				
				Utils.PrintInstruction("LDH A, (a8)",
										instruction, 
										cpu.PC.get(), 
										parameters, 2
				);
	
				cpu.PC.set(cpu.PC.get() + 2);
				
				break;				
			case (byte) 0xF2:
				parameters[0] = cpu.memory.read(
						cpu.BC.getLowByte() & 0xFF | 0xFF00
				);
				
				cpu.AF.setHighByte(parameters[0]);
				
				Utils.PrintInstruction("LDH A, (C)",
										instruction, 
										cpu.PC.get(), 
										parameters, 1
				);
	
				cpu.PC.set(cpu.PC.get() + 2);
				
				break;				
			case (byte) 0xF8:
				parameters[0] = cpu.memory.read(cpu.PC.get() + 1);
				
				cpu.HL.set(cpu.SP.get() + parameters[0]);
				
				Utils.PrintInstruction("LD HL, SP+r8",
										instruction, 
										cpu.PC.get(), 
										parameters, 1
				);
	
				cpu.PC.set(cpu.PC.get() + 2);
			
				break;			
			case (byte) 0xF9:
				cpu.SP.set(cpu.HL.get());
			
				Utils.PrintInstruction("LD SP, HL",
										instruction, 
										cpu.PC.get(), 
										null, 0
				);
		
				cpu.PC.set(cpu.PC.get() + 1);
			
				break;	
			case (byte) 0xFA:
				parameters[0] = cpu.memory.read(cpu.PC.get() + 1);
				parameters[1] = cpu.memory.read(cpu.PC.get() + 2);
				address = parameters[1] << 8 | (int) parameters[0];
				
				cpu.AF.setHighByte(cpu.memory.read(address));
				
				Utils.PrintInstruction("LDH A, (a16)",
										instruction, 
										cpu.PC.get(), 
										parameters, 2
				);
	
				cpu.PC.set(cpu.PC.get() + 3);
				
				break;				
			default:
				System.err.println("Unknown variant of the \"LD\" opcode: " + 
									Utils.hex(instruction & 0xFF)
				);
				
				cpu.error = true;
				return;		
		}	
	}
}
