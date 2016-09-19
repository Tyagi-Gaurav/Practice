package gt.practice.java2d.beginner;

import javax.swing.*;
import java.awt.*;

public class Application extends JFrame {
    public Application() throws HeadlessException {
        initUI();
    }

    private void initUI() {
        add(new Board());
        setSize(250, 200);
        setTitle("Application");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                Application application = new Application();
                application.setVisible(true);
            }
        });
    }
}
