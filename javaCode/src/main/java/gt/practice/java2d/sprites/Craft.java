package gt.practice.java2d.sprites;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

import static java.awt.event.KeyEvent.*;

public class Craft extends Sprite {
    private int dx;
    private int dy;

    private java.util.List<Missile> missles;

    public Craft(int x, int y) {
        super(x, y);
        initCraft();
    }

    private void initCraft() {
        missles = new ArrayList<>();
        loadImage("gun.png");
    }

    public java.util.List<Missile> getMissles() {
        return missles;
    }

    public void move() {
        x += dx;
        y += dy;

        if (x < 1) {
            x = 1;
        }

        if (y < 1) {
            y = 1;
        }
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    public void keyPressed(KeyEvent keyEvent) {
        int keyCode = keyEvent.getKeyCode();

        switch(keyCode) {
            case VK_SPACE: fire(); break;
            case VK_LEFT: dx -=1; break;
            case VK_RIGHT: dx +=1; break;
            case VK_DOWN: dy +=1; break;
            case VK_UP: dy -=1; break;
        }
    }

    private void fire() {
        missles.add(new Missile(x+width, y+1));
    }

    public void keyReleased(KeyEvent keyEvent) {
        int keyCode = keyEvent.getKeyCode();

        if (keyCode == VK_LEFT || keyCode == VK_RIGHT) {
            dx = 0;
        }

        if (keyCode == VK_DOWN || keyCode == VK_UP) {
            dy = 0;
        }
    }
}
