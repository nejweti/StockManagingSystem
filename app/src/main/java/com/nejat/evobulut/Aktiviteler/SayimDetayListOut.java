package com.nejat.evobulut.Aktiviteler;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.nejat.evobulut.Adapterler.GecmisStoklar;
import com.nejat.evobulut.Adapterler.StokSayimAdapter;
import com.nejat.evobulut.Databasler.SAYDETDB;
import com.nejat.evobulut.Databasler.StoklarDB;
import com.nejat.evobulut.R;
import com.nejat.evobulut.klaslar.Kullanici;
import com.nejat.evobulut.klaslar.Saydet;
import com.nejat.evobulut.klaslar.Stoklar;

import java.util.ArrayList;
import java.util.List;

public class SayimDetayListOut extends AppCompatActivity implements View.OnClickListener{
    FloatingActionButton fab_Button;
    SAYDETDB saydetdb;
    GecmisStoklar gecmisStoklar;
    List<Saydet> saydetnew = new ArrayList<>();
    Kullanici kullanici;
    List<Stoklar> stoklarDetay =  new ArrayList<>();
    StoklarDB stokdb;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sayim_detay_list_out);
        saydetdb = new SAYDETDB(getApplicationContext());
        stokdb =  new StoklarDB(getApplicationContext());
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview_gecmis_stok);
        fab_Button = (FloatingActionButton) findViewById(R.id.fab_gecmis);
        fab_Button.setOnClickListener(this);

        kullanici = (Kullanici) getApplicationContext();


            if(saydetdb.getSaydetWheresaymasisequal(kullanici.getSaymas_id()) != null){
                saydetnew = saydetdb.getSaydetWheresaymasisequal(kullanici.getSaymas_id());
        }
        for(int i = 0;i < saydetnew.size();i++){
            Stoklar stokobj = stokdb.getStok(saydetnew.get(i).getStok_id());
            stoklarDetay.add(stokobj);
        }

        gecmisStoklar =  new GecmisStoklar(stoklarDetay,this,saydetnew);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(gecmisStoklar);
    }


    @Override
    public void onClick(View v) {
        if(v == fab_Button){
            startActivity(new Intent(SayimDetayListOut.this,SayimDetay.class));
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
