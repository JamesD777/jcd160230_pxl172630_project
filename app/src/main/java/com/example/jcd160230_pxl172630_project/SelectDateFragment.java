package com.example.jcd160230_pxl172630_project;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;
import java.util.Calendar;

/******************************************************************************
 * This is a Pong game, called Paddle Ball, that lets you play with a tennis
 * ball in a court that is the screen width wide and high.  One or two people
 * can play, and the speed of the ball can be adjusted with a slider.
 *
 * Written by John Cole at The University of Texas at Dallas starting June 13,
 * 2013, for a summer workshop in Android development.
 ******************************************************************************/

public class SelectDateFragment extends DialogFragment{
    DatePickerDialog picker;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //show a calendar
        final Calendar calendar = Calendar.getInstance();
        //get the current year, month, and day
        int yy = calendar.get(Calendar.YEAR);
        int mm = calendar.get(Calendar.MONTH);
        int dd = calendar.get(Calendar.DAY_OF_MONTH);
        //show the date picker and send the updated date to the main2activity
        picker = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                ((Main2Activity) getActivity()).setText(monthOfYear+"/"+dayOfMonth+"/"+year, getTag());
            }
        }, yy, mm, dd);
        picker.show();
        return picker;
    }

}
