package com.nejat.evobulut.Aktiviteler;

import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.os.AsyncTask;
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
import android.widget.Toast;

import com.nejat.evobulut.Adapterler.StokSayimAdapter;
import com.nejat.evobulut.Databasler.DEPODB;
import com.nejat.evobulut.Databasler.SAYDETDB;
import com.nejat.evobulut.Databasler.StoklarDB;
import com.nejat.evobulut.Fragments.StokSayimFragmnet;
import com.nejat.evobulut.Kutuphaneler.kutuphaneler;
import com.nejat.evobulut.R;
import com.nejat.evobulut.klaslar.Depolar;
import com.nejat.evobulut.klaslar.Kullanici;
import com.nejat.evobulut.klaslar.Stoklar;

import java.util.ArrayList;
import java.util.List;

public class SayimDetay extends AppCompatActivity implements StokSayimAdapter.ItemClickListener {

    StoklarDB stoklarDB;
    List<Stoklar> stoklarList = new ArrayList<>();
    ProgressDialog pd;
    SAYDETDB saydetdb;
    StokSayimAdapter stokSayimAdapter;
    RecyclerView recyclerView;
    List<Depolar> depolarList = new ArrayList<>();
    DEPODB depodb;
    SearchView mSearchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sayim_detay);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

        recyclerView = (RecyclerView) findViewById(R.id.sayim_detay_recycler_view);

        stoklarDB = new StoklarDB(getApplicationContext());
        saydetdb = new SAYDETDB(getApplicationContext());
        depodb = new DEPODB(getApplicationContext());
        pd = new ProgressDialog(SayimDetay.this);
        new BindStokToRecyclerView().execute();
        mSearchView = (SearchView) findViewById(R.id.sayim_detay_search_view);

        mSearchView.setOnQueryTextListener(msearchQuery);


//        final Kullanici kullanici=(Kullanici)getApplicationContext();
//        kullanici.kullaniciclassindakiinterface=new Kullanici.somethingChanged() {
//            @Override
//            public void changed() {
//                new BindStokToRecyclerView().execute();
//                Toast.makeText(kullanici, "EKLENDI", Toast.LENGTH_SHORT).show();
//            }
//        };


    }

    @Override
    public void onItemClickListener(int position) {
        Kullanici kullanici = (Kullanici) getApplicationContext();
        StokSayimFragmnet fragmnet = new StokSayimFragmnet();
        android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
        fragmnet.show(manager,"TT");
        Log.i("depoid",kullanici.getDepo_id()+"");
        Log.i("stokid",kullanici.getStok_id()+"");
    }

    public class BindStokToRecyclerView extends AsyncTask<String,Void,String>{
        @Override
        protected String doInBackground(String... strings) {
            for(int i = 0;i<stoklarDB.getStoklarCount();i++){
                stoklarList.add(new Stoklar(stoklarDB.getAllStoklar().get(i).getId(),stoklarDB.getAllStoklar().get(i).getStok_adi(),stoklarDB.getAllStoklar().get(i).getStok_kodu(),stoklarDB.getAllStoklar().get(i).getStok_birimi()));

            }
            for(int i = 0 ; i < depodb.getAllDepolar().size();i++){
                depolarList.add(new Depolar(depodb.getAllDepolar().get(i).getDepo_id(),depodb.getAllDepolar().get(i).getDepo_adi()));
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd.setCancelable(false);
            pd.setMessage("Lustef biraz bekleyin..");
            pd.show();

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            stokSayimAdapter = new StokSayimAdapter(SayimDetay.this,stoklarList,depolarList);
            stokSayimAdapter.setItemClickListener(SayimDetay.this);
            recyclerView.setLayoutManager(new LinearLayoutManager(SayimDetay.this));
            recyclerView.setAdapter(stokSayimAdapter);
            pd.dismiss();
        }
    }
    SearchView.OnQueryTextListener msearchQuery = new SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextSubmit(String query) {
            return false;
        }

        @Override
        public boolean onQueryTextChange(String newText) {
            stokSayimAdapter.filter(newText.toString());
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
        kutuphaneler kutuphane = new kutuphaneler(SayimDetay.this);
        switch (item.getItemId()){
            case android.R.id.home:
                kutuphane.EvoDialogInterface("Uyari", "Cikmak ister misiniz ?", new kutuphaneler.cikisKarari() {

                    @Override
                    public void karar(boolean bool) {
                        if(bool)
                            finish();
                    }

                });
                break;
            case R.id.search_menu:

                if(mSearchView.isShown()){
                    mSearchView.setVisibility(View.GONE);
                }
                else  mSearchView.setVisibility(View.VISIBLE);
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    public void onBackPressed() {
       finish();
    }
}
