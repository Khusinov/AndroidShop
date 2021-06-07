package com.example.shop.adapter;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shop.HttpHandler;
import com.example.shop.R;
import com.example.shop.model.Slave;
import com.example.shop.ui.IncomingAdd;
import com.example.shop.ui.IncomingProducts;
import com.example.shop.ui.IncomingWork;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ItemSlaveAdapter extends ArrayAdapter<Slave> {

    private  Slave item;
    private ItemSlaveAdapter adapter;
    private String reqUrl = "";
    private Integer position=-1;
    private Integer asosId=-1;
    private IncomingAddListener listener;
    private String ip ;
    private ArrayList<Slave> items;

    public ItemSlaveAdapter(Context context, int resource, ArrayList<Slave> items, String ip, Integer asosId, IncomingAddListener listener) {
        super(context,resource, items);
        this.items = items;
        this.adapter=this;
        this.reqUrl="http://"+ip+":8080/application/json/delasosslave/asosid=";
        this.asosId=asosId;
        this.ip = ip ;
        this.listener = listener;
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
    public View getView(final int position, View convertView, ViewGroup parent) {

        item=getItem(position);
        Integer putId=item.getPutId();
        if (convertView == null) {
            LayoutInflater inflater =LayoutInflater.from(getContext());
            convertView=inflater.inflate(R.layout.list_item, parent, false);
        }


        if(this.position.equals(position)){
            convertView.setBackgroundResource(R.drawable.backgroun3ch);
        }
        else{
            convertView.setBackgroundResource(R.drawable.backgroun3);
        }
        ((TextView)convertView.findViewById(R.id.item_name)).setText(item.getTovar_nom());
        ((TextView)convertView.findViewById(R.id.item_count)).setText(item.getKol().toString());
        ((TextView)convertView.findViewById(R.id.item_incount)).setText(item.getKol_in().toString());
       // ((TextView)convertView.findViewById(R.id.item_sum)).setText(getModny(item.getKol()*item.getSena()+item.getKol_in()*item.getSena_in()));

        ((TextView)convertView.findViewById(R.id.item_seriya)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //listener.itemSlaveClick(items , position ); //888
                Intent intent = new Intent(getContext(), IncomingWork.class);
                intent.putExtra("ip", ip);
                intent.putExtra("slaveId", items.get(position).getId()); // slaveId
                intent.putExtra("name",items.get(position).getTovar_nom());
                intent.putExtra("soni", items.get(position).getKol());
                intent.putExtra("id", items.get(position).getId());
                getContext().startActivity(intent);
            }
        });



        ((ImageView)convertView.findViewById(R.id.item_delete)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                               new DelItem().execute();
                                break;
                            case DialogInterface.BUTTON_NEGATIVE:
                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setMessage("Сиз ростан хам ўчиришни истайсизми?").setPositiveButton("Ха", dialogClickListener)
                        .setNegativeButton("Йўқ", dialogClickListener).show();
            }
        });
       convertView.setTag(item);
       return convertView;
    }




    public class DelItem extends AsyncTask<Void, Void, Void> {
        ProgressDialog progressDialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog=new ProgressDialog(getContext());
            progressDialog.setMessage("Kirim tovar y`chirilmoqda !!!");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            HttpHandler httpHandler=new HttpHandler();
            Log.v("myTag",reqUrl);
            httpHandler.makeServiceDelItem(reqUrl+ asosId+"/id="+item.getId());
//            +47+"/id="+8
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if(progressDialog.isShowing()){
                progressDialog.dismiss();
            }
            adapter.remove(item);
        }
    }




}
