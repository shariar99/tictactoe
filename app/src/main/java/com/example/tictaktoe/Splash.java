package com.example.tictaktoe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import static java.lang.Thread.sleep;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Thread thread= new Thread(() -> {
            try {
                sleep(1000);
                Intent i = new Intent(Splash.this,MainActivity.class);
                startActivity(i);
                finish();


            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        thread.start();
    }
}