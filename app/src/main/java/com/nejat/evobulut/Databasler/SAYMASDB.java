package com.nejat.evobulut.Databasler;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.nejat.evobulut.Aktiviteler.Saymaster;
import com.nejat.evobulut.klaslar.Depolar;
import com.nejat.evobulut.klaslar.Kullanici;
import com.nejat.evobulut.klaslar.Saydet;
import com.nejat.evobulut.klaslar.Saymas;
import com.nejat.evobulut.klaslar.Stoklar;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 2/28/2018.
 */

public class SAYMASDB extends SQLiteOpenHelper {
    public static final int  VERSION = 1;

    public static final String DATABASE_NAME = "SAYIM YONETIMI";

    public static final String TABLE_NAME = "SAYMAS_TBL";

    public static final String KEY_ID = "id";
    public static final String KEY_DEPO_ID = "depo_id";
    public static final String KEY_TARIH = "tarih";
    public static final String KEY_ACK = "ack";
    public static long id;


    public SAYMASDB(Context context) {
        super(context,DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("hey","created");
        String query = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "("+ KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+KEY_DEPO_ID+" INTEGER, "+KEY_TARIH+" TEXT,"+KEY_ACK+" TEXT" + ")";
        Log.d("the query is", query);
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

        onCreate(db);
        Log.i("info","recreated");
    }

    public void addSaymas(Saymas saymas){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

//        values.put(KEY_ID,saymas.getId());
        values.put(KEY_DEPO_ID,saymas.getDepo_id());
        values.put(KEY_TARIH,saymas.getTarih());
        values.put(KEY_ACK,saymas.getAck());

       id = db.insert(TABLE_NAME,null,values);
       saymas.setId(id);
    }

    public Saymas getSaymas(long id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Saymas saymas = null;

        Cursor cursor = db.query(TABLE_NAME, new String[]{KEY_ID,
                KEY_DEPO_ID, KEY_TARIH, KEY_ACK}, KEY_ID + "=?", new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor.moveToFirst()) {
            saymas = new Saymas(cursor.getInt(0), cursor.getInt(1), cursor.getString(2), cursor.getString(3));

        }
        return saymas;

    }

    public int getSayimasCount() {
        String countQuery = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();

        return count;
    }

    public List<Saymas> getAllSaymas(){
        List<Saymas> saymasList = new ArrayList<>();

        String query = "SELECT * FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query,null);

        if(cursor.moveToFirst()){
            do{
                saymasList.add(new Saymas(Integer.parseInt(cursor.getString(0)),cursor.getInt(1),cursor.getString(2),cursor.getString(3)));
            }while (cursor.moveToNext());
        }

        return  saymasList;
    }

    public int updateSaymas(Saymas saymas){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values =  new ContentValues();

        values.put(KEY_DEPO_ID,saymas.getDepo_id());
        values.put(KEY_TARIH,saymas.getTarih());
        values.put(KEY_ACK,saymas.getAck());

        return db.update(TABLE_NAME,values,KEY_ID+" =?",new String[]{String.valueOf(saymas.getId())});
    }

    public void deleteSaymas(Saymas saymas){
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_NAME,KEY_ID +" =?",new String[]{String.valueOf(saymas.getId())});
    }
}
