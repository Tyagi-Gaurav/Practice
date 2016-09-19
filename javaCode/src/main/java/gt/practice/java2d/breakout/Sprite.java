package gt.practice.java2d.breakout;

import java.awt.*;

public class Sprite {
    protected int x;
    protected int y;
    protected int i_width;
    protected int i_height;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return i_width;
    }

    public int getHeight() {
        return i_height;
    }

    Rectangle getRect() {
        return new Rectangle(x, y, getWidth(), getHeight());
    }
}
