package com.example.bishnureddy.musicplayer40.Activity;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bishnureddy.musicplayer40.R;
import com.example.bishnureddy.musicplayer40.Utility.Util;
import com.example.bishnureddy.musicplayer40.helper.DatabaseHelper;
import com.example.bishnureddy.musicplayer40.model.Album;
import com.example.bishnureddy.musicplayer40.model.Artist;
import com.example.bishnureddy.musicplayer40.model.Song;

import java.util.List;

/**
 * Created by Bishnu.Reddy on 4/16/2016.
 */
public class SongActivity extends AppCompatActivity implements View.OnClickListener{
    DatabaseHelper db;
    static Song song;
    static MediaPlayer mediaPlayer;
    Thread updateseekbar;
    SeekBar seekBar;

@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.each_song_play);
    Album album= Util.album;
    ImageButton btnply=(ImageButton) findViewById(R.id.btnplay);
    ImageButton btnnext=(ImageButton) findViewById(R.id.btnnext);
    ImageButton btnprv=(ImageButton) findViewById(R.id.btnprv);
    ImageButton btnback=(ImageButton) findViewById(R.id.btnbackward);
    ImageButton btnforward=(ImageButton) findViewById(R.id.btnforward);
    seekBar=(SeekBar)findViewById(R.id.idseekbar);
    btnback.setOnClickListener(this);
    btnply.setOnClickListener(this);
    btnnext.setOnClickListener(this);
    btnforward.setOnClickListener(this);
    btnprv.setOnClickListener(this);
    db=new DatabaseHelper(Util.context);
    song=db.getSongListById().get(Util.Position);
    setTitle(song.getName());
    ImageView backgndImg=(ImageView) findViewById(R.id.BG_albumIV);
    ImageView songimg=(ImageView) findViewById(R.id.imgvwsong);
    TextView songnamemarquee=(TextView) findViewById(R.id.songnamemarquee);
    songnamemarquee.setText(song.getName());
    songnamemarquee.setSelected(true);
    updateseekbar=new Thread() {
    @Override
        public void run()
    {
        int duration=mediaPlayer.getDuration();
        int currentPosition=0;

        while (currentPosition<duration)
        {
            try{
                sleep(500);
                currentPosition=mediaPlayer.getCurrentPosition();
                seekBar.setProgress(currentPosition);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    };
    if(Util.FilterType=="album")
    {
        if(album.getAlbumImage()==null || song.getSongImage()==null)
        {
            backgndImg.setImageResource(R.mipmap.unknownimg);
            songimg.setImageResource(R.mipmap.unknownimg);
        }
        else {
            backgndImg.setImageBitmap(BitmapFactory.decodeByteArray(song.getSongImage(),0,song.getSongImage().length));
            songimg.setImageBitmap(BitmapFactory.decodeByteArray(song.getSongImage(),0,song.getSongImage().length));
        }
    }
    else {
        if(song.getSongImage()==null)
        {
            backgndImg.setImageResource(R.mipmap.unknownimg);
            songimg.setImageResource(R.mipmap.artist);
        }
        else {
            backgndImg.setImageBitmap(BitmapFactory.decodeByteArray(song.getSongImage(),0,song.getSongImage().length));
            songimg.setImageBitmap(BitmapFactory.decodeByteArray(song.getSongImage(),0,song.getSongImage().length));
        }

    }

    if(mediaPlayer!=null) {
        mediaPlayer.stop();
        mediaPlayer.release();
        db.playSong(song.getId());
        Util.currentPlayingSongId = song.getId();
    }
    mediaPlayer = MediaPlayer.create(this, Uri.parse(song.getPath()));
    mediaPlayer.start();
    seekBar.setMax(mediaPlayer.getDuration());
    updateseekbar.start();
    seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar skBar) {
        mediaPlayer.seekTo(skBar.getProgress());
        }
    });
    db.playSong(song.getId());
    Util.currentPlayingSongId = song.getId();

}

    @Override
    public void onClick(View view) {
    int vwid=view.getId();

        switch (vwid)
        {
            case R.id.btnprv:{
                mediaPlayer.stop();
                mediaPlayer.release();
                Util.Position=Util.Position-1<0?db.getSongListById().size()-1:Util.Position-1;
                song=db.getSongListById().get(Util.Position);
                mediaPlayer=MediaPlayer.create(this,Uri.parse(song.getPath()));
                mediaPlayer.start();
                seekBar.setMax(mediaPlayer.getDuration());
                break;
            }
            case R.id.btnbackward:{
                mediaPlayer.seekTo(mediaPlayer.getCurrentPosition()-5000);
                break;
            }
            case R.id.btnplay:{

               song=db.getSongById(song.getId());
               /* if(mediaPlayer != null && mediaPlayer.isPlaying()) {
                    if(song.getId()!=Util.currentPlayingSongId)
                    {
                        db.pausesong(Util.currentPlayingSongId);
                    }
                    mediaPlayer.stop();
                    mediaPlayer.reset();
                }*/
                if(song.getIsPlaying()==0) {
                    mediaPlayer = MediaPlayer.create(this, Uri.parse(song.getPath()));
                    if(!mediaPlayer.isPlaying())
                    {
                        mediaPlayer.start();
                    }
                    Toast.makeText(getApplicationContext(), song.getName() + " is playing", Toast.LENGTH_SHORT).show();
                    ((ImageView)view).setImageResource(R.mipmap.pause);
                    db.playSong(song.getId());
                    Util.currentPlayingSongId=song.getId();
                }
                else
                {
                    if(mediaPlayer.isPlaying())
                    {
                        mediaPlayer.stop();
                    }
                    ((ImageView)view).setImageResource(R.mipmap.play);
                    db.pausesong(song.getId());
                }
                break;
            }
            case R.id.btnforward:{
                mediaPlayer.seekTo(mediaPlayer.getCurrentPosition()+ 5000);
                break;
            }
            case R.id.btnnext:{
                mediaPlayer.stop();
                mediaPlayer.release();
                Util.Position=Util.Position+1%db.getSongListById().size();
                song=db.getSongListById().get(Util.Position);
                mediaPlayer=MediaPlayer.create(this,Uri.parse(song.getPath()));
                mediaPlayer.start();
                seekBar.setMax(mediaPlayer.getDuration());
                break;
            }
            default:{
                break;
            }
        }
    }
}
