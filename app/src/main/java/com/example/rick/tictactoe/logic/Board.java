package com.example.rick.tictactoe.logic;

public interface Board {
    void initializeBoard();
    void printBoard();
    boolean isBoardFull();
    boolean checkForWin();

    boolean checkRowsForWin();
    boolean checkColumnsForWins();
    boolean checkDiagonalForWins();
    boolean checkRowCol(char c1,char c2,char c3);
    void changePlayer();
    boolean placeMark(int row, int col);
}
