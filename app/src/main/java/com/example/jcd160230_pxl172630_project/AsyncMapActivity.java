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


import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;
/****************************************************************************
 * Pings google's geocoding with the given address and returns the lat and lng
 * Author: James Dunlap, heavily borrowed from a stackoverflow answer
 * ****************************************************************************/
public class AsyncMapActivity extends AsyncTask<String, Void, String[]> {
    private WeakReference<Context> contextRef;
    private String response;
    /****************************************************************************
     * Get the address from the map activity
     * Author: James Dunlap
     * ****************************************************************************/
    public AsyncMapActivity(Context context, String response) {
        contextRef = new WeakReference<>(context);
        this.response = response;
    }
    private ProgressDialog dialog;
    /****************************************************************************
     * Start a dialog box that finishes once the async task is done
     * Author: James Dunlap, heavily borrowed from a stackoverflow answer
     * ****************************************************************************/
    @Override
    protected void onPreExecute() {
        Context context = contextRef.get();
        dialog = new ProgressDialog(context);
        super.onPreExecute();
        dialog.setMessage("Please wait...");
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }
    /****************************************************************************
     * Ping the url and get the json response
     * Author: James Dunlap, heavily borrowed from a stackoverflow answer
     * ****************************************************************************/
    @Override
    protected String[] doInBackground(String... params) {
        String response;
        try {
            response = getLatLongByURL(this.response);
            Log.d("response", "" + response);
            return new String[]{response};
        } catch (Exception e) {
            return new String[]{"error"};
        }
    }
    /****************************************************************************
     * Once the json has been retrieved, parse the lg and lat and send it to the map fragment
     * Author: James Dunlap, heavily borrowed from a stackoverflow answer
     * ****************************************************************************/
    @Override
    protected void onPostExecute(String... result) {
        try {
            JSONObject jsonObject = new JSONObject(result[0]);
            //parse json
            double lng = ((JSONArray) jsonObject.get("results")).getJSONObject(0)
                    .getJSONObject("geometry").getJSONObject("location")
                    .getDouble("lng");

            double lat = ((JSONArray) jsonObject.get("results")).getJSONObject(0)
                    .getJSONObject("geometry").getJSONObject("location")
                    .getDouble("lat");

            MapFragment.updateMap(lat, lng);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    /****************************************************************************
     * Pings google's geocoding with the given address and returns the lat and lng
     * Author: James Dunlap, heavily borrowed from a stackoverflow answer
     * ****************************************************************************/
    private String getLatLongByURL(String requestURL) {
        URL url;
        String response = "";
        try {
            url = new URL(requestURL);
            //basic url connecting
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");
            conn.setDoOutput(true);
            int responseCode = conn.getResponseCode();
            //if the server responds well, read the json
            if (responseCode == HttpsURLConnection.HTTP_OK) {
                String line;
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ((line = br.readLine()) != null) {
                    response += line;
                }
            } else {
                response = "";
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }
}
