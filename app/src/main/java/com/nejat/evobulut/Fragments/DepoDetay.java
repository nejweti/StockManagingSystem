package com.nejat.evobulut.Fragments;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.nejat.evobulut.Adapterler.SpinnerAdapter;
import com.nejat.evobulut.Aktiviteler.SayimMasterListOut;
import com.nejat.evobulut.Databasler.DEPODB;
import com.nejat.evobulut.Databasler.SAYMASDB;
import com.nejat.evobulut.Kutuphaneler.kutuphaneler;
import com.nejat.evobulut.R;
import com.nejat.evobulut.klaslar.Depolar;
import com.nejat.evobulut.klaslar.Kullanici;
import com.nejat.evobulut.klaslar.Saymas;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class DepoDetay extends Fragment implements View.OnClickListener{
    SAYMASDB saymasdb;
    DEPODB depodb;
    Kullanici kullanici;
    private int mYear, mMonth, mDay;
    Saymas saymas;
    private EditText depoAdi, depoTarih,depoAck;
    private Button depoUpdate, depoDelete;
    private Spinner spinner;
    private List<Depolar> depolarList = new ArrayList<>();
    private SpinnerAdapter spinnerAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        kullanici = (Kullanici) getActivity().getApplicationContext();
        saymasdb = new SAYMASDB(getActivity().getApplication());
        depodb = new DEPODB(getActivity().getApplicationContext());
        saymas = saymasdb.getSaymas(kullanici.getSaymas_id());
        View view  =  inflater.inflate(R.layout.fragment_depo_detay, container, false);

        spinner = (Spinner) view.findViewById(R.id.depo_detay_depo_adi);
        depoTarih = (EditText) view.findViewById(R.id.depo_detay_depo_tarih);
        depoAck = (EditText) view.findViewById(R.id.depa_detay_depo_ack);
        depoDelete = (Button) view.findViewById(R.id.depo_delete);
        depoUpdate = (Button) view.findViewById(R.id.depo_update);

        Calendar calendar = Calendar.getInstance();
        mYear = calendar.get(Calendar.YEAR);
        mMonth = calendar.get(Calendar.MONTH);
        mDay = calendar.get(Calendar.DATE);

        depoUpdate.setOnClickListener(this);
        depoDelete.setOnClickListener(this);
        depoTarih.setOnClickListener(this);


        kullanici.setDepo_id(saymas.getDepo_id());
        depoTarih.setText(saymas.getTarih());
        depoAck.setText(saymas.getAck());

        for(int i = 0 ; i < depodb.getAllDepolar().size();i++){
            depolarList.add(new Depolar(depodb.getAllDepolar().get(i).getDepo_id(),depodb.getAllDepolar().get(i).getDepo_adi()));
        }

//        for(int i = 0 ; i< saymasdb.getAllSaymas().size();i++){
//            saymasList.add(new Saymas(saymasdb.getAllSaymas().get(i).getDepo_id(),saymasdb.getAllSaymas().get(i).getTarih(),saymasdb.getAllSaymas().get(i).getAck()));
//        }

        spinnerAdapter = new SpinnerAdapter(getActivity(),depolarList);
        spinner.setAdapter(spinnerAdapter);
        Depolar depo = new Depolar(kullanici.getDepo_id(),depodb.getDepo(kullanici.getDepo_id()).getDepo_adi());

        Log.i("info",depo.getDepo_adi()+depo.getDepo_id());
        for(int i = 0 ; i< depolarList.size();i++){
            Log.i("depo_list",depolarList.get(i).getDepo_adi()+depolarList.get(i).getDepo_id()+"");
        }
        spinner.setSelection((kullanici.getDepo_id() - 1));

        Log.i("depo spinne",""+depolarList.indexOf(new Depolar(1,"Ana Depo")));

        Log.i("depo_id",kullanici.getDepo_id()+"");
        Log.i("depofrompbj",depolarList.size()+"");
        Log.i("depo_adi",depodb.getDepo(kullanici.getDepo_id()).getDepo_adi()+"");

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                kullanici.setDepo_id(depolarList.get(position).getDepo_id());
                Log.i("depo_id",kullanici.getDepo_id()+"");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        return view;

    }


    @Override
    public void onClick(View v) {
        if(v == depoDelete){
            new kutuphaneler(getActivity()).EvoDialogInterface("Silmek Ister Misiniz?", "Uyari...", new kutuphaneler.cikisKarari() {
                @Override
                public void karar(boolean bool) {
                    if(bool){
                        Saymas saymas = new Saymas(kullanici.getSaymas_id(),kullanici.getDepo_id(),depoTarih.getText().toString(),depoAck.getText().toString());
                        saymasdb.deleteSaymas(saymas);
                        Toast.makeText(getActivity(), "Depo Silindi", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(getActivity(), SayimMasterListOut.class));
                    }
                }
            });

        }
        if(v == depoUpdate){
            Saymas saymas = new Saymas(kullanici.getSaymas_id(),kullanici.getDepo_id(),depoTarih.getText().toString(),depoAck.getText().toString());
            saymasdb.updateSaymas(saymas);
            Toast.makeText(getActivity(), "Depo Guncelendi", Toast.LENGTH_LONG).show();
            startActivity(new Intent(getActivity(), SayimMasterListOut.class));

        }
        if(v == depoTarih){
            DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    if((mMonth+1) > 0 && (mMonth+1) < 10){
                        depoTarih.setText(mDay+"."+"0"+(mMonth+1)+"."+mYear);
                    }
                    else{depoTarih.setText(mDay+"."+(mMonth+1)+"."+mYear);}

                }
            },mYear,mMonth,mDay);
            datePickerDialog.show();
        }
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
}
