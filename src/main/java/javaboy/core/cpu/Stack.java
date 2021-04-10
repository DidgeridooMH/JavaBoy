package javaboy.core.cpu;

import javaboy.Utils;

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
            printPop(cpu, "BC", (byte) 0xC1);
            cpu.PC.set(cpu.PC.get() + 1);
        });
        funcTable.put((byte) 0xD1, () -> {
            cpu.DE.set(cpu.pop16());
            printPop(cpu, "DE", (byte) 0xD1);
            cpu.PC.set(cpu.PC.get() + 1);
        });
        funcTable.put((byte) 0xE1, () -> {
            cpu.HL.set(cpu.pop16());
            printPop(cpu, "HL", (byte) 0xE1);
            cpu.PC.set(cpu.PC.get() + 1);
        });
        funcTable.put((byte) 0xF1, () -> {
            cpu.flags.byteToFlags(cpu.pop());
            cpu.AF.setHighByte(cpu.pop());
            printPop(cpu, "AF", (byte) 0xF1);
            cpu.PC.set(cpu.PC.get() + 1);
        });

        funcTable.put((byte) 0xC5, () -> {
            cpu.push16((short) cpu.BC.get());
            printPush(cpu, "BC", (byte) 0xC5);
            cpu.PC.set(cpu.PC.get() + 1);
        });
        funcTable.put((byte) 0xD5, () -> {
            cpu.push16((short) cpu.BC.get());
            printPush(cpu, "DE", (byte) 0xD5);
            cpu.PC.set(cpu.PC.get() + 1);
        });
        funcTable.put((byte) 0xE5, () -> {
            cpu.push16((short) cpu.BC.get());
            printPush(cpu, "HL", (byte) 0xE5);
            cpu.PC.set(cpu.PC.get() + 1);
        });
        funcTable.put((byte) 0xF5, () -> {
            cpu.push((byte) cpu.AF.getHighByte());
            cpu.push(cpu.flags.generateReg());
            printPush(cpu, "AF", (byte) 0xF5);
            cpu.PC.set(cpu.PC.get() + 1);
        });
    }

    private static void printPop(CPU cpu, String register, byte instruction) {
        Utils.PrintInstruction("pop " + register, instruction, cpu.PC.get(), null, 0);
    }

    private static void printPush(CPU cpu, String register, byte instruction) {
        Utils.PrintInstruction("push " + register, instruction, cpu.PC.get(), null, 0);
    }
}
