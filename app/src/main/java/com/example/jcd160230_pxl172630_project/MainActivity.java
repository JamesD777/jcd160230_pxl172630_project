package com.example.jcd160230_pxl172630_project;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.ActionMenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {
    String contactsFile = "contacts.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
        ArrayList<Contact> contactsArrayList = contactsFile(contactsFile);
        final ListView contactListView =(ListView)findViewById(R.id.cList);
        final ContactAdapter contactAdapter = new ContactAdapter(MainActivity.this, contactsArrayList);



        contactListView.setAdapter(contactAdapter);

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
        System.out.println("Hello?");
    }

    private ArrayList contactsFile(String contactsFile) {
        ArrayList<Contact> contactsArray = new ArrayList<>();
        BufferedReader reader = null;
        try {
            //Scanner scanner = new Scanner(new File(contactsFile));
            InputStreamReader inputreader = new InputStreamReader((getAssets().open("contacts.txt")));
            reader = new BufferedReader(inputreader);
            String line;
            while ((line = reader.readLine()) != null) {
                String[] row = line.split("\\t");
                contactsArray.add(new Contact(row[0], row[1], row[2], row[3], row[4]));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if(reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return contactsArray;
    }

    //public class sortLastName implements Comparator<Contact> {
    //    public int compare()
    //}
}
