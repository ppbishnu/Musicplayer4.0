package com.example.bishnureddy.musicplayer40.Activity;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.bishnureddy.musicplayer40.Adapter.SongListAdapter;
import com.example.bishnureddy.musicplayer40.R;
import com.example.bishnureddy.musicplayer40.Utility.Util;
import com.example.bishnureddy.musicplayer40.helper.DatabaseHelper;
import com.example.bishnureddy.musicplayer40.model.Album;
import com.example.bishnureddy.musicplayer40.model.Song;

import java.util.List;

public class SongListActivity extends AppCompatActivity {
    ListView SongListView;
    DatabaseHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song);
        Album album= Util.album;
        db=new DatabaseHelper(Util.context);
        List<Song> songList=db.getSongListByAlbumId(album.getId());
        SongListAdapter songAdapter=new SongListAdapter(SongListActivity.this,db.getSongListByAlbumId(album.getId()));
        setTitle(album.getName());
        ImageView albumImage=(ImageView) findViewById(R.id.BG_albumIV);
        if(album.getAlbumImage()==null)
        {
            albumImage.setImageResource(R.mipmap.unknownimg);
        }
        else {
            albumImage.setImageBitmap(BitmapFactory.decodeByteArray(album.getAlbumImage(),0,album.getAlbumImage().length));
        }

        SongListView=(ListView) findViewById(R.id.SongLV);
        SongListView.setAdapter(songAdapter);
        SongListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getApplicationContext(),"clicked",Toast.LENGTH_SHORT).show();
            }
        });

        SongListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Util.toast("ldjfljdf",getApplicationContext());

            }
        });

    }
}
