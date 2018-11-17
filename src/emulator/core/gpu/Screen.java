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

	private GPU gpu;

	public Screen(GPU gpu) {
	    this.gpu = gpu;
	    display = new BufferedImage(256, 256, BufferedImage.TYPE_INT_RGB);
        update();
	}

	public void update() {
        display.setRGB(0, 0, 256, 256, gpu.getBuffer(), 0, 256);
        validate();
        repaint();
    }

	@Override
	public void paintComponent(Graphics gfx) {
	    super.paintComponent(gfx);

	    this.update();

	    Graphics2D gfx2d = (Graphics2D) gfx.create();

	    gfx2d.drawImage(display, -(gpu.getScrollX()), -(gpu.getScrollY()), 256, 256, null);

	    gfx2d.dispose();
	}
}
