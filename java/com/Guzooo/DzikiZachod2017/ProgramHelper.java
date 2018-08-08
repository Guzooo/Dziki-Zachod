package com.Guzooo.DzikiZachod2017;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ProgramHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "program";
    private static final int DB_VERSION = 2;

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
            AddEvent(db, R.string.program_otwarcie_imprezy, 960, 975, R.string.program_day_1, R.string.program_opis_otwarcie_imprezy, 0 ,0, 1);
            AddEvent(db, R.string.program_prezentacja, 990, 1020, R.string.program_day_1, 0, R.drawable.sponsorzy, 0, 1);
            AddEvent(db, R.string.program_zywa_historia, 1035, 1125, R.string.program_day_1, 0 ,0, 0, 3);
            AddEvent(db, R.string.program_moda_historyczna, 1110, 1140, R.string.program_day_1, 0, 0, 0, 2);
            AddEvent(db, R.string.program_biesiada_przy_disco, 1140, 1320, R.string.program_day_1, 0, 0, 0, 1);
            AddEvent(db, R.string.program_rozpoczecie, 600, 615, R.string.program_day_2, 0, 0, 0, 1);
            AddEvent(db, R.string.program_blok_konkursow_zabaw, 620, 660, R.string.program_day_2, 0, 0, 0, 1);
            AddEvent(db, R.string.program_indian, 660, 690, R.string.program_day_2, R.string.program_opis_indian, R.drawable.taniec_indian, 0, 1);
            AddEvent(db, R.string.program_country, 690, 720, R.string.program_day_2, R.string.program_opis_country, R.drawable.taniec_country, 0 , 1);
            AddEvent(db, R.string.program_iluzja, 720, 750, R.string.program_day_2, 0, 0, 0, 1);
            AddEvent(db, R.string.program_kadryl, 750, 780, R.string.program_day_2, R.string.program_opis_kadryl, R.drawable.kadryl, 0, 2);
            AddEvent(db, R.string.program_country, 780, 810, R.string.program_day_2, R.string.program_opis_country, R.drawable.taniec_country, 0, 1);
            AddEvent(db, R.string.program_zywa_historia, 780, 810, R.string.program_day_2, 0, 0, 0, 3);
            AddEvent(db, R.string.program_traperzy, 810, 840, R.string.program_day_2, 0, 0,0, 1);
            AddEvent(db, R.string.program_pokaz_nauka_indian, 840, 870, R.string.program_day_2, R.string.program_opis_indian, R.drawable.taniec_indian, 0, 1);
            AddEvent(db, R.string.program_moda_historyczna, 870, 930, R.string.program_day_2, 0, 0, 0, 2);
            AddEvent(db, R.string.program_zywa_historia, 960, 990, R.string.program_day_2, 0, 0, 0, 3);
            AddEvent(db, R.string.program_cyrkowe, 960, 1020, R.string.program_day_2, 0, 0, 0, 1);
            AddEvent(db, R.string.program_koncert_koliber, 1035, 1065, R.string.program_day_2, R.string.program_opis_koncert1, 0, 0, 1);
            AddEvent(db, R.string.program_pokaz_nauka_liniowego, 1080, 1110, R.string.program_day_2, R.string.program_opis_country, R.drawable.nauka_liniowego, 0, 1);
            AddEvent(db, R.string.program_koncert_koliber, 1110, 1140, R.string.program_day_2, R.string.program_opis_koncert2, 0, 0, 1);
            AddEvent(db, R.string.program_gala_disco, 1170, 1320, R.string.program_day_2, R.string.program_opis_gala_disco, 0, 0, 1);
            AddEvent(db, R.string.program_kadryl_pochodnie, 1260, 1290, R.string.program_day_2, R.string.program_opis_kadryl, 0, 2, 2);
            AddEvent(db, R.string.program_fontanny, 1320, 1350, R.string.program_day_2, R.string.program_opis_fontanny, R.drawable.fontanna, 2, 4);
            AddEvent(db, R.string.program_dyskoteka, 1350, 0, R.string.program_day_2,0, 0, 0, 1);
            AddEvent(db, R.string.program_rozpoczecie, 600, 615, R.string.program_day_3, 0, 0, 0, 1);
            AddEvent(db, R.string.program_blok_konkursow_zabaw, 615, 660, R.string.program_day_3, 0, 0, 0, 1);
            AddEvent(db, R.string.program_indian, 660, 690, R.string.program_day_3, R.string.program_opis_indian, R.drawable.taniec_indian, 0, 1);
            AddEvent(db, R.string.program_zywa_historia, 660, 690, R.string.program_day_3, 0, 0, 0, 3);
            AddEvent(db, R.string.program_brzeszczykiewycz_krejzolka, 690, 720, R.string.program_day_3, 0, 0, 0, 1);
            AddEvent(db, R.string.program_country, 720, 750, R.string.program_day_3, R.string.program_opis_country, R.drawable.taniec_country, 0, 1);
            AddEvent(db, R.string.program_kadryl, 750, 780, R.string.program_day_3, R.string.program_opis_kadryl, R.drawable.kadryl, 0, 2);
            AddEvent(db, R.string.program_brzeszczykiewicz, 780, 825, R.string.program_day_3, 0, 0, 0, 1);
            AddEvent(db, R.string.program_zywa_historia, 810, 840, R.string.program_day_3, 0, 0, 0, 3);
            AddEvent(db, R.string.program_kabaret, 825, 855, R.string.program_day_3, R.string.program_opis_kabaret1, 0, 0, 1);
            AddEvent(db, R.string.program_koncert_andrzej_przyjaciele, 870, 900, R.string.program_day_3, 0, 0, 0, 1);
            AddEvent(db, R.string.program_parada, 900, 945, R.string.program_day_3, 0, R.drawable.parada, 0, 0);
            AddEvent(db, R.string.program_iluzja, 945, 990, R.string.program_day_3, 0, 0, 0, 1);
            AddEvent(db, R.string.program_kadryl, 990, 1020, R.string.program_day_3, R.string.program_opis_kadryl, R.drawable.kadryl, 0, 2);
            AddEvent(db, R.string.program_koncert_maskotki, 1020, 1050, R.string.program_day_3, R.string.program_opis_koncert1, 0, 0, 1);
            AddEvent(db, R.string.program_pokaz_nauka_liniowego, 1050, 1080, R.string.program_day_3, R.string.program_opis_country, R.drawable.nauka_liniowego, 0, 1);
            AddEvent(db, R.string.program_koncert_maskotki, 1080, 1110, R.string.program_day_3, R.string.program_opis_koncert2, 0, 0, 1);
            AddEvent(db, R.string.program_kadryl, 1110, 1140, R.string.program_day_3, R.string.program_opis_kadryl,R.drawable.kadryl, 0, 2);
            AddEvent(db, R.string.program_zywa_historia, 1140, 1170, R.string.program_day_3, 0, 0, 0, 3);
            AddEvent(db, R.string.program_kabaret, 1140, 1185, R.string.program_day_3, R.string.program_opis_kabaret2, 0, 0, 1);
            AddEvent(db, R.string.program_gwiazda, 1200, 1290, R.string.program_day_3, R.string.program_opis_gwiazda, 0, 2, 1);
            AddEvent(db, R.string.program_fajerwerki, 1320, -1, R.string.program_day_3, R.string.program_opis_fajerwerki, R.drawable.fajerwerki, 0, 0);

            db.execSQL("CREATE TABLE PLACES (_id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "NAME INTEGER,"
                    + "DESCRIPTION INTEGER,"
                    + "IMAGE_RSC INTEGER, "
                    + "TYPE INTEGER,"
                    + "Y REAL,"
                    + "X REAL,"
                    + "FAVORITE INTEGER)");
            AddPlace(db, R.string.mapa_title_scena, R.string.mapa_snippet_scena, R.drawable.scena,R.string.mapa_type_other,50.596402f,21.099829f,0);
            AddPlace(db, R.string.mapa_title_fosa,0,R. drawable.fosa, R.string.mapa_type_other, 50.596834f, 21.100361f, 0);
            AddPlace(db, R.string.mapa_title_ujezdzalnia, 0, R.drawable.ujezdzalnia, R.string.mapa_type_event_attractions, 50.596187f, 21.102068f, 0);
            AddPlace(db, R.string.program_fontanny, R.string.program_opis_fontanny, R.drawable.fontanna, R.string.mapa_type_event_attractions, 50.596400f, 21.100566f, 0);
            AddPlace(db, R.string.mapa_title_pizzeria, R.string.mapa_snippet_pizzeria, R.drawable.pizzeria, R.string.mapa_type_eat, 50.597148f, 21.100830f, 2);
            AddPlace(db, R.string.mapa_title_grill, R.string.mapa_snippet_grill, R.drawable.wiata, R.string.mapa_type_eat, 50.595793f, 21.100779f, 0);
            AddPlace(db, 0, R.string.mapa_snippet_lunapark, R.drawable.lunapark, R.string.mapa_type_lunapark, 50.595837f, 21.098673f, 0);
            AddPlace(db, 0, R.string.mapa_snippet_lunapark, R.drawable.lunapark, R.string.mapa_type_lunapark, 50.596138f, 21.099498f, 0);
            AddPlace(db, 0, R.string.mapa_snippet_lunapark, R.drawable.lunapark, R.string.mapa_type_lunapark, 50.596235f, 21.099004f, 0);
            AddPlace(db, R.string.mapa_title_oranzeria, R.string.mapa_snippet_oranzeria, R.drawable.oranzeria , R.string.mapa_type_eat, 50.596852f, 21.099843f, 0);
            AddPlace(db, R.string.mapa_title_taras, R.string.mapa_snippet_taras, R.drawable.taras, R.string.mapa_type_eat, 50.597070f, 21.100407f, 0);
            AddPlace(db, R.string.mapa_title_labirynt, R.string.mapa_snippet_labirynt, R.drawable.labirynt, R.string.mapa_type_event_attractions, 50.595001f, 21.101540f, 2);
            AddPlace(db, R.string.mapa_title_plansza_golf, 0, 0, R.string.mapa_type_fun, 50.595278f, 21.100963f, 2);
            AddPlace(db, R.string.mapa_title_camping, R.string.mapa_snippet_camping, R.drawable.camping, R.string.mapa_type_other, 50.598213f, 21.101935f, 2);

            db.execSQL("CREATE TABLE PLACESOTHER (_id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "NAME INTEGERPLACESOTHER,"
                    + "DESCRIPTION INTEGER,"
                    + "TYPE INTEGER,"
                    + "Y REAL,"
                    + "X REAL)");
            AddPlaceOther(db, R.string.mapa_other_title_tur,0, R.string.mapa_type_zoo, 50.597338f, 21.098513f);
            AddPlaceOther(db, R.string.mapa_other_title_strus, 0 , R.string.mapa_type_zoo, 50.596267f, 21.101448f);
            AddPlaceOther(db, R.string.mapa_other_title_kasa, R.string.mapa_other_snippet_kasa, R.string.mapa_type_other, 50.595021f, 21.096958f);
            AddPlaceOther(db, R.string.mapa_other_title_indian, 0, R.string.mapa_type_event_attractions, 50.595917f, 21.099584f);
            AddPlaceOther(db, R.string.mapa_other_title_traper, 0 , R.string.mapa_type_event_attractions, 50.595959f, 21.099723f);
            AddPlaceOther(db, R.string.mapa_other_title_zloto, 0 , R.string.mapa_type_event_attractions, 50.596046f, 21.099889f);
            AddPlaceOther(db, 0, 0, R.string.mapa_type_toalety, 50.595840f, 21.099780f);
            AddPlaceOther(db, R.string.mapa_other_title_mustang, 0, R.string.mapa_type_other, 50.594911f, 21.100620f);
            AddPlaceOther(db, R.string.mapa_other_title_byk, 0, R.string.mapa_type_fun, 50.595881f, 21.101317f);
            AddPlaceOther(db, R.string.mapa_other_title_kucyki, 0, R.string.mapa_type_fun, 50.596761f, 21.101888f);
            AddPlaceOther(db, 0, 0, R.string.mapa_type_toalety, 50.595348f, 21.101185f);
            AddPlaceOther(db, R.string.mapa_other_title_plac_zabaw, 0, R.string.mapa_type_fun, 50.595967f, 21.100478f);
            AddPlaceOther(db, 0, 0, R.string.mapa_type_toalety, 50.596549f, 21.101187f);
            AddPlaceOther(db, 0, 0, R.string.mapa_type_info, 50.596295f, 21.099902f);
            AddPlaceOther(db, 0, 0, R.string.mapa_type_info, 50.595028f, 21.096836f);
            AddPlaceOther(db, 0, 0, R.string.mapa_type_toalety, 50.596603f, 21.099548f);
            AddPlaceOther(db, 0, 0, R.string.mapa_type_toalety, 50.597169f, 21.099762f);
            AddPlaceOther(db, 0, 0 ,R.string.mapa_type_medical, 50.597089f, 21.099852f);
            AddPlaceOther(db, 0, 0, R.string.mapa_type_zoo, 50.596691f, 21.102447f);
            AddPlaceOther(db, 0, 0, R.string.mapa_type_zoo, 50.596556f, 21.102940f);
            AddPlaceOther(db, 0, 0, R.string.mapa_type_zoo, 50.595846f, 21.102464f);
            AddPlaceOther(db, 0, 0, R.string.mapa_type_zoo, 50.595747f, 21.101741f);
            AddPlaceOther(db, 0, 0, R.string.mapa_type_zoo, 50.596386f, 21.104450f);
            AddPlaceOther(db, R.string.mapa_other_title_bizon, 0, R.string.mapa_type_zoo, 50.597336f, 21.104399f);
            AddPlaceOther(db, R.string.mapa_other_title_kasa, R.string.mapa_other_snippet_kasa2, R.string.mapa_type_other, 50.596247f, 21.099975f);
            AddPlaceOther(db, 0, 0, R.string.mapa_type_parking, 50.593033f, 21.097020f);
            AddPlaceOther(db, 0, 0, R.string.mapa_type_parking, 50.594495f, 21.097006f);
            AddPlaceOther(db, 0, 0, R.string.mapa_type_parking, 50.595188f, 21.094055f);
            AddPlaceOther(db, R.string.mapa_other_title_punkt_przyjecia, 0, R.string.mapa_type_bezpieczeństwo, 50.597591f, 21.099582f);
            AddPlaceOther(db, R.string.mapa_other_title_miejsce_zbiorki, 0, R.string.mapa_type_bezpieczeństwo, 50.597762f, 21.099695f);
            AddPlaceOther(db, R.string.mapa_other_title_hydrant, 0, R.string.mapa_type_bezpieczeństwo, 50.596862f, 21.099526f);
            AddPlaceOther(db, R.string.mapa_other_title_gasnica, 0, R.string.mapa_type_bezpieczeństwo, 50.597155f, 21.100341f);
            AddPlaceOther(db, R.string.mapa_other_title_gasnica, 0, R.string.mapa_type_bezpieczeństwo, 50.596824f, 21.099686f);
            AddPlaceOther(db, 0, 0, R.string.mapa_type_eat, 50.596148f, 21.099644f);
            AddPlaceOther(db, 0, 0, R.string.mapa_type_eat, 50.596558f, 21.100330f);
        }

        if(oldVersion < 2){
            ContentValues placeValue = new ContentValues();
            placeValue.put("IMAGE_RSC", R.drawable.zz_wielkie_gry);

            db.update("PLACES",
                    placeValue,
                    "_id = ?",
                    new String[] {Integer.toString(13)});
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
