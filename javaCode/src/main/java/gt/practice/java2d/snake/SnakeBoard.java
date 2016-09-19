package gt.practice.java2d.snake;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

import static gt.practice.java2d.snake.Direction.*;
import static java.awt.event.KeyEvent.*;

public class SnakeBoard extends JPanel implements ActionListener{
    private static final int BOARD_WIDTH = 600;
    private static final int BOARD_HEIGHT = 600;
    private static final int DELAY = 140;
    private static final int BEAD_WIDTH = 10;
    private static final int BEAD_HEIGHT = 10;
    private static final int DOT_SIZE = 10;
    private static final int RAND_POS = 29;
    private Timer timer;
    private java.util.List<Point> snakeLocation;
    private Direction currentDirection = UP;
    private Point currentAppleLocation = new Point(10, 10);
    private static final int SNAKE_BEAD_GAP = 10;
    private final Random random = new Random();
    private boolean inGame = false;

    public SnakeBoard() throws HeadlessException {
        initBoard();
    }

    private void initBoard() {
        snakeLocation = new ArrayList<>();
        setBackground(Color.black);
        setFocusable(true);
        setPreferredSize(new Dimension(BOARD_WIDTH, BOARD_HEIGHT));
        initGame();
    }

    private void initGame() {
        int startPos = 200;
        snakeLocation.add(new Point(50, startPos));
        snakeLocation.add(new Point(50, startPos + BEAD_HEIGHT/2));
        snakeLocation.add(new Point(50, startPos + BEAD_HEIGHT));

        this.addKeyListener(registerKeyAdapter());
        inGame = true;

        timer = new Timer(DELAY, this);
        timer.start();
    }

    private KeyListener registerKeyAdapter() {
        return new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();

                if (keyCode == VK_LEFT) {
                    currentDirection = LEFT;
                } else if (keyCode == VK_RIGHT) {
                    currentDirection = RIGHT;
                } else if (keyCode == VK_DOWN) {
                    currentDirection = DOWN;
                } else if (keyCode == VK_UP) {
                    currentDirection = UP;
                }
            }
        };
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (inGame) {
            if (hasSnakeAppleCollided()) {
                increaseSnakeLength();
                randomizeAppleLocation();
            } else if (hasSnakeCollidedWithItselfOrWalls()) {
                inGame = false;
            }
        }

        if (inGame) {
            moveSnake();
            drawApple(g);
            drawSnake(g);
        } else {
            gameOver(g);
            timer.stop();
        }
    }

    private boolean hasSnakeCollidedWithItselfOrWalls() {
        Point point = snakeLocation.get(0);

        if (point.getX() <= 0 ||
            point.getY() <= 0 ||
            point.getX() >= BOARD_WIDTH ||
            point.getY() >= BOARD_HEIGHT) {
            return true;
        }

        for (int i=snakeLocation.size() - 1;i > 0 ;--i) {
            Point currentPoint = snakeLocation.get(i);
            if (point.toString().equals(currentPoint.toString())) {
                return true;
            }
        }

        return false;
    }

    private void gameOver(Graphics g) {
        String msg = "Game Over";
        Font small = new Font("Helvetica", Font.BOLD, 14);
        FontMetrics metr = getFontMetrics(small);

        g.setColor(Color.white);
        g.setFont(small);
        g.drawString(msg, (BOARD_WIDTH - metr.stringWidth(msg)) / 2, BOARD_HEIGHT / 2);
    }

    private void randomizeAppleLocation() {
        int r = (int) (Math.random() * RAND_POS);
        int apple_x = ((r * DOT_SIZE));

        r = (int) (Math.random() * RAND_POS);
        int apple_y = ((r * DOT_SIZE));

        currentAppleLocation = new Point(apple_x, apple_y);
    }

    private void increaseSnakeLength() {
        snakeLocation.add(new Point(0,0));
    }

    private boolean hasSnakeAppleCollided() {
        Point snakeHead = snakeLocation.get(0);
        return snakeHead.toString().equals(currentAppleLocation.toString());
    }

    private void moveSnake() {
        for (int i=snakeLocation.size() - 1;i >= 0 ;--i) {
            Point point = snakeLocation.get(i);
            if (i == 0) {
                if (currentDirection == UP) {
                    point.setLocation(point.getX(), point.getY() - SNAKE_BEAD_GAP);
                } else if (currentDirection == LEFT) {
                    point.setLocation(point.getX() - SNAKE_BEAD_GAP, point.getY());
                } else if (currentDirection == RIGHT) {
                    point.setLocation(point.getX() + SNAKE_BEAD_GAP, point.getY());
                } else if (currentDirection == DOWN) {
                    point.setLocation(point.getX(), point.getY()+ SNAKE_BEAD_GAP);
                }
            } else {
                Point previousPoint = snakeLocation.get(i - 1);
                point.setLocation(previousPoint.getX(), previousPoint.getY());
            }
        }
    }

    private void drawSnake(Graphics g) {
        for (int i=0;i < snakeLocation.size();++i) {
            if (i == 0) {
                g.setColor(Color.white);
            } else {
                g.setColor(Color.green);
            }

            Point point = snakeLocation.get(i);
            g.fillOval(((int) point.getX()), ((int) point.getY()),
                    BEAD_WIDTH, BEAD_HEIGHT);
        }
    }

    private void drawApple(Graphics g) {
        g.setColor(Color.red);
        g.fillOval(((int) currentAppleLocation.getX()),
                ((int) currentAppleLocation.getY()), BEAD_WIDTH, BEAD_HEIGHT);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }
}
