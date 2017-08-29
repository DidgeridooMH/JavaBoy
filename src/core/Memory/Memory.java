/*
 * Memory.java
 * 
 * This handles reading and writing to the
 * internal memory. It also handles loading
 * the rom and bios file on startup.
 * 
 */

package core.Memory;

import java.io.*;
import java.nio.file.*;

public class Memory {

	byte biosBank[];
	byte romBank[];
	private byte internalMemory[];

	public Memory(String bios, String rom)
	{
		try {
			Path romPath = Paths.get(rom);
			romBank = Files.readAllBytes(romPath);
		}
		catch(IOException e) {
			System.err.println("Caught exception while loading bios: " + e);
		}
		
		try {
			Path biosPath = Paths.get(bios);
			biosBank = Files.readAllBytes(biosPath);
		}
		catch(IOException e) {
			System.err.println("Caught exception while loading rom: " + e);
		}
		
		internalMemory = new byte[0x10000];
		
		for(int i = 0; i < 0xFF; i++)
			internalMemory[i] = biosBank[i];
		
		// Load home bank
		for(int i = 0x100; i < 0x4000; i++)
			internalMemory[i] = romBank[i];
		
		// Load switchable bank
		for(int i = 0x4000; i < 0x8000; i++)
			internalMemory[i] = romBank[i];
	}
	
	public void Write(byte in, int location)
	{
		internalMemory[(location & 0xFFFF)] = in;
		return;
	}
	
	public byte Read(int location)
	{
		return internalMemory[(location & 0xFFFF)];
	}
}
