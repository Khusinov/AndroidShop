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
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import me.sudar.zxingorient.ZxingOrient;
import me.sudar.zxingorient.ZxingOrientResult;

public class ProductsList extends AppCompatActivity {
    Intent intent;
    ImageView barcodescan;
    ImageView add;

    ListView listView;
    SearchView searchView;
    ArrayList<STovar> list;
    STovarAdapter adapter;
    ProgressDialog progressDialog;
    private String ip;
    private User thisuUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Махсулотлар");
        setSupportActionBar(toolbar);
        add=findViewById(R.id.products_list_input_add);
        barcodescan=findViewById(R.id.products_list_barcodescan);
        listView=findViewById(R.id.products_list_list_view);
        searchView=findViewById(R.id.searchView);
        intent=getIntent();
        ip=intent.getStringExtra("ip");
        thisuUser=(User) intent.getSerializableExtra("user");
        list=new ArrayList<>();
        new GetProducts().execute();
        barcodescan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new ZxingOrient(ProductsList.this).setIcon(R.mipmap.ic_launcher).initiateScan();
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent nextintent=new Intent(ProductsList.this,ProductAdd.class);
                STovar tovar = null;
                intent.putExtra("stovar",tovar);
                setDownIntent(nextintent);
                startActivity(nextintent);
                finish();
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
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                STovar tovar=(STovar) view.getTag();
                if (tovar != null)
                    Log.v("MyTag",tovar.toString());
                Intent nextintent=new Intent(ProductsList.this,ProductAdd.class);
                intent.putExtra("stovar",tovar);
                setDownIntent(nextintent);
                startActivity(nextintent);
                finish();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.item1) {
            Intent intent=new Intent(ProductsList.this,TypeChangeActivity.class);
            setDownIntent(intent);
            startActivity(intent);
            finish();
        }
        if (id == R.id.item2){
            Intent intent=new Intent(ProductsList.this,MainActivity.class);
            setDownIntent(intent);
            startActivity(intent);
            finish();
        }
        if (id == R.id.item4){
            Intent intent=new Intent(ProductsList.this,IncomingProducts.class);
            setDownIntent(intent);
            startActivity(intent);
            finish();
        }


        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        ZxingOrientResult scanResult =
                ZxingOrient.parseActivityResult(requestCode, resultCode, intent);

        if (scanResult != null) {
            searchView.setQuery(scanResult.getContents(),false);
        }
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

        AlertDialog.Builder builder = new AlertDialog.Builder(ProductsList.this);
        builder.setMessage("Дастурдан чикишни истайсизми?").setPositiveButton("Ха", dialogClickListener)
                .setNegativeButton("Йўқ", dialogClickListener).show();
    }




    public void setDownIntent(Intent nextIntent) {
        nextIntent.putExtra("user",intent.getSerializableExtra("user"));
        nextIntent.putExtra("ip",intent.getStringExtra("ip"));
        nextIntent.putExtra("asosId",intent.getIntExtra("asosId",0));
        nextIntent.putExtra("type",intent.getIntExtra("type",0));
        nextIntent.putExtra("sumprice",intent.getStringExtra("sumprice"));
        nextIntent.putExtra("stovar",intent.getSerializableExtra("stovar"));
    }
    private class GetProducts extends AsyncTask<Void, Void, Void> {
        //        http://localhost:8080/application/json/clientid=4/4/products
        private String urlProducts="http://"+ip+":8080/application/json/getproduct/clientid="+thisuUser.getClientId();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog=new ProgressDialog(ProductsList.this);
            progressDialog.setMessage("Малумот юкланяпти");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            HttpHandler httpHandler=new HttpHandler();
            String jsonStr=httpHandler.makeServiceCall(urlProducts);
            if(jsonStr != null) {

                try {
                    JSONArray jsonArray = new JSONArray(jsonStr);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        STovar tovar = new STovar();
                        JSONObject object = jsonArray.getJSONObject(i);
                        Log.v("MyLog",object.toString());
                        tovar.setId(object.getInt("id"));
                        tovar.setNom(object.getString("nom"));
                        tovar.setNom_ru(object.getString("nom_ru"));
                        tovar.setNom_sh(object.getString("nom_sh"));
                        tovar.setShtrix(object.getString("shtrix"));
                        tovar.setShtrix_in(object.getString("shtrix_in"));
                        tovar.setTz_id(object.getInt("tz_id"));
                        tovar.setKg(object.getInt("kg"));
                        tovar.setShtrix_full(object.getString("shtrix_full"));
                        tovar.setShtrix1(object.getString("shtrix1"));
                        tovar.setShtrix2(object.getString("shtrix2"));
                        tovar.setKat(object.getInt("kat"));
                        tovar.setBrend(object.getInt("brend"));
                        tovar.setPapka(object.getInt("papka"));
                        tovar.setQr(object.getString("qr"));
                        tovar.setShtrixkod(object.getInt("shtrixkod"));
                        tovar.setQrkod(object.getString("qrkod"));
                        tovar.setIzm_id(object.getInt("izm_id"));
                        tovar.setDel_flag(object.getInt("del_flag"));
                        tovar.setClient_id(object.getInt("client_id"));
                        tovar.setSotish(object.getDouble("sotish"));
                        tovar.setUlg1(object.getDouble("ulg1"));
                        tovar.setUlg2(object.getDouble("ulg2"));
                        tovar.setUlg1_pl(object.getDouble("ulg1_pl"));
                        tovar.setUlg2_pl(object.getDouble("ulg2_pl"));
                        tovar.setBank(object.getDouble("bank"));
                        tovar.setSena(object.getDouble("sena"));
                        tovar.setKol_in(object.getInt("kol_in"));
                        tovar.setSena_d(object.getDouble("sena_d"));
                        tovar.setSena_in_d(object.getDouble("sena_in_d"));
                        list.add(tovar);

                    }
                } catch (final JSONException e) {
                    Log.v("MyTag2", e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(ProductsList.this, "Хатолик юз берди", Toast.LENGTH_LONG).show();
                        }
                    });
                }

            }
            else{
                Log.v("MyTag2", "serverdan galmadi");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(ProductsList.this,"Сервер билан муамо бор",Toast.LENGTH_LONG).show();
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
            adapter=new STovarAdapter(ProductsList.this,R.layout.stovar_item, list);
            listView.setAdapter(adapter);
        }
    }
}
