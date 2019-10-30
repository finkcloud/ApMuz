package com.example.aptec;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
    }


    public void goToSignIn(View view) {
        Intent loginIntent = new Intent(this, MusicLibraryActivity.class);
        startActivity(loginIntent);
    }
}
