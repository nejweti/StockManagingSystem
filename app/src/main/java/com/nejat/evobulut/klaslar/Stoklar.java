package com.nejat.evobulut.klaslar;

import android.content.Context;

/**
 * Created by user on 2/28/2018.
 */

public class Stoklar {
    int id;
    String Stok_kodu;
    String Stok_adi;
    String Stok_birimi;

    public Stoklar(int id, String adi,String kodu, String birimi) {
        this.id = id;
        this.Stok_kodu = kodu;
        this.Stok_adi = adi;
        this.Stok_birimi = birimi;
    }

    public int getId() {

        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStok_kodu() {
        return Stok_kodu;
    }

    public void setStok_kodu(String stok_kodu) {
        Stok_kodu = stok_kodu;
    }

    public String getStok_adi() {
        return Stok_adi;
    }

    public void setStok_adi(String stok_adi) {
        Stok_adi = stok_adi;
    }

    public String getStok_birimi() {
        return Stok_birimi;
    }

    public void setStok_birimi(String stok_birimi) {
        Stok_birimi = stok_birimi;
    }
}
