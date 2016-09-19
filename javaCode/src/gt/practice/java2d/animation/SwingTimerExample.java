package gt.practice.java2d.animation;

import javax.swing.*;
import java.awt.*;

public class SwingTimerExample extends JFrame {
    public SwingTimerExample() throws HeadlessException {
        initUI();
    }

    private void initUI() {
        add(new BoardWithUtilTimer());

        setResizable(false);
        pack();

        setTitle("Star");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame ex = new SwingTimerExample();
                ex.setVisible(true);
            }
        });
    }
}
