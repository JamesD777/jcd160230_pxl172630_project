package com.example.jcd160230_pxl172630_project;




import android.app.FragmentTransaction;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

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
        //dobDate = selectedContact.getBirthDate();
        //docDate = selectedContact.getDateAdded();
        //this.setTextDOB(selectedContact.getBirthDate(), "DOB ");
        //this.setTextDOB(selectedContact.getDateAdded(), "DOC ");
    }

    public void viewDate(View view) {
        //getSupportFragmentManager().beginTransaction().add(R.id.container, new clickDate()).commit();

    }
    public void openDatePicker(View view) {
        DialogFragment newFragment = new SelectDateFragment();
        TextView textView = (TextView)view.findViewById(R.id.textView);
        if(textView.getText().toString().equals(dobDate)){
            newFragment.show(getSupportFragmentManager(), "DOB ");
        }
        else{
            newFragment.show(getSupportFragmentManager(), "DOC ");
        }
    }

    public void setTextDOB(String date, String tag){
        dob = (clickDate) getSupportFragmentManager().findFragmentByTag(tag.trim());
        dob.setText(date);
        if(tag.trim().equals("DOB")){
            this.dobDate = date;
        }
        else{
            this.docDate = date;
        }
    }

    public void onDelete(View view) {
        selectedContact.setFirstName("");
        selectedContact.setLastName("");
        selectedContact.setPhoneNumber("");
        selectedContact.setBirthDate("");
        selectedContact.setDateAdded("");
    }

    public void onSave(View view) {
        edit = (TextInputEditText) findViewById(R.id.textInputEdit);
        selectedContact.setFirstName(edit.getText().toString());
        edit = (TextInputEditText) findViewById(R.id.textInputEdit2);
        selectedContact.setLastName(edit.getText().toString());
        et = (EditText) findViewById(R.id.editText3);
        selectedContact.setPhoneNumber(et.getText().toString());
        selectedContact.setBirthDate(dobDate);
        selectedContact.setDateAdded(docDate);
    }
}
