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

public class ProductAdapter extends ArrayAdapter<Product> {

    public ArrayList<Product> list;
    public ArrayList<Product> originalList = new ArrayList<>();
    private CustomFilter filter;
    private Integer position=-1;


    public ProductAdapter( Context context, int resource, ArrayList<Product> products) {
        super(context, resource, products);
        list=products;
        originalList.addAll(products);
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
    public View getView(int position, View convertView,ViewGroup parent) {
        Product product=getItem(position);
        LayoutInflater inflater =LayoutInflater.from(getContext());
        convertView=inflater.inflate(R.layout.products_item, parent, false);

        if(this.position.equals(position) ){
            convertView.setBackgroundResource(R.drawable.backgroun4ch);
        }
        else{
            convertView.setBackgroundResource(R.drawable.backgroun4);
        }


        ((TextView)convertView.findViewById(R.id.product_name)).setText(product.getName());
        ((TextView)convertView.findViewById(R.id.product_prices)).setText("("+ product.getCount()+" та, "+getModny(product.getPrice())+" Сўм)");
//        ((TextView)convertView.findViewById(R.id.product_inprices)).setText("("+ product.getIncount()+" та, "+getModny(product.getInprice())+" Сўм)");
        ((TextView)convertView.findViewById(R.id.product_inprices)).setText( "( " +getModny(product.getSena_d())+" =)");

        convertView.setTag(product);
        return convertView;
    }

    @Override
    public Filter getFilter() {
        if(filter==null)
        {
            filter=new CustomFilter(originalList,this);
        }

        return filter;
    }
}
