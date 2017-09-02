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

import java.awt.EventQueue;

import emulator.core.cpu.CPU;

/**
 * Handles interactions between the emulation
 * components.
 * 
 * @author Daniel Simpkins
 *
 */
public class Machine {
	private CPU cpu;
	private Renderer windowT;
	
	/**
	 * 0 - Machine is off.
	 * 1 - Machine is on.
	 * 2 - Machine is paused.
	 */
	public int state = 1;
	
	/**
	 * Loads CPU module and sets up GUI
	 * window.
	 * 
	 * @param bios Path to BIOS file.
	 * @param rom Path to ROM file.
	 */
	public Machine(String bios, String rom) {
		System.out.println("Machine Initialized!");
		cpu = new CPU(bios, rom);
		
		windowT = new Renderer();
		
		EventQueue.invokeLater(windowT);
	}
	
	
	/**
	 * Proceeds one machine cycle.
	 */
	public void execute()
	{
		cpu.execute();
		
		if(cpu.isError()) {
			state = 0;
		}
	}
	
}
