package com.example.aptec;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent openStartingPoint = new Intent(SplashScreen.this, PlayerActivity.class);
                startActivity(openStartingPoint);
                finish();
            }
        }, 3000);
    }

//
//    public void goToSignIn(View view) {
//        Intent loginIntent = new Intent(this, PlayerActivity.class);
//        startActivity(loginIntent);
//    }
}
