package com.Guzooo.DzikiZachod2017;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.drm.DrmStore;
import android.widget.Toolbar;

import java.sql.Time;

public class ProgramHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "program";
    private static final int DB_VERSION = 1;

    ProgramHelper(Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        updateMyDatabase(db, 0 ,DB_VERSION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        updateMyDatabase(db, oldVersion, newVersion);
    }

    private static void updateMyDatabase(SQLiteDatabase db, int oldVersion, int newVersion){
        if(oldVersion < 1){
            db.execSQL("CREATE TABLE EVENTS (_id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "NAME INTEGER, "
                    + "TIME_START INTEGER, "
                    + "TIME_END INTEGER, "
                    + "DAY INTEGER, "
                    + "DESCRIPTION INTEGER, "
                    + "IMAGE_RSC, "
                    + "FAVORITE)");
            AddEvent(db,R.string.program_taniec_country, 990,1020,R.string.program_day_pt,R.string.program_opis_taniec_country,0,0);
            AddEvent(db, R.string.program_otwarcie_imprezy,900, 960,R.string.program_day_pt,R.string.program_opis_otwarcie_imprezy,0,0);
            AddEvent(db, R.string.program_taniec_indian,960,990,R.string.program_day_pt,R.string.program_opis_taniec_indian,0,0);
            AddEvent(db,R.string.program_rozpoczencie_imprezy,600,630,R.string.program_day_sob,R.string.program_opis_rozpoczecie_imprezy_1,0,0);
            AddEvent(db,R.string.program_taniec_indian,630,690,R.string.program_day_sob,R.string.program_opis_taniec_indian,0,0);
            AddEvent(db,R.string.program_spektakl_dla_dzieci,660,780,R.string.program_day_sob,R.string.program_opis_spektakl_dla_dzieci,0,0);
            AddEvent(db,R.string.program_rozpoczencie_imprezy,600,630,R.string.program_day_nd,R.string.program_opis_rozpoczecie_imprezy_2,0,0);
            AddEvent(db,R.string.program_taniec_indian,630,660,R.string.program_day_nd,R.string.program_opis_taniec_indian,0,0);
            AddEvent(db,R.string.program_fajerwerki,1320,1350,R.string.program_day_nd,R.string.program_opis_fajerwerki,0,0);
            AddEvent(db,R.string.program_koniec,1380,0,R.string.program_day_nd,R.string.program_opis_koniec,0,0);
        }
    }

    private static void AddEvent (SQLiteDatabase db, int name, int start, int end, int day, int description, int image, int favorite){
        ContentValues eventValues = new ContentValues();
        eventValues.put("NAME", name);
        eventValues.put("TIME_START", start);
        eventValues.put("TIME_END",end);
        eventValues.put("DAY", day);
        eventValues.put("DESCRIPTION", description);
        eventValues.put("IMAGE_RSC", image);
        eventValues.put("FAVORITE", favorite);
        db.insert("EVENTS", null, eventValues);
    }
}
