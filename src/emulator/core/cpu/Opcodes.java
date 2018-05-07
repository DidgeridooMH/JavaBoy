package emulator.core.cpu;

import java.util.HashMap;
import java.util.Map;

public class Opcodes {
    private Map<Byte, Runnable> m_funcTable;

    Opcodes(CPU cpu) {
        m_funcTable = new HashMap<>();

        this.m_funcTable.put((byte)0x09, () -> Addition.addition_16(cpu, cpu.HL, cpu.BC, (byte)0x09));
        this.m_funcTable.put((byte)0x19, () -> Addition.addition_16(cpu, cpu.HL, cpu.DE, (byte)0x19));
        this.m_funcTable.put((byte)0x29, () -> Addition.addition_16(cpu, cpu.HL, cpu.HL, (byte)0x29));
        this.m_funcTable.put((byte)0x39, () -> Addition.addition_16(cpu, cpu.HL, cpu.SP, (byte)0x39));
        this.m_funcTable.put((byte)0x80, () ->
                Addition.addition_8(cpu, cpu.AF, new Register(cpu.BC.getHighByte(), "B"), (byte)0x80));
        this.m_funcTable.put((byte)0x81, () ->
                Addition.addition_8(cpu, cpu.AF, new Register(cpu.BC.getLowByte(), "C"), (byte)0x81));
        this.m_funcTable.put((byte)0x82, () ->
                Addition.addition_8(cpu, cpu.AF, new Register(cpu.DE.getHighByte(), "D"), (byte)0x82));
        this.m_funcTable.put((byte)0x83, () ->
                Addition.addition_8(cpu, cpu.AF, new Register(cpu.DE.getLowByte(), "E"), (byte)0x83));
        this.m_funcTable.put((byte)0x84, () ->
                Addition.addition_8(cpu, cpu.AF, new Register(cpu.HL.getHighByte(), "H"), (byte)0x84));
        this.m_funcTable.put((byte)0x85, () ->
                Addition.addition_8(cpu, cpu.AF, new Register(cpu.HL.getLowByte(), "L"), (byte)0x85));
        this.m_funcTable.put((byte)0x86, () -> {
            int memValue = cpu.memory.read(cpu.HL.get());
            Addition.addition_8(cpu, cpu.AF, new Register(memValue, "MEM"), (byte)0x86);
        });
        this.m_funcTable.put((byte)0x87, () ->
                Addition.addition_8(cpu, cpu.AF, new Register(cpu.AF.getHighByte(), "A"), (byte)0x87));
    }

    public void execute(byte instruction) {
        this.m_funcTable.get(instruction).run();
    }

    boolean isValid(byte instruction) {
        return this.m_funcTable.containsKey(instruction);
    }
}
