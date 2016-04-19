package com.example.bishnureddy.musicplayer40.Adapter;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bishnureddy.musicplayer40.R;
import com.example.bishnureddy.musicplayer40.Utility.Util;
import com.example.bishnureddy.musicplayer40.helper.DatabaseHelper;
import com.example.bishnureddy.musicplayer40.model.Song;

import java.util.List;

public class SongListAdapter extends ArrayAdapter<Song>  {
    public Activity context;
    ViewHolder viewHolder;
    private List<Song> input;
    static MediaPlayer mediaPlayer;
    DatabaseHelper db;
    Song song;
    static class ViewHolder{
        ImageView playButton;
        ImageView favButton;
        TextView txtTitle;
        TextView artists;
        TextView duration;
        ImageView songImage;
    }
    public SongListAdapter(Activity context, List<Song> songslist)
    {
        super(context,0,songslist);
        this.context=context;
        this.input=songslist;
    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        song= input.get(position);
        viewHolder = new ViewHolder();;
        db=new DatabaseHelper(context);
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView=view;
        if(rowView ==null)
        {
            rowView= inflater.inflate(R.layout.activity_each_song, null, true);

           /* viewHolder.playButton =(ImageView)rowView.findViewById(R.id.playIB);*/
            viewHolder.songImage=(ImageView)rowView.findViewById(R.id.songimageIV);
            viewHolder.favButton =(ImageView)rowView.findViewById(R.id.AddToFavIB);
            viewHolder.txtTitle = (TextView) rowView.findViewById(R.id.songNameTV);
            viewHolder.artists = (TextView) rowView.findViewById(R.id.artists);
            viewHolder.duration = (TextView) rowView.findViewById(R.id.Duration);
            rowView.setTag(viewHolder);
        }
        else
        {
            viewHolder =(ViewHolder)rowView.getTag();
        }

        if(song != null) {
            viewHolder.txtTitle.setText(song.getName());
            viewHolder.artists.setText(song.getArtist());
            viewHolder.duration.setText(song.getDurationString());
            if(song.getSongImage()!=null)
            {
                viewHolder.songImage.setImageBitmap(BitmapFactory.decodeByteArray(song.getSongImage(),0,song.getSongImage().length));
            }
            else {
                viewHolder.songImage.setImageResource(R.mipmap.unknownimg);
            }
            /*if(song.getIsPlaying()==1)
            {

                viewHolder.playButton.setImageResource(R.mipmap.pause);
            }
            else {
                viewHolder.playButton.setImageResource(R.mipmap.play);
            }*/
            song=db.getSongById(song.getId());
            if(song.getIsAddedToFav()==1)
            {
                viewHolder.favButton.setImageResource(R.mipmap.like_filled);
            }
            else {
                viewHolder.favButton.setImageResource(R.mipmap.like);
            }
        }


       /* viewHolder.playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                song=db.getSongById(song.getId());
                if(mediaPlayer != null && mediaPlayer.isPlaying()) {
                    if(song.getId()!=Util.currentPlayingSongId)
                    {
                        db.pausesong(Util.currentPlayingSongId);
                    }
                    mediaPlayer.stop();
                    mediaPlayer.reset();
                }
                if(song.getIsPlaying()==0) {
                    mediaPlayer = MediaPlayer.create(context,Uri.parse(song.getPath()));
                    if(!mediaPlayer.isPlaying())
                    {
                        mediaPlayer.start();
                    }
                    Toast.makeText(getContext(), song.getName() + " is playing", Toast.LENGTH_SHORT).show();
                    ((ImageView)v).setImageResource(R.mipmap.pause);
                    db.playSong(song.getId());
                    Util.currentPlayingSongId=song.getId();
                }
                else
                {
                    if(mediaPlayer.isPlaying())
                    {
                        mediaPlayer.stop();
                    }
                    ((ImageView)v).setImageResource(R.mipmap.play);
                    db.pausesong(song.getId());
                }
            }

        });*/

        viewHolder.favButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                song=db.getSongById(song.getId());
                if (song.getIsAddedToFav()==0) {
                    Toast.makeText(getContext(), song.getName() + " is added to favourites", Toast.LENGTH_SHORT).show();
                    ((ImageView)v).setImageResource(R.mipmap.like_filled);
                    db.addAsFavSong(song.getId());
                }
                else {
                    Toast.makeText(getContext(), song.getName() + " is removed from favourites", Toast.LENGTH_SHORT).show();
                    ((ImageView)v).setImageResource(R.mipmap.like);
                    db.addAsUnFavSong(song.getId());
                }
            }
        });
        return rowView;
    }
}