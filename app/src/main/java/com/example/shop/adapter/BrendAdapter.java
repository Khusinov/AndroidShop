package com.example.shop.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.ArrayAdapter;

import com.example.shop.model.Brend;

import java.util.ArrayList;

public class BrendAdapter extends ArrayAdapter<Brend> {
    private ArrayList<Brend> brend;
    public BrendAdapter(@NonNull Context context, int resource, ArrayList<Brend> brends) {

        super(context, resource);
        this.brend = brends ;
    }
}
