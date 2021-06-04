package com.example.shop.adapter;

import com.example.shop.model.Slave;
import com.example.shop.model.STovar;
import com.example.shop.model.User;

import java.util.ArrayList;
import java.util.List;

public interface IncomingAddListener {
    public void itemSeriesClick(ArrayList<Slave> products , Integer position);

    void itemSlaveClick(ArrayList<Slave> items, int position);
    //public void itemSeriesClick(String ip, User user , STovar  , Integer slaveId , String name , String soni , Integer id);
}
