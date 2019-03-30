package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.location.FusedLocationProviderClient;

public class MainActivity extends AppCompatActivity {

private BottomSheetBehavior mBottomSheetBehavior;
private FusedLocationProviderClient fusedLocationProviderClient;
private static final int MY_PERMISSION_REQUEST_FINE_LOCATION = 101;

    private static final String TAG = "MainActivity";

public TextView mLocation;
//public TextView mCurrentTime;
public TextView mCurrentTemp;

    private static int splashTimeOut=3000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        mLocation = (TextView) findViewById(R.id.locationTextView);
        mCurrentTemp = (TextView) findViewById(R.id.currentTemperatureTxt);
        //mCurrentTime = (TextView) findViewById(R.id.currentTimeTxt);

       checkNetworkConnection();
       startAnimation();
        

    }

    private void startAnimation() {
       ImageView image = findViewById(R.id.welcomeImageView);
       TextView text = findViewById(R.id.welcomeTextView);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(MainActivity.this,CurrentWeather.class);
                startActivity(i);
                finish();
            }
        },splashTimeOut);

        Animation myanim = AnimationUtils.loadAnimation(this,R.anim.splash_screen_animation);
        image.startAnimation(myanim);
        text.startAnimation(myanim);
    }



    private void checkNetworkConnection() {

        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            //gatherInfo();
        }
        else {
            Toast.makeText(this,"Cannot connect to network", Toast.LENGTH_LONG).show();
        }


    }

    //Starting to gather info from seperate class
    private void gatherInfo() {
        Intent intent = new Intent(MainActivity.this, CurrentWeather.class);
        startActivity(intent);
    }



}