package com.example.jcd160230_pxl172630_project;




import android.app.FragmentTransaction;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Main2Activity extends AppCompatActivity {
    String dobDate = "DOB";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);


        getSupportFragmentManager().beginTransaction().add(R.id.frameLayout, new clickDate(), "DOB").commit();
        getSupportFragmentManager().beginTransaction().add(R.id.frameLayout2, new clickDate(), "DOC").commit();
    }

    public void viewDate(View view) {
        //getSupportFragmentManager().beginTransaction().add(R.id.container, new clickDate()).commit();

    }
    public void openDatePicker(View view) {
        DialogFragment newFragment = new SelectDateFragment();
        TextView textView = (TextView)view.findViewById(R.id.textView);

        if(textView.getText().toString().trim().equals(dobDate)){
            newFragment.show(getSupportFragmentManager(), "DOB ");
        }
        else{
            newFragment.show(getSupportFragmentManager(), "DOC ");
        }
    }

    public void setTextDOB(String date, String tag){
        clickDate dob = (clickDate) getSupportFragmentManager().findFragmentByTag(tag.trim());
        dob.setText(date);
        if(tag.trim().equals("DOB")){
            this.dobDate = date;
        }
    }
}
