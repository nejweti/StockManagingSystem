package com.nejat.evobulut.klaslar;

/**
 * Created by user on 2/28/2018.
 */

public class Saymas {
    long id;
    int depo_id;
    String tarih;
    String ack;

    public Saymas(long id, int depo_id, String tarih, String ack) {
        this.id = id;
        this.depo_id = depo_id;
        this.tarih = tarih;
        this.ack = ack;
    }

    public Saymas(int depo_id, String tarih, String ack) {
        this.depo_id = depo_id;
        this.tarih = tarih;
        this.ack = ack;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getDepo_id() {
        return depo_id;
    }

    public void setDepo_id(int depo_id) {
        this.depo_id = depo_id;
    }

    public String getTarih() {
        return tarih;
    }

    public void setTarih(String tarih) {
        this.tarih = tarih;
    }

    public String getAck() {
        return ack;
    }

    public void setAck(String ack) {
        this.ack = ack;
    }
}
