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

import java.util.Map;

/**
 * Transfers memory from ram, registers,
 * and 16-bit registers.
 * 
 * @author Daniel Simpkins
 *
 */
public class Load {

    public static void buildOpcodes(Map<Byte, Runnable> funcTable, CPU cpu) {
        funcTable.put((byte)0x01, () -> {
            int d16 = (cpu.memory.read(cpu.PC.get() + 1) & 0xFF)
                    | ((cpu.memory.read(cpu.PC.get() + 2) & 0xFF) << 8);
            Load.load16Bit(cpu, cpu.BC, "BC", d16, (byte)0x01);
            cpu.PC.set(cpu.PC.get() + 2);
        });
        funcTable.put((byte)0x11, () -> {
            int d16 = (cpu.memory.read(cpu.PC.get() + 1) & 0xFF)
                    | ((cpu.memory.read(cpu.PC.get() + 2) & 0xFF) << 8);
            Load.load16Bit(cpu, cpu.DE, "DE", d16, (byte)0x11);
            cpu.PC.set(cpu.PC.get() + 2);
        });
        funcTable.put((byte)0x21, () -> {
            int d16 = (cpu.memory.read(cpu.PC.get() + 1) & 0xFF)
                    | ((cpu.memory.read(cpu.PC.get() + 2) & 0xFF) << 8);
            Load.load16Bit(cpu, cpu.HL, "HL", d16, (byte)0x21);
            cpu.PC.set(cpu.PC.get() + 2);
        });
        funcTable.put((byte)0x31, () -> {
            int d16 = (cpu.memory.read(cpu.PC.get() + 1) & 0xFF)
                    | ((cpu.memory.read(cpu.PC.get() + 2) & 0xFF) << 8);
            Load.load16Bit(cpu, cpu.SP, "SP", d16, (byte)0x31);
            cpu.PC.set(cpu.PC.get() + 2);
        });

        funcTable.put((byte)0x02, () -> {
            Register reg = new Register(0, "MEM");
            Load.load8Bit(cpu, reg, false, "(BC)", (byte)cpu.AF.getHighByte(), (byte)0x02);
            cpu.memory.write((byte)reg.getLowByte(), cpu.BC.get());
        });
        funcTable.put((byte)0x12, () -> {
            Register reg = new Register(0, "MEM");
            Load.load8Bit(cpu, reg, false, "(DE)", (byte)cpu.AF.getHighByte(), (byte)0x12);
            cpu.memory.write((byte)reg.getLowByte(), cpu.DE.get());
        });
        funcTable.put((byte)0x22, () -> {
            Register reg = new Register(0, "MEM");
            Load.load8Bit(cpu, reg, false, "(HL+)", (byte)cpu.AF.getHighByte(), (byte)0x22);
            cpu.memory.write((byte)reg.getLowByte(), cpu.HL.get());
            cpu.HL.set(cpu.HL.get() + 1);
        });
        funcTable.put((byte)0x32, () -> {
            Register reg = new Register(0, "MEM");
            Load.load8Bit(cpu, reg, false, "(HL-)", (byte)cpu.AF.getHighByte(), (byte)0x32);
            cpu.memory.write((byte)reg.getLowByte(), cpu.HL.get());
            cpu.HL.set(cpu.HL.get() - 1);
        });

        funcTable.put((byte)0x08, () -> {
            int address = (cpu.memory.read(cpu.PC.get() + 1) & 0xFF)
                    | ((cpu.memory.read(cpu.PC.get() + 2) & 0xFF) << 8);
            Register reg = new Register(0, "MEM");
            Load.load16Bit(cpu, reg, "(a16)", cpu.SP.get() & 0xFFFF, (byte)0x08);
            cpu.memory.write((byte)reg.getLowByte(), address);
            cpu.memory.write((byte)reg.getHighByte(), address);
            cpu.PC.set(cpu.PC.get() + 2);
        });

        funcTable.put((byte)0x06, () -> {
            byte d8 = cpu.memory.read(cpu.PC.get() + 1);
            Load.load8Bit(cpu, cpu.BC, true, "B", d8, (byte)0x06);
            cpu.PC.set(cpu.PC.get() + 1);
        });
        funcTable.put((byte)0x16, () -> {
            byte d8 = cpu.memory.read(cpu.PC.get() + 1);
            Load.load8Bit(cpu, cpu.DE, true, "D", d8, (byte)0x16);
            cpu.PC.set(cpu.PC.get() + 1);
        });
        funcTable.put((byte)0x26, () -> {
            byte d8 = cpu.memory.read(cpu.PC.get() + 1);
            Load.load8Bit(cpu, cpu.HL, true, "H", d8, (byte)0x26);
            cpu.PC.set(cpu.PC.get() + 1);
        });
        funcTable.put((byte)0x36, () -> {
            byte d8 = cpu.memory.read(cpu.PC.get() + 1);
            Register reg = new Register(d8, "mem");
            Load.load8Bit(cpu, reg, false, "(HL)", d8, (byte)0x36);
            cpu.memory.write(d8, cpu.HL.get());
            cpu.PC.set(cpu.PC.get() + 1);
        });

        funcTable.put((byte)0x0E, () -> {
            byte d8 = cpu.memory.read(cpu.PC.get() + 1);
            Load.load8Bit(cpu, cpu.BC, false, "C", d8, (byte)0x0E);
            cpu.PC.set(cpu.PC.get() + 1);
        });
        funcTable.put((byte)0x1E, () -> {
            byte d8 = cpu.memory.read(cpu.PC.get() + 1);
            Load.load8Bit(cpu, cpu.DE, false, "E", d8, (byte)0x1E);
            cpu.PC.set(cpu.PC.get() + 1);
        });
        funcTable.put((byte)0x2E, () -> {
            byte d8 = cpu.memory.read(cpu.PC.get() + 1);
            Load.load8Bit(cpu, cpu.HL, false, "L", d8, (byte)0x2E);
            cpu.PC.set(cpu.PC.get() + 1);
        });
        funcTable.put((byte)0x3E, () -> {
            byte d8 = cpu.memory.read(cpu.PC.get() + 1);
            Load.load8Bit(cpu, cpu.AF, true, "A", d8, (byte)0x3E);
            cpu.PC.set(cpu.PC.get() + 1);
        });

        funcTable.put((byte)0x0A, () -> {
           Load.load8Bit(cpu, cpu.AF, true, "A", cpu.memory.read(cpu.BC.get()), (byte)0x0A);
        });
        funcTable.put((byte)0x1A, () -> {
            Load.load8Bit(cpu, cpu.AF, true, "A", cpu.memory.read(cpu.DE.get()), (byte)0x1A);
        });
        funcTable.put((byte)0x2A, () -> {
            Load.load8Bit(cpu, cpu.AF, true, "A", cpu.memory.read(cpu.HL.get()), (byte)0x2A);
            cpu.HL.set(cpu.HL.get() + 1);
        });
        funcTable.put((byte)0x3A, () -> {
            Load.load8Bit(cpu, cpu.AF, true, "A", cpu.memory.read(cpu.HL.get()), (byte)0x3A);
            cpu.HL.set(cpu.HL.get() - 1);
        });

        funcTable.put((byte)0x40, () -> {
            Load.load8Bit(cpu, cpu.BC, true, "B", (byte)cpu.BC.getHighByte(), (byte)0x40);
        });
        funcTable.put((byte)0x41, () -> {
            Load.load8Bit(cpu, cpu.BC, true, "B", (byte)cpu.BC.getLowByte(), (byte)0x41);
        });
        funcTable.put((byte)0x42, () -> {
            Load.load8Bit(cpu, cpu.BC, true, "B", (byte)cpu.DE.getHighByte(), (byte)0x42);
        });
        funcTable.put((byte)0x43, () -> {
            Load.load8Bit(cpu, cpu.BC, true, "B", (byte)cpu.DE.getLowByte(), (byte)0x43);
        });
        funcTable.put((byte)0x44, () -> {
            Load.load8Bit(cpu, cpu.BC, true, "B", (byte)cpu.HL.getHighByte(), (byte)0x44);
        });
        funcTable.put((byte)0x45, () -> {
            Load.load8Bit(cpu, cpu.BC, true, "B", (byte)cpu.HL.getLowByte(), (byte)0x45);
        });
        funcTable.put((byte)0x46, () -> {
            Load.load8Bit(cpu, cpu.BC, true, "B", cpu.memory.read(cpu.HL.get()), (byte)0x46);
        });
        funcTable.put((byte)0x47, () -> {
            Load.load8Bit(cpu, cpu.BC, true, "B", (byte)cpu.AF.getHighByte(), (byte)0x47);
        });

        funcTable.put((byte)0x48, () -> {
            Load.load8Bit(cpu, cpu.BC, false, "C", (byte)cpu.BC.getHighByte(), (byte)0x48);
        });
        funcTable.put((byte)0x49, () -> {
            Load.load8Bit(cpu, cpu.BC, false, "C", (byte)cpu.BC.getLowByte(), (byte)0x49);
        });
        funcTable.put((byte)0x4A, () -> {
            Load.load8Bit(cpu, cpu.BC, false, "C", (byte)cpu.DE.getHighByte(), (byte)0x4A);
        });
        funcTable.put((byte)0x4B, () -> {
            Load.load8Bit(cpu, cpu.BC, false, "C", (byte)cpu.DE.getLowByte(), (byte)0x4B);
        });
        funcTable.put((byte)0x4C, () -> {
            Load.load8Bit(cpu, cpu.BC, false, "C", (byte)cpu.HL.getHighByte(), (byte)0x4C);
        });
        funcTable.put((byte)0x4D, () -> {
            Load.load8Bit(cpu, cpu.BC, false, "C", (byte)cpu.HL.getLowByte(), (byte)0x4D);
        });
        funcTable.put((byte)0x4E, () -> {
            Load.load8Bit(cpu, cpu.BC, false, "C", cpu.memory.read(cpu.HL.get()), (byte)0x4E);
        });
        funcTable.put((byte)0x4F, () -> {
            Load.load8Bit(cpu, cpu.BC, false, "C", (byte)cpu.AF.getHighByte(), (byte)0x4F);
        });

        funcTable.put((byte)0x50, () -> {
            Load.load8Bit(cpu, cpu.DE, true, "D", (byte)cpu.BC.getHighByte(), (byte)0x50);
        });
        funcTable.put((byte)0x51, () -> {
            Load.load8Bit(cpu, cpu.DE, true, "D", (byte)cpu.BC.getLowByte(), (byte)0x51);
        });
        funcTable.put((byte)0x52, () -> {
            Load.load8Bit(cpu, cpu.DE, true, "D", (byte)cpu.DE.getHighByte(), (byte)0x52);
        });
        funcTable.put((byte)0x53, () -> {
            Load.load8Bit(cpu, cpu.DE, true, "D", (byte)cpu.DE.getLowByte(), (byte)0x53);
        });
        funcTable.put((byte)0x54, () -> {
            Load.load8Bit(cpu, cpu.DE, true, "D", (byte)cpu.HL.getHighByte(), (byte)0x54);
        });
        funcTable.put((byte)0x55, () -> {
            Load.load8Bit(cpu, cpu.DE, true, "D", (byte)cpu.HL.getLowByte(), (byte)0x55);
        });
        funcTable.put((byte)0x56, () -> {
            Load.load8Bit(cpu, cpu.DE, true, "D", cpu.memory.read(cpu.HL.get()), (byte)0x56);
        });
        funcTable.put((byte)0x57, () -> {
            Load.load8Bit(cpu, cpu.DE, true, "D", (byte)cpu.AF.getHighByte(), (byte)0x57);
        });

        funcTable.put((byte)0x58, () -> {
            Load.load8Bit(cpu, cpu.DE, false, "E", (byte)cpu.BC.getHighByte(), (byte)0x58);
        });
        funcTable.put((byte)0x59, () -> {
            Load.load8Bit(cpu, cpu.DE, false, "E", (byte)cpu.BC.getLowByte(), (byte)0x59);
        });
        funcTable.put((byte)0x5A, () -> {
            Load.load8Bit(cpu, cpu.DE, false, "E", (byte)cpu.DE.getHighByte(), (byte)0x5A);
        });
        funcTable.put((byte)0x5B, () -> {
            Load.load8Bit(cpu, cpu.DE, false, "E", (byte)cpu.DE.getLowByte(), (byte)0x5B);
        });
        funcTable.put((byte)0x5C, () -> {
            Load.load8Bit(cpu, cpu.DE, false, "E", (byte)cpu.HL.getHighByte(), (byte)0x5C);
        });
        funcTable.put((byte)0x5D, () -> {
            Load.load8Bit(cpu, cpu.DE, false, "E", (byte)cpu.HL.getLowByte(), (byte)0x5D);
        });
        funcTable.put((byte)0x5E, () -> {
            Load.load8Bit(cpu, cpu.DE, false, "E", cpu.memory.read(cpu.HL.get()), (byte)0x5E);
        });
        funcTable.put((byte)0x5F, () -> {
            Load.load8Bit(cpu, cpu.DE, false, "E", (byte)cpu.AF.getHighByte(), (byte)0x5F);
        });

        funcTable.put((byte)0x60, () -> {
            Load.load8Bit(cpu, cpu.HL, true, "H", (byte)cpu.BC.getHighByte(), (byte)0x60);
        });
        funcTable.put((byte)0x61, () -> {
            Load.load8Bit(cpu, cpu.HL, true, "H", (byte)cpu.BC.getLowByte(), (byte)0x61);
        });
        funcTable.put((byte)0x62, () -> {
            Load.load8Bit(cpu, cpu.HL, true, "H", (byte)cpu.DE.getHighByte(), (byte)0x62);
        });
        funcTable.put((byte)0x63, () -> {
            Load.load8Bit(cpu, cpu.HL, true, "H", (byte)cpu.DE.getLowByte(), (byte)0x63);
        });
        funcTable.put((byte)0x64, () -> {
            Load.load8Bit(cpu, cpu.HL, true, "H", (byte)cpu.HL.getHighByte(), (byte)0x64);
        });
        funcTable.put((byte)0x65, () -> {
            Load.load8Bit(cpu, cpu.HL, true, "H", (byte)cpu.HL.getLowByte(), (byte)0x65);
        });
        funcTable.put((byte)0x66, () -> {
            Load.load8Bit(cpu, cpu.HL, true, "H", cpu.memory.read(cpu.HL.get()), (byte)0x66);
        });
        funcTable.put((byte)0x67, () -> {
            Load.load8Bit(cpu, cpu.HL, true, "H", (byte)cpu.AF.getHighByte(), (byte)0x67);
        });

        funcTable.put((byte)0x68, () -> {
            Load.load8Bit(cpu, cpu.HL, false, "L", (byte)cpu.BC.getHighByte(), (byte)0x68);
        });
        funcTable.put((byte)0x69, () -> {
            Load.load8Bit(cpu, cpu.HL, false, "L", (byte)cpu.BC.getLowByte(), (byte)0x69);
        });
        funcTable.put((byte)0x6A, () -> {
            Load.load8Bit(cpu, cpu.HL, false, "L", (byte)cpu.DE.getHighByte(), (byte)0x6A);
        });
        funcTable.put((byte)0x6B, () -> {
            Load.load8Bit(cpu, cpu.HL, false, "L", (byte)cpu.DE.getLowByte(), (byte)0x6B);
        });
        funcTable.put((byte)0x6C, () -> {
            Load.load8Bit(cpu, cpu.HL, false, "L", (byte)cpu.HL.getHighByte(), (byte)0x6C);
        });
        funcTable.put((byte)0x6D, () -> {
            Load.load8Bit(cpu, cpu.HL, false, "L", (byte)cpu.HL.getLowByte(), (byte)0x6D);
        });
        funcTable.put((byte)0x6E, () -> {
            Load.load8Bit(cpu, cpu.HL, false, "L", cpu.memory.read(cpu.HL.get()), (byte)0x6E);
        });
        funcTable.put((byte)0x6F, () -> {
            Load.load8Bit(cpu, cpu.HL, false, "L", (byte)cpu.AF.getHighByte(), (byte)0x6F);
        });

        funcTable.put((byte)0x70, () -> {
            Register reg = new Register(0, "mem");
            Load.load8Bit(cpu, reg, false, "(HL)", (byte)cpu.BC.getHighByte(), (byte)0x70);
            cpu.memory.write((byte)reg.getLowByte(), cpu.HL.get());
            cpu.PC.set(cpu.PC.get() + 1);
        });
        funcTable.put((byte)0x71, () -> {
            Register reg = new Register(0, "mem");
            Load.load8Bit(cpu, reg, false, "(HL)", (byte)cpu.BC.getLowByte(), (byte)0x71);
            cpu.memory.write((byte)reg.getLowByte(), cpu.HL.get());
            cpu.PC.set(cpu.PC.get() + 1);
        });
        funcTable.put((byte)0x72, () -> {
            Register reg = new Register(0, "mem");
            Load.load8Bit(cpu, reg, false, "(HL)", (byte)cpu.DE.getHighByte(), (byte)0x72);
            cpu.memory.write((byte)reg.getLowByte(), cpu.HL.get());
            cpu.PC.set(cpu.PC.get() + 1);
        });
        funcTable.put((byte)0x73, () -> {
            Register reg = new Register(0, "mem");
            Load.load8Bit(cpu, reg, false, "(HL)", (byte)cpu.DE.getLowByte(), (byte)0x73);
            cpu.memory.write((byte)reg.getLowByte(), cpu.HL.get());
            cpu.PC.set(cpu.PC.get() + 1);
        });
        funcTable.put((byte)0x74, () -> {
            Register reg = new Register(0, "mem");
            Load.load8Bit(cpu, reg, false, "(HL)", (byte)cpu.HL.getHighByte(), (byte)0x74);
            cpu.memory.write((byte)reg.getLowByte(), cpu.HL.get());
            cpu.PC.set(cpu.PC.get() + 1);
        });
        funcTable.put((byte)0x75, () -> {
            Register reg = new Register(0, "mem");
            Load.load8Bit(cpu, reg, false, "(HL)", (byte)cpu.HL.getLowByte(), (byte)0x75);
            cpu.memory.write((byte)reg.getLowByte(), cpu.HL.get());
            cpu.PC.set(cpu.PC.get() + 1);
        });
        funcTable.put((byte)0x76, () -> {
            cpu.error = true;
        });
        funcTable.put((byte)0x77, () -> {
            Register reg = new Register(0, "mem");
            Load.load8Bit(cpu, reg, false, "(HL)", (byte)cpu.AF.getHighByte(), (byte)0x77);
            cpu.memory.write((byte)reg.getLowByte(), cpu.HL.get());
            cpu.PC.set(cpu.PC.get() + 1);
        });

        funcTable.put((byte)0x78, () -> {
            Load.load8Bit(cpu, cpu.AF, true, "A", (byte)cpu.BC.getHighByte(), (byte)0x78);
        });
        funcTable.put((byte)0x79, () -> {
            Load.load8Bit(cpu, cpu.AF, true, "A", (byte)cpu.BC.getLowByte(), (byte)0x79);
        });
        funcTable.put((byte)0x7A, () -> {
            Load.load8Bit(cpu, cpu.AF, true, "A", (byte)cpu.DE.getHighByte(), (byte)0x7A);
        });
        funcTable.put((byte)0x7B, () -> {
            Load.load8Bit(cpu, cpu.AF, true, "A", (byte)cpu.DE.getLowByte(), (byte)0x7B);
        });
        funcTable.put((byte)0x7C, () -> {
            Load.load8Bit(cpu, cpu.AF, true, "A", (byte)cpu.HL.getHighByte(), (byte)0x7C);
        });
        funcTable.put((byte)0x7D, () -> {
            Load.load8Bit(cpu, cpu.AF, true, "A", (byte)cpu.HL.getLowByte(), (byte)0x7D);
        });
        funcTable.put((byte)0x7E, () -> {
            Load.load8Bit(cpu, cpu.AF, true, "A", cpu.memory.read(cpu.HL.get()), (byte)0x7E);
        });
        funcTable.put((byte)0x7F, () -> {
            Load.load8Bit(cpu, cpu.AF, true, "A", (byte)cpu.AF.getHighByte(), (byte)0x7F);
        });

        funcTable.put((byte)0xE0, () -> {
            int offset = cpu.memory.read(cpu.PC.get() + 1) & 0xFF;
            byte d8 = cpu.memory.read(0xFF00 + offset);
            Register reg = new Register(d8, "mem");
            Load.load8Bit(cpu, reg, false, "(a8)", (byte)cpu.AF.getHighByte(), (byte)0xE0);
            cpu.memory.write(d8, 0xFF00 + offset);
            cpu.PC.set(cpu.PC.get() + 1);
        });

        funcTable.put((byte)0xE2, () -> {
            int offset = (byte)cpu.BC.getLowByte();
            byte d8 = cpu.memory.read(0xFF00 + offset);
            Register reg = new Register(d8, "mem");
            Load.load8Bit(cpu, reg, false, "(a8)", (byte)cpu.AF.getHighByte(), (byte)0xE2);
            cpu.memory.write(d8, 0xFF00 + offset);
            cpu.PC.set(cpu.PC.get() + 1);
        });

        funcTable.put((byte)0xEA, () -> {
            int address = (cpu.memory.read(cpu.PC.get() + 1) & 0xFF)
                    | ((cpu.memory.read(cpu.PC.get() + 2) & 0xFF) << 8);
            Register reg = new Register(cpu.memory.read(address), "MEM");
            Load.load8Bit(cpu, reg, false,  "(a16)", (byte)cpu.AF.getHighByte(), (byte)0xEA);
            cpu.memory.write((byte)reg.getLowByte(), address);
            cpu.PC.set(cpu.PC.get() + 2);
        });

        funcTable.put((byte)0xF0, () -> {
            int offset = cpu.memory.read(cpu.PC.get() + 1) & 0xFF;
            Load.load8Bit(cpu, cpu.AF, true, "A", cpu.memory.read(0xFF00 + offset), (byte)0xF0);
            cpu.PC.set(cpu.PC.get() + 1);
        });
        funcTable.put((byte)0xF2, () -> {
            int offset = (byte)cpu.BC.getLowByte();
            Load.load8Bit(cpu, cpu.AF, true, "A", cpu.memory.read(0xFF00 + offset), (byte)0xF2);
            cpu.PC.set(cpu.PC.get() + 1);
        });

        funcTable.put((byte)0xFA, () -> {
            int address = (cpu.memory.read(cpu.PC.get() + 1) & 0xFF)
                    | ((cpu.memory.read(cpu.PC.get() + 2) & 0xFF) << 8);
            Load.load8Bit(cpu, cpu.AF, true,  "A", cpu.memory.read(address), (byte)0xFA);
            cpu.PC.set(cpu.PC.get() + 2);
        });

        funcTable.put((byte)0xF8, () -> {
            int initial = cpu.SP.get() & 0xFFFF;
            int result = initial + cpu.memory.read(cpu.PC.get() + 1);
            result &= 0xFFFF;
            cpu.HL.set(result);
            cpu.flags.setZero(false);
            cpu.flags.setSign(false);
            cpu.flags.setFlags(initial, result, false, Flags.HALFC | Flags.CARRY);
            cpu.PC.set(cpu.PC.get() + 2);
        });

        funcTable.put((byte)0xF9, () -> {
            Load.load16Bit(cpu, cpu.SP, "SP", cpu.HL.get(), (byte)0xF9);
        });
    }

