package com.nejat.evobulut.Adapterler;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.nejat.evobulut.R;
import com.nejat.evobulut.klaslar.Depolar;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 3/8/2018.
 */

public class SpinnerAdapter extends BaseAdapter {

    Activity activity;
    List<Depolar> depolarList =  new ArrayList<>();
    LayoutInflater inflater;

    public SpinnerAdapter(Activity activity, List<Depolar> depolarList) {
        this.activity = activity;
        this.depolarList = depolarList;
        inflater = LayoutInflater.from(activity);
    }

    @Override
    public int getCount() {
        return depolarList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       convertView = inflater.inflate(R.layout.spinner_depo,null);
       TextView textView = (TextView) convertView.findViewById(R.id.depo_ad_inside_spinner);
        textView.setText(depolarList.get(position).getDepo_adi());
        return convertView;
    }
}
