package com.nejat.evobulut.Fragments;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
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

import com.nejat.evobulut.Adapterler.GecmisStoklar;
import com.nejat.evobulut.Aktiviteler.MainActivity;
import com.nejat.evobulut.Aktiviteler.SayimDetay;
import com.nejat.evobulut.Databasler.DEPODB;
import com.nejat.evobulut.Databasler.SAYDETDB;
import com.nejat.evobulut.Databasler.SAYMASDB;
import com.nejat.evobulut.Databasler.StoklarDB;
import com.nejat.evobulut.Interfaceler.updateMiktar;
import com.nejat.evobulut.Kutuphaneler.kutuphaneler;
import com.nejat.evobulut.R;
import com.nejat.evobulut.klaslar.Kullanici;
import com.nejat.evobulut.klaslar.Saydet;
import com.nejat.evobulut.klaslar.Stoklar;
import com.travijuu.numberpicker.library.NumberPicker;

import java.util.ArrayList;
import java.util.List;


public class StokSayimFragmnet extends DialogFragment implements updateMiktar,View.OnClickListener {
    private TextView detayStokAdi, detayStokKodu,detayBirimi,detayDepoAdi,detayEskiMiktar;
    EditText detayStokYeniMiktar;
    LinearLayout stoksayimLinear;


    private Button detayStokKAydet;
    StoklarDB stoklarDB;
    DEPODB depodb;
    List<Stoklar> stoklarList = new ArrayList<>();
    public List<Saydet> saydetList = new ArrayList<>();
    Kullanici kullanici;
    Saydet saydet;
    SAYDETDB saydetdb;
    updateMiktar updateMiktar;
    MainActivity mainActivity;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_stok_sayim_fragmnet, container, false);

        stoklarDB = new StoklarDB(getContext());
        depodb = new DEPODB(getContext());
        saydetdb = new SAYDETDB(getContext());
        updateMiktar = this;


        kullanici = (Kullanici) getContext().getApplicationContext();
        createSaydet(getContext());

        detayStokYeniMiktar = (EditText) view.findViewById(R.id.detay_yeni_miktar);
        detayEskiMiktar = (TextView) view.findViewById(R.id.detay_update_eski_miktar);
        stoksayimLinear = (LinearLayout) view.findViewById(R.id.stok_sayim_linear);

        detayStokYeniMiktar.requestFocus();
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        mainActivity =  new MainActivity();
        stoklarList = mainActivity.getArrayList("StokinSharedPreference",getActivity());
        for(int i = 0 ; i < 10 ; i++){
            stoklarList.add(stoklarList.get(i));
        }

        detayStokAdi = (TextView) view.findViewById(R.id.detay_stok_adi);
        detayBirimi = (TextView) view.findViewById(R.id.birimi);
        detayStokKodu = (TextView) view.findViewById(R.id.detay_stok_kodu);
//        detayStokEskiMiktar = (TextView)view.findViewById(R.id.detay_eski_miktar);

        detayDepoAdi = (TextView)view.findViewById(R.id.detay_depo_ismi);
        detayStokKAydet = (Button) view.findViewById(R.id.detay_kaydet);

        Log.e("sokid",kullanici.getStok_id()+"");
        detayStokAdi.setText(stoklarDB.getStok(kullanici.getStok_id()).getStok_adi());
        detayStokKodu.setText(stoklarDB.getStok(kullanici.getStok_id()).getStok_kodu());
        detayDepoAdi.setText(depodb.getDepo(kullanici.getDepo_id()).getDepo_adi());

        String replaced = stoklarDB.getStok(kullanici.getStok_id()).getStok_birimi().replace("null","");
        Log.i("null", stoklarDB.getStok(kullanici.getStok_id()).getStok_birimi() + "");
        detayBirimi.setText(replaced);

        detayStokKAydet.setOnClickListener(this);
        return view;
    }
//    Saymas(saymasdb.getAllSaymas().get(i).getId()

    @Override
    public void onClick(View v) {
        if(v == detayStokKAydet){
            if(detayStokYeniMiktar.getText().toString().trim().equals("")){
                Toast.makeText(getActivity(),"Lutfen numara giriniz",Toast.LENGTH_LONG).show();
            }
            else {
                    saydetdb.addSaydet(new Saydet((int) kullanici.getSaydet_id(), (int) kullanici.getSaymas_id(), kullanici.getStok_id(), Integer.parseInt(detayStokYeniMiktar.getText().toString().trim())));

                    Log.i("saymas", kullanici.getSaymas_id() + "");
                    Log.i("saydetLast", saydetdb.getAllSAydet().size() + "");

                    Toast.makeText(getActivity(), "Stok Kayedildi", Toast.LENGTH_LONG).show();
                    kullanici.kullanicigecmisdataupdated.onGecmisDataChanged();
                    dismiss();

            }


        }
    }


    public void createSaydet(Context context) {
        SQLiteDatabase database = saydetdb.getWritableDatabase();
        saydetdb.onCreate(database);
        database.close();
    }


    @Override
    public void onUpdateMiktar() {

    }
}
