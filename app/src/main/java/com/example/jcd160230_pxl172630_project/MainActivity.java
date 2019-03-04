package com.example.jcd160230_pxl172630_project;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.support.v7.widget.Toolbar;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity {
    String contactsFile = "contacts.txt";
    ArrayList<Contact> contactsArrayList = new ArrayList<Contact>();
    ListView contactListView;
    ContactAdapter contactAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Allow file permissions
        //if(ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
        //    requestPermissions(new String[] {
        //            Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        //}
        //Fragment f = new Fragment();
        //FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        //        fragmentTransaction.add(R.id.container, this.f);
        //        fragmentTransaction.commit();

        // Read in file and apply to ListView
        populateList();
    }

    private void populateList() {
        // Load file by calling contactsFile()
        contactsArrayList = contactsFile(contactsFile);
        System.out.println("ArrayList: " + contactsArrayList);

        // Create ListView and set to cList
        contactListView = (ListView)findViewById(R.id.cList);

        // Sort the ListView
        Collections.sort(contactsArrayList, new Comparator<Contact>() {
            public int compare(Contact lhs, Contact rhs) {
                return lhs.getLastName().compareTo(rhs.getLastName());
            }
        });

        // Create ContactAdapter
        contactAdapter = new ContactAdapter(MainActivity.this, contactsArrayList);

        // Set adapter to ContactAdapter
        contactListView.setAdapter(contactAdapter);

        // Send intent to Main2Activity on item click
        contactListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Contact contact = (Contact)parent.getItemAtPosition(position);
                System.out.println(contact.getFirstName());

                Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                intent.putExtra("sendContact", contact);
                startActivity(intent);
            }
        });
        System.out.println("Populate List");
    }

    private ArrayList contactsFile(String contactsFile) {
        ArrayList<Contact> contactsArray = new ArrayList<>();
        BufferedReader reader = null;
        InputStreamReader inputreader = null;
        try {
            inputreader = new InputStreamReader((getAssets().open("contacts.txt")));
            reader = new BufferedReader(inputreader);
            String line;
            while ((line = reader.readLine()) != null) {
                String[] row = line.split("\\t");
                contactsArray.add(new Contact(row[0], row[1], row[2], row[3], row[4]));
                System.out.println(row[0]);
            }
            reader.close();
            inputreader.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if(reader != null) {
                try {
                    reader.close();
                    inputreader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return contactsArray;
    }

    // Create new contact
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.create_newcontact) {
            Intent intent = new Intent(MainActivity.this, Main2Activity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    public void onResume() {
        super.onResume();
        System.out.println("On Resume");

        /*ArrayList newContactList = new ArrayList<Contact>();
        newContactList = contactsFile(contactsFile);
        ContactAdapter newContactAdapter = new ContactAdapter(MainActivity.this, newContactList);
        contactListView.setAdapter(newContactAdapter);*/
        //contactAdapter.updateList(newContactList);
        //contactsArrayList.clear();
        //contactsArrayList.addAll(contactsFile(contactsFile));
        populateList();
        contactAdapter.notifyDataSetChanged();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK) {
            Contact saveContact;
            saveContact = this.getIntent().getExtras().getParcelable("saveContact");

        }
    }
}
