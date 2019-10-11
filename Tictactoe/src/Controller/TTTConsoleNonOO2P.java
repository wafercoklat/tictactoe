package Controller;

import java.util.Scanner;
import javafx.beans.value.WritableMapValue;

/**
 * Tic-Tac-Toe: Two-player console, non-graphics, non-OO version. All
 * variables/methods are declared as static (belong to the class) in the non-OO
 * version.
 */
public class TTTConsoleNonOO2P {

    // Name-constants to represent the seeds and cell contents
    public static final int EMPTY = 0;
    public static final int CROSS = 1;
    public static final int NOUGHT = 2;

    // Name-constants to represent the various states of the game
    public static final int PLAYING = 0;
    public static final int DRAW = 1;
    public static final int CROSS_WON = 2;
    public static final int NOUGHT_WON = 3;

    // The game board and the game status
//    public static final int ROWS = 5, COLS = 5; // number of rows and columns
    public static int MAPS;
    public static int[][] board; // game board in 2D array

    //  containing (EMPTY, CROSS, NOUGHT)
    public static int currentState;  // the current state of the game
    // (PLAYING, DRAW, CROSS_WON, NOUGHT_WON)
    public static int currentPlayer; // the current player (CROSS or NOUGHT)
    public static int currntRow, currentCol; // current seed's row and column

    public static Scanner in = new Scanner(System.in); // the input Scanner

    /**
     * The entry main method (the program starts here)
     */
    public static void main(String[] args) {
        // Initialize the game-board and current status
        initGame();
        // Play the game once
        do {
            printBoard();
            playerMove(currentPlayer); // update currentRow and currentCol
            updateGame(currentPlayer, currntRow, currentCol); // update currentState
            // Print message if game-over
            if (currentState == CROSS_WON) {
                System.out.println("'X' won! Bye!");
            } else if (currentState == NOUGHT_WON) {
                System.out.println("'O' won! Bye!");
            } else if (currentState == DRAW) {
                System.out.println("It's a Draw! Bye!");
            }
            // Switch player
            currentPlayer = (currentPlayer == CROSS) ? NOUGHT : CROSS;
        } while (currentState == PLAYING); // repeat if not game-over
    }

    /**
     * Initialize the game-board contents and the current states
     */
    public static void initGame() {
        //Set value for maps of the game
        System.out.println("Maps = ");
        MAPS = in.nextInt(); //Input Maps 3x3, 4x4, 5x5 and soon
        board = new int[MAPS][MAPS];  //Update

        for (int row = 0; row < MAPS; ++row) {
            for (int col = 0; col < MAPS; ++col) {
                board[row][col] = EMPTY;  // all cells empty
            }
        }
        currentState = PLAYING; // ready to play
        currentPlayer = CROSS;  // cross plays first
    }

    /**
     * Player with the "theSeed" makes one move, with input validation. Update
     * global variables "currentRow" and "currentCol".
     */
    public static void playerMove(int thePlayer) {
        boolean validInput = false;  // for input validation
        do {
            if (thePlayer == CROSS) {
                System.out.print("Player 'X', pick your move (1 - " +MAPS*MAPS +" ): ");
            } else {
                System.out.print("Player 'O', pick your move (1 - " +MAPS*MAPS +" ): ");
            }

            //Change input here
            int inputUser = in.nextInt() - 1;
            int helpValue = 0;
            if (inputUser >= 0 && inputUser < MAPS * MAPS) {
                for (int i = 0; i < MAPS; i++) {
                    for (int j = 0; j < MAPS; j++) {
                        //Init the map of game for user input
                        int valueMaps = helpValue + j;
                        if (inputUser == valueMaps && board[i][j] == EMPTY) {
                            board[i][j] = thePlayer; // update game-board content
                            validInput = true;  // input okay, exit loop
                        }
                    }
                    helpValue += MAPS;
                }
            } else {
                System.out.println("INPUT VALID PLEASE INPUT FROM 1 TO " + MAPS*MAPS);
            }
        } while (!validInput);  // repeat until input is valid
    }

    /**
     * Update the "currentState" after the player with "theSeed" has placed on
     * (currentRow, currentCol).
     */
    public static void updateGame(int theSeed, int currentRow, int currentCol) {
        if (hasWon(theSeed, currentRow, currentCol)) {  // check if winning move
            currentState = (theSeed == CROSS) ? CROSS_WON : NOUGHT_WON;
        } else if (isDraw()) {  // check for draw
            currentState = DRAW;
        }
        // Otherwise, no change to currentState (still PLAYING).
    }

    /**
     * Return true if it is a draw (no more empty cell)
     */
    // TODO: Shall declare draw if no player can "possibly" win
    public static boolean isDraw() {
        for (int row = 0; row < MAPS; ++row) {
            for (int col = 0; col < MAPS; ++col) {
                if (board[row][col] == EMPTY) {
                    return false;  // an empty cell found, not draw, exit
                }
            }
        }
        return true;  // no empty cell, it's a draw
    }

    /**
     * Return true if the player with "theSeed" has won after placing at
     * (currentRow, currentCol)
     */
    public static boolean hasWon(int theSeed, int currentRow, int currentCol) {
        //check row
        boolean win = true;
        for (int i = 0; i < board.length; i++) {
            if (board[currentRow][i] != theSeed) {
                win = false;
                break;
            }
        }
        if (win) {
            return true;
        }

        //check column
        win = true;
        for (int i = 0; i < board.length; i++) {
            if (board[i][currentCol] != theSeed) {
                win = false;
                break;
            }
        }
        if (win) {
            return true;
        }

        //check back diagonal
        win = true;
        for (int i = 0; i < board.length; i++) {
            if (board[i][i] != theSeed) {
                win = false;
                break;
            }
        }
        if (win) {
            return true;
        }

        //check forward diagonal
        win = true;
        for (int i = 0; i < board.length; i++) {
            if (board[i][board.length - i - 1] != theSeed) {
                win = false;
                break;
            }
        }
        if (win) {
            return true;
        }

        return false;
    }

    /**
     * Print the game board
     */
    public static void printBoard() {
        int helpValue = 1;
        for (int row = 0; row < MAPS; row++) {
            for (int col = 0; col < MAPS; col++) {
                int valueMaps = helpValue + col;
                printCell(board[row][col]); // print each of the cells
                if (col != MAPS ) {
                    if (board[row][col] == NOUGHT) {
                        System.out.print("|"); // Change the O when pick the number
                    } else if (board[row][col] == CROSS) {
                        System.out.print("|"); // Change the X when pick the number
                    } else {
                     System.out.print(+valueMaps +" |");   // print vertical partition   
                    }
                } 
            }
            System.out.println();
            if (row != MAPS - 1) {
                System.out.println("-----------"); // print horizontal partition
            }
            helpValue += MAPS;
        }
        System.out.println();
    }

    /**
     * Print a cell with the specified "content"
     */
    public static void printCell(int content) {
        switch (content) {
            case EMPTY:
                System.out.print(" ");
                break;
            case NOUGHT:
                System.out.print(" O ");
                break;
            case CROSS:
                System.out.print(" X ");
                break;
        }
    }
}
