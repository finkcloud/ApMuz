package com.example.aptec;

import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aptec.data.MusicAdapter;
import com.example.aptec.data.RecyclerItemClickListener;
import com.example.aptec.data.StorageUtil;
import com.example.aptec.model.Music;
import com.example.aptec.service.MusicService;

import java.util.ArrayList;

public class PlayerActivity extends AppCompatActivity {

    public static final String Broadcast_PLAY_NEW_AUDIO = "com.example.aptec";

   TextView songTitle;
    // get the reference of RecyclerView
    RecyclerView recyclerView;
    MusicAdapter musicAdapter;

    LinearLayoutManager linearLayoutManager;

    ArrayList<Music> musicList;
    String media;
    private MusicService player;
    boolean serviceBound = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);


        Bundle intentBundle = getIntent().getExtras();
        try{
            media = intentBundle.getString("media");

        }catch (NullPointerException e){
            e.printStackTrace();
        }


        loadAudio();


        //load recycler view
        setupRecylerView();



    }


    private void setupRecylerView(){

        if(musicList.size() > 0){
            // find recyclerview
            recyclerView =  findViewById(R.id.music_list);

            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            recyclerView.setHasFixedSize(true);

            // set a LinearLayoutManager with default vertical orientation  and 1 column
            linearLayoutManager = new LinearLayoutManager(this);

            // set list to layout manager
            recyclerView.setLayoutManager(linearLayoutManager); // set LayoutManager to RecyclerView

            //check if music adapter is empty
            if(musicAdapter == null){
                musicAdapter = new MusicAdapter(PlayerActivity.this,  musicList);

                recyclerView.setAdapter(musicAdapter); // sets adapter to hold the image

            }

            recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this,
                    new RecyclerItemClickListener.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    playAudio(position);
                }
            }));

        }


    }

    private void loadAudio() {
        ContentResolver contentResolver = getContentResolver();

        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String selection = MediaStore.Audio.Media.IS_MUSIC + "!= 0";
        String sortOrder = MediaStore.Audio.Media.TITLE + " ASC";
        Cursor cursor = contentResolver.query(uri, null, selection, null, sortOrder);

        if (cursor != null && cursor.getCount() > 0) {
            musicList = new ArrayList<>();
            while (cursor.moveToNext()) {
                String data = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
                String title = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
                String album = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM));
                String artist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));

                // Save to audioList
                musicList.add(new Music(data, title, album, artist));
            }
        }
        cursor.close();
    }

    public void onPlaySongClicked(View v){

        if(media != null){
           // playAudio(media);
        }

    }

    public void onStopSongClicked(View v){
        Intent musicIntent = new Intent(this, MusicService.class);
       // Intent mIntent = new Intent(this,TestService.class);
       // stopService(musicIntent);
        //stopService(mIntent);
    }

    //Binding this Client to the AudioPlayer Service (musicserice)
    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            MusicService.LocalBinder binder = (MusicService.LocalBinder) service;
            player = binder.getService();
            serviceBound = true;

            Toast.makeText(PlayerActivity.this, "Service Bound", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            serviceBound = false;
        }
    };

    // play audio supplied with d auio file
// will be added to the play button
    /*private void playAudio(String media) {
        //Check is service is active
        if (!serviceBound) {
            Intent playerIntent = new Intent(this, MusicService.class);
            playerIntent.putExtra("media", media);
            startService(playerIntent);
            bindService(playerIntent, serviceConnection, Context.BIND_AUTO_CREATE);
        } else {

            //Service is active
            //Send a broadcast to the service -> PLAY_NEW_AUDIO
            Intent broadcastIntent = new Intent(Broadcast_PLAY_NEW_AUDIO);
            broadcastIntent.putExtra("new_media", media);
            sendBroadcast(broadcastIntent);
        }
    }*/

    private void playAudio(int audioIndex) {
        //Check is service is active
        if (!serviceBound) {
            //Store Serializable audioList to SharedPreferences
            StorageUtil storage = new StorageUtil(getApplicationContext());
            storage.storeAudio(musicList);
            storage.storeAudioIndex(audioIndex);

            Intent playerIntent = new Intent(this, MusicService.class);
            startService(playerIntent);
            bindService(playerIntent, serviceConnection, Context.BIND_AUTO_CREATE);
        } else {
            //Store the new audioIndex to SharedPreferences
            StorageUtil storage = new StorageUtil(getApplicationContext());
            storage.storeAudioIndex(audioIndex);

            //Service is active
            //Send a broadcast to the service -> PLAY_NEW_AUDIO
            Intent broadcastIntent = new Intent(Broadcast_PLAY_NEW_AUDIO);
            sendBroadcast(broadcastIntent);
        }
    }




    @Override
    public  boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.player_menu, menu);
        return true;
    }

    @Override
    public  boolean onOptionsItemSelected(@NonNull MenuItem item){
        return true;
    }



    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        serviceBound = savedInstanceState.getBoolean("ServiceState");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (serviceBound) {
            unbindService(serviceConnection);
            //service is active
            player.stopSelf();
        }
    }
}
