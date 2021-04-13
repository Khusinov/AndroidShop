package com.example.shop.ui;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shop.HttpHandler;
import com.example.shop.R;
import com.example.shop.adapter.GetListAdapter;
import com.example.shop.model.GetList;
import com.example.shop.model.Product;
import com.example.shop.model.STovar;
import com.example.shop.model.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import me.sudar.zxingorient.ZxingOrient;
import me.sudar.zxingorient.ZxingOrientResult;

public class ProductAdd extends AppCompatActivity {

    Intent intent;
    Button saveProduct;
    Button editProduct;
    Button back;
    EditText name;
    EditText name_short;
    EditText in_count;
    EditText barcode1;
    EditText barcode2;
    EditText barcode3;
    EditText type1;
    EditText type2;
    EditText type3;
    EditText type4;
    EditText type5;
    EditText type6;
    EditText for_count;
    ListView listView;
    private ProgressDialog progressDialog;
    private MutableLiveData<ArrayList<GetList>> liveData;
  //  AutoCompleteTextView incomingdiller;
    EditText for_incount;
    EditText incomingprice;
    Spinner spinner;
    ImageView barcodescan;
    ArrayAdapter adapterdillers;
    STovar sTovar;
    List<String> dillerList;
    User thisUser;
    String ip="192.168.1.100";
    Integer barcode=0;
    Integer update=0;
    Integer series = 0;
    Integer x;
    Integer isEdit = 0;
    Integer kat;
    Integer brend;
    GetListAdapter adapter; // ozgartrsh garak
    Integer slaveId = 0; // intentdan alish garak
    private ArrayList<GetList> list; /// modelni ozgartirish garak
    private GetList getList;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_add);
        intent=getIntent();
        back=findViewById(R.id.product_add_back);
        saveProduct=findViewById(R.id.product_add_save_product_for_add);
        editProduct=findViewById(R.id.product_change_product_for_add);
        barcodescan=findViewById(R.id.product_add_barcodescan);
        name=findViewById(R.id.product_add_name);
        name_short=findViewById(R.id.product_add_name_short);
        in_count=findViewById(R.id.product_add_in_count);
        barcode1=findViewById(R.id.product_add_barcode1);
        barcode2=findViewById(R.id.product_add_barcode2);
        barcode3=findViewById(R.id.product_add_barcode3);
        type1=findViewById(R.id.product_add_type1);
        type2=findViewById(R.id.product_add_type2);
        type3=findViewById(R.id.product_add_type3);
        type4=findViewById(R.id.product_add_type4);
        type5=findViewById(R.id.product_add_type5);
        type6=findViewById(R.id.product_add_type6);
        spinner = findViewById(R.id.spinner);
        listView = findViewById(R.id.slaveList);
        for_count=findViewById(R.id.product_add_for_count);
        for_incount=findViewById(R.id.product_add_for_incount);
        incomingprice=findViewById(R.id.product_add_incomingprice);
        ip=intent.getStringExtra("ip");
        thisUser=(User) intent.getSerializableExtra("user");
        sTovar=(STovar) intent.getSerializableExtra("stovar");
        kat = intent.getIntExtra("tovarKol",0);
        brend = intent.getIntExtra("brend",0);

        Log.d("brend",brend.toString());

        isEdit = intent.getIntExtra("edit",0);

        liveData = new MutableLiveData<>();
        list = new ArrayList<>();

        dillerList = new ArrayList<>();
        dillerList.add("neobizatilni");
        dillerList.add("50/50");
        dillerList.add("obizatilni");

        if(sTovar != null){
            copyPraporty(sTovar);
          //  listView.setAdapter();
            new  GetListNew().execute();

                liveData.observe(this, new Observer<ArrayList<GetList>>() {
                    @Override
                    public void onChanged(@Nullable ArrayList<GetList> seriesModels) {
                        adapter = new GetListAdapter(ProductAdd.this,R.layout.get_list_item, seriesModels);
                        listView.setAdapter(adapter);
                    }
                });

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Intent intent = new Intent(ProductAdd.this,IncomingWork.class);
                        GetList list1 = list.get(i);
                        setDownIntent(intent);
                        intent.putExtra("name",name.getText().toString());
                        intent.putExtra("id",list1.getId());
                        intent.putExtra("kol",list1.getKol());
                        intent.putExtra("kolin",list1.getKolIn());
                        intent.putExtra("kolost",list1.getKolOst());
                        intent.putExtra("kolinost",list1.getKolInOst());
                        startActivity(intent);
                    }
                });
        }
        else {
            sTovar=new STovar();
        }
        barcodescan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new ZxingOrient(ProductAdd.this).setIcon(R.mipmap.ic_launcher).initiateScan();
            }
        });
        Log.d("iseds",isEdit.toString());
        if (isEdit != 0){
            saveProduct.setVisibility(View.GONE);
            editProduct.setVisibility(View.VISIBLE);
        }

        editProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (name.getText().toString().isEmpty()){
                    name.setError("kiriting");
                }
                else if (!for_incount.getText().toString().isEmpty() && for_count.getText().toString().isEmpty()){
                    for_count.setError("kiriting");
                }else {
                    copyPraporty();
                    new EditProduct().execute();
                }

            }
        });

        saveProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (name.getText().toString().isEmpty()){
                    name.setError("kiriting");
                }
                else if (!for_incount.getText().toString().isEmpty() && for_count.getText().toString().isEmpty()){
                        for_count.setError("kiriting");
                }else {
                    copyPraporty();
                    new AddNewProduct().execute();
                }

            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ProductAdd.this, ProductsList.class);
                setDownIntent(intent);
                startActivity(intent);
                finish();
            }
        });

        adapterdillers = new ArrayAdapter(this,android.R.layout.simple_dropdown_item_1line, dillerList);
        spinner.setAdapter(adapterdillers);
        if (sTovar != null){
            spinner.setSelection(sTovar.getSeriya());
        }

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0){
                    series = 0;
                }
                else if (i == 1){
                    series = 1;
                }
                else{
                    series = 2;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    private void copyPraporty() {

        sTovar.setNom(name.getText().toString());
        sTovar.setNom_sh(name_short.getText().toString());
        Integer kol_in=tryParse(in_count.getText().toString());
        if (kol_in==0)
            kol_in++;

        sTovar.setKol_in(kol_in);

        sTovar.setShtrix(barcode1.getText().toString());
        sTovar.setShtrix1(barcode2.getText().toString());
        sTovar.setShtrix2(barcode3.getText().toString());
        sTovar.setSotish(tryParseDouble(type1.getText().toString() ) );
        sTovar.setUlg1(tryParseDouble(type2.getText().toString()) );
        sTovar.setUlg2(tryParseDouble(type3.getText().toString()) );
        sTovar.setUlg1_pl(tryParseDouble(type4.getText().toString()) );
        sTovar.setUlg2_pl(tryParseDouble(type5.getText().toString()) );
        sTovar.setBank(tryParseDouble(type6.getText().toString()) );
        sTovar.setShtrixkod(1);
        sTovar.setSena(tryParseDouble(incomingprice.getText().toString()));
        sTovar.setSeriya(series);
        if (for_count.getText().toString().isEmpty()){
            sTovar.setTkol(0);
        }else {
            sTovar.setTkol(Integer.parseInt(for_count.getText().toString()));
        }
        if (for_incount.getText().toString().isEmpty()){
            sTovar.setTkol_in(0);
        }else {
            sTovar.setTkol_in(Integer.parseInt(for_incount.getText().toString()));
        }


    }


    private void copyPraporty(STovar tovar) {
        update=1;
        name.setText(tovar.getNom());
        name_short.setText(tovar.getNom_sh());
        in_count.setText(tovar.getKol_in().toString());
      //  Log.d("Logdddd" , tovar.getShtrix());
        if(!tovar.getShtrix().equals("null")){
            barcode=1;
        }
        if(!tovar.getShtrix1().equals("null")){
            barcode=2;
        }
        if(!tovar.getShtrix2().equals("null")){
            barcode=3;
        }

        if (tovar.getShtrix() == null)
        {
            barcode1.setText("");
        }else {
            barcode1.setText(tovar.getShtrix());
        }
        if (tovar.getShtrix1().equals("null")){
            barcode2.setText("");
        }else {
            barcode2.setText(tovar.getShtrix1());
        }
        if (tovar.getShtrix2().equals("null")){
           barcode3.setText("");
        }else {
            barcode3.setText(tovar.getShtrix2());
        }

        type1.setText(tovar.getSotish().toString());
        type2.setText(tovar.getUlg1().toString());
        type3.setText(tovar.getUlg2().toString());
        type4.setText(tovar.getUlg1_pl().toString());
        type5.setText(tovar.getUlg2_pl().toString());
        type6.setText(tovar.getBank().toString() );
        incomingprice.setText(tovar.getSena().toString());
        spinner.setSelection(tovar.getSeriya());

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent){

        ZxingOrientResult scanResult =
                ZxingOrient.parseActivityResult(requestCode, resultCode, intent);

        if (scanResult != null) {
            // handle the result
            CharSequence c=scanResult.getContents();
            setText(c);
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

        AlertDialog.Builder builder = new AlertDialog.Builder(ProductAdd.this);
        builder.setMessage("Дастурдан чикишни истайсизми?").setPositiveButton("Ха", dialogClickListener)
                .setNegativeButton("Йўқ", dialogClickListener).show();
    }

    public void setText(CharSequence sequence){
        if(barcode==0){
            barcode1.setText(sequence, TextView.BufferType.EDITABLE);
            barcode=1;
        }
        else if(barcode==1){
            barcode2.setText(sequence, TextView.BufferType.EDITABLE);
            barcode=2;
        }
        else  if(barcode==2){
            barcode3.setText(sequence, TextView.BufferType.EDITABLE);
            barcode=3;
        }
        else {
            Toast.makeText(this,"Барча штрих қодлар банд",Toast.LENGTH_LONG).show();
        }
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
    public Double tryParseDouble(Object obj) {
        Double retVal;
        try {
            retVal = Double.parseDouble((String) obj);
        } catch (NumberFormatException nfe) {
            retVal = 0.0; // or null if that is your preference
        }
        return retVal;
    }
    public void setDownIntent(Intent nextIntent) {
        nextIntent.putExtra("user",intent.getSerializableExtra("user"));
        nextIntent.putExtra("ip",intent.getStringExtra("ip"));
        nextIntent.putExtra("asosId",intent.getIntExtra("asosId",0));
        nextIntent.putExtra("type",intent.getIntExtra("type",0));
        nextIntent.putExtra("sumprice",intent.getStringExtra("sumprice"));
        nextIntent.putExtra("stovar",intent.getSerializableExtra("stovar"));
        nextIntent.putExtra("name",name.getText().toString());
        if (!for_count.getText().toString().isEmpty()){
            nextIntent.putExtra("slave_id",x);
            nextIntent.putExtra("soni",for_count.getText().toString());
        }
    }

    private  class EditProduct extends AsyncTask<Void,Void,Void>{

        ProgressDialog progressDialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog=new ProgressDialog(ProductAdd.this);
            progressDialog.setMessage("Сақлаyмоқда !!!");
            progressDialog.setCancelable(false);
            progressDialog.show();

        }

        @Override
        protected Void doInBackground(Void... voids) {
            HttpHandler httpHandler=new HttpHandler();
            String reqUrl="http://"+ip+":8080/application/json/addproduct";
            Log.d("latss",kat.toString());
            x = httpHandler.makeServiceChangeProducts(reqUrl,sTovar,thisUser,kat,brend);
            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if(progressDialog.isShowing())
                progressDialog.dismiss();
            if (series > 0 && !for_count.getText().toString().isEmpty()){
                Intent nextIntent = new Intent(ProductAdd.this, IncomingWork.class);
                setDownIntent(nextIntent);
                startActivity(nextIntent);
            }else {
                Intent nextIntent = new Intent(ProductAdd.this, ProductsList.class);
                setDownIntent(nextIntent);
                startActivity(nextIntent);
                finish();
            }

        }
    }

    private  class AddNewProduct extends AsyncTask<Void,Void,Void>{

        ProgressDialog progressDialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog=new ProgressDialog(ProductAdd.this);
            progressDialog.setMessage("Сақлаyмоқда !!!");
            progressDialog.setCancelable(false);
            progressDialog.show();

        }

        @Override
        protected Void doInBackground(Void... voids) {
            HttpHandler httpHandler=new HttpHandler();
            String reqUrl="http://"+ip+":8080/application/json/addproduct";
            x = httpHandler.makeServiceAddNewProducts(reqUrl,sTovar,thisUser);
            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if(progressDialog.isShowing())
                progressDialog.dismiss();
            if (series > 0 && !for_count.getText().toString().isEmpty()){
                Intent nextIntent = new Intent(ProductAdd.this, IncomingWork.class);
                setDownIntent(nextIntent);
                startActivity(nextIntent);
            }else {
                Intent nextIntent = new Intent(ProductAdd.this, ProductsList.class);
                setDownIntent(nextIntent);
                startActivity(nextIntent);
                finish();
            }

        }
    }

    private class GetListNew extends AsyncTask<Void, Void, Void> {
        //        http://localhost:8080/application/json//4/products
        private String urlProducts="http://"+ip+":8080/application/json/getAsosSlave/"+sTovar.getId();
        // ishladi))
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog=new ProgressDialog(ProductAdd.this);
            progressDialog.setMessage("Малумот юкланяпти");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            HttpHandler httpHandler=new HttpHandler();
            String jsonStr=httpHandler.makeServiceCall(urlProducts);
            Log.d("urlsss",urlProducts);
            Log.d("ssssa",jsonStr);
            if(jsonStr!=null){
                try {
                    list.clear();
                    JSONArray jsonArray2=new JSONArray(jsonStr);
                    for (int i=0;i<jsonArray2.length();i++){
                        GetList getList = new GetList();
                        JSONObject object2 = jsonArray2.getJSONObject(i);

                        getList.setId(object2.getInt("id"));
                   //     Log.d("zzzz0",String.valueOf(object2.getInt("kolIn")));
                        if (String.valueOf(object2.getInt("kol")).equals("null")){
                            getList.setKol(null);
                        }else {
                            getList.setKol(object2.getInt("kol"));
                        }
                    /*    if (String.valueOf(object2.getInt("kolIn")).equals("null")){
                            getList.setKolIn(null);
                        }else {
                            getList.setKolIn(object2.getInt("kolIn"));
                        }
                        if (String.valueOf(object2.getInt("kolOst")).equals("null")){
                            getList.setKolOst(null);
                        }else {
                            getList.setKolOst(object2.getInt("kolOst"));
                        }
                        if (String.valueOf(object2.getInt("kolInOst")).equals("null")){
                            getList.setKolOst(null);
                        }else {
                            getList.setKolInOst(object2.getInt("kolInOst"));
                        }*/

                        list.add(getList);
                        if (!list.isEmpty()){
                            liveData.postValue(list);
                        }
                    }
                } catch (JSONException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(ProductAdd.this,"serverdan galmadi",Toast.LENGTH_LONG).show();
                        }
                    });

                }

            }
            else{
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(ProductAdd.this,"Сервер билан муамо бор",Toast.LENGTH_LONG).show();
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


        }
    }
}
