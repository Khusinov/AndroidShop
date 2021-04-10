package com.example.shop;

public class Product {

    private Integer putId;
    private Integer id;
    private String name;
    private Double price;
    private Double inprice;
    private Integer count;
    private Integer incount;
    private  String shtrix;
    private Integer incnt;
    private  Double sena_d;
    private  Double sena_in_d;
    private  String shtrix_full;

    public Product() {
    }

    public Product(Integer putId, Integer id, String name, Double price, Double inprice, Integer count, Integer incount) {
        this.putId = putId;
        this.id = id;
        this.name = name;
        this.price = price;
        this.inprice = inprice;
        this.count = count;
        this.incount = incount;
    }

    public Product(String name) {
        this.name = name;
    }

    public Product(Integer id, String name, Double price, Double inprice) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.inprice = inprice;
    }


    public Product(String name, Double price, Double inprice) {
        this.name = name;
        this.price = price;
        this.inprice = inprice;
    }


    public String getShtrix_full() {
        return shtrix_full;
    }

    public void setShtrix_full(String shtrix_full) {
        this.shtrix_full = shtrix_full;
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

    public Integer getIncnt() {
        return incnt;
    }

    public void setIncnt(Integer incnt) {
        this.incnt = incnt;
    }

    public String getShtrix() {
        return shtrix;
    }

    public void setShtrix(String shtrix) {
        this.shtrix = shtrix;
    }

    public Integer getPutId() {
        return putId;
    }

    public void setPutId(Integer putId) {
        this.putId = putId;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getIncount() {
        return incount;
    }

    public void setIncount(Integer incount) {
        this.incount = incount;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getInprice() {
        return inprice;
    }

    public void setInprice(Double inprice) {
        this.inprice = inprice;
    }

    @Override
    public String toString() {
        return "Product{" +
                "putId=" + putId +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", inprice=" + inprice +
                ", count=" + count +
                ", incount=" + incount +
                ", shtrix='" + shtrix + '\'' +
                '}';
    }
}
