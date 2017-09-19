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

package emulator.core.memory;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import emulator.core.gpu.GPU;

import java.nio.file.Files;

/**
 * Manages the 16K memory core
 * 
 * @author Daniel Simpkins
 *
 */
public class Memory {

	private byte biosBank[];
	
	private byte romBank[];
	
	private byte internalMemory[];
	
	private GPU gpu = null;

	/**
	 * Loads the bios into first bank, then loads
	 * rom into the rest.
	 * 
	 * @param bios Path to bios file.
	 * @param rom Path to rom file.
	 */
	public Memory(String bios, String rom) {
		try {
			Path romPath = Paths.get(rom);
			romBank = Files.readAllBytes(romPath);
		} catch(IOException e) {
			System.err.println("Caught exception while loading bios: " + e);
		}
		
		try {
			Path biosPath = Paths.get(bios);
			biosBank = Files.readAllBytes(biosPath);
		} catch(IOException e) {
			System.err.println("Caught exception while loading rom: " + e);
		}
		
		internalMemory = new byte[0x10000];
		
		// Loads bios
		for(int i = 0; i < 0xFF; i++)
			internalMemory[i] = biosBank[i];
		
		// Load home bank
		for(int i = 0x100; i < 0x4000; i++)
			internalMemory[i] = romBank[i];
		
		// Load switchable bank
		for(int i = 0x4000; i < 0x8000; i++)
			internalMemory[i] = romBank[i];
	}
	
	/**
	 * Writes a byte into memory.
	 * 
	 * @param in Byte to write.
	 * @param location Location in memory to write the byte.
	 */
	public synchronized void Write(byte in, int location) {
		if(location <= 0xFF4F && location >= 0xFF40) {
			gpu.writeRegister(in, location);
		}
		
		internalMemory[(location & 0xFFFF)] = in;
	}
	
	/**
	 * Reads a byte from memory.
	 * 
	 * @param location Location to read from.
	 * @return Byte from location in memory.
	 */
	public synchronized byte Read(int location) {
		return internalMemory[(location & 0xFFFF)];
	}
	
	public void setGPU(GPU gpu) {
		this.gpu = gpu;
	}

	/**
	 * Dumps the entire internal memory into a binary
	 * file.
	 * 
	 * @param filename Name of the file to write to.
	 * @return True if dump was successful.
	 */
	public boolean dump(String filename) {
		try {
			FileOutputStream out = new FileOutputStream(filename);
			out.write(internalMemory);
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
