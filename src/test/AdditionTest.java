package test;

import emulator.core.cpu.Addition;
import emulator.core.cpu.CPU;
import emulator.core.cpu.Register;
import emulator.core.gpu.GPU;
import emulator.core.memory.Memory;
import org.junit.Assert;
import org.junit.Test;

public class AdditionTest {

    @Test
    public void Addition16_0_1() {
        Memory memory = new Memory("BIOS/bios.bin", "ROMS/pokemon.gb");
        GPU gpu = new GPU(memory);
        memory.setGPU(gpu);
        CPU cpu = new CPU(memory);

        Register input = new Register(0, "input");
        Register operand = new Register(1, "operand");

        Addition.addition_16(cpu, input, operand, (byte)0x09);

        Assert.assertEquals(input.get(), 1);
        Assert.assertEquals(operand.get(), 1);
    }

    @Test
    public void Addition16_22_54() {
        Memory memory = new Memory("BIOS/bios.bin", "ROMS/pokemon.gb");
        GPU gpu = new GPU(memory);
        memory.setGPU(gpu);
        CPU cpu = new CPU(memory);

        Register input = new Register(22, "input");
        Register operand = new Register(54, "operand");

        Addition.addition_16(cpu, input, operand, (byte)0x09);

        Assert.assertEquals(input.get(), 76);
        Assert.assertEquals(operand.get(), 54);
    }

    @Test
    public void Addition16_255_1() {
        Memory memory = new Memory("BIOS/bios.bin", "ROMS/pokemon.gb");
        GPU gpu = new GPU(memory);
        memory.setGPU(gpu);
        CPU cpu = new CPU(memory);

        Register input = new Register(0xFFFF, "input");
        Register operand = new Register(1, "operand");

        Addition.addition_16(cpu, input, operand, (byte)0x09);

        Assert.assertEquals(input.get(), 0x0000);
        Assert.assertEquals(operand.get(), 1);
    }


    @Test
    public void Addition8_0_1() {
        Memory memory = new Memory("BIOS/bios.bin", "ROMS/pokemon.gb");
        GPU gpu = new GPU(memory);
        memory.setGPU(gpu);
        CPU cpu = new CPU(memory);

        Register input = new Register(0, "input");
        Register operand = new Register(1, "operand");

        Addition.addition_8(cpu, input, operand, (byte)0x09);

        Assert.assertEquals(input.get(), 1);
        Assert.assertEquals(operand.get(), 1);
    }

    @Test
    public void Addition8_22_54() {
        Memory memory = new Memory("BIOS/bios.bin", "ROMS/pokemon.gb");
        GPU gpu = new GPU(memory);
        memory.setGPU(gpu);
        CPU cpu = new CPU(memory);

        Register input = new Register(22, "input");
        Register operand = new Register(54, "operand");

        Addition.addition_8(cpu, input, operand, (byte)0x09);

        Assert.assertEquals(input.get(), 76);
        Assert.assertEquals(operand.get(), 54);
    }

    @Test
    public void Addition8_255_1() {
        Memory memory = new Memory("BIOS/bios.bin", "ROMS/pokemon.gb");
        GPU gpu = new GPU(memory);
        memory.setGPU(gpu);
        CPU cpu = new CPU(memory);

        Register input = new Register(0xFFFF, "input");
        Register operand = new Register(1, "operand");

        Addition.addition_8(cpu, input, operand, (byte)0x09);

        Assert.assertEquals(input.get(), 0x0000);
        Assert.assertEquals(operand.get(), 1);
    }


}
