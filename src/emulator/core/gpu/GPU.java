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

import java.text.DecimalFormat;
import java.util.Arrays;

/**
 * Handles writing pixels to the main display. Also,
 * handles all the LCD related registers by bypassing
 * the memory.
 * 
 * @author Daniel Simpkins
 *
 */
public class GPU {
    
    private Memory memory;

    private GUI gui;

    private int currentPixel = 0;

    private int[] backBuffer;
    private int[] frontBuffer;

    private byte lcdc, stat, coordY, lyCompare, windowY, windowX, bgPalette, objPalette0, objPalette1, scrollY, scrollX;

    private long lastTime;

    private int frameUpdateDelay = 0;

    /**
     * Initializes a display window as well as
     * sets the video related registers to default
     * values.
     * 
     * @param memory A reference to the memory module
     */
    public GPU(Memory memory) {
        this.memory = memory;
        this.frontBuffer = new int[256 * 256];
        Arrays.fill(this.frontBuffer, 0xFFFFFF);
        this.backBuffer = new int[256 * 256];
        Arrays.fill(this.backBuffer, 0xFFFFFF);
        this.lastTime = System.nanoTime();
    }

    public void bindGUI(GUI gui) {
        this.gui = gui;
    }

    private void updateFPS() {
        if(this.gui != null) {
            long currentTime = System.nanoTime();
            double secTime = (currentTime - lastTime) / 100000000.0;
            double fps = 1.0 / secTime;
            DecimalFormat fpsFormat = new DecimalFormat("##.00");
            if(frameUpdateDelay > 100) {
                this.gui.setTitle("JavaBoy " + fpsFormat.format(fps) + "fps");
                frameUpdateDelay = 0;
            } else {
                frameUpdateDelay++;
            }
            lastTime = currentTime;
        }
    }

    private void swapBuffers() {
        int[] temp = backBuffer;
        this.backBuffer = this.frontBuffer;
        this.frontBuffer = temp;
    }

    public byte getScrollX() {
        return this.scrollX;
    }

    public byte getScrollY() {
        return this.scrollY;
    }

    public int[] getBuffer() {
        return this.frontBuffer;
    }

    public void execute() {
        drawSliver();

        currentPixel += 8;

        if(currentPixel >= 256) {
            currentPixel = 0;
            if (((int)(coordY) & 0xFF) == 0xFF) {
                swapBuffers();
                updateFPS();
            }
            coordY++;
        }
    }

    public void pushPixel(int x, int y, int r, int g, int b) {
        int rgb = (r & 0xFF) << 16;
        rgb |= (g & 0xFF) << 8;
        rgb |= b & 0xFF;
        backBuffer[x + (y * 256)] = rgb;
    }

    private int getBackgroundTile() {
        int tileNumber = ((coordY / 8) * 32) + currentPixel / 8;
        return memory.read(0x9800 + tileNumber);
    }

    private void drawPixel(int x, int y, int colorID) {
        switch(colorID) {
            case 0:
                this.pushPixel(x, y, 0x9b, 0xbc, 0x0f);
                break;
            case 1:
                this.pushPixel(x, y, 0x8b, 0xac, 0x0f);
                break;
            case 2:
                this.pushPixel(x, y, 0x30, 0x62, 0x30);
                break;
            case 3:
                this.pushPixel(x, y, 0x0f, 0x38, 0x0f);
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
                return this.scrollY;
            case 0xFF43:
                return this.scrollX;
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
                this.scrollY = in;
                break;
            case 0xFF43:
                this.scrollX = in;
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
