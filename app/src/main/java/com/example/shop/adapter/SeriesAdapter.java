package com.example.shop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.shop.R;
import com.example.shop.model.SeriesModel;

import java.util.ArrayList;

public class SeriesAdapter  extends ArrayAdapter<SeriesModel> {

    public ArrayList<SeriesModel> list;
    public ArrayList<SeriesModel> originalList = new ArrayList<>();
    private Integer position=-1;

    public SeriesAdapter(Context context, int resource, ArrayList<SeriesModel> STovars) {
        super(context, resource, STovars);
        list=STovars;
        originalList.addAll(STovars);
    }
    public void setPosition(int posit) {
        this.position = posit;
    }

    private int getPosition() {
        return this.position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SeriesModel STovar=getItem(position);
        LayoutInflater inflater =LayoutInflater.from(getContext());
        convertView=inflater.inflate(R.layout.activity_incoming__item, parent, false);


        if(this.position.equals(position) ){
            convertView.setBackgroundResource(R.drawable.backgroun4ch);
        }
        else{
            convertView.setBackgroundResource(R.drawable.backgroun4);
        }



       ((TextView)convertView.findViewById(R.id.Incoming_name)).setText(STovar.getSerial());

        convertView.setTag(STovar);
        return convertView;
    }

}

