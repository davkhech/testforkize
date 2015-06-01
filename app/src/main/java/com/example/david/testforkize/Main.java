package com.example.david.testforkize;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
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

        final LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        final LocationListener locationListener = new LocationListener() {
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

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
                int width = displayMetrics.widthPixels;
                int height = displayMetrics.heightPixels;
                float density = displayMetrics.density;
                int densityDpi = displayMetrics.densityDpi;
                float xdpi = displayMetrics.xdpi;
                float ydpi = displayMetrics.ydpi;

                StringBuffer stringBuffer = new StringBuffer("Display: ");
                stringBuffer.append(String.valueOf(width)).append(" ").append(String.valueOf(height)).append("\n");
                stringBuffer.append("Density: ").append(String.valueOf(density)).append(" ").append(String.valueOf(densityDpi)).append("\n");
                stringBuffer.append(String.valueOf(xdpi)).append(" ").append(String.valueOf(ydpi)).append("\n");
                stringBuffer.append("\n");

                String str = "Device: " + Build.DEVICE + "\nBRAND: " + Build.BRAND  + "\nMODEL: " + Build.MODEL
                        + "\nProduct: " + Build.PRODUCT + "\nID: " + Build.ID + "\nSERIAL: " + Build.SERIAL + "\n" + "\n";
                stringBuffer.append(str);

                str = "Board: " + Build.BOARD + "\nBootLoader: " + Build.BOOTLOADER + "\nFingerPrint: " + Build.FINGERPRINT
                        + "\nHost: " + Build.HOST + "\nUser: " + Build.USER + "\nTYPE: " + Build.TYPE + "\n" + "\n";
                stringBuffer.append(str);
                stringBuffer.append("Hardware: ");
                stringBuffer.append(Build.HARDWARE);
                stringBuffer.append("\nDisplay: ");
                stringBuffer.append(Build.DISPLAY);
                stringBuffer.append("\nManufacturer: ");
                stringBuffer.append(Build.MANUFACTURER);

                stringBuffer.append("\n").append("\nRelease ");
                stringBuffer.append(Build.VERSION.RELEASE).append(" API Level: ").append(Build.VERSION.SDK_INT).append("\n");

                stringBuffer.append("\n");
                stringBuffer.append("\nTesting... ");

                String conn = (isNetworkAvailable()) ? "Connected" : "Disconnected";
                stringBuffer.append(conn);
                stringBuffer.append("\n").append("\n");

                boolean netProv = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
                boolean gpsProv = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

                if (!netProv && !gpsProv){
                    stringBuffer.append("There are no available providers\n");
                    PackageManager packageManager = getPackageManager();

                    if (!packageManager.hasSystemFeature(PackageManager.FEATURE_LOCATION)){
                        stringBuffer.append("Your device has no feature to show location\n");
                    } else {
                        if (!packageManager.hasSystemFeature(PackageManager.FEATURE_LOCATION_GPS)){
                            stringBuffer.append("Your device has no feature to show location via GPS\n");
                        }
                        if (!packageManager.hasSystemFeature(PackageManager.FEATURE_LOCATION_NETWORK)){
                            stringBuffer.append("Your device has no feature to show location via Network");
                        }
                    }
                } else {

                    if (netProv){
                        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
                    } else {
                        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
                    }

                    stringBuffer.append("Your location is: ");
                    stringBuffer.append(String.valueOf(longitude));
                    stringBuffer.append(": ");
                    stringBuffer.append(String.valueOf(latitude));
                }

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