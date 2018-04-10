package com.nejat.evobulut.Kutuphaneler;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.nejat.evobulut.Aktiviteler.LoginEkrani;
import com.nejat.evobulut.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * Created by user on 3/1/2018.
 */

public class kutuphaneler {

    static Activity activity;

    public kutuphaneler(Activity activity) {
        this.activity = activity;
    }

    public static void wifiEkraniAc() {
        activity.startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));

    }

    public static boolean internetKontrol() {
        NetworkInfo info = (NetworkInfo) ((ConnectivityManager) activity.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        if (info == null || !info.isConnected()) {
            Toast.makeText(activity, "İnternet bağlantınızı kontrol edin!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (info.isRoaming()) {
            return false;
        }
        return true;
    }



public void EvoData(String url, int method, final EvoDataCallback callback) {


    Log.e("Sorgulanan url", url);
    final ProgressDialog pd = new ProgressDialog(activity);
    pd.setCancelable(false);
    if (internetKontrol())
        try {
            pd.setMessage("İşlem Gerçekleştiriliyor.Lütfen Bekleyiniz..");
            pd.show();
            RequestQueue mRequestQueue;
            StringRequest stringRequest = (StringRequest) new StringRequest(method, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                response = URLDecoder.decode(URLEncoder.encode(response, "iso8859-1"), "UTF-8");
                                Log.e("Kutuphane gelen", response);
                                JSONObject object = new JSONObject(response);
                                JSONArray jsonArray = new JSONArray(object.getString("veri"));
                                if (object.getString("status").equals("OK")) {
                                    callback.cevap(jsonArray.toString());
                                } else if (object.getString("status").equals("ERR")) {

                                    JSONObject hataobje = (JSONObject) jsonArray.get(0);
                                    String hata = hataobje.getString("mesaj");
                                    Toast.makeText(activity, "" + hata, Toast.LENGTH_SHORT).show();

                                    if (hata.equals("Hatalı Oturum Bilgisi. Lütfen Tekrar Login Olunuz.")) {

                                        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                                        builder.setTitle("UYARI");
                                        builder.setMessage("Üyeliğiniz ile başka bir telefondan giriş yapıldı!");
                                        builder.setPositiveButton("Tekrar Bağlan", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
//                                                activity.startActivity(new Intent(activity, SplashYonlendirici.class));

                                            }
                                        });
                                        builder.setNegativeButton("Çıkış Yap", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
//                                                SharedPreferences sharedPreferences = activity.getSharedPreferences(UygulamaSabitleri.giris_bilgileri, Context.MODE_PRIVATE);
//                                                SharedPreferences.Editor editor = sharedPreferences.edit();
//                                                editor.putString("oto", "");
//                                                editor.commit();
                                                Intent intent = new Intent(activity.getApplicationContext(), LoginEkrani.class);
                                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                activity.startActivity(intent);
                                            }
                                        });

                                        AlertDialog dialog = builder.create();
                                        dialog.show();


                                    }

                                }


                            } catch (Exception e) {
                                Log.e("Kutuphane Hata", e.toString());
                                e.printStackTrace();
                            }

                            if (pd.isShowing())
                                pd.dismiss();
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            callback.hata(error.toString());
                            if (pd.isShowing())
                                pd.dismiss();
                        }
                    }).setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            mRequestQueue = Volley.newRequestQueue(activity.getApplicationContext());
            mRequestQueue.add(stringRequest);  //request ayarlandı çıkarılabilir 07 08 2017
        } catch (Exception e) {
            Log.e("HATA:::", e.toString());
            if (pd.isShowing())
                pd.dismiss();
        }
    else
        EvoDialogInterface("Şu anda internet bağlantınız olmadığından programı aktif olarak kullanamıyorsunuz. Lütfen internete bağlı olduğunuzdan emin olun.", "Bağlantı Hatası", new cikisKarari() {
            @Override
            public void karar(boolean bool) {
                if (bool)
                    wifiEkraniAc();
            }
        });
}


    public interface EvoDataCallback {
        void cevap(String cevap);
        void hata(String hata);
    }


    public static void EvoDialogInterface(String icerik, String baslik, final cikisKarari cikisKarari) {
        final AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            builder = new AlertDialog.Builder(activity, R.style.EvoTemasi);
            builder = new AlertDialog.Builder(activity);

        } else {
            builder = new AlertDialog.Builder(activity);
        }

        final AlertDialog dialog = builder.setTitle(baslik)
                .setMessage(icerik)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        cikisKarari.karar(true);
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        cikisKarari.karar(false);
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.WHITE);
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.WHITE);
            }
        });
    }

    public interface cikisKarari {
        void karar(boolean bool);
    }
}