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
 * Handles Pushing and Popping registers, 16-bit combined registers, and bytes
 * of memory to and from the stack.
 * 
 * @author Daniel Simpkins
 *
 */
public class Stack {

    public static void buildOpcodes(Map<Byte, Runnable> funcTable, CPU cpu) {
        funcTable.put((byte) 0xC1, () -> {
            cpu.BC.set(cpu.pop16());
            printPop(cpu, "BC", (byte)0xC1);
            cpu.PC.set(cpu.PC.get() + 1);
        });
        funcTable.put((byte) 0xD1, () -> {
            cpu.DE.set(cpu.pop16());
            printPop(cpu, "DE", (byte)0xD1);
            cpu.PC.set(cpu.PC.get() + 1);
        });
        funcTable.put((byte) 0xE1, () -> {
            cpu.HL.set(cpu.pop16());
            printPop(cpu, "HL", (byte)0xE1);
            cpu.PC.set(cpu.PC.get() + 1);
        });
        funcTable.put((byte) 0xF1, () -> {
            cpu.flags.byteToFlags(cpu.pop());
            cpu.AF.setHighByte(cpu.pop());
            printPop(cpu, "AF", (byte)0xF1);
            cpu.PC.set(cpu.PC.get() + 1);
        });

        funcTable.put((byte) 0xC5, () -> {
            cpu.push16((short)cpu.BC.get());
            printPush(cpu, "BC", (byte)0xC5);
            cpu.PC.set(cpu.PC.get() + 1);
        });
        funcTable.put((byte) 0xD5, () -> {
            cpu.push16((short)cpu.BC.get());
            printPush(cpu, "DE", (byte)0xD5);
            cpu.PC.set(cpu.PC.get() + 1);
        });
        funcTable.put((byte) 0xE5, () -> {
            cpu.push16((short)cpu.BC.get());
            printPush(cpu, "HL", (byte)0xE5);
            cpu.PC.set(cpu.PC.get() + 1);
        });
        funcTable.put((byte) 0xF5, () -> {
            cpu.push((byte) cpu.AF.getHighByte());
            cpu.push(cpu.flags.generateReg());
            printPush(cpu, "AF", (byte)0xF5);
            cpu.PC.set(cpu.PC.get() + 1);
        });
    }

    private static void printPop(CPU cpu, String register, byte instruction) {
        Utils.PrintInstruction("pop " + register,
                instruction,
                cpu.PC.get(),
                null, 0
        );
    }

    private static void printPush(CPU cpu, String register, byte instruction) {
        Utils.PrintInstruction("push " + register,
                instruction,
                cpu.PC.get(),
                null, 0
        );
    }
}
