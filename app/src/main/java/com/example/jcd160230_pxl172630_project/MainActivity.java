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

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.support.v7.widget.Toolbar;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity {
    ArrayList<Contact> contactsArrayList = new ArrayList<Contact>();
    ListView contactListView;
    ContactAdapter contactAdapter;
    int editedPosition;
    private static final int REQUEST_CODE = 1;

    /****************************************************************************
     * Creates the toolbar and initializes the contacts list
     * Author: Perry Lee
     * ****************************************************************************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create toolbar and add "New" clickable menu button
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Initialize the global ArrayList and add data to it
        contactsArrayList = createDummyData();
        ArrayList<Contact> test = File_IO.readContactsFile(this, 1);
    }

    /****************************************************************************
     * Populate the ListView using custom adapter
     * Author: Perry Lee
     * ****************************************************************************/
    private ArrayList<Contact> populateList(ArrayList<Contact> contactsAL) {

        // Create ListView and set to cList
        contactListView = (ListView)findViewById(R.id.cList);

        // Sort the ListView
        Collections.sort(contactsAL, new Comparator<Contact>() {
            public int compare(Contact lhs, Contact rhs) {
                return lhs.getLastName().compareTo(rhs.getLastName());
            }
        });

        // Create ContactAdapter
        contactAdapter = new ContactAdapter(MainActivity.this, contactsAL);

        // Set adapter to ContactAdapter
        contactListView.setAdapter(contactAdapter);

        // Send intent to Main2Activity on item click
        contactListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Contact contact = (Contact)parent.getItemAtPosition(position);
                editedPosition = position;
                System.out.println(contact.getFirstName());

                Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                intent.putExtra("sendContact", contact);
                MainActivity.this.startActivityForResult(intent, REQUEST_CODE);
            }
        });
        System.out.println("Populate List");
        return contactsAL;
    }
    /****************************************************************************
     * Create a new contact button
     * Author: Perry Lee
     * ****************************************************************************/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    /****************************************************************************
     * When new contact button is clicked, open up second activity with blank info
     * Author: Perry Lee
     * ****************************************************************************/
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.create_newcontact) {
            Intent intent = new Intent(MainActivity.this, Main2Activity.class);
            MainActivity.this.startActivityForResult(intent, REQUEST_CODE);
            editedPosition = contactsArrayList.size() + 1;
        }

        return super.onOptionsItemSelected(item);
    }

    /****************************************************************************
     * Receive data from Main2Activity and construct new contacts ArrayList
     * Author: Perry Lee
     * ****************************************************************************/
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        contactsArrayList = File_IO.readContactsFile(this, 1);
        //check the returned result code
        if(resultCode == RESULT_OK) {
            Contact saveContact;
            //get the modified contact
            saveContact = data.getExtras().getParcelable("saveContact");
            //add modified contact back into the arraylist
            if(saveContact.getFirstName().equals("")) {
                contactsArrayList.remove(editedPosition);
            }
            else if (editedPosition > contactsArrayList.size()) {
                contactsArrayList.add(saveContact);
            }
            else {
                contactsArrayList.set(editedPosition, saveContact);
            }
        }
        //repopulate the screen with the modified data
        populateList(contactsArrayList);
        //write it back to the file
        File_IO.writeContactsFile(this, contactsArrayList);
    }

    /****************************************************************************
     * Fill file with dummy data from assets to work with, using File_IO class
     * Author: Perry Lee
     * ****************************************************************************/
    public ArrayList<Contact> createDummyData() {
        ArrayList<Contact> dummyContacts;
        dummyContacts = File_IO.readContactsFile(this,0);
        dummyContacts = populateList(dummyContacts);
        File_IO.writeContactsFile(this, dummyContacts);
        return dummyContacts;
    }
}
