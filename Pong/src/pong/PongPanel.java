package pong;

import java.awt.Stroke;
import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JPanel;
import javax.swing.Timer;

public class PongPanel extends JPanel implements ActionListener, KeyListener {
    private Timer timer;
    private static final int DELAY = 10; // Milliseconds between timer ticks
    private static final Color BACKGROUND_COLOUR = Color.BLACK;
    private final static int TIMER_DELAY = 5;
    private static final int BALL_MOVEMENT_SPEED = 5;
    GameState gameState = GameState.INITIALISING;
    Ball ball;
    Paddle paddle1, paddle2;

    public PongPanel() {
        setFocusable(true);
        addKeyListener(this);
        setBackground(BACKGROUND_COLOUR);
        Timer timer = new Timer(TIMER_DELAY, this);
        timer.start();
    }

    public void createObjects() {
        ball = new Ball(getWidth(), getHeight());
        paddle1 = new Paddle(Player.One, getWidth(), getHeight());
        paddle2 = new Paddle(Player.Two, getWidth(), getHeight());
    }

    private void update() {
        switch(gameState) {
            case INITIALISING: {
                createObjects();
                ball.setXVelocity(BALL_MOVEMENT_SPEED);
                ball.setYVelocity(BALL_MOVEMENT_SPEED);
                gameState = GameState.PLAYING;
                break;
            }
            case PLAYING: {
            	moveObject(ball);
            	checkWallBounce();
                break;
            }
            case GAMEOVER: {
                break;
            }
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        paintDottedLine(g);
        if(gameState!= GameState.INITIALISING) {
            paintSprite(g, ball);
            paintSprite(g, paddle1);
            paintSprite(g, paddle2);
        }
    }

    private void paintDottedLine(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        Stroke dashed = new BasicStroke(3, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0);
        g2d.setStroke(dashed);
        g2d.setPaint(Color.WHITE);
        g2d.drawLine(getWidth() / 2, 0, getWidth() / 2, getHeight());
        g2d.dispose();
    }

    private void paintSprite(Graphics g, Sprite sprite) {
        g.setColor(sprite.getColour());
        g.fillRect(sprite.getXPosition(), sprite.getYPosition(), sprite.getWidth(), sprite.getHeight());
    }

    private void moveObject(Sprite sprite) {
        int newX = sprite.getXPosition() + sprite.getXVelocity();
        int newY = sprite.getYPosition() + sprite.getYVelocity();
        sprite.setXPosition(newX, getWidth());
        sprite.setYPosition(newY, getHeight());
    }
    
    private void resetBall() {
    	ball.resetToInitialPosition();
    }
    
    private void checkWallBounce() {
    	if (ball.getXPosition() <= 0 || ball.getXPosition() + ball.getWidth() >= getWidth()) {
    		resetBall();
    	}
    	if (ball.getYPosition() <= 0 || ball.getYPosition() + ball.getHeight() >= getHeight()) {
    		ball.setYVelocity(-ball.getYVelocity());
    	}
    }
    


    @Override
    public void keyTyped(KeyEvent event) {
    }

    @Override
    public void keyPressed(KeyEvent event) {
    }

    @Override
    public void keyReleased(KeyEvent event) {
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        update();
        repaint();
    }
}