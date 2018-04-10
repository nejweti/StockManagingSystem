package com.nejat.evobulut.Fragments;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.nejat.evobulut.Adapterler.GecmisStoklar;
import com.nejat.evobulut.Adapterler.StokSayimAdapter;
import com.nejat.evobulut.Aktiviteler.SayimDetay;
import com.nejat.evobulut.Aktiviteler.StokEkle;
import com.nejat.evobulut.Databasler.DEPODB;
import com.nejat.evobulut.Databasler.SAYDETDB;
import com.nejat.evobulut.Databasler.StoklarDB;
import com.nejat.evobulut.Kutuphaneler.kutuphaneler;
import com.nejat.evobulut.R;
import com.nejat.evobulut.klaslar.Kullanici;
import com.nejat.evobulut.klaslar.Saydet;
import com.nejat.evobulut.klaslar.Stoklar;

import java.util.ArrayList;
import java.util.List;


public class DepodakiStokDetay extends Fragment implements View.OnClickListener {
    FloatingActionButton fab_Button;
    SAYDETDB saydetdb;
    GecmisStoklar gecmisStoklar;
    List<Saydet> saydetnew = new ArrayList<>();
    Kullanici kullanici;
    List<Stoklar> stoklarDetay =  new ArrayList<>();
    StoklarDB stokdb;
    RecyclerView recyclerView;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        clearDatabase(getActivity());
        saydetdb = new SAYDETDB(getActivity().getApplicationContext());
        stokdb =  new StoklarDB(getActivity().getApplicationContext());
        View view =  inflater.inflate(R.layout.fragment_depodaki_stok_detay, container, false);
        fab_Button = (FloatingActionButton) view.findViewById(R.id.stok_detay_fab);
        recyclerView = (RecyclerView) view.findViewById(R.id.stok_detay_recycler_view);
        kullanici = (Kullanici) getActivity().getApplicationContext();
        fab_Button.setOnClickListener(this);
        Log.e("saydetdbstart", saydetdb.getAllSAydet().size()+"");

//        if(saydetdb.getSaydetWheresaymasisequal(kullanici.getSaymas_id()) != null){
            saydetnew = saydetdb.getSaydetWheresaymasisequal(kullanici.getSaymas_id());
        Log.e("SAYmasidkull", kullanici.getSaymas_id()+"program devam etti");



        for(int i = 0;i < saydetnew.size();i++){
            Stoklar stokobj = stokdb.getStok(saydetnew.get(i).getStok_id());
            stoklarDetay.add(stokobj);
        }


        Log.e("saydetnew", saydetnew.size()+"");
        Log.e("Stoklardetay", stoklarDetay.size()+"");
        kullanici.setWarningalertStatus(View.VISIBLE);


        gecmisStoklar =  new GecmisStoklar(stoklarDetay,getActivity(),saydetnew);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(gecmisStoklar);

        final StokSayimAdapter.ItemClickListener gecmislistener = new StokSayimAdapter.ItemClickListener() {
            @Override
            public void onItemClickListener(int position) {
                StokSayimUpdate fragmnet = new StokSayimUpdate();
                android.support.v4.app.FragmentManager manager = getChildFragmentManager();

                fragmnet.show(manager,"StokSayim");
                Log.i("pressed detay",position+ "detay pressed");
            }
        };
        gecmisStoklar.setItemClickListener(gecmislistener);


        Log.e("durumadap", "program devam etti");

         kullanici.kullanicigecmisdataupdated  = new Kullanici.gecmisDataAdded()  {
             @Override
             public void onGecmisDataChanged() {

                 saydetnew = new ArrayList<>();
                 stoklarDetay =  new ArrayList<>();
                 saydetnew = saydetdb.getSaydetWheresaymasisequal(kullanici.getSaymas_id());

                 Log.e("SAYmasidkull", kullanici.getSaymas_id()+"program devam etti");

                 for(int i = 0;i < saydetnew.size();i++){
                     Stoklar stokobj = stokdb.getStok(saydetnew.get(i).getStok_id());

                     Log.e("GELEN ID? ",""+saydetnew.get(i).getStok_id());

                     stoklarDetay.add(stokobj);
                 }

                 gecmisStoklar =  new GecmisStoklar(stoklarDetay,getActivity(),saydetnew);
                 recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                 recyclerView.setAdapter(gecmisStoklar);
                 gecmisStoklar.setItemClickListener(gecmislistener);

                 Log.e("durumadap", "program callbackde");

             }
         };

        return view;
    }


    @Override
    public void onClick(View v) {
        if(v == fab_Button){
            getActivity().startActivity(new Intent(getActivity(), StokEkle.class));

//            StokSayimFragmnet fragmnet = new StokSayimFragmnet();
//            FragmentManager manager = getChildFragmentManager();
//            fragmnet.show(manager,"Manager");
        }

    }
    public void clearDatabase(Context context) {
        SAYDETDB saydetDb = new SAYDETDB(context);

        SQLiteDatabase database = saydetDb.getWritableDatabase();

        saydetDb.onCreate(database);
        database.close();


    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        menu.clear();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

//    @Override
//    public void onItemClickListener(int position) {
//        StokSayimFragmnet fragmnet = new StokSayimFragmnet();
//        android.support.v4.app.FragmentManager manager = getChildFragmentManager();
//
//        fragmnet.show(manager,"StokSayim");
//    }

    @Override
    public void onResume() {
        super.onResume();
        saydetnew = saydetdb.getSaydetWheresaymasisequal(kullanici.getSaymas_id());
        if(saydetnew.size() == 0){
            new kutuphaneler(getActivity()).EvoDialogInterface("Fışinizde stok yoktur,Eklemek için artıyı basınız", "Stok bulunmamakata", new kutuphaneler.cikisKarari() {

                @Override
                public void karar(boolean bool) {

                }
            });

        }
    }
}
