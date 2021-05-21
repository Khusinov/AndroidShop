package com.example.shop.model;

public class Brend {
    private Integer id ;
    private String nom ;

    public Brend(Integer id, String nom) {
        this.id = id;
        this.nom = nom;
    }

    public Brend() {
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
}
