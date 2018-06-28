package com.example.lenovo.movieapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class DetailActivity extends AppCompatActivity {

    //static public  boolean fs=true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        android.support.v7.widget.Toolbar my_toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.my_toolbar);
        Bundle extras=getIntent().getExtras();
        if(savedInstanceState == null){
            DetailFragment detailFragment=new DetailFragment();
            detailFragment.setArguments(extras);
            getSupportFragmentManager().beginTransaction().add(R.id.container , detailFragment).commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.menu:

              startActivity(new Intent(this,SettingsActivity.class));


        }
        return super.onOptionsItemSelected(item);


    }}

