package com.nejat.evobulut.Adapterler;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.nejat.evobulut.Interfaceler.ClickListener;
import com.nejat.evobulut.R;
import com.nejat.evobulut.klaslar.menuSayfa;

import java.util.List;

/**
 * Created by user on 2/28/2018.
 */

public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.ViewHolder> {

   private List<menuSayfa> menuList;
    private Context context;
    private ClickListener clickListener;

    public RecycleViewAdapter(Context context,List<menuSayfa> menuList ) {
        this.menuList = menuList;
        this.context = context;
    }

    public void  setClickListener(ClickListener clickListener){
        this.clickListener = clickListener;

    }

    @Override
    public RecycleViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view,parent,false);
        return new ViewHolder(itemView);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBindViewHolder(RecycleViewAdapter.ViewHolder holder, final int position) {
        holder.textView.setText(menuList.get(position).getMenu_adi());
        holder.imageView.setImageResource(menuList.get(position).getMenu_resim_id());
      holder.imageView.setBackgroundResource(menuList.get(position).getMenu_background());

        holder.holderClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(clickListener != null){
                    clickListener.itemClickListener(v,position);
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return menuList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        public ImageView imageView;
        public LinearLayout holderClick;
        public ViewHolder(View itemView) {
            super(itemView);
            holderClick=(LinearLayout) itemView.findViewById(R.id.holderClick);
            textView = (TextView) itemView.findViewById(R.id.menu_card_view_text_view);
            imageView = (ImageView) itemView.findViewById(R.id.menu_card_view_image_view);


        }


    }

}
