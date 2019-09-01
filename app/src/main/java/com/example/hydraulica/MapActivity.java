package com.example.hydraulica;

import android.app.ActionBar;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    // LOGTAG used to log in LogCat for debugging purpose
    public static final String TAG = "Hydraulica2019_RamaRai";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        //Logging in the LOCat
        Log.v(TAG, "Google Maps Activity generated");
        //Generate the Activity Icon on Action Bar
        ActionBar actionBar = getActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_SHOW_TITLE);
        actionBar.setIcon(R.drawable.google_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        //Logging in the LOCat
        Log.v(TAG, " Markers done on Google Maps");
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

        //    Add a marker in Locations and move the camera

        LatLng mexico = new LatLng(19.409713, -99.147913);
        mMap.addMarker(new MarkerOptions().position(mexico).title("Mexico City").snippet("Hydraulica Office"));
        //  mMap.moveCamera(CameraUpdateFactory.newLatLng(mexico));

        LatLng usa = new LatLng(29.756110, -95.360132);
        mMap.addMarker(new MarkerOptions().position(usa).title("USA, Houston").snippet("Hydraulica Regional office"));
       // mMap.moveCamera(CameraUpdateFactory.newLatLng(usa));

        LatLng toronto = new LatLng(43.6890, -79.3017);
        mMap.addMarker(new MarkerOptions().position(toronto).title("Main Street, Toronto").snippet("Hydraulica HQ Toronto"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(toronto));

    }
}
