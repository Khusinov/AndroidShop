package com.example.shop;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import me.sudar.zxingorient.ZxingOrient;
import me.sudar.zxingorient.ZxingOrientResult;

public class IncomingWork extends AppCompatActivity {

    private Intent intent;
    private ImageView barcodescan;
    private ImageView add;

    private ListView listView;
    private TextView searchView;
    private ArrayList<SeriesModel> list;
    private SeriesAdapter adapter;
    private ProgressDialog progressDialog;
    private String ip;
    private User thisuUser;
    private STovar sTovar;
    private String barcode;
   // private STovar sTovar;
    private SeriesModel seriesModel;
    private Integer slaveId;
    private String name;
    private TextView soni;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incoming_work);



        add = findViewById(R.id.products_list_input_add);
        barcodescan = findViewById(R.id.products_list_barcodescan);
        listView = findViewById(R.id.products_list_list_view);
        searchView = findViewById(R.id.searchView);
        soni = findViewById(R.id.soni);

        intent = getIntent();
        ip = intent.getStringExtra("ip");
        thisuUser = (User) intent.getSerializableExtra("user");
        sTovar = (STovar) intent.getSerializableExtra("stovar");
        slaveId = intent.getIntExtra("slave_id",0);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(name);
        setSupportActionBar(toolbar);
        new GetSeries().execute();
        list = new ArrayList<>();
        seriesModel = new SeriesModel();
        soni.setText(String.valueOf(slaveId));

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

        searchView.setText(sequence, TextView.BufferType.EDITABLE); // list add
        seriesModel.setSerial(String.valueOf(sequence));
        new AddSeries().execute();


    }

    public void setDownIntent(Intent nextIntent) {
        nextIntent.putExtra("user",intent.getSerializableExtra("user"));
        nextIntent.putExtra("ip",intent.getStringExtra("ip"));
        nextIntent.putExtra("asosId",intent.getIntExtra("asosId",0));
        nextIntent.putExtra("type",intent.getIntExtra("type",0));
        nextIntent.putExtra("sumprice",intent.getStringExtra("sumprice"));
        nextIntent.putExtra("stovar",intent.getSerializableExtra("stovar"));
    }

    private class GetSeries extends AsyncTask<Void, Void, Void> {
        //        http://localhost:8080/application/json//4/products
        private String urlProducts="http://"+ip+":8080/application/json/getMainSlave/"+slaveId;
        // ishladi))
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog=new ProgressDialog(IncomingWork.this);
            progressDialog.setMessage("Малумот юкланяпти");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            HttpHandler httpHandler=new HttpHandler();
            String jsonStr=httpHandler.makeServiceCall(urlProducts);

            Log.d("ipmaa",urlProducts);
            // Log.d("jsons",jsonStr);

            if(jsonStr != null) {

                try {
                    JSONArray jsonArray = new JSONArray(jsonStr);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        SeriesModel tovar = new SeriesModel();
                        JSONObject object = jsonArray.getJSONObject(i);
                        Log.v("MyLog1",object.toString());
                       // Log.d("object", object.getString("nom"));
                        tovar.setId(object.getInt("id"));
                        tovar.setMain_id(object.getInt("main_id"));
                        tovar.setSlave_id(object.getInt("slave_id"));
                        tovar.setSerial(object.getString("serial"));
                        Log.d("nom",tovar.getSerial());

                        list.add(tovar);

                    }
                } catch (final JSONException e) {
                    Log.v("MyTag2", e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(IncomingWork.this, "Хатолик юз берди", Toast.LENGTH_LONG).show();
                        }
                    });
                }

            }
            else{
                Log.v("MyTag2", "serverdan galmadi");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(IncomingWork.this,"Сервер билан муамо бор",Toast.LENGTH_LONG).show();
                    }
                });
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if(progressDialog.isShowing()){
                progressDialog.dismiss();
            }
            adapter = new SeriesAdapter(IncomingWork.this,R.layout.stovar_item, list);
            listView.setAdapter(adapter);
        }
    }

    private  class AddSeries extends AsyncTask<Void,Void,Void> {

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
            String reqUrl="http://"+ip+":8080/application/json/addSerial/"+seriesModel.getSerial();
            String reqUrl2="http://"+ip+":8080/application/json/addMainSlave";
            Integer x=httpHandler.makeServicePostSeries(reqUrl,seriesModel,slaveId);
            Integer x2=httpHandler.makeServicePostSeriesWithSlave(reqUrl2,seriesModel,slaveId);
            Log.v("MyTag2:",x+" sTovar:"+seriesModel.toString());
            Log.v("Myssla:",x2+" sTovar:"+seriesModel.toString());
            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (progressDialog.isShowing())
                progressDialog.dismiss();
        }
    }

}
