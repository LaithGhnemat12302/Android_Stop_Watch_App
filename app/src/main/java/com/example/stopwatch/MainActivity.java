package com.example.stopwatch;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private int seconds = 0;    // Initial value
    private boolean running = false;    // Initial value

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); // super.methodName(object)
        setContentView(R.layout.activity_main);

        checkSavedInstance(savedInstanceState);
        runTimer();
    }

    private void checkSavedInstance(Bundle savedInstanceState) {    // This method to get the saved data from the Bundle
        if(savedInstanceState != null){     // If we have data (if there is something on the Bundle savedInstanceState)
            seconds = savedInstanceState.getInt("seconds"); // Get the value from the the Bundle savedInstanceState, (key --> name)
            running = savedInstanceState.getBoolean("running"); // Get the value from the the Bundle savedInstanceState, (key --> name)
        }
    }


    @Override
    protected void onSaveInstanceState(Bundle outState){    // This method to save data on the Bundle outState(Only used in orientation)
        super.onSaveInstanceState(outState);    // super.methodName(object)
        outState.putInt("seconds", seconds);    // Put up the data on the Bundle --> (name, value)
        outState.putBoolean("running", running);    // Put up the data on the Bundle --> (name, value)
    }


    //__________________________________________________________________________________________________________________________________________
    public void runTimer(){     // This method build a work on another thread
        final TextView txtTime = findViewById(R.id.txtTime);    // UI element should be final to deal with the primary thread

        final Handler handler = new Handler();  // We use handler to make another thread(should be final)
        handler.post( () -> {   // Event handler with lambda

            if(running){
                seconds++;
            }

            int hours = seconds/3600;
            int minutes = (seconds%3600) / 60;
            int secs = seconds % 60;

            String time = hours + " : " + minutes + " : " + secs;
            txtTime.setText(time);

            handler.postDelayed(this::runTimer, 1000);    // Do this work every 1000ms(1sec)
        });
    }
    //__________________________________________________________________________________________________________________________________________

    public void btnStartOnClick(View view) {
        running = true;
    }

    public void btnStopOnClick(View view) {
        running = false;
    }

    public void btnResetOnClick(View view) {
        running = false;
        seconds = 0;
    }
}