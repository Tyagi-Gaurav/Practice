package gt.practice.java2d.snake;

import javax.swing.*;
import java.awt.*;

public class Snake extends JFrame {

    public Snake() throws HeadlessException {
        add(new SnakeBoard());
        setResizable(false);
        pack();

        setLocationRelativeTo(null);
        setTitle("Snake");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                Snake snake = new Snake();
                snake.setVisible(true);
            }
        });
    }
}
