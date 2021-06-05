package com.example.shop.db.beans;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;


@Entity(tableName = "s_tovar")
public class STovar implements Serializable {

    @PrimaryKey
    private Integer id;
    private String nom;
    private String nom_ru;
    private String nom_sh;
    private String shtrix;
    private String shtrix_in;
    private Integer tz_id;
    private Integer kg;
    private String shtrix_full;
    private String shtrix1;
    private String shtrix2;
    private Integer kat;
    private Integer brend;
    private Integer papka;
    private String qr;
    private Integer shtrixkod;
    private String qrkod;
    private Integer izm_id;
    private Integer del_flag;
    private Integer client_id;
    private Double sotish;
    private Double ulg1;
    private Double ulg2;
    private Double ulg1_pl;
    private Double ulg2_pl;
    private Double bank;
    private Double sena;
    private Integer kol_in;
    private Double sena_d;
    private Double sena_in_d;
    private Integer tkol;
    private Integer tkol_in;
    private Integer seriya = 0;


    public STovar() {
    }

    public Integer getSeriya() {
        return seriya;
    }

    public void setSeriya(Integer seriya) {
        this.seriya = seriya;
    }

    public Integer getTkol() {
        return tkol;
    }

    public void setTkol(Integer tkol) {
        this.tkol = tkol;
    }

    public Integer getTkol_in() {
        return tkol_in;
    }

    public void setTkol_in(Integer tkol_in) {
        this.tkol_in = tkol_in;
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
