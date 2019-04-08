package com.example.rick.tictactoe.Sprites;

import android.util.Log;

import com.example.emobadaragaminglib.Base.Game;
import com.example.emobadaragaminglib.Base.Image;
import com.example.emobadaragaminglib.Components.Sprite;
import com.example.rick.tictactoe.Assets.Again;
import com.example.rick.tictactoe.Assets.MyX_O;
import com.example.rick.tictactoe.logic.BoardImpl;

import static com.example.rick.tictactoe.state.GameActivity.Losingsound;
import static com.example.rick.tictactoe.state.GameActivity.Winningsound;

public class Square extends Sprite {
    private static final String TAG = "Square";
    private int i ; //place in the matrix
    private int j; // place in the matrix
    Game g; //for drawing purposes
    public Square(Game game, Image image, int x, int y, int height, int width) {
        super(image, x, y, height, width);
        //Look explanation of the below code right under
        this.i = this.getX()/this.getWidth();
        this.j = this.getY()/this.getHeight();
        this.g = game;
    }
    /*
    * Notice that we defined square in the Screen as
    * ph[i][j] = new Square(game,SquareAssets.square,i*w_unit,j*h_unit,h_unit,w_unit);
    * so to get the iterators i and j back, and use them in the logic of the game
    * notice that i = this.x / this.width because
    * this.x = i*w_unit and this.width = w_unit
    * */

    public boolean fillSquare(BoardImpl board){
        Log.i(TAG, "fillSquare: Called");
        //while the game has not ended
        if (!board.checkForWin() && !board.isBoardFull()) {
            //If the Current square has nothing in it
            if (board.placeMark(this.i,this.j)) {
                //if it is X turn
                if (board.getCurrentPlayerMark() == 'x') {
                    g.getCurrentScreen().
                            addSprite(new Sprite(MyX_O.__x,this.getX(),this.getY(),this.getWidth(),this.getWidth()));
                    Log.i(TAG, "fillSquare: Draw X");
                }//It is O turn
                else if (board.getCurrentPlayerMark() == 'o') {
                    g.getCurrentScreen().
                            addSprite(new Sprite(MyX_O.__o,this.getX(),this.getY(),this.getWidth(),this.getWidth()));
                    Log.i(TAG, "fillSquare: Draw O");
                }
                //Change the turns
                board.changePlayer();

                //To notify me as soon as the game ends!
                fillSquare(board);
            }
        } //The game has ended Let's check the results
        //No One won
        else if (!board.checkForWin() && board.isBoardFull()) {
            Log.i(TAG, "fillSquare: Game is TIE");
            try{Losingsound.start();}catch(Exception e){Log.d("Error", "audio file is missing");}
            g.getCurrentScreen().
                    addSprite(new AgainButton(g,Again.image,g.getScreenWidth()/2-this.getWidth(),g.getScreenHeight()/2-this.getWidth(),this.getHeight(),this.getHeight()));


        } //Someone won!
        else if (board.checkForWin()) {
            Log.i(TAG, "fillSquare: SomeoneWon!");
            try{Winningsound.start();}catch(Exception e){Log.d("Error", "audio file is missing");}
            g.getCurrentScreen().
                    addSprite(new AgainButton(g,Again.image,g.getScreenWidth()/2-this.getWidth(),g.getScreenHeight()/2-this.getWidth(),this.getHeight(),this.getHeight()));

        }
        return true;
    }

}
