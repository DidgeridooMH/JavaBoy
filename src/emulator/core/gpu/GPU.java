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

package emulator.core.gpu;

import emulator.Utils;
import emulator.core.memory.Memory;

/**
 * Handles writing pixels to the main display. Also,
 * handles all the LCD related registers by bypassing
 * the memory.
 * 
 * @author Daniel Simpkins
 *
 */
public class GPU {
	
	private Renderer graphicsT = null;
	
	private Screen surface = null;
	
	private byte stat;
	
	private byte coordY;
	
	private byte lyCompare;
	
	private byte windowY;
	private byte windowX;
	
	private byte bgPalette;
	private byte objPalette0;
	private byte objPalette1;
	
	/**
	 * Initializes a display window as well as
	 * sets the video related registers to default
	 * values.
	 * 
	 * @param memory A reference to the memory module
	 */
	public GPU(Memory memory) {
		surface = new Screen();
		surface.setMemory(memory);
		graphicsT = new Renderer(surface);
		graphicsT.setName("GUI Render");
		
		surface.setLCDC(memory.Read(0xFF40));
		stat = memory.Read(0xFF41);
		surface.setScrollY(memory.Read(0xFF42));
		surface.setScrollX(memory.Read(0xFF43));
		coordY = memory.Read(0xFF44);
		lyCompare = memory.Read(0xFF45);
		windowY = memory.Read(0xFF4A);
		windowX = memory.Read(0xFF4B);
		bgPalette = memory.Read(0xFF47);
		objPalette0 = memory.Read(0xFF48);
		objPalette1 = memory.Read(0xFF49);
	}
	
	/**
	 * Starts the window thread
	 */
	public void start() {
		graphicsT.start();
	}
	
	/**
	 * Pauses the window thread
	 */
	public void stop() {
	}
	
	public boolean isVBlank() {
		return surface.isVBlank();
	}
	
	public void setVBlank(boolean verticalBlank) {
		surface.setVBlank(verticalBlank);
	}
	
	/**
	 * Retrieves the value of a hardware register
	 * at a specified address. Returns a 0xFF if there
	 * was an error.
	 * 
	 * @param address Address of the hardware register
	 * @return Value of the hardware register
	 */
	public byte readRegister(int address) {
		switch(address) {
			case 0xFF40:
				return (byte) surface.getLCDC();
			case 0xFF41:
				return stat;
			case 0xFF42:
				return (byte) surface.getScrollY();
			case 0xFF43:
				return (byte) surface.getScrollX();
			case 0xFF44:
				return coordY;
			case 0xFF45:
				return lyCompare;
			case 0xFF4A:
				return windowY;
			case 0xFF4B:
				return windowX;
			case 0xFF47:
				return bgPalette;
			case 0xFF48:
				return objPalette0;
			case 0xFF49:
				return objPalette1;
			default:
				System.err.println("GPU read fault: " +
									"at address " + Utils.hex(address)
				);
				return (byte) 0xFF;
		}
	}
	
	/**
	 * Writes to a hardware register within the LCD
	 * video module.
	 * 
	 * @param in 8-bit byte to write to the register
	 * @param address Address of the hardware register
	 */
	public void writeRegister(byte in, int address) {
		switch(address) {
			case 0xFF40:
				surface.setLCDC(in);
				break;
			case 0xFF41:
				stat = in;
				break;
			case 0xFF42:
				surface.setScrollY(in);
				break;
			case 0xFF43:
				surface.setScrollX(in);
				break;
			case 0xFF44:
				coordY = in;
				break;
			case 0xFF45:
				lyCompare = in;
				break;
			case 0xFF4A:
				windowY = in;
				break;
			case 0xFF4B:
				windowX = in;
				break;
			case 0xFF47:
				bgPalette = in;
				break;
			case 0xFF48:
				objPalette0 = in;
				break;
			case 0xFF49:
				objPalette1 = in;
				break;
			default:
				System.err.println("GPU write fault: " +
									"[" + Utils.hex(in) + "] " +
									"at address " + Utils.hex(address)
				);
				return;
		}
	}
	
}
