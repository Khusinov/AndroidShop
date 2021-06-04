package com.example.shop.model;

public class Slave {

    public Integer getPutId() {
        return putId;
    }

    public void setPutId(Integer putId) {
        this.putId = putId;
    }

    private Integer putId;
    private Integer id;
    private Integer tovar_id;
    private String tovar_nom;
    private Integer asos_id;
    private Integer user_id;
    private Integer kol;
    private Integer kol_in;
    private Integer kol_ost;
    private Integer kol_in_ost;
    private  Double sena;
    private  Double sena_in;
    private  Double sotish;
    private  Double sotish_in;
    public Slave() {
    }

    public Slave(Integer putId, Integer id, Integer asos_id,Integer user_id,Integer tovar_id,String tovar_nom, Double sotish, Double sotish_in, Integer kol, Integer kol_in, Integer kol_ost, Integer kol_in_ost) {
        this.putId = putId;
        this.id = id;
        this.tovar_nom = tovar_nom;
        this.tovar_id = tovar_id;
        this.asos_id = asos_id;
        this.user_id = user_id;
        this.sotish = sotish;
        this.sotish_in = sotish_in;
        this.kol = kol;
        this.kol_in = kol_in;
        this.kol_ost = kol_ost;
        this.kol_in_ost = kol_in_ost;
    }

    public Slave(String tovar_nom) {
        this.tovar_nom = tovar_nom;
    }

    public Slave(Integer id, String tovar_nom, Double sena, Double sena_in) {
        this.id = id;
        this.tovar_nom = tovar_nom;
        this.sena = sena;
        this.sena_in = sena_in;
    }


    public Slave(String tovar_nom, Double sena, Double sena_in) {
        this.tovar_nom = tovar_nom;
        this.sena = sena;
        this.sena_in = sena_in;
    }
    public Integer getTovar_id() {
        return tovar_id;
    }

    public void setTovar_id(Integer tovar_id) {
        this.tovar_id = tovar_id;
    }

    public String getTovar_nom() {
        return tovar_nom;
    }

    public void setTovar_nom(String tovar_nom) {
        this.tovar_nom = tovar_nom;
    }

    public Integer getAsos_id() {
        return asos_id;
    }

    public void setAsos_id(Integer asos_id) {
        this.asos_id = asos_id;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public Integer getKol() {
        return kol;
    }

    public void setKol(Integer kol) {
        this.kol = kol;
    }

    public Integer getKol_in() {
        return kol_in;
    }

    public void setKol_in(Integer kol_in) {
        this.kol_in = kol_in;
    }

    public Integer getKol_ost() {
        return kol_ost;
    }

    public void setKol_ost(Integer kol_ost) {
        this.kol_ost = kol_ost;
    }

    public Integer getKol_in_ost() {
        return kol_in_ost;
    }

    public void setKol_in_ost(Integer kol_in_ost) {
        this.kol_in_ost = kol_in_ost;
    }

    public Double getSena() {
        return sena;
    }

    public void setSena(Double sena) {
        this.sena = sena;
    }

    public Double getSena_in() {
        return sena_in;
    }

    public void setSena_in(Double sena_in) {
        this.sena_in = sena_in;
    }

    public Double getSotish() {
        return sotish;
    }

    public void setSotish(Double sotish) {
        this.sotish = sotish;
    }

    public Double getSotish_in() {
        return sotish_in;
    }

    public void setSotish_in(Double sotish_in) {
        this.sotish_in = sotish_in;
    }

    public Slave(Integer id, String tovar_nom, Double sotish, Double sotish_in, Integer kol, Integer kol_in, Integer kol_ost, Integer kol_in_ost) {
        this.id = id;
        this.tovar_nom = tovar_nom;
        this.sotish = sotish;
        this.sotish_in = sotish_in;
        this.kol = kol;
        this.kol_in = kol_in;
        this.kol_ost = kol_ost;
        this.kol_in_ost = kol_in_ost;
    }



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


}
