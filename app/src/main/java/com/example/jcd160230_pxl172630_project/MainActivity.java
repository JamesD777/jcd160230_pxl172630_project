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
    String contactsFile = "contacts.txt";
    ArrayList<Contact> contactsArrayList = new ArrayList<Contact>();
    ListView contactListView;
    ContactAdapter contactAdapter;
    int editedPosition;
    private static final int REQUEST_CODE = 1;
    private File dir;
    PrintWriter pw = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create toolbar and add "New" clickable menu button
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Initialize the global ArrayList and add data to it
        contactsArrayList = createDummyData();
        ArrayList<Contact> test = readContactsFile(1);
    }

    // Function to read in the file and populate the ListView using custom adapter
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

    // Read contacts from file and set to ArrayList
    private ArrayList readContactsFile(int whichData) {
        ArrayList<Contact> contactsArray = new ArrayList<>();

        try {
            FileInputStream file = openFileInput(contactsFile);
            InputStreamReader inputReader;
            if(whichData == 0) {
                inputReader = new InputStreamReader((getAssets().open("contacts.txt")));
            }
            else {
                inputReader = new InputStreamReader(file);
            }
            BufferedReader reader = new BufferedReader(inputReader);

            String line;
            while ((line = reader.readLine()) != null) {
                String[] row = line.split("\\t");
                contactsArray.add(new Contact(row[0], row[1], row[2], row[3], row[4]));
                System.out.println(row[0]);
            }
            //reader.close();
            inputReader.close();
            file.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return contactsArray;
    }
    // Write newly constructed contacts ArrayList and write to file.
    public void writeContactsFile(Context context, ArrayList<Contact> contactsArrayList) {
        File findFile = new File(context.getFilesDir(), contactsFile);

        try {
            dir = new File(context.getFilesDir(), contactsFile);
            pw = new PrintWriter(dir);

            FileInputStream file = openFileInput(contactsFile);
            InputStreamReader inputReader = new InputStreamReader(file);
            BufferedReader reader = new BufferedReader(inputReader);
            FileOutputStream fileOutput = openFileOutput(contactsFile, Context.MODE_PRIVATE);
            for(Contact c: contactsArrayList) {
                String row = c.getFirstName() + "\t" + c.getLastName() + "\t" + c.getPhoneNumber() + "\t" + c.getBirthDate() + "\t" + c.getDateAdded();
                System.out.println("Write: " + row);
                pw.println(row);
            }
            pw.close();
            fileOutput.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Create new contact
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    // Create "New Contact"
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

    // Receive data from Main2Activity and construct new contacts ArrayList
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        contactsArrayList = readContactsFile(1);

        if(resultCode == RESULT_OK) {
            Contact saveContact;
            saveContact = data.getExtras().getParcelable("saveContact");

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
        populateList(contactsArrayList);
        writeContactsFile(this, contactsArrayList);
    }

    // Fill file with dummy data from assets to work with
    public ArrayList<Contact> createDummyData() {
        ArrayList<Contact> dummyContacts;
        dummyContacts = readContactsFile(0);
        dummyContacts = populateList(dummyContacts);
        writeContactsFile(this, dummyContacts);
        return dummyContacts;
    }
}
