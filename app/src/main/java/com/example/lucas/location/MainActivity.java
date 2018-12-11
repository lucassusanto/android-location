package com.example.lucas.location;

import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.location.*;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
//    protected static final int REQUEST_CHECK_SETTINGS = 0x1;

    private FusedLocationProviderClient mFusedLocationClient;
    private LocationCallback mLocationCallback;
    private LocationRequest mLocationRequest;

    private TextView txLat;
    private TextView txLon;
    private TextView txAccuracy;
    private TextView txTime;
    private TextView txProv;

    private Calendar cal;
    private SimpleDateFormat format1;
    private String formatted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txLat = (TextView) findViewById(R.id.tvLat);
        txLon = (TextView) findViewById(R.id.tvLon);
        txAccuracy = (TextView) findViewById(R.id.tvAcc);
        txTime = (TextView) findViewById(R.id.tvTime);
        txProv = (TextView) findViewById(R.id.tvProv);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        setLocationCallback();
        createLocationRequest();
    }

    private void setLocationCallback() {
        mLocationCallback = (new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    return;
                }
                for (Location location : locationResult.getLocations()) {
                    // Update UI with location data
                    cal = Calendar.getInstance();
                    format1 = new SimpleDateFormat("hh:mm:ss");
                    formatted = format1.format(cal.getTime());

                    txLat.setText(String.valueOf(location.getLatitude()));
                    txLon.setText(String.valueOf(location.getLongitude()));
                    txAccuracy.setText(String.valueOf(location.getAccuracy()));
                    txTime.setText(formatted);

                    if(location.getProvider() == LocationManager.GPS_PROVIDER) {
                        txProv.setText("GPS");
                    } else if(location.getProvider() == LocationManager.NETWORK_PROVIDER) {
                        txProv.setText("Network");
                    } else {
                        txProv.setText("Passive");
                    }

                    Toast.makeText(MainActivity.this, "Location updated", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopLocationUpdates();
    }

    @Override
    protected void onResume() {
        super.onResume();
        startLocationUpdates();
    }

    private void startLocationUpdates() {
        mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, null);
    }

    private void stopLocationUpdates() {
        mFusedLocationClient.removeLocationUpdates(mLocationCallback);
    }

    public void getLocation(View view) {
        startLocationUpdates();
    }

    public void openNextPage(View view) {
        Intent intent = new Intent(this, Main2Activity.class);
        startActivity(intent);
    }
}
