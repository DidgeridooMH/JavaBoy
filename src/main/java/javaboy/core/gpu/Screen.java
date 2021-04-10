package javaboy.core.gpu;

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * Draws to the window.
 * 
 * @author Daniel Simpkins
 *
 */
public class Screen extends JPanel {

    private static final long serialVersionUID = 7262022831772616973L;

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
