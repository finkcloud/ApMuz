package com.example.aptec.model;

public class Music {
  private String song;
  private String title;
  private String album;
    private String artist;


    public Music(String song, String title, String album, String artist) {
        this.song = song;
        this.title = title;
        this.album = album;
        this.artist = artist;
    }

    public String getSong() {
        return song;
    }

    public void setSong(String song) {
        this.song = song;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }
}


