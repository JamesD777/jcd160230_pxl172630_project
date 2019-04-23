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

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/****************************************************************************
 * Show a google map on the screen, at the location of the contact's address
 * Author: James Dunlap
 * ****************************************************************************/
public class MapFragment extends Fragment implements OnMapReadyCallback, LocationListener{

    public MapFragment() {// Required empty public constructor
        }
    /****************************************************************************
     * basic on create function
     * Author: James Dunlap
     * ****************************************************************************/
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    static GoogleMap map;
    static double currentLat;
    static double currentLng;
    String mprovider;

    public static Location location;
    /****************************************************************************
     * When the fragment is created, request permissions, set up the location manager,
     * and create the map
     * Author: James Dunlap
     * ****************************************************************************/
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_map, container, false);

        // Request Camera and Location permissions from user
        if (!PermissionsHelper.hasWhichPermission(getActivity())) {
            //location permissions are not there.
            PermissionsHelper.requestAllPermissions(getActivity());
        }

        LocationManager locationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();

        mprovider = locationManager.getBestProvider(criteria, false);
        //set up the location manager
        if (mprovider != null && !mprovider.equals("")) {
            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return rootView;
            }
            Location location = locationManager.getLastKnownLocation(mprovider);
            locationManager.requestLocationUpdates(mprovider, 15000, 1, this);

            if (location != null)
                onLocationChanged(location);
            else
                Toast.makeText(getContext(), "No Location Provider Found Check Your Code", Toast.LENGTH_SHORT).show();
        }
        //set up the google map
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.frg);  //use SuppoprtMapFragment for using in fragment instead of activity  MapFragment = activity   SupportMapFragment = fragment
        mapFragment.getMapAsync(this);

        locationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);

        return rootView;
    }
    /****************************************************************************
     * Updates the map to be the location of the address given, called after
     * asyncmapactivity is finished
     * Author: James Dunlap
     * ****************************************************************************/
    public static void updateMap(double lat, double lng) {
        currentLng = lng;
        currentLat = lat;
        if (location != null && map != null) {//if the map exists
            map.clear(); //clear old markers
            CameraPosition currentLocation = CameraPosition.builder() //move camera to the address
                    .target(new LatLng(currentLat,currentLng))
                    .zoom(15)
                    .bearing(0)
                    .tilt(45)
                    .build();

            map.animateCamera(CameraUpdateFactory.newCameraPosition(currentLocation), 5000, null);
            map.addMarker(new MarkerOptions()
                    .position(new LatLng(currentLat, currentLng))
                    .title("Address"));
        }
    }
    /****************************************************************************
     * Create the map. Set the location to be the current location, either 0,0 or
     * the given address depending on if mapasync is done
     * Author: James Dunlap
     * ****************************************************************************/
    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        map.clear(); //clear old markers

        CameraPosition currentLocation = CameraPosition.builder()
                .target(new LatLng(currentLat,currentLng))
                .zoom(15)
                .bearing(0)
                .tilt(45)
                .build();

        map.animateCamera(CameraUpdateFactory.newCameraPosition(currentLocation), 5000, null);

        map.addMarker(new MarkerOptions()
                .position(new LatLng(currentLat, currentLng))
                .title("Address"));
    }
    /****************************************************************************
     * Location listener, updates the current lat/long of the user
     * Author: James Dunlap
     * ****************************************************************************/
    @Override
    public void onLocationChanged(Location loc) {
        location = loc;
        System.out.println("curr loc: " + location);
        System.out.println("Current Longitude:" + location.getLongitude());
        System.out.println("Current Latitude:" + location.getLatitude());
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

}