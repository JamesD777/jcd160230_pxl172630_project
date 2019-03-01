package com.example.jcd160230_pxl172630_project;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;
import java.util.Calendar;


public class SelectDateFragment extends DialogFragment{
    DatePickerDialog picker;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar calendar = Calendar.getInstance();
        int yy = calendar.get(Calendar.YEAR);
        int mm = calendar.get(Calendar.MONTH);
        int dd = calendar.get(Calendar.DAY_OF_MONTH);
        picker = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                ((Main2Activity) getActivity()).setTextDOB(monthOfYear+"/"+dayOfMonth+"/"+year, getTag());
            }
        }, yy, mm, dd);
        picker.show();
        return picker;
    }

}
