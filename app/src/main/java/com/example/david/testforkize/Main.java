package com.example.david.testforkize;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Main extends Activity{

    TextView textView;
    Button button;
    double longitude;
    double latitude;

    @Override
    protected void onStart() {
        super.onStart();
        setContentView(R.layout.main);

        textView =(TextView) findViewById(R.id.tv);
        button = (Button) findViewById(R.id.button);

        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                longitude = location.getLongitude();
                latitude = location.getLatitude();
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
                int width = displayMetrics.widthPixels;
                int height = displayMetrics.heightPixels;

                StringBuffer stringBuffer = new StringBuffer("Display: ");
                stringBuffer.append(String.valueOf(width));
                stringBuffer.append(" ");
                stringBuffer.append(String.valueOf(height));
                stringBuffer.append("\n");

                stringBuffer.append(Build.VERSION.CODENAME);
                stringBuffer.append("\n");
                stringBuffer.append(Build.VERSION.RELEASE);
                stringBuffer.append("\nHardware: ");
                stringBuffer.append(Build.HARDWARE);
                stringBuffer.append("\nDisplay: ");
                stringBuffer.append(Build.DISPLAY);
                stringBuffer.append("\nManufacturer: ");
                stringBuffer.append(Build.MANUFACTURER);
                stringBuffer.append("\n");
                stringBuffer.append("\n");

                String conn = (isNetworkAvailable()) ? "Connected" : "Disconnected";
                stringBuffer.append(conn);
                stringBuffer.append("\n");
                stringBuffer.append("\nYour Location is: ");

                stringBuffer.append(String.valueOf(longitude));
                stringBuffer.append(": ");
                stringBuffer.append(String.valueOf(latitude));

                textView.setText(stringBuffer);
            }
        });
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }
}