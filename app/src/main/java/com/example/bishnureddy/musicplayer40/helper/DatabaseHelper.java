package com.example.bishnureddy.musicplayer40.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.bishnureddy.musicplayer40.model.Album;
import com.example.bishnureddy.musicplayer40.model.Song;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bishnu.Reddy on 4/15/2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASENAME="dbMusicPlayer";
    private static final int DATABASE_VERSION=1;

    private static final String TABLE_ALBUM="tblalbum";
    private static final String TABLE_SONG="tblsong";
    private static final String TABLE_ALBUMSONGBRIDGE="tblalbumsongbridge";

    private static final String KEY_ID = "_id";
    private static final String KEY_Name="name";
    private static final String KEY_IMAGE="image";

    private static final String KEY_ALBUMSONG_ALBUMID="_idalbum";
    private static final String KEY_ALBUMSONG_SONGID="_idsong";

    private static final String KEY_ALBUM_NOOFSONG="noofsong";

    private static final String KEY_SONG_ARTIST="artist";
    private static final String KEY_SONG_ALBUMNAME="albumName";
    private static final String KEY_SONG_PATH="path";
    private static final String KEY_SONG_ISPLAYING="isplaying";
    private static final String KEY_SONG_ISADDEDFAV="isaddedfav";
    private static final String KEY_SONG_DURATION="duration";
    private static final String KEY_SONG_ALBUMID="albumid";


    private static final String CREATE_ALBUM_TBL="create table "+TABLE_ALBUM +"("+KEY_ID+" integer primary key autoincrement, "+KEY_Name+" text, "+KEY_ALBUM_NOOFSONG+" integer,"+KEY_IMAGE+" BloB)";
    private static final String CREATE_SONG_TBL="create table "+TABLE_SONG +"("+KEY_ID+" integer primary key autoincrement, "+KEY_Name+" text,"+KEY_SONG_ARTIST+" text,"+KEY_SONG_ALBUMNAME+" text,"+KEY_SONG_PATH+" text,"+KEY_IMAGE+" BloB,"+KEY_SONG_ISPLAYING+" int,"+KEY_SONG_ISADDEDFAV+" int,"+KEY_SONG_DURATION+" int,"+KEY_SONG_ALBUMID+" int)";
    private static final String CREATE_ALBUMSONGBRIDGE_TBL="create table "+TABLE_ALBUMSONGBRIDGE+"("+KEY_ID+" integer primary key autoincrement, "+KEY_ALBUMSONG_ALBUMID+" integer,"+KEY_ALBUMSONG_SONGID+" integer)";

    public DatabaseHelper(Context context)
    {
        super(context,DATABASENAME,null,DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(CREATE_ALBUM_TBL);
        db.execSQL(CREATE_SONG_TBL);
        db.execSQL(CREATE_ALBUMSONGBRIDGE_TBL);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db,int olderversion,int newerversion)
    {

    }

    public long createAlbum(Album album) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_Name, album.getName());
        values.put(KEY_ALBUM_NOOFSONG, album.getNo_Of_Songs());
        values.put(KEY_IMAGE, album.getAlbumImage());

        // insert row
        long album_id = db.insert(TABLE_ALBUM, null, values);
        return album_id;
    }

    public long createAlbumSongBridge(Long albumid,Long songid) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_ALBUMSONG_ALBUMID, albumid);
        values.put(KEY_ALBUMSONG_SONGID, songid);

        // insert row
        long album_id = db.insert(TABLE_ALBUMSONGBRIDGE, null, values);
        return album_id;
    }


    public long createSong(Song song) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_Name, song.getName());
        values.put(KEY_IMAGE, song.getSongImage());
        /*values.put(KEY_SONG_ALBUMID,album_id);*/
        values.put(KEY_SONG_ARTIST, song.getArtist());
        values.put(KEY_SONG_ALBUMNAME, song.getName());
        values.put(KEY_SONG_DURATION, song.getDuration());
        values.put(KEY_SONG_ISADDEDFAV, song.getIsAddedToFav());
        values.put(KEY_SONG_ISPLAYING, song.getIsPlaying());
        values.put(KEY_SONG_PATH, song.getPath());

        // insert row
        long song_id = db.insert(TABLE_SONG, null, values);
        return song_id;
    }


