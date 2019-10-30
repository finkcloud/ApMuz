package com.example.aptec.model;

public class Music {
    int albumArt;
    String trackName;
    String ArtistName;
    int trackId;

    public Music(int albumArt, String trackName, String artistName) {
        this.albumArt = albumArt;
        this.trackName = trackName;
        this.ArtistName = artistName;
    }

    public Music (int albumArt, String trackName, int trackId, String artistName){
        this.albumArt = albumArt;
        this.trackId = trackId;
        this.ArtistName = artistName;
        this.trackName = trackName;
    }

    public int getAlbumArt() {
        return albumArt;
    }

    public String getTrackName() {
        return trackName;
    }

    public String getArtistName() {
        return ArtistName;
    }

    public  int getTrackId(){
        return trackId;
    }
}
