/**
 * GUI.java
 * 
 * Displays a window for the program and
 * handles all screen draws and GUI buttons
 * and menus.
 */

package emulator;

import javax.swing.JFrame;

public class GUI extends JFrame {

	public GUI() 
	{
		Initialize();
	}
	
	private void Initialize()
	{
		add( new Screen() );
		setTitle("JavaBoy - Gameboy Emulator");
		setSize(144, 160);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
}
