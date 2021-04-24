package com.example.shop.model;

import java.io.Serializable;

public class AsosModell implements Serializable {
    private Integer id;
    private Integer client_id;
    private Integer userId;
    private Integer xodimId;
    private Integer haridorId;
    private String sana;
    private Integer diler_id;
    private Integer tur_oper;
    private Double summa;
    private Integer sotuv_turi;
    private String nomer;
    private Integer del_flag;
    private Integer dollar;
    private Double kurs;
    private Double sum_d;
    private Integer kol;

    //  private Double sena_d ;
    //  private Double sena_in_d ;


    public AsosModell() {
    }

    // public Double getSena_d() {
    //     return sena_d;
    // }

    // public void setSena_d(Double sena_d) {
    //  this.sena_d = sena_d;
    // }

//    public Double getSena_in_d() {
//        return sena_in_d;
//    }
//
//    public void setSena_in_d(Double sena_in_d) {
//        this.sena_in_d = sena_in_d;
//    }

    public String getNomer() {
        return nomer;
    }

    public void setNomer(String nomer) {
        this.nomer = nomer;
    }

    public Double getSumma() {
        return summa;
    }

    public void setSumma(Double summa) {
        this.summa = summa;
    }

    public Integer getSotuv_turi() {
        return sotuv_turi;
    }

    public void setSotuv_turi(Integer sotuv_turi) {
        this.sotuv_turi = sotuv_turi;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getClient_id() {
        return client_id;
    }

    public void setClient_id(Integer client_id) {
        this.client_id = client_id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getXodimId() {
        return xodimId;
    }

    public void setXodimId(Integer xodimId) {
        this.xodimId = xodimId;
    }

    public Integer getHaridorId() {
        return haridorId;
    }

    public void setHaridorId(Integer haridorId) {
        this.haridorId = haridorId;
    }

    public Integer getDiler_id() {
        return diler_id;
    }

    public void setDiler_id(Integer diler_id) {
        this.diler_id = diler_id;
    }

    public Integer getTur_oper() {
        return tur_oper;
    }

    public void setTur_oper(Integer tur_oper) {
        this.tur_oper = tur_oper;
    }

    public String getSana() {
        return sana;
    }

    public void setSana(String sana) {
        this.sana = sana;
    }


    public Integer getDel_flag() {
        return del_flag;
    }

    public void setDel_flag(Integer del_flag) {
        this.del_flag = del_flag;
    }

    public Integer getDollar() {
        return dollar;
    }

    public void setDollar(Integer dollar) {
        this.dollar = dollar;
    }

    public Double getKurs() {
        return kurs;
    }

    public void setKurs(Double kurs) {
        this.kurs = kurs;
    }

    public Double getSum_d() {
        return sum_d;
    }

    public void setSum_d(Double sum_d) {
        this.sum_d = sum_d;
    }

    public Integer getKol() {
        return kol;
    }

    public void setKol(Integer kol) {
        this.kol = kol;
    }


}
