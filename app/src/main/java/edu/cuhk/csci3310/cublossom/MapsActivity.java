package edu.cuhk.csci3310.cublossom;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Locale;

import edu.cuhk.csci3310.cublossom.databinding.ActivityMapsBinding;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    private static final int REQUEST_LOCATION_PERMISSION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

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

    private void setMapLongClick(final GoogleMap map) {
        map.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                map.addMarker(new MarkerOptions().position(latLng));

                map.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
                    @Override
                    public void onMapLongClick(LatLng latLng) {
                        String snippet = String.format(Locale.getDefault(),
                                "Lat: %1$.5f, Long: %2$.5f",
                                latLng.latitude,
                                latLng.longitude);
                        map.addMarker(new MarkerOptions()
                                .position(latLng)
                                .title("Your Pin")
                                .snippet(snippet));
                    }

                });
            }
        });
    }

    private void enableMyLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        } else {
            ActivityCompat.requestPermissions(this, new String[]
                            {Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_LOCATION_PERMISSION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_LOCATION_PERMISSION:
                if (grantResults.length > 0
                        && grantResults[0]
                        == PackageManager.PERMISSION_GRANTED) {
                    enableMyLocation();
                    break;
                }
        }
    }

    private void setPins(){
        LatLng naCan = new LatLng(22.42111, 114.20918);
        LatLng ucCan = new LatLng(22.42101, 114.205940);
        LatLng medCan = new LatLng(22.41955, 114.20879);
        LatLng coco = new LatLng(22.41832, 114.20557);
        LatLng dst = new LatLng(22.41832, 114.20520);
        mMap.addMarker(new MarkerOptions().position(naCan).title("New Asia College Student Canteen"));
        mMap.addMarker(new MarkerOptions().position(ucCan).title("United College Staff Canteen"));
        mMap.addMarker(new MarkerOptions().position(medCan).title("Basic Medical Sciences Building Snack Bar"));
        mMap.addMarker(new MarkerOptions().position(coco).title("Benjamin Franklin Centre Coffee Corner"));
        mMap.addMarker(new MarkerOptions().position(dst).title("Benjamin Franklin Centre Student Canteen"));
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        float zoom = 16;
        LatLng cuhk = new LatLng(22.41969, 114.20675);
        setPins();
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(cuhk,zoom));
        enableMyLocation();
        setMapLongClick(googleMap);
    }
}