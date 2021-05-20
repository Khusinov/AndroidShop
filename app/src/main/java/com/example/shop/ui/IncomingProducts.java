package com.example.shop.ui;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.shop.HttpHandler;
import com.example.shop.R;
import com.example.shop.model.AsosModell;
import com.example.shop.model.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static android.widget.Toast.LENGTH_SHORT;

public class IncomingProducts extends AppCompatActivity {
    DatePickerDialog picker;
    EditText incomingDate;
    EditText incomingNum;
    AutoCompleteTextView incomingdiller;
    CheckBox incomingDollar;
    Spinner listView;
    List<CharSequence> list;
    List<String> dillerList;
    List<Integer> dillerListId;
    //List<AsosModell> modelAsos;
    List<AsosModell> modellList;
    List<String> listAsos;
    ArrayAdapter<String> adapterdillers;
    ArrayAdapter<CharSequence> adapter;
    ProgressDialog progressDialog;
    User thisUser;
    String ip;
    Intent intent;
    private Boolean clear;
    private AsosModell asos;
    private AsosModell inserAsos;
    private Integer dillerId;
    private ImageView save;
    private Integer newAsosCheck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.incomingproducts);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        incomingDate = findViewById(R.id.incoming_date);
        incomingDate.setInputType(InputType.TYPE_NULL);
        incomingNum = findViewById(R.id.incoming_num);
        incomingdiller = findViewById(R.id.incoming_diller);
        incomingDollar = findViewById(R.id.incoming_dollar);
        listView = findViewById(R.id.incoming_ac_list);
        save = findViewById(R.id.incoming_save);
        ImageView imageView4 = findViewById(R.id.imageView4);
        Button next = findViewById(R.id.incoming_add_product_next);
        intent = getIntent();
        thisUser = (User) intent.getSerializableExtra("user");
        ip = intent.getStringExtra("ip");
        clear = false;
        newAsosCheck = 0;

        asos = new AsosModell();
        //  asos.setId(1); //  modellList.get().getId()
        asos.setClient_id(thisUser.getClient_id());
        asos.setUserId(thisUser.getId());
        asos.setDel_flag(1);
        asos.setTur_oper(1);
        asos.setXodimId(thisUser.getId());
        asos.setHaridorId(0);
        asos.setSana("");
        asos.setDiler_id(0); // test uchun
        asos.setTur_oper(1);
        asos.setSumma(0.0);
        asos.setSotuv_turi(1);
        asos.setNomer("");
        asos.setDollar(1);
        asos.setKurs(0.0);
        asos.setSum_d(0.0);
        asos.setKol(1);

        //This is plus button | Add new Document | Save edi.
        imageView4.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                clear = false ; // clear qilmaydi
                inserAsos = new AsosModell();
                asos.setNomer(incomingNum.getText().toString());
                asos.setSana(incomingDate.getText().toString());
               // Log.d("DillerId" , dillerId.toString());
                if (!dillerId.equals("null")){
                    asos.setDiler_id(dillerId);
                    Log.d("DillerID_IP", dillerId.toString());

                    asos.setId(1);
                    int check = 0;
                    if (incomingDollar.isChecked()) {
                        check = 1;
                    }
                    asos.setDollar(check);
                    newAsosCheck = 1;
                    copyProperties(inserAsos, asos);
                    Log.d("Getgani", inserAsos.getSumma().toString());
                    new getDiller().execute();
                    incomingDate.setText("");
                    incomingNum.setText("");
                    incomingdiller.setText("");
                    incomingDollar.setChecked(false);
                    // newAsosCheck = 0 ;
                    // new getDiller().execute();

                } else {
                incomingdiller.setError("Diller yo'q");
                }

            }
        });

        // Edit qilib saqlash uchun | This is save button

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clear = true ; // clear qiladi
                inserAsos = new AsosModell();
                asos.setNomer(incomingNum.getText().toString());
                asos.setSana(incomingDate.getText().toString());
                asos.setDiler_id(dillerId);
                Log.d("DillerID_IPSave", dillerId.toString());
               //  asos.setId(); | id modellistdan Asos ga o'tadi
                int check = 0;
                if (incomingDollar.isChecked()) {
                    check = 1;
                }
                asos.setDollar(check);
                copyProperties(inserAsos, asos);
                Log.d("Save ID", inserAsos.getId().toString());
                new EditAsos().execute();
                newAsosCheck = 0 ;
                new getDiller().execute();
            }
        });

        incomingdiller.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String selectDiller = adapterView.getAdapter().getItem(i).toString();
                for (int i1 = 0; i1 < dillerList.size(); i1++) {
                    Log.d("Diller", dillerList.get(i1));
                    if (selectDiller.equals(dillerList.get(i1))) {
                        dillerId = dillerListId.get(i1);
                        Log.d("DillerId1", dillerId.toString());
                    }
                }
                Log.d("Adapter", adapterView.getAdapter().getItem(i).toString());

                // dillerId = dillerListId.get(i);// xato shu joyda
            }
        });
        try {
            listView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                @Override
                public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

                    Log.d("Modellist_IP1", modellList.toString());
                    Log.d("position", String.valueOf(position));
                    try {
                        asos = modellList.get(position);
                        Log.d("Asos", asos.getId().toString());
                    } catch (Exception e) {
                        Log.d("Catch", e.toString());
                    }

                    Integer index = dillerListId.indexOf(asos.getDiler_id());
                    Log.d("Index", index.toString());
                    if ((index.equals(null) && index.equals(-1))) {
                        try {
                            incomingdiller.setSelection(index);
                        } catch (Exception e) {
                            Log.d("CatchError1", e.toString());
                            Toast.makeText(IncomingProducts.this, "Taminotchi yo'q 1", LENGTH_SHORT).show();
                        }

                        dillerId = dillerListId.get(index); // +1
                        incomingdiller.setText(incomingdiller.getAdapter().getItem(index).toString(), false);
                    }
                    if (index > -1) {
                        dillerId = dillerListId.get(index);
                        Log.d("Dillerid2", dillerId.toString());
                        incomingdiller.setText(dillerList.get(index));
                        incomingNum.setText(asos.getNomer());
                        incomingDate.setText(asos.getSana());
                        incomingDollar.setChecked(asos.getDollar() == 1);
                    }
                    // incomingdiller.setText(dillerList.get(asos.getDiler_id()));
                    // incomingdiller.setText(asos.getDiler_id().toString());
                  //  incomingNum.setText(asos.getNomer());
                  //  incomingDate.setText(asos.getSana());
                   // incomingDollar.setChecked(asos.getDollar() == 1);

                }

                @Override
                public void onNothingSelected(AdapterView<?> parentView) {
                    incomingNum.setText("");
                    incomingDate.setText("");
                    incomingdiller.setText("");
                    incomingDollar.setChecked(false);
                }

            });
        } catch (Exception e) {
            Log.d("ErrCatch", e.toString());
            Toast.makeText(IncomingProducts.this, "Taminotchi yo'q 2", LENGTH_SHORT).show();
        }

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent nextIntent = new Intent(IncomingProducts.this, IncomingAdd.class);
                setDownIntent(nextIntent);
                startActivity(nextIntent);
            }
        });

        modellList = new ArrayList<>();
        list = new ArrayList<>();
        dillerList = new ArrayList<>();
        dillerListId = new ArrayList<>();

        listAsos = new ArrayList<>();

        adapterdillers = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, dillerList);
        incomingdiller.setAdapter(adapterdillers);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, list);
        listView.setAdapter(adapter);

        new getDiller().execute();

        incomingDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(IncomingProducts.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                incomingDate.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                            }
                        }, year, month, day);
                picker.show();
            }
        });


    }

    private void copyProperties(AsosModell asosBefore, AsosModell asosLast) {
        if (newAsosCheck == 0) {
            asosBefore.setId(asosLast.getId());
        }
        asosBefore.setUserId(asosLast.getUserId());
        asosBefore.setDollar(asosLast.getDollar());
        asosBefore.setClient_id(asosLast.getClient_id());
        asosBefore.setDel_flag(1);
        asosBefore.setTur_oper(1);
        asosBefore.setXodimId(asosLast.getXodimId());
        asosBefore.setHaridorId(0);
        asosBefore.setSana(asosLast.getSana());
        asosBefore.setDiler_id(asosLast.getDiler_id());
        Log.d("dillerid", asosBefore.getDiler_id().toString());
        asosBefore.setTur_oper(1);
        asosBefore.setSumma(0.0);
        asosBefore.setSotuv_turi(1);
        asosBefore.setNomer(asosLast.getNomer());
        asosBefore.setKurs(asosLast.getKurs());
        asosBefore.setSum_d(0.0);
        asosBefore.setKol(1);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.item1) {
            Intent intent = new Intent(IncomingProducts.this, TypeChangeActivity.class);
            setDownIntent(intent);
            startActivity(intent);
            finish();
        }
        if (id == R.id.item2) {
            Intent intent = new Intent(IncomingProducts.this, MainActivity.class);
            setDownIntent(intent);
            startActivity(intent);
            finish();
        }
        if (id == R.id.item3) {
            Intent intent = new Intent(IncomingProducts.this, ProductsList.class);
            setDownIntent(intent);
            startActivity(intent);
            finish();
        }
        if (id == R.id.item5) {
            SharedPreferences preferences = getSharedPreferences("LoginPref", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();

            editor.clear();
            editor.apply();
            finish();
        }
        return super.onOptionsItemSelected(item);
    }


    private String getModny(Double DoubleValue) {
        DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
        String formattedValue = decimalFormat.format(DoubleValue);
        return formattedValue;
    }

    public void setDownIntent(Intent nextIntent) {
        nextIntent.putExtra("user", intent.getSerializableExtra("user"));
        nextIntent.putExtra("ip", intent.getStringExtra("ip"));
        nextIntent.putExtra("asosId", asos.getId());//intent.getIntExtra("asosId", 0)

        nextIntent.putExtra("type", intent.getIntExtra("type", 0));
        nextIntent.putExtra("sumprice", intent.getStringExtra("sumprice"));
        nextIntent.putExtra("stovar", intent.getSerializableExtra("stovar"));
    }

    private void loadData() {
        if (clear)  {
            list.clear();
        }
        for (int i = 0; i < modellList.size(); i++) {
            CharSequence x = "";
            int index = -1;
            index = dillerListId.indexOf(modellList.get(i).getDiler_id());
            if (index > -1)
                x = dillerList.get(index);
            else
                x = "Таминотчи йўқ";

            x = x + " | Сумма: " + getModny(modellList.get(i).getSumma());
            list.add(x);
            adapter.notifyDataSetChanged();
        }
    }

    class getDiller extends AsyncTask<Void, Void, Void> {

        HttpHandler httpHandler = new HttpHandler();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(IncomingProducts.this);
            progressDialog.setMessage("Малумот юкланяпти");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            if (thisUser.getId() != null) {
                String urlGetDillers = "http://" + ip + ":8080/application/json/" + thisUser.getClient_id() + "/dillers";
                String urlGetAsoss = "http://" + ip + ":8080/application/json/asoss";
                String urlNewAsos = "http://" + ip + ":8080/application/json/newasos";

                Log.v("MyTag", asos.toString());
                String jsonDillersStr = httpHandler.makeServiceCall(urlGetDillers);
                String jsonAsosStr;
                AsosModell asosModell = new AsosModell();
                asosModell.setUserId(asos.getUserId());
                asosModell.setClient_id(asos.getClient_id());
                asosModell.setDollar(asos.getDollar());
                asosModell.setDiler_id(asos.getDiler_id());
                asosModell.setSana(asos.getSana());
                asosModell.setKol(asos.getKol());

                if (newAsosCheck == 0) {
                    jsonAsosStr = httpHandler.makeServiceCreate(urlGetAsoss, asos);
                } else {
                    jsonAsosStr = httpHandler.makeServiceCreate(urlNewAsos, inserAsos);
                }

                if (jsonDillersStr != null) {
                    dillerList.clear();
                    dillerListId.clear();
                    Log.d("Dillers", jsonDillersStr);
                    try {
                        JSONArray jsonArray = new JSONArray(jsonDillersStr);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            dillerListId.add(object.getInt("id"));
                            dillerList.add(object.getString("nom"));
                            /* {
                           "id": 1,
                           "nom": "01.Artel dileri",
                           "tel": "+998999664660",
                           "client_id": 3
                           },*/

                        }
                        adapterdillers.notifyDataSetChanged();
                    } catch (final JSONException e) {
                        Log.v("new erorr", e.getMessage());
                        Log.d("ipsa", urlGetDillers);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(IncomingProducts.this, "Хатолик юз берди", Toast.LENGTH_LONG).show();
                            }
                        });
                    }

                } else {
                    Log.v("ntre", "serverdan galmadi");
                    //   Log.d("ipsa",urlGetDillers);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(IncomingProducts.this, "Сервер билан муамо бор 1", Toast.LENGTH_LONG).show();
                        }
                    });
                }
                Log.d("json", jsonAsosStr);
                if (jsonAsosStr != null) {
                    if (!jsonAsosStr.contains("[") && !jsonAsosStr.contains("]")) {
                        jsonAsosStr = "[" + jsonAsosStr + "]";
                        Log.d("JsonObj", jsonAsosStr);
                    }
                    modellList.clear();
                    try {
                        JSONArray jsonArray = new JSONArray(jsonAsosStr);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            Log.d("Object ", object.toString());
                            AsosModell modell = new AsosModell();
                            modell.setId(object.getInt("id"));
                            if (object.get("client_id").toString().equals("null")) {
                                modell.setClient_id(0);
                            } else
                                modell.setClient_id(object.getInt("client_id"));
                            if (object.get("userId").toString().equals("null")) {
                                modell.setUserId(0);
                            } else
                                modell.setUserId(object.getInt("userId"));
                            if (object.get("xodimId").toString().equals("null")) {
                                modell.setXodimId(0);
                            } else
                                modell.setXodimId(object.getInt("xodimId"));
                            if (object.get("haridorId").toString().equals("null")) {
                                modell.setHaridorId(-1);
                            } else {
                                modell.setHaridorId(object.getInt("haridorId"));
                            }
                            if (object.get("sana").toString().equals("null")) {
                                modell.setSana("");
                            } else
                                modell.setSana(object.getString("sana"));
                            if (object.get("diler_id").toString().equals("null")) {
                                modell.setDiler_id(0);
                            } else
                                modell.setDiler_id(object.getInt("diler_id"));

                            if (object.get("tur_oper").toString().equals("null")) {
                                modell.setTur_oper(0);
                            } else {
                                modell.setTur_oper(object.getInt("tur_oper"));
                            }
                            if (object.get("summa").toString().equals("null")) {
                                modell.setSumma(0.0);
                            } else
                                modell.setSumma(object.getDouble("summa"));
                            if (object.get("sotuv_turi").toString().equals("null")) {
                                modell.setSotuv_turi(0);
                            } else {
                                modell.setSotuv_turi(object.getInt("sotuv_turi"));
                            }
                            if (object.get("nomer").toString().equals("null")) {
                                modell.setNomer("");
                            } else
                                modell.setNomer(object.getString("nomer"));
                            if (object.get("del_flag").toString().equals("null")) {
                                modell.setDel_flag(0);
                            } else
                                modell.setDel_flag(object.getInt("del_flag"));
                            if (object.get("dollar").toString().equals("null")) {
                                modell.setDollar(0);
                            } else
                                modell.setDollar(object.getInt("dollar"));
                            if (object.get("kurs").toString().equals("null")) {
                                modell.setKurs(0.0);
                            } else {
                                modell.setKurs(object.getDouble("kurs"));
                            }
                            if (object.get("sum_d").toString().equals("null")) {
                                modell.setSum_d(0.0);
                            } else
                                modell.setSum_d(object.getDouble("sum_d"));
                            if (object.get("kol").toString().equals("null")) {
                                modell.setKol(0);
                            } else
                                modell.setKol(object.getInt("kol"));
                            /*{
                                "id": 1782,
                                    "clientId": 4,
                                    "userId": 30,
                                    "xodimId": 99,
                                    "haridorId": 1,
                                    "sana": "2019-09-18",
                                    "dilerId": 0,
                                    "turOper": 2,
                                    "summa": 4500,
                                    "sotuvTuri": 1,
                                    "nomer": null,
                                    "del_flag": 1,
                                    "dollar": null,
                                    "kurs": null,
                                    "sum_d": null,
                                    "kol": null
                            }*/
                            modellList.add(modell);
                            Log.d("Modellist_IP", modellList.toString());
                        }

                    } catch (JSONException e) {
                        Log.v("CatchError", e.getMessage());
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(IncomingProducts.this, "Хатолик юз берди 2", Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                }

            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
            newAsosCheck = 0;
            loadData();
        }
    }


    private  class EditAsos extends AsyncTask<Void,Void,Void>{

        ProgressDialog progressDialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog=new ProgressDialog(IncomingProducts.this);
            progressDialog.setMessage("Сақланмоқда!!!");
            progressDialog.setCancelable(false);
            progressDialog.show();

        }

        @Override
        protected Void doInBackground(Void... voids) {
            HttpHandler httpHandler=new HttpHandler();
            String reqUrl="http://"+ip+":8080/application/json/editasos";
            Integer x = httpHandler.makeServiceChangeAsos(reqUrl, inserAsos);
            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if(progressDialog.isShowing())
                progressDialog.dismiss();

        }
    }


}
