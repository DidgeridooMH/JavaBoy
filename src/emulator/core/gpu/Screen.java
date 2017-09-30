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

import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

import emulator.core.cpu.CPU;
import emulator.core.memory.Memory;

/**
 * Draws to the window.
 * 
 * @author Daniel Simpkins
 *
 */
public class Screen extends JPanel {

	private Memory memory = null;
	
	private CPU cpu = null;
	
	private static final int BG_TILE_RAM = 0x8000;
	
	private static final int BG_RAM = 0x9800;
	
	private int tileX = 0;
	
	private int tileY = 0;
	
	private int pixelX = 0;
	
	private int pixelY = 0;
	
	private int scrollX = 0;
	
	private int scrollY = 0;
	
	private int lcdc = 0;
	
	private boolean verticalBlank = true;
	
	public void setMemory(Memory memory) {
		this.memory = memory;
	}
	
	public void setScrollX(int x) {
		scrollX = x;
	}
	
	public int getScrollX() {
		return scrollX;
	}
	
	public void setScrollY(int y) {
		scrollY = y;
	}
	
	public int getScrollY() {
		return scrollY;
	}
	
	public void setLCDC(int lcdc) {
		this.lcdc = (lcdc & 0xFF);
	}
	
	public int getLCDC() {
		return (lcdc & 0xFF);
	}
	
	public boolean isVBlank() {
		return verticalBlank;
	}
	
	public void setVBlank(boolean verticalBlank) {
		this.verticalBlank = verticalBlank;
	}
	
	public void setCPU(CPU cpu) {
		this.cpu = cpu;
	}
	
	/**
	 * Draw the display if LCD is switched on
	 * 
	 * @param gfx Reference to the graphics context.
	 */
	private void draw(Graphics gfx) {
		Graphics2D gfx2d = (Graphics2D) gfx;
		tileX = 0;
		tileY = 0;
		if(((lcdc >> 7) & 0x01) > 0) {
			drawBackground(gfx2d);
		}
	}
	
	/**
	 * Draw the background layer.
	 * 
	 * @param gfx2d Reference to the 2D graphics context.
	 */
	private void drawBackground(Graphics2D gfx2d) {
		for(int i = 0; i < 1023; i++) {
			int tileID = memory.Read(BG_RAM + i);
			drawBackgroundTile(gfx2d, tileID);
		}
	}

	/**
	 * Draw a tile from background ram.
	 * 
	 * @param gfx2d Reference to the 2D graphics context.
	 * @param tileNum The ID of the tile in memory.
	 */
	private void drawBackgroundTile(Graphics2D gfx2d, int tileNum) {
		int tileAddress = tileNum * 0x10;
		pixelX = 0;
		pixelY = 0;
		
		if(memory != null) {
			for(int i = 0x0; i < 0x0F; i += 2) {
				byte pixel[] = { memory.Read(BG_TILE_RAM + i + tileAddress), memory.Read(BG_TILE_RAM + i + 1 + tileAddress) };
				int out = 0;
				for(int b = 7; b >= 0; b--) {
					int primary = ((byte)(pixel[1] & 0xFF >> (b-1))) & 0x02;
					int secondary = (byte) ((pixel[0] & 0xFF) >> b) & 0x01;
					out = primary | secondary;
					pushPixel(gfx2d, out);
				}
			}
		}
	
		forwardTile();
	}
	
	private void pushPixel(Graphics2D gfx2d, int out) {
		switch(out) {
		case 0:
			forward();
			break;
		case 1:
			gfx2d.drawLine(	(tileX * 8) + pixelX - (scrollX & 0xFF), 
							(tileY * 8) + pixelY - (scrollY & 0xFF), 
							(tileX * 8) + pixelX - (scrollX & 0xFF),  
							(tileY * 8) + pixelY - (scrollY & 0xFF));
			forward();
			break;
		case 2:
			gfx2d.drawLine(	(tileX * 8) + pixelX - (scrollX & 0xFF), 
							(tileY * 8) + pixelY - (scrollY & 0xFF), 
							(tileX * 8) + pixelX - (scrollX & 0xFF),  
							(tileY * 8) + pixelY - (scrollY & 0xFF));
			forward();
			break;
		case 3:
			gfx2d.drawLine(0, 0, 0, 0);
			forward();
			break;
		default:
			System.err.println("INVALID PIXEL AT (" + 0 + ", " + 0 + ")");
		}
	}

	/**
	 * Moves the coordinate of the pixel to paint within a tile.
	 */
	private void forward() {
		pixelX++;
		if(pixelX > 7) {
			pixelY++;
			pixelX = 0;
		}
	}
	
	/**
	 * Moves the coordinate of the tile to be painted.
	 */
	private void forwardTile() {
		tileX++;
		if(tileX > 31) {
			tileY++;
			tileX = 0;
		}
	}
	
	private void waitForCycles() {
		synchronized (this) {
			try {
				this.wait(500);
			} catch (InterruptedException e) {
				return;
			}
		}
	}
	
	@Override
	public void paintComponent(Graphics gfx) {
		super.paintComponent(gfx);
		super.invalidate();
		super.validate();
		super.repaint();
		draw(gfx);
		
		// Small hack to prevent the window from locking up
		if(!cpu.isError() ) {
			for(int i = 0; i < 155; i++) {
				memory.Write((byte) i, 0xFF44);
				//drawNextLine();
				waitForCycles();
			}
		}
		
	}
}
