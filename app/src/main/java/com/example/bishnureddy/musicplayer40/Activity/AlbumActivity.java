package com.example.bishnureddy.musicplayer40.Activity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.bishnureddy.musicplayer40.Adapter.AlbumAdapter;
import com.example.bishnureddy.musicplayer40.Adapter.ArtistAdapter;
import com.example.bishnureddy.musicplayer40.Adapter.SongListAdapter;
import com.example.bishnureddy.musicplayer40.R;
import com.example.bishnureddy.musicplayer40.Utility.Util;
import com.example.bishnureddy.musicplayer40.helper.DatabaseHelper;
import com.example.bishnureddy.musicplayer40.model.Album;
import com.example.bishnureddy.musicplayer40.model.Artist;
import com.example.bishnureddy.musicplayer40.model.Song;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class AlbumActivity extends AppCompatActivity {

    static ArrayList<Song> songList;
    static GridView albumGridView;
    static Button btnAlbumList;
    static Button btnAllSongList;
    static Button btnArtistList;
    static byte[] art;
    static ListView SongListView;
    Album album;
    Artist artist;
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


        ArrayList<Song> songsFile = new ArrayList<Song>();
        songsFile = findSongs(Environment.getExternalStorageDirectory());

        btnAlbumList=(Button)findViewById(R.id.btnlistbyalbum);
        btnAllSongList=(Button)findViewById(R.id.btnallsonglist);
        btnArtistList=(Button)findViewById(R.id.btnlistbyartist);
        SongListView=(ListView)findViewById(R.id.SongLV);
       /* long albumid = db.createAlbum(new Album(songsFile.get(5).getName(),songsFile.get(5).getSongImage(),1));
        db.createSong(songsFile.get(5),albumid);
        List<Song> songs=db.getAllSongs();
        db.closeDB();
*/

      /*  Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.all_list);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        album = new Album("All Songs list", songsFile.size(),stream.toByteArray());
        albumList.add(album);
        db.createAlbum(album);*/



        ArrayList<String> albumNames = new ArrayList<String>();
        ArrayList<Artist> artists=new ArrayList<Artist>();
        ArrayList<String> artistNameList=new ArrayList<String>();
        for (int i = 0; i < songsFile.size(); i++) {
            if (songsFile.get(i).getAlbum() != null && songsFile.get(i).getAlbum().length() > 0) {
                album = new Album();
                long albumid;
                long artistid;
                artist=new Artist();
                album.setName(songsFile.get(i).getAlbum());

                if (!albumNames.contains(album.getName())) {
                    albumNames.add(album.getName());
                    album.setAlbumImage(songsFile.get(i).getSongImage());
                    album.setNo_Of_Songs(1);
                    albumid= db.createAlbum(album);
                    songsFile.get(i).setId((int)db.createSong(songsFile.get(i)));
                    db.createAlbumSongBridge(albumid,(long)songsFile.get(i).getId());
                }
                else {
                    albumid=db.getAlbumIdByName(album.getName());
                    db.incrementNoSongOfAlbum(albumid);
                    songsFile.get(i).setId((int)db.createSong(songsFile.get(i)));
                    db.createAlbumSongBridge(albumid,(long)songsFile.get(i).getId());
                }

                for(String artistname:songsFile.get(i).getArtist().split(","))
                {
                    if(artistNameList.contains(artistname))
                    {
                        artistid= db.getArtistidByName(artistname);
                        db.createArtistSongBridge(artistid,(long)songsFile.get(i).getId());
                    }
                    else {
                        artistNameList.add(artistname);
                        artistid= db.CreateArtist(artistname);
                        db.createArtistSongBridge(artistid,(long)songsFile.get(i).getId());
                    }
                }

            }
        }

        List<Artist> artistList=db.getAllArtists();
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

btnAlbumList.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        btnAlbumList.setClickable(false);
        btnArtistList.setClickable(true);
        btnAllSongList.setClickable(true);
        SongListView.setVisibility(View.INVISIBLE);
        albumGridView.setVisibility(View.VISIBLE);
        AlbumAdapter albumAdapter = new AlbumAdapter(AlbumActivity.this, db.getAllAlbum());
        albumGridView = (GridView) findViewById(R.id.albumgv);
        albumGridView.setAdapter(albumAdapter);
        setTitle("Album List");
        Util.FilterType="album";
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
});

        btnAllSongList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Util.toast("all song list is selected",getApplicationContext());
                albumGridView.setVisibility(View.INVISIBLE);
                SongListView.setVisibility(View.VISIBLE);
                btnAllSongList.setClickable(false);
                btnArtistList.setClickable(true);
                btnAlbumList.setClickable(true);
                Util.context=getApplicationContext();
                SongListAdapter songAdapter=new SongListAdapter(AlbumActivity.this,db.getAllSongs());
                setTitle("All Song List");
                Util.FilterType="album";
               /* ImageView albumImage=(ImageView) findViewById(R.id.BG_albumIV);
                if(album.getAlbumImage()==null)
                {
                    albumImage.setImageResource(R.mipmap.unknownimg);
                }
                else {
                    albumImage.setImageBitmap(BitmapFactory.decodeByteArray(album.getAlbumImage(),0,album.getAlbumImage().length));
                }*/

                SongListView.setAdapter(songAdapter);
                SongListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.all_list);
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                        album = new Album("All Songs list", db.getAllSongs().size(),stream.toByteArray());
                        album.setId(-1);
                        Util.album=album;
                        Util.toast(db.getSongListById().get(i).getName()+" is playing",getApplicationContext());
                        Util.Position=i;
                        Util.context=getApplicationContext();
                        Intent intent = new Intent(getApplicationContext(), SongActivity.class);
                        startActivity(intent);

                    }
                });
            }
        });

        btnArtistList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Util.toast("all Artist list is selected",getApplicationContext());
                btnArtistList.setClickable(false);
                btnAllSongList.setClickable(true);
                btnAlbumList.setClickable(true);
                albumGridView.setVisibility(View.INVISIBLE);
                SongListView.setVisibility(View.VISIBLE);
                ArtistAdapter albumAdapter = new ArtistAdapter(AlbumActivity.this, db.getAllArtists());
                SongListView.setAdapter(albumAdapter);
                setTitle("Artist List");
                Util.FilterType="artis";
                SongListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Util.context=getApplicationContext();
                        Util.FilterType="artist";
                        Util.Position=i;
                        artist = db.getAllArtists().get(i);
                        Util.artist=artist;
                        Intent intent = new Intent(getApplicationContext(), SongListActivity.class);
                        startActivity(intent);
                    }
                });

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