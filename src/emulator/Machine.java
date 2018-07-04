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

import emulator.core.memory.Memory;

/**
 * Handles interactions between the emulation
 * components.
 * 
 * @author Daniel Simpkins
 *
 */
public class Machine {
	
	private CPU cpu;
	
	private GPU gpu;

	enum State {
	    STATE_ON,
	    STATE_OFF
    }

	private State state = Machine.State.STATE_ON;

    State getState() { return state; }

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

		gpu = new GPU(memory);

        memory.setGPU(gpu);

        cpu = new CPU(memory);
	}
	
	
	/**
	 * Proceeds one machine cycle.
	 */
	public void execute() {
		if(cpu.isError()) {
			state = State.STATE_OFF;
		} else {
		    cpu.execute();
		    for(int i = 0; i < 3; i++) {
                gpu.execute();
            }
        }
	}
	
}
