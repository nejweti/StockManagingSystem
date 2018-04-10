package com.nejat.evobulut.Adapterler;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nejat.evobulut.Databasler.SAYDETDB;
import com.nejat.evobulut.Fragments.StokSayimFragmnet;
import com.nejat.evobulut.Interfaceler.updateMiktar;
import com.nejat.evobulut.R;
import com.nejat.evobulut.klaslar.Depolar;
import com.nejat.evobulut.klaslar.Kullanici;
import com.nejat.evobulut.klaslar.Stoklar;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 3/5/2018.
 */

public class StokSayimAdapter extends RecyclerView.Adapter<StokSayimAdapter.ViewHolder> {

    List<Stoklar> filtered_stoklarList =  new ArrayList<>();
    List<Stoklar> default_stoklarList =  new ArrayList<>();
    ItemClickListener itemClickListener;
    Activity activity;
    List<Depolar> depolarList;
    SAYDETDB sayimdetdb;
    Kullanici kullanici;
    int newPostion;
    updateMiktar updateMiktar;
    public StokSayimAdapter(Activity activity,List<Stoklar> stoklarList, List<Depolar> depolarList){
        kullanici = (Kullanici) activity.getApplicationContext();
        this.default_stoklarList =stoklarList;
//        filtered_stoklarList = default_stoklarList;
        this.activity = activity;
        this.depolarList = depolarList;
        sayimdetdb = new SAYDETDB(activity.getApplicationContext());

    }

    public void setItemClickListener(ItemClickListener itemClickListener){
        this.itemClickListener = itemClickListener;

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

    @Override
    public StokSayimAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.stok_linear,parent,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final StokSayimAdapter.ViewHolder holder, final int position) {
        holder.mstoklar_birimi.setText(filtered_stoklarList.get(position).getStok_birimi());
        holder.mstoklar_kodu.setText(filtered_stoklarList.get(position).getStok_kodu());
        holder.mstoklar_adi.setText(filtered_stoklarList.get(position).getStok_adi());
//        if(sayimdetdb.getSaydet(kullanici.getSaydet_id()).getMiktar() != 0 ) {
//            holder.mstoklar_miktari.setText(String.valueOf(sayimdetdb.getSaydet(kullanici.getSaydet_id()).getMiktar()));
//        }

        holder.mlinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("is Stock Clicked ? ","yes");
                Log.i("position",position+"");


                kullanici.setStok_id(filtered_stoklarList.get(position).getId());
                itemClickListener.onItemClickListener(position);

            }
        });
        kullanici.kullanicindakimiktarUpdated = new Kullanici.miktarUpdated() {
            @Override
            public void onMiktarUpdated() {
                Log.i("saydetid",sayimdetdb.getSaydet(kullanici.getSaydet_id())+"");
                Log.i("position",position+"");
               holder.mstoklar_miktari.setText(String.valueOf(sayimdetdb.getSaydet(kullanici.getSaydet_id()).getMiktar()));
            }
        };

    }

    @Override
    public int getItemCount() {
        return filtered_stoklarList.size();
    }

    public void filter(String query) {
        filtered_stoklarList = new ArrayList<>();
//        for (Stoklar stok : default_stoklarList)
            Log.i("defaultstoklarlist",default_stoklarList.get(0).getStok_adi());
            for(int i = 0; i< default_stoklarList.size();i++){
                Stoklar stok = default_stoklarList.get(i);
                if(stok.getStok_adi().toLowerCase().contains(query.toLowerCase())) {
                    filtered_stoklarList.add(stok);
            }
        }
        Log.i("size",filtered_stoklarList.size()+ "");
        notifyDataSetChanged();

    }

    public interface ItemClickListener{
        void onItemClickListener(int position);
    }


}
