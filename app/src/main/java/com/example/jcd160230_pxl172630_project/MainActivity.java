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
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity {
    private ArrayList<Contact> contactsArrayList = new ArrayList<Contact>();
    private ListView contactListView;
    private ContactAdapter contactAdapter;
    private int editedPosition;
    private static final int REQUEST_CODE = 1;

    private SensorManager sensorManager;
    private Sensor accelerometer;
    private SensorHandler sensorHandler;
    private int currentSort = 0; // 0 for ascending, 1 for descending

    /****************************************************************************
     * Creates the toolbar, handles shaking for list reversal, and initializes
     * the contacts list
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

        // Handle shaking of device
        sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorHandler = new SensorHandler();
        sensorHandler.setOnShakeListener(new SensorHandler.OnShakeListener() {
            @Override
            public void onShake(int count) {
                Toast toast = Toast.makeText(getApplicationContext(), "List Reversed", Toast.LENGTH_SHORT);
                toast.show();

                //sortContacts(contactsArrayList);
                if(currentSort == 0) {
                    currentSort = 1;
                }
                else if(currentSort == 1) {
                    currentSort = 0;
                }
                populateList(contactsArrayList);
                System.out.println("Current Sort: " + currentSort);
            }
        });
    }
    /****************************************************************************
     * Register the listener when the main activity is resumed
     * Author: Perry Lee
     * ****************************************************************************/
    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(sensorHandler, accelerometer, SensorManager.SENSOR_DELAY_UI);
    }
    /****************************************************************************
     * Unregister when the screen is paused to try and slow battery useage
     * Author: James Dunlap
     * ****************************************************************************/
    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(sensorHandler);
    }

    /****************************************************************************
     * Populate the ListView using custom adapter
     * Author: Perry Lee
     * ****************************************************************************/
    private ArrayList<Contact> populateList(ArrayList<Contact> contactsAL) {

        // Create ListView and set to cList
        contactListView = (ListView)findViewById(R.id.cList);

        // Sort the ListView
        sortContacts(contactsAL);

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

    public void sortContacts(ArrayList<Contact> contactsAL) {
        if(currentSort == 0) {
            Collections.sort(contactsAL, new Comparator<Contact>() {
                public int compare(Contact lhs, Contact rhs) {
                    return lhs.getLastName().compareTo(rhs.getLastName());
                }
            });
        }
        else if(currentSort == 1) {
            Collections.sort(contactsAL, new Comparator<Contact>() {
                public int compare(Contact lhs, Contact rhs) {
                    return rhs.getLastName().compareTo(lhs.getLastName());
                }
            });
        }
    }
}
