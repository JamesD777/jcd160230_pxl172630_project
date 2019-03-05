/******************************************************************************
 * This is an application written for 4301.002, to display a contact list in an
 * android app that is modifiable by the user. It has a list that opens up a
 * specific contact's info when you click their name. This contact information
 * can be modified by the user and is saved when the save button is clicked.
 *
 * Written by James Dunlap(jcd160230) and Perry Lee (pxl172630) at The University
 * of Texas at Dallas starting March 4, 2019, for an Android development course.
 ******************************************************************************/

package com.example.jcd160230_pxl172630_project;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;
import java.util.Calendar;


public class SelectDateFragment extends DialogFragment{
    DatePickerDialog picker;
    /****************************************************************************
     * Create the third activity, a calendar date picker which lets you select a date
     * Author: James Dunlap
     * ****************************************************************************/
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
