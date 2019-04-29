package com.example.rick.tictactoe.state;

import android.media.MediaPlayer;

import com.e_mobadara.audiomanaging.moblibAudioFileManager;
import com.example.emobadaragaminglib.Base.Graphics;
import com.example.emobadaragaminglib.Base.Screen;
import com.example.emobadaragaminglib.Implementation.AndroidGame;
import com.example.rick.tictactoe.Assets.Again;
import com.example.rick.tictactoe.Assets.MyX_O;
import com.example.rick.tictactoe.Assets.SquareAssets;
import com.example.rick.tictactoe.R;
import com.example.rick.tictactoe.Views.GameScreen;

public class GameActivity extends AndroidGame {

    public static MediaPlayer Losingsound;
    public static MediaPlayer Winningsound;

    @Override
    public Screen getInitScreen() {
        

        initAssets();
        return new GameScreen(this);
    }

    private void initAssets() {
        MyX_O.__o = getGraphics().newImage(R.drawable.o,Graphics.ImageFormat.ARGB8888,getResources());
        MyX_O.__x = getGraphics().newImage(R.drawable.x,Graphics.ImageFormat.ARGB8888,getResources());
        SquareAssets.square = getGraphics().newImage(R.drawable.square,Graphics.ImageFormat.ARGB8888,getResources());
        Again.image  = getGraphics().newImage(R.drawable.again,Graphics.ImageFormat.ARGB8888,getResources());
        Losingsound = moblibAudioFileManager.getRandomAudioFile(this,"encouragement","AR");
        Winningsound = moblibAudioFileManager.getRandomAudioFile(this,"good","AR");
    }
}
