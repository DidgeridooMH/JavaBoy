/*
 * 
 * MIT License
 * 
 * Copyright (c) 2017 Daniel Simpkins
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 * 
 */

package emulator;

import emulator.core.cpu.CPU;

import emulator.core.gpu.GPU;

import emulator.core.gpu.GUI;
import emulator.core.gpu.Screen;
import emulator.core.memory.Memory;

import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Handles interactions between the emulation
 * components.
 * 
 * @author Daniel Simpkins
 *
 */
public class Machine extends Thread {
    
    private CPU cpu;
    
    private GPU gpu;

    private GUI gui;

    /**
     * Loads CPU module and sets up GUI
     * window.
     * 
     * @param bios Path to BIOS file.
     * @param rom Path to ROM file.
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
        while(!this.cpu.isError()) {
            cpu.execute();
            for (int i = 0; i < 3; i++) {
                gpu.execute();
            }
        }
    }
}
