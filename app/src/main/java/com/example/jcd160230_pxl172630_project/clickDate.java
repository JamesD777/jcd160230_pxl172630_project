package com.example.jcd160230_pxl172630_project;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/******************************************************************************
 * This is a Pong game, called Paddle Ball, that lets you play with a tennis
 * ball in a court that is the screen width wide and high.  One or two people
 * can play, and the speed of the ball can be adjusted with a slider.
 *
 * Written by John Cole at The University of Texas at Dallas starting June 13,
 * 2013, for a summer workshop in Android development.
 ******************************************************************************/

public class clickDate extends Fragment {

    public clickDate() {
        // Required empty public constructor
    }

    View v;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //set the view
        v = inflater.inflate(R.layout.fragment_click_date, container, false);
        //create a textview to hold the current date
        TextView textView = (TextView)v.findViewById(R.id.textView);
        textView.setText(getTag());
        return v;

    }

    public void setText(String dob){
        TextView textView = (TextView)v.findViewById(R.id.textView);
        textView.setText(dob);
    }

    public void openDatePicker(View view) {
        // Required empty onclick function that is ignored
    }

    @Override
    public void onResume() {
        super.onResume();
        ((Main2Activity) getActivity()).afterFragmentComplete();
    }
}
