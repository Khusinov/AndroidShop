package com.example.shop.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.shop.R;
import com.example.shop.model.GetList;
import com.example.shop.model.Product;

import java.util.ArrayList;

public class GetListAdapter  extends ArrayAdapter<GetList> {

    public ArrayList<GetList> list;
    public ArrayList<GetList> originalList = new ArrayList<>();
    private Integer position=-1;

    public GetListAdapter(Context context, int resource, ArrayList<GetList> STovars) {
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
        GetList STovar=getItem(position);
        LayoutInflater inflater =LayoutInflater.from(getContext());
        convertView=inflater.inflate(R.layout.get_list_item, parent, false);


        if(this.position.equals(position) ){
            convertView.setBackgroundResource(R.drawable.backgroun4ch);
        }
        else{
            convertView.setBackgroundResource(R.drawable.backgroun4);
        }


        ((TextView)convertView.findViewById(R.id.kol)).setText("kolichstva " + STovar.getKol());
        ((TextView)convertView.findViewById(R.id.kolIn)).setText("kol in " + STovar.getKolIn());
        ((TextView)convertView.findViewById(R.id.kolOst)).setText("kol ost " + STovar.getKolOst() );
        ((TextView)convertView.findViewById(R.id.kolInOst)).setText("kol in ost" + STovar.getKolInOst());

        convertView.setTag(STovar);
        return convertView;
    }

}
