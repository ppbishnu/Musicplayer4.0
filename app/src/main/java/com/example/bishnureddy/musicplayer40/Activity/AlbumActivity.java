package com.example.bishnureddy.musicplayer40.Activity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaMetadataRetriever;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.example.bishnureddy.musicplayer40.Adapter.AlbumAdapter;
import com.example.bishnureddy.musicplayer40.R;
import com.example.bishnureddy.musicplayer40.Utility.Util;
import com.example.bishnureddy.musicplayer40.helper.DatabaseHelper;
import com.example.bishnureddy.musicplayer40.model.Album;
import com.example.bishnureddy.musicplayer40.model.Song;

import java.io.File;
import java.util.ArrayList;

public class AlbumActivity extends AppCompatActivity {

    static ArrayList<Song> songList;
    static GridView albumGridView;
    static byte[] art;
    Album album;
   DatabaseHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album);

       /* Util.preferences = getSharedPreferences("Music",MODE_PRIVATE);
        Util.editor = Util.preferences.edit();

        if(Util.preferences.getBoolean("isfirsttime",true))
        {
            Util.editor.putBoolean("isfirsttime",false);
            Util.editor.commit();
        }else{
            SQLiteDatabase sqLiteDatabase = new SQLiteDatabase();
        }*/


        db=new DatabaseHelper(getApplicationContext());

        db.deleteAllData();
       /*albumList=ListOfAlbums();*/

        ArrayList<Song> songsFile = new ArrayList<Song>();
        songsFile = findSongs(Environment.getExternalStorageDirectory());



       /* long albumid = db.createAlbum(new Album(songsFile.get(5).getName(),songsFile.get(5).getSongImage(),1));
        db.createSong(songsFile.get(5),albumid);
        List<Song> songs=db.getAllSongs();
        db.closeDB();
*/

        /*Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.all_list);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        album = new Album("All Songs list", songsFile.size(),stream.toByteArray());
        albumList.add(album);
        db.createAlbum(album);*/



        ArrayList<String> albumNames = new ArrayList<String>();
        for (int i = 0; i < songsFile.size(); i++) {
            if (songsFile.get(i).getAlbum() != null && songsFile.get(i).getAlbum().length() > 0) {
                album = new Album();
                long albumid;
                album.setName(songsFile.get(i).getAlbum());
                /*for (Song s : songsFile) {
                    if (s.getAlbum() != null && s.getAlbum().length() > 0) {
                       *//* if (s.getAlbum().hashCode() == songsFile.get(i).getAlbum().hashCode()) {
                           *//**//* album.getAlbumSongs().add(s);*//**//*
                            album.setNo_Of_Songs(album.getNo_Of_Songs() + 1);
                        }*//*
                    }
                }*/
                if (!albumNames.contains(album.getName())) {
                    albumNames.add(album.getName());
                    album.setAlbumImage(songsFile.get(i).getSongImage());
                    album.setNo_Of_Songs(1);
                    albumid= db.createAlbum(album);
                    db.createSong(songsFile.get(i),albumid);
                }
                else {
                    albumid=db.getAlbumIdByName(album.getName());
                    db.incrementNoSongOfAlbum(albumid);
                    db.createSong(songsFile.get(i),albumid);
                }
            }
        }

        AlbumAdapter albumAdapter = new AlbumAdapter(AlbumActivity.this, db.getAllAlbum());

        albumGridView = (GridView) findViewById(R.id.albumgv);
        albumGridView.setAdapter(albumAdapter);
        albumGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedAlbum = db.getAllAlbum().get(position).getName();
                Util.album = db.getAllAlbum().get(position);
                Util.context=getApplicationContext();
                Toast.makeText(getApplicationContext(), selectedAlbum + " " + "selected.", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), SongListActivity.class);
                startActivity(intent);
            }
        });

    }

    private ArrayList<Song> findSongs(File root) {
        ArrayList<Song> songsFile = new ArrayList<Song>();
        File[] files = root.listFiles();
        for (File singleFile : files) {
            if (singleFile.isDirectory() && !singleFile.isHidden()) {
                songsFile.addAll(findSongs(singleFile));
            } else {
                if (singleFile.getName().endsWith(".mp3")) {
                    Song song = new Song();
                    MediaMetadataRetriever m = new MediaMetadataRetriever();
                    m.setDataSource(singleFile.getPath());
                    art = m.getEmbeddedPicture();
                    song.setSongImage(art);
                    song.setArtist(m.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST));
                    song.setName(singleFile.getName());
                    song.setAlbum(m.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM));
                    song.setDuration(m.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION));
                    song.setPath(singleFile.getPath());
                    songsFile.add(song);
                }
            }

        }
        return songsFile;
    }
}