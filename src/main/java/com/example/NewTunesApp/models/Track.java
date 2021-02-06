package com.example.NewTunesApp.models;

public class Track {
    private String artist;
    private String song;
    private String genre;
    private String album;

    public Track() {
    }

    public Track(String artist, String song, String genre, String album) {
        this.artist = artist;
        this.song = song;
        this.genre = genre;
        this.album = album;
    }

    public String getArtist() { return artist; }
    public String getSong() {
        return song;
    }
    public String getGenre() {
        return genre;
    }
    public String getAlbum() {
        return album;
    }
}