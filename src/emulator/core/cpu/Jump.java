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
 * Handles jump, jump-to-subroutine,
 * and call instructions.
 * 
 * @author Daniel Simpkins
 *
 */
public class Jump {

    public static void buildOpcodes(Map<Byte, Runnable> funcTable, CPU cpu) {
        /*
         * Call opcodes
         */
        funcTable.put((byte)0xC4, () -> Jump.call(cpu, "NZ", !cpu.flags.getZero(), (byte)0xC4));
        funcTable.put((byte)0xCC, () -> Jump.call(cpu, "Z", cpu.flags.getZero(), (byte)0xCC));
        funcTable.put((byte)0xD4, () -> Jump.call(cpu, "NC", !cpu.flags.getCarry(), (byte)0xD4));
        funcTable.put((byte)0xDC, () -> Jump.call(cpu, "C", cpu.flags.getCarry(), (byte)0xDC));
        funcTable.put((byte)0xCD, () -> Jump.call(cpu, "", true, (byte)0xCD));

        /*
         * Jump to Subroutine opcodes
         */
        funcTable.put((byte)0x20, () -> Jump.jumpSubroutine(cpu, "NZ", !cpu.flags.getZero(), (byte)0x20));
        funcTable.put((byte)0x28, () -> Jump.jumpSubroutine(cpu, "Z", cpu.flags.getZero(), (byte)0x28));
        funcTable.put((byte)0x30, () -> Jump.jumpSubroutine(cpu, "NC", !cpu.flags.getCarry(), (byte)0x30));
        funcTable.put((byte)0x38, () -> Jump.jumpSubroutine(cpu, "C", cpu.flags.getCarry(), (byte)0x38));
        funcTable.put((byte)0x18, () -> Jump.jumpSubroutine(cpu, "", true, (byte)0x18));

        /*
         * Return opcodes
         */
        funcTable.put((byte)0xC0, () -> Jump.returnFromCall(cpu, "NZ", !cpu.flags.getZero(), (byte)0xC0));
        funcTable.put((byte)0xC8, () -> Jump.returnFromCall(cpu, "Z", cpu.flags.getZero(), (byte)0xC8));
        funcTable.put((byte)0xD0, () -> Jump.returnFromCall(cpu, "NC", !cpu.flags.getCarry(), (byte)0xD0));
        funcTable.put((byte)0xD8, () -> Jump.returnFromCall(cpu, "C", cpu.flags.getCarry(), (byte)0xD8));
        funcTable.put((byte)0xC9, () -> Jump.returnFromCall(cpu, "", true, (byte)0xC9));

        /*
         * Jump opcodes
         */
        funcTable.put((byte)0xC2, () -> Jump.jump(cpu, "NZ", !cpu.flags.getZero(), (byte)0xC2));
        funcTable.put((byte)0xCA, () -> Jump.jump(cpu, "Z", cpu.flags.getZero(), (byte)0xCA));
        funcTable.put((byte)0xD2, () -> Jump.jump(cpu, "NC", !cpu.flags.getCarry(), (byte)0xD2));
        funcTable.put((byte)0xDA, () -> Jump.jump(cpu, "C", cpu.flags.getCarry(), (byte)0xDA));
        funcTable.put((byte)0xC3, () -> Jump.jump(cpu, "", true, (byte)0xC3));
    }

    private static void callLogInstruction(CPU cpu, String condition, int address, byte instruction) {
        Utils.PrintInstruction("CALL " + condition + address,
                instruction,
                cpu.PC.get(),
                null, 0
        );
    }

    private static void call(CPU cpu, String condition, boolean doJump, byte instruction) {
        if(doJump) {
            byte parameters[] = { cpu.memory.read(cpu.PC.get() + 1),
                    cpu.memory.read(cpu.PC.get() + 2) };
            int address = ((short) parameters[1] << 8) | (short) (parameters[0] & 0xFF);

            cpu.push16((short) (cpu.PC.get() + 3));
            cpu.PC.set(address);

            callLogInstruction(cpu, condition, address, instruction);
        } else {
            cpu.PC.set(cpu.PC.get() + 3);
            callLogInstruction(cpu, condition, 0, instruction);
        }
    }

    private static void jumpSubLogInstruction(CPU cpu, String condition, int address, byte instruction) {
        Utils.PrintInstruction("JMP_SUB " + condition + address,
                instruction,
                cpu.PC.get(),
                null, 0
        );
    }

    private static void jumpSubroutine(CPU cpu, String condition, boolean doJump, byte instruction) {
        if(doJump) {
            byte offset = cpu.memory.read(cpu.PC.get() + 1);
            cpu.PC.set(cpu.PC.get() + offset + 2);

            jumpSubLogInstruction(cpu, condition, offset, instruction);
        } else {
            cpu.PC.set(cpu.PC.get() + 2);

            jumpSubLogInstruction(cpu, condition, 0, instruction);
        }
    }

    private static void returnFromCall(CPU cpu, String condition, boolean doJump, byte instruction) {
        if(doJump) {
            cpu.PC.set(cpu.pop16());
        } else {
            cpu.PC.set(cpu.PC.get() + 1);
        }

        Utils.PrintInstruction("RET " + condition, instruction, cpu.PC.get(), null, 0);
    }

    private static void jumpLogInstruction(CPU cpu, String condition, int address, byte instruction) {
        Utils.PrintInstruction("JUMP " + condition + address,
                instruction,
                cpu.PC.get(),
                null, 0
        );
    }

    private static void jump(CPU cpu, String condition, boolean doJump, byte instruction) {
        if(doJump) {
            int highByte = (cpu.memory.read(cpu.PC.get() + 2) << 8) & 0xFF00;
            int lowByte = cpu.memory.read(cpu.PC.get() + 1);
            int address = (highByte & 0xFF00) | (lowByte & 0x00FF);
            cpu.PC.set(address);

            jumpLogInstruction(cpu, condition, address, instruction);
        } else {
            cpu.PC.set(cpu.PC.get() + 2);
            jumpLogInstruction(cpu, condition, 0, instruction);
        }
    }
    
}
