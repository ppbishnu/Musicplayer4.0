package com.example.bishnureddy.musicplayer40.Utility;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.example.bishnureddy.musicplayer40.model.Album;
import com.example.bishnureddy.musicplayer40.model.Artist;


public class Util {
    public static Album album;
    public static Artist artist;
    public static Context context;
    public static int currentPlayingSongId;
    public static final String isFirstTime = "firsttime";
    public static SharedPreferences preferences;
    public static  SharedPreferences.Editor editor;
    public static void  toast(String text,Context context)
    {
        Toast.makeText(context,text,Toast.LENGTH_SHORT).show();
    }
    public static int Position;
    public static String FilterType="album";
}
