package gt.practice.java2d.sprites;

import javax.swing.*;
import java.awt.*;

public class MovingSprite extends JFrame {
    public MovingSprite() throws HeadlessException {
        initFrame();
    }

    private void initFrame() {
        add(new SpriteBoard());
        setSize(500, 400);
        setResizable(false);

        setTitle("Moving Sprite");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                MovingSprite movingSprite = new MovingSprite();
                movingSprite.setVisible(true);
            }
        });
    }
}
