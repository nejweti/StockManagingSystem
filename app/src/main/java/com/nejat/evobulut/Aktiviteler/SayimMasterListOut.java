package com.nejat.evobulut.Aktiviteler;

import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.nejat.evobulut.Adapterler.DepolarAdapter;
import com.nejat.evobulut.Databasler.DEPODB;
import com.nejat.evobulut.Databasler.SAYMASDB;
import com.nejat.evobulut.Databasler.StoklarDB;
import com.nejat.evobulut.Fragments.ViewPager;
import com.nejat.evobulut.Fragments.depoSayimEkle;
import com.nejat.evobulut.Kutuphaneler.kutuphaneler;
import com.nejat.evobulut.R;
import com.nejat.evobulut.klaslar.Depolar;
import com.nejat.evobulut.klaslar.Kullanici;
import com.nejat.evobulut.klaslar.Saymas;
import com.nejat.evobulut.klaslar.Stoklar;

import java.util.ArrayList;
import java.util.List;

public class SayimMasterListOut extends AppCompatActivity  implements DepolarAdapter.depoClicked ,View.OnClickListener{
    List<Stoklar> stoklarList = new ArrayList<>();
    List<Saymas> saymasList = new ArrayList<>();
    List<Depolar> depolarList = new ArrayList<>();
    private android.support.design.widget.FloatingActionButton fab;

    StoklarDB stoklarDB;
    SAYMASDB saymasdb;
    DEPODB depoDB;


    ProgressDialog pd;

    DepolarAdapter depolarAdapter;
    LinearLayout warningDepoLinear;
    RecyclerView mRecyclerview;
    SearchView mSearchView;
    kutuphaneler Kutuphaneler;
    depoSayimEkle fragmnet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sayim_yap);
        Kutuphaneler = new kutuphaneler(this);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setTitle("Kesilmiş Fişler");

        mRecyclerview = (RecyclerView) findViewById(R.id.sayim_yap_recycler_view);
        mSearchView = (SearchView) findViewById(R.id.search_view);
        fab  = (android.support.design.widget.FloatingActionButton) findViewById(R.id.fab);
        warningDepoLinear = (LinearLayout) findViewById(R.id.warningdepoLinear);

        stoklarDB = new StoklarDB(getApplicationContext());
        saymasdb =  new SAYMASDB(this);
        fragmnet = new depoSayimEkle();


        createSaymas(getApplicationContext());
//        clearDatabase(getApplicationContext());
//        if(saymasdb.getAllSaymas().size() == 0 ){
//            Kullanici kullanici = (Kullanici) getApplicationContext();
//            kullanici.setWarningalertStatus(View.VISIBLE);
////            warningDepoLinear.setVisibility(kullanici.getWarningDepoStatus());
//        }

        BindtoRecyclerView bindtoRecyclerView = new BindtoRecyclerView();
        bindtoRecyclerView.execute();
        fab.setOnClickListener(this);

        final Kullanici kullanici=(Kullanici)getApplicationContext();
        kullanici.kullaniciclassindakiinterface=new Kullanici.somethingChanged() {
            @Override
            public void changed() {
             new BindtoRecyclerView().execute();
                Toast.makeText(kullanici, "EKLENDI", Toast.LENGTH_SHORT).show();
            }
        };
    }

    @Override
    public void onClick(View v) {
        if(v == fab) {

            android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
            android.support.v4.app.FragmentTransaction transaction = manager.beginTransaction();
            transaction.add(R.id.fragment_container,fragmnet);
            transaction.addToBackStack(null);
            transaction.commit();
            mSearchView.setVisibility(View.GONE);
            fab.hide();
        }
    }

    public void changeAdapterSize(){
//        fragmnet.depoEklendi(new depoSayimEkle.kaydoldulistener() {
//            @Override
//            public void onDepoEklendi(boolean cevap) {
//                if(cevap){
//                    mRecyclerview.setAdapter(depolarAdapter);
//
//                }
//            }
//        });
    }

    @Override
    public void onDepoClicked(int position) {

        ViewPager viewPager = new ViewPager();
        android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.fragment_container,viewPager);
        mSearchView.setVisibility(View.GONE);
        fab.hide();
        transaction.commit();



    }


    public class BindtoRecyclerView extends AsyncTask<String,Void,String>{

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
//                pd = new ProgressDialog(SayimMasterListOut.this);
//                pd.setMessage("Lutfen biraz bekleyin..");
//                pd.setCancelable(false);
//                pd.show();
            }
