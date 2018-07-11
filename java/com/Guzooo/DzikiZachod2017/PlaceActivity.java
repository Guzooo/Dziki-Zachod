package com.Guzooo.DzikiZachod2017;

import android.app.Activity;
import android.os.Bundle;

public class PlaceActivity extends Activity {

    public static final String EXTRA_ID = "id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place);
    }
}
