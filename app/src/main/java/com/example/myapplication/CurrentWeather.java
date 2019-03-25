package com.example.myapplication;

import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class CurrentWeather extends AppCompatActivity {

    private static final String TAG = "CurrentWeather";

    String summary;
    TextView mSummary;
    ImageView mCurrentIconImageView;

    int temperature;
    TextView mTemp;

    int apparentTemp;
    TextView mApparentTemp;

    String location;
    TextView mLocation;

    int highTemp;
    TextView mHighTemp;

    int lowTemp;
    TextView mLowTemp;

    double chanceOfRain;
    TextView mChanceOfRain;

    //BottomSheetInfo

    double currentHumidity;
    TextView mCurrentHumidity;

    double currentWindSpeed;
    TextView mCurrentWindSpeed;
    ImageView mCurrentWindBearing;

    TextView mCurrentPressure;

    TextView mCurrentUVIndex;

    TextView mCurrentVisibility;

    TextView mCurrentCloudCover;

    String currentWeatherData;

    //Temp table textviews
    TextView mFirstHourTempOutput;
    TextView mSecondHourTempOutput;
    TextView mThirdHourTempOutput;
    TextView mFourthHourTempOutput;
    TextView mFifthHourTempOutput;
    TextView mSixthHourTempOutput;
    TextView mSeventhHourTempOutput;
    TextView mEighthHourTempOutput;
    TextView mNinthHourTempOutput;
    TextView mTenthHourTempOutput;
    TextView mEleventhHourTempOutput;
    TextView mTwelfthHourTempOutput;

    ImageView mFirstHourIcon;
    ImageView mSecondHourIcon;
    ImageView mThirdHourIcon;
    ImageView mFourthHourIcon;
    ImageView mFifthHourIcon;
    ImageView mSixthHourIcon;
    ImageView mSeventhHourIcon;
    ImageView mEighthHourIcon;
    ImageView mNinthHourIcon;
    ImageView mTenthHourIcon;
    ImageView mEleventhHourIcon;
    ImageView mTwelfthHourIcon;

    TextView mFirstHourTimeTextView;
    TextView mSecondHourTimeTextView;
    TextView mThirdHourTimeTextView;
    TextView mFourthHourTimeTextView;
    TextView mFifthHourTimeTextView;
    TextView mSixthHourTimeTextView;
    TextView mSeventhHourTimeTextView;
    TextView mEighthHourTimeTextView;
    TextView mNinthHourTimeTextView;
    TextView mTenthHourTimeTextView;
    TextView mEleventhHourTimeTextView;
    TextView mTwelfthHourTimeTextView;


    private String mIcon;

    private FusedLocationProviderClient mFusedLocationProviderClient;

    Location currentLocation;
    LocationRequest locationRequest;
    LocationCallback locationCallback;

    double latitude;
    TextView mLatitude;

    private static final int MY_PERMISSION_REQUEST_FINE_LOCATION = 101;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        //getLocation();
        initialiseView();
        gatherInfo();


    }

    private void initialiseView() {
        mCurrentIconImageView = findViewById(R.id.currentIconImageView);

        mTemp = findViewById(R.id.currentTemperatureTxt);
        //mApparentTemp = findViewById(R.id.apparentTemp);
        mLocation = findViewById(R.id.locationTxt);
        // mHighTemp = findViewById(R.id.highTempOutput);
        // mLowTemp = findViewById(R.id.lowTempOutput);
        mChanceOfRain = findViewById(R.id.chanceOfRainOutput);
        mCurrentHumidity = findViewById(R.id.humidityOutput);
        mCurrentWindSpeed = findViewById(R.id.windspeedOutput);
        mCurrentWindBearing = findViewById(R.id.currentWindBearingImageView);
        mCurrentPressure = findViewById(R.id.currentPressureOutput);
        mCurrentUVIndex = findViewById(R.id.currentUVIndexOutput);
        mCurrentVisibility = findViewById(R.id.currentVisibilityOutput);
        mCurrentCloudCover = findViewById(R.id.currentCloudCoverOutput);
        mSummary = findViewById(R.id.summary);

        mFirstHourTempOutput = findViewById(R.id.firstHourTemperatureOutput);
        mSecondHourTempOutput = findViewById(R.id.secondHourTemperatureOutput);
        mThirdHourTempOutput = findViewById(R.id.thirdHourTemperatureOutput);
        mFourthHourTempOutput = findViewById(R.id.fourthHourTemperatureOutput);
        mFifthHourTempOutput = findViewById(R.id.fifthHourTemperatureOutput);
        mSixthHourTempOutput = findViewById(R.id.sixthHourTemperatureOutput);
        mSeventhHourTempOutput = findViewById(R.id.seventhHourTemperatureOutput);
        mEighthHourTempOutput = findViewById(R.id.eighthHourTemperatureOutput);
        mNinthHourTempOutput = findViewById(R.id.ninthHourTemperatureOutput);
        mTenthHourTempOutput = findViewById(R.id.tenthHourTemperatureOutput);
        mEleventhHourTempOutput = findViewById(R.id.eleventhHourTemperatureOutput);
        mTwelfthHourTempOutput = findViewById(R.id.twelfthHourTemperatureOutput);

        mFirstHourIcon = findViewById(R.id.firstHourIcon);
        mSecondHourIcon = findViewById(R.id.secondHourImg);
        mThirdHourIcon = findViewById(R.id.thirdHourImg);
        mFourthHourIcon = findViewById(R.id.fourthHourImg);
        mFifthHourIcon = findViewById(R.id.fifthHourImg);
        mSixthHourIcon = findViewById(R.id.sixthHourImg);
        mSeventhHourIcon = findViewById(R.id.seventhHourImg);
        mEighthHourIcon = findViewById(R.id.eighthHourImg);
        mNinthHourIcon = findViewById(R.id.ninthHourImg);
        mTenthHourIcon = findViewById(R.id.tenthHourImg);
        mEleventhHourIcon = findViewById(R.id.eleventhHourImg);
        mTwelfthHourIcon = findViewById(R.id.twelfthHourImg);

        mFirstHourTimeTextView = findViewById(R.id.firstHourTimeTextView);
        mSecondHourTimeTextView = findViewById(R.id.secondHourTimeTextView);
        mThirdHourTimeTextView = findViewById(R.id.thirdHourTimeTextView);
        mFourthHourTimeTextView = findViewById(R.id.fourthHourTimeTextView);
        mFifthHourTimeTextView = findViewById(R.id.fifthHourTimeTextView);
        mSixthHourTimeTextView = findViewById(R.id.sixthHourTimeTextView);
        mSeventhHourTimeTextView = findViewById(R.id.seventhHourTimeTextView);
        mEighthHourTimeTextView = findViewById(R.id.eighthHourTimeTextView);
        mNinthHourTimeTextView = findViewById(R.id.ninthHourTimeTextView);
        mTenthHourTimeTextView = findViewById(R.id.tenthHourTimeTextView);
        mEleventhHourTimeTextView = findViewById(R.id.eleventhHourTimeTextView);
        mTwelfthHourTimeTextView = findViewById(R.id.twelfthHourTimeTextView);

        // mLatitude = findViewById(R.id.latitude);
    }


    private void getLocation() {
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        try {
            Task location = mFusedLocationProviderClient.getLastLocation();
            location.addOnCompleteListener(new OnCompleteListener() {
                @Override
                public void onComplete(@NonNull Task task) {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "onComplete: Found location");
                        currentLocation = (Location) task.getResult();
                        Log.d(TAG, "updateLocation: Latitude: " + currentLocation);
                        //updateLocation();


                    }

                }
            });

        } catch (SecurityException e) {
            Log.e(TAG, "getLocation: " + e.getMessage());
        }
    }

    private void updateLocation() {
        Log.d(TAG, "updateLocation: Latitude: " + currentLocation);
    }


    private void gatherInfo() {

        OkHttpClient client = new OkHttpClient();

        String url = "  https://api.darksky.net/forecast/1934e1a58c7f8b7ce7441684cccaf5bd/-27.45113485317587,153.04351949888286";

        final Request request = new Request.Builder().
                url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    currentWeatherData = response.body().string();

                    CurrentWeather.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            setSummary();
                            setCurrentIcon();
                            setTemp();
                            // setApparentTemp();
                            setLocation();
                            // setHighTemp();
                            // setLowTemp();
                            setChanceOfRain();

                            setHourlyTemperatureInfo();

                            setFormattedTime();

                            setHumidity();
                            setWindSpeed();
                            setWindBearingImage();

                            //Bottom Sheet info
                            setPressure();
                            setUVIndex();
                            setVisibility();
                            setCloudCover();


                        }
                    });

                }
            }
        });

    }


