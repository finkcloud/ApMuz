package com.example.aptec.data;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.aptec.R;
import com.example.aptec.model.Music;

import java.util.ArrayList;

public class MusicAdapter extends RecyclerView.Adapter<MusicAdapter.MusicViewHolder> {

    private Context mContext;
    private ArrayList<Music> mMusicList;


    public MusicAdapter(Context context, ArrayList<Music> musiclist){
        this.mContext = context;
        this.mMusicList = musiclist;

    }


    @Override
    public MusicViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {

        // inflate the recycler view with row item
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.music_list_item, parent, false);
        return new MusicViewHolder(view);
    }

    @Override
    public void onBindViewHolder( MusicViewHolder holder,final int position) {
        // get refence to the list of object
        Music music = mMusicList.get(position);

        //set its data
        holder.album.setText(trimTtile(music.getAlbum(), 23));
        holder.textViewArtistName.setText(trimTtile(music.getArtist(), 23));
        holder.textViewMusicTitle.setText(trimTtile(music.getTitle(),26));

//        // implement setOnClickListener event on item view.
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                // open another activity on item click
//                Intent intent = new Intent(mContext, PlayerActivity.class);
//                intent.putExtra("media", mMusicList.get(position).getSong()); // put image data in Intent
//                mContext.startActivity(intent); // start Intent
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return mMusicList.size();
    }

    private String trimTtile(String title, int limit){
        if (title.length() > limit){
            String newTitle = title.substring(0, limit);
            return  newTitle + " ...";
        }
        return title;
    }

    public class MusicViewHolder extends RecyclerView.ViewHolder {
        TextView album;
        TextView textViewMusicTitle;
        TextView textViewArtistName;

        public MusicViewHolder( View itemView) {
            super(itemView);
            album = itemView.findViewById(R.id.album);
            textViewMusicTitle = itemView.findViewById(R.id.title_of_song);
            textViewArtistName = itemView.findViewById(R.id.artist_name);
        }
    }
}
