package com.example.bishnureddy.musicplayer40.Adapter;

import android.app.Activity;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.bishnureddy.musicplayer40.R;
import com.example.bishnureddy.musicplayer40.helper.DatabaseHelper;
import com.example.bishnureddy.musicplayer40.model.Artist;

import java.util.List;

/**
 * Created by sindhuja.sirigireddy on 4/19/2016.
 */
public class ArtistAdapter extends ArrayAdapter<Artist> {
    public Activity context;
    ViewHolder viewHolder;
    private List<Artist> input;
    static MediaPlayer mediaPlayer;
    DatabaseHelper db;
    Artist artist;

    static class ViewHolder {

        TextView txtTitle;

    }

    public ArtistAdapter(Activity context, List<Artist> artistList) {
        super(context, 0, artistList);
        this.context = context;
        this.input = artistList;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        artist = input.get(position);
        viewHolder = new ViewHolder();
        ;
        db = new DatabaseHelper(context);
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = view;
        if (rowView == null) {
            rowView = inflater.inflate(R.layout.activity_each_artist, null, true);
            viewHolder.txtTitle = (TextView) rowView.findViewById(R.id.artistNameTV);
            rowView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) rowView.getTag();
        }

        if (artist != null) {
            viewHolder.txtTitle.setText(artist.getName());


        }
        return rowView;

    }
}
