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

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


/****************************************************************************
 * Opens when the map address button is clicked in a contact, spawns a map fragment
 * Author: James Dunlap
 * ****************************************************************************/
public class MapActivity extends AppCompatActivity {
    /****************************************************************************
     * Spawns the map fragment and starts the async map activity for geocoding
     * Author: James Dunlap
     * ****************************************************************************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        String receivedAddress = getIntent().getStringExtra("contactAddress");
        addFragment(new MapFragment(), false, "one");
        new AsyncMapActivity(MapActivity.this, receivedAddress).execute();
    }
    /****************************************************************************
     * Adds the map fragment to the screen
     * Author: James Dunlap
     * ****************************************************************************/
    public void addFragment(Fragment fragment, boolean addToBackStack, String tag) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();

        if (addToBackStack) {
            ft.addToBackStack(tag);
        }
        ft.replace(R.id.container, fragment, tag);
        ft.commitAllowingStateLoss();
    }
}