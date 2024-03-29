package javaboy.core.cpu;

import javaboy.Utils;

import java.util.Map;

/**
 * Increments a register or 16-bit combined register.
 * 
 * @author Daniel Simpkins
 *
 */
public class Increment {

        public static void buildOpcodes(Map<Byte, Runnable> funcTable, CPU cpu) {
                funcTable.put((byte) 0x03, () -> Increment.increment16Bit(cpu, cpu.BC, "BC", (byte) 0x03));
                funcTable.put((byte) 0x13, () -> Increment.increment16Bit(cpu, cpu.DE, "DE", (byte) 0x13));
                funcTable.put((byte) 0x23, () -> Increment.increment16Bit(cpu, cpu.HL, "HL", (byte) 0x23));
                funcTable.put((byte) 0x33, () -> Increment.increment16Bit(cpu, cpu.SP, "SP", (byte) 0x33));
                funcTable.put((byte) 0x04, () -> Increment.increment8Bit(cpu, cpu.BC, true, "B", (byte) 0x04));
                funcTable.put((byte) 0x14, () -> Increment.increment8Bit(cpu, cpu.DE, true, "D", (byte) 0x14));
                funcTable.put((byte) 0x24, () -> Increment.increment8Bit(cpu, cpu.HL, true, "H", (byte) 0x24));
                funcTable.put((byte) 0x34, () -> {
                        Register reg = new Register(cpu.memory.read(cpu.HL.get()), "MEM");
                        Increment.increment8Bit(cpu, reg, false, "MEM", (byte) 0x34);
                        cpu.memory.write((byte) (reg.getLowByte()), cpu.HL.get());
                });
                funcTable.put((byte) 0x0C, () -> Increment.increment8Bit(cpu, cpu.BC, false, "C", (byte) 0x0C));
                funcTable.put((byte) 0x1C, () -> Increment.increment8Bit(cpu, cpu.DE, false, "E", (byte) 0x1C));
                funcTable.put((byte) 0x2C, () -> Increment.increment8Bit(cpu, cpu.HL, false, "L", (byte) 0x2C));
                funcTable.put((byte) 0x3C, () -> Increment.increment8Bit(cpu, cpu.AF, true, "A", (byte) 0x3C));
        }

        private static void incrementLogInstruction(CPU cpu, String input, byte instruction) {
                Utils.PrintInstruction("INC " + input, instruction, cpu.PC.get(), null, 0);
        }

        private static void increment8Bit(CPU cpu, Register reg, boolean highLow, String inputStr, byte instruction) {
                int initial;
                if (highLow) {
                        initial = reg.getHighByte();
                        reg.setHighByte((byte) (initial + 1));
                } else {
                        initial = reg.getLowByte();
                        reg.setLowByte((byte) (initial + 1));
                }

                cpu.flags.setFlags(initial, (byte) (initial + 1), false, Flags.HALFC | Flags.ZERO);

                cpu.flags.setSign(false);

                incrementLogInstruction(cpu, inputStr, instruction);

                cpu.PC.set(cpu.PC.get() + 1);
        }

        private static void increment16Bit(CPU cpu, Register reg, String inputStr, byte instruction) {
                int initial = reg.get();
                reg.set(initial + 1);

                incrementLogInstruction(cpu, inputStr, instruction);

                cpu.PC.set(cpu.PC.get() + 1);
        }
}
