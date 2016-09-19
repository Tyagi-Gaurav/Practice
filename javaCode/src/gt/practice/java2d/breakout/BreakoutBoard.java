package gt.practice.java2d.breakout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.TimerTask;

public class BreakoutBoard extends JPanel implements Common  {
    private Brick[] bricks;
    private java.util.Timer timer;
    private Ball ball;
    private Paddle paddle;

    public BreakoutBoard() {
        initBoard();
    }

    private void initBoard() {
        setPreferredSize(new Dimension(Common.WIDTH, Common.HEIGHT));
        setBackground(Color.cyan);
        setFocusable(true);
        addKeyListener(new PaddleKeyAdapter());

        bricks = new Brick[NO_OF_BRICKS];
        for (int k=0,i = 0; i < 8; i++) {
            for (int j = 0; j < 6; j++) {
                bricks[k] = new Brick(j * 70 + 30, i * 20 + 10);
                k++;
            }
        }
        setDoubleBuffered(true);
        ball = new Ball();
        paddle = new Paddle();

        timer = new java.util.Timer();
        timer.scheduleAtFixedRate(new ScheduledTask(), DELAY, PERIOD);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        drawBall(g);
        drawBricks(g);
        drawPaddle(g);
    }

    private void drawPaddle(Graphics g) {
        g.setColor(Color.black);
        g.fillRoundRect(paddle.getX(),
                paddle.getY(),
                paddle.getWidth(),
                paddle.getHeight(),
                2, 2);
    }

    private void drawBricks(Graphics g) {
        g.setColor(Color.blue);
        for (Brick brick : bricks) {
            if (!brick.isDestroyed()) {
                g.fillRect(brick.getX(), brick.getY(), brick.getWidth(), brick.getHeight());
            }
        }
    }

    private void drawBall(Graphics g) {
        g.setColor(Color.red);
        g.fillOval(ball.getX(),
                ball.getY(),
                ball.getWidth(),
                ball.getHeight());
    }

    private class ScheduledTask extends TimerTask {
        @Override
        public void run() {
            ball.move();
            paddle.move();
            checkCollisionWithBricks();
            repaint();
        }
    }

    private void checkCollisionWithBricks() {
        checkCollisionOfBallWithBrick();
        checkCollisionOfBallWithPaddle();
    }

    private void checkCollisionOfBallWithPaddle() {
        if (paddle.getRect().intersects(ball.getRect())) {
            ball.updateBallDirection(paddle.getRect());
        }
    }

    private void checkCollisionOfBallWithBrick() {


        for (Brick brick : bricks) {
            Rectangle brickRect = brick.getRect();
            if (!brick.isDestroyed() && brickRect.intersects(ball.getRect())) {
                ball.updateBallDirection(brickRect);
                brick.destroy();
            }
        }
    }

    private class PaddleKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            paddle.keyPressed(e);
        }

        @Override
        public void keyReleased(KeyEvent e) {
            paddle.keyReleased(e);
        }
    }
}
