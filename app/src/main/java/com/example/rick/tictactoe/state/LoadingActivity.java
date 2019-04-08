package com.example.rick.tictactoe.state;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.rick.tictactoe.audiomanaging.AudioSettingsActivity;
import com.example.rick.tictactoe.audiomanaging.MainActivity;
import com.example.rick.tictactoe.R;

public class LoadingActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
    }

    public void start(View view) {
        Intent i = new Intent(this,GameActivity.class);
        startActivity(i);
    }

    public void startAudioSettings(View view) {
        Intent i = new Intent(this,AudioSettingsActivity.class);
        startActivity(i);
    }
}
