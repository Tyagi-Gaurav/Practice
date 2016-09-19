package gt.practice.java2d.sprites;

public class Alien extends Sprite {
    private static final int INITIAL_X = 400;

    public Alien(int x, int y) {
        super(x, y);
        initAlien();
    }

    private void initAlien() {
        loadImage("alien.jpg");
    }

    public void move() {
        if (x < 0) {
            x = INITIAL_X;
        }

        x-=1;
    }
}
