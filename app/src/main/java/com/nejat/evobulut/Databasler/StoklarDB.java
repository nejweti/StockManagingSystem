package com.nejat.evobulut.Databasler;

import android.app.DownloadManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.renderscript.Sampler;
import android.util.Log;

import com.nejat.evobulut.klaslar.Stoklar;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 2/28/2018.
 */

public class StoklarDB extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;

    public static final String DATABASE_NAME = "SAYIM YONETIMI";

    public static final String TABLE_NAME = "Stok";

    public static final String KEY_ID = "stok_id";
    public static final String KEY_ADI = "stok_adi";
    public static final String  KEY_KODU = "stok_kodu";
    public static final String KEY_BIRIMI = "stok_birimi";


    public StoklarDB(Context context) {
        super(context, DATABASE_NAME,null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_STOK_TBL = "Create table if not exists " + TABLE_NAME + "(" + KEY_ID + " INTEGER PRIMARY KEY , " + KEY_ADI + " TEXT , "+KEY_KODU + " TEXT , "+KEY_BIRIMI + " TEXT"  +")";
        db.execSQL(CREATE_STOK_TBL);
        Log.i("info","created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

        onCreate(db);
        Log.i("info","recreated");

    }

    public void addStok(Stoklar stok){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID,stok.getId());
        values.put(KEY_ADI,stok.getStok_adi());
        values.put(KEY_KODU,stok.getStok_kodu());
        values.put(KEY_BIRIMI,stok.getStok_birimi());

        db.insert(TABLE_NAME,null,values);
        db.close();

    }
    public Stoklar getStok(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Stoklar stok = null;

        Cursor cursor = db.query(TABLE_NAME, new String[] {
                KEY_ID,KEY_ADI,KEY_KODU,KEY_BIRIMI},KEY_ID + "=?",new String[]{String.valueOf(id)},null,null,null,null);

        if (cursor.moveToFirst()){
             stok = new Stoklar(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getString(3));

        }
        return  stok;
    }

    public List<Stoklar> getAllStoklar(){
        List<Stoklar> stoklarList = new ArrayList<>();

        String query = "SELECT * FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query,null);

        if(cursor.moveToFirst()){
            do{
                stoklarList.add(new Stoklar(Integer.parseInt(cursor.getString(0)),cursor.getString(1),cursor.getString(2),cursor.getString(3)));
            }while (cursor.moveToNext());
        }

        return  stoklarList;
    }

    public Cursor searchStok(String searchTerm)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {KEY_ADI,KEY_KODU};
        Cursor c = null;
        if(searchTerm != null && searchTerm.length()>0){
            String sql = "SELECT * FROM "+TABLE_NAME+" WHERE "+ KEY_ADI+" LIKE '%" + searchTerm+"%'";
            c = db.rawQuery(sql,null);
            return c;
        }
        c = db.query(TABLE_NAME,columns,null,null,null,null,null);
        return c;
    }

    public int updateStok(Stoklar stok){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values =  new ContentValues();

        values.put(KEY_KODU,stok.getStok_kodu());
        values.put(KEY_BIRIMI,stok.getStok_birimi());
        values.put(KEY_ADI,stok.getStok_adi());

        return db.update(TABLE_NAME,values,KEY_ID+" =?",new String[]{String.valueOf(stok.getId())});
    }

    public void deleteStok(Stoklar stok){
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_NAME,KEY_ID +" =?",new String[]{String.valueOf(stok.getId())});
    }


    public int getStoklarCount() {
        String countQuery = "SELECT  * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();

        // return count
        return count;
    }


}
