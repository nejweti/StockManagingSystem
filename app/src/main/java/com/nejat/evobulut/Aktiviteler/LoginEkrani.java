package com.nejat.evobulut.Aktiviteler;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.nejat.evobulut.Kutuphaneler.kutuphaneler;
import com.nejat.evobulut.R;

import org.json.JSONArray;
import org.json.JSONObject;

public class LoginEkrani extends AppCompatActivity implements View.OnClickListener {

    private TextView loginButtonText;
    private EditText Email_TextView;
    private EditText Password_TextView;
    SharedPreferences sharedPreferences;
    CheckBox loginCheckBox;
    ImageView loginbg;
    Animation frombottom , fromtoptobottom, fromlefttoright;
    LinearLayout linearButton , linearUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_login);

        sharedPreferences = getSharedPreferences("kullaniciData",MODE_PRIVATE);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        loginButtonText = (TextView) findViewById(R.id.login_textView);
        Email_TextView = (EditText) findViewById(R.id.user_email);
        Password_TextView = (EditText) findViewById(R.id.password);
        loginCheckBox = (CheckBox) findViewById(R.id.login_checkbox);
        loginbg = (ImageView) findViewById(R.id.loginbg);
        linearButton = (LinearLayout) findViewById(R.id.linearButton);
        linearUser = (LinearLayout) findViewById(R.id.linearUser);

        frombottom = AnimationUtils.loadAnimation(this,R.anim.frombottom);
        fromtoptobottom = AnimationUtils.loadAnimation(this,R.anim.fromtoptobottom);
        fromlefttoright = AnimationUtils.loadAnimation(this,R.anim.fromlefttoright);


        linearButton.setAnimation(frombottom);
        linearUser.setAnimation(fromtoptobottom);
        loginbg.setAnimation(fromlefttoright);

        if(sharedPreferences.getBoolean("hatirlat",false)){
            placeholderEmailPassword();
            loginButtonText.performClick();
        }

        loginButtonText.setOnClickListener(this);

    }

    public void placeholderEmailPassword(){
        Email_TextView.setText(sharedPreferences.getString("kullanici_kodu",""));
        Password_TextView.setText(sharedPreferences.getString("kullanici_sifre",""));
    }

    @Override
    public void onClick(View v) {
        if(v == loginButtonText){
            login();
        }

    }

    public void login(){
        kutuphaneler kutuphane=new kutuphaneler(LoginEkrani.this);
        String userEmail = Email_TextView.getText().toString().trim();
        String password = Password_TextView.getText().toString().trim();

        if(TextUtils.isEmpty(userEmail))
            Email_TextView.setError("Email bos olamaz");
        else if(TextUtils.isEmpty(password))
            Password_TextView.setError("Sifre bos olamaz");
        else{
        final String loginURL = "http://www.evobulut.com:4000/?mdl=base&cmd=euas&p1=" + userEmail + "&p2=" + password;
        kutuphane.EvoData(loginURL, Request.Method.GET, new kutuphaneler.EvoDataCallback() {
            @Override
            public void cevap(String cevap)
            {   Log.e("cevap?",cevap);
                try {
                    JSONArray jsonArray = new JSONArray(cevap);
                    JSONObject jsonObject = (JSONObject) jsonArray.get(0);

                    String kullanici_adi = jsonObject.getString("kullanici_adi");
                    String kullanici_kodu = jsonObject.getString("kullanici_kodu");
                    int id = jsonObject.getInt("id");
                    String UID = jsonObject.getString("UID");


                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    if(loginCheckBox.isChecked()){
                        editor.putBoolean("hatirlat", true);
                    }
                    editor.putString("kullanici_adi",kullanici_adi);
                    editor.putString("kullanici_kodu",kullanici_kodu);
                    editor.putString("kullanici_sifre",Password_TextView.getText().toString());
                    editor.putString("UID",UID);
                    editor.putInt("id",id);
                    editor.commit();

                    startActivity(new Intent(LoginEkrani.this,MainActivity.class));


                    Log.i("info","we are in login");
                    Log.e("information", jsonObject.toString());

                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void hata(String hata) {

            }
        });
    }
    }

}
