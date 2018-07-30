package com.Guzooo.DzikiZachod2017;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

public class PlaceActivity extends Activity {

    public static final String EXTRA_ID = "id";

    private int name;
    private int description;
    private int imageRSC;
    private int type;
    private float y;
    private float x;
    private boolean favorite;

    private SQLiteDatabase db;
    private Cursor cursor;
    private ProgramCardAdapter adapter;

    //TODO: wątek poboczny
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place);

        TextView txtDescription = findViewById(R.id.place_description);
        TextView txtType = findViewById(R.id.place_type);
        CheckBox checkFavorite = findViewById(R.id.place_favorite);

        try {
            SQLiteOpenHelper openHelper = new ProgramHelper(this);
            SQLiteDatabase db = openHelper.getReadableDatabase();
            Cursor cursor = db.query("PLACES",
                    new String[]{"NAME", "DESCRIPTION", "IMAGE_RSC", "TYPE", "Y", "X", "FAVORITE"},
                    "_id = ?",
                    new String[]{Integer.toString(getIntent().getIntExtra(EXTRA_ID, 0))},
                    null, null, null);

            if (cursor.moveToFirst()) {
                name = cursor.getInt(0);
                description = cursor.getInt(1);
                imageRSC = cursor.getInt(2);
                type = cursor.getInt(3);
                y = cursor.getFloat(4);
                x = cursor.getFloat(5);
                favorite = (cursor.getInt(6) == 1);
            }

            cursor.close();
            db.close();
        } catch (SQLiteException e) {
            Toast.makeText(this, R.string.error_read_database, Toast.LENGTH_SHORT).show();
        }

        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setTitle(getString(name));
        txtType.setText(getString(type));
        txtDescription.setText(getString(description));
        checkFavorite.setChecked(favorite);

        if(cursor == null) {
            try {
                SQLiteOpenHelper openHelper = new ProgramHelper(this);
                db = openHelper.getReadableDatabase();
                cursor = db.query("EVENTS",
                        new String[]{"_id", "NAME", "TIME_START", "TIME_END", "DAY"},
                        "PLACE = ?",
                        new String[]{Integer.toString(getIntent().getIntExtra(EXTRA_ID, 0))},
                        null, null,
                        "DAY, TIME_START, NAME");

            } catch (SQLiteException e) {
                Toast.makeText(this, R.string.error_read_database, Toast.LENGTH_SHORT).show();
            }
        }

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        RecyclerView recyclerView = findViewById(R.id.place_recycler);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new ProgramCardAdapter(cursor, findViewById(R.id.place_null));
        recyclerView.setAdapter(adapter);

        adapter.setListener(new ProgramCardAdapter.Listener() {
            @Override
            public void onClick(int id) {
                Intent intent = new Intent(getApplicationContext(), WydarzenieActivity.class);
                intent.putExtra(WydarzenieActivity.EXTRA_ID, id);
                startActivity(intent);
            }
        });
    }

    public void onClickFavorite(View v){
        CheckBox favorite = findViewById(R.id.place_favorite);

        ContentValues placeValues = new ContentValues();
        placeValues.put("FAVORITE", favorite.isChecked());

        try{
            SQLiteOpenHelper openHelper = new ProgramHelper(this);
            SQLiteDatabase db = openHelper.getWritableDatabase();
            db.update("PLACES",
                    placeValues,
                    "_id = ?",
                    new String[] {Integer.toString(getIntent().getIntExtra(EXTRA_ID, 0))});
            db.close();
        }catch (SQLiteException e){
            Toast.makeText(this, R.string.error_read_database, Toast.LENGTH_SHORT).show();
        }
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
    protected void onDestroy() {
        super.onDestroy();
        adapter.CloseCursor();
        cursor.close();
        db.close();
    }
}
