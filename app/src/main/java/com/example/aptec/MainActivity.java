package com.example.aptec;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.aptec.service.MusicService;

public class MainActivity extends AppCompatActivity {

    private MusicService player;
    boolean serviceBound = false;
    String email = "";
    String password = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Bundle intentBundle = getIntent().getExtras();

        try{
            email = intentBundle.getString("email");
            password = intentBundle.getString("password");

            Toast.makeText(this,"your email: "+ email
                    + "Your password: " + password,Toast.LENGTH_LONG).show();
        }catch (NullPointerException e){
            e.printStackTrace();
        }





    }


    //Binding this Client to the AudioPlayer Service (musicserice)
    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            MusicService.LocalBinder binder = (MusicService.LocalBinder) service;
            player = binder.getService();
            serviceBound = true;

            Toast.makeText(MainActivity.this, "Service Bound", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            serviceBound = false;
        }
    };


    // play audio supplied with d auio file

    private void playAudio(String media) {
        //Check is service is active
        if (!serviceBound) {
            Intent playerIntent = new Intent(this, MusicService.class);
            playerIntent.putExtra("media", media);
            startService(playerIntent);
            bindService(playerIntent, serviceConnection, Context.BIND_AUTO_CREATE);
        } else {
            //Service is active
            //Send media with BroadcastReceiver
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.player_menu, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected( MenuItem item) {

        int id = item.getItemId();

        switch (id){
            case  R.id.action_exit:
                // exit
                break;
            case R.id.action_lib:
                // open lib
        }


        return true;
    }
}
