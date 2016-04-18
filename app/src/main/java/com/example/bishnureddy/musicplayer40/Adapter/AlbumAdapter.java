package com.example.bishnureddy.musicplayer40.Adapter;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bishnureddy.musicplayer40.R;
import com.example.bishnureddy.musicplayer40.model.Album;

import java.util.List;

public class AlbumAdapter extends ArrayAdapter<Album> {
    private Activity context;
    private List<Album> albumList;
    static class ViewHolder{
        TextView txtTitle;
        TextView SongCount ;
        ImageView albumImage;
    }
   public AlbumAdapter(Activity context, List<Album> albumlist)
{
    super(context,0,albumlist);
    this.context=context;
    this.albumList=albumlist;
}

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        Album album = albumList.get(position);
        ViewHolder viewHolder;
        LayoutInflater inflater = context.getLayoutInflater();
        View convertView=view;
        if(convertView ==null)
        {
            convertView= inflater.inflate(R.layout.activity_each_album, null, true);
            viewHolder=new ViewHolder();
            viewHolder.txtTitle = (TextView) convertView.findViewById(R.id.album_nameTV);
            viewHolder.SongCount = (TextView) convertView.findViewById(R.id.No_Of_SongsTV);
            viewHolder.albumImage = (ImageView) convertView.findViewById(R.id.albumIV);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder=(ViewHolder)convertView.getTag();
        }
        if(album != null) {
            viewHolder.txtTitle.setText(album.getName());
            viewHolder.SongCount.setText("songs:"+""+album.getNo_Of_Songs()+"");
            if(album.getAlbumImage()==null)
            {
                viewHolder.albumImage.setImageResource(R.mipmap.unknownimg);
            }
            else {
                viewHolder.albumImage.setImageBitmap(BitmapFactory.decodeByteArray(album.getAlbumImage(),0,album.getAlbumImage().length));
            }

        }
        return convertView;
    }
}
