package javaboy.core.memory;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import javaboy.core.gpu.GPU;

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
     * Loads the bios into first bank, then loads rom into the rest.
     * 
     * @param bios Path to bios file.
     * @param rom  Path to rom file.
     */
    public Memory(String bios, String rom) {
        try {
            Path romPath = Paths.get(rom);
            romBank = Files.readAllBytes(romPath);
        } catch (IOException e) {
            System.err.println("Caught exception while loading bios: " + e);
            System.exit(2);
        }

        try {
            Path biosPath = Paths.get(bios);
            biosBank = Files.readAllBytes(biosPath);
        } catch (IOException e) {
            System.err.println("Caught exception while loading rom: " + e);
            System.exit(2);
        }

        internalMemory = new byte[0x10000];

        // Loads bios
        for (int i = 0; i < 0xFF; i++) {
            // TODO: clean code to eliminate DMG rom
            write(biosBank[i], i);
        }

        // Load home bank
        for (int i = 0x100; i < 0x8000; i++) {
            write(romBank[i], i);
        }
    }

    /**
     * Writes a byte into memory.
     * 
     * @param in       Byte to write.
     * @param location Location in memory to write the byte.
     */
    public void write(byte in, int location) {
        if (location >= 0xFF40 && location <= 0xFF49) {
            gpu.writeRegister(in, location);
        } else {
            internalMemory[(location & 0xFFFF)] = in;
        }
    }

    /**
     * Reads a byte from memory.
     * 
     * @param location Location to read from.
     * @return Byte from location in memory.
     */
    public byte read(int location) {
        byte out;

        if (location >= 0xFF40 && location <= 0xFF49) {
            out = gpu.readRegister(location);
        } else {
            out = internalMemory[(location & 0xFFFF)];
        }

        return out;
    }

    public void setGPU(GPU gpu) {
        this.gpu = gpu;
    }

    /**
     * Dumps the entire internal memory into a binary file.
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
