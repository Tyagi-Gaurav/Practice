package gt.practice.java2d.sprites;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

import static java.awt.Color.WHITE;

public class SpriteBoard extends JPanel implements ActionListener {

    private static final int B_WIDTH = 400 ;
    private static final int B_HEIGHT = 300;
    private Timer timer;
    private Craft craft = new Craft(40, 60);
    private final int DELAY = 10;

    private boolean ingame;
    private ArrayList<Alien> aliens;
    private final int[][] pos = {
            {2380, 29}, {2500, 59}, {1380, 89},
            {780, 109}, {580, 139}, {680, 239},
            {790, 259}, {760, 50}, {790, 150},
            {980, 209}, {560, 45}, {510, 70},
            {930, 159}, {590, 80}, {530, 60},
            {940, 59}, {990, 30}, {920, 200},
            {900, 259}, {660, 50}, {540, 90},
            {810, 220}, {860, 20}, {740, 180},
            {820, 128}, {490, 170}, {700, 30}
    };

    public SpriteBoard() {
        initBoard();
    }

    private void initBoard() {
        addKeyListener(new KeyAdapter());
        setFocusable(true);
        setBackground(WHITE);
        ingame = true;

        initAliens();

        timer = new Timer(DELAY, this);
        timer.start();
    }

    private void initAliens() {
        aliens = new ArrayList<Alien>();

        for (int[] p : pos) {
            aliens.add(new Alien(p[0], p[1]));
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (ingame) {
            doDrawing(g);
        } else {
            drawGameOver(g);
        }
        Toolkit.getDefaultToolkit().sync();
    }

    private void drawGameOver(Graphics g) {
        String msg = "Game Over";
        Font small = new Font("Helvetica", Font.BOLD, 14);
        FontMetrics fontMetrics = getFontMetrics(small);

        g.setColor(Color.BLACK);
        g.setFont(small);
        g.drawString(msg, (B_WIDTH - fontMetrics.stringWidth(msg)) / 2,
                B_HEIGHT / 2);
    }

    private void doDrawing(Graphics g) {
        Graphics2D graphics2D = (Graphics2D) g;

        if (craft.isVisible()) {
            graphics2D.drawImage(craft.getImage(),
                    craft.getX(),
                    craft.getY(),
                    this);
        }

        for (Missile missile : craft.getMissles()) {
            if (missile.isVisible()) {
                graphics2D.drawImage(missile.getImage(), missile.getX(), missile.getY(), this);
            }
        }

        for (Alien alien : aliens) {
            if (alien.isVisible()) {
                g.drawImage(alien.getImage(), alien.getX(), alien.getY(), this);
            }
        }

        g.setColor(Color.WHITE);
        g.drawString("Aliens left: " + aliens.size(), 5, 15);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        inGame();
        updateCraft();
        updateMissiles();
        updateAliens();
        checkCollisions();
        repaint();
    }

    private void checkCollisions() {
        Rectangle craftBound = craft.getBounds();

        for (Alien alien : aliens) {
            Rectangle alienBounds = alien.getBounds();
            if (craftBound.intersects(alienBounds)) {
                craft.setVis(false);
                alien.setVis(false);
                ingame = false;
            }
        }

        for (Missile missile : craft.getMissles()) {
            Rectangle missileBounds = missile.getBounds();

            for (Alien alien : aliens) {
                Rectangle alienBounds = alien.getBounds();
                if (missileBounds.intersects(alienBounds)) {
                    missile.setVis(false);
                    alien.setVis(false);
                }
            }
        }
    }

    private void updateAliens() {
        if (aliens.isEmpty()) {
            ingame = false;
            return;
        }

        for (int i=0;i < aliens.size();++i) {
            Alien alien = aliens.get(i);
            if (alien.isVisible()) {
                alien.move();
            } else {
                aliens.remove(i);
            }
        }
    }

    private void inGame() {
        if (!ingame) {
            timer.stop();
        }
    }

    private void updateMissiles() {
        List<Missile> missiles = craft.getMissles();

        for (int i = 0; i < missiles.size(); i++) {
            Missile missile = missiles.get(i);
            if (missile.isVisible()) {
                missile.move();
            } else {
                missiles.remove(i);
            }
        }
    }

    private void updateCraft() {
        craft.move();
    }

    private class KeyAdapter implements KeyListener {
        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyPressed(KeyEvent e) {
            craft.keyPressed(e);
        }

        @Override
        public void keyReleased(KeyEvent e) {
            craft.keyReleased(e);
        }
    }
}
