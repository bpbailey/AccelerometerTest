package edu.illinois.cs498.accelerometertest;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.widget.TextView;

/**
 * Created by Brian on 3/12/2016.
 */
public class OrientationTrackerSystem implements SensorEventListener {
    private Activity mContext;
    private float[] mGravityVector;
    private float[] mGeomagneticVector;
    private int yaw, pitch, roll;

    private SensorManager mSensorManager;
    private Sensor mAccelSensor, mGeomagneticSensor;


    public OrientationTrackerSystem(Activity context) {
        mContext = context;
        mGravityVector = null;
        mGeomagneticVector = null;
        mSensorManager = null;
        mAccelSensor = null;
        mGeomagneticSensor = null;


        mSensorManager = (SensorManager) mContext.getSystemService(Context.SENSOR_SERVICE);
        if (mSensorManager != null) {
            mAccelSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            mGeomagneticSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        }
    }

    // Call when the activity resumes
    protected void registerListeners() {
        if (mSensorManager != null) {
            mSensorManager.registerListener(this, mAccelSensor, SensorManager.SENSOR_DELAY_UI);
            mSensorManager.registerListener(this, mGeomagneticSensor, SensorManager.SENSOR_DELAY_UI);
        }
    }

    // Call when the activity pauses to prevent processing sensor events in the background
    protected void unRegisterListeners() {
        if (mSensorManager != null) {
            // this unregisters listeners for both magnetometer and accelerometer
            mSensorManager.unregisterListener(this);
        }
    }


    public void onSensorChanged(SensorEvent event) {
        switch(event.sensor.getType()) {
            case Sensor.TYPE_ACCELEROMETER:
                mGravityVector = event.values;
                break;
            case Sensor.TYPE_MAGNETIC_FIELD:
                mGeomagneticVector = event.values;
                break;
            default:
                break;
        }

        float[] ypr = null;
        if ((mGravityVector != null) && (mGeomagneticVector != null)) {
            float R[] = new float[9];
            float I[] = new float[9];
            boolean success = SensorManager.getRotationMatrix(R, I, mGravityVector, mGeomagneticVector);
            if (success) {
                ypr = new float[3];
                SensorManager.getOrientation(R, ypr);
                yaw = Math.round((int)Math.toDegrees(ypr[0]));
                pitch = Math.round((int)Math.toDegrees(ypr[1]));
                roll = Math.round((int)Math.toDegrees(ypr[2]));
            }
        }
        print();
    }


    public int getYaw() {
        return yaw;
    }

    public int getPitch() {
        return pitch;
    }

    public int getRoll() {
        return roll;
    }


    private double getVectorLength(float [] vector) {
        return Math.sqrt(vector[0]*vector[0] + vector[1]*vector[1] + vector[2]*vector[2]);
    }



    public void print(){
        String s = "Yaw: " + Integer.toString((int) yaw) + " Pitch: " + Integer.toString((int) pitch) + " Roll: " + Integer.toString((int) roll);
        TextView view = (TextView) mContext.findViewById(R.id.mTextView2);
        if (view != null) {
            view.setText(s);
        } else {
            System.out.println(s);
        }
    }


    // Must implement for interface but not needed
    public void onAccuracyChanged(Sensor sensor, int accuracy) {}

}
