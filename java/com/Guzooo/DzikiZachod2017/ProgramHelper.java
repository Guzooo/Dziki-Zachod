package com.Guzooo.DzikiZachod2017;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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
                    + "IMAGE_RSC INTEGER, "
                    + "FAVORITE INTEGER,"
                    + "PLACE INTEGER)");
            AddEvent(db,R.string.program_taniec_country, 990,1020,R.string.program_day_pt,R.string.program_opis_taniec_country,0,0,1);
            AddEvent(db, R.string.program_otwarcie_imprezy,900, 960,R.string.program_day_pt,R.string.program_opis_otwarcie_imprezy,0,0,1);
            AddEvent(db, R.string.program_taniec_indian,960,990,R.string.program_day_pt,R.string.program_opis_taniec_indian,0,0,1);
            AddEvent(db,R.string.program_rozpoczencie_imprezy,600,630,R.string.program_day_sob,R.string.program_opis_rozpoczecie_imprezy_1,0,0,1);
            AddEvent(db,R.string.program_taniec_indian,630,690,R.string.program_day_sob,R.string.program_opis_taniec_indian,0,0,1);
            AddEvent(db,R.string.program_spektakl_dla_dzieci,660,780,R.string.program_day_sob,R.string.program_opis_spektakl_dla_dzieci,0,0,0);
            AddEvent(db,R.string.program_rozpoczencie_imprezy,600,630,R.string.program_day_nd,R.string.program_opis_rozpoczecie_imprezy_2,0,0,1);
            AddEvent(db,R.string.program_taniec_indian,630,660,R.string.program_day_nd,R.string.program_opis_taniec_indian,0,0,1);
            AddEvent(db,R.string.program_fajerwerki,1320,1350,R.string.program_day_nd,R.string.program_opis_fajerwerki,0,0,0);
            AddEvent(db,R.string.program_koniec,1380,0,R.string.program_day_nd,R.string.program_opis_koniec,0,0,1);

            db.execSQL("CREATE TABLE PLACES (_id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "NAME INTEGER,"
                    + "DESCRIPTION INTEGER,"
                    + "IMAGE_RSC INTEGER, "
                    + "TYPE INTEGER,"
                    + "Y REAL,"
                    + "X REAL,"
                    + "FAVORITE INTEGER)");
            AddPlace(db, R.string.mapa_title_pizza, R.string.mapa_snippet_pizza,0, R.string.mapa_type_eat, 50.597148f, 21.100830f, 0);
            AddPlace(db, R.string.mapa_title_scena, R.string.mapa_snippet_scena, 0, R.string.mapa_type_other, 50.596402f, 21.099829f, 0);
            AddPlace(db,R.string.mapa_title_wiata, R.string.mapa_snippet_wiata, 0, R.string.mapa_type_eat, 50.595793f, 21.100779f, 0);

            db.execSQL("CREATE TABLE PLACESOTHER (_id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "NAME INTEGERPLACESOTHER,"
                    + "DESCRIPTION INTEGER,"
                    + "TYPE INTEGER,"
                    + "Y REAL,"
                    + "X REAL)");
            AddPlaceOther(db, R.string.mapa_other_title_tur,0, R.string.mapa_type_zoo, 50.597338f, 21.098513f);
            AddPlaceOther(db, R.string.mapa_other_title_strus, 0, R.string.mapa_type_zoo, 50.596267f, 21.101448f);
        }
    }

    private static void AddEvent (SQLiteDatabase db, int name, int start, int end, int day, int description, int image, int favorite, int place){
        ContentValues eventValues = new ContentValues();
        eventValues.put("NAME", name);
        eventValues.put("TIME_START", start);
        eventValues.put("TIME_END",end);
        eventValues.put("DAY", day);
        eventValues.put("DESCRIPTION", description);
        eventValues.put("IMAGE_RSC", image);
        eventValues.put("FAVORITE", favorite);
        eventValues.put("PLACE", place);
        db.insert("EVENTS", null, eventValues);
    }

    private static void AddPlace (SQLiteDatabase db, int name, int description, int image, int type, float y, float x, int favorite){
        ContentValues placeValues = new ContentValues();
        placeValues.put("NAME", name);
        placeValues.put("DESCRIPTION", description);
        placeValues.put("IMAGE_RSC", image);
        placeValues.put("TYPE", type);
        placeValues.put("Y", y);
        placeValues.put("X", x);
        placeValues.put("FAVORITE", favorite);
        db.insert("PLACES", null, placeValues);
    }

    private static void AddPlaceOther (SQLiteDatabase db, int name, int description, int type, float y, float x){
        ContentValues placeOtherValues = new ContentValues();
        placeOtherValues.put("NAME", name);
        placeOtherValues.put("DESCRIPTION", description);
        placeOtherValues.put("TYPE", type);
        placeOtherValues.put("Y", y);
        placeOtherValues.put("X", x);
        db.insert("PLACESOTHER", null, placeOtherValues);
    }
}
