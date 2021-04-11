package com.example.shop;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import me.sudar.zxingorient.ZxingOrient;
import me.sudar.zxingorient.ZxingOrientResult;

public class IncomingWork extends AppCompatActivity {

    private Intent intent;
    private ImageView barcodescan;
    private ImageView add;

    private ListView listView;
    private SearchView searchView;
    private ArrayList<SeriesModel> list;
    private STovarAdapter adapter;
    private ProgressDialog progressDialog;
    private String ip;
    private User thisuUser;
    private String barcode;
    private SeriesModel seriesModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incoming_work);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Seriya Raqam");
        setSupportActionBar(toolbar);

        add = findViewById(R.id.products_list_input_add);
        barcodescan = findViewById(R.id.products_list_barcodescan);
        listView = findViewById(R.id.products_list_list_view);
        searchView = findViewById(R.id.searchView);

        intent = getIntent();
        ip = intent.getStringExtra("ip");
        thisuUser = (User) intent.getSerializableExtra("user");
        seriesModel = (SeriesModel) intent.getSerializableExtra("stovar");
        list = new ArrayList<>();

        barcodescan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new ZxingOrient(IncomingWork.this).setIcon(R.mipmap.ic_launcher).initiateScan();
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent){

        ZxingOrientResult scanResult =
                ZxingOrient.parseActivityResult(requestCode, resultCode, intent);

        if (scanResult != null) {
            // handle the result
            CharSequence c=scanResult.getContents();
            Log.v("MyTag",""+c);
            setText(c);
        }

    }

    public void setText(CharSequence sequence){

       //     barcode1.setText(sequence, TextView.BufferType.EDITABLE); // list add

    }

    public void setDownIntent(Intent nextIntent) {
        nextIntent.putExtra("user",intent.getSerializableExtra("user"));
        nextIntent.putExtra("ip",intent.getStringExtra("ip"));
        nextIntent.putExtra("asosId",intent.getIntExtra("asosId",0));
        nextIntent.putExtra("type",intent.getIntExtra("type",0));
        nextIntent.putExtra("sumprice",intent.getStringExtra("sumprice"));
        nextIntent.putExtra("stovar",intent.getSerializableExtra("stovar"));
    }

    private  class AddNewProduct extends AsyncTask<Void,Void,Void> {

        ProgressDialog progressDialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog=new ProgressDialog(IncomingWork.this);
            progressDialog.setMessage("Сақлаyмоқда !!!");
            progressDialog.setCancelable(false);
            progressDialog.show();

        }

        @Override
        protected Void doInBackground(Void... voids) {
            HttpHandler httpHandler=new HttpHandler();
            String reqUrl="http://"+ip+":8080/application/json/addproduct";
            Integer x=httpHandler.makeServicePostSeries(reqUrl,seriesModel,thisuUser);
            Log.v("MyTag:",x+" sTovar:"+seriesModel.toString());
            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if(progressDialog.isShowing())
                progressDialog.dismiss();
            Intent nextIntent = new Intent(IncomingWork.this, ProductsList.class);
            setDownIntent(nextIntent);
            startActivity(nextIntent);
            finish();
        }
    }

}
