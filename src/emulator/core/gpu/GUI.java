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

import java.awt.Dimension;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

/**
 * Creates a window for the program.
 * 
 * @author Daniel Simpkins
 *
 */
public class GUI extends JFrame {

    private static final String IMAGE_256 = "icon_256.png";

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
          URL iconURL = loader.getResource(IMAGE_256);
          if(iconURL != null) {
              ImageIcon programIcon = new ImageIcon(iconURL);
              setIconImage(programIcon.getImage());
          } else {
              System.out.println("Unable to load image " + IMAGE_256);
          }
        } catch (Exception e) {
            System.out.println("Unable to load image " + IMAGE_256);
        }

        setTitle("JavaBoy - Gameboy Emulator");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        surface.setPreferredSize(new Dimension(160, 144));
        setResizable(false);
        setLocationRelativeTo(null);

        pack();
    }
}
