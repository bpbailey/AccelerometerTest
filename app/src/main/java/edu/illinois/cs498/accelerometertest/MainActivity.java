package edu.illinois.cs498.accelerometertest;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import java.util.Calendar;
import java.util.List;


public class MainActivity extends Activity {
    private OrientationTrackerAccelerometer tracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tracker = new OrientationTrackerAccelerometer(this);
    }


    protected void onResume() {
        super.onResume();
        tracker.onResume();
    }


    protected void onPause() {
        super.onPause();
        tracker.onPause();
    }

}

