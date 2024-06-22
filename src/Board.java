//topics used...
//Class Diagrams and Planning
//Recursion
//Abstract Data Types
//Object and Classes
//Inheritance (board)

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

public class Board extends JPanel implements ActionListener, KeyListener {
    
    // class variables
    private final int TILESIZE = 20;
    private final int WIDTH = 400;
    private final int HEIGHT = 400;
    private final int DELAY = 70;

    private ArrayList<Integer> x = new ArrayList<>();
    private ArrayList<Integer> y = new ArrayList<>();

    private int snakeSize;
    private int foodX;
    private int foodY;
    private int score;

    private boolean leftDirection = false;
    private boolean rightDirection = true;
    private boolean upDirection = false;
    private boolean downDirection = false;

    private boolean inGame = true;
    private Timer timer;
    private Random random;

    public Board() {
        initBoard();
    }

    private void initBoard() {
        setFocusable(true);
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        addKeyListener(this);

        random = new Random();
        startGame();
    }

    private void startGame() {
        // set snakes length in tiles
        snakeSize = 3;

        for (int i = 0; i < snakeSize; i++) {
            x.add(100 - i * TILESIZE);
            y.add(100);
        }

        spawnFood();
        timer = new Timer(DELAY, this);
        timer.start();
    }

    // randomize food spawning
    private void spawnFood() {
        foodX = random.nextInt(WIDTH / TILESIZE) * TILESIZE;
        foodY = random.nextInt(HEIGHT / TILESIZE) * TILESIZE;
    }

    // if snake collides with food, lengthen snake and add a point
    private void checkFood() {
        if ((x.get(0) == foodX) && (y.get(0) == foodY)) {
            snakeSize++;
            score++;
            x.add(x.get(snakeSize - 2));
            y.add(y.get(snakeSize - 2));
            spawnFood();
        }
    }

    private void checkCollision() {
        for (int i = snakeSize - 1; i > 0; i--) {
            if ((x.get(0).equals(x.get(i))) && (y.get(0).equals(y.get(i)))) {
                inGame = false;
            }
        }

        if (y.get(0) >= HEIGHT) {
            inGame = false;
        }

        if (y.get(0) < 0) {
            inGame = false;
        }

        if (x.get(0) >= WIDTH) {
            inGame = false;
        }

        if (x.get(0) < 0) {
            inGame = false;
        }

        if (!inGame) {
            timer.stop();
        }
    }

    private void move() {
        // continous movement, snake is always moving a tile over
        for (int i = snakeSize - 1; i > 0; i--) {
            x.set(i, x.get(i - 1));
            y.set(i, y.get(i - 1));
        }

        if (leftDirection) {
            x.set(0, x.get(0) - TILESIZE);
        }

        if (rightDirection) {
            x.set(0, x.get(0) + TILESIZE);
        }

        if (upDirection) {
            y.set(0, y.get(0) - TILESIZE);
        }

        if (downDirection) {
            y.set(0, y.get(0) + TILESIZE);
        }
    }

    private void doDrawing(Graphics g) {
        if (inGame) {
            g.setColor(Color.pink);
            g.fillRect(foodX, foodY, TILESIZE, TILESIZE);

            for (int i = 0; i < snakeSize; i++) {
                if (i == 0) {
                    g.setColor(Color.orange);
                } else {
                    g.setColor(Color.pink);
                }
                g.fillRect(x.get(i), y.get(i), TILESIZE, TILESIZE);
            }

        } else {
            gameOver(g);
        }
    }

    private void gameOver(Graphics g) {
        String msg = "GAME OVER";
        String scoreMsg = "SCORE: " + score;
        Font small = new Font("Helvetica", Font.BOLD, 50);
        FontMetrics metr = getFontMetrics(small);
        g.setFont(small);
        g.drawString(msg, (WIDTH - metr.stringWidth(msg)) / 2, HEIGHT / 2);
        g.drawString(scoreMsg, (WIDTH - metr.stringWidth(scoreMsg)) / 2, HEIGHT / 2 + 80);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        doDrawing(g);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (inGame) {
            checkFood();
            checkCollision();
            move();
        }
        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if ((key == KeyEvent.VK_LEFT) && (!rightDirection)) {
            leftDirection = true;
            upDirection = false;
            downDirection = false;
        }

        if ((key == KeyEvent.VK_RIGHT) && (!leftDirection)) {
            rightDirection = true;
            upDirection = false;
            downDirection = false;
        }

        if ((key == KeyEvent.VK_UP) && (!downDirection)) {
            upDirection = true;
            rightDirection = false;
            leftDirection = false;
        }

        if ((key == KeyEvent.VK_DOWN) && (!upDirection)) {
            downDirection = true;
            rightDirection = false;
            leftDirection = false;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        Board board = new Board();
        frame.add(board);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}