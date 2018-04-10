package com.nejat.evobulut.Aktiviteler;

import android.app.VoiceInteractor;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.android.volley.Request;
import com.nejat.evobulut.Adapterler.DepolarAdapter;
import com.nejat.evobulut.Kutuphaneler.kutuphaneler;
import com.nejat.evobulut.R;
import com.nejat.evobulut.klaslar.Kullanici;

public class Saymaster extends AppCompatActivity{

    private TextView textView;
    Kullanici kullanici;
    kutuphaneler kutuphane;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saymaster);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        textView = (TextView) findViewById(R.id.saymastertextView);
        kullanici = (Kullanici) getApplicationContext();

        textView.setText(kullanici.getStok_id() + "");

    }

    public void stokData() {
        Log.i("inof", "we are in stokcekme");

        sharedPreferences = getSharedPreferences("kullaniciData", MODE_PRIVATE);
        String url = "http://www.evobulut.com:4000/?mdl=stok&cmd=jq_depo_list_full&UID=5" + sharedPreferences.getString("UID", "");
        kutuphane.EvoData(url, Request.Method.GET, new kutuphaneler.EvoDataCallback() {
            @Override
            public void cevap(String cevap) {

            }

            @Override
            public void hata(String hata) {

            }
        });
    }


        @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            kutuphane.EvoDialogInterface("Uyari", "Cikmak ister misiniz ?", new kutuphaneler.cikisKarari() {

                @Override
                public void karar(boolean bool) {
                    if (bool)
                        finish();
                }

            });
        }
            return super.onOptionsItemSelected(item);

    }



}