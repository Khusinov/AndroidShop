package com.example.shop;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class TypeChangeActivity extends AppCompatActivity {

    private AutoCompleteTextView client;
    private List<String> list;
    private List<Integer> listId;
    private Intent mainIntent;

    private static String ip="192.168.43.57";
    private Integer  asosId;
    private User thisUser;
    private ProgressDialog progressDialog;
    private ArrayAdapter<String> adapter;
    private Integer haridorId;
    private Integer type;
    private RadioButton type1;
    private RadioButton type2;
    private RadioButton type3;
    private RadioButton type4;
    private RadioButton type5;
    private RadioButton type6;
    private Integer selectionIndex=-1;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type_change);
        Button gone=findViewById(R.id.gone);
        type1=findViewById(R.id.type1);
        type2=findViewById(R.id.type2);
        type3=findViewById(R.id.type3);
        type4=findViewById(R.id.type4);
        type5=findViewById(R.id.type5);
        type6=findViewById(R.id.type6);

        mainIntent=getIntent();
        thisUser=(User)mainIntent.getSerializableExtra("user");
        ip=mainIntent.getStringExtra("ip");
        type=mainIntent.getIntExtra("type",0);

        listId=new ArrayList<>();
        list =new ArrayList<>();
        new getHaridor().execute();


        client = (AutoCompleteTextView) findViewById(R.id.client);
        adapter=new ArrayAdapter<>(this,android.R.layout.simple_dropdown_item_1line, list);
        client.setAdapter(adapter);
        client.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long rowId) {
                String selection = (String)parent.getItemAtPosition(position);
                selectionIndex=list.indexOf(selection);
                haridorId=listId.get(selectionIndex);
            }
        });

       retrieveChoices();
