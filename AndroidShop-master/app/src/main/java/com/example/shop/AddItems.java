package com.example.shop;

public class AddItems {
  private Integer putId;
  private Integer id;
  private String name;
  private Integer price;
  private Integer inprice;
  private Integer count;
  private Integer incount;

  public AddItems() {
  }

  public AddItems(Integer putId, Integer id, String name, Integer price, Integer inprice, Integer count, Integer incount) {
    this.putId = putId;
    this.id = id;
    this.name = name;
    this.price = price;
    this.inprice = inprice;
    this.count = count;
    this.incount = incount;
  }

  public Integer getPutId() {
    return putId;
  }

  public void setPutId(Integer putId) {
    this.putId = putId;
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

  public Integer getPrice() {
    return price;
  }

  public void setPrice(Integer price) {
    this.price = price;
  }

  public Integer getInprice() {
    return inprice;
  }

  public void setInprice(Integer inprice) {
    this.inprice = inprice;
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
}
