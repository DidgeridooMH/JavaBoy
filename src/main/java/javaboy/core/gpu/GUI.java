package javaboy.core.gpu;

import java.awt.Dimension;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

/**
 * Creates a window for the program.
 * 
 * @author Daniel Simpkins
 */
public class GUI extends JFrame {

    private static final long serialVersionUID = 8566499335747253891L;
    private static final String IMAGE_256 = "icon_256.png";

    /**
     * Initializes window settings and creates a surface to draw to.
     * 
     * @param surface A reference to the Screen object
     */
    public GUI(Screen surface) {
        initialize(surface);
    }

    private void initialize(Screen surface) {
        add(surface);

        try {
            ClassLoader loader = this.getClass().getClassLoader();
            URL iconURL = loader.getResource(IMAGE_256);
            if (iconURL != null) {
                ImageIcon programIcon = new ImageIcon(iconURL);
                setIconImage(programIcon.getImage());
            } else {
                System.out.println("Unable to load image " + IMAGE_256);
            }
        } catch (Exception e) {
            System.out.println("Unable to load image " + IMAGE_256);
        }

        setTitle("JavaBoy - ");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // surface.setPreferredSize(new Dimension(160, 144));
        surface.setPreferredSize(new Dimension(256, 256));
        setResizable(false);
        setLocationRelativeTo(null);

        pack();
    }
}