//       retrieveAutoCompleteTextView();
        gone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                downActivity();
            }
        });


    }

    @Override
    public void onBackPressed() {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        finish();
                        break;
                    case DialogInterface.BUTTON_NEGATIVE:
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(TypeChangeActivity.this);
        builder.setMessage("Дастурдан чикишни истайсизми?").setPositiveButton("Ха", dialogClickListener)
                .setNegativeButton("Йўқ", dialogClickListener).show();
    }

   /* private void saveAutoCompleteTextView(){
        android.content.SharedPreferences sPref = getPreferences(MODE_PRIVATE);
        android.content.SharedPreferences.Editor ed = sPref.edit();
        ed.putInt(SharedPreferences.AUTOCOMPLETETEXTVIEW_SHARED_PREF, selectionIndex);
        ed.apply();
    }

    private void retrieveAutoCompleteTextView(){
        android.content.SharedPreferences sPref = getPreferences(MODE_PRIVATE);
        int i=sPref.getInt(SharedPreferences.AUTOCOMPLETETEXTVIEW_SHARED_PREF, -1);
        if( i >= 0){
            client.setThreshold(0);
            client.setText(list.get(i));
            haridorId=listId.get(i);
        }
    }*/

    private void saveRadioChoice(){
        android.content.SharedPreferences sPref = getPreferences(MODE_PRIVATE);
        android.content.SharedPreferences.Editor ed = sPref.edit();
        RadioGroup localRadioGroup = (RadioGroup) findViewById(R.id.choices);
        ed.putInt(SharedPreferences.CHOICES__SHARED_PREF, localRadioGroup.indexOfChild(findViewById(localRadioGroup.getCheckedRadioButtonId())));
        ed.apply();
    }

    private void retrieveChoices(){

        android.content.SharedPreferences sPref = getPreferences(MODE_PRIVATE);
        int i=sPref.getInt(SharedPreferences.CHOICES__SHARED_PREF, -1);
        if( i >= 0){
            ((RadioButton) ((RadioGroup)findViewById(R.id.choices)).getChildAt(i)).setChecked(true);
        }
    }


    private void downActivity(){
        if (chechIsSelectItems()){
            new getAsos().execute();
        }
        else{
            Toast.makeText(TypeChangeActivity.this,"Савдо турини танланг !!!",Toast.LENGTH_LONG).show();
        }
    }

    private boolean chechIsSelectItems() {
        if(type1.isChecked()){
            type=1;
            return true;
        }
        else if(type2.isChecked()){
            type=2;
            return true;
        }
        else if(type3.isChecked()){
            type=3;
            return true;
        }
        else if(type4.isChecked()){
            type=4;
            return true;
        }
        else if(type5.isChecked()){
            type=5;
            return true;
        }
        else if(type6.isChecked()){
            type=6;
            return true;
        }
        else{
            type=0;
            return  false;
        }

    }

    private class getAsos extends AsyncTask<Void,Void,Void>{


        HttpHandler httpHandler=new HttpHandler();
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog=new ProgressDialog(TypeChangeActivity.this);
            progressDialog.setMessage("Малумот юкланяпти");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }
        @Override
        protected Void doInBackground(Void... voids) {
            if (thisUser.getId()!=null) {
                String urlPostAsos = "http://" + ip + ":8080/application/json/asos";
                Log.v("MyLog", urlPostAsos);
                String jsonAsosStr=httpHandler.makeServiceCreateAsos(urlPostAsos, thisUser,haridorId,type);
                JSONObject jsonAsos= null;
                try {
                    jsonAsos = new JSONObject(jsonAsosStr);
                    if(!jsonAsos.isNull("id")){
                        asosId=jsonAsos.getInt("id");
//                        haridorId=jsonAsos.getInt("haridorId");
                        Log.v("MyLog3","TypeChange:"+asosId);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if(progressDialog.isShowing()){
                progressDialog.dismiss();
            }
            saveRadioChoice();
//            saveAutoCompleteTextView();
            mainIntent.putExtra("type",type);
            mainIntent.putExtra("asosId",asosId);
            Log.v("MyLog3","mainIntent"+(Integer)mainIntent.getIntExtra("asosId",0));
            Intent intent = new Intent(TypeChangeActivity.this, MainActivity.class);
            intent.putExtra("user", mainIntent.getSerializableExtra("user"));
            intent.putExtra("ip", mainIntent.getStringExtra("ip"));
            intent.putExtra("asosId",mainIntent.getIntExtra("asosId",0));
            intent.putExtra("type",mainIntent.getIntExtra("type",0));
            intent.putExtra("sumprice",mainIntent.getStringExtra("sumprice"));

            startActivity(intent);
            finish();
        }
    }
    private class getHaridor extends AsyncTask<Void,Void,Void>{


        HttpHandler httpHandler=new HttpHandler();
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog=new ProgressDialog(TypeChangeActivity.this);
            progressDialog.setMessage("Малумот юкланяпти");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }
        @Override
        protected Void doInBackground(Void... voids) {
            if (thisUser.getId()!=null) {
//                http://localhost:8080/application/json/clientid=4/harodors
                String urlGetHaridor = "http://" + ip + ":8080/application/json/clientid="+thisUser.getClientId()+"/harodors";

                String jsonHaridorsStr=httpHandler.makeServiceCall(urlGetHaridor);
                if(jsonHaridorsStr != null) {
                    try {
                        JSONArray jsonArray = new JSONArray(jsonHaridorsStr);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            Product product = new Product();
                            JSONObject object = jsonArray.getJSONObject(i);

                           /* {
                                    "id": 1,
                                    "clientId": 4,
                                    "nom": "Нуржамол опа (Нурлан )",
                                    "adress": "Беруний",
                                    "manzil": null
                            },*/
                            listId.add(object.getInt("id"));
                            list.add(object.getString("nom"));
                            adapter.notifyDataSetChanged();

                        }
                    } catch (final JSONException e) {
                        Log.v("MyTag2", e.getMessage());
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(TypeChangeActivity.this, "Хатолик юз берди", Toast.LENGTH_LONG).show();
                            }
                        });
                    }

                }
                else{
                    Log.v("MyTag2", "serverdan galmadi");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(TypeChangeActivity.this,"Сервер билан муамо бор",Toast.LENGTH_LONG).show();
                        }
                    });
                }



            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if(progressDialog.isShowing()){
                progressDialog.dismiss();
            }
        }
    }





}
