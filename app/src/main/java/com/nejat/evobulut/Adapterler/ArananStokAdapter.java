package com.nejat.evobulut.Adapterler;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nejat.evobulut.Aktiviteler.StokEkle;
import com.nejat.evobulut.Databasler.SAYDETDB;
import com.nejat.evobulut.Databasler.StoklarDB;
import com.nejat.evobulut.Fragments.StokSayimFragmnet;
import com.nejat.evobulut.Fragments.StokSayimUpdate;
import com.nejat.evobulut.Kutuphaneler.kutuphaneler;
import com.nejat.evobulut.R;
import com.nejat.evobulut.klaslar.Kullanici;
import com.nejat.evobulut.klaslar.Saydet;
import com.nejat.evobulut.klaslar.Stoklar;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 3/14/2018.
 */

public class ArananStokAdapter extends RecyclerView.Adapter<ArananStokAdapter.ViewHolder>{
    Activity context;
    List<Stoklar> filtered_stoklarList = new ArrayList<>();
    StoklarDB stokdb;
    SAYDETDB saydetdb;
    List<Stoklar> shared_stoklarList = new ArrayList<>();
    StokSayimAdapter.ItemClickListener itemClickListener;
    onEklendiTrue onEklendiTrue;

    public ArananStokAdapter(Activity context, List<Stoklar> stoklarList, List<Stoklar> SharedStoklar) {
        this.context = context;
        this.filtered_stoklarList = stoklarList;
        this.shared_stoklarList = SharedStoklar;
        stokdb = new StoklarDB(context);
        saydetdb = new SAYDETDB(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.stok_linear,parent,false);
        return new ViewHolder(itemView);
    }
    public void setItemClickListener(StokSayimAdapter.ItemClickListener itemClickListener){
        this.itemClickListener = itemClickListener;

    }

    @Override
    public void onBindViewHolder(ArananStokAdapter.ViewHolder holder, final int position) {
        holder.mstoklar_kodu.setText(filtered_stoklarList.get(position).getStok_kodu());
        holder.mstoklar_adi.setText(filtered_stoklarList.get(position).getStok_adi());
        holder.mlinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Kullanici kullanici = (Kullanici) context.getApplicationContext();
                kullanici.setStok_id(filtered_stoklarList.get(position).getId());
                if(saydetdb.FindIfStokAddedAlready(kullanici.getSaymas_id(),kullanici.getStok_id()) == null){
                    Log.i("saydetdb","not found");
                    itemClickListener.onItemClickListener(position);
                }
                else {
                    kullanici.setSaydet_id(saydetdb.FindIfStokAddedAlready(kullanici.getSaymas_id(), kullanici.getStok_id()).getId());
                    Log.i("saydetdb", "found");
                onEklendiTrue.onEklendi();
                }


            }
        });

    }



    @Override
    public int getItemCount() {
        return filtered_stoklarList.size();
    }

    public void setOnEklendi(onEklendiTrue onEklendi) {
        this.onEklendiTrue = onEklendi;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mstoklar_adi;
        public TextView mstoklar_kodu;
        public TextView mstoklar_birimi;
        public TextView mstoklar_miktari;
        public LinearLayout mlinearLayout;
        public ViewHolder(View itemView) {
            super(itemView);
            mstoklar_adi = (TextView) itemView.findViewById(R.id.stok_adi);
            mstoklar_miktari = (TextView) itemView.findViewById(R.id.stok_miktar);
            mstoklar_kodu = (TextView) itemView.findViewById(R.id.stok_kodu);
            mstoklar_birimi = (TextView) itemView.findViewById(R.id.stok_birimi);
            mlinearLayout = (LinearLayout) itemView.findViewById(R.id.stok_sayim_linear);

        }
    }

    public void filter(String query) {
        filtered_stoklarList = new ArrayList<>();
        Log.i("defaultstoklarlist",shared_stoklarList.get(0).getStok_adi());
        for(int i = 0; i< shared_stoklarList.size();i++){
            Stoklar stok = shared_stoklarList.get(i);
            if((stok.getStok_adi().toLowerCase().contains(query.toLowerCase())) || (stok.getStok_kodu().toLowerCase().contains(query.toLowerCase()))) {
                filtered_stoklarList.add(stok);
            }
        }
        Log.i("size",filtered_stoklarList.size()+ "");
        notifyDataSetChanged();

    }

    public interface onEklendiTrue{
        void onEklendi();
    }
}

