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

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.*;

import emulator.core.memory.Memory;

/**
 * Draws to the window.
 * 
 * @author Daniel Simpkins
 *
 */
public class Screen extends JPanel {

	private BufferedImage display;

	private int[] pixelBuffer;

	private int scrollX = 0;

	private int scrollY = 0;

	public Screen() {
	    display = new BufferedImage(256, 256, BufferedImage.TYPE_INT_RGB);
	    pixelBuffer = new int[256 * 256];

	    for (int y = 0; y < 256; y++){
	        for (int x = 0; x < 256; x++) {
	            pushPixel(x, y, 0xFF, 0xFF, 0xFF);
            }
        }

        update();
	}

	public void setScrollX(int x) { scrollX = x; }

    public void setScrollY(int y) { scrollY = y; }

    public int getScrollX() { return scrollX; }

    public int getScrollY() { return scrollY; }

	public void pushPixel(int x, int y, int r, int g, int b) {
	    int rgb = (r & 0xFF) << 16;
	    rgb |= (g & 0xFF) << 8;
	    rgb |= b & 0xFF;
	    pixelBuffer[x + (y * 256)] = rgb;
    }

	public void update() {
        display.setRGB(0, 0, 256, 256, pixelBuffer, 0, 256);
        validate();
        repaint();
    }

	@Override
	public void paintComponent(Graphics gfx) {
	    super.paintComponent(gfx);

	    Graphics2D gfx2d = (Graphics2D) gfx.create();

	    gfx2d.drawImage(display, -(scrollX), -(scrollY) + 75, 256, 256, null);

	    gfx2d.dispose();
	}
}