public void setFormattedTime(){



}

    private void setSummary() {
        JSONObject currentInfoObject = null;
        try {
            currentInfoObject = new JSONObject(currentWeatherData);
            JSONObject currentObject = currentInfoObject.getJSONObject("currently");
            summary = currentObject.getString("summary");

            mSummary.setText(summary);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setCurrentIcon() {
        JSONObject currentInfoObject = null;
        try {
            currentInfoObject = new JSONObject(currentWeatherData);
            JSONObject currentObject = currentInfoObject.getJSONObject("currently");
            String icon = currentObject.getString("icon");


            if (icon.equals("partly-cloudy-day")) {
                mCurrentIconImageView.setImageResource(R.drawable.partly_cloudy_day);
            } else if (icon.equals("clear-day")) {
                mCurrentIconImageView.setImageResource(R.drawable.sun);
            } else if (icon.equals("partly_cloudy_day")) {
                mCurrentIconImageView.setImageResource(R.drawable.overcast);
            } else if (icon.equals("rain")) {
                mCurrentIconImageView.setImageResource(R.drawable.raining);
            } else if (icon.equals("clear-night")) {
                mCurrentIconImageView.setImageResource(R.drawable.moon);
            }

            //HAVE TO ADD MORE ICONS


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void setHourlyTemperatureInfo() {
        JSONObject currentInfoObject = null;
        try {
            currentInfoObject = new JSONObject(currentWeatherData);
            String timeZoneObject = currentInfoObject.getString("timezone");
            JSONObject currentObject = currentInfoObject.getJSONObject("hourly");
            JSONArray dataArray = currentObject.getJSONArray("data");

            for (int i = 0; i < 13; i++) {
                JSONObject dataArrayJSONObject = dataArray.getJSONObject(i);

                if (i == 0) {
                    int firstHourTemp = dataArrayJSONObject.getInt("temperature");
                    String hourlyIcon = dataArrayJSONObject.getString("icon");
                    long hourlyTime = dataArrayJSONObject.getLong("time");


                    firstHourTemp = (firstHourTemp - 32) * 5 / 9;
                    mFirstHourTempOutput.setText(String.valueOf(firstHourTemp));

                    if (hourlyIcon.equals("partly-cloudy-day")) {
                        mFirstHourIcon.setImageResource(R.drawable.partly_cloudy_day);
                    } else if (hourlyIcon.equals("clear-day")) {
                        mFirstHourIcon.setImageResource(R.drawable.sun);
                    } else if (hourlyIcon.equals("partly_cloudy_day")) {
                        mFirstHourIcon.setImageResource(R.drawable.overcast);
                    } else if (hourlyIcon.equals("rain")) {
                        mFirstHourIcon.setImageResource(R.drawable.raining);
                    } else if (hourlyIcon.equals("clear-night")) {
                        mFirstHourIcon.setImageResource(R.drawable.moon);
                    }

                    SimpleDateFormat formatter = new SimpleDateFormat("h a");
                    formatter.setTimeZone(TimeZone.getTimeZone(timeZoneObject));
                    Date dateTime = new Date(hourlyTime * 1000);
                    String timeString = formatter.format(dateTime);

                    mFirstHourTimeTextView.setText(timeString);


                } else if (i == 1) {
                    int secondHourTemp = dataArrayJSONObject.getInt("temperature");
                    String hourlyIcon = dataArrayJSONObject.getString("icon");
                    long hourlyTime = dataArrayJSONObject.getLong("time");


                    secondHourTemp = (secondHourTemp - 32) * 5 / 9;
                    mSecondHourTempOutput.setText(String.valueOf(secondHourTemp));

                    if (hourlyIcon.equals("partly-cloudy-day")) {
                        mSecondHourIcon.setImageResource(R.drawable.partly_cloudy_day);
                    } else if (hourlyIcon.equals("clear-day")) {
                        mSecondHourIcon.setImageResource(R.drawable.sun);
                    } else if (hourlyIcon.equals("partly_cloudy_day")) {
                        mSecondHourIcon.setImageResource(R.drawable.overcast);
                    } else if (hourlyIcon.equals("rain")) {
                        mSecondHourIcon.setImageResource(R.drawable.raining);
                    } else if (hourlyIcon.equals("clear-night")) {
                        mSecondHourIcon.setImageResource(R.drawable.moon);
                    }

                    SimpleDateFormat formatter = new SimpleDateFormat("h a");
                    formatter.setTimeZone(TimeZone.getTimeZone(timeZoneObject));
                    Date dateTime = new Date(hourlyTime * 1000);
                    String timeString = formatter.format(dateTime);
                    mSecondHourTimeTextView.setText(timeString);

                } else if (i == 2) {
                    int thirdHourTemp = dataArrayJSONObject.getInt("temperature");
                    String hourlyIcon = dataArrayJSONObject.getString("icon");
                    long hourlyTime = dataArrayJSONObject.getLong("time");


                    thirdHourTemp = (thirdHourTemp - 32) * 5 / 9;
                    mThirdHourTempOutput.setText(String.valueOf(thirdHourTemp));

                    if (hourlyIcon.equals("partly-cloudy-day")) {
                        mThirdHourIcon.setImageResource(R.drawable.partly_cloudy_day);
                    } else if (hourlyIcon.equals("clear-day")) {
                        mThirdHourIcon.setImageResource(R.drawable.sun);
                    } else if (hourlyIcon.equals("partly_cloudy_day")) {
                        mThirdHourIcon.setImageResource(R.drawable.overcast);
                    } else if (hourlyIcon.equals("rain")) {
                        mThirdHourIcon.setImageResource(R.drawable.raining);
                    } else if (hourlyIcon.equals("clear-night")) {
                        mThirdHourIcon.setImageResource(R.drawable.moon);
                    }

                    SimpleDateFormat formatter = new SimpleDateFormat("h a");
                    formatter.setTimeZone(TimeZone.getTimeZone(timeZoneObject));
                    Date dateTime = new Date(hourlyTime * 1000);
                    String timeString = formatter.format(dateTime);
                    mThirdHourTimeTextView.setText(timeString);

                } else if (i == 3) {
                    int fourthHourTemp = dataArrayJSONObject.getInt("temperature");
                    String hourlyIcon = dataArrayJSONObject.getString("icon");
                    long hourlyTime = dataArrayJSONObject.getLong("time");

                    fourthHourTemp = (fourthHourTemp - 32) * 5 / 9;
                    mFourthHourTempOutput.setText(String.valueOf(fourthHourTemp));

                    if (hourlyIcon.equals("partly-cloudy-day")) {
                        mFourthHourIcon.setImageResource(R.drawable.partly_cloudy_day);
                    } else if (hourlyIcon.equals("clear-day")) {
                        mFourthHourIcon.setImageResource(R.drawable.sun);
                    } else if (hourlyIcon.equals("partly_cloudy_day")) {
                        mFourthHourIcon.setImageResource(R.drawable.overcast);
                    } else if (hourlyIcon.equals("rain")) {
                        mFourthHourIcon.setImageResource(R.drawable.raining);
                    } else if (hourlyIcon.equals("clear-night")) {
                        mFourthHourIcon.setImageResource(R.drawable.moon);
                    }

                    SimpleDateFormat formatter = new SimpleDateFormat("h a");
                    formatter.setTimeZone(TimeZone.getTimeZone(timeZoneObject));
                    Date dateTime = new Date(hourlyTime * 1000);
                    String timeString = formatter.format(dateTime);
                    mFourthHourTimeTextView.setText(timeString);

                } else if (i == 4) {
                    int fifthHourTemp = dataArrayJSONObject.getInt("temperature");
                    String hourlyIcon = dataArrayJSONObject.getString("icon");
                    long hourlyTime = dataArrayJSONObject.getLong("time");

                    fifthHourTemp = (fifthHourTemp - 32) * 5 / 9;
                    mFifthHourTempOutput.setText(String.valueOf(fifthHourTemp));

                    if (hourlyIcon.equals("partly-cloudy-day")) {
                        mFifthHourIcon.setImageResource(R.drawable.partly_cloudy_day);
                    } else if (hourlyIcon.equals("clear-day")) {
                        mFifthHourIcon.setImageResource(R.drawable.sun);
                    } else if (hourlyIcon.equals("partly_cloudy_day")) {
                        mFifthHourIcon.setImageResource(R.drawable.overcast);
                    } else if (hourlyIcon.equals("rain")) {
                        mFifthHourIcon.setImageResource(R.drawable.raining);
                    } else if (hourlyIcon.equals("clear-night")) {
                        mFifthHourIcon.setImageResource(R.drawable.moon);
                    }

                    SimpleDateFormat formatter = new SimpleDateFormat("h a");
                    formatter.setTimeZone(TimeZone.getTimeZone(timeZoneObject));
                    Date dateTime = new Date(hourlyTime * 1000);
                    String timeString = formatter.format(dateTime);
                    mFifthHourTimeTextView.setText(timeString);

                } else if (i == 5) {
                    int sixthHourTemp = dataArrayJSONObject.getInt("temperature");
                    String hourlyIcon = dataArrayJSONObject.getString("icon");
                    long hourlyTime = dataArrayJSONObject.getLong("time");

                    sixthHourTemp = (sixthHourTemp - 32) * 5 / 9;
                    mSixthHourTempOutput.setText(String.valueOf(sixthHourTemp));

                    if (hourlyIcon.equals("partly-cloudy-day")) {
                        mSixthHourIcon.setImageResource(R.drawable.partly_cloudy_day);
                    } else if (hourlyIcon.equals("clear-day")) {
                        mSixthHourIcon.setImageResource(R.drawable.sun);
                    } else if (hourlyIcon.equals("partly_cloudy_day")) {
                        mSixthHourIcon.setImageResource(R.drawable.overcast);
                    } else if (hourlyIcon.equals("rain")) {
                        mSixthHourIcon.setImageResource(R.drawable.raining);
                    } else if (hourlyIcon.equals("clear-night")) {
                        mSixthHourIcon.setImageResource(R.drawable.moon);
                    }

                    SimpleDateFormat formatter = new SimpleDateFormat("h a");
                    formatter.setTimeZone(TimeZone.getTimeZone(timeZoneObject));
                    Date dateTime = new Date(hourlyTime * 1000);
                    String timeString = formatter.format(dateTime);
                    mSixthHourTimeTextView.setText(timeString);

                } else if (i == 6) {
                    int seventhHourTemp = dataArrayJSONObject.getInt("temperature");
                    String hourlyIcon = dataArrayJSONObject.getString("icon");
                    long hourlyTime = dataArrayJSONObject.getLong("time");

                    seventhHourTemp = (seventhHourTemp - 32) * 5 / 9;
                    mSeventhHourTempOutput.setText(String.valueOf(seventhHourTemp));

                    if (hourlyIcon.equals("partly-cloudy-day")) {
                        mSeventhHourIcon.setImageResource(R.drawable.partly_cloudy_day);
                    } else if (hourlyIcon.equals("clear-day")) {
                        mSeventhHourIcon.setImageResource(R.drawable.sun);
                    } else if (hourlyIcon.equals("partly_cloudy_day")) {
                        mSeventhHourIcon.setImageResource(R.drawable.overcast);
                    } else if (hourlyIcon.equals("rain")) {
                        mSeventhHourIcon.setImageResource(R.drawable.raining);
                    } else if (hourlyIcon.equals("clear-night")) {
                        mSeventhHourIcon.setImageResource(R.drawable.moon);
                    }

                    SimpleDateFormat formatter = new SimpleDateFormat("h a");
                    formatter.setTimeZone(TimeZone.getTimeZone(timeZoneObject));
                    Date dateTime = new Date(hourlyTime * 1000);
                    String timeString = formatter.format(dateTime);
                    mSeventhHourTimeTextView.setText(timeString);

                } else if (i == 7) {
                    int eighthHourTemp = dataArrayJSONObject.getInt("temperature");
                    String hourlyIcon = dataArrayJSONObject.getString("icon");
                    long hourlyTime = dataArrayJSONObject.getLong("time");

                    eighthHourTemp = (eighthHourTemp - 32) * 5 / 9;
                    mEighthHourTempOutput.setText(String.valueOf(eighthHourTemp));

                    if (hourlyIcon.equals("partly-cloudy-day")) {
                        mEighthHourIcon.setImageResource(R.drawable.partly_cloudy_day);
                    } else if (hourlyIcon.equals("clear-day")) {
                        mEighthHourIcon.setImageResource(R.drawable.sun);
                    } else if (hourlyIcon.equals("partly_cloudy_day")) {
                        mEighthHourIcon.setImageResource(R.drawable.overcast);
                    } else if (hourlyIcon.equals("rain")) {
                        mEighthHourIcon.setImageResource(R.drawable.raining);
                    } else if (hourlyIcon.equals("clear-night")) {
                        mEighthHourIcon.setImageResource(R.drawable.moon);
                    }

                    SimpleDateFormat formatter = new SimpleDateFormat("h a");
                    formatter.setTimeZone(TimeZone.getTimeZone(timeZoneObject));
                    Date dateTime = new Date(hourlyTime * 1000);
                    String timeString = formatter.format(dateTime);
                    mEighthHourTimeTextView.setText(timeString);

                } else if (i == 8) {
                    int ninthHourTemp = dataArrayJSONObject.getInt("temperature");
                    String hourlyIcon = dataArrayJSONObject.getString("icon");
                    long hourlyTime = dataArrayJSONObject.getLong("time");

                    ninthHourTemp = (ninthHourTemp - 32) * 5 / 9;
                    mNinthHourTempOutput.setText(String.valueOf(ninthHourTemp));

                    if (hourlyIcon.equals("partly-cloudy-day")) {
                        mNinthHourIcon.setImageResource(R.drawable.partly_cloudy_day);
                    } else if (hourlyIcon.equals("clear-day")) {
                        mNinthHourIcon.setImageResource(R.drawable.sun);
                    } else if (hourlyIcon.equals("partly_cloudy_day")) {
                        mNinthHourIcon.setImageResource(R.drawable.overcast);
                    } else if (hourlyIcon.equals("rain")) {
                        mNinthHourIcon.setImageResource(R.drawable.raining);
                    } else if (hourlyIcon.equals("clear-night")) {
                        mNinthHourIcon.setImageResource(R.drawable.moon);
                    }

                    SimpleDateFormat formatter = new SimpleDateFormat("h a");
                    formatter.setTimeZone(TimeZone.getTimeZone(timeZoneObject));
                    Date dateTime = new Date(hourlyTime * 1000);
                    String timeString = formatter.format(dateTime);
                    mNinthHourTimeTextView.setText(timeString);

                } else if (i == 9) {
                    int tenthHourTemp = dataArrayJSONObject.getInt("temperature");
                    String hourlyIcon = dataArrayJSONObject.getString("icon");
                    long hourlyTime = dataArrayJSONObject.getLong("time");

                    tenthHourTemp = (tenthHourTemp - 32) * 5 / 9;
                    mTenthHourTempOutput.setText(String.valueOf(tenthHourTemp));

                    if (hourlyIcon.equals("partly-cloudy-day")) {
                        mTenthHourIcon.setImageResource(R.drawable.partly_cloudy_day);
                    } else if (hourlyIcon.equals("clear-day")) {
                        mTenthHourIcon.setImageResource(R.drawable.sun);
                    } else if (hourlyIcon.equals("partly_cloudy_day")) {
                        mTenthHourIcon.setImageResource(R.drawable.overcast);
                    } else if (hourlyIcon.equals("rain")) {
                        mTenthHourIcon.setImageResource(R.drawable.raining);
                    } else if (hourlyIcon.equals("clear-night")) {
                        mTenthHourIcon.setImageResource(R.drawable.moon);
                    }

                    SimpleDateFormat formatter = new SimpleDateFormat("h a");
                    formatter.setTimeZone(TimeZone.getTimeZone(timeZoneObject));
                    Date dateTime = new Date(hourlyTime * 1000);
                    String timeString = formatter.format(dateTime);
                    mTenthHourTimeTextView.setText(timeString);

                } else if (i == 10) {
                    int eleventhHourTemp = dataArrayJSONObject.getInt("temperature");
                    String hourlyIcon = dataArrayJSONObject.getString("icon");
                    long hourlyTime = dataArrayJSONObject.getLong("time");

                    eleventhHourTemp = (eleventhHourTemp - 32) * 5 / 9;
                    mEleventhHourTempOutput.setText(String.valueOf(eleventhHourTemp));

                    if (hourlyIcon.equals("partly-cloudy-day")) {
                        mEleventhHourIcon.setImageResource(R.drawable.partly_cloudy_day);
                    } else if (hourlyIcon.equals("clear-day")) {
                        mEleventhHourIcon.setImageResource(R.drawable.sun);
                    } else if (hourlyIcon.equals("partly_cloudy_day")) {
                        mEleventhHourIcon.setImageResource(R.drawable.overcast);
                    } else if (hourlyIcon.equals("rain")) {
                        mEleventhHourIcon.setImageResource(R.drawable.raining);
                    } else if (hourlyIcon.equals("clear-night")) {
                        mEleventhHourIcon.setImageResource(R.drawable.moon);
                    }

                    SimpleDateFormat formatter = new SimpleDateFormat("h a");
                    formatter.setTimeZone(TimeZone.getTimeZone(timeZoneObject));
                    Date dateTime = new Date(hourlyTime * 1000);
                    String timeString = formatter.format(dateTime);
                    mEleventhHourTimeTextView.setText(timeString);

                } else if (i == 11) {
                    int twelfthHourTemp = dataArrayJSONObject.getInt("temperature");
                    String hourlyIcon = dataArrayJSONObject.getString("icon");
                    long hourlyTime = dataArrayJSONObject.getLong("time");

                    twelfthHourTemp = (twelfthHourTemp - 32) * 5 / 9;
                    mTwelfthHourTempOutput.setText(String.valueOf(twelfthHourTemp));

                    if (hourlyIcon.equals("partly-cloudy-day")) {
                        mTwelfthHourIcon.setImageResource(R.drawable.partly_cloudy_day);
                    } else if (hourlyIcon.equals("clear-day")) {
                        mTwelfthHourIcon.setImageResource(R.drawable.sun);
                    } else if (hourlyIcon.equals("partly_cloudy_day")) {
                        mTwelfthHourIcon.setImageResource(R.drawable.overcast);
                    } else if (hourlyIcon.equals("rain")) {
                        mTwelfthHourIcon.setImageResource(R.drawable.raining);
                    } else if (hourlyIcon.equals("clear-night")) {
                        mTwelfthHourIcon.setImageResource(R.drawable.moon);
                    }

                    SimpleDateFormat formatter = new SimpleDateFormat("h a");
                    formatter.setTimeZone(TimeZone.getTimeZone(timeZoneObject));
                    Date dateTime = new Date(hourlyTime * 1000);
                    String timeString = formatter.format(dateTime);
                    mTwelfthHourTimeTextView.setText(timeString);

                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setCloudCover() {
        JSONObject currentInfoObject = null;
        try {
            currentInfoObject = new JSONObject(currentWeatherData);
            JSONObject currentObject = currentInfoObject.getJSONObject("currently");
            double currentCloudCover = currentObject.getInt("cloudCover");

            Log.i(TAG, "setCloudCover: " + currentCloudCover);

            currentCloudCover *= 100;
            mCurrentCloudCover.setText("Cloud Cover: " + Math.round(currentCloudCover) + "%");

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setVisibility() {
        JSONObject currentInfoObject = null;
        try {
            currentInfoObject = new JSONObject(currentWeatherData);
            JSONObject currentObject = currentInfoObject.getJSONObject("currently");
            double currentVisibility = currentObject.getDouble("visibility");

            currentVisibility *= 1.6;
            mCurrentVisibility.setText("Visibility: " + Math.round(currentVisibility) + "km");

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setUVIndex() {
        JSONObject currentInfoObject = null;
        try {
            currentInfoObject = new JSONObject(currentWeatherData);
            JSONObject currentObject = currentInfoObject.getJSONObject("currently");
            int currentUVIndex = currentObject.getInt("uvIndex");


            mCurrentUVIndex.setText("UV Index: " + currentUVIndex);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setPressure() {
        JSONObject currentInfoObject = null;
        try {
            currentInfoObject = new JSONObject(currentWeatherData);
            JSONObject currentObject = currentInfoObject.getJSONObject("currently");
            double currentPressure = currentObject.getDouble("pressure");


            mCurrentPressure.setText("Pressure: " + Math.round(currentPressure) + "mbar");

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setWindBearingImage() {
        JSONObject currentInfoObject = null;
        try {
            currentInfoObject = new JSONObject(currentWeatherData);
            JSONObject currentObject = currentInfoObject.getJSONObject("currently");
            double currentWindBearing = currentObject.getDouble("windBearing");

            if (currentWindBearing >= 337.5 && currentWindBearing < 360 || currentWindBearing >= 0 && currentWindBearing < 22.5) {
                mCurrentWindBearing.setImageResource(R.drawable.northern_wind);
            } else if (currentWindBearing >= 22.5 && currentWindBearing < 67.5) {
                mCurrentWindBearing.setImageResource(R.drawable.north_eastern_wind);
            } else if (currentWindBearing >= 67.5 && currentWindBearing < 112.5) {
                mCurrentWindBearing.setImageResource(R.drawable.eastern_wind);
            } else if (currentWindBearing >= 112.5 && currentWindBearing < 157.5) {
                mCurrentWindBearing.setImageResource(R.drawable.south_eastern_wind);
            } else if (currentWindBearing >= 157.5 && currentWindBearing < 202.5) {
                mCurrentWindBearing.setImageResource(R.drawable.southern_wind);
            } else if (currentWindBearing >= 202.5 && currentWindBearing < 247.5) {
                mCurrentWindBearing.setImageResource(R.drawable.south_western_wind);
            } else if (currentWindBearing >= 247.5 && currentWindBearing < 292.5) {
                mCurrentWindBearing.setImageResource(R.drawable.western_wind);
            } else if (currentWindBearing >= 292.5 && currentWindBearing < 337.5) {
                mCurrentWindBearing.setImageResource(R.drawable.north_western_wind);
            } else {
                mCurrentWindBearing.setImageResource(R.drawable.wind);

            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setApparentTemp() {
        JSONObject currentInfoObject = null;
        try {
            currentInfoObject = new JSONObject(currentWeatherData);
            JSONObject currentObject = currentInfoObject.getJSONObject("currently");
            apparentTemp = currentObject.getInt("apparentTemperature");

            //converting to Celcius
            apparentTemp = (apparentTemp - 32) * 5 / 9;

            mApparentTemp.setText("Feels like " + apparentTemp + "°C");

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private void setWindSpeed() {
        JSONObject currentInfoObject = null;
        try {
            currentInfoObject = new JSONObject(currentWeatherData);
            JSONObject currentObject = currentInfoObject.getJSONObject("currently");
            currentWindSpeed = currentObject.getDouble("windSpeed");

            //converting from miles per hour to kph
            currentWindSpeed = (currentWindSpeed * 1.60934);

            mCurrentWindSpeed.setText(Math.round(currentWindSpeed) + "km/h");

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //NOT WORKING PROPERLY
    private void setChanceOfRain() {
        JSONObject currentInfoObject = null;
        try {
            currentInfoObject = new JSONObject(currentWeatherData);
            JSONObject currentObject = currentInfoObject.getJSONObject("daily");
            JSONArray dataArray = currentObject.getJSONArray("data");

            for (int i = 0; i < dataArray.length(); i++) {
                JSONObject dataArrayJSONObject = dataArray.getJSONObject(i);

                double chanceOfRainJSON = dataArrayJSONObject.getDouble("precipProbability");

                chanceOfRain = chanceOfRainJSON;
                //converting to %
                chanceOfRain = (chanceOfRain * 100);

                mChanceOfRain.setText(Math.round(chanceOfRain) + "%");
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setTemp() {
        JSONObject currentInfoObject = null;
        try {
            currentInfoObject = new JSONObject(currentWeatherData);
            JSONObject currentObject = currentInfoObject.getJSONObject("currently");
            temperature = currentObject.getInt("temperature");

            //converting to Celcius
            temperature = (temperature - 32) * 5 / 9;

            mTemp.setText(temperature + "°C");

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setLowTemp() {

        JSONObject currentInfoObject = null;
        try {
            currentInfoObject = new JSONObject(currentWeatherData);
            JSONObject currentObject = currentInfoObject.getJSONObject("daily");
            JSONArray dataArray = currentObject.getJSONArray("data");

            for (int i = 0; i < dataArray.length(); i++) {
                JSONObject dataArrayJSONObject = dataArray.getJSONObject(i);

                int minTemp = dataArrayJSONObject.getInt("temperatureMin");
                //Log.d(TAG, "setHighTemp: FUCK YEAH");

                lowTemp = minTemp;
                //converting to Celcius
                lowTemp = (lowTemp - 32) * 5 / 9;

                mLowTemp.setText(lowTemp + "°↓");
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setHighTemp() {
        JSONObject currentInfoObject = null;
        try {
            currentInfoObject = new JSONObject(currentWeatherData);
            JSONObject currentObject = currentInfoObject.getJSONObject("daily");
            JSONArray dataArray = currentObject.getJSONArray("data");

            for (int i = 0; i < dataArray.length(); i++) {
                JSONObject dataArrayJSONObject = dataArray.getJSONObject(i);

                int maxTemp = dataArrayJSONObject.getInt("temperatureMax");
                Log.d(TAG, "setHighTemp: FUCK YEAH");

                highTemp = maxTemp;
                //converting to Celcius
                highTemp = (highTemp - 32) * 5 / 9;

                mHighTemp.setText(highTemp + "°↑");
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setLocation() {
        JSONObject currentInfoObject = null;
        try {
            currentInfoObject = new JSONObject(currentWeatherData);
            location = currentInfoObject.getString("timezone");
            mLocation.setText(location);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setHumidity() {
        JSONObject currentInfoObject = null;
        try {
            currentInfoObject = new JSONObject(currentWeatherData);
            JSONObject currentObject = currentInfoObject.getJSONObject("currently");
            currentHumidity = currentObject.getDouble("humidity");

            //converting to %
            currentHumidity = (currentHumidity * 100);

            mCurrentHumidity.setText(Math.round(currentHumidity) + "%");

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}
