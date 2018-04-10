package com.nejat.evobulut.Aktiviteler;

import android.Manifest;
import android.app.ActionBar;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
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
import android.widget.Button;
import android.widget.Toast;

import com.google.zxing.Result;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.nejat.evobulut.Adapterler.ArananStokAdapter;
import com.nejat.evobulut.Adapterler.StokSayimAdapter;
import com.nejat.evobulut.Databasler.DEPODB;
import com.nejat.evobulut.Databasler.SAYDETDB;
import com.nejat.evobulut.Databasler.StoklarDB;
import com.nejat.evobulut.Fragments.StokSayimFragmnet;
import com.nejat.evobulut.Fragments.StokSayimUpdate;
import com.nejat.evobulut.Kutuphaneler.kutuphaneler;
import com.nejat.evobulut.R;
import com.nejat.evobulut.klaslar.Depolar;
import com.nejat.evobulut.klaslar.Saydet;
import com.nejat.evobulut.klaslar.Stoklar;

import java.util.ArrayList;
import java.util.List;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class StokEkle extends AppCompatActivity implements SearchView.OnQueryTextListener,StokSayimAdapter.ItemClickListener, View.OnClickListener,ArananStokAdapter.onEklendiTrue{

    StoklarDB stoklarDB;
    List<Stoklar> stoklarList = new ArrayList<>();
    ProgressDialog pd;
    SAYDETDB saydetdb;
    ArananStokAdapter arananStokAdapter;
    RecyclerView recyclerView;
    List<Depolar> depolarList = new ArrayList<>();
    DEPODB depodb;
    SearchView mSearchView;
    Button barkodButton;
    ArrayList<Stoklar> StoklarList = new ArrayList<>();
    MainActivity mainActivity = new MainActivity();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stok_ekle);
        checkPermission();

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        Log.i("actionbar","actionbarrrstokekle");
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Stok Ekle");

        recyclerView = (RecyclerView) findViewById(R.id.stok_ekle_recycler_view);
        mSearchView = (SearchView) findViewById(R.id.stok_ekle_search_view);
        barkodButton = (Button) findViewById(R.id.barkode_button);
        mSearchView.setOnQueryTextListener(this);
        stoklarDB = new StoklarDB(getApplicationContext());
        saydetdb = new SAYDETDB(getApplicationContext());
        depodb = new DEPODB(getApplicationContext());
        barkodButton.setOnClickListener(this);

        StoklarList = mainActivity.getArrayList("StokinSharedPreference",this);
        for(int i = 0 ; i < 10 ; i++){
            stoklarList.add(StoklarList.get(i));
        }
        Log.i("stoklarafterget",StoklarList.size()+"");


        arananStokAdapter = new ArananStokAdapter(this,stoklarList,StoklarList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(arananStokAdapter);
        arananStokAdapter.setItemClickListener(this);
        arananStokAdapter.setOnEklendi(this);

    }

    private void getStoklar(String searchTerm){

    }

    @Override
    public boolean onQueryTextSubmit(String query) {

        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        arananStokAdapter.filter(newText);
        return false;
    }

    @Override
    public void onItemClickListener(int position) {
        StokSayimFragmnet fragmnet = new StokSayimFragmnet();
        android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
        fragmnet.show(manager,"StokSayim");
    }

    public void checkPermission(){
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED){
            Log.i("CheckPermission","Permission Granted");

        }
        else {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA},123);
        }
    }

    @Override
    public void onClick(View v) {
        if(v == barkodButton){
            IntentIntegrator integrator = new IntentIntegrator(this);
            integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
            integrator.setPrompt("Scan");
            integrator.setCameraId(0);
            integrator.setBeepEnabled(false);
            integrator.setBarcodeImageEnabled(false);
            integrator.initiateScan();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() == null) {
                Log.d("MainActivity", "Cancelled scan");
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            } else {
                Log.d("MainActivity", "Scanned");
                mSearchView.setQuery(result.getContents(),false);
                Toast.makeText(this, "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.search_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {

            finish();
        }
        if(item.getItemId() == R.id.search_menu){
            if(mSearchView.isShown()){
                mSearchView.setVisibility(View.GONE);
            }
            else  mSearchView.setVisibility(View.VISIBLE);

    }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onEklendi() {
        new kutuphaneler(this).EvoDialogInterface(  "Stok Eklenmiştir Guncelemek ister mısınız?", "Uyari..", new kutuphaneler.cikisKarari() {
            @Override
            public void karar(boolean bool) {
                if(bool){
//                                saydetdb.updateSaydet(new Saydet((int) kullanici.getSaydet_id(), (int) kullanici.getSaymas_id(), kullanici.getStok_id(), Integer.parseInt(detayStokYeniMiktar.getText().toString().trim())));
//                                Toast.makeText(getActivity(), "Stok Guncenlendi", Toast.LENGTH_LONG).show();
//                                kullanici.kullanicigecmisdataupdated.onGecmisDataChanged();

//                                kullanici.setSaydet_id(id);
                    StokSayimUpdate fragment = new StokSayimUpdate();
                    android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
                    fragment.show(manager,"Fragment");

                }
            }
        });

    }
}