public List<Song> getSongListByAlbumId(long albumid)
{
   /* String selectQuery="select * from "+TABLE_SONG+" tbl where tbl."+KEY_SONG_ALBUMID+"="+albumid+"";
    List<Song> songList=new ArrayList<Song>();
    SQLiteDatabase db = this.getReadableDatabase();
    Cursor c = db.rawQuery(selectQuery, null);

    // looping through all rows and adding to list
    if (c.moveToFirst()) {
        do {
            Song song = new Song();
            song.setId(c.getInt((c.getColumnIndex(KEY_ID))));
            song.setName((c.getString(c.getColumnIndex(KEY_Name))));
            song.setArtist(c.getString(c.getColumnIndex(KEY_SONG_ARTIST)));
            song.setDuration(c.getString(c.getColumnIndex(KEY_SONG_DURATION)));
            song.setIsAddedToFav(c.getInt(c.getColumnIndex(KEY_SONG_ISADDEDFAV)));
            song.setIsPlaying(c.getInt(c.getColumnIndex(KEY_SONG_ISPLAYING)));
            song.setPath(c.getString(c.getColumnIndex(KEY_SONG_PATH)));
            song.setSongImage(c.getBlob(c.getColumnIndex(KEY_IMAGE)));
            song.setAlbumId(c.getInt(c.getColumnIndex(KEY_SONG_ALBUMID)));
            // adding to todo list
            songList.add(song);
        } while (c.moveToNext());
    }
    */
        String selectQuery="select * from tblsong s where s._id in (select _idsong from tblalbumsongbridge a where a._idalbum="+albumid+")";
        List<Song> songList=new ArrayList<Song>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
    // looping through all rows and adding to list
    if (c.moveToFirst()) {
        do {
            Song song = new Song();
            song.setId(c.getInt((c.getColumnIndex(KEY_ID))));
            song.setName((c.getString(c.getColumnIndex(KEY_Name))));
            song.setArtist(c.getString(c.getColumnIndex(KEY_SONG_ARTIST)));
            song.setDuration(c.getString(c.getColumnIndex(KEY_SONG_DURATION)));
            song.setIsAddedToFav(c.getInt(c.getColumnIndex(KEY_SONG_ISADDEDFAV)));
            song.setIsPlaying(c.getInt(c.getColumnIndex(KEY_SONG_ISPLAYING)));
            song.setPath(c.getString(c.getColumnIndex(KEY_SONG_PATH)));
            song.setSongImage(c.getBlob(c.getColumnIndex(KEY_IMAGE)));
           /* song.setAlbumId(c.getInt(c.getColumnIndex(KEY_SONG_ALBUMID)));*/
            // adding to todo list
            songList.add(song);
        } while (c.moveToNext());
    }
    return songList;
}





    public List<Album> getAllAlbum() {
        List<Album> albumList = new ArrayList<Album>();
        String selectQuery = "SELECT  * FROM " + TABLE_ALBUM;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Album album = new Album();
                album.setId(c.getInt((c.getColumnIndex(KEY_ID))));
                album.setName((c.getString(c.getColumnIndex(KEY_Name))));
                album.setAlbumImage((c.getBlob(c.getColumnIndex(KEY_IMAGE))));
                album.setNo_Of_Songs(c.getInt(c.getColumnIndex(KEY_ALBUM_NOOFSONG)));

                // adding to todo list
                albumList.add(album);
            } while (c.moveToNext());
        }
        return albumList;
    }



    public List<Song> getAllSongs() {
        List<Song> songList = new ArrayList<Song>();
        String selectQuery = "SELECT  * FROM " + TABLE_SONG;

        /*Log.e(LOG, selectQuery);*/

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Song song = new Song();
                song.setId(c.getInt((c.getColumnIndex(KEY_ID))));
                song.setName((c.getString(c.getColumnIndex(KEY_Name))));
                song.setArtist(c.getString(c.getColumnIndex(KEY_SONG_ARTIST)));
                song.setDuration(c.getString(c.getColumnIndex(KEY_SONG_DURATION)));
                song.setIsAddedToFav(c.getInt(c.getColumnIndex(KEY_SONG_ISADDEDFAV)));
                song.setIsPlaying(c.getInt(c.getColumnIndex(KEY_SONG_ISPLAYING)));
                song.setPath(c.getString(c.getColumnIndex(KEY_SONG_PATH)));
                song.setSongImage(c.getBlob(c.getColumnIndex(KEY_IMAGE)));
                song.setAlbumId(c.getInt(c.getColumnIndex(KEY_SONG_ALBUMID)));
                // adding to todo list
                songList.add(song);
            } while (c.moveToNext());
        }

        return songList;
    }

    public int getAlbumIdByName(String albumName)
    {
        int albumid=0;
        String query="select * from "+TABLE_ALBUM+" tbl where tbl."+KEY_Name+"='"+albumName+"'";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(query, null);
        if(c.moveToFirst())
        {
            albumid=c.getInt(c.getColumnIndex(KEY_ID));

        }
        return albumid;
    }

    public Song getSongById(int songId)
    {
        String query="select * from "+TABLE_SONG+" tbl where tbl."+KEY_ID+"='"+songId+"'";
        Song song = new Song();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(query, null);
        if(c.moveToFirst())
        {
            song.setId(c.getInt((c.getColumnIndex(KEY_ID))));
            song.setName((c.getString(c.getColumnIndex(KEY_Name))));
            song.setArtist(c.getString(c.getColumnIndex(KEY_SONG_ARTIST)));
            song.setDuration(c.getString(c.getColumnIndex(KEY_SONG_DURATION)));
            song.setIsAddedToFav(c.getInt(c.getColumnIndex(KEY_SONG_ISADDEDFAV)));
            song.setIsPlaying(c.getInt(c.getColumnIndex(KEY_SONG_ISPLAYING)));
            song.setPath(c.getString(c.getColumnIndex(KEY_SONG_PATH)));
            song.setSongImage(c.getBlob(c.getColumnIndex(KEY_IMAGE)));
            song.setAlbumId(c.getInt(c.getColumnIndex(KEY_SONG_ALBUMID)));
        } while (c.moveToNext());
        return song;
    }



    public void playSong(int songId)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(KEY_SONG_ISPLAYING,1);
        db.update(TABLE_SONG,values,KEY_ID+" = ?",new String[]{String.valueOf(songId)});
    }

    public void pausesong(int songId)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(KEY_SONG_ISPLAYING,0);
        db.update(TABLE_SONG,values,KEY_ID+" = ?",new String[]{String.valueOf(songId)});
    }

    public void addAsFavSong(int songId)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(KEY_SONG_ISADDEDFAV,1);
        db.update(TABLE_SONG,values,KEY_ID+" = ?",new String[]{String.valueOf(songId)});
    }

    public void addAsUnFavSong(int songId)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(KEY_SONG_ISADDEDFAV,0);
        db.update(TABLE_SONG,values,KEY_ID+" = ?",new String[]{String.valueOf(songId)});
    }
public void incrementNoSongOfAlbum(Long albumid)
{
    int noofsong=1;
    String query="select * from "+TABLE_ALBUM+" tbl where tbl."+KEY_ID+"="+albumid+"";
    SQLiteDatabase db=this.getReadableDatabase();
    Cursor cursor=db.rawQuery(query,null);
    if(cursor.moveToFirst())
    {
        noofsong=cursor.getInt(cursor.getColumnIndex(KEY_ALBUM_NOOFSONG));
    }
    SQLiteDatabase dbwr=this.getWritableDatabase();
    ContentValues values=new ContentValues();
    values.put(KEY_ALBUM_NOOFSONG,(noofsong+1));
    dbwr.update(TABLE_ALBUM,values,KEY_ID+" = ?",new String[]{String.valueOf(albumid)});
}

    public void closeDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen())
            db.close();
    }

    public void deleteAllData()
    {
        SQLiteDatabase db= this.getWritableDatabase();
        db.delete(TABLE_ALBUM, null, null);
        db.delete(TABLE_SONG, null, null);
    }
}
