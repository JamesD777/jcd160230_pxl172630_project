package com.example.jcd160230_pxl172630_project;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class clickDate extends Fragment {

    public clickDate() {
        // Required empty public constructor
    }

    View v;
    /****************************************************************************
     * Create the first fragment that shows on main2activity and populate it
     * Author: James Dunlap
     * ****************************************************************************/
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //set the view
        v = inflater.inflate(R.layout.fragment_click_date, container, false);
        //create a textview to hold the current date
        TextView textView = (TextView)v.findViewById(R.id.textView);
        textView.setText(getTag());
        return v;

    }
    /****************************************************************************
     * Set the data of the textview, used from outside this fragment
     * Author: James Dunlap
     * ****************************************************************************/
    public void setText(String dob){
        TextView textView = (TextView)v.findViewById(R.id.textView);
        textView.setText(dob);
    }

    public void openDatePicker(View view) {
        // Required empty onclick function that is ignored
    }
    /****************************************************************************
     * Populate the initial data only after the fragment is created
     * Author: James Dunlap
     * ****************************************************************************/
    @Override
    public void onResume() {
        super.onResume();
        ((Main2Activity) getActivity()).afterFragmentComplete();
    }
}
