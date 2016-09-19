package gt.practice.java2d.sprites;

public class Missile extends Sprite {
    private static final int MISSILE_SPEED = 2;
    private static final int BOARD_WIDTH = 390;

    public Missile(int x, int y) {
        super(x, y);
        initMissile();
    }

    private void initMissile() {
        loadImage("missile.png");
    }

    public void move() {
        x += MISSILE_SPEED;

        if (x > BOARD_WIDTH) {
            setVis(false);
        }
    }
}
