package TicTacToe;

import java.awt.*;

public class Board {
    // grid line width
    public static final int GRID_WIDTH = 8;
    // grid line half width
    public static final int GRID_WIDTH_HALF = GRID_WIDTH / 2;

    // 2D array of ROWS-by-COLS Cell instances
    Cell[][] cells;

    /** Constructor to create the game board */
    public Board() {
        // The cells are now initialized using ROWS and COLS constants
        cells = new Cell[GameMain.ROWS][GameMain.COLS];

        // Create each cell in the grid
        for (int row = 0; row < GameMain.ROWS; ++row) {
            for (int col = 0; col < GameMain.COLS; ++col) {
                cells[row][col] = new Cell(row, col);
            }
        }
    }

    /** Return true if it is a draw (i.e., no more EMPTY cells) */
    public boolean isDraw() {
        // Check whether the game has ended in a draw.
        // Used a nested loop to check if the cells content in the board grid are Player.Empty.
        for (int row = 0; row < GameMain.ROWS; ++row) {
            for (int col = 0; col < GameMain.COLS; ++col) {
                if (cells[row][col].content == Player.Empty) {
                    return false; // it's not a draw if there is at least one empty cell
                }
            }
        }
        return true; // it's a draw if no empty cells left 
    }

    /** Return true if the current player "thePlayer" has won after making their move */
    public boolean hasWon(Player thePlayer, int playerRow, int playerCol) {
        // Check if player has 3-in-that-row
        if (cells[playerRow][0].content == thePlayer && cells[playerRow][1].content == thePlayer && cells[playerRow][2].content == thePlayer) {
            return true;
        }

        // Check if the player has 3 in the playerCol
        if (cells[0][playerCol].content == thePlayer && cells[1][playerCol].content == thePlayer && cells[2][playerCol].content == thePlayer) {
            return true;
        }

        // 3-in-the-diagonal
        if (cells[0][0].content == thePlayer && cells[1][1].content == thePlayer && cells[2][2].content == thePlayer) {
            return true;
        }

        // Check the diagonal in the other direction
        if (cells[0][2].content == thePlayer && cells[1][1].content == thePlayer && cells[2][0].content == thePlayer) {
            return true;
        }

        // No winner, keep playing
        return false;
    }

    /**
     * Draws the grid (rows then columns) using constant sizes, then call on the
     * Cells to paint themselves into the grid
     */
    public void paint(Graphics g) {
        // Draw the grid
        g.setColor(Color.gray);
        for (int row = 1; row < GameMain.ROWS; ++row) {
            g.fillRoundRect(0, GameMain.CELL_SIZE * row - GRID_WIDTH_HALF,
                    GameMain.CANVAS_WIDTH - 1, GRID_WIDTH,
                    GRID_WIDTH, GRID_WIDTH);
        }
        for (int col = 1; col < GameMain.COLS; ++col) {
            g.fillRoundRect(GameMain.CELL_SIZE * col - GRID_WIDTH_HALF, 0,
                    GRID_WIDTH, GameMain.CANVAS_HEIGHT - 1,
                    GRID_WIDTH, GRID_WIDTH);
        }

        // Draw the cells
        for (int row = 0; row < GameMain.ROWS; ++row) {
            for (int col = 0; col < GameMain.COLS; ++col) {
                cells[row][col].paint(g);
            }
        }
    }
}