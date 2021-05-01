package com.example.shop.adapter;

import com.example.shop.model.Product;
import com.example.shop.model.STovar;
import com.example.shop.model.User;

import java.util.ArrayList;
import java.util.List;

public interface IncomingAddListener {
    public void itemSeriesClick(ArrayList<Product> products , Integer position);
    //public void itemSeriesClick(String ip, User user , STovar  , Integer slaveId , String name , String soni , Integer id);
}
