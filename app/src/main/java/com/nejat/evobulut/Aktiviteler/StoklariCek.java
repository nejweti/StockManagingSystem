package com.nejat.evobulut.Aktiviteler;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.StringRequest;
import com.nejat.evobulut.Adapterler.StokSayimAdapter;
import com.nejat.evobulut.Databasler.DEPODB;
import com.nejat.evobulut.Databasler.StoklarDB;
import com.nejat.evobulut.Kutuphaneler.kutuphaneler;
import com.nejat.evobulut.R;
import com.nejat.evobulut.klaslar.Depolar;
import com.nejat.evobulut.klaslar.Stoklar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class StoklariCek extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    StoklarDB stoklarDB;
    Stoklar stok;
    DEPODB depoDB;
    Depolar depo;
    ArrayList<Depolar> depolarList =  new ArrayList<>();
    ArrayList<Stoklar> stoklarList = new ArrayList<>();
    StokSayimAdapter stokSayimAdapter;
    RecyclerView mRecyclerview;
    SearchView mSearchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stoklari_cek);


        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        mRecyclerview = (RecyclerView) findViewById(R.id.stok_cek_recycler_view);
        clearDatabase(getApplicationContext());
        stokData();
//        depoData();

    }


    public void clearDatabase(Context context) {
        stoklarDB = new StoklarDB(context);
        depoDB = new DEPODB(context);
        SQLiteDatabase database = stoklarDB.getWritableDatabase();
        SQLiteDatabase depodatabase = depoDB.getWritableDatabase();
        database.execSQL("DROP TABLE IF EXISTS " + stoklarDB.TABLE_NAME);
        depodatabase.execSQL("DROP TABLE IF EXISTS " + depoDB.TABLE_NAME);
        depoDB.onCreate(depodatabase);
        stoklarDB.onCreate(database);
        database.close();
        depodatabase.close();

    }

    public void depoData(){
        kutuphaneler kutuphaneler=new kutuphaneler(StoklariCek.this);
        Log.i("inof","we are in depocekme");

        sharedPreferences = getSharedPreferences("kullaniciData",MODE_PRIVATE);
        String url = "http://www.evobulut.com:4000/?mdl=stok&cmd=jq_depo_list_full&UID=" + sharedPreferences.getString("UID","");
        kutuphaneler.EvoData(url, Request.Method.GET, new kutuphaneler.EvoDataCallback() {

            @Override
            public void cevap(String cevap) {
                try {
                    JSONArray jsonArray = new JSONArray(cevap);
                    JSONObject jsonObject = null;

                    for(int i = 0 ; i < jsonArray.length() ;i++){
                        jsonObject = (JSONObject) jsonArray.get(i);
                        depo = new Depolar(jsonObject.getInt("a_id"),jsonObject.getString("a_adi"));
                        depoDB.addDepo(depo);
                        depolarList.add(depo);
//                        Log.i("depo info",depoDB.TABLE_NAME);
//                        Log.i("database",depoDB.DATABASE_NAME);

                    }
                    Log.i("cevap",cevap);
                }

                catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void hata(String hata) {

                Log.i("error","you are on fire");
            }
        });
    }

    public void stokData(){
            kutuphaneler kutuphaneler=new kutuphaneler(StoklariCek.this);
            Log.i("inof","we are in stokcekme");

            sharedPreferences = getSharedPreferences("kullaniciData",MODE_PRIVATE);
            String url = "http://www.evobulut.com:4000/?mdl=stok&cmd=jq_stok_list_full&UID=" + sharedPreferences.getString("UID","");
            kutuphaneler.EvoData(url, Request.Method.GET, new kutuphaneler.EvoDataCallback() {

                @Override
                public void cevap(String cevap) {
                    Log.d("info",stoklarDB.TABLE_NAME+"  Size  " + stoklarDB.getStoklarCount());
                    try {
                        JSONArray jsonArray = new JSONArray(cevap);
                        JSONObject jsonObject = null;

                        for(int i = 0 ; i < jsonArray.length() ;i++){
                            jsonObject = (JSONObject) jsonArray.get(i);
                            stok = new Stoklar(jsonObject.getInt("a_id"),jsonObject.getString("a_adi"),jsonObject.getString("a_kod"),jsonObject.getString("Brm_Adi"));
                            stoklarDB.addStok(stok);
                            stoklarList.add(stok);
                        }
                        Log.i("cevap",cevap);
                    }

                    catch (Exception e){
                        e.printStackTrace();
                    }
                }

                @Override
                public void hata(String hata) {

                    Log.i("error","you are on fire");
                }
            });
    }



        @Override
        public boolean onSupportNavigateUp(){
            kutuphaneler.EvoDialogInterface("Uyari", "Cikmak ister misiniz ?", new kutuphaneler.cikisKarari() {

                @Override
                public void karar(boolean bool) {
                    if(bool)
                        finish();

                }

            });
            return true;
        }


}
