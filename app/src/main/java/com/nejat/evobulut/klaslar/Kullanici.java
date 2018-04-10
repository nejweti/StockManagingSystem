package com.nejat.evobulut.klaslar;

import android.app.Application;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 3/2/2018.
 */

public class Kullanici extends Application {
    int id;
    String kullanici_kodu;
    String kullanici_adi;
    int departman_id;
    int adminmi;
    String mail;
    String sehir_id;
    String sehir_ad;
    int a_firma_id;
    int a_sektor;

    public int getWarningDepoStatus() {
        return warningDepoStatus;
    }

    public void setWarningDepoStatus(int warningDepoStatus) {
        this.warningDepoStatus = warningDepoStatus;
    }

    /***** Stoklar Id *****/

    int stok_id;

    /** Depo ID***/

    int depo_id;

    /** Saymas ID***/

    long saymas_id;

    public int getWarningalertStatus() {
        return warningalertStatus;
    }

    int warningDepoStatus;

    public void setWarningalertStatus(int warningalertStatus) {
        this.warningalertStatus = warningalertStatus;
    }

    int warningalertStatus;

    public List<Stoklar> stoklarList = new ArrayList<>();

    public List<Stoklar> getStoklarList() {
        return stoklarList;
    }

    public void setStoklarList(List<Stoklar> stoklarList) {
        this.stoklarList = stoklarList;
    }

    public long getSaydet_id() {
        return saydet_id;
    }

    public void setSaydet_id(long saydet_id) {
        this.saydet_id = saydet_id;
    }

    /** Saymas ID***/

    long saydet_id;


    public long getSaymas_id() {
        return saymas_id;
    }

    public void setSaymas_id(long saymas_id) {
        this.saymas_id = saymas_id;
    }

    public Kullanici(){

    }
    public Kullanici(int id, String kullanici_kodu, String kullanici_adi, int departman_id, int adminmi, String mail, String sehir_id, String sehir_ad, int a_firma_id, int a_sektor) {
        this.id = id;
        this.kullanici_kodu = kullanici_kodu;
        this.kullanici_adi = kullanici_adi;
        this.departman_id = departman_id;
        this.adminmi = adminmi;
        this.mail = mail;
        this.sehir_id = sehir_id;
        this.sehir_ad = sehir_ad;
        this.a_firma_id = a_firma_id;
        this.a_sektor = a_sektor;
    }

    public int getStok_id() {
        return stok_id;
    }

    public somethingChanged kullaniciclassindakiinterface;
    public miktarUpdated kullanicindakimiktarUpdated;
    public gecmisDataAdded kullanicigecmisdataupdated;

    @Override
    public void onCreate() {
        super.onCreate();
        kullaniciclassindakiinterface=new somethingChanged() {
            @Override
            public void changed() {

            }
        };
        kullanicindakimiktarUpdated = new miktarUpdated() {
            @Override
            public void onMiktarUpdated() {

            }
        };

    }

    public interface gecmisDataAdded{
        void onGecmisDataChanged();
    }
    public interface somethingChanged{
        void changed();
    }

    public interface miktarUpdated{
        void onMiktarUpdated();
    }

    public void setStok_id(int stok_id) {
        this.stok_id = stok_id;
    }


    public int getDepo_id() {
        return depo_id;
    }

    public void setDepo_id(int depo_id) {
        this.depo_id = depo_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKullanici_kodu() {
        return kullanici_kodu;
    }

    public void setKullanici_kodu(String kullanici_kodu) {
        this.kullanici_kodu = kullanici_kodu;
    }

    public String getKullanici_adi() {
        return kullanici_adi;
    }

    public void setKullanici_adi(String kullanici_adi) {
        this.kullanici_adi = kullanici_adi;
    }

    public int getDepartman_id() {
        return departman_id;
    }

    public void setDepartman_id(int departman_id) {
        this.departman_id = departman_id;
    }

    public int getAdminmi() {
        return adminmi;
    }

    public void setAdminmi(int adminmi) {
        this.adminmi = adminmi;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getSehir_id() {
        return sehir_id;
    }

    public void setSehir_id(String sehir_id) {
        this.sehir_id = sehir_id;
    }

    public String getSehir_ad() {
        return sehir_ad;
    }

    public void setSehir_ad(String sehir_ad) {
        this.sehir_ad = sehir_ad;
    }

    public int getA_firma_id() {
        return a_firma_id;
    }

    public void setA_firma_id(int a_firma_id) {
        this.a_firma_id = a_firma_id;
    }

    public int getA_sektor() {
        return a_sektor;
    }

    public void setA_sektor(int a_sektor) {
        this.a_sektor = a_sektor;
    }
}
