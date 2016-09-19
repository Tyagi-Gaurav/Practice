package gt.practice.java2d.beginner;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static java.awt.RenderingHints.*;

public class Board extends JPanel {
    public Board() {
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        try {
            drawDonut(g);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void drawDonut(Graphics g) throws IOException {
        BufferedImage bufferedImage = new BufferedImage(50,50,BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics2D = bufferedImage.createGraphics();

        RenderingHints rh = new RenderingHints(KEY_ANTIALIASING, VALUE_ANTIALIAS_ON);
        rh.put(KEY_RENDERING, VALUE_RENDER_QUALITY);

        graphics2D.setRenderingHints(rh);

        Dimension size = getSize();
        double w = size.getWidth();
        double h = size.getHeight();

        Ellipse2D ellipse2D = new Ellipse2D.Double(0, 0, 80, 130);
        graphics2D.setStroke(new BasicStroke(1));
        graphics2D.setColor(Color.gray);

        for (double deg=0;deg < 360;deg += 5) {
            AffineTransform affineTransform = AffineTransform.getTranslateInstance(w/2, h/2);
            affineTransform.rotate(Math.toRadians(deg));
            graphics2D.draw(affineTransform.createTransformedShape(ellipse2D));
        }

        Graphics graphics = bufferedImage.getGraphics();
        Container container = new Container();
        container.paint(graphics2D);
        ImageIO.write(bufferedImage, "PNG", new File("test.png"));
    }
}
