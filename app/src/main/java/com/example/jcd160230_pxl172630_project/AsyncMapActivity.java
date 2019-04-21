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
import java.net.URLEncoder;

import javax.net.ssl.HttpsURLConnection;

public class AsyncMapActivity extends AsyncTask<Void, Void, Void> {
    private WeakReference<Context> contextRef;
    private String apiURL;
    private static final String TAG = AsyncMapActivity.class.getSimpleName();
    JSONObject jsonObj;
    HttpURLConnection connection;
    String URL;
    URL url;
    BufferedReader br;
    StringBuilder sb;
    double longitude;
    double latitude;
    String address = "800+W+Cambpell+Rd,+Richardson,+TX+75080";

    public AsyncMapActivity(Context context, String passedURL) {
        contextRef = new WeakReference<>(context);
        apiURL = passedURL;
    }
    private ProgressDialog dialog;
    @Override
    protected void onPreExecute() {
        Context context = contextRef.get();
        dialog = new ProgressDialog(context);
        super.onPreExecute();
        dialog.setMessage("Please wait...");
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

//    @Override
//    protected void doInBackground(Void... params) {
//        String response;
//        System.out.println("Executing background...");
//        try {
//            response = getLatLongByURL("https://maps.google.com/maps/api/geocode/json?address=mumbai&sensor=false&key=AIzaSyCvnWVxqgsijolJ1FRTP9jaec7XnO2PLLE");
//            Log.d("response", "" + response);
//            System.out.println("Response: " + response); // test
//            return new String[]{response};
//        } catch (Exception e) {
//            return new String[]{"error"};
//        }
//    }
@Override
protected Void doInBackground(Void... params)  {
    try {
        StringBuilder urlStringBuilder = new StringBuilder("https://maps.google.com/maps/api/geocode/json");
        urlStringBuilder.append("?address=" + URLEncoder.encode(address, "utf8"));
        urlStringBuilder.append("&sensor=false");
        URL = urlStringBuilder.toString();
        Log.d(TAG, "URL: " + URL);

        URL url = new URL("https://maps.googleapis.com/maps/api/geocode/json?address=800+W+Campbell+Rd,+Richardson,+TX+75080&sensor=true_or_false&key=AIzaSyCvnWVxqgsijolJ1FRTP9jaec7XnO2PLLE");
        connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setDoInput(true);
        connection.connect();
        br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        sb = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            sb = sb.append(line + "\n");
        }
    }catch (Exception e){e.printStackTrace(); }
    return null;
}

//    @Override
//    protected void onPostExecute(String... result) {
//        try {
//            JSONObject jsonObject = new JSONObject(result[0]);
//
//            double lng = ((JSONArray) jsonObject.get("results")).getJSONObject(0)
//                    .getJSONObject("geometry").getJSONObject("location")
//                    .getDouble("lng");
//
//            double lat = ((JSONArray) jsonObject.get("results")).getJSONObject(0)
//                    .getJSONObject("geometry").getJSONObject("location")
//                    .getDouble("lat");
//
//            Log.d("latitude", "" + lat);
//            Log.d("longitude", "" + lng);
//            System.out.println("Latitude: " + lat); // test
//            System.out.println("Longitude: " + lng); // test
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        if (dialog.isShowing()) {
//            dialog.dismiss();
//        }
//    }


    private String getLatLongByURL(String requestURL) {
        URL url;
        String response = "";
        System.out.println("1" + requestURL);
        try {
            url = new URL(requestURL);
            System.out.println("2" + requestURL);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");
            conn.setDoOutput(true);
            //int responseCode = conn.getResponseCode();

            //if (responseCode == HttpsURLConnection.HTTP_OK) {
                String line;
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ((line = br.readLine()) != null) {
                    response += line;
                }
                System.out.println("TEST RESPONSE " + response);
            //} else {
            //    response = "Failed";
            //}

        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    public void getGeoPoint(){
        try{
            longitude = ((JSONArray)jsonObj.get("results")).getJSONObject(0)
                    .getJSONObject("geometry").getJSONObject("location")
                    .getDouble("lng");
            latitude = ((JSONArray)jsonObj.get("results")).getJSONObject(0)
                    .getJSONObject("geometry").getJSONObject("location")
                    .getDouble("lat");

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    protected void onPostExecute(Void aVoid) {
        try {
            Log.d(TAG, "response code: " + connection.getResponseCode());
            jsonObj = new JSONObject(sb.toString());
            Log.d(TAG, "JSON obj: " + jsonObj);
            getGeoPoint();
            Log.d("latitude", "" + latitude);
            Log.d("longitude", "" + longitude);


        }
        catch (Exception e) {
            e.printStackTrace();
        }
        super.onPostExecute(aVoid);
    }
}

