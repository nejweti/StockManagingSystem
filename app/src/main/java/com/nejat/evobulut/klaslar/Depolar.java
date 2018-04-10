package com.nejat.evobulut.klaslar;

/**
 * Created by user on 2/28/2018.
 */

public class Depolar {
    int depo_id;
    String Depo_adi;

    public Depolar(int id, String depo_adi) {
        this.depo_id = id;
        this.Depo_adi = depo_adi;
    }

    public int getDepo_id() {
        return depo_id;
    }

    public void setDepo_id(int id) {
        this.depo_id = id;
    }

    public String getDepo_adi() {
        return Depo_adi;
    }

    public void setDepo_adi(String depo_adi) {
        Depo_adi = depo_adi;
    }
}
