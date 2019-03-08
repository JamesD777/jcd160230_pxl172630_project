package com.example.jcd160230_pxl172630_project;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class SensorHandler implements SensorEventListener {
    private static final float SHAKE_THRESHOLD = 2.7f;
    private static final int SHAKE_SLOP = 500;
    private static final int SHAKE_STOP = 3000;

    private OnShakeListener shakeListener;
    public long shakeTimestamp;
    public int shakeCount;
    float x, y, z;

    public void setOnShakeListener(OnShakeListener listener) {
        this.shakeListener = listener;
    }
    public interface OnShakeListener {
        public void onShake(int count);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (shakeListener != null) {
            x = event.values[0];
            y = event.values[1];
            z = event.values[2];

            float gravityX = x / SensorManager.GRAVITY_EARTH;
            float gravityY = y / SensorManager.GRAVITY_EARTH;
            float gravityZ = z / SensorManager.GRAVITY_EARTH;

            float gravityForce = (float)Math.sqrt(gravityX * gravityX + gravityY * gravityY + gravityZ * gravityZ);

            if(gravityForce >= SHAKE_THRESHOLD) {
                final long current = System.currentTimeMillis();

                if(shakeTimestamp + SHAKE_SLOP > current) {
                    return;
                }
                if(shakeTimestamp + SHAKE_STOP < current) {
                    shakeCount = 0;
                }

                shakeTimestamp = current;
                shakeCount++;

                shakeListener.onShake(shakeCount);
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
