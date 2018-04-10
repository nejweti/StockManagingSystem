package com.nejat.evobulut.Aktiviteler;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nejat.evobulut.Adapterler.RecycleViewAdapter;
import com.nejat.evobulut.Databasler.DEPODB;
import com.nejat.evobulut.Databasler.StoklarDB;
import com.nejat.evobulut.Fragments.depoSayimEkle;
import com.nejat.evobulut.Interfaceler.ClickListener;
import com.nejat.evobulut.Kutuphaneler.kutuphaneler;
import com.nejat.evobulut.R;
import com.nejat.evobulut.klaslar.Depolar;
import com.nejat.evobulut.klaslar.Kullanici;
import com.nejat.evobulut.klaslar.Stoklar;
import com.nejat.evobulut.klaslar.TinyDB;
import com.nejat.evobulut.klaslar.menuSayfa;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity implements ClickListener {

    StoklarDB stoklarDB;
    Stoklar stok;
//    ArrayList<Stoklar> stoklarList = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private RecycleViewAdapter mAdapter;
    DEPODB depoDB;
    Depolar depo;
    ArrayList<Depolar> depolarList =  new ArrayList<>();
    ArrayList<Stoklar> stoklarList =  new ArrayList<>();
    private RecyclerView.LayoutManager mLayoutManager;
    ArrayList<menuSayfa> menuList =new ArrayList<>();
    SharedPreferences sharedPreferences;
    SharedPreferences sharedStok;
    TextView kullaniciTextView;
    depoSayimEkle fragment;
    Kullanici kullanici;
    TinyDB tinydb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        ImageView imageView = (ImageView) findViewById(R.id.profilepic);
        kullanici = (Kullanici) getApplicationContext();
        CircleImageView circleImageView = (CircleImageView) findViewById(R.id.profilepic);

        sharedPreferences = getSharedPreferences("kullaniciData",MODE_PRIVATE);
        sharedStok = getSharedPreferences("sharedStok",MODE_PRIVATE);





        ActionBar actionBar = getSupportActionBar();
//        actionBar.setDisplayHomeAsUpEnabled(true);
//        actionBar.setHomeButtonEnabled(true);

        tinydb = new TinyDB(this);
        kullaniciTextView = (TextView) findViewById(R.id.mainKullaniAdi);
        kullaniciTextView.setText(sharedPreferences.getString("kullanici_adi","Kullanici Adi"));
        int id = sharedPreferences.getInt("id",1);

        fragment = new depoSayimEkle();

        String url = "https://web.evobulut.com/profilepic/"+ id +"/L" + id + ".jpg";
        Picasso.with(this).load(url).placeholder(R.mipmap.ic_launcher).into(circleImageView, new Callback() {
            @Override
            public void onSuccess() {
                Log.i("imge","loaded");
            }

            @Override
            public void onError() {
                Log.i("image","error happened");
            }
        });



        mRecyclerView = (RecyclerView) findViewById(R.id.main_recycler_view);
        mRecyclerView.setHasFixedSize(true);

        menuList.add(new menuSayfa("Stokları Al", R.drawable.circlebackgroundyello,R.drawable.download));
        menuList.add(new menuSayfa("Sayım Yap", R.drawable.circlebackgrounddarkred,R.drawable.count));
        menuList.add(new menuSayfa("Stokları Gonder", R.drawable.circlebackgroundgreen,R.drawable.upload));
        menuList.add(new menuSayfa("Çıkış", R.drawable.circlebackgrounddeeppurple,R.drawable.logout));

        mAdapter = new RecycleViewAdapter(this, menuList);
        mAdapter.setClickListener(this);

        mRecyclerView.setAdapter(mAdapter);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        mRecyclerView.setLayoutManager(gridLayoutManager);

        actionBar.setTitle("STOK SAYIMA HOSGELDINIZ");
        actionBar.setSubtitle(sharedPreferences.getString("kullanici_adi","Kullanici Adi"));

    }


    @Override
    public void itemClickListener(View view, int position) {
        switch (position){
            case 0 :
//                   startActivity(new Intent(MainActivity.this, StoklariCek.class));
                    clearDatabase(getApplicationContext());
                    depoData();
                    stokData();
                      Log.i("info","clicked");

                break;
            case 1:

                Log.i("stoktaken",sharedStok.getInt("stoktaken",0)+"");
                if (sharedStok.getInt("stoktaken",0) == 1 && sharedStok.getInt("depotaken",0) == 1){
                    startActivity(new Intent (MainActivity.this,SayimMasterListOut.class));
                }
                else
                    new kutuphaneler(this).EvoDialogInterface("Lutfen once datalari cekiniz", "Uyari", new kutuphaneler.cikisKarari() {
                        @Override
                        public void karar(boolean bool) {
                            if(bool){
                                clearDatabase(getApplicationContext());
                                depoData();
                                stokData();
                            }
                        }
                    });



                break;
            case 2:

                Log.i("info","clicked");
//                android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
//                android.support.v4.app.FragmentTransaction transaction = manager.beginTransaction();
//                fragment.show(manager,"DEPOEKLEFRAGMENT");
//                transaction.addToBackStack(null);
//                transaction.commit();
//                   Log.i("info","clicked");
                break;
            case 3:
                new kutuphaneler(this).EvoDialogInterface("Uyarı", "Çıkmak ister mısınız ?", new kutuphaneler.cikisKarari() {

                    @Override
                    public void karar(boolean bool) {
                        if (bool)
                            finish();
                    }

                });
                break;

        }


    }

    @Override
    public void onBackPressed() {
        new kutuphaneler(this).EvoDialogInterface("Uyari", "Cikmak ister misiniz ?", new kutuphaneler.cikisKarari() {

            @Override
            public void karar(boolean bool) {
                if (bool)
                    finish();
            }

        });

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


    public void stokData(){
        kutuphaneler kutuphaneler=new kutuphaneler(MainActivity.this);
        Log.i("inof","we are in stokcekme");
        SharedPreferences.Editor editor = sharedStok.edit();
        editor.putInt("stoktaken",1);
        editor.commit();



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

                    new copyToTinyDB().execute(stoklarList).get();
                    ArrayList<Stoklar> StoklarList = getArrayList("StokinSharedPreference",MainActivity.this);
                    Log.i("stoklarafterget",StoklarList.size()+"");
                    Toast.makeText(MainActivity.this,"Stoklar alinmistir",Toast.LENGTH_LONG).show();
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

    public void depoData(){
        kutuphaneler kutuphaneler=new kutuphaneler(MainActivity.this);
        Log.i("inof","we are in depocekme");
        SharedPreferences.Editor editor = sharedStok.edit();
        editor.putInt("depotaken",1);
        editor.commit();


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
                        Log.i("depo adi",depoDB.getAllDepolar().get(i).getDepo_adi());
                        depolarList.add(depo);
                    }

                    Log.i("depo size",depolarList.size()+"");
                    Log.i("depo info",depoDB.TABLE_NAME);
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

    public class copyToTinyDB extends AsyncTask<ArrayList<Stoklar>,Void,ArrayList<Stoklar>>{

        @Override
        protected ArrayList<Stoklar> doInBackground(ArrayList<Stoklar>[] arrayLists) {

            saveArrayList(arrayLists[0],"StokinSharedPreference");
            return null;
        }
    }
    public void saveArrayList(ArrayList<Stoklar> list, String key){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(list);
        editor.putString(key, json);
        editor.apply();     // This line is IMPORTANT !!!
    }

    public ArrayList<Stoklar> getArrayList(String key, Activity activity){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(activity);
        Gson gson = new Gson();
        String json = prefs.getString(key, null);
        Type type = new TypeToken<ArrayList<Stoklar>>() {}.getType();
        return gson.fromJson(json, type);
    }

}
