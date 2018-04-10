package com.nejat.evobulut.Databasler;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.nejat.evobulut.klaslar.Depolar;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 2/28/2018.
 */

public class DEPODB extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;

    public static final String DATABASE_NAME = "SAYIM YONETIMI";

    public static final String TABLE_NAME = "DEPOLAR_TBL";

    public static final String  KEY_ID = "id";
    public static final String KEY_ADI = "depo_adi";

    public DEPODB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "( "+KEY_ID+" INTEGER PRIMARY KEY, "+KEY_ADI+ " TEXT"+")";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query = "DROP IF EXISTS" + TABLE_NAME;
        db.execSQL(query);

        onCreate(db);

    }

    public void addDepo(Depolar depo){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_ID,depo.getDepo_id());
        values.put(KEY_ADI,depo.getDepo_adi());

        db.insert(TABLE_NAME,null,values);
    }

    public List<Depolar> getAllDepolar(){
        List<Depolar> depolarList =  new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM " + TABLE_NAME;

        Cursor cursor = db.rawQuery(query,null);

        if(cursor.moveToFirst()){
            do{
              depolarList.add(new Depolar(Integer.parseInt(cursor.getString(0)),cursor.getString(1)));
            }while(cursor.moveToNext());
        }

        return  depolarList;
    }

    public void deleteDepo(Depolar depo){
        SQLiteDatabase db =  this.getWritableDatabase();
        db.delete(TABLE_NAME,KEY_ID+"=?",new String[]{String.valueOf(depo.getDepo_id())});
    }

    public Depolar getDepo(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Depolar depo = null;

        Cursor cursor = db.query(TABLE_NAME, new String[]{
                KEY_ID, KEY_ADI}, KEY_ID + "=?", new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor.moveToFirst()) {
            depo = new Depolar(cursor.getInt(0), cursor.getString(1));
        }
        return depo;

    }
}
