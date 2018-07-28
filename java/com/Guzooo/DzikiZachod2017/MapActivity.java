package com.Guzooo.DzikiZachod2017;

import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
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

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        drawerLayout = findViewById(R.id.map_drawer);

        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout,R.string.szuflada_open,R.string.szuflada_close){
            @Override
            public void onDrawerClosed(View view){
                super.onDrawerClosed(view);
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerOpened(View drawerView){
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu();
            }
        };
        drawerLayout.addDrawerListener(drawerToggle);

        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
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

        showPlaceWithDetails(R.string.mapa_type_other, typeOther, 0);
        showPlaceWithDetails(R.string.mapa_type_eat, typeEat, R.id.map_check_eat);

        showPlace(R.string.mapa_type_zoo, typeZoo, R.id.map_check_zoo);
    }

    private void showPlaceWithDetails(int type, ArrayList<Marker> list, int viewId) {
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
        }

        if(viewId != 0) {
            CheckBox checkBox = findViewById(viewId);

            for (int i = 0; i < list.size(); i++) {
                list.get(i).setVisible(checkBox.isChecked());
            }
        }
    }

    private void showPlace(int type, ArrayList<Marker> list, int viewId){
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
        }

        if(viewId != 0) {
            CheckBox checkBox = findViewById(viewId);

            for (int i = 0; i < list.size(); i++) {
                list.get(i).setVisible(checkBox.isChecked());
            }
        }
    }

    public void onClickCategory (View v){
        CheckBox checkBox = (CheckBox) v;

        switch (v.getId()){
            case R.id.map_check_zoo:
                showPlace(R.string.mapa_type_zoo, typeZoo, v.getId());
                break;
            case R.id.map_check_eat:
                showPlaceWithDetails(R.string.mapa_type_eat, typeEat, v.getId());
                break;
        }
    }

    public void onClickMapaGoogle(View view){
        Uri uri = Uri.parse("https://www.google.pl/maps/dir//Parking,+Kurozw%C4%99ki/@50.5944718,21.0619917,11802m/data=!3m1!1e3!4m9!4m8!1m0!1m5!1m1!1s0x4717e332b7cb3a21:0xe37cffbd88ac840b!2m2!1d21.097011!2d50.594477!3e0");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }
}
