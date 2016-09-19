package gt.practice.java2d.animation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import static java.awt.Color.BLACK;

public class BoardWithSwingTimer extends JPanel implements ActionListener {

    public static final int B_WIDTH = 350;
    public static final int B_HEIGHT = 350;
    private static final int INITIAL_Y = -40;
    private static final int INITIAL_X = -40;
    private static final int DELAY = 25;
    private Image star;
    private int x;
    private int y;
    private Timer timer;

    public BoardWithSwingTimer() {
        initBoard();
    }

    private void initBoard() {
        setBackground(BLACK);
        setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));
        setDoubleBuffered(true);

        loadImage();

        x = INITIAL_X;
        y = INITIAL_Y;

        timer = new Timer(DELAY, this);
        timer.start();
    }

    private void loadImage() {
        URL resource = this.getClass().getResource("test.png");
        ImageIcon imageIcon = new ImageIcon(resource);
        star = imageIcon.getImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawStar(g);
    }

    private void drawStar(Graphics g) {
        g.drawImage(star, x, y, this);
        Toolkit.getDefaultToolkit().sync();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        x+=1;
        y+=1;

        if (y > B_HEIGHT) {
            y = INITIAL_Y;
            x = INITIAL_X;
        }

        repaint();
    }
}
