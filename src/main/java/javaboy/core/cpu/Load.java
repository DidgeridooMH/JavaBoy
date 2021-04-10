package javaboy.core.cpu;

import javaboy.Utils;

import java.util.Map;

/**
 * Transfers memory from ram, registers, and 16-bit registers.
 * 
 * @author Daniel Simpkins
 *
 */
public class Load {

    public static void buildOpcodes(Map<Byte, Runnable> funcTable, CPU cpu) {
        funcTable.put((byte) 0x01, () -> {
            int d16 = (cpu.memory.read(cpu.PC.get() + 1) & 0xFF) | ((cpu.memory.read(cpu.PC.get() + 2) & 0xFF) << 8);
            Load.load16Bit(cpu, cpu.BC, "BC", d16, (byte) 0x01);
            cpu.PC.set(cpu.PC.get() + 2);
        });
        funcTable.put((byte) 0x11, () -> {
            int d16 = (cpu.memory.read(cpu.PC.get() + 1) & 0xFF) | ((cpu.memory.read(cpu.PC.get() + 2) & 0xFF) << 8);
            Load.load16Bit(cpu, cpu.DE, "DE", d16, (byte) 0x11);
            cpu.PC.set(cpu.PC.get() + 2);
        });
        funcTable.put((byte) 0x21, () -> {
            int d16 = (cpu.memory.read(cpu.PC.get() + 1) & 0xFF) | ((cpu.memory.read(cpu.PC.get() + 2) & 0xFF) << 8);
            Load.load16Bit(cpu, cpu.HL, "HL", d16, (byte) 0x21);
            cpu.PC.set(cpu.PC.get() + 2);
        });
        funcTable.put((byte) 0x31, () -> {
            int d16 = (cpu.memory.read(cpu.PC.get() + 1) & 0xFF) | ((cpu.memory.read(cpu.PC.get() + 2) & 0xFF) << 8);
            Load.load16Bit(cpu, cpu.SP, "SP", d16, (byte) 0x31);
            cpu.PC.set(cpu.PC.get() + 2);
        });

        funcTable.put((byte) 0x02, () -> {
            Register reg = new Register(0, "MEM");
            Load.load8Bit(cpu, reg, false, "(BC)", (byte) cpu.AF.getHighByte(), (byte) 0x02);
            cpu.memory.write((byte) reg.getLowByte(), cpu.BC.get());
        });
        funcTable.put((byte) 0x12, () -> {
            Register reg = new Register(0, "MEM");
            Load.load8Bit(cpu, reg, false, "(DE)", (byte) cpu.AF.getHighByte(), (byte) 0x12);
            cpu.memory.write((byte) reg.getLowByte(), cpu.DE.get());
        });
        funcTable.put((byte) 0x22, () -> {
            Register reg = new Register(0, "MEM");
            Load.load8Bit(cpu, reg, false, "(HL+)", (byte) cpu.AF.getHighByte(), (byte) 0x22);
            cpu.memory.write((byte) reg.getLowByte(), cpu.HL.get());
            cpu.HL.set(cpu.HL.get() + 1);
        });
        funcTable.put((byte) 0x32, () -> {
            Register reg = new Register(0, "MEM");
            Load.load8Bit(cpu, reg, false, "(HL-)", (byte) cpu.AF.getHighByte(), (byte) 0x32);
            cpu.memory.write((byte) reg.getLowByte(), cpu.HL.get());
            cpu.HL.set(cpu.HL.get() - 1);
        });

        funcTable.put((byte) 0x08, () -> {
            int address = (cpu.memory.read(cpu.PC.get() + 1) & 0xFF)
                    | ((cpu.memory.read(cpu.PC.get() + 2) & 0xFF) << 8);
            Register reg = new Register(0, "MEM");
            Load.load16Bit(cpu, reg, "(a16)", cpu.SP.get() & 0xFFFF, (byte) 0x08);
            cpu.memory.write((byte) reg.getLowByte(), address);
            cpu.memory.write((byte) reg.getHighByte(), address);
            cpu.PC.set(cpu.PC.get() + 2);
        });

        funcTable.put((byte) 0x06, () -> {
            byte d8 = cpu.memory.read(cpu.PC.get() + 1);
            Load.load8Bit(cpu, cpu.BC, true, "B", d8, (byte) 0x06);
            cpu.PC.set(cpu.PC.get() + 1);
        });
        funcTable.put((byte) 0x16, () -> {
            byte d8 = cpu.memory.read(cpu.PC.get() + 1);
            Load.load8Bit(cpu, cpu.DE, true, "D", d8, (byte) 0x16);
            cpu.PC.set(cpu.PC.get() + 1);
        });
        funcTable.put((byte) 0x26, () -> {
            byte d8 = cpu.memory.read(cpu.PC.get() + 1);
            Load.load8Bit(cpu, cpu.HL, true, "H", d8, (byte) 0x26);
            cpu.PC.set(cpu.PC.get() + 1);
        });
        funcTable.put((byte) 0x36, () -> {
            byte d8 = cpu.memory.read(cpu.PC.get() + 1);
            Register reg = new Register(d8, "mem");
            Load.load8Bit(cpu, reg, false, "(HL)", d8, (byte) 0x36);
            cpu.memory.write(d8, cpu.HL.get());
            cpu.PC.set(cpu.PC.get() + 1);
        });

        funcTable.put((byte) 0x0E, () -> {
            byte d8 = cpu.memory.read(cpu.PC.get() + 1);
            Load.load8Bit(cpu, cpu.BC, false, "C", d8, (byte) 0x0E);
            cpu.PC.set(cpu.PC.get() + 1);
        });
        funcTable.put((byte) 0x1E, () -> {
            byte d8 = cpu.memory.read(cpu.PC.get() + 1);
            Load.load8Bit(cpu, cpu.DE, false, "E", d8, (byte) 0x1E);
            cpu.PC.set(cpu.PC.get() + 1);
        });
        funcTable.put((byte) 0x2E, () -> {
            byte d8 = cpu.memory.read(cpu.PC.get() + 1);
            Load.load8Bit(cpu, cpu.HL, false, "L", d8, (byte) 0x2E);
            cpu.PC.set(cpu.PC.get() + 1);
        });
        funcTable.put((byte) 0x3E, () -> {
            byte d8 = cpu.memory.read(cpu.PC.get() + 1);
            Load.load8Bit(cpu, cpu.AF, true, "A", d8, (byte) 0x3E);
            cpu.PC.set(cpu.PC.get() + 1);
        });

        funcTable.put((byte) 0x0A, () -> {
            Load.load8Bit(cpu, cpu.AF, true, "A", cpu.memory.read(cpu.BC.get()), (byte) 0x0A);
        });
        funcTable.put((byte) 0x1A, () -> {
            Load.load8Bit(cpu, cpu.AF, true, "A", cpu.memory.read(cpu.DE.get()), (byte) 0x1A);
        });
        funcTable.put((byte) 0x2A, () -> {
            Load.load8Bit(cpu, cpu.AF, true, "A", cpu.memory.read(cpu.HL.get()), (byte) 0x2A);
            cpu.HL.set(cpu.HL.get() + 1);
        });
        funcTable.put((byte) 0x3A, () -> {
            Load.load8Bit(cpu, cpu.AF, true, "A", cpu.memory.read(cpu.HL.get()), (byte) 0x3A);
            cpu.HL.set(cpu.HL.get() - 1);
        });

        funcTable.put((byte) 0x40, () -> {
            Load.load8Bit(cpu, cpu.BC, true, "B", (byte) cpu.BC.getHighByte(), (byte) 0x40);
        });
        funcTable.put((byte) 0x41, () -> {
            Load.load8Bit(cpu, cpu.BC, true, "B", (byte) cpu.BC.getLowByte(), (byte) 0x41);
        });
        funcTable.put((byte) 0x42, () -> {
            Load.load8Bit(cpu, cpu.BC, true, "B", (byte) cpu.DE.getHighByte(), (byte) 0x42);
        });
        funcTable.put((byte) 0x43, () -> {
            Load.load8Bit(cpu, cpu.BC, true, "B", (byte) cpu.DE.getLowByte(), (byte) 0x43);
        });
        funcTable.put((byte) 0x44, () -> {
            Load.load8Bit(cpu, cpu.BC, true, "B", (byte) cpu.HL.getHighByte(), (byte) 0x44);
        });
        funcTable.put((byte) 0x45, () -> {
            Load.load8Bit(cpu, cpu.BC, true, "B", (byte) cpu.HL.getLowByte(), (byte) 0x45);
        });
        funcTable.put((byte) 0x46, () -> {
            Load.load8Bit(cpu, cpu.BC, true, "B", cpu.memory.read(cpu.HL.get()), (byte) 0x46);
        });
        funcTable.put((byte) 0x47, () -> {
            Load.load8Bit(cpu, cpu.BC, true, "B", (byte) cpu.AF.getHighByte(), (byte) 0x47);
        });

        funcTable.put((byte) 0x48, () -> {
            Load.load8Bit(cpu, cpu.BC, false, "C", (byte) cpu.BC.getHighByte(), (byte) 0x48);
        });
        funcTable.put((byte) 0x49, () -> {
            Load.load8Bit(cpu, cpu.BC, false, "C", (byte) cpu.BC.getLowByte(), (byte) 0x49);
        });
        funcTable.put((byte) 0x4A, () -> {
            Load.load8Bit(cpu, cpu.BC, false, "C", (byte) cpu.DE.getHighByte(), (byte) 0x4A);
        });
        funcTable.put((byte) 0x4B, () -> {
            Load.load8Bit(cpu, cpu.BC, false, "C", (byte) cpu.DE.getLowByte(), (byte) 0x4B);
        });
        funcTable.put((byte) 0x4C, () -> {
            Load.load8Bit(cpu, cpu.BC, false, "C", (byte) cpu.HL.getHighByte(), (byte) 0x4C);
        });
        funcTable.put((byte) 0x4D, () -> {
            Load.load8Bit(cpu, cpu.BC, false, "C", (byte) cpu.HL.getLowByte(), (byte) 0x4D);
        });
        funcTable.put((byte) 0x4E, () -> {
            Load.load8Bit(cpu, cpu.BC, false, "C", cpu.memory.read(cpu.HL.get()), (byte) 0x4E);
        });
        funcTable.put((byte) 0x4F, () -> {
            Load.load8Bit(cpu, cpu.BC, false, "C", (byte) cpu.AF.getHighByte(), (byte) 0x4F);
        });

        funcTable.put((byte) 0x50, () -> {
            Load.load8Bit(cpu, cpu.DE, true, "D", (byte) cpu.BC.getHighByte(), (byte) 0x50);
        });
        funcTable.put((byte) 0x51, () -> {
            Load.load8Bit(cpu, cpu.DE, true, "D", (byte) cpu.BC.getLowByte(), (byte) 0x51);
        });
        funcTable.put((byte) 0x52, () -> {
            Load.load8Bit(cpu, cpu.DE, true, "D", (byte) cpu.DE.getHighByte(), (byte) 0x52);
        });
        funcTable.put((byte) 0x53, () -> {
            Load.load8Bit(cpu, cpu.DE, true, "D", (byte) cpu.DE.getLowByte(), (byte) 0x53);
        });
        funcTable.put((byte) 0x54, () -> {
            Load.load8Bit(cpu, cpu.DE, true, "D", (byte) cpu.HL.getHighByte(), (byte) 0x54);
        });
        funcTable.put((byte) 0x55, () -> {
            Load.load8Bit(cpu, cpu.DE, true, "D", (byte) cpu.HL.getLowByte(), (byte) 0x55);
        });
        funcTable.put((byte) 0x56, () -> {
            Load.load8Bit(cpu, cpu.DE, true, "D", cpu.memory.read(cpu.HL.get()), (byte) 0x56);
        });
        funcTable.put((byte) 0x57, () -> {
            Load.load8Bit(cpu, cpu.DE, true, "D", (byte) cpu.AF.getHighByte(), (byte) 0x57);
        });

        funcTable.put((byte) 0x58, () -> {
            Load.load8Bit(cpu, cpu.DE, false, "E", (byte) cpu.BC.getHighByte(), (byte) 0x58);
        });
        funcTable.put((byte) 0x59, () -> {
            Load.load8Bit(cpu, cpu.DE, false, "E", (byte) cpu.BC.getLowByte(), (byte) 0x59);
        });
        funcTable.put((byte) 0x5A, () -> {
            Load.load8Bit(cpu, cpu.DE, false, "E", (byte) cpu.DE.getHighByte(), (byte) 0x5A);
        });
        funcTable.put((byte) 0x5B, () -> {
            Load.load8Bit(cpu, cpu.DE, false, "E", (byte) cpu.DE.getLowByte(), (byte) 0x5B);
        });
        funcTable.put((byte) 0x5C, () -> {
            Load.load8Bit(cpu, cpu.DE, false, "E", (byte) cpu.HL.getHighByte(), (byte) 0x5C);
        });
        funcTable.put((byte) 0x5D, () -> {
            Load.load8Bit(cpu, cpu.DE, false, "E", (byte) cpu.HL.getLowByte(), (byte) 0x5D);
        });
        funcTable.put((byte) 0x5E, () -> {
            Load.load8Bit(cpu, cpu.DE, false, "E", cpu.memory.read(cpu.HL.get()), (byte) 0x5E);
        });
        funcTable.put((byte) 0x5F, () -> {
            Load.load8Bit(cpu, cpu.DE, false, "E", (byte) cpu.AF.getHighByte(), (byte) 0x5F);
        });

        funcTable.put((byte) 0x60, () -> {
            Load.load8Bit(cpu, cpu.HL, true, "H", (byte) cpu.BC.getHighByte(), (byte) 0x60);
        });
        funcTable.put((byte) 0x61, () -> {
            Load.load8Bit(cpu, cpu.HL, true, "H", (byte) cpu.BC.getLowByte(), (byte) 0x61);
        });
        funcTable.put((byte) 0x62, () -> {
            Load.load8Bit(cpu, cpu.HL, true, "H", (byte) cpu.DE.getHighByte(), (byte) 0x62);
        });
        funcTable.put((byte) 0x63, () -> {
            Load.load8Bit(cpu, cpu.HL, true, "H", (byte) cpu.DE.getLowByte(), (byte) 0x63);
        });
        funcTable.put((byte) 0x64, () -> {
            Load.load8Bit(cpu, cpu.HL, true, "H", (byte) cpu.HL.getHighByte(), (byte) 0x64);
        });
        funcTable.put((byte) 0x65, () -> {
            Load.load8Bit(cpu, cpu.HL, true, "H", (byte) cpu.HL.getLowByte(), (byte) 0x65);
        });
        funcTable.put((byte) 0x66, () -> {
            Load.load8Bit(cpu, cpu.HL, true, "H", cpu.memory.read(cpu.HL.get()), (byte) 0x66);
        });
        funcTable.put((byte) 0x67, () -> {
            Load.load8Bit(cpu, cpu.HL, true, "H", (byte) cpu.AF.getHighByte(), (byte) 0x67);
        });

        funcTable.put((byte) 0x68, () -> {
            Load.load8Bit(cpu, cpu.HL, false, "L", (byte) cpu.BC.getHighByte(), (byte) 0x68);
        });
        funcTable.put((byte) 0x69, () -> {
            Load.load8Bit(cpu, cpu.HL, false, "L", (byte) cpu.BC.getLowByte(), (byte) 0x69);
        });
        funcTable.put((byte) 0x6A, () -> {
            Load.load8Bit(cpu, cpu.HL, false, "L", (byte) cpu.DE.getHighByte(), (byte) 0x6A);
        });
        funcTable.put((byte) 0x6B, () -> {
            Load.load8Bit(cpu, cpu.HL, false, "L", (byte) cpu.DE.getLowByte(), (byte) 0x6B);
        });
        funcTable.put((byte) 0x6C, () -> {
            Load.load8Bit(cpu, cpu.HL, false, "L", (byte) cpu.HL.getHighByte(), (byte) 0x6C);
        });
        funcTable.put((byte) 0x6D, () -> {
            Load.load8Bit(cpu, cpu.HL, false, "L", (byte) cpu.HL.getLowByte(), (byte) 0x6D);
        });
        funcTable.put((byte) 0x6E, () -> {
            Load.load8Bit(cpu, cpu.HL, false, "L", cpu.memory.read(cpu.HL.get()), (byte) 0x6E);
        });
        funcTable.put((byte) 0x6F, () -> {
            Load.load8Bit(cpu, cpu.HL, false, "L", (byte) cpu.AF.getHighByte(), (byte) 0x6F);
        });

        funcTable.put((byte) 0x70, () -> {
            Register reg = new Register(0, "mem");
            Load.load8Bit(cpu, reg, false, "(HL)", (byte) cpu.BC.getHighByte(), (byte) 0x70);
            cpu.memory.write((byte) reg.getLowByte(), cpu.HL.get());
            cpu.PC.set(cpu.PC.get() + 1);
        });
        funcTable.put((byte) 0x71, () -> {
            Register reg = new Register(0, "mem");
            Load.load8Bit(cpu, reg, false, "(HL)", (byte) cpu.BC.getLowByte(), (byte) 0x71);
            cpu.memory.write((byte) reg.getLowByte(), cpu.HL.get());
            cpu.PC.set(cpu.PC.get() + 1);
        });
        funcTable.put((byte) 0x72, () -> {
            Register reg = new Register(0, "mem");
            Load.load8Bit(cpu, reg, false, "(HL)", (byte) cpu.DE.getHighByte(), (byte) 0x72);
            cpu.memory.write((byte) reg.getLowByte(), cpu.HL.get());
            cpu.PC.set(cpu.PC.get() + 1);
        });
        funcTable.put((byte) 0x73, () -> {
            Register reg = new Register(0, "mem");
            Load.load8Bit(cpu, reg, false, "(HL)", (byte) cpu.DE.getLowByte(), (byte) 0x73);
            cpu.memory.write((byte) reg.getLowByte(), cpu.HL.get());
            cpu.PC.set(cpu.PC.get() + 1);
        });
        funcTable.put((byte) 0x74, () -> {
            Register reg = new Register(0, "mem");
            Load.load8Bit(cpu, reg, false, "(HL)", (byte) cpu.HL.getHighByte(), (byte) 0x74);
            cpu.memory.write((byte) reg.getLowByte(), cpu.HL.get());
            cpu.PC.set(cpu.PC.get() + 1);
        });
        funcTable.put((byte) 0x75, () -> {
            Register reg = new Register(0, "mem");
            Load.load8Bit(cpu, reg, false, "(HL)", (byte) cpu.HL.getLowByte(), (byte) 0x75);
            cpu.memory.write((byte) reg.getLowByte(), cpu.HL.get());
            cpu.PC.set(cpu.PC.get() + 1);
        });
        funcTable.put((byte) 0x76, () -> {
            cpu.error = true;
        });
        funcTable.put((byte) 0x77, () -> {
            Register reg = new Register(0, "mem");
            Load.load8Bit(cpu, reg, false, "(HL)", (byte) cpu.AF.getHighByte(), (byte) 0x77);
            cpu.memory.write((byte) reg.getLowByte(), cpu.HL.get());
            cpu.PC.set(cpu.PC.get() + 1);
        });

        funcTable.put((byte) 0x78, () -> {
            Load.load8Bit(cpu, cpu.AF, true, "A", (byte) cpu.BC.getHighByte(), (byte) 0x78);
        });
        funcTable.put((byte) 0x79, () -> {
            Load.load8Bit(cpu, cpu.AF, true, "A", (byte) cpu.BC.getLowByte(), (byte) 0x79);
        });
        funcTable.put((byte) 0x7A, () -> {
            Load.load8Bit(cpu, cpu.AF, true, "A", (byte) cpu.DE.getHighByte(), (byte) 0x7A);
        });
        funcTable.put((byte) 0x7B, () -> {
            Load.load8Bit(cpu, cpu.AF, true, "A", (byte) cpu.DE.getLowByte(), (byte) 0x7B);
        });
        funcTable.put((byte) 0x7C, () -> {
            Load.load8Bit(cpu, cpu.AF, true, "A", (byte) cpu.HL.getHighByte(), (byte) 0x7C);
        });
        funcTable.put((byte) 0x7D, () -> {
            Load.load8Bit(cpu, cpu.AF, true, "A", (byte) cpu.HL.getLowByte(), (byte) 0x7D);
        });
        funcTable.put((byte) 0x7E, () -> {
            Load.load8Bit(cpu, cpu.AF, true, "A", cpu.memory.read(cpu.HL.get()), (byte) 0x7E);
        });
        funcTable.put((byte) 0x7F, () -> {
            Load.load8Bit(cpu, cpu.AF, true, "A", (byte) cpu.AF.getHighByte(), (byte) 0x7F);
        });

        funcTable.put((byte) 0xE0, () -> {
            int offset = cpu.memory.read(cpu.PC.get() + 1) & 0xFF;
            Register reg = new Register(0, "mem");
            Load.load8Bit(cpu, reg, false, "(a8)", (byte) cpu.AF.getHighByte(), (byte) 0xE0);
            cpu.memory.write((byte) reg.getLowByte(), 0xFF00 + offset);
            cpu.PC.set(cpu.PC.get() + 1);
        });

        funcTable.put((byte) 0xE2, () -> {
            int offset = (byte) cpu.BC.getLowByte();
            Register reg = new Register(0, "mem");
            Load.load8Bit(cpu, reg, false, "(a8)", (byte) cpu.AF.getHighByte(), (byte) 0xE2);
            cpu.memory.write((byte) reg.getLowByte(), 0xFF00 + offset);
        });

        funcTable.put((byte) 0xEA, () -> {
            int address = (cpu.memory.read(cpu.PC.get() + 1) & 0xFF)
                    | ((cpu.memory.read(cpu.PC.get() + 2) & 0xFF) << 8);
            Register reg = new Register(cpu.memory.read(address), "MEM");
            Load.load8Bit(cpu, reg, false, "(a16)", (byte) cpu.AF.getHighByte(), (byte) 0xEA);
            cpu.memory.write((byte) reg.getLowByte(), address);
            cpu.PC.set(cpu.PC.get() + 2);
        });

        funcTable.put((byte) 0xF0, () -> {
            int offset = cpu.memory.read(cpu.PC.get() + 1) & 0xFF;
            Load.load8Bit(cpu, cpu.AF, true, "A", cpu.memory.read(0xFF00 + offset), (byte) 0xF0);
            cpu.PC.set(cpu.PC.get() + 1);
        });
        funcTable.put((byte) 0xF2, () -> {
            int offset = (byte) cpu.BC.getLowByte();
            Load.load8Bit(cpu, cpu.AF, true, "A", cpu.memory.read(0xFF00 + offset), (byte) 0xF2);
        });

        funcTable.put((byte) 0xFA, () -> {
            int address = (cpu.memory.read(cpu.PC.get() + 1) & 0xFF)
                    | ((cpu.memory.read(cpu.PC.get() + 2) & 0xFF) << 8);
            Load.load8Bit(cpu, cpu.AF, true, "A", cpu.memory.read(address), (byte) 0xFA);
            cpu.PC.set(cpu.PC.get() + 2);
        });

        funcTable.put((byte) 0xF8, () -> {
            int initial = cpu.SP.get() & 0xFFFF;
            int result = initial + cpu.memory.read(cpu.PC.get() + 1);
            result &= 0xFFFF;
            cpu.HL.set(result);
            cpu.flags.setZero(false);
            cpu.flags.setSign(false);
            cpu.flags.setFlags(initial, result, false, Flags.HALFC | Flags.CARRY);
            cpu.PC.set(cpu.PC.get() + 2);
        });

        funcTable.put((byte) 0xF9, () -> {
            Load.load16Bit(cpu, cpu.SP, "SP", cpu.HL.get(), (byte) 0xF9);
        });
    }

    private static void loadLogInstruction(CPU cpu, String inputStr, int input, byte instruction) {
        Utils.PrintInstruction("LD " + inputStr + " " + input, instruction, cpu.PC.get(), null, 0);
    }

    private static void load8Bit(CPU cpu, Register reg, boolean highLow, String inputStr, byte input,
            byte instruction) {
        if (highLow) {
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
}
