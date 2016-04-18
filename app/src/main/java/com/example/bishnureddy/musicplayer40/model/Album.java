package com.example.bishnureddy.musicplayer40.model;

/**
 * Created by Bishnu.Reddy on 4/15/2016.
 */
public class Album {
    int Id;
    String Name;
    int No_Of_Songs;
    byte[] AlbumImage;
    public Album()
    {

    }
    public Album(String name,int no_Of_Songs,byte[] albumImage)
    {
        Name= name;
        No_Of_Songs=no_Of_Songs;
        AlbumImage=albumImage;
    }

    public int getId() {
        return Id;
    }

    public byte[] getAlbumImage() {
        return AlbumImage;
    }

    public int getNo_Of_Songs() {
        return No_Of_Songs;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setNo_Of_Songs(int no_Of_Songs) {
        No_Of_Songs = no_Of_Songs;
    }

    public void setAlbumImage(byte[] albumImage) {
        AlbumImage = albumImage;
    }

    public void setId(int id) {
        Id = id;
    }
}
