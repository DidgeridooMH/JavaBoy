package javaboy.core.cpu;

import javaboy.Utils;

import java.util.Map;

/**
 * Decrements a register or 16-bit combined register.
 * 
 * @author Daniel Simpkins
 *
 */
public class Decrement {

        public static void buildOpcodes(Map<Byte, Runnable> funcTable, CPU cpu) {
                funcTable.put((byte) 0x0B, () -> Decrement.decrement16Bit(cpu, cpu.BC, "BC", (byte) 0x0B));
                funcTable.put((byte) 0x1B, () -> Decrement.decrement16Bit(cpu, cpu.DE, "DE", (byte) 0x1B));
                funcTable.put((byte) 0x2B, () -> Decrement.decrement16Bit(cpu, cpu.HL, "HL", (byte) 0x2B));
                funcTable.put((byte) 0x3B, () -> Decrement.decrement16Bit(cpu, cpu.SP, "SP", (byte) 0x3B));
                funcTable.put((byte) 0x05, () -> Decrement.decrement8Bit(cpu, cpu.BC, true, "B", (byte) 0x05));
                funcTable.put((byte) 0x15, () -> Decrement.decrement8Bit(cpu, cpu.DE, true, "D", (byte) 0x15));
                funcTable.put((byte) 0x25, () -> Decrement.decrement8Bit(cpu, cpu.HL, true, "H", (byte) 0x25));
                funcTable.put((byte) 0x35, () -> {
                        Register reg = new Register(cpu.memory.read(cpu.HL.get()), "MEM");
                        Decrement.decrement8Bit(cpu, reg, false, "MEM", (byte) 0x35);
                        cpu.memory.write((byte) (reg.getLowByte()), cpu.HL.get());
                });
                funcTable.put((byte) 0x0D, () -> Decrement.decrement8Bit(cpu, cpu.BC, false, "C", (byte) 0x0D));
                funcTable.put((byte) 0x1D, () -> Decrement.decrement8Bit(cpu, cpu.DE, false, "E", (byte) 0x1D));
                funcTable.put((byte) 0x2D, () -> Decrement.decrement8Bit(cpu, cpu.HL, false, "L", (byte) 0x2D));
                funcTable.put((byte) 0x3D, () -> Decrement.decrement8Bit(cpu, cpu.AF, true, "A", (byte) 0x3D));
        }

        private static void decrementLogInstruction(CPU cpu, String input, byte instruction) {
                Utils.PrintInstruction("DEC " + input, instruction, cpu.PC.get(), null, 0);
        }

        private static void decrement8Bit(CPU cpu, Register reg, boolean highLow, String inputStr, byte instruction) {
                int initial;
                if (highLow) {
                        initial = reg.getHighByte();
                        reg.setHighByte((byte) (initial - 1));
                } else {
                        initial = reg.getLowByte();
                        reg.setLowByte((byte) (initial - 1));
                }

                cpu.flags.setFlags(initial, (byte) (initial - 1), false, Flags.HALFC | Flags.ZERO);

                cpu.flags.setSign(true);

                decrementLogInstruction(cpu, inputStr, instruction);

                cpu.PC.set(cpu.PC.get() + 1);
        }

        private static void decrement16Bit(CPU cpu, Register reg, String inputStr, byte instruction) {
                int initial = reg.get();
                reg.set(initial - 1);

                decrementLogInstruction(cpu, inputStr, instruction);

                cpu.PC.set(cpu.PC.get() + 1);
        }
}
