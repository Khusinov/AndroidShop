package com.example.shop.ui;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.arch.lifecycle.Observer;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shop.HttpHandler;
import com.example.shop.R;
import com.example.shop.adapter.IncomingAddListener;
import com.example.shop.adapter.ItemSlaveAdapter;
import com.example.shop.adapter.STovarAdapter;
import com.example.shop.db.AppDatabase;
import com.example.shop.db.beans.STovar;
import com.example.shop.model.Slave;
import com.example.shop.model.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import me.sudar.zxingorient.ZxingOrient;
import me.sudar.zxingorient.ZxingOrientResult;


public class IncomingAdd extends AppCompatActivity {

    private static Integer selectedSlave = 0;
    private static Double selectProductSum = 0.0;
    private static Double sum = 0.0;
    Intent intent;
    ImageView barcodescan;
    private ArrayList<Slave> list2;
    private ItemSlaveAdapter adapter2;
    private ListView listView;
    private SwipeRefreshLayout swrl;
    private ListView listView2;
    private SearchView searchView;
    private STovarAdapter adapter;
    private ProgressDialog progressDialog;
    private String ip;
    private User thisuUser;
    private Integer asosId;
    private STovar tovar;
    private Slave selectSlave;
    private ImageView save;
    private EditText count;
    private EditText incount;
    private TextView nomer;
    private TextView sana;
    private TextView taminotchi;
    private LinearLayout main_changed1;
    private LinearLayout main_changed2;
    private Slave slave;
    private TextView selectProductView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incoming_add);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Кирим");
        setSupportActionBar(toolbar);

        save = findViewById(R.id.product_incoming_add_save);
        count = findViewById(R.id.product_incoming_add_price_product_count);
        incount = findViewById(R.id.product_incoming_add_price_inproduct_count);
        //  nomer = findViewById(R.id.nomerid);
