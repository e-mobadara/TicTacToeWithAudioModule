package com.example.rick.tictactoe.logic;

public class BoardImpl implements Board{
    public char[][] board;
    private char currentPlayerMark;

    public BoardImpl() {
        board = new char[3][3];
        currentPlayerMark = 'x';
        initializeBoard();
    }

    public char getCurrentPlayerMark() {
        return currentPlayerMark;
    }

    @Override
    public void initializeBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = '-';
            }
        }
    }

    @Override
    public void printBoard() {
        //This is for the Console version of the game
        //I have been using the same Logic for all the versions
        System.out.println("*******************");
        for (int i = 0; i < 3; i++) {
            System.out.print("| ");
            for (int j = 0; j < 3; j++) {
                System.out.print(board[i][j] + " |");
            }
            System.out.println();
        }
        System.out.println("*******************");

    }

    @Override
    public boolean isBoardFull() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == '-')
                    return false;
            }
        }
        return true;
    }

    @Override
    public boolean checkForWin() {
        return checkDiagonalForWins()
                | checkRowsForWin()
                | checkColumnsForWins();
    }

    @Override
    public boolean checkRowsForWin() {
        return checkRowCol(board[0][0], board[0][1], board[0][2])
                | checkRowCol(board[1][0], board[1][1], board[1][2])
                | checkRowCol(board[2][0], board[2][1], board[2][2]);
    }

    @Override
    public boolean checkColumnsForWins() {
        return checkRowCol(board[0][0], board[1][0], board[2][0])
                | checkRowCol(board[0][1], board[1][1], board[2][1])
                | checkRowCol(board[0][2], board[1][2], board[2][2]);
    }

    @Override
    public boolean checkDiagonalForWins() {
        return checkRowCol(board[0][0], board[1][1], board[2][2])
                | checkRowCol(board[0][2], board[1][1], board[2][0]);
    }

    @Override
    public boolean checkRowCol(char c1, char c2, char c3) {
        if (c1 != '-' && c2 != '-' && c3 != '-') {
            return c1 == c2 && c1 == c3;
        }
        return false;
    }

    @Override
    public void changePlayer() {
        if (currentPlayerMark == 'x') {
            currentPlayerMark = 'o';
        } else
            currentPlayerMark = 'x';
    }

    @Override
    public boolean placeMark(int row, int col) {
        //checking that the row and col are not out of the boundaries of the box
        if (row < 3 && row >= 0) {
            if (col < 3 && col >= 0) {
                if (board[row][col] == '-') {
                    board[row][col] = currentPlayerMark;
                    return true;
                }
            }
        }
        return false;
    }

}
