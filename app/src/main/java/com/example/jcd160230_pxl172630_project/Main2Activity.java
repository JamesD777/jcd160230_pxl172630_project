package com.example.jcd160230_pxl172630_project;


import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


/******************************************************************************
 * This is a Pong game, called Paddle Ball, that lets you play with a tennis
 * ball in a court that is the screen width wide and high.  One or two people
 * can play, and the speed of the ball can be adjusted with a slider.
 *
 * Written by John Cole at The University of Texas at Dallas starting June 13,
 * 2013, for a summer workshop in Android development.
 ******************************************************************************/

public class Main2Activity extends AppCompatActivity {

    String dobDate = "DOB";
    String docDate = "DOC";
    TextInputEditText edit;
    EditText et;
    Contact selectedContact;
    clickDate dob;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        //get the contact's info
        selectedContact = this.getIntent().getExtras().getParcelable("sendContact");
        //set up date fragments
        getSupportFragmentManager().beginTransaction().add(R.id.frameLayout, new clickDate(), "DOB").commit();
        getSupportFragmentManager().beginTransaction().add(R.id.frameLayout2, new clickDate(), "DOC").commit();
        //populate the contact's info into the view
        edit = (TextInputEditText) findViewById(R.id.textInputEdit);
        edit.setText(selectedContact.getFirstName());
        edit = (TextInputEditText) findViewById(R.id.textInputEdit2);
        edit.setText(selectedContact.getLastName());
        et = (EditText) findViewById(R.id.editText3);
        et.setText(selectedContact.getPhoneNumber());
    }

    public void openDatePicker(View view) {
        //create a date picker fragment
        DialogFragment newFragment = new SelectDateFragment();
        TextView textView = (TextView)view.findViewById(R.id.textView);
        //determine which date is being updated and open a datepicker for that field
        if(textView.getText().toString().equals(dobDate)){
            newFragment.show(getSupportFragmentManager(), "DOB ");
        }
        else{
            newFragment.show(getSupportFragmentManager(), "DOC ");
        }
    }

    public void setText(String date, String tag){
        //set the text of the selected date to the clickDate fragment
        dob = (clickDate) getSupportFragmentManager().findFragmentByTag(tag.trim());
        dob.setText(date);
        //update the local date
        if(tag.trim().equals("DOB")){
            this.dobDate = date;
        }
        else{
            this.docDate = date;
        }
    }

    public void onDelete(View view) {
        //update the selected contact with empty values
        selectedContact.setFirstName("");
        selectedContact.setLastName("");
        selectedContact.setPhoneNumber("");
        selectedContact.setBirthDate("");
        selectedContact.setDateAdded("");
        //send the updated info back to the main activity
        Intent intent = new Intent(Main2Activity.this, MainActivity.class);
        intent.putExtra("saveContact", selectedContact);
        setResult(RESULT_OK, intent);
        finish();
    }

    public void onSave(View view) {
        //update the selected contact
        edit = (TextInputEditText) findViewById(R.id.textInputEdit);
        selectedContact.setFirstName(edit.getText().toString());
        edit = (TextInputEditText) findViewById(R.id.textInputEdit2);
        selectedContact.setLastName(edit.getText().toString());
        et = (EditText) findViewById(R.id.editText3);
        selectedContact.setPhoneNumber(et.getText().toString());
        selectedContact.setBirthDate(dobDate);
        selectedContact.setDateAdded(docDate);
        //send the updated info back to the main activity
        Intent intent = new Intent(Main2Activity.this, MainActivity.class);
        intent.putExtra("saveContact", selectedContact);
        setResult(RESULT_OK, intent);
        finish();
    }

    public void afterFragmentComplete(){
        //update the date fragments
        dobDate = selectedContact.getBirthDate();
        docDate = selectedContact.getDateAdded();
        this.setText(selectedContact.getBirthDate(), "DOB");
        this.setText(selectedContact.getDateAdded(), "DOC");
    }
}