//        sana = findViewById(R.id.sanaid);
        //   taminotchi = findViewById(R.id.taminotchiid);

        barcodescan = findViewById(R.id.product_incoming_add_barcodescan);
        listView = findViewById(R.id.product_incoming_add_list_view);
        swrl  = findViewById(R.id.sw_refresh_layout);
        listView2 = findViewById(R.id.product_incoming_add_list_view2);
        searchView = findViewById(R.id.product_incoming_add_searchView);
        intent = getIntent();
        main_changed1 = findViewById(R.id.linerLayout1);
        main_changed2 = findViewById(R.id.linerLayout2);
        selectProductView = findViewById(R.id.select_product);


        ip = intent.getStringExtra("ip");
        asosId = intent.getIntExtra("asosId", 0);
        thisuUser = (User) intent.getSerializableExtra("user");
        AppDatabase.getInstance(this).sTovarDao().getAllLive().observe(this, new Observer<List<STovar>>() {
            @Override
            public void onChanged(@Nullable List<STovar> sTovars) {
                showSTovarList((ArrayList<STovar>) sTovars);
            }
        });

        swrl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new GetSTovar().execute();
            }
        });

        list2 = new ArrayList<>();
        new GetProducts().execute();
        barcodescan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new ZxingOrient(IncomingAdd.this).setIcon(R.mipmap.ic_launcher).initiateScan();
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                adapter.getFilter().filter(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                adapter.getFilter().filter(s);
                return false;
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                STovar stovar = (STovar) view.getTag();
                if (stovar != null) {
                    tovar = stovar;
                    selectedSlave = 1;
                }
                Integer price_product_count_int = tryParse(count.getText().toString());
                Integer price_inproduct_count_int = tryParse(incount.getText().toString());
                Slave slave = new Slave();
                slave.setId(tovar.getId());
                slave.setPutId(0);
                slave.setTovar_id(tovar.getId());
                slave.setTovar_nom(tovar.getNom());
                slave.setKol(price_product_count_int);
                slave.setKol_in(price_inproduct_count_int);
                slave.setKol_ost(price_product_count_int);
                slave.setSena(0.0);
                slave.setSena_in(0.0);
                slave.setSotish_in(0.0);
                slave.setSotish_in(0.0);
                selectSlave = slave;
                selectedSlave = 2;
                adapter.setPosition(i);
                adapter2.setPosition(-1);
                adapter.notifyDataSetChanged();
                adapter2.notifyDataSetChanged();
                selectProduct();
            }
        });
        listView2.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        selectedSlave = 1;
                        adapter.setPosition(-1);
                        adapter2.setPosition(position);
                        adapter.notifyDataSetChanged();
                        adapter2.notifyDataSetChanged();
                        slave = (Slave) view.getTag();
                        selectSlave = slave;
                        selectProduct();
                    }
                }
        );
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedSlave == 2) {
                    Integer price_product_count_int = tryParse(count.getText().toString());
                    Integer price_inproduct_count_int = tryParse(incount.getText().toString());
                    Slave slave = new Slave();
                    slave.setPutId(0);
                    //slave.setId(tovar.getId());
                    slave.setTovar_nom(tovar.getNom());
                    slave.setTovar_id(tovar.getId());
                    slave.setAsos_id(asosId);
                    slave.setKol(price_product_count_int);
                    slave.setKol_in(price_inproduct_count_int);
                    slave.setKol_ost(price_product_count_int);
                    slave.setSena(0.0);
                    slave.setSena_in(0.0);
                    slave.setSotish_in(0.0);
                    slave.setSotish_in(0.0);
                    selectSlave = slave;
                    new AddProduct().execute();
                    list2.add(slave);
                    adapter2.notifyDataSetChanged();
                } else if (selectedSlave == 1) {
                    if (!count.getText().toString().equals("")) {
                        slave.setKol(Integer.valueOf(count.getText().toString()));
                    }

                    if (!incount.getText().toString().equals("")) {
                        slave.setKol_in(Integer.valueOf(incount.getText().toString()));
                    }


                    selectSlave = slave;
                    new PutSlave().execute();
                    //   new GetProducts().execute();
                }
                selectedSlave = 0;
                selectProduct();
            }
        });
    }

    private void showSTovarList(ArrayList<STovar> list) {
        adapter = new STovarAdapter(IncomingAdd.this, R.layout.stovar_item, list);
        listView.setAdapter(adapter);
    }

    public void showSoftKeyboard(View view) {
        if (view.requestFocus()) {
            InputMethodManager imm = (InputMethodManager)
                    getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
        }
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
            Intent intent = new Intent(IncomingAdd.this, TypeChangeActivity.class);
            setDownIntent(intent);
            startActivity(intent);
            finish();
        }
        if (id == R.id.item2) {
            Intent intent = new Intent(IncomingAdd.this, MainActivity.class);
            setDownIntent(intent);
            startActivity(intent);
            finish();
        }
        if (id == R.id.item4) {
            Intent intent = new Intent(IncomingAdd.this, IncomingProducts.class);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        ZxingOrientResult scanResult =
                ZxingOrient.parseActivityResult(requestCode, resultCode, intent);

        if (scanResult != null) {
            searchView.setQuery(scanResult.getContents(), false);
        }
    }

    public void setDownIntent(Intent nextIntent) {
        nextIntent.putExtra("user", intent.getSerializableExtra("user"));
        nextIntent.putExtra("ip", intent.getStringExtra("ip"));
        nextIntent.putExtra("asosId", intent.getIntExtra("asosId", 0));
        nextIntent.putExtra("type", intent.getIntExtra("type", 0));
        nextIntent.putExtra("sumprice", intent.getStringExtra("sumprice"));
        nextIntent.putExtra("stovar", intent.getSerializableExtra("stovar"));
    }

    @Override
    public void onBackPressed() {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        finish();
                        break;
                    case DialogInterface.BUTTON_NEGATIVE:
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(IncomingAdd.this);
        builder.setMessage("Дастурдан чикишни истайсизми?").setPositiveButton("Ха", dialogClickListener)
                .setNegativeButton("Йўқ", dialogClickListener).show();
    }


    private void selectProduct() {
        if (selectedSlave != 0) {
            incount.setEnabled(true);
            count.setEnabled(true);
            if (selectedSlave == 2) {
                Log.d("else2", "tttt");
                main_changed1.setBackgroundResource(R.drawable.backgroun4ch); //backgroun3ch
                main_changed2.setBackgroundResource(R.drawable.backgroun4ch);
                if (selectSlave.getKol() > 0) {
                    CharSequence c = "" + selectSlave.getKol();
                    count.setText(c, EditText.BufferType.EDITABLE);
                }
                if (selectSlave.getKol_in() > 0) {
                    CharSequence c = "" + selectSlave.getKol_in();
                    incount.setText(c, EditText.BufferType.EDITABLE);
                }
                if (selectSlave.getTovar_nom() != null) {
                    selectProductView.setText(selectSlave.getTovar_nom());
                }

            } else {
                incount.setEnabled(true);
                count.setEnabled(true);
                if (selectSlave.getKol() > 0) {
                    CharSequence c = "" + selectSlave.getKol();
                    count.setText(c, EditText.BufferType.EDITABLE);
                }
                if (selectSlave.getKol_in() > 0) {
                    CharSequence c = "" + selectSlave.getKol_in();
                    count.setText(c, EditText.BufferType.EDITABLE);
                }
                if (selectSlave.getTovar_nom() != null) {
                    selectProductView.setText(selectSlave.getTovar_nom());
                }
                main_changed1.setBackgroundResource(R.drawable.backgroun3ch); //backgroun4ch
                main_changed2.setBackgroundResource(R.drawable.backgroun3ch);
                Log.d("else1", "tttt");
            }
        } else {
            Log.d("elsenutnull", "tttt");
            selectProductView.setText(R.string.product);
            count.getText().clear();
            incount.getText().clear();
            main_changed1.setBackgroundResource(R.drawable.backgroun4);
            main_changed2.setBackgroundResource(R.drawable.backgroun4);

            adapter.setPosition(-1);
            adapter2.setPosition(-1);
            adapter.notifyDataSetChanged();
            adapter2.notifyDataSetChanged();
        }


    }


    private void copyProperties(Slave productBefore, Slave slaveLast) {
        productBefore.setId(slaveLast.getId());
        productBefore.setAsos_id(slaveLast.getAsos_id());
        productBefore.setUser_id(slaveLast.getUser_id());
        productBefore.setTovar_id(slaveLast.getTovar_id());
        productBefore.setTovar_nom(slaveLast.getTovar_nom());
        productBefore.setKol(slaveLast.getKol());
        productBefore.setKol_in(slaveLast.getKol_in());
        productBefore.setKol_ost(slaveLast.getKol_ost());
        productBefore.setKol_in_ost(slaveLast.getKol_in_ost());
        productBefore.setSotish(slaveLast.getSotish());
        productBefore.setSotish_in(slaveLast.getSotish());
        productBefore.setSena(slaveLast.getSena());
        productBefore.setSena_in(slaveLast.getSena_in());
    }

    public Integer tryParse(Object obj) {
        Integer retVal;
        try {
            retVal = Integer.parseInt((String) obj);
        } catch (NumberFormatException nfe) {
            retVal = 0; // or null if that is your preference
        }
        return retVal;
    }


    private class AddProduct extends AsyncTask<Void, Void, Void> {
        String urlRequest = "http://" + ip + ":8080/application/json/asosslave2/" + asosId + "/" + thisuUser.getId();
        Integer i = 0;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(IncomingAdd.this);
            progressDialog.setMessage("Маьлумот сақланяпти");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            HttpHandler httpHandler = new HttpHandler();
            i = httpHandler.makeServiceAddSlave(urlRequest, selectSlave);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
            if (i != 0) {
                selectSlave.setPutId(i);
            }
        }

    }


    private class GetProducts extends AsyncTask<Void, Void, Void> {
        //        http://localhost:8080/application/json/clientid=4/4/products
        private String urlProducts = "http://" + ip + ":8080/application/json/getproduct/" + thisuUser.getClient_id();
        private String urlAddProducts = "http://" + ip + ":8080/application/json/getKirimSlave/" + asosId;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(IncomingAdd.this);
            progressDialog.setMessage("Kirim tovarlar yuklanmoqda");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            HttpHandler httpHandler = new HttpHandler();

            List<STovar> list = AppDatabase.getInstance(IncomingAdd.this).sTovarDao().getAll();

            if (list.isEmpty()) {
                String jsonStr = httpHandler.makeServiceCall(urlProducts);
                if (jsonStr != null) {
                    try {
                        JSONArray jsonArray = new JSONArray(jsonStr);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            STovar tovar = new STovar();
                            JSONObject object = jsonArray.getJSONObject(i);
                            Log.v("incoming Jsonstr", object.toString());
                            if (object.get("id").toString().equals("null")) {
                                tovar.setId(0);
                            } else
                                tovar.setId(object.getInt("id"));
                            if (object.get("nom").toString().equals("null")) {
                                tovar.setNom("");
                            } else
                                tovar.setNom(object.getString("nom"));
                            if (object.get("nom_ru").toString().equals("null")) {
                                tovar.setNom_ru("");
                            } else
                                tovar.setNom_ru(object.getString("nom_ru"));
                            if (object.get("nom_sh").toString().equals("null")) {
                                tovar.setNom_sh("");
                            } else
                                tovar.setNom_sh(object.getString("nom_sh"));
                            if (object.get("shtrix").toString().equals("null")) {
                                tovar.setShtrix("");
                            } else
                                tovar.setShtrix(object.getString("shtrix"));
                            if (object.get("shtrix_in").toString().equals("null")) {
                                tovar.setShtrix_in("");
                            } else
                                tovar.setShtrix_in(object.getString("shtrix_in"));

                            if (object.get("tz_id").toString().equals("null")) {
                                tovar.setTz_id(0);
                            } else {
                                tovar.setTz_id(object.getInt("tz_id"));
                            }
                            if (object.get("kg").toString().equals("null")) {
                                tovar.setKg(0);
                            } else
                                tovar.setKg(object.getInt("kg"));
                            if (object.get("shtrix_full").toString().equals("null")) {
                                tovar.setShtrix_full("");
                            } else
                                tovar.setShtrix_full(object.getString("shtrix_full"));
                            if (object.get("shtrix1").toString().equals("null")) {
                                tovar.setShtrix1("");
                            } else
                                tovar.setShtrix1(object.getString("shtrix1"));
                            if (object.get("shtrix2").toString().equals("null")) {
                                tovar.setShtrix2("");
                            } else
                                tovar.setShtrix2(object.getString("shtrix2"));
                            if (object.get("kat").toString().equals("null")) {
                                tovar.setKat(0);
                            } else
                                tovar.setKat(object.getInt("kat"));
                            if (object.get("brend").toString().equals("null")) {
                                tovar.setBrend(0);
                            } else
                                tovar.setBrend(object.getInt("brend"));

                            if (object.get("papka").toString().equals("null")) {
                                tovar.setPapka(0);
                            } else
                                tovar.setPapka(object.getInt("papka"));
                            if (object.get("qr").toString().equals("null")) {
                                tovar.setQr("");
                            } else
                                tovar.setQr(object.getString("qr"));
                            if (object.get("shtrixkod").toString().equals("null")) {
                                tovar.setShtrixkod(0);
                            } else
                                tovar.setShtrixkod(object.getInt("shtrixkod"));
                            if (object.get("qrkod").toString().equals("null")) {
                                tovar.setQrkod("");
                            } else
                                tovar.setQrkod(object.getString("qrkod"));
                            if (object.get("izm_id").toString().equals("null")) {
                                tovar.setIzm_id(0);
                            } else
                                tovar.setIzm_id(object.getInt("izm_id"));
                            if (object.get("del_flag").toString().equals("null")) {
                                tovar.setDel_flag(0);
                            }
                            tovar.setDel_flag(object.getInt("del_flag"));
                            if (object.get("client_id").toString().equals("null")) {
                                tovar.setClient_id(0);
                            } else
                                tovar.setClient_id(object.getInt("client_id"));
                            if (object.get("sotish").toString().equals("null")) {
                                tovar.setSotish(0.00);
                            } else
                                tovar.setSotish(object.getDouble("sotish"));
                            if (object.get("ulg1").toString().equals("null")) {
                                tovar.setUlg1(0.0);
                            } else
                                tovar.setUlg1(object.getDouble("ulg1"));
                            if (object.get("ulg2").toString().equals("null")) {
                                tovar.setUlg2(0.0);
                            } else
                                tovar.setUlg2(object.getDouble("ulg2"));
                            if (object.get("ulg1_pl").toString().equals("null")) {
                                tovar.setUlg1_pl(0.0);
                            } else
                                tovar.setUlg1_pl(object.getDouble("ulg1_pl"));
                            if (object.get("ulg2_pl").toString().equals("null")) {
                                tovar.setUlg1_pl(0.0);
                            } else
                                tovar.setUlg2_pl(object.getDouble("ulg2_pl"));
                            if (object.get("bank").toString().equals("null")) {
                                tovar.setBank(0.0);
                            } else
                                tovar.setBank(object.getDouble("bank"));
                            if (object.get("sena").toString().equals("null")) {
                                tovar.setSena(0.0);
                            } else
                                tovar.setSena(object.getDouble("sena"));
                            if (object.get("kol_in").toString().equals("kol_in")) {
                                tovar.setKol_in(0);
                            } else
                                tovar.setKol_in(object.getInt("kol_in"));
                            if (object.get("sena_d").toString().equals("null")) {
                                tovar.setSena_d(0.0);
                            } else
                                tovar.setSena_d(object.getDouble("sena_d"));
                            if (object.get("sena_in_d").toString().equals("null")) {
                                tovar.setSena_in_d(0.0);
                            } else
                                tovar.setSena_in_d(object.getDouble("sena_in_d"));
                            list.add(tovar);

                        }

                        AppDatabase appDatabase = AppDatabase.getInstance(IncomingAdd.this);
                        appDatabase.sTovarDao().insertAll(list);
                    } catch (final JSONException e) {
                        Log.v("MyTag3", e.getMessage());
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(IncomingAdd.this, "Хатолик юз берди 1", Toast.LENGTH_LONG).show();
                            }
                        });
                    }

                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(IncomingAdd.this, "Сервер билан муамо бор 1", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
            String jsonStr2 = httpHandler.makeServiceCall(urlAddProducts);
            if (jsonStr2 != null) {
                try {
                    JSONArray jsonArray2 = new JSONArray(jsonStr2);
                    for (int i = 0; i < jsonArray2.length(); i++) {
                        Slave item = new Slave();
                        JSONObject object2 = jsonArray2.getJSONObject(i);
                        item.setId(object2.getInt("id"));
                        item.setAsos_id(object2.getInt("asos_id"));
                        item.setTovar_id(object2.getInt("tovar_id"));
                        item.setTovar_nom(object2.getString("tovar_nom"));
                        item.setKol(object2.getInt("kol"));
                        item.setKol_in((Integer) object2.getInt("kol_in"));
                        item.setKol_ost(object2.getInt("kol_ost"));
                        item.setKol_in_ost(object2.getInt("kol_in_ost"));
                        Slave pr = new Slave();
                        copyProperties(pr, item);
                        list2.add(pr);
                    }
                } catch (JSONException e) {
                    e.getMessage();
                }

            } else {
                Log.v("MyTag5", "serverdan galmadi");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //   Toast.makeText(IncomingAdd.this, "Сервер билан муамо бор 2", Toast.LENGTH_LONG).show();
                    }
                });
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
            adapter2 = new ItemSlaveAdapter(IncomingAdd.this, R.layout.list_item, list2, ip, asosId, new IncomingAddListener() {
                @Override
                public void itemSeriesClick(ArrayList<Slave> products, Integer position) {
                    Intent intent = new Intent(getApplicationContext(), IncomingWork.class);
                    intent.putExtra("ip", ip);
                    intent.putExtra("user", thisuUser);
                    intent.putExtra("sTovar", tovar);
                    intent.putExtra("slaveId", asosId); // slaveId
                    intent.putExtra("name", products.get(position).getTovar_nom());
                    intent.putExtra("soni", products.get(position).getKol());
                    intent.putExtra("id", products.get(position).getId());
                    startActivity(intent);
                }

                @Override
                public void itemSlaveClick(ArrayList<Slave> items, int position) {

                }

            });
            listView2.setAdapter(adapter2);
        }
    }

    private class GetSTovar extends AsyncTask<Void, Void, Void> {
        private String urlProducts = "http://" + ip + ":8080/application/json/getproduct/" + thisuUser.getClient_id();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            HttpHandler httpHandler = new HttpHandler();

            List<STovar> list = new ArrayList<>();

            String jsonStr = httpHandler.makeServiceCall(urlProducts);
            if (jsonStr != null) {
                try {
                    JSONArray jsonArray = new JSONArray(jsonStr);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        STovar tovar = new STovar();
                        JSONObject object = jsonArray.getJSONObject(i);
                        Log.v("incoming Jsonstr", object.toString());
                        if (object.get("id").toString().equals("null")) {
                            tovar.setId(0);
                        } else
                            tovar.setId(object.getInt("id"));
                        if (object.get("nom").toString().equals("null")) {
                            tovar.setNom("");
                        } else
                            tovar.setNom(object.getString("nom"));
                        if (object.get("nom_ru").toString().equals("null")) {
                            tovar.setNom_ru("");
                        } else
                            tovar.setNom_ru(object.getString("nom_ru"));
                        if (object.get("nom_sh").toString().equals("null")) {
                            tovar.setNom_sh("");
                        } else
                            tovar.setNom_sh(object.getString("nom_sh"));
                        if (object.get("shtrix").toString().equals("null")) {
                            tovar.setShtrix("");
                        } else
                            tovar.setShtrix(object.getString("shtrix"));
                        if (object.get("shtrix_in").toString().equals("null")) {
                            tovar.setShtrix_in("");
                        } else
                            tovar.setShtrix_in(object.getString("shtrix_in"));

                        if (object.get("tz_id").toString().equals("null")) {
                            tovar.setTz_id(0);
                        } else {
                            tovar.setTz_id(object.getInt("tz_id"));
                        }
                        if (object.get("kg").toString().equals("null")) {
                            tovar.setKg(0);
                        } else
                            tovar.setKg(object.getInt("kg"));
                        if (object.get("shtrix_full").toString().equals("null")) {
                            tovar.setShtrix_full("");
                        } else
                            tovar.setShtrix_full(object.getString("shtrix_full"));
                        if (object.get("shtrix1").toString().equals("null")) {
                            tovar.setShtrix1("");
                        } else
                            tovar.setShtrix1(object.getString("shtrix1"));
                        if (object.get("shtrix2").toString().equals("null")) {
                            tovar.setShtrix2("");
                        } else
                            tovar.setShtrix2(object.getString("shtrix2"));
                        if (object.get("kat").toString().equals("null")) {
                            tovar.setKat(0);
                        } else
                            tovar.setKat(object.getInt("kat"));
                        if (object.get("brend").toString().equals("null")) {
                            tovar.setBrend(0);
                        } else
                            tovar.setBrend(object.getInt("brend"));

                        if (object.get("papka").toString().equals("null")) {
                            tovar.setPapka(0);
                        } else
                            tovar.setPapka(object.getInt("papka"));
                        if (object.get("qr").toString().equals("null")) {
                            tovar.setQr("");
                        } else
                            tovar.setQr(object.getString("qr"));
                        if (object.get("shtrixkod").toString().equals("null")) {
                            tovar.setShtrixkod(0);
                        } else
                            tovar.setShtrixkod(object.getInt("shtrixkod"));
                        if (object.get("qrkod").toString().equals("null")) {
                            tovar.setQrkod("");
                        } else
                            tovar.setQrkod(object.getString("qrkod"));
                        if (object.get("izm_id").toString().equals("null")) {
                            tovar.setIzm_id(0);
                        } else
                            tovar.setIzm_id(object.getInt("izm_id"));
                        if (object.get("del_flag").toString().equals("null")) {
                            tovar.setDel_flag(0);
                        }
                        tovar.setDel_flag(object.getInt("del_flag"));
                        if (object.get("client_id").toString().equals("null")) {
                            tovar.setClient_id(0);
                        } else
                            tovar.setClient_id(object.getInt("client_id"));
                        if (object.get("sotish").toString().equals("null")) {
                            tovar.setSotish(0.00);
                        } else
                            tovar.setSotish(object.getDouble("sotish"));
                        if (object.get("ulg1").toString().equals("null")) {
                            tovar.setUlg1(0.0);
                        } else
                            tovar.setUlg1(object.getDouble("ulg1"));
                        if (object.get("ulg2").toString().equals("null")) {
                            tovar.setUlg2(0.0);
                        } else
                            tovar.setUlg2(object.getDouble("ulg2"));
                        if (object.get("ulg1_pl").toString().equals("null")) {
                            tovar.setUlg1_pl(0.0);
                        } else
                            tovar.setUlg1_pl(object.getDouble("ulg1_pl"));
                        if (object.get("ulg2_pl").toString().equals("null")) {
                            tovar.setUlg1_pl(0.0);
                        } else
                            tovar.setUlg2_pl(object.getDouble("ulg2_pl"));
                        if (object.get("bank").toString().equals("null")) {
                            tovar.setBank(0.0);
                        } else
                            tovar.setBank(object.getDouble("bank"));
                        if (object.get("sena").toString().equals("null")) {
                            tovar.setSena(0.0);
                        } else
                            tovar.setSena(object.getDouble("sena"));
                        if (object.get("kol_in").toString().equals("kol_in")) {
                            tovar.setKol_in(0);
                        } else
                            tovar.setKol_in(object.getInt("kol_in"));
                        if (object.get("sena_d").toString().equals("null")) {
                            tovar.setSena_d(0.0);
                        } else
                            tovar.setSena_d(object.getDouble("sena_d"));
                        if (object.get("sena_in_d").toString().equals("null")) {
                            tovar.setSena_in_d(0.0);
                        } else
                            tovar.setSena_in_d(object.getDouble("sena_in_d"));
                        list.add(tovar);

                    }

                    AppDatabase appDatabase = AppDatabase.getInstance(IncomingAdd.this);
                    appDatabase.sTovarDao().deleteAll();
                    appDatabase.sTovarDao().insertAll(list);
                } catch (final JSONException e) {
                    Log.v("MyTag3", e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(IncomingAdd.this, "Хатолик юз берди 1", Toast.LENGTH_LONG).show();
                        }
                    });
                }

            } else {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(IncomingAdd.this, "Сервер билан муамо бор 1", Toast.LENGTH_LONG).show();
                    }
                });
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            swrl.setRefreshing(false);

            adapter2 = new ItemSlaveAdapter(IncomingAdd.this, R.layout.list_item, list2, ip, asosId, new IncomingAddListener() {
                @Override
                public void itemSeriesClick(ArrayList<Slave> products, Integer position) {
                    Intent intent = new Intent(getApplicationContext(), IncomingWork.class);
                    intent.putExtra("ip", ip);
                    intent.putExtra("user", thisuUser);
                    intent.putExtra("sTovar", tovar);
                    intent.putExtra("slaveId", asosId); // slaveId
                    intent.putExtra("name", products.get(position).getTovar_nom());
                    intent.putExtra("soni", products.get(position).getKol());
                    intent.putExtra("id", products.get(position).getId());
                    startActivity(intent);
                }

                @Override
                public void itemSlaveClick(ArrayList<Slave> items, int position) {

                }

            });
            listView2.setAdapter(adapter2);
        }
    }

    private class PutSlave extends AsyncTask<Void, Void, Void> {
        private String urlPutProducts = "http://" + ip + ":8080/application/json/saveinslave/";
//        http://localhost:8080/application/json/


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(IncomingAdd.this);
            progressDialog.setMessage("Omborga ma`lumot qo`shilmoqda");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            HttpHandler httpHandler = new HttpHandler();
            httpHandler.putSlave(urlPutProducts, selectSlave);
            return null;
        }

        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
//            sum -= selectProductSum;
//            selectProductSum = (selectSlave.getSena() * selectSlave.getKol() + selectSlave.getSena() * selectSlave.getKol_in());
//            sum += selectProductSum;

            listView2.setAdapter(adapter2);
            //addList(selectProduct);
            adapter2.notifyDataSetChanged();

            selectedSlave = 0;

            // sumPrice.setText("Умуммий сумма: " + sum + " Сўм");
            //  intent.putExtra("sumprice", sumPrice.getText().toString());
            //  setProduct(selectProduct);

        }
    }
}
