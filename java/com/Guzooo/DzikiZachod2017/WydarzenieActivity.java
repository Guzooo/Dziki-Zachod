package com.Guzooo.DzikiZachod2017;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

public class WydarzenieActivity extends Activity {

    public static final String EXTRA_ID = "id";
    public static final String BUNDLE_BTN_NUM = "btnnum";

    private int name;
    private int timeStart;
    private int timeEnd;
    private int day;
    private int description;
    private int imageRSC;
    private boolean favorite;
    private int place;

    private SQLiteDatabase db;
    private Cursor cursor;
    private ProgramCardAdapter adapter;
    private View nullCard;
    private TextView nullCardText;

    private Button btnOld;
    private int btnOn = 0;

    //TODO: mozliwosc wielokrotnego otwierania nastepnych wydarzen cofanie ich trojkatem, a trzalka u gory mozliwosc powrotu do glownego activity
    //TODO: wyswietlanie komunikatu BRAK
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wydarzenie);

        TextView txtTime = findViewById(R.id.wydarzenie_time);
        TextView txtDescription = findViewById(R.id.wydarzenie_description);
        CheckBox checkFavorite = findViewById(R.id.wydarzenie_favorite);
        nullCard = findViewById(R.id.wydarzenie_null);
        nullCardText = findViewById(R.id.wydarzenie_null_text);

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
                favorite = (cursor.getInt(6) == 1);
                place = cursor.getInt(7);
            }

            cursor.close();
            db.close();
        } catch (SQLiteException e) {
            Toast.makeText(this, R.string.error_read_database, Toast.LENGTH_SHORT).show();
        }

        getActionBar().setTitle(getString(name));

        String hours = getString(day);
        int hour = timeStart / 60;
        int minute = timeStart % 60;
        hours += " " + Integer.toString(hour) + ":" + Integer.toString(minute);
        if (minute == 0) {
            hours += Integer.toString(0);
        }
        hour = timeEnd / 60;
        minute = timeEnd % 60;
        if (hour >= 0) {
            hours += "-" + Integer.toString(hour) + ":" + Integer.toString(minute);
            if (minute == 0) {
                hours += Integer.toString(0);
            }
        }
        txtTime.setText(hours);

        txtDescription.setText(getString(description));

        checkFavorite.setChecked(favorite);

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
    }

    public void onClickFavorite(View v){
        CheckBox favorite = findViewById(R.id.wydarzenie_favorite);

        ContentValues eventValues = new ContentValues();
        eventValues.put("FAVORITE", favorite.isChecked());

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
                    "TIME_START, NAME");
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
                    "DAY, TIME_START, NAME");
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
        b.setTextColor(getResources().getColor(R.color.pressedText));
        btnOld = b;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt(BUNDLE_BTN_NUM, btnOn);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        adapter.CloseCursor();
        cursor.close();
        db.close();
    }
}
