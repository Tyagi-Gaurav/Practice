package gt.practice.java2d.breakout;

public class Brick extends Sprite {
    private boolean destroyed;

    public Brick(int x,int y) {
        this.x = x;
        this.y = y;
        destroyed = false;

        i_width = 65;
        i_height = 15;
    }

    public boolean isDestroyed() {
        return destroyed;
    }

    public void destroy() {
        this.destroyed = true;
    }
}
