package com.nejat.evobulut.klaslar;

/**
 * Created by user on 2/28/2018.
 */

public class Saydet {


    int id;
    int saymas_id;
    int stok_id;
    int miktar;
    int eklendi;

    public Saydet(int id, int saymas_id, int stok_id, int miktar, int eklendi) {
        this.id = id;
        this.saymas_id = saymas_id;
        this.stok_id = stok_id;
        this.miktar = miktar;
        this.eklendi = eklendi;
    }


    public Saydet(int id, int saymas_id, int stok_id, int miktar) {
        this.id = id;
        this.saymas_id = saymas_id;
        this.stok_id = stok_id;
        this.miktar = miktar;
    }

    public Saydet(int saymas_id, int stok_id, int miktar) {
        this.saymas_id = saymas_id;
        this.stok_id = stok_id;
        this.miktar = miktar;
    }
    public int getEklendi() {
        return eklendi;
    }

    public void setEklendi(int eklendi) {
        this.eklendi = eklendi;
    }


    public int getSaymas_id() {
        return saymas_id;
    }

    public void setSaymas_id(int saymas_id) {
        this.saymas_id = saymas_id;
    }

    public int getStok_id() {
        return stok_id;
    }

    public void setStok_id(int stok_id) {
        this.stok_id = stok_id;
    }

    public int getMiktar() {
        return miktar;
    }

    public void setMiktar(int miktar) {
        this.miktar = miktar;
    }

    public int getId() {

        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
