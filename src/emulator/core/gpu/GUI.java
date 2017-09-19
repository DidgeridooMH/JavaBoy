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

import javax.swing.ImageIcon;
import javax.swing.JFrame;

/**
 * Creates a window for the program.
 * 
 * @author Daniel Simpkins
 *
 */
public class GUI extends JFrame {

	/**
	 * Initializes window settings and creates
	 * a surface to draw to.
	 * 
	 * @param surface A reference to the Screen object
	 */
	public GUI(Screen surface) {
		initialize(surface);
	}
	
	private void initialize(Screen surface) {
		add( surface );
		
		try {
		  ClassLoader loader = this.getClass().getClassLoader();
		  ImageIcon programIcon = new ImageIcon(loader.getResource("icon_256.png"));
		  setIconImage(programIcon.getImage());
		} catch (Exception e) {
		    e.printStackTrace();
		   System.exit(2);
		}
		
		setTitle("JavaBoy - Gameboy Emulator");
		setSize(160, 144);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
