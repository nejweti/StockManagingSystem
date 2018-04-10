package com.nejat.evobulut.klaslar;

import android.view.View;

import com.nejat.evobulut.R;

/**
 * Created by user on 2/28/2018.
 */

public class menuSayfa{

        String  menu_adi;
        int menu_resim_id;
        int menu_background;

    public int getMenu_background() {
        return menu_background;
    }

    public void setMenu_background(int menu_background) {
        this.menu_background = menu_background;
    }

    public menuSayfa(String menu_adi, int menu_background, int menu_resim_id ) {
                this.menu_adi = menu_adi;
                this.menu_resim_id = menu_resim_id;
                this.menu_background = menu_background;
        }

        public String getMenu_adi() {
                return menu_adi;
        }

        public void setMenu_adi(String menu_adi) {
                this.menu_adi = menu_adi;
        }

        public int getMenu_resim_id() {
                return menu_resim_id;
        }

        public void setMenu_resim_id(int menu_resim_id) {
                this.menu_resim_id = menu_resim_id;
        }
}
