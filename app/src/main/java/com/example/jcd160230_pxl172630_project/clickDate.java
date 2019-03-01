package com.example.jcd160230_pxl172630_project;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class clickDate extends Fragment {

    public clickDate() {
        // Required empty public constructor
    }
    View v;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_click_date, container, false);
        TextView textView = (TextView)v.findViewById(R.id.textView);
        textView.setText(getTag());
        return v;

    }

    public void setText(String dob){
        TextView textView = (TextView)v.findViewById(R.id.textView);
        textView.setText(dob);
    }

    public void openDatePicker(View view) {
    }
}
