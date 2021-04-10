package com.example.shop;

import java.io.Serializable;

public class STovar  implements Serializable {
    Integer id;
    String nom;
    String nom_ru;
    String nom_sh;
    String shtrix;
    String shtrix_in;
    Integer tz_id;
    Integer kg;
    String shtrix_full;
    String shtrix1;
    String shtrix2;
    Integer kat;
    Integer brend;
    Integer papka;
    String qr;
    Integer shtrixkod;
    String qrkod;
    Integer izm_id;
    Integer del_flag;
    Integer client_id;
    Double sotish;
    Double ulg1;
    Double ulg2;
    Double ulg1_pl;
    Double ulg2_pl;
    Double bank;
    Double sena;
    Integer kol_in;
    Double sena_d;
    Double sena_in_d;


    public STovar() {
    }

    public Double getSena_d() {
        return sena_d;
    }

    public void setSena_d(Double sena_d) {
        this.sena_d = sena_d;
    }

    public Double getSena_in_d() {
        return sena_in_d;
    }

    public void setSena_in_d(Double sena_in_d) {
        this.sena_in_d = sena_in_d;
    }

    public Integer getKol_in() {
        return kol_in;
    }

    public void setKol_in(Integer kol_in) {
        this.kol_in = kol_in;
    }

    public Double getSena() {
        return sena;
    }

    public void setSena(Double sena) {
        this.sena = sena;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getNom_ru() {
        return nom_ru;
    }

    public void setNom_ru(String nom_ru) {
        this.nom_ru = nom_ru;
    }

    public String getNom_sh() {
        return nom_sh;
    }

    public void setNom_sh(String nom_sh) {
        this.nom_sh = nom_sh;
    }

    public String getShtrix() {
        return shtrix;
    }

    public void setShtrix(String shtrix) {
        this.shtrix = shtrix;
    }

    public String getShtrix_in() {
        return shtrix_in;
    }

    public void setShtrix_in(String shtrix_in) {
        this.shtrix_in = shtrix_in;
    }

    public Integer getTz_id() {
        return tz_id;
    }

    public void setTz_id(Integer tz_id) {
        this.tz_id = tz_id;
    }

    public Integer getKg() {
        return kg;
    }

    public void setKg(Integer kg) {
        this.kg = kg;
    }

    public String getShtrix_full() {
        return shtrix_full;
    }

    public void setShtrix_full(String shtrix_full) {
        this.shtrix_full = shtrix_full;
    }

    public String getShtrix1() {
        return shtrix1;
    }

    public void setShtrix1(String shtrix1) {
        this.shtrix1 = shtrix1;
    }

    public String getShtrix2() {
        return shtrix2;
    }

    public void setShtrix2(String shtrix2) {
        this.shtrix2 = shtrix2;
    }

    public Integer getKat() {
        return kat;
    }

    public void setKat(Integer kat) {
        this.kat = kat;
    }

    public Integer getBrend() {
        return brend;
    }

    public void setBrend(Integer brend) {
        this.brend = brend;
    }

    public Integer getPapka() {
        return papka;
    }

    public void setPapka(Integer papka) {
        this.papka = papka;
    }

    public String getQr() {
        return qr;
    }

    public void setQr(String qr) {
        this.qr = qr;
    }

    public Integer getShtrixkod() {
        return shtrixkod;
    }

    public void setShtrixkod(Integer shtrixkod) {
        this.shtrixkod = shtrixkod;
    }

    public String getQrkod() {
        return qrkod;
    }

    public void setQrkod(String qrkod) {
        this.qrkod = qrkod;
    }

    public Integer getIzm_id() {
        return izm_id;
    }

    public void setIzm_id(Integer izm_id) {
        this.izm_id = izm_id;
    }

    public Integer getDel_flag() {
        return del_flag;
    }

    public void setDel_flag(Integer del_flag) {
        this.del_flag = del_flag;
    }

    public Integer getClient_id() {
        return client_id;
    }

    public void setClient_id(Integer client_id) {
        this.client_id = client_id;
    }

    public Double getSotish() {
        return sotish;
    }

    public void setSotish(Double sotish) {
        this.sotish = sotish;
    }

    public Double getUlg1() {
        return ulg1;
    }

    public void setUlg1(Double ulg1) {
        this.ulg1 = ulg1;
    }

    public Double getUlg2() {
        return ulg2;
    }

    public void setUlg2(Double ulg2) {
        this.ulg2 = ulg2;
    }

    public Double getUlg1_pl() {
        return ulg1_pl;
    }

    public void setUlg1_pl(Double ulg1_pl) {
        this.ulg1_pl = ulg1_pl;
    }

    public Double getUlg2_pl() {
        return ulg2_pl;
    }

    public void setUlg2_pl(Double ulg2_pl) {
        this.ulg2_pl = ulg2_pl;
    }

    public Double getBank() {
        return bank;
    }

    public void setBank(Double bank) {
        this.bank = bank;
    }

    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                ", nom='" + nom +
                ", nom_ru='" + nom_ru +
                ", nom_sh='" + nom_sh +
                ", shtrix='" + shtrix +
                ", shtrix_in='" + shtrix_in +
                ", tz_id=" + tz_id +
                ", kg=" + kg +
                ", shtrix_full='" + shtrix_full +
                ", shtrix1='" + shtrix1 +
                ", shtrix2='" + shtrix2 +
                ", kat=" + kat +
                ", brend=" + brend +
                ", papka=" + papka +
                ", qr='" + qr +
                ", shtrixkod=" + shtrixkod +
                ", qrkod='" + qrkod +
                ", izm_id=" + izm_id +
                ", del_flag=" + del_flag +
                ", client_id=" + client_id +
                ", sotish=" + sotish +
                ", ulg1=" + ulg1 +
                ", ulg2=" + ulg2 +
                ", ulg1_pl=" + ulg1_pl +
                ", ulg2_pl=" + ulg2_pl +
                ", bank=" + bank +
                ", sena=" + sena +
                ", kol_in=" + kol_in +
                ", sena_d=" + sena_d +
                ", sena_in_d=" + sena_in_d +
                '}';
    }
}
