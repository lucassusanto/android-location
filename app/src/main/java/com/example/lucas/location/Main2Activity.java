package com.example.lucas.location;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

public class Main2Activity extends AppCompatActivity {
    private LocationService ls;

    private TextView txLat;
    private TextView txLon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        txLat = findViewById(R.id.tvLat);
        txLon = findViewById(R.id.tvLon);

        ls = new LocationService(this, 2000);
        ls.resume();
    }

    @Override
    protected void onResume() {
        super.onResume();
        ls.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        ls.pause();
    }

    public void updateLocation(View view) {
        txLat.setText(String.valueOf(ls.getLatitude()));
        txLon.setText(String.valueOf(ls.getLongitude()));
    }
}
