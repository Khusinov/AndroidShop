package com.example.shop;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import java.text.DecimalFormat;
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

    private String getModny(Double DoubleValue){
        DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
        String formattedValue = decimalFormat.format(DoubleValue);
        return formattedValue;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SeriesModel STovar=getItem(position);
        LayoutInflater inflater =LayoutInflater.from(getContext());
        convertView=inflater.inflate(R.layout.stovar_item, parent, false);

        if(this.position.equals(position) ){
            convertView.setBackgroundResource(R.drawable.backgroun4ch);
        }
        else{
            convertView.setBackgroundResource(R.drawable.backgroun4);
        }



       // ((TextView)convertView.findViewById(R.id.stovar_name)).setText(STovar.getNom());

        convertView.setTag(STovar);
        return convertView;
    }

}

