package com.Guzooo.DzikiZachod2017;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    private ArrayList<Marker> typeZoo = new ArrayList<>();
    private ArrayList<Marker> typeEat = new ArrayList<>();
    private ArrayList<Marker> typeOther = new ArrayList<>();

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
                    intent.putExtra(PlaceActivity.EXTRA_ID, Integer.parseInt(marker.getSnippet()));
                    startActivity(intent);
                }
                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {
                return null;
            }
        });

        createPlece(R.string.mapa_type_other, typeOther);
        createPlece(R.string.mapa_type_eat, typeEat);

        showPlaceOther(R.string.mapa_type_zoo, typeZoo);
    }

    private void createPlece (int type, ArrayList<Marker> list) {
        if(list.isEmpty()) {
            try {
                SQLiteOpenHelper openHelper = new ProgramHelper(this);
                SQLiteDatabase db = openHelper.getReadableDatabase();
                Cursor cursor = db.query("PLACES",
                        new String[]{"_id", "Y", "X"},
                        "TYPE = ?",
                        new String[]{Integer.toString(type)},
                        null, null, null);

                if (cursor.moveToFirst()) {
                    do {
                        list.add(mMap.addMarker(new MarkerOptions()
                                .position(new LatLng(cursor.getFloat(1), cursor.getFloat(2)))
                                .snippet(Integer.toString(cursor.getInt(0)))));
                    } while (cursor.moveToNext());

                    cursor.close();
                    db.close();
                }
            } catch (SQLiteException e) {
                Toast.makeText(this, R.string.error_read_database, Toast.LENGTH_SHORT).show();
            }
        } else {
            for (int i = 0; i < list.size(); i++){
                mMap.addMarker(new MarkerOptions()
                                .position(list.get(i).getPosition())
                                .snippet(list.get(i).getSnippet()));
            }
        }
    }

    private void showPlaceOther(int type, ArrayList<Marker> list){
        if(list.isEmpty()) {
            try {
                SQLiteOpenHelper openHelper = new ProgramHelper(this);
                SQLiteDatabase db = openHelper.getReadableDatabase();
                Cursor cursor = db.query("PLACESOTHER",
                        new String[]{"NAME", "DESCRIPTION", "Y", "X"},
                        "TYPE = ?",
                        new String[]{Integer.toString(type)},
                        null, null, null);

                if (cursor.moveToFirst()) {
                    do {
                        list.add(mMap.addMarker(new MarkerOptions()
                                .position(new LatLng(cursor.getFloat(2), cursor.getFloat(3)))
                                .title(getString(cursor.getInt(0)))));

                        if (cursor.getInt(1) != 0) {
                            list.get(list.size() - 1).setSnippet(getString(cursor.getInt(1)));
                        }
                    } while (cursor.moveToNext());

                    cursor.close();
                    db.close();
                }
            } catch (SQLiteException e) {
                Toast.makeText(this, R.string.error_read_database, Toast.LENGTH_SHORT).show();
            }
        } else {
            for (int i = 0; i < list.size(); i++){
                mMap.addMarker(new MarkerOptions()
                        .position(list.get(i).getPosition())
                        .title(list.get(i).getTitle())
                        .snippet(list.get(i).getSnippet()));
            }
        }
    }
}
