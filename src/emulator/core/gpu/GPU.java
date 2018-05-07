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
	
    private Memory memory = null;

    private GUI guiWindow = null;
	
	private Screen surface = null;

	private int currentPixel = 0;

	private byte lcdc, stat, coordY, lyCompare, windowY, windowX, bgPalette, objPalette0, objPalette1;
	
	/**
	 * Initializes a display window as well as
	 * sets the video related registers to default
	 * values.
	 * 
	 * @param memory A reference to the memory module
	 */
	public GPU(Memory memory) {
	    this.memory = memory;
		this.surface = new Screen();
        this.guiWindow = new GUI(surface);
        this.guiWindow.setVisible(true);
	}

	public void execute() {
	    drawSliver();

        currentPixel += 8;

	    if(currentPixel >= 256) {
	        coordY++;
	        currentPixel = 0;
        }

        if (((int)(coordY) & 0xFF) > 144) {
	        surface.update();
	        coordY = 0;
        }
    }

    private int getBackgroundTile() {
        int tileNumber = ((coordY / 8) * 32) + currentPixel / 8;
        return memory.read(0x9800 + tileNumber);
    }

    private void drawPixel(int x, int y, int colorID) {
        switch(colorID) {
            case 0:
                surface.pushPixel(x, y, 0xFF, 0xFF, 0xFF);
                break;
            case 1:
                surface.pushPixel(x, y, 0xAA, 0xAA, 0xAA);
                break;
            case 2:
                surface.pushPixel(x, y, 0x55, 0x55, 0x55);
                break;
            case 3:
                surface.pushPixel(x, y, 0x0, 0x0, 0x0);
                break;
        }
    }

    private void drawSliver() {
	    int tileNumber = getBackgroundTile();

        int sliver = coordY % 8;

        byte pixelData[] = {memory.read(0x8000 + (tileNumber * 0x10) + (sliver * 2)),
                        memory.read(0x8000 + (tileNumber * 0x10) + (sliver * 2) + 1)};

        for(int pixel = 7; pixel >= 0; pixel--) {
            int primary = ((byte) (pixelData[1] & 0xFF >> (pixel - 1))) & 0x02;
            int secondary = (byte) ((pixelData[0] & 0xFF) >> pixel) & 0x01;

            drawPixel(currentPixel + (7 - pixel), (int)(coordY) & 0xFF, primary | secondary);
        }
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
				return lcdc;
			case 0xFF41:
				return stat;
			case 0xFF42:
				return (byte)(surface.getScrollY());
			case 0xFF43:
				return (byte)(surface.getScrollX());
			case 0xFF44:
				return coordY;
			case 0xFF45:
				return lyCompare;
            case 0xFF47:
                return bgPalette;
            case 0xFF48:
                return objPalette0;
            case 0xFF49:
                return objPalette1;
			case 0xFF4A:
				return windowY;
			case 0xFF4B:
				return windowX;
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
				lcdc = in;
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
            case 0xFF47:
                bgPalette = in;
                break;
            case 0xFF48:
                objPalette0 = in;
                break;
            case 0xFF49:
                objPalette1 = in;
                break;
			case 0xFF4A:
				windowY = in;
				break;
			case 0xFF4B:
				windowX = in;
				break;
			default:
				System.err.println("GPU write fault: " +
									"[" + Utils.hex(in) + "] " +
									"at address " + Utils.hex(address)
				);
		}
	}
}
