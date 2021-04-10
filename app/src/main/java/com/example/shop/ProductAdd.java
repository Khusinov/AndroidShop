package com.example.shop;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import me.sudar.zxingorient.ZxingOrient;
import me.sudar.zxingorient.ZxingOrientResult;

public class ProductAdd extends AppCompatActivity {

    Intent intent;
    Button saveProduct;
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
    EditText for_incount;
    EditText incomingprice;
    ImageView barcodescan;
    STovar sTovar;
    User thisUser;
    String ip="192.168.1.100";
    Integer barcode=0;
    Integer update=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_add);
        intent=getIntent();
        back=findViewById(R.id.product_add_back);
        saveProduct=findViewById(R.id.product_add_save_product_for_add);
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
        for_count=findViewById(R.id.product_add_for_count);
        for_incount=findViewById(R.id.product_add_for_incount);
        incomingprice=findViewById(R.id.product_add_incomingprice);
        ip=intent.getStringExtra("ip");
        thisUser=(User) intent.getSerializableExtra("user");
        sTovar=(STovar) intent.getSerializableExtra("stovar");

        if(sTovar != null){
            Log.v("MyTag$",sTovar.toString());
            copyPraporty(sTovar);
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
        saveProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                copyPraporty();
                new AddNewProduct().execute();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ProductAdd.this,ProductsList.class);
                setDownIntent(intent);
                startActivity(intent);
                finish();
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
    }
    private void copyPraporty(STovar tovar) {
        update=1;
        name.setText(tovar.getNom());
        name_short.setText(tovar.getNom_sh());
        in_count.setText(tovar.getKol_in().toString());
        if(!tovar.getShtrix().equals("")){
            barcode=1;
        }
        if(!tovar.getShtrix1().equals("")){
            barcode=2;
        }
        if(!tovar.getShtrix2().equals("")){
            barcode=3;
        }
        barcode1.setText(tovar.getShtrix());
        barcode2.setText(tovar.getShtrix1());
        barcode3.setText(tovar.getShtrix2());
        type1.setText(tovar.getSotish().toString());
        type2.setText(tovar.getUlg1().toString());
        type3.setText(tovar.getUlg2().toString());
        type4.setText(tovar.getUlg1_pl().toString());
        type5.setText(tovar.getUlg2_pl().toString());
        type6.setText(tovar.getBank().toString() );
        incomingprice.setText(tovar.getSena().toString());
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
            Integer x=httpHandler.makeServiceAddNewProducts(reqUrl,sTovar,thisUser);
            Log.v("MyTag:",x+" sTovar:"+sTovar.toString());
            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if(progressDialog.isShowing())
                progressDialog.dismiss();
            Intent nextIntent = new Intent(ProductAdd.this, ProductsList.class);
            setDownIntent(nextIntent);
            startActivity(nextIntent);
            finish();
        }
    }

}
