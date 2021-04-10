package javaboy.core.cpu;

import javaboy.Utils;

import java.util.Map;

/**
 * Adds registers, 16-bit combined registers, and memory locations.
 * 
 * @author Daniel Simpkins
 *
 */
public class Addition {

        public static void buildOpcodes(Map<Byte, Runnable> funcTable, CPU cpu) {
                funcTable.put((byte) 0x09, () -> Addition.addition16Bit(cpu, cpu.BC.get(), "BC", (byte) 0x09));
                funcTable.put((byte) 0x19, () -> Addition.addition16Bit(cpu, cpu.DE.get(), "DE", (byte) 0x19));
                funcTable.put((byte) 0x29, () -> Addition.addition16Bit(cpu, cpu.HL.get(), "HL", (byte) 0x29));
                funcTable.put((byte) 0x39, () -> Addition.addition16Bit(cpu, cpu.SP.get(), "SP", (byte) 0x39));
                funcTable.put((byte) 0x80, () -> Addition.addition8Bit(cpu, cpu.BC.getHighByte(), "B", (byte) 0x80));
                funcTable.put((byte) 0x81, () -> Addition.addition8Bit(cpu, cpu.BC.getLowByte(), "C", (byte) 0x81));
                funcTable.put((byte) 0x82, () -> Addition.addition8Bit(cpu, cpu.DE.getHighByte(), "D", (byte) 0x82));
                funcTable.put((byte) 0x83, () -> Addition.addition8Bit(cpu, cpu.DE.getLowByte(), "E", (byte) 0x83));
                funcTable.put((byte) 0x84, () -> Addition.addition8Bit(cpu, cpu.HL.getHighByte(), "H", (byte) 0x84));
                funcTable.put((byte) 0x85, () -> Addition.addition8Bit(cpu, cpu.HL.getLowByte(), "L", (byte) 0x85));
                funcTable.put((byte) 0x86, () -> {
                        int memValue = cpu.memory.read(cpu.HL.get());
                        Addition.addition8Bit(cpu, memValue, "MEM", (byte) 0x86);
                });
                funcTable.put((byte) 0x87, () -> Addition.addition8Bit(cpu, cpu.AF.getHighByte(), "A", (byte) 0x87));
                funcTable.put((byte) 0xC6, () -> {
                        int memValue = cpu.memory.read(cpu.PC.get() + 1);
                        Addition.addition8Bit(cpu, memValue, "MEM", (byte) 0xC6);
                        cpu.PC.set(cpu.PC.get() + 1);
                });
        }

        private static void additionLogInstruction(CPU cpu, String input, String operand, byte instruction) {
                Utils.PrintInstruction("ADD " + input + ", " + operand, instruction, cpu.PC.get(), null, 0);
        }

        private static void addition8Bit(CPU cpu, int input, String inputStr, byte instruction) {
                int initial = cpu.AF.getHighByte();
                int result = initial + input;
                cpu.AF.setHighByte((byte) result);

                cpu.flags.setFlags(initial, result, false, Flags.HALFC | Flags.CARRY | Flags.ZERO);

                cpu.flags.setSign(false);

                additionLogInstruction(cpu, "A", inputStr, instruction);

                cpu.PC.set(cpu.PC.get() + 1);
        }

        private static void addition16Bit(CPU cpu, int input, String inputStr, byte instruction) {
                int initial = cpu.HL.get();
                int result = initial + input;
                cpu.HL.set(result);

                cpu.flags.setFlags(initial, result, true, Flags.HALFC | Flags.CARRY | Flags.ZERO);

                cpu.flags.setSign(false);

                additionLogInstruction(cpu, "HL", inputStr, instruction);

                cpu.PC.set(cpu.PC.get() + 1);
        }
}
