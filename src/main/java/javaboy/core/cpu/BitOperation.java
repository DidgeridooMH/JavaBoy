package javaboy.core.cpu;

import javaboy.Utils;

import java.util.Map;

/**
 * Bitwise operation OPCodes
 * 
 * @author Daniel Simpkins
 *
 */
public class BitOperation {

    public static void buildOpcodes(Map<Byte, Runnable> funcTable, CPU cpu) {
        /*
         * AND opcodes
         */
        funcTable.put((byte) 0xA0, () -> BitOperation.and(cpu, cpu.BC.getHighByte(), "B", (byte) 0xA0));
        funcTable.put((byte) 0xA1, () -> BitOperation.and(cpu, cpu.BC.getLowByte(), "C", (byte) 0xA1));
        funcTable.put((byte) 0xA2, () -> BitOperation.and(cpu, cpu.DE.getHighByte(), "D", (byte) 0xA2));
        funcTable.put((byte) 0xA3, () -> BitOperation.and(cpu, cpu.DE.getLowByte(), "E", (byte) 0xA3));
        funcTable.put((byte) 0xA4, () -> BitOperation.and(cpu, cpu.HL.getHighByte(), "H", (byte) 0xA4));
        funcTable.put((byte) 0xA5, () -> BitOperation.and(cpu, cpu.HL.getLowByte(), "L", (byte) 0xA5));
        funcTable.put((byte) 0xA7, () -> BitOperation.and(cpu, cpu.AF.getHighByte(), "A", (byte) 0xA7));
        funcTable.put((byte) 0xA6, () -> {
            int memValue = cpu.memory.read(cpu.HL.get());
            BitOperation.and(cpu, memValue, "MEM", (byte) 0xA6);
        });
        funcTable.put((byte) 0xE6, () -> {
            int memValue = cpu.memory.read(cpu.PC.get() + 1);
            BitOperation.and(cpu, memValue, "d8", (byte) 0xE6);
            cpu.PC.set(cpu.PC.get() + 1);
        });

        /*
         * OR opcodes
         */
        funcTable.put((byte) 0xB0, () -> BitOperation.or(cpu, cpu.BC.getHighByte(), "B", (byte) 0xB0));
        funcTable.put((byte) 0xB1, () -> BitOperation.or(cpu, cpu.BC.getLowByte(), "C", (byte) 0xB1));
        funcTable.put((byte) 0xB2, () -> BitOperation.or(cpu, cpu.DE.getHighByte(), "D", (byte) 0xB2));
        funcTable.put((byte) 0xB3, () -> BitOperation.or(cpu, cpu.DE.getLowByte(), "E", (byte) 0xB3));
        funcTable.put((byte) 0xB4, () -> BitOperation.or(cpu, cpu.HL.getHighByte(), "H", (byte) 0xB4));
        funcTable.put((byte) 0xB5, () -> BitOperation.or(cpu, cpu.HL.getLowByte(), "L", (byte) 0xB5));
        funcTable.put((byte) 0xB7, () -> BitOperation.or(cpu, cpu.AF.getHighByte(), "A", (byte) 0xB7));
        funcTable.put((byte) 0xB6, () -> {
            int memValue = cpu.memory.read(cpu.HL.get());
            BitOperation.or(cpu, memValue, "MEM", (byte) 0xB6);
        });
        funcTable.put((byte) 0xF6, () -> {
            int memValue = cpu.memory.read(cpu.PC.get() + 1);
            BitOperation.or(cpu, memValue, "d8", (byte) 0xF6);
            cpu.PC.set(cpu.PC.get() + 1);
        });

        /*
         * XOR opcodes
         */
        funcTable.put((byte) 0xA8, () -> BitOperation.xor(cpu, cpu.BC.getHighByte(), "B", (byte) 0xA8));
        funcTable.put((byte) 0xA9, () -> BitOperation.xor(cpu, cpu.BC.getLowByte(), "C", (byte) 0xA9));
        funcTable.put((byte) 0xAA, () -> BitOperation.xor(cpu, cpu.DE.getHighByte(), "D", (byte) 0xAA));
        funcTable.put((byte) 0xAB, () -> BitOperation.xor(cpu, cpu.DE.getLowByte(), "E", (byte) 0xAB));
        funcTable.put((byte) 0xAC, () -> BitOperation.xor(cpu, cpu.HL.getHighByte(), "H", (byte) 0xAC));
        funcTable.put((byte) 0xAD, () -> BitOperation.xor(cpu, cpu.HL.getLowByte(), "L", (byte) 0xAD));
        funcTable.put((byte) 0xAF, () -> BitOperation.xor(cpu, cpu.AF.getHighByte(), "A", (byte) 0xAF));
        funcTable.put((byte) 0xAE, () -> {
            int memValue = cpu.memory.read(cpu.HL.get());
            BitOperation.xor(cpu, memValue, "MEM", (byte) 0xAE);
        });
        funcTable.put((byte) 0xEE, () -> {
            int memValue = cpu.memory.read(cpu.PC.get() + 1);
            BitOperation.xor(cpu, memValue, "d8", (byte) 0xEE);
            cpu.PC.set(cpu.PC.get() + 1);
        });
    }

