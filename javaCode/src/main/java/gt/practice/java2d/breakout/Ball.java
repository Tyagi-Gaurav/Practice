package gt.practice.java2d.breakout;

import java.awt.*;

public class Ball extends Sprite implements Common {
    private int ydir;
    private int xdir;

    public Ball() {
        xdir = 1;
        ydir = -1;

        i_width = 10;
        i_height = 10;

        resetState();
    }

    private void resetState() {
        x = INIT_BALL_X;
        y = INIT_BALL_Y;
    }

    public void setYdir(int ydir) {
        this.ydir = ydir;
    }

    public void setXdir(int xdir) {
        this.xdir = xdir;
    }

    public void move() {
        x += xdir;
        y += ydir;

        if (x == 0) {
            setXdir(1);
        }

        if (x == WIDTH - i_width) {
            setXdir(-1);
        }

        if (y == 0) {
            setYdir(1);
        }
    }

    public void updateBallDirection(Rectangle rect) {
        int minX = (int) this.getRect().getMinX();
        int minY = (int) this.getRect().getMinY();
        int width = (int) this.getRect().getWidth();
        int height = (int) this.getRect().getHeight();

        Point vTopLeftUp = new Point(minX + 1, minY-1);
        Point vBottomLeftDown = new Point(minX + 1, minY + height+1);
        Point vTopRightUp = new Point(minX + width -1, minY-1);
        Point vBottomRightDown = new Point(minX + width -1, minY + height+1);

        Point hBelowTopLeft = new Point(minX-1, minY + 1);
        Point hBelowTopRight = new Point(minX+width+1, minY + 1);

        if (rect.contains(hBelowTopLeft)) {
            this.setXdir(1);
        } else if (rect.contains(hBelowTopRight)) {
            this.setXdir(-1);
        }

        if (rect.contains(vTopLeftUp) ||
                rect.contains(vTopRightUp)) {
            this.setYdir(1);
        } else if (rect.contains(vBottomLeftDown) ||
                rect.contains(vBottomRightDown)) {
            this.setYdir(-1);
        }

    }
}
