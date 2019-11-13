package com.example.aptec;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aptec.data.MusicAdapter;
import com.example.aptec.model.Music;

import java.util.ArrayList;

public class MusicLibraryActivity extends AppCompatActivity {

    // get the reference of RecyclerView
    RecyclerView recyclerView;
    MusicAdapter musicAdapter;
    LinearLayoutManager linearLayoutManager;
   ArrayList<Music> musicList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_library);

        // find recyclerview
        recyclerView =  findViewById(R.id.music_list);


        // load audio from device
        loadAudio();

        //play the first audio in the ArrayList
        //playAudio(musicList.get(0).getsong());



        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // create some dummy music data
//        Music m1 = new Music(R.drawable.maron5, "Maron 5 Memories", R.raw.maron_5_memories,"Maron 5");
//        Music m2 = new Music(R.drawable.maron5, "Don't matter", R.raw.maron_5_memories,"Akon ");
//        Music m3 = new Music(R.drawable.maron5, "Oju elegba", R.raw.maron_5_memories,"Wizkid ");
//        Music m4 = new Music(R.drawable.maron5, "Woman", R.raw.maron_5_memories,"Tekno ");
//        Music m5 = new Music(R.drawable.maron5, "Assurance", R.raw.maron_5_memories,"Davido ");
//        Music m6 = new Music(R.drawable.maron5, "Lil me", R.raw.maron_5_memories,"Treysong 5");
//        Music m7 = new Music(R.drawable.maron5, "Orinoko", R.raw.maron_5_memories," Enya");
//        Music m8 = new Music(R.drawable.maron5, "Grow", R.raw.maron_5_memories," Jon B");
//
//
//        // adding the music to the list
//        musicList.add(m1);
//        musicList.add(m2);
//        musicList.add(m3);
//        musicList.add(m4);
//        musicList.add(m5);
//        musicList.add(m6);
//        musicList.add(m7);
//        musicList.add(m8);
//


        // set a LinearLayoutManager with default vertical orientation  and 1 column
         linearLayoutManager = new LinearLayoutManager(this);

        // set list to layout manager
        recyclerView.setLayoutManager(linearLayoutManager); // set LayoutManager to RecyclerView


        //check if music adapter is empty
        if(musicAdapter == null){
           musicAdapter = new MusicAdapter(MusicLibraryActivity.this,  musicList);

           recyclerView.setAdapter(musicAdapter); // sets adapter to hold the image

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
}
