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
 * Compares register or byte in memory.
 * 
 * @author Daniel Simpkins
 *
 */
public class Compare {

    public static void buildOpcodes(Map<Byte, Runnable> funcTable, CPU cpu) {
        funcTable.put((byte)0xB8, () -> Compare.compare(cpu, cpu.BC.getHighByte(), "B", (byte)0xB8));
        funcTable.put((byte)0xB9, () -> Compare.compare(cpu, cpu.BC.getLowByte(), "C", (byte)0xB9));
        funcTable.put((byte)0xBA, () -> Compare.compare(cpu, cpu.DE.getHighByte(), "D", (byte)0xBA));
        funcTable.put((byte)0xBB, () -> Compare.compare(cpu, cpu.DE.getLowByte(), "E", (byte)0xBB));
        funcTable.put((byte)0xBC, () -> Compare.compare(cpu, cpu.HL.getHighByte(), "H", (byte)0xBC));
        funcTable.put((byte)0xBD, () -> Compare.compare(cpu, cpu.HL.getLowByte(), "L", (byte)0xBD));
        funcTable.put((byte)0xBF, () -> Compare.compare(cpu, cpu.AF.getHighByte(), "A", (byte)0xBF));
        funcTable.put((byte)0xBE, () -> {
            int memValue = cpu.memory.read(cpu.HL.get());
            Compare.compare(cpu, memValue, "MEM", (byte)0xBE);
        });
        funcTable.put((byte)0xFE, () -> {
            int memValue = cpu.memory.read(cpu.PC.get() + 1);
            Compare.compare(cpu, memValue, "d8", (byte)0xFE);
            cpu.PC.set(cpu.PC.get() + 1);
        });
    }

    private static void compare(CPU cpu, int input, String inputStr, byte instruction) {
        byte initial = (byte) cpu.AF.getHighByte();
        byte result = (byte) (initial - ((byte) input));


        cpu.flags.setFlags(initial, result,
                false,
                Flags.CARRY |
                        Flags.ZERO |
                        Flags.HALFC |
                        Flags.SUBTRACT
        );

        Utils.PrintInstruction("CP " + inputStr,
                instruction,
                cpu.PC.get(),
                null, 0
        );

        cpu.PC.set(cpu.PC.get() + 1);
    }
}