    private static void loadLogInstruction(CPU cpu, String inputStr, int input, byte instruction) {
        Utils.PrintInstruction("LD " + inputStr + " " + input,
                instruction,
                cpu.PC.get(),
                null, 0
        );
    }

    private static void load8Bit(CPU cpu, Register reg, boolean highLow, String inputStr, byte input, byte instruction) {
        if(highLow) {
            reg.setHighByte(input);
        } else {
            reg.setLowByte(input);
        }

        cpu.PC.set(cpu.PC.get() + 1);

        loadLogInstruction(cpu, inputStr, input, instruction);
    }

    private static void load16Bit(CPU cpu, Register reg, String inputStr, int input, byte instruction) {
        reg.set(input);

        cpu.PC.set(cpu.PC.get() + 1);

        loadLogInstruction(cpu, inputStr, input, instruction);
    }

    /**
     * Transfers memory from ram, registers,
     * and 16-bit registers.
     * 
     * @param cpu Reference to CPU object.
     * @param instruction Instruction opcode.
     */
    static void load(CPU cpu, byte instruction) {
        byte parameters[] = { 0x0, 0x0 };
        int address;
        
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
                
                break;            case 0x02:
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
                
                break;            case 0x12:
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
                
                break;            case 0x1A:
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
                
                break;            case 0x36:
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
                        cpu.memory.read(parameters[0] & 0xFF | 0xFF00));
                
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
