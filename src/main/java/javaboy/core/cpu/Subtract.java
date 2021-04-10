package javaboy.core.cpu;

import javaboy.Utils;

import java.util.Map;

/**
 * Subtracts registers and byte of memory.
 * 
 * @author Daniel Simpkins
 *
 */
public class Subtract {

        public static void buildOpcodes(Map<Byte, Runnable> funcTable, CPU cpu) {
                funcTable.put((byte) 0x90, () -> Subtract.subtract8Bit(cpu, cpu.BC.getHighByte(), "B", (byte) 0x90));
                funcTable.put((byte) 0x91, () -> Subtract.subtract8Bit(cpu, cpu.BC.getLowByte(), "C", (byte) 0x91));
                funcTable.put((byte) 0x92, () -> Subtract.subtract8Bit(cpu, cpu.DE.getHighByte(), "D", (byte) 0x92));
                funcTable.put((byte) 0x93, () -> Subtract.subtract8Bit(cpu, cpu.DE.getLowByte(), "E", (byte) 0x93));
                funcTable.put((byte) 0x94, () -> Subtract.subtract8Bit(cpu, cpu.HL.getHighByte(), "H", (byte) 0x94));
                funcTable.put((byte) 0x95, () -> Subtract.subtract8Bit(cpu, cpu.HL.getLowByte(), "L", (byte) 0x95));
                funcTable.put((byte) 0x97, () -> Subtract.subtract8Bit(cpu, cpu.AF.getHighByte(), "A", (byte) 0x97));

                funcTable.put((byte) 0x96, () -> {
                        int memValue = cpu.memory.read(cpu.HL.get());
                        Subtract.subtract8Bit(cpu, memValue, "MEM", (byte) 0x96);
                });

                funcTable.put((byte) 0x98, () -> Subtract.subtractCarry8Bit(cpu, cpu.BC.getHighByte(), "B", (byte) 0x98,
                                cpu.flags.getCarry()));
                funcTable.put((byte) 0x99, () -> Subtract.subtractCarry8Bit(cpu, cpu.BC.getLowByte(), "C", (byte) 0x99,
                                cpu.flags.getCarry()));
                funcTable.put((byte) 0x9A, () -> Subtract.subtractCarry8Bit(cpu, cpu.DE.getHighByte(), "D", (byte) 0x9A,
                                cpu.flags.getCarry()));
                funcTable.put((byte) 0x9B, () -> Subtract.subtractCarry8Bit(cpu, cpu.DE.getLowByte(), "E", (byte) 0x9B,
                                cpu.flags.getCarry()));
                funcTable.put((byte) 0x9C, () -> Subtract.subtractCarry8Bit(cpu, cpu.HL.getHighByte(), "H", (byte) 0x9C,
                                cpu.flags.getCarry()));
                funcTable.put((byte) 0x9D, () -> Subtract.subtractCarry8Bit(cpu, cpu.HL.getLowByte(), "L", (byte) 0x9D,
                                cpu.flags.getCarry()));
                funcTable.put((byte) 0x9F, () -> Subtract.subtractCarry8Bit(cpu, cpu.AF.getHighByte(), "A", (byte) 0x9F,
                                cpu.flags.getCarry()));

                funcTable.put((byte) 0x9E, () -> {
                        int memValue = cpu.memory.read(cpu.HL.get());
                        Subtract.subtractCarry8Bit(cpu, memValue, "MEM", (byte) 0x9E, cpu.flags.getCarry());
                });

                funcTable.put((byte) 0xD6, () -> {
                        int immValue = cpu.memory.read(cpu.PC.get() + 1);
                        Subtract.subtract8Bit(cpu, immValue, "d8", (byte) 0xD6);
                        cpu.PC.set(cpu.PC.get() + 1);
                });

                funcTable.put((byte) 0xDE, () -> {
                        int immValue = cpu.memory.read(cpu.PC.get() + 1);
                        Subtract.subtractCarry8Bit(cpu, immValue, "d8", (byte) 0xDE, cpu.flags.getCarry());
                        cpu.PC.set(cpu.PC.get() + 1);
                });
        }

        private static void subtractLogInstruction(CPU cpu, String input, byte instruction) {
                Utils.PrintInstruction("SUB " + input, instruction, cpu.PC.get(), null, 0);
        }

        private static void subtract8Bit(CPU cpu, int input, String inputStr, byte instruction) {
                int initial = cpu.AF.getHighByte();
                int result = initial - input;
                cpu.AF.setHighByte((byte) result);

                cpu.flags.setFlags(initial, result, false, Flags.HALFC | Flags.CARRY | Flags.ZERO);

                cpu.flags.setSign(true);

                subtractLogInstruction(cpu, inputStr, instruction);

                cpu.PC.set(cpu.PC.get() + 1);
        }

        private static void subtractCarryLogInstruction(CPU cpu, String input, byte instruction) {
                Utils.PrintInstruction("SBC " + input, instruction, cpu.PC.get(), null, 0);
        }

        private static void subtractCarry8Bit(CPU cpu, int input, String inputStr, byte instruction, boolean carry) {
                int initial = cpu.AF.getHighByte();
                int result = initial - (input + (carry ? 1 : 0));
                cpu.AF.setHighByte((byte) result);

                cpu.flags.setFlags(initial, result, false, Flags.HALFC | Flags.CARRY | Flags.ZERO);

                cpu.flags.setSign(true);

                subtractCarryLogInstruction(cpu, inputStr, instruction);

                cpu.PC.set(cpu.PC.get() + 1);
        }
}