    private static void bitLogInstruction(CPU cpu, String insName, String input, String operand, byte instruction) {
        Utils.PrintInstruction(insName + " " + input + ", " + operand, instruction, cpu.PC.get(), null, 0);
    }

    private static void and(CPU cpu, int input, String inputStr, byte instruction) {
        int initial = cpu.AF.getHighByte();
        int result = initial & input;
        cpu.AF.setHighByte((byte) result);

        cpu.flags.setFlags(initial, result, false, Flags.ZERO);
        cpu.flags.setSign(false);
        cpu.flags.setHalfCarry(true);
        cpu.flags.setCarry(false);

        bitLogInstruction(cpu, "AND", "A", inputStr, instruction);

        cpu.PC.set(cpu.PC.get() + 1);
    }

    private static void or(CPU cpu, int input, String inputStr, byte instruction) {
        int initial = cpu.AF.getHighByte();
        int result = initial | input;
        cpu.AF.setHighByte((byte) result);

        cpu.flags.setFlags(initial, result, false, Flags.ZERO);
        cpu.flags.setSign(false);
        cpu.flags.setHalfCarry(false);
        cpu.flags.setCarry(false);

        bitLogInstruction(cpu, "OR", "A", inputStr, instruction);

        cpu.PC.set(cpu.PC.get() + 1);
    }

    private static void xor(CPU cpu, int input, String inputStr, byte instruction) {
        int initial = cpu.AF.getHighByte();
        int result = initial ^ input;
        cpu.AF.setHighByte((byte) result);

        cpu.flags.setFlags(initial, result, false, Flags.ZERO);
        cpu.flags.setSign(false);
        cpu.flags.setHalfCarry(false);
        cpu.flags.setCarry(false);

        bitLogInstruction(cpu, "XOR", "A", inputStr, instruction);

        cpu.PC.set(cpu.PC.get() + 1);
    }

