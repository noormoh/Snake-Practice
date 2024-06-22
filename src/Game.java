import javax.swing.*;

public class Game extends JFrame {
    private static final int WIDTH = 800;
    private static final int HEIGHT = 800;
    private Board board;

    public Game() {
        setTitle("Snake Game");
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        board = new Board();
        add(board);
        pack();

        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        new Game();
    }
}
