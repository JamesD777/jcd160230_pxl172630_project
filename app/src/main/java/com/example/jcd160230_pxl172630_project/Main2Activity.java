package com.example.jcd160230_pxl172630_project;


import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Main2Activity extends AppCompatActivity {
static String date = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Contact selectedContact = this.getIntent().getExtras().getParcelable("sendContact");

        System.out.println(selectedContact.getFirstName() + " " + selectedContact.getPhoneNumber());
    }

    public void viewDate(View view) {
        DialogFragment newFragment = new SelectDateFragment();
        newFragment.show(getSupportFragmentManager(), "DatePicker");
    }

    public static void setText(String dob){
        date = dob;
    }
}