//        Saymas(saymasdb.getAllSaymas().get(i).getId()
            @Override
            protected String doInBackground(String... strings) {
                saymasList=new ArrayList<>();
                depolarList =new ArrayList<>();
                for(int i =0 ; i< saymasdb.getSayimasCount(); i++){
                    saymasList.add(new Saymas(saymasdb.getAllSaymas().get(i).getId(),saymasdb.getAllSaymas().get(i).getDepo_id(),saymasdb.getAllSaymas().get(i).getTarih(),saymasdb.getAllSaymas().get(i).getAck()));

                }
                for(int i =0;i < depoDB.getAllDepolar().size();i++){
                    depolarList.add(new Depolar(depoDB.getAllDepolar().get(i).getDepo_id(),depoDB.getAllDepolar().get(i).getDepo_adi()));
                    Log.i("depodbbbbb",depoDB.getDepo(depolarList.get(0).getDepo_id()).getDepo_adi());
                }
                return null;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
//                stokSayimAdapter =  new StokSayimAdapter(SayimMasterListOut.this,stoklarList);
                depolarAdapter = new DepolarAdapter(SayimMasterListOut.this,saymasList,depolarList);
                depolarAdapter.setDepoClicker(SayimMasterListOut.this);
                mRecyclerview.setLayoutManager(new LinearLayoutManager(SayimMasterListOut.this));
                mRecyclerview.setAdapter(depolarAdapter);
                mSearchView.setOnQueryTextListener(msearchQuery);
//                pd.dismiss();
            }

            @Override
            protected void onProgressUpdate(Void... values) {
                super.onProgressUpdate(values);

                Log.i("sayim yapma",stoklarDB.getAllStoklar().get(1).getStok_adi());
                Log.i("List",stoklarList.size()+"");

            }


        }


        SearchView.OnQueryTextListener msearchQuery = new SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextSubmit(String query) {
            return false;
        }

        @Override
        public boolean onQueryTextChange(String newText) {
//            depolarAdapter.filter(newText.toString());
            return true;
        }
    };



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.search_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                    finish();
                break;
            case R.id.search_menu:
                SearchView mSearchView = (SearchView) findViewById(R.id.search_view);
                if(mSearchView.isShown()){
                    mSearchView.setVisibility(View.GONE);
                }
                else  mSearchView.setVisibility(View.VISIBLE);
        }
        return super.onOptionsItemSelected(item);

    }


    public void createSaymas(Context context) {
        saymasdb = new SAYMASDB(context);
        depoDB = new DEPODB(context);
        SQLiteDatabase database = saymasdb.getWritableDatabase();
        SQLiteDatabase db = depoDB.getWritableDatabase();
        depoDB.onCreate(db);
        saymasdb.onCreate(database);
        database.close();
        db.close();
    }

    @Override
    protected void onResume() {
        super.onResume();
        saymasdb = new SAYMASDB(this);
        List<Saymas> saymasListCheck = new ArrayList<>();
        for(int i =0 ; i< saymasdb.getSayimasCount(); i++){
            saymasListCheck.add(new Saymas(saymasdb.getAllSaymas().get(i).getId(),saymasdb.getAllSaymas().get(i).getDepo_id(),saymasdb.getAllSaymas().get(i).getTarih(),saymasdb.getAllSaymas().get(i).getAck()));

        }
        if(saymasListCheck.size() == 0){
            new kutuphaneler(this).EvoDialogInterface("Fışiniz yoktur,Eklemek için artıyı basınız", "Fiş bulunmamakata", new kutuphaneler.cikisKarari() {

                @Override
                public void karar(boolean bool) {

                }
            });

        }
    }

//    @Override
//    protected void onPause() {
//        super.onPause();
//        warningDepoLinear.setVisibility(View.GONE);
//    }
}
