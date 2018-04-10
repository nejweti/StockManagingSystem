package com.nejat.evobulut.Adapterler;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nejat.evobulut.Databasler.DEPODB;
import com.nejat.evobulut.Databasler.SAYDETDB;
import com.nejat.evobulut.Databasler.SAYMASDB;
import com.nejat.evobulut.Fragments.ViewPager;
import com.nejat.evobulut.R;
import com.nejat.evobulut.klaslar.Depolar;
import com.nejat.evobulut.klaslar.Kullanici;
import com.nejat.evobulut.klaslar.Saymas;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by user on 3/7/2018.
 */

public class DepolarAdapter extends RecyclerView.Adapter<DepolarAdapter.ViewHolder> {
    List<Saymas> saymasList =  new ArrayList<>();
    List<Depolar> depolarList = new ArrayList<>();
    Activity activity;
    DepolarAdapter.depoClicked  depoClicked;
    DEPODB depodb;
    SAYMASDB saymasdb;
    SAYDETDB saydetdb;

    public DepolarAdapter(Activity activity,List<Saymas> saymasList, List<Depolar> depolarList) {
        this.saymasList = saymasList;
        this.activity = activity;
        this.depolarList = depolarList;
        Collections.reverse(this.depolarList);
        Collections.reverse(this.saymasList);
        depodb = new DEPODB(activity.getApplicationContext());
        saymasdb = new SAYMASDB(activity.getApplicationContext());
        saydetdb =  new SAYDETDB(activity.getApplicationContext());
    }

    public void setDepoClicker(DepolarAdapter.depoClicked depoClicked){
        this.depoClicked = depoClicked;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.depo_linear,parent,false);
        return new ViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        if(saymasList.size() == 0){
            Toast.makeText(activity,"Sayim hic Yapilmadi",Toast.LENGTH_LONG).show();
        }
        else {
            holder.saymas_tarih.setText(saymasList.get(position).getTarih());
            holder.saymas_ack.setText(saymasList.get(position).getAck());
//            holder.depo_adi.setText(depodb.getAllDepolar().get(position).getDepo_adi());
            holder.depo_adi.setText(depodb.getDepo(saymasList.get(position).getDepo_id()).getDepo_adi());
//            Log.e("depo",depodb.getDepo(2).getDepo_adi());
//            Log.i("getid",depodb.getAllDepolar().get(position).getDepo_id()+"");
//            Depolar depo = depodb.getDepo(saymasList.get(position).getDepo_id());
//            Log.i("depoadii",depo.getDepo_adi());
//            Log.i("depoaa",depo.getDepo_id()+"");
            holder.depo_linear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Kullanici kullanici = (Kullanici) activity.getApplicationContext();
                    kullanici.setDepo_id(saymasList.get(position).getDepo_id());
                    kullanici.setSaymas_id(saymasList.get(position).getId());

                    Log.i("saymasid",kullanici.getSaymas_id()+"");

                    depoClicked.onDepoClicked(position);

                }
            });


        }


    }

    @Override
    public int getItemCount() {
        return saymasList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView depo_adi;
        public TextView saymas_tarih;
        public TextView saymas_ack;
        public LinearLayout depo_linear;

        public ViewHolder(View itemView) {
            super(itemView);
            depo_adi = (TextView) itemView.findViewById(R.id.depo_adi);
            saymas_ack = (TextView) itemView.findViewById(R.id.sayim_ack);
            saymas_tarih = (TextView) itemView.findViewById(R.id.sayim_tarih);
            depo_linear = (LinearLayout) itemView.findViewById(R.id.depo_sayim_linear);



        }
    }

    public interface depoClicked{
        void onDepoClicked(int position);
    }
}
