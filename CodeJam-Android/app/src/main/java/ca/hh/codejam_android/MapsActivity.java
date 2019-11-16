package ca.hh.codejam_android;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.ClusterManager;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
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

        // Obtain the map
        mMap = googleMap;

        // City latitude and longitude
        LatLng montreal = new LatLng(45.50884, -73.58781);

        // Position
        CameraPosition pos = new CameraPosition.Builder().target(montreal).zoom(20).bearing(300).tilt(50).build();

        // Add a marker in Sydney and move the camera
        mMap.addMarker(new MarkerOptions().position(montreal).title("Marker in Montreal"));

        for (int i = 0; i<100; i++){
            LatLng temp = new LatLng(45.50884 + i*0.0001, -73.58781 + i*0.0001);
            mMap.addMarker(new MarkerOptions().position(temp).title(Integer.toString(i)));
        }

        this.setUpClusterer();
        // Update position
        //mMap.moveCamera(pos);


    }


    // Declare a variable for the cluster manager.
    private ClusterManager<MyItem> mClusterManager;

    private void setUpClusterer() {
        // Position the map.
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(51.503186, -0.126446), 10));

        // Initialize the manager with the context and the map.
        // (Activity extends context, so we can pass 'this' in the constructor.)
    mClusterManager = new ClusterManager<MyItem>(this, mMap);

    // Point the map's listeners at the listeners implemented by the cluster
    // manager.
    mMap.setOnCameraIdleListener(mClusterManager);
    mMap.setOnMarkerClickListener(mClusterManager);

    // Add cluster items (markers) to the cluster manager.
    addItems();
}

    private void addItems() {

        // Set some lat/lng coordinates to start with.
        double lat = 51.5145160;
        double lng = -0.1270060;

        // Add ten cluster items in close proximity, for purposes of this example.
        for (int i = 0; i < 10; i++) {
            double offset = i / 60d;
            lat = lat + offset;
            lng = lng + offset;
            MyItem offsetItem = new MyItem(lat, lng);
            mClusterManager.addItem(offsetItem);



        }
    }
}