    /**
     * Parses instruction and rotates/shifts register or memory.
     * 
     * @param cpu         Reference to the CPU object.
     * @param instruction Instruction opcode.
     * @param isPrefix    States whether the opcode is prefix or standard.
     */
    static void rotate(CPU cpu, byte instruction, boolean isPrefix) {
        byte highByte = (byte) (instruction >> 4);
        byte lowByte = (byte) (instruction & 0xF);
        byte regByte = (byte) (lowByte & 0x07);

        /*
         * Stores which type of instruction to execute and which direction to rotate the
         * bits.
         */
        boolean fromCarry = highByte > 0;
        boolean direction = lowByte > 7;

        String instructionName = "";
        String register = "";

        instructionName = (direction) ? "RR" : "RL";

        /*
         * Parses the register to be used and which direction to rotate
         */
        switch (regByte) {
        case 0x00:
            if (direction) {
                rotateRight(cpu.BC, cpu.flags, true, fromCarry, true);
            } else {
                rotateLeft(cpu.BC, cpu.flags, true, fromCarry, true);
            }
            register = "B";
            break;
        case 0x01:
            if (direction) {
                rotateRight(cpu.BC, cpu.flags, false, fromCarry, true);
            } else {
                rotateLeft(cpu.BC, cpu.flags, false, fromCarry, true);
            }
            register = "C";
            break;
        case 0x02:
            if (direction) {
                rotateRight(cpu.DE, cpu.flags, true, fromCarry, true);
            } else {
                rotateLeft(cpu.DE, cpu.flags, true, fromCarry, true);
            }
            register = "D";
            break;
        case 0x03:
            if (direction) {
                rotateRight(cpu.DE, cpu.flags, false, fromCarry, true);
            } else {
                rotateLeft(cpu.DE, cpu.flags, false, fromCarry, true);
            }
            register = "E";
            break;
        case 0x04:
            if (direction) {
                rotateRight(cpu.HL, cpu.flags, true, fromCarry, true);
            } else {
                rotateLeft(cpu.HL, cpu.flags, true, fromCarry, true);
            }
            register = "H";
            break;
        case 0x05:
            if (direction) {
                rotateRight(cpu.HL, cpu.flags, false, fromCarry, true);
            } else {
                rotateLeft(cpu.HL, cpu.flags, false, fromCarry, true);
            }
            register = "L";
            break;

        /*
         * Case 0x06 will be for (HL). Overload rotate functions?
         */

        case 0x07:
            if (direction) {
                rotateRight(cpu.AF, cpu.flags, true, fromCarry, isPrefix);
            } else {
                rotateLeft(cpu.AF, cpu.flags, true, fromCarry, isPrefix);
            }
            register = "A";
            break;
        default:
            System.err.println("Error while parsing Rotate instruction: " + Utils.hex(instruction) + " at "
                    + Utils.hex(cpu.PC.get()));
            cpu.error = true;
            return;
        }

        cpu.flags.setSubtract(false);
        cpu.flags.setHalfCarry(false);

        Utils.PrintInstruction(instructionName + " " + register, instruction, cpu.PC.get(), null, 0);

        /*
         * Increments the program counter based on if the instruction was prefixed or
         * standard.
         */
        if (isPrefix) {
            cpu.PC.set(cpu.PC.get() + 2);
        } else {
            cpu.PC.set(cpu.PC.get() + 1);
        }

    }

    /**
     * Rotates a register or byte from memory one bit to the left.
     * 
     * @param register Reference to the 16-bit register object to rotate.
     * @param flags    Reference to the flags object to affect.
     * @param highLow  True for high byte, false for low byte
     * @param useCarry Set if carry is to be stored into bit 0
     * @param setZero  Set if the zero flag is to be set
     */
    static void rotateLeft(Register register, Flags flags, boolean highLow, boolean useCarry, boolean setZero) {

        byte regValue = (byte) ((highLow) ? register.getHighByte() : register.getLowByte());

        byte bit7 = (byte) (((regValue & 0x80) > 0) ? 1 : 0);
        byte value = (byte) (regValue << 1);

        /*
         * If set, the carry flag will be stored in bit 0 of the register. Otherwise,
         * store bit 7 into bit 0.
         */
        if (useCarry) {
            if (flags.getCarry()) {
                value += 1;
            }

            if (bit7 > 0) {
                flags.setCarry(true);
            } else {
                flags.setCarry(false);
            }
        } else {
            if (bit7 > 0) {
                value += 1;
                flags.setCarry(true);
            } else {
                flags.setCarry(false);
            }
        }

        if (setZero) {
            flags.setZero(value == 0);
        }

        if (highLow) {
            register.setHighByte(value);
        } else {
            register.setLowByte(value);
        }
    }

    /**
     * Rotates a register or byte from memory one bit to the right.
     * 
     * @param register Reference to the 16-bit register object to rotate.
     * @param flags    Reference to the flags object to affect.
     * @param highLow  True for high byte, false for low byte.
     * @param useCarry Set if carry is to be stored into bit 0.
     * @param setZero  Set if the zero flag is to be set.
     */
    static void rotateRight(Register register, Flags flags, boolean highLow, boolean useCarry, boolean setZero) {

        byte regValue = (byte) ((highLow) ? register.getHighByte() : register.getLowByte());

        byte bit0 = (byte) (((regValue & 0x01) > 0x0) ? 1 : 0);
        byte value = (byte) (regValue >> 1);

        /*
         * If set, the carry flag will be stored in bit 0 of the register. Otherwise,
         * store bit 7 into bit 0.
         */
        if (useCarry) {
            if (flags.getCarry()) {
                value += 0x80;
            }
            if (bit0 > 0) {
                flags.setCarry(true);
            } else {
                flags.setCarry(false);
            }
        } else {
            if (bit0 > 0) {
                value += 0x80;
                flags.setCarry(true);
            } else {
                flags.setCarry(false);
            }
        }

        if (setZero) {
            flags.setZero(value == 0);
        }

        if (highLow) {
            register.setHighByte(value);
        } else {
            register.setLowByte(value);
        }
    }

