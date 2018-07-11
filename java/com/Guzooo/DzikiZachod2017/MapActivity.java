package com.Guzooo.DzikiZachod2017;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        // TODO: Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * TODO: Manipulates the map once available.
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

        mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng (50.596184, 21.099909)));
        mMap.setMaxZoomPreference(20);
        mMap.setMinZoomPreference(17);
        mMap.setMapType(googleMap.MAP_TYPE_SATELLITE);
        mMap.getUiSettings().setMapToolbarEnabled(false);

        mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(Marker marker) {
                if(marker.getTitle() == null) {
                    Intent intent = new Intent(getApplicationContext(), PlaceActivity.class);
                    intent.putExtra(PlaceActivity.EXTRA_ID, marker.getId());
                    startActivity(intent);
                }
                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {
                return null;
            }
        });

        createPlece(1,50.596402f,  21.099829f);
        createPlece(2, 50.597148f, 21.100830f);
        createPlece(3, 50.595793f, 21.100779f);

        createPlaceOther(R.string.mapa_title_tur,0,50.597338f,21.098513f);
        createPlaceOther(R.string.mapa_title_strus,0, 50.596267f, 21.101448f);
    }

    private void createPlece (int id, float Y, float X) {
        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(Y, X))
                .snippet(Integer.toString(id)));
    }

    private void createPlaceOther(int name, int description, float Y, float X){
        Marker marker = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(Y, X))
                .title(getString(name)));
        if(description != 0){
            marker.setSnippet(getString(description));
        }
    }
}
