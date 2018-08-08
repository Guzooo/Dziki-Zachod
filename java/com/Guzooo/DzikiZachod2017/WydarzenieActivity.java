package com.Guzooo.DzikiZachod2017;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class WydarzenieActivity extends FragmentActivity implements OnMapReadyCallback{

    public static final String EXTRA_ID = "id";
    public static final String BUNDLE_BTN_NUM = "btnnum";

    private int name;
    private int timeStart;
    private int timeEnd;
    private int day;
    private int description;
    private int imageRSC;
    private int favorite;
    private int place;

    private float Y;
    private float X;

    private SQLiteDatabase db;
    private Cursor cursor;
    private ProgramCardAdapter adapter;
    private View nullCard;
    private TextView nullCardText;

    private Button btnOld;
    private int btnOn = 0;

    private class onClickListener implements MapView.OnClickListener, GoogleMap.OnMapClickListener {
        @Override
        public void onClick(View v){}

        @Override
        public void onMapClick(LatLng latLng) {
            Intent intent = new Intent(getApplicationContext(), MapActivity.class);
            intent.putExtra(MapActivity.EXTRA_Y, Y);
            intent.putExtra(MapActivity.EXTRA_X, X);
            intent.putExtra(MapActivity.EXTRA_ZOOM, 20);
            startActivity(intent);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            SQLiteOpenHelper openHelper = new ProgramHelper(this);
            SQLiteDatabase db = openHelper.getReadableDatabase();
            Cursor cursor = db.query("EVENTS",
                    new String[]{"NAME", "TIME_START", "TIME_END", "DAY", "DESCRIPTION", "IMAGE_RSC", "FAVORITE", "PLACE"},
                    "_id = ?",
                    new String[]{Integer.toString(getIntent().getIntExtra(EXTRA_ID, 0))},
                    null, null, null);

            if (cursor.moveToFirst()) {
                name = cursor.getInt(0);
                timeStart = cursor.getInt(1);
                timeEnd = cursor.getInt(2);
                day = cursor.getInt(3);
                description = cursor.getInt(4);
                imageRSC = cursor.getInt(5);
                favorite = cursor.getInt(6);
                place = cursor.getInt(7);
            }

            if(place != 0) {
                cursor = db.query("PLACES",
                        new String[]{"Y", "X"},
                        "_id = ?",
                        new String[]{Integer.toString(place)},
                        null, null, null);

                if (cursor.moveToFirst()) {
                    Y = cursor.getFloat(0);
                    X = cursor.getFloat(1);
                }
            }

            cursor.close();
            db.close();
        } catch (SQLiteException e) {
            Toast.makeText(this, R.string.error_read_database, Toast.LENGTH_SHORT).show();
        }

        if(imageRSC != 0){
            setContentView(R.layout.activity_wydarzenie);

            ImageView imageView = findViewById(R.id.wydarzenie_image);
            imageView.setImageResource(imageRSC);
        } else {
            setContentView(R.layout.activity_wydarzenie_no_image);
        }

        TextView txtTime = findViewById(R.id.wydarzenie_time);
        TextView txtDescription = findViewById(R.id.wydarzenie_description);
        CheckBox checkFavorite = findViewById(R.id.wydarzenie_favorite);
        nullCard = findViewById(R.id.wydarzenie_null);
        nullCardText = findViewById(R.id.wydarzenie_null_text);

        getActionBar().setDisplayHomeAsUpEnabled(true);

        getActionBar().setTitle(getString(name));

        String hours = getString(day);
        int hour = timeStart / 60;
        int minute = timeStart % 60;
        hours += " " + Integer.toString(hour) + ":" + Integer.toString(minute);
        if (minute == 0) {
            hours += Integer.toString(0);
        }

        if (timeEnd >= 0) {
            hour = timeEnd / 60;
            minute = timeEnd % 60;

            hours += "-" + Integer.toString(hour) + ":" + Integer.toString(minute);
            if (minute == 0) {
                hours += Integer.toString(0);
            }
        }
        txtTime.setText(hours);

        if(description != 0) {
            txtDescription.setText(getString(description));
        }

        if(favorite == 1 || favorite == 3) {
            checkFavorite.setChecked(true);
        } else {
            checkFavorite.setChecked(false);
        }

        if(savedInstanceState != null){
            btnOn = savedInstanceState.getInt(BUNDLE_BTN_NUM);
        } else if (btnOn == 0){
            btnOn = 1;
        }

        Button btn;
        switch (btnOn){
            case 1:
                btn = findViewById(R.id.wydarzenie_btn_inne_wyd);
                btn.callOnClick();
                break;
            case 2:
                btn = findViewById(R.id.wydarzenie_btn_inne_god);
                btn.callOnClick();
                break;
        }

        if(place != 0) {
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.wydarzenie_map);
            mapFragment.getMapAsync(this);
        } else {
            View v = findViewById(R.id.wydarzenie_card_map);
            v.setVisibility(View.GONE);
        }
    }

    public void onClickFavorite(View v){
        CheckBox favoriteBox = findViewById(R.id.wydarzenie_favorite);

        if(favoriteBox.isChecked()) {
            favorite++;
        }else {
            favorite--;
        }

        ContentValues eventValues = new ContentValues();
        eventValues.put("FAVORITE", favorite);

        try{
            SQLiteOpenHelper openHelper = new ProgramHelper(this);
            SQLiteDatabase db = openHelper.getWritableDatabase();
            db.update("EVENTS",
                    eventValues,
                    "_id = ?",
                    new String[] {Integer.toString(getIntent().getIntExtra(EXTRA_ID, 0))});
            db.close();
        } catch (SQLiteException e){
            Toast.makeText(this, R.string.error_read_database, Toast.LENGTH_SHORT).show();
        }
    }

    public void onClickInneWydarzenia(View v){
        MarkBtn((Button) v);
        btnOn = 1;
        nullCardText.setText(R.string.wydarzenie_ten_czas_null);

        try {
            SQLiteOpenHelper openHelper = new ProgramHelper(this);
            db = openHelper.getReadableDatabase();
            cursor = db.query("EVENTS",
                    new String[]{"_id", "NAME", "TIME_START", "TIME_END"},
                    "((TIME_START >= ? AND TIME_START < ?) OR (TIME_END > ? AND TIME_END <= ?)) AND DAY = ? AND _id != ?",
                    new String[] {Integer.toString(timeStart), Integer.toString(timeEnd),Integer.toString(timeStart), Integer.toString(timeEnd), Integer.toString(day), Integer.toString(getIntent().getIntExtra(EXTRA_ID, 0))},
                    null, null,
                    "TIME_START, TIME_END,  NAME");
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            RecyclerView recyclerView = findViewById(R.id.wydarzenie_recycler);
            recyclerView.setLayoutManager(layoutManager);
            adapter = new ProgramCardAdapter(cursor, nullCard);
            recyclerView.setAdapter(adapter);

            adapter.setListener(new ProgramCardAdapter.Listener() {
                @Override
                public void onClick(int id) {
                    Intent intent = new Intent(getApplicationContext(), WydarzenieActivity.class);
                    intent.putExtra(WydarzenieActivity.EXTRA_ID, id);
                    startActivity(intent);
                }
            });
        }catch (SQLiteException e){
            Toast.makeText(this, R.string.error_read_database,Toast.LENGTH_SHORT).show();
        }
    }

    public void onClickInneGodziny(View v){
        MarkBtn((Button) v);
        btnOn = 2;
        nullCardText.setText(R.string.wydarzenie_te_same_null);

        try {
            SQLiteOpenHelper openHelper = new ProgramHelper(this);
            db = openHelper.getReadableDatabase();
            cursor = db.query("EVENTS",
                    new String[]{"_id", "DAY", "TIME_START", "TIME_END"},
                    "NAME = ? AND _id != ?",
                    new String[] {Integer.toString(name),Integer.toString(getIntent().getIntExtra(EXTRA_ID, 0))},
                    null, null,
                    "DAY, TIME_START,  NAME");
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            RecyclerView recyclerView = findViewById(R.id.wydarzenie_recycler);
            recyclerView.setLayoutManager(layoutManager);
            adapter = new ProgramCardAdapter(cursor, nullCard);
            recyclerView.setAdapter(adapter);

            adapter.setListener(new ProgramCardAdapter.Listener() {
                @Override
                public void onClick(int id) {
                    Intent intent = new Intent(getApplicationContext(), WydarzenieActivity.class);
                    intent.putExtra(WydarzenieActivity.EXTRA_ID, id);
                    startActivity(intent);
                }
            });
        }catch (SQLiteException e){
            Toast.makeText(this, R.string.error_read_database,Toast.LENGTH_SHORT).show();
        }
    }

    private void MarkBtn(Button b){
        if(btnOld != null){
            btnOld.setTextColor(b.getTextColors());
        }
        b.setTextColor(getResources().getColor(R.color.colorAccent));
        btnOld = b;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt(BUNDLE_BTN_NUM, btnOn);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        adapter.CloseCursor();
        cursor.close();
        db.close();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        googleMap.setOnMapClickListener(new onClickListener());

        googleMap.moveCamera(CameraUpdateFactory.zoomTo(17));
        googleMap.setMapType(googleMap.MAP_TYPE_SATELLITE);
        googleMap.getUiSettings().setMapToolbarEnabled(false);
        googleMap.getUiSettings().setAllGesturesEnabled(false);

        googleMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(Marker marker) {
                new onClickListener().onMapClick(null);
                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {
                return null;
            }
        });

        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(Y, X)));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(Y, X)));
    }
}
