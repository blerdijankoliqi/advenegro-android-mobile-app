package com.example.advenegro;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    private ImageButton backButton;
    private ImageView backgroundImage;
    private ImageView locationIcon;
    private TextView locationText;
    private ImageView shortDescriptionIcon;
    private TextView shortDescriptionText;
    private TextView about;
    private TextView titleMapsActivity;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        titleMapsActivity = (TextView) findViewById(R.id.title_maps);
        backButton = (ImageButton) findViewById(R.id.back_button_maps);
        backgroundImage = (ImageView) findViewById(R.id.image_maps);
        locationIcon = (ImageView) findViewById(R.id.location_icon_dashboard);
        locationText = (TextView) findViewById(R.id.location_textView_dashboard);
        shortDescriptionIcon = (ImageView) findViewById(R.id.short_description_maps);
        shortDescriptionText = (TextView) findViewById(R.id.short_description_textView_maps);
        about = (TextView) findViewById(R.id.about_textView_maps);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }






    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Durmitor and move the camera
        LatLng durmitor = new LatLng(43.1496, 19.0896);
        mMap.addMarker(new MarkerOptions().position(durmitor).title("Marker in Durmitor"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(durmitor));
    }
}