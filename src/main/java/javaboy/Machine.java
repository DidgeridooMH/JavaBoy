package javaboy;

import javaboy.core.cpu.CPU;

import javaboy.core.gpu.GPU;

import javaboy.core.gpu.GUI;
import javaboy.core.gpu.Screen;
import javaboy.core.memory.Memory;

/**
 * Handles interactions between the emulation components.
 * 
 * @author Daniel Simpkins
 *
 */
public class Machine extends Thread {

    private CPU cpu;

    private GPU gpu;

    private GUI gui;

    /**
     * Loads CPU module and sets up GUI window.
     * 
     * @param bios Path to BIOS file.
     * @param rom  Path to ROM file.
     */
    public Machine(String bios, String rom) {
        System.out.println("Machine Initialized!");

        Memory memory = new Memory(bios, rom);

        this.gpu = new GPU(memory);

        memory.setGPU(gpu);

        this.cpu = new CPU(memory);

        this.gui = new GUI(new Screen(gpu));
        this.gui.setVisible(true);

        this.gpu.bindGUI(this.gui);
    }

    @Override
    public void run() {
        while (!this.cpu.isError()) {
            cpu.execute();
            for (int i = 0; i < 3; i++) {
                gpu.execute();
            }
        }
    }
}
