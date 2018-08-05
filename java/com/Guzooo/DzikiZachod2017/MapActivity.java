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
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback {

    public static final String EXTRA_Y = "y";
    public static final String EXTRA_X = "x";
    public static final String EXTRA_ZOOM = "zoom";

    private GoogleMap mMap;

    private ArrayList<Marker> typeEat = new ArrayList<>();
    private ArrayList<Marker> typeAttraction = new ArrayList<>();
    private ArrayList<Marker> typeLunapark = new ArrayList<>();
    private ArrayList<Marker> typeOther = new ArrayList<>();
    private ArrayList<Marker> typeFun = new ArrayList<>();

    private ArrayList<Marker> typeOtherZoo = new ArrayList<>();
    private ArrayList<Marker> typeOtherAttraction = new ArrayList<>();
    private ArrayList<Marker> typeOtherToalety = new ArrayList<>();
    private ArrayList<Marker> typeOtherFun = new ArrayList<>();
    private ArrayList<Marker> typeOtherEat = new ArrayList<>();
    private ArrayList<Marker> typeOtherInfo = new ArrayList<>();
    private ArrayList<Marker> typeOtherMedical = new ArrayList<>();
    private ArrayList<Marker> typeOtherOther = new ArrayList<>();
    private ArrayList<Marker> typeOtherParking = new ArrayList<>();
    private ArrayList<Marker> typeOtherBezpieczenstwo = new ArrayList<>();

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

        mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(getIntent().getFloatExtra(EXTRA_Y, 50.596184f), getIntent().getFloatExtra(EXTRA_X, 21.099909f))));
        mMap.setLatLngBoundsForCameraTarget(new LatLngBounds(new LatLng(50.593008, 21.093231), new LatLng(50.598361, 21.105293)));
        mMap.moveCamera(CameraUpdateFactory.zoomTo(getIntent().getIntExtra(EXTRA_ZOOM,17)));
        mMap.setMaxZoomPreference(20);
        mMap.setMinZoomPreference(16);

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

        showPlaceWithDetails(R.string.mapa_type_other, typeOther, 0, R.drawable.marker_other);
        showPlaceWithDetails(R.string.mapa_type_eat, typeEat, R.id.map_check_eat, R.drawable.marker_eat);
        showPlaceWithDetails(R.string.mapa_type_event_attractions, typeAttraction, R.id.map_check_attraction, R.drawable.marker_attraction);
        showPlaceWithDetails(R.string.mapa_type_lunapark, typeLunapark, R.id.map_check_lunapark, R.drawable.marker_lunapark);
        showPlaceWithDetails(R.string.mapa_type_fun, typeFun, R.id.map_check_fun, R.drawable.marker_fun);

        showPlace(R.string.mapa_type_zoo, typeOtherZoo, R.id.map_check_zoo,  R.drawable.marker_zoo);
        showPlace(R.string.mapa_type_other, typeOtherOther, 0, R.drawable.marker_other);
        showPlace(R.string.mapa_type_event_attractions, typeOtherAttraction, R.id.map_check_attraction, R.drawable.marker_attraction);
        showPlace(R.string.mapa_type_toalety, typeOtherToalety, R.id.map_check_toalety, R.drawable.marker_wc);
        showPlace(R.string.mapa_type_fun, typeOtherFun, R.id.map_check_fun, R.drawable.marker_fun);
        showPlace(R.string.mapa_type_info, typeOtherInfo, R.id.map_check_info, R.drawable.marker_info);
        showPlace(R.string.mapa_type_medical, typeOtherMedical, R.id.map_check_medical, R.drawable.marker_medical);
        showPlace(R.string.mapa_type_eat, typeOtherEat, R.id.map_check_eat, R.drawable.marker_eat);
        showPlace(R.string.mapa_type_parking, typeOtherParking, 0, R.drawable.marker_parking);
        showPlace(R.string.mapa_type_bezpieczeństwo, typeOtherBezpieczenstwo, R.id.map_check_bezpieczenstwo, R.drawable.marker_bezpieczenstwo);
    }

    private void showPlaceWithDetails(int type, ArrayList<Marker> list, int viewId, int imageId) {
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

                        if(imageId != 0){
                            list.get(list.size()-1).setIcon(BitmapDescriptorFactory.fromResource(imageId));
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

    private void showPlace(int type, ArrayList<Marker> list, int viewId, int imageId){
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
                                .position(new LatLng(cursor.getFloat(2), cursor.getFloat(3)))));

                        if (cursor.getInt(1) != 0) {
                            list.get(list.size() - 1).setSnippet(getString(cursor.getInt(1)));
                        }

                        if (cursor.getInt(0) != 0){
                            list.get(list.size() - 1).setTitle(getString(cursor.getInt(0)));
                        } else {
                            list.get(list.size() - 1).setTitle(getString(type));
                        }

                        if(imageId != 0){
                            list.get(list.size()-1).setIcon(BitmapDescriptorFactory.fromResource(imageId));
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
                showPlace(R.string.mapa_type_zoo, typeOtherZoo, v.getId(), R.drawable.marker_zoo);
                break;

            case R.id.map_check_eat:
                showPlaceWithDetails(R.string.mapa_type_eat, typeEat, v.getId(), R.drawable.marker_eat);
                showPlace(R.string.mapa_type_eat, typeOtherEat, v.getId(), R.drawable.marker_eat);
                break;

            case R.id.map_check_attraction:
                showPlaceWithDetails(R.string.mapa_type_event_attractions, typeAttraction, v.getId(), R.drawable.marker_attraction);
                showPlace(R.string.mapa_type_event_attractions, typeOtherAttraction, v.getId(), R.drawable.marker_attraction);
                break;

            case R.id.map_check_fun:
                showPlaceWithDetails(R.string.mapa_type_fun, typeFun, v.getId(), R.drawable.marker_fun);
                showPlace(R.string.mapa_type_fun, typeOtherFun, v.getId(), R.drawable.marker_fun);
                break;

            case R.id.map_check_info:
                showPlace(R.string.mapa_type_info, typeOtherInfo, v.getId(), R.drawable.marker_info);
                break;

            case R.id.map_check_lunapark:
                showPlaceWithDetails(R.string.mapa_type_lunapark, typeLunapark, v.getId(), R.drawable.marker_lunapark);
                break;

            case R.id.map_check_medical:
                showPlace(R.string.mapa_type_medical, typeOtherMedical, v.getId(), R.drawable.marker_medical);
                break;

            case R.id.map_check_toalety:
                showPlace(R.string.mapa_type_toalety, typeOtherToalety, v.getId(), R.drawable.marker_wc);
                break;

            case R.id.map_check_bezpieczenstwo:
                showPlace(R.string.mapa_type_bezpieczeństwo, typeOtherBezpieczenstwo, R.id.map_check_bezpieczenstwo, R.drawable.marker_bezpieczenstwo);
                break;
        }
    }

    public void onClickMapaGoogle(View view){
        Uri uri = Uri.parse("https://www.google.pl/maps/dir//50.5946645,21.0955824/@50.5939712,21.0988753,757m/data=!3m1!1e3!4m2!4m1!3e0");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }
}
