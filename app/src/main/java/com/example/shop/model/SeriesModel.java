package com.example.shop.model;

import java.io.Serializable;

public class SeriesModel implements Serializable {
    private Integer id;
    private Integer main_id;
    private Integer slave_id;
    private String serial;

    public SeriesModel() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMain_id() {
        return main_id;
    }

    public void setMain_id(Integer main_id) {
        this.main_id = main_id;
    }

    public Integer getSlave_id() {
        return slave_id;
    }

    public void setSlave_id(Integer slave_id) {
        this.slave_id = slave_id;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }
}