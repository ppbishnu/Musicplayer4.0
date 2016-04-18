package com.example.bishnureddy.musicplayer40.model;

/**
 * Created by Bishnu.Reddy on 4/15/2016.
 */
public class Song {
    private int id;
    private String name;
    private String artist,album,path;
    private byte[]songImage;
    private int isPlaying=0;
    private int isAddedToFav=0;
    String duration;
    int albumId;

    public Song(int songID, String songTitle, String songArtist) {
        id=songID;
        name =songTitle;
        artist=songArtist;
    }
    public void setSongImage(byte[] songImage) {
        this.songImage = songImage;
    }

    public void setIsPlaying(int playing) {
        isPlaying = playing;
    }

    public void setIsAddedToFav(int addedToFav) {
        isAddedToFav = addedToFav;
    }

    public void setAlbumId(int albumId) {
        this.albumId = albumId;
    }

    public void setId(int id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name==null?"unknown Album":name;
    }
    public void setArtist(String artist) {
        this.artist = artist==null?"Unknown Artist":artist;
    }
    public void setAlbum(String album) {
        this.album = album==null?"Unknown Album":album;
    }
    public void setPath(String path) {
        this.path = path;
    }

    public String getAlbum() {
        return album;
    }
    public String getPath() {
        return path;
    }
    public String getDurationString() {
        int convDuration=Integer.parseInt(duration)/1000;
        long s = convDuration % 60;
        long m = (convDuration / 60) % 60;
        long h = (convDuration / (60 * 60)) % 24;
        return String.format("%d:%02d:%02d", h,m,s);
    }
    public String getDuration() {
        return duration;
    }
    public void setDuration(String duration) {
        this.duration = duration;
    }

    public Song(){

    }

    public byte[] getSongImage() {
        return songImage;
    }

    public String getName() {
        return name;
    }

    public String getArtist() {
        return artist;
    }

    public int getId() {
        return id;
    }

    public int getIsPlaying() {
        return isPlaying;
    }
    public int getIsAddedToFav() {
        return isAddedToFav;
    }
}
