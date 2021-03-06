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

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
/****************************************************************************
 * Reverses the contacts when a shake of the phone is detected
 * Author: Perry Lee, James Dunlap
 * ****************************************************************************/
public class SensorHandler implements SensorEventListener {
    //set up shake defaults
    private static final float SHAKE_THRESHOLD = 2.7f;
    private static final int SHAKE_SLOP = 500;
    private static final int SHAKE_STOP = 3000;

    private OnShakeListener shakeListener;
    public long shakeTimestamp;
    public int shakeCount;
    float x, y, z;
    /****************************************************************************
     * Create the listener
     * Author: Perry Lee
     * ****************************************************************************/
    public void setOnShakeListener(OnShakeListener listener) {
        this.shakeListener = listener;
    }
    public interface OnShakeListener {
        public void onShake(int count);
    }
    /****************************************************************************
     * When the sensor changes to a large enough degree, increase shake counter
     * Author: Perry Lee
     * ****************************************************************************/
    @Override
    public void onSensorChanged(SensorEvent event) {
        if (shakeListener != null) {
            x = event.values[0];
            y = event.values[1];
            z = event.values[2];
            //set up x/y/z
            float gravityX = x / SensorManager.GRAVITY_EARTH;
            float gravityY = y / SensorManager.GRAVITY_EARTH;
            float gravityZ = z / SensorManager.GRAVITY_EARTH;

            float gravityForce = (float)Math.sqrt(gravityX * gravityX + gravityY * gravityY + gravityZ * gravityZ);
            //if the phone is being shook more than the set threshold
            if(gravityForce >= SHAKE_THRESHOLD) {
                final long current = System.currentTimeMillis();
                //dont count it
                if(shakeTimestamp + SHAKE_SLOP > current) {
                    return;
                }
                //reset
                if(shakeTimestamp + SHAKE_STOP < current) {
                    shakeCount = 0;
                }
                //increase shake count
                shakeTimestamp = current;
                shakeCount++;

                shakeListener.onShake(shakeCount);
            }
        }
    }
    /****************************************************************************
     * blank accuracy function
     * Author: N/A
     * ****************************************************************************/
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) { }
}
