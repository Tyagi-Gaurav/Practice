package gt.practice.java2d.breakout;

import java.awt.event.KeyEvent;

import static java.awt.event.KeyEvent.VK_LEFT;
import static java.awt.event.KeyEvent.VK_RIGHT;

public class Paddle extends Sprite implements Common {

    private int dx;

    public Paddle() {
        this.x = INIT_PADDLE_X;
        this.y = INIT_PADDLE_Y;
    }

    @Override
    public int getWidth() {
        return 50;
    }

    @Override
    public int getHeight() {
        return 10;
    }

    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();

        if (keyCode == VK_LEFT) {
            dx = -1;
        } else if (keyCode == VK_RIGHT) {
            dx = 1;
        }
    }

    public void move() {
        x += dx;
        if (x < 0) {
            x = 0;
        }

        if (x > WIDTH - 50) {
            x = WIDTH - 50;
        }
    }

    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();

        if (keyCode == VK_LEFT || keyCode == VK_RIGHT) {
            dx = 0;
        }
    }
}
