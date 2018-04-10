package com.nejat.evobulut.Fragments;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
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
import com.nejat.evobulut.Aktiviteler.SayimDetay;
import com.nejat.evobulut.Aktiviteler.SayimMasterListOut;
import com.nejat.evobulut.Databasler.DEPODB;
import com.nejat.evobulut.Databasler.SAYMASDB;
import com.nejat.evobulut.R;
import com.nejat.evobulut.klaslar.Depolar;
import com.nejat.evobulut.klaslar.Kullanici;
import com.nejat.evobulut.klaslar.Saymas;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class depoSayimEkle extends Fragment implements View.OnClickListener{

    private EditText depo_adi,tarih,ack;
    private int mYear, mMonth, mDay;
    private Button depo_kaydet;
    private Spinner spinner;
    SpinnerAdapter spinnerAdapter;
    ArrayList<Depolar> depolarList = new ArrayList<>();
    DEPODB depodb;
    SAYMASDB saymasdb;
    public List<Saymas> saymasList = new ArrayList<>();
    Kullanici kullanici;
    boolean eklendi = false;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_depo_sayim_ekle, container, false);

        spinner = (Spinner) view.findViewById(R.id.spinner_demo);
        tarih = (EditText) view.findViewById(R.id.edit_depo_tarih);
        ack = (EditText) view.findViewById(R.id.edit_depo_ack);
        depo_kaydet = (Button) view.findViewById(R.id.depo_kaydet_fragmnet_button);
        depodb = new DEPODB(getContext());
        saymasdb = new SAYMASDB(getContext());
        Calendar calendar = Calendar.getInstance();
        mYear = calendar.get(Calendar.YEAR);
        mMonth = calendar.get(Calendar.MONTH);
        mDay = calendar.get(Calendar.DATE);

        if((mMonth+1) > 0 && (mMonth+1) < 10){
            tarih.setText(mDay+"."+"0"+(mMonth+1)+"."+mYear);
        }
        else{tarih.setText(mDay+"."+(mMonth+1)+"."+mYear);}



        tarih.setOnClickListener(this);
        SQLiteDatabase db = saymasdb.getReadableDatabase();

//        clearDatabase(getContext());
        kullanici = (Kullanici) getContext().getApplicationContext();
        Log.i("size",depodb.getAllDepolar().size()+"");


        for(int i = 0 ; i < depodb.getAllDepolar().size();i++){
            depolarList.add(new Depolar(depodb.getAllDepolar().get(i).getDepo_id(),depodb.getAllDepolar().get(i).getDepo_adi()));
        }

//        for(int i = 0 ; i< saymasdb.getAllSaymas().size();i++){
//            saymasList.add(new Saymas(saymasdb.getAllSaymas().get(i).getDepo_id(),saymasdb.getAllSaymas().get(i).getTarih(),saymasdb.getAllSaymas().get(i).getAck()));
//        }

        spinnerAdapter = new SpinnerAdapter(getActivity(),depolarList);
        spinner.setAdapter(spinnerAdapter);

        depo_kaydet.setOnClickListener(this);
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

    public void clearDatabase(Context context) {
        SQLiteDatabase database = saymasdb.getWritableDatabase();
        database.execSQL("DROP TABLE IF EXISTS " + saymasdb.TABLE_NAME);
        saymasdb.onCreate(database);
        database.close();
    }

    @Override
    public void onClick(View v) {
        if(v == tarih){
            Log.i("tarih cicked","tarih clicked");



            DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    if((mMonth+1) > 0 && (mMonth+1) < 10){
                        tarih.setText(dayOfMonth+"."+"0"+(month+1)+"."+year);
                    }
                    else{tarih.setText(dayOfMonth+"."+(month+1)+"."+year);}
                }
            },mYear,mMonth,mDay);
            datePickerDialog.show();
        }
        if(v == depo_kaydet){

            saymasdb.addSaymas(new Saymas(kullanici.getDepo_id(),tarih.getText().toString().trim(),ack.getText().toString().trim()));

            for(int i = 0 ; i< saymasdb.getAllSaymas().size();i++)
                {
                    Log.i("sayimmasid",saymasdb.getAllSaymas().get(i).getId()+"");

                }

                Log.i("sayimyapdb",saymasdb.getSayimasCount()+"");

            Log.i("id","");
            kullanici.setWarningDepoStatus(View.GONE);
            Toast.makeText(getActivity(), "Depo Kayedildi", Toast.LENGTH_LONG).show();
            startActivity(new Intent(getActivity(), SayimMasterListOut.class));
//            startActivity(new Intent(getActivity(), SayimDetay.class));
//            kullanici.kullaniciclassindakiinterface.changed();


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
