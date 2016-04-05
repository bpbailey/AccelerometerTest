package edu.illinois.cs498.accelerometertest;

import android.app.Activity;
import android.os.Bundle;


public class MainActivity extends Activity {
    private OrientationTrackerAccelerometer orientationAccel;
    private OrientationTrackerSystem orientationSystem;
    private ShakeGestureDetector shakeGesture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
        These objects will print to text views. The second text view will show the correct
        yaw value because it uses Android's sensor framework to retrieve the values. The system
        framework uses the magnetometer in addition to the accelerometer to calculate yaw, pitch,
        and roll, and is therefore more expensive. Roll and pitch calculations between the
        two objects may differ by sign due to different assumptions for the starting orientation.
        */
        orientationAccel = new OrientationTrackerAccelerometer(this);
        orientationSystem = new OrientationTrackerSystem(this);

        // Toast will appear when shake is detected.
        shakeGesture = new ShakeGestureDetector(this);
    }


    protected void onResume() {
        super.onResume();
        orientationAccel.registerListeners();
        orientationSystem.registerListeners();
        shakeGesture.registerListeners();
    }

    protected void onPause() {
        super.onPause();
        orientationAccel.unRegisterListeners();
        orientationSystem.unRegisterListeners();
        shakeGesture.unRegisterListeners();
    }

}

