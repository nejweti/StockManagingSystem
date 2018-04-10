package com.nejat.evobulut.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nejat.evobulut.R;

public class ViewPager extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_view_pager,container,false);
        android.support.v4.view.ViewPager viewPager = (android.support.v4.view.ViewPager) view.findViewById(R.id.view_pager);
        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tab_layout);
        viewPager.setAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return position == 1? new DepoDetay():new DepodakiStokDetay();
            }

            @Override
            public int getCount() {
                return 2;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return position == 1? "Fiş Bilgiler":"Fiş detayları";
            }
        });
        tabLayout.setupWithViewPager(viewPager);
        return view;
    }
}
