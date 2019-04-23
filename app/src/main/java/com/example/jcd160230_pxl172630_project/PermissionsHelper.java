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
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

/****************************************************************************
 * Helper to ask permission for hardware modules.
 * Author: James Dunlap
 * ****************************************************************************/
public class PermissionsHelper implements ActivityCompat.OnRequestPermissionsResultCallback {
    private static String MANIFEST_PERMISSION = "android.permission.";

    private static final int PERMISSION_ALL = 1;
    private static String[] PERMISSIONS = {
            Manifest.permission.ACCESS_FINE_LOCATION
    };
    /****************************************************************************
     * returns boolean on whether the user has granted us permissions
     * Author: James Dunlap
     * ****************************************************************************/
    public static boolean hasPermissions(Context context, String... permissions) {
        if(context != null && permissions != null) {
            for(String permission:permissions) {
                if(ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }
    /****************************************************************************
     * Check to see we have the necessary permissions for this app.
     * Author: James Dunlap
     * ****************************************************************************/
    public static boolean hasWhichPermission(Activity activity) {
        return ContextCompat.checkSelfPermission(activity, MANIFEST_PERMISSION)
                == PackageManager.PERMISSION_GRANTED;
    }
    /****************************************************************************
     * Check to see we have the necessary permissions for this app, and ask for them if we don't.
     * Author: James Dunlap
     * ****************************************************************************/
    public static void requestAllPermissions(Activity activity) {
        if(!hasPermissions(activity, PERMISSIONS)){
            ActivityCompat.requestPermissions(activity, PERMISSIONS, PERMISSION_ALL);
        }
    }

    /****************************************************************************
     * If this worked, it would close the app upon user denying either or both permissions.
     * Author: James Dunlap
     * ****************************************************************************/
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        Log.d("GRANT RESULTS: ", Integer.toString(grantResults[0]));
        switch (requestCode) {
            case PERMISSION_ALL:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                } else {
                    System.exit(0);
                    Log.d("SHOULD ", "HAVE EXITED");
                }
                break;
        }
    }
}