package com.nejat.evobulut.Aktiviteler;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.nejat.evobulut.Kutuphaneler.kutuphaneler;
import com.nejat.evobulut.R;

public class StoklariGonder extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stoklari_gonder);

        android.app.ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
    }

//    @Override
//    public boolean onSupportNavigateUp() {
//        kutuphaneler.EvoAlerDialog(this, "Uyari!", "Cikmak  ister misiniz ?", new kutuphaneler.kararListener() {
//            @Override
//            public void karar(boolean sonuc) {
//                if(sonuc)
//                    finish();
//            }
//        });
//        return super.onSupportNavigateUp();
//    }
}
