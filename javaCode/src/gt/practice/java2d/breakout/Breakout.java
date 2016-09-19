package gt.practice.java2d.breakout;

import javax.swing.*;
import java.awt.*;

public class Breakout extends JFrame {
    public Breakout() throws HeadlessException {
        add(new BreakoutBoard());

        pack();
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                Breakout breakout = new Breakout();
                breakout.setVisible(true);
            }
        });
    }
}
