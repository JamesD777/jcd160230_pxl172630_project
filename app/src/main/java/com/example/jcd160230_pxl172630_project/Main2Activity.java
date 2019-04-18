/******************************************************************************
 * This is an application written for 4301.002, to display a contact list in an
 * android app that is modifiable by the user. It has a list that opens up a
 * specific contact's info when you click their name. This contact information
 * can be modified by the user and is saved to a sqlite database when the save
 * button is clicked.
 *
 * Written by James Dunlap(jcd160230) and Perry Lee (pxl172630) at The University
 * of Texas at Dallas starting March 4, 2019, for an Android development course.
 ******************************************************************************/

package com.example.jcd160230_pxl172630_project;


import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/****************************************************************************
 * Second activity that lets a user modify a clicked contact
 * Author: James Dunlap
 * ****************************************************************************/
public class Main2Activity extends AppCompatActivity {

    String dobDate = "DOB";
    String docDate = "DOC";
    TextInputEditText edit;
    EditText et;
    Contact selectedContact;
    clickDate dob;

    /****************************************************************************
     * Create the second activity and populate it with the contact given.
     * Author: James Dunlap
     * ****************************************************************************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        //get the contact's info
        if(getIntent().getExtras() != null) {
            selectedContact = this.getIntent().getExtras().getParcelable("sendContact");
            //populate the contact's info into the view
            edit = (TextInputEditText) findViewById(R.id.textInputEdit);
            edit.setText(selectedContact.getFirstName());
            edit = (TextInputEditText) findViewById(R.id.textInputEdit2);
            edit.setText(selectedContact.getLastName());
            et = (EditText) findViewById(R.id.editText3);
            et.setText(selectedContact.getPhoneNumber());
            et = (EditText) findViewById(R.id.postalText);
            et.setText(selectedContact.getPostal());
            et = (EditText) findViewById(R.id.postalText2);
            et.setText(selectedContact.getPostal2());
            et = (EditText) findViewById(R.id.cityText);
            et.setText(selectedContact.getCity());
            et = (EditText) findViewById(R.id.stateText);
            et.setText(selectedContact.getState());
            et = (EditText) findViewById(R.id.zipText);
            et.setText(selectedContact.getZipCode());
        }
        else {
            selectedContact = new Contact();
            Button disable = (Button)findViewById(R.id.DeleteBtn);
            disable.setEnabled(false);
        }
        getSupportFragmentManager().beginTransaction().add(R.id.frameLayout, new clickDate(), "DOB").commit();
        getSupportFragmentManager().beginTransaction().add(R.id.frameLayout2, new clickDate(), "DOC").commit();
    }
    /****************************************************************************
     * Opens the datepicker fragment, naming it based on the corresponding date field
     * Author: James Dunlap
     * ****************************************************************************/
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
    /****************************************************************************
     * Sets the text from the datepicker to the clickDate fragment
     * Author: James Dunlap
     * ****************************************************************************/
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
    /****************************************************************************
     * When the delete button is clicked, clear the data of the contact and return
     * Author: James Dunlap
     * ****************************************************************************/
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
    /****************************************************************************
     * When the save button is clicked, save the edited data and return
     * Author: James Dunlap, Perry Lee
     * ****************************************************************************/
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

        if(selectedContact.getFirstName().equals("") && selectedContact.getLastName().equals("")) {
            Toast toast = Toast.makeText(getApplicationContext(), "Please fill in a name!", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
            toast.show();
        }
        else {
            //send the updated info back to the main activity
            Intent intent = new Intent(Main2Activity.this, MainActivity.class);
            intent.putExtra("saveContact", selectedContact);
            setResult(RESULT_OK, intent);
            finish();
        }
    }
    /****************************************************************************
     * Once the clickDate fragment is created, set the initial dates
     * Author: James Dunlap
     * ****************************************************************************/
    public void afterFragmentComplete(){
        String storedBirthDate = selectedContact.getBirthDate();
        String storedDateAdded = selectedContact.getDateAdded();
        String defaultDate = "01/01/1900";
        //update the date fragments
        if(selectedContact.getBirthDate() != null && selectedContact.getDateAdded() != null) {
            dobDate = storedDateAdded;
            docDate = storedBirthDate;
            this.setText(storedBirthDate, "DOB");
            this.setText(storedDateAdded, "DOC");
        }
        else {
            dobDate = defaultDate;
            docDate = defaultDate;
            this.setText(defaultDate, "DOB");
            this.setText(defaultDate, "DOC");
        }

    }

    public void openMap(View view) {
        //format the request for the reverse Geocoding
        String address = selectedContact.getPostal().replaceAll("\\s","+") + "+" + selectedContact.getPostal2().replaceAll("\\s","+");
        System.out.println(address);
        String reverseRequest = "http://maps.googleapis.com/maps/api/geocode/json?address=" + address + ",+"+selectedContact.getCity() + ",+" + selectedContact.getState() + "&sensor=true_or_false&key=\"" + key + "\"";
    }
}