    /**
     * Tests if a bit is set in a register or byte of memory.
     * 
     * @param cpu         Reference to CPU object.
     * @param instruction Instruction opcode.
     */
    static void bitTest(CPU cpu, byte instruction) {
        int regValue = 0x0;
        byte bitValue = 0x0;
        byte regNibble = 0x0;
        byte bitNibble = 0x0;
        String regName = "";
        String testBit = "";

        /*
         * High nibble(4 bits) is the register to test. Low nibble is the bit to test
         * for.
         */
        regNibble = (byte) (instruction & 0x0F);
        bitNibble = (byte) ((instruction >> 4) & 0xF);

        switch (regNibble) {
        case 0x0:
            // fall through
        case 0x8:
            regValue = (byte) cpu.BC.getHighByte();
            regName = "B";
            break;
        case 0x1:
            // fall through
        case 0x9:
            regValue = (byte) cpu.BC.getLowByte();
            regName = "C";
            break;
        case 0x2:
            // fall through
        case 0xA:
            regValue = (byte) cpu.DE.getHighByte();
            regName = "D";
            break;
        case 0x3:
            // fall through
        case 0xB:
            regValue = (byte) cpu.DE.getLowByte();
            regName = "E";
            break;
        case 0x4:
            // fall through
        case 0xC:
            regValue = cpu.HL.getHighByte() & 0x00FF;
            regName = "H";
            break;
        case 0x5:
            // fall through
        case 0xD:
            regValue = (byte) cpu.HL.getLowByte();
            regName = "L";
            break;
        case 0x6:
            // fall through
        case 0xE:
            regValue = cpu.memory.read(cpu.HL.get());
            regName = "(HL)";
            break;
        case 0x7:
            // fall through
        case 0xF:
            regValue = (byte) cpu.AF.getHighByte();
            regName = "A";
            break;
        default:
            System.err.println("Error parsing BIT instruction at: " + Utils.hex(cpu.PC.get()));
            cpu.error = true;
            return;
        }

        switch (bitNibble) {
        case 0x4:
            bitValue = (regNibble > 0x07) ? (byte) 0x02 : 0x00;
            testBit = (regNibble > 0x07) ? "1" : "0";
            break;
        case 0x5:
            bitValue = (regNibble > 0x07) ? (byte) 0x08 : 0x04;
            testBit = (regNibble > 0x07) ? "3" : "2";
            break;
        case 0x6:
            bitValue = (regNibble > 0x07) ? (byte) 0x20 : 0x10;
            testBit = (regNibble > 0x07) ? "5" : "4";
            break;
        case 0x7:
            bitValue = (regNibble > 0x07) ? (byte) 0x80 : 0x40;
            testBit = (regNibble > 0x07) ? "7" : "6";
            break;
        default:
            System.err.println("Error parsing BIT instruction at: " + Utils.hex(cpu.PC.get()));
            cpu.error = true;
            return;
        }

        cpu.flags.setZero(!((regValue & (bitValue & 0xFF)) > 0));
        cpu.flags.setSubtract(false);
        cpu.flags.setHalfCarry(true);

        Utils.PrintInstruction("BIT " + testBit + ", " + regName, instruction, cpu.PC.get(), null, 0);

        cpu.PC.set(cpu.PC.get() + 2);

    }

    static void resetBit(CPU cpu, byte instruction) {
        switch (instruction) {
        case (byte) 0x87:
            cpu.AF.setHighByte((byte) (cpu.AF.getHighByte() & 0xFE));

            cpu.PC.set(cpu.PC.get() + 2);

            break;
        default:
            System.err.println("Unimplemented RES instruction!!!");
            break;
        }
    }
}
