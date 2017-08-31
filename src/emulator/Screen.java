/*
 * Screen.java
 * 
 * Draws pixel to the window
 */

package emulator;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class Screen extends JPanel {

	private void Draw(Graphics gfx)
	{
		Graphics2D gfx2d = (Graphics2D) gfx;
		gfx2d.drawString("Hello World!", 0, 0);
	}
	
	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Draw(g);
	}
	
}
