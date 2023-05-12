package com.example.poolpal;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class MapsActivity extends AppCompatActivity{

    private EditText sourceEditText;
    private EditText destinationEditText;
    private MapView mapView;
    private TextView distanceTextView;
    private GoogleMap googleMap;
    private double sourceLat,sourceLng,destLat,destLng;
    private SQLiteDatabase db;
    private String source, destination;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        sourceEditText = findViewById(R.id.sourceEditText);
        destinationEditText = findViewById(R.id.destinationEditText);
        distanceTextView = findViewById(R.id.distanceTextView);

        mapView = findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                MapsActivity.this.googleMap = googleMap;
            }
        });

        Button findRideButton = findViewById(R.id.offerbtn);
        findRideButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db = openOrCreateDatabase("MyDatabase", MODE_PRIVATE, null);
                db.execSQL("CREATE TABLE Distance (source TEXT, dest TEXT, sourceLat REAL, sourceLng REAL, destLat REAL, destLng REAL)");
                ContentValues values = new ContentValues();
                values.put("sourceLat", sourceLat);
                values.put("sourceLng", sourceLng);
                values.put("destLat", destLat);
                values.put("destLng", destLng);
                values.put("source",source);
                values.put("dest",destination);



                db.insert("Distance", null, values);

                Intent intent = new Intent(MapsActivity.this, OfferRide.class);
                startActivity(intent);
            }
        });






        Button showButton = findViewById(R.id.showButton);
        showButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                source = sourceEditText.getText().toString();
                destination = destinationEditText.getText().toString();

                Geocoder geocoder = new Geocoder(MapsActivity.this);
                try {
                    List<Address> sourceAddresses = geocoder.getFromLocationName(source, 1);
                    List<Address> destinationAddresses = geocoder.getFromLocationName(destination, 1);

                    if (sourceAddresses.size() > 0 && destinationAddresses.size() > 0) {
                        LatLng sourceLatLng = new LatLng(sourceAddresses.get(0).getLatitude(), sourceAddresses.get(0).getLongitude());
                        LatLng destinationLatLng = new LatLng(destinationAddresses.get(0).getLatitude(), destinationAddresses.get(0).getLongitude());
                        sourceLat = sourceAddresses.get(0).getLatitude();
                        sourceLng = sourceAddresses.get(0).getLongitude();
                        destLat = destinationAddresses.get(0).getLatitude();
                        destLng = destinationAddresses.get(0).getLongitude();

                        Location sourceLocation = new Location("");
                        sourceLocation.setLatitude(sourceLat);
                        sourceLocation.setLongitude(sourceLng);

                        Location destLocation = new Location("");
                        destLocation.setLatitude(destLat);
                        destLocation.setLongitude(destLng);

                        float distance = (sourceLocation.distanceTo(destLocation))/1000;

                        distanceTextView.setText("Distance: " + distance + " KM");

                        googleMap.clear();
                        googleMap.addMarker(new MarkerOptions().position(sourceLatLng).title("Source"));
                        googleMap.addMarker(new MarkerOptions().position(destinationLatLng).title("Destination"));

                        LatLngBounds.Builder builder = new LatLngBounds.Builder();
                        builder.include(sourceLatLng);
                        builder.include(destinationLatLng);
                        LatLngBounds bounds = builder.build();
                        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, 50);
                        googleMap.animateCamera(cameraUpdate);
                    } else {
                        Toast.makeText(MapsActivity.this, "Could not find source or destination", Toast.LENGTH_SHORT).show();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

}