package com.example.tictaktoe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GameDisplay extends AppCompatActivity {

    private TicTacToe ticTacToe ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_display);
          Button playAgainBTN = findViewById(R.id.play_again);
          Button homeBTN = findViewById(R.id.home_button);
          TextView playerTern = findViewById(R.id.player_display);
          playAgainBTN.setVisibility(View.GONE);
          homeBTN.setVisibility(View.GONE);

          String [] playerName = getIntent().getStringArrayExtra("PLAYER_NAMES") ;
          if (playerName != null){
              playerTern.setText((playerName[0] + "'s Turn"));
          }
        ticTacToe = findViewById(R.id.ticTacToe3);
        ticTacToe.setUpGame(playAgainBTN ,homeBTN,playerTern,playerName);
    }
    public void playAgainButtonClick(View view){

        ticTacToe.resetGame();
        ticTacToe.invalidate();
    }
    public void homeButtonClick(View view ){

        Intent intent = new Intent(this ,MainActivity.class);
        startActivity(intent);

    }
}