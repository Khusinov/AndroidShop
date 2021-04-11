package com.example.shop;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;

import java.util.ArrayList;

public class IncomingWork extends AppCompatActivity {

    private Intent intent;
    private ImageView barcodescan;
    private ImageView add;

    private ListView listView;
    private SearchView searchView;
    private ArrayList<STovar> list;
    private STovarAdapter adapter;
    private ProgressDialog progressDialog;
    private String ip;
    private User thisuUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incoming_work);
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

    }

}
