package com.example.rick.tictactoe.Sprites;

import com.example.emobadaragaminglib.Base.Game;
import com.example.emobadaragaminglib.Base.Image;
import com.example.emobadaragaminglib.Components.Sprite;
import com.example.rick.tictactoe.Views.GameScreen;

public class AgainButton extends Sprite {
    Game g;
    public AgainButton(Game game, Image image, int x, int y, int height, int width) {
        super(image, x, y, height, width);
        this.g = game;
    }
    public void resetGame(){
        g.setScreen(new GameScreen(g));
    }
}
