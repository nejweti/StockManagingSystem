package com.nejat.evobulut.Fragments;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nejat.evobulut.Databasler.DEPODB;
import com.nejat.evobulut.Databasler.SAYDETDB;
import com.nejat.evobulut.Databasler.StoklarDB;
import com.nejat.evobulut.R;
import com.nejat.evobulut.klaslar.Kullanici;
import com.nejat.evobulut.klaslar.Saydet;

import java.util.ArrayList;
import java.util.List;


public class StokSayimUpdate extends DialogFragment implements View.OnClickListener {
    private TextView detayStokAdi, detayStokKodu,detayBirimi,detayDepoAdi,detayStokEskiMiktar,detayYeniBirimi;
    LinearLayout stoksayimLinear;
    private EditText detayStokYeniMiktar;
    private Button detayStokUpdate;
    StoklarDB stoklarDB;
    DEPODB depodb;
    public List<Saydet> saydetList = new ArrayList<>();
    Kullanici kullanici;
    Saydet saydet;
    SAYDETDB saydetdb;
    com.nejat.evobulut.Interfaceler.updateMiktar updateMiktar;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_stok_sayim_update, container, false);

        stoklarDB = new StoklarDB(getContext());
        depodb = new DEPODB(getContext());
        saydetdb = new SAYDETDB(getContext());


        kullanici = (Kullanici) getContext().getApplicationContext();
        createSaydet(getContext());

        detayStokAdi = (TextView) view.findViewById(R.id.updatedetay_stok_kodu);
        detayBirimi = (TextView) view.findViewById(R.id.update_eski_birimi);
        detayYeniBirimi =  (TextView) view.findViewById(R.id.update_yeni_birimi);
        detayStokKodu = (TextView) view.findViewById(R.id.updatedetay_stok_kodu);
        detayStokEskiMiktar = (TextView) view.findViewById(R.id.detay_update_eski_miktar);


//        detayStokEskiMiktar = (TextView)view.findViewById(R.id.detay_eski_miktar);
        detayStokYeniMiktar = (EditText) view.findViewById(R.id.detay_update_miktar);

        detayStokYeniMiktar.requestFocus();
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        detayDepoAdi = (TextView)view.findViewById(R.id.detay_depo_ismi);
        detayStokUpdate = (Button) view.findViewById(R.id.detay_update);

        Log.e("sokid",kullanici.getStok_id()+"");
        detayStokAdi.setText(stoklarDB.getStok(kullanici.getStok_id()).getStok_adi());
        detayStokKodu.setText(stoklarDB.getStok(kullanici.getStok_id()).getStok_kodu());
        detayDepoAdi.setText(depodb.getDepo(kullanici.getDepo_id()).getDepo_adi());
        detayStokEskiMiktar.setText(saydetdb.getSaydet(kullanici.getSaydet_id()).getMiktar()+"");
        String replaced = stoklarDB.getStok(kullanici.getStok_id()).getStok_birimi().replace("null","");
        Log.i("null", stoklarDB.getStok(kullanici.getStok_id()).getStok_birimi() + "");
        detayBirimi.setText(replaced);
        detayYeniBirimi.setText(replaced);


        detayStokUpdate.setOnClickListener(this);
        return view;
    }
//    Saymas(saymasdb.getAllSaymas().get(i).getId()

    @Override
    public void onClick(View v) {
        if(v == detayStokUpdate){
            if(detayStokYeniMiktar.getText().toString().trim().equals("")){
                Toast.makeText(getActivity(),"Lutfen numara giriniz",Toast.LENGTH_LONG).show();
            }
            else {
                saydetdb.updateSaydet(new Saydet((int) kullanici.getSaydet_id(), (int) kullanici.getSaymas_id(), kullanici.getStok_id(), Integer.parseInt(detayStokYeniMiktar.getText().toString().trim())));
//            kullanici.setSaydet_id(saydetdb.id);
                Log.i("saymas", kullanici.getSaymas_id() + "");
                Log.i("saydetLast", saydetdb.getAllSAydet().size() + "");
                Toast.makeText(getActivity(), "Stok Guncenlendi", Toast.LENGTH_LONG).show();
                kullanici.kullanicigecmisdataupdated.onGecmisDataChanged();
                dismiss();

            }

        }
    }


    public void createSaydet(Context context) {
        SQLiteDatabase database = saydetdb.getWritableDatabase();
        saydetdb.onCreate(database);
        database.close();
    }}