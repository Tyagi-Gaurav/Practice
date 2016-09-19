package gt.practice.java2d.sprites;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class Sprite {
    private boolean vis;

    protected int x;
    protected int y;
    private Image image;
    protected int width;
    protected int height;

    public Sprite(int x, int y) {
        this.x = x;
        this.y = y;
        vis = true;
    }

    protected void loadImage(String imageName) {
        URL resource = Sprite.class.getResource(imageName);
        ImageIcon imageIcon = new ImageIcon(resource);
        image = imageIcon.getImage();
        width = image.getWidth(null);
        height = image.getHeight(null);
    }

    public Image getImage() {
        return image;
    }

    public int getY() {
        return y;
    }

    public void setVis(boolean vis) {
        this.vis = vis;
    }

    public boolean isVisible() {
        return vis;
    }

    public int getX() {
        return x;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }
}
