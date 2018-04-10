package com.nejat.evobulut.Adapterler;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nejat.evobulut.Aktiviteler.MainActivity;
import com.nejat.evobulut.Aktiviteler.SayimMasterListOut;
import com.nejat.evobulut.Databasler.SAYDETDB;
import com.nejat.evobulut.Databasler.StoklarDB;
import com.nejat.evobulut.Fragments.DepodakiStokDetay;
import com.nejat.evobulut.Fragments.StokSayimFragmnet;
import com.nejat.evobulut.Kutuphaneler.kutuphaneler;
import com.nejat.evobulut.R;
import com.nejat.evobulut.klaslar.Kullanici;
import com.nejat.evobulut.klaslar.Saydet;
import com.nejat.evobulut.klaslar.Saymas;
import com.nejat.evobulut.klaslar.Stoklar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by user on 3/13/2018.
 */

public class GecmisStoklar  extends RecyclerView.Adapter<GecmisStoklar.ViewHolder>{
    List<Stoklar> saydetList;
    Activity context;
    List<Saydet> saydetnew = new ArrayList<>();
    StoklarDB stokdb;
    SAYDETDB saydetdb;
    final  Kullanici kullanici;
    StokSayimAdapter.ItemClickListener itemClickListener;
    public GecmisStoklar(List<Stoklar> saydetList, Activity context,List<Saydet> saydetnew) {

        this.saydetList = saydetList;
        this.context = context;
        stokdb = new StoklarDB(context.getApplicationContext());
        saydetdb = new SAYDETDB(context.getApplicationContext());
        this.saydetnew = saydetnew;
        Collections.reverse(saydetList);
        Collections.reverse(saydetnew);
        kullanici = (Kullanici) context.getApplicationContext();




    }

    public void setItemClickListener(StokSayimAdapter.ItemClickListener itemClickListener){
        this.itemClickListener = itemClickListener;

    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.stok_detay_linear,parent,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
//        holder.mstoklar_adi.setText(stokdb.getStok(saydetList.get(position).getStok_id()).getStok_adi());
        holder.mstoklar_adi.setText(saydetList.get(position).getStok_adi());
        holder.mstoklar_miktari.setText(saydetnew.get(position).getMiktar() + "");
        holder.mstoklar_kodu.setText(saydetList.get(position).getStok_kodu());
        String replaced = saydetList.get(position).getStok_birimi().replace("null","");
        holder.mstoklar_birimi.setText(replaced);
        Log.i("notify","working");


        holder.update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                kullanici.setStok_id(saydetList.get(position).getId());
                kullanici.setSaydet_id(saydetnew.get(position).getId());
                itemClickListener.onItemClickListener(position);


            }
        });
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kullanici.setStok_id(saydetList.get(position).getId());
                kullanici.setSaydet_id(saydetnew.get(position).getId());
                new kutuphaneler(context).EvoDialogInterface("Silmek Ister Misiniz?", "Uyari...", new kutuphaneler.cikisKarari() {
                    @Override
                    public void karar(boolean bool) {
                        if(bool){
                            Saydet saydet = new Saydet(saydetnew.get(position).getId(), (int) kullanici.getSaymas_id(),saydetList.get(position).getId(),saydetnew.get(position).getMiktar());
                            saydetdb.deleteSaydet(saydet);
                            Toast.makeText(context,"Stok Kaydedildi",Toast.LENGTH_LONG);
                            kullanici.kullanicigecmisdataupdated.onGecmisDataChanged();
                        }
                    }
                });

            }
        });
    }

    @Override
    public int getItemCount() {
        return saydetList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mstoklar_adi;
        public TextView mstoklar_kodu;
        public TextView mstoklar_birimi;
        public TextView mstoklar_miktari;
        public LinearLayout mlinearLayout;
        public ImageButton update;
        public ImageButton delete;
        public LinearLayout warningLinear;
        public ViewHolder(View itemView) {
            super(itemView);
            mstoklar_adi = (TextView) itemView.findViewById(R.id.stok_adi);
            mstoklar_miktari = (TextView) itemView.findViewById(R.id.stok_miktar);
            mstoklar_kodu = (TextView) itemView.findViewById(R.id.stok_kodu);
            mstoklar_birimi = (TextView) itemView.findViewById(R.id.stok_birimi);
            mlinearLayout = (LinearLayout) itemView.findViewById(R.id.stok_sayim_linear);
            update = (ImageButton) itemView.findViewById(R.id.stok_detay_update);
            delete = (ImageButton) itemView.findViewById(R.id.stok_detay_delete);


        }

    }


}
