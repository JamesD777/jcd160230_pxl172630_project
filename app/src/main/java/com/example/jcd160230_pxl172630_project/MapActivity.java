package com.example.jcd160230_pxl172630_project;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MapActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        String receivedAddress = getIntent().getStringExtra("contactAddress");
    }
}
