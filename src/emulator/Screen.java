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

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import emulator.core.memory.Memory;

/**
 * Draws to the window.
 * 
 * @author Daniel Simpkins
 *
 */
public class Screen extends JPanel {
	
	/**
	 * Sets a reference to the systems
	 * memory.
	 * 
	 * @param memory Reference to CPU's memory
	 */
	public void setMemory(Memory memory) {
		
	}
	
	private void draw(Graphics gfx) {
		Graphics2D gfx2d = (Graphics2D) gfx;
		gfx2d.drawLine(0, 0, 0, 0);
	}
	
	@Override
	public void paintComponent(Graphics gfx) {
		super.paintComponent(gfx);
		draw(gfx);
	}
	
}
