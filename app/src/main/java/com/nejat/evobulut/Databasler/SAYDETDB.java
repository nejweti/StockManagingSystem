package com.nejat.evobulut.Databasler;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

import com.android.volley.toolbox.StringRequest;
import com.nejat.evobulut.klaslar.Saydet;
import com.nejat.evobulut.klaslar.Saymas;
import com.nejat.evobulut.klaslar.Stoklar;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 3/1/2018.
 */

public class SAYDETDB extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;

    public static final String DATABASE_NAME = "SAYIM YONETIMI";

    public static final String TABLE_NAME = "SAYDET_TBL";

    public static final String KEY_ID = "id";
    public static final String KEY_SAYMAS_ID = "saymas_id";
    public static final String KEY_STOK_ID = "stok_id";
    public static final String KEY_MIKTAR = "miktar";


    public SAYDETDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query= "CREATE TABLE IF NOT EXISTS " + TABLE_NAME+ "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+KEY_SAYMAS_ID+" INTEGER,"+ KEY_STOK_ID+" INTEGER,"+KEY_MIKTAR+" INTEGER" + ")";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query = "DROP IF EXISTS";
        db.execSQL(query + TABLE_NAME);
        onCreate(db);

    }

    public void addSaydet(Saydet saydet){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

//        values.put(KEY_ID,saydet.getId());
        values.put(KEY_SAYMAS_ID,saydet.getSaymas_id());
        values.put(KEY_STOK_ID,saydet.getStok_id());
        values.put(KEY_MIKTAR,saydet.getMiktar());

        db.insert(TABLE_NAME,null,values);
        db.close();
    }

    public Saydet getSaydet(long id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Saydet saydet = null;

        Cursor cursor = db.query(TABLE_NAME, new String[]{KEY_ID, KEY_SAYMAS_ID, KEY_STOK_ID, KEY_MIKTAR}, KEY_ID + "=?", new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor.moveToFirst()) {
            saydet = new Saydet(cursor.getInt(0), cursor.getInt(1), cursor.getInt(2), cursor.getInt(3));

        }
        return saydet;

    }

    public List<Saydet> getSaydetWheresaymasisequal(long id) {
        SQLiteDatabase db = this.getReadableDatabase();
        List<Saydet> saydetlist = new ArrayList<>();

    Cursor cursor = db.query(TABLE_NAME, new String[]{KEY_ID, KEY_SAYMAS_ID, KEY_STOK_ID, KEY_MIKTAR}, KEY_SAYMAS_ID + "=?", new String[]{String.valueOf(id)}, null, null, null, null);

        if(cursor.moveToFirst()){
        do{
            saydetlist.add( new Saydet(cursor.getInt(0), cursor.getInt(1), cursor.getInt(2), cursor.getInt(3)));
        }while (cursor.moveToNext());
    }
        return saydetlist;

    }

    public Saydet FindIfStokAddedAlready(long masid,long stokid){
        SQLiteDatabase db = this.getReadableDatabase();
        Saydet saydet = null;

        Cursor cursor = db.query(TABLE_NAME, new String[]{KEY_ID, KEY_SAYMAS_ID, KEY_STOK_ID, KEY_MIKTAR}, KEY_SAYMAS_ID + "=?"+" AND " + KEY_STOK_ID +"=?",new String[]{String.valueOf(masid), String.valueOf(stokid)}, null, null, null, null);

        if(cursor.moveToFirst()){
            do{
                saydet = new Saydet(cursor.getInt(0), cursor.getInt(1), cursor.getInt(2), cursor.getInt(3));
            }while (cursor.moveToNext());
        }
        return saydet;
    }

    public List<Saydet> getAllSAydet(){
        List<Saydet> saydetlist = new ArrayList<>();

        String query = "SELECT * FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query,null);

        if(cursor.moveToFirst()){
            do{
                saydetlist.add( new Saydet(cursor.getInt(0), cursor.getInt(1), cursor.getInt(2), cursor.getInt(3)));
            }while (cursor.moveToNext());
        }

        return  saydetlist;
    }
    public int updateSaydet(Saydet saydet){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values =  new ContentValues();

        values.put(KEY_MIKTAR,saydet.getMiktar());
        values.put(KEY_STOK_ID,saydet.getStok_id());
        values.put(KEY_SAYMAS_ID,saydet.getSaymas_id());

        return db.update(TABLE_NAME,values,KEY_ID+" =?",new String[]{String.valueOf(saydet.getId())});
    }

    public void deleteSaydet(Saydet saydet){
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_NAME,KEY_ID +" =?",new String[]{String.valueOf(saydet.getId())});
    }

}
