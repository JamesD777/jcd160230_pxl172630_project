package com.example.jcd160230_pxl172630_project;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.net.URI;
import java.util.List;


public class MapActivity extends AppCompatActivity {
    private LatLng test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        String receivedAddress = getIntent().getStringExtra("contactAddress");
        addFragment(new MapFragment(), false, "one");
        new AsyncMapActivity(MapActivity.this, receivedAddress).execute();
        //getLocationFromAddress(this, "800 W Campbell Rd, Richardson TX 75080");
    }

    public void addFragment(Fragment fragment, boolean addToBackStack, String tag) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();

        if (addToBackStack) {
            ft.addToBackStack(tag);
        }
        ft.replace(R.id.container, fragment, tag);
        ft.commitAllowingStateLoss();
    }

    public LatLng getLocationFromAddress(Context context, String strAddress) {

        Geocoder coder = new Geocoder(context);
        List<Address> address;
        LatLng p1 = null;

        try {
            // May throw an IOException
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null) {
                return null;
            }

            Address location = address.get(0);
            System.out.println("Latitude: " + location.getLatitude());
            System.out.println("Longitude: " + location.getLongitude());
            p1 = new LatLng(location.getLatitude(), location.getLongitude() );

        } catch (IOException ex) {

            ex.printStackTrace();
        }

        return p1;
    }
}