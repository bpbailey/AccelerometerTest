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

        orientationAccel = new OrientationTrackerAccelerometer(this);
        orientationSystem = new OrientationTrackerSystem(this);
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

