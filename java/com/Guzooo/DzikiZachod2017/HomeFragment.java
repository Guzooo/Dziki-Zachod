package com.Guzooo.DzikiZachod2017;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;
import android.widget.VideoView;

public class HomeFragment extends Fragment {

    private final String PREFERENCE_VIDEO_START = "videostart";

    private SQLiteDatabase db;

    private PlaceCardAdapter adapterPlacesFavorite;
    private ProgramCardAdapter adapterEventsFavorite;
    private PlaceCardAdapter adapterPlacesRecomended;
    private ProgramCardAdapter adapterEventsRecomended;
    private Cursor cursorPlacesFavorite;
    private Cursor cursorEventsFavorite;
    private Cursor cursorPlacesRecomended;
    private Cursor cursorEventsRecomended;

    private View layout;

    public HomeFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        layout = inflater.inflate(R.layout.fragment_home, container, false);

        SharedPreferences sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);

        if(sharedPreferences.getInt(PREFERENCE_VIDEO_START, 0) == 0){
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt(PREFERENCE_VIDEO_START, 1);
            editor.apply();

            VideoView videoView = layout.findViewById(R.id.home_video);
            final View contener = layout.findViewById(R.id.home_video_contener);

            Animation wejscie = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.wejscie);

            Uri uri = Uri.parse("android.resource://com.Guzooo.DzikiZachod2017/" + R.raw.anim_logo);
            videoView.setVideoURI(uri);

            contener.startAnimation(wejscie);

            contener.setVisibility(View.VISIBLE);
            videoView.start();
            videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    contener.setVisibility(View.GONE);
                }
            });
        }

        return layout;
    }

    @Override
    public void onStart() {
        super.onStart();

        RecyclerView eventsFavorite = layout.findViewById(R.id.home_events_favorite_recycler);
        RecyclerView placesFavorite = layout.findViewById(R.id.home_places_favorite_recycler);
        RecyclerView eventsRecomended = layout.findViewById(R.id.home_events_recomended_recycler);
        RecyclerView placesRecomended = layout.findViewById(R.id.home_places_recomended_recycler);

        try {
            SQLiteOpenHelper openHelper = new ProgramHelper(getActivity());
            db = openHelper.getReadableDatabase();

            cursorEventsFavorite = db.query("EVENTS",
                    new String[]{"_id", "NAME", "TIME_START", "TIME_END", "DAY"},
                    "FAVORITE = ? OR FAVORITE = ?",
                    new String[]{Integer.toString(1), Integer.toString(3)},
                    null, null,
                    "DAY, TIME_START, TIME_END,  NAME");
            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
            eventsFavorite.setLayoutManager(layoutManager);
            adapterEventsFavorite = new ProgramCardAdapter(cursorEventsFavorite, layout.findViewById(R.id.home_events_favorite_null));
            eventsFavorite.setAdapter(adapterEventsFavorite);

            adapterEventsFavorite.setListener(new ProgramCardAdapter.Listener() {
                @Override
                public void onClick(int id) {
                    Intent intent = new Intent(getActivity(), WydarzenieActivity.class);
                    intent.putExtra(WydarzenieActivity.EXTRA_ID, id);
                    getActivity().startActivity(intent);
                }
            });

            cursorPlacesFavorite = db.query("PLACES",
                    new String[]{"_id", "NAME", "IMAGE_RSC", "TYPE"},
                    "FAVORITE = ? OR FAVORITE = ?",
                    new String[]{Integer.toString(1), Integer.toString(3)},
                    null, null,
                    "NAME");
            layoutManager = new GridLayoutManager(getActivity(), 3);
            placesFavorite.setLayoutManager(layoutManager);
            adapterPlacesFavorite = new PlaceCardAdapter(cursorPlacesFavorite, layout.findViewById(R.id.home_places_favorite_null));
            placesFavorite.setAdapter(adapterPlacesFavorite);

            adapterPlacesFavorite.setListener(new PlaceCardAdapter.Listener() {
                @Override
                public void onClick(int id) {
                    Intent intent = new Intent(getActivity(), PlaceActivity.class);
                    intent.putExtra(PlaceActivity.EXTRA_ID, id);
                    getActivity().startActivity(intent);
                }
            });

            cursorEventsRecomended = db.query("EVENTS",
                    new String[]{"_id", "NAME", "TIME_START", "TIME_END", "DAY"},
                    "FAVORITE = ? OR FAVORITE = ?",
                    new String[]{Integer.toString(2), Integer.toString(3)},
                    null, null,
                    "DAY, TIME_START, TIME_END,  NAME");
            layoutManager = new LinearLayoutManager(getActivity());
            eventsRecomended.setLayoutManager(layoutManager);
            adapterEventsRecomended = new ProgramCardAdapter(cursorEventsRecomended, layout.findViewById(R.id.home_events_recomended_null));
            eventsRecomended.setAdapter(adapterEventsRecomended);

            adapterEventsRecomended.setListener(new ProgramCardAdapter.Listener() {
                @Override
                public void onClick(int id) {
                    Intent intent = new Intent(getActivity(), WydarzenieActivity.class);
                    intent.putExtra(WydarzenieActivity.EXTRA_ID, id);
                    getActivity().startActivity(intent);
                }
            });


            cursorPlacesRecomended = db.query("PLACES",
                    new String[]{"_id", "NAME", "IMAGE_RSC", "TYPE"},
                    "FAVORITE = ? OR FAVORITE = ?",
                    new String[]{Integer.toString(2), Integer.toString(3)},
                    null, null,
                    "NAME");
            layoutManager = new GridLayoutManager(getActivity(), 2);
            placesRecomended.setLayoutManager(layoutManager);
            adapterPlacesRecomended = new PlaceCardAdapter(cursorPlacesRecomended, layout.findViewById(R.id.home_places_recomended_null));
            placesRecomended.setAdapter(adapterPlacesRecomended);

            adapterPlacesRecomended.setListener(new PlaceCardAdapter.Listener() {
                @Override
                public void onClick(int id) {
                    Intent intent = new Intent(getActivity(), PlaceActivity.class);
                    intent.putExtra(PlaceActivity.EXTRA_ID, id);
                    getActivity().startActivity(intent);
                }
            });

        } catch (SQLiteException e) {
            Toast.makeText(getActivity(), R.string.error_read_database, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        adapterEventsFavorite.CloseCursor();
        adapterPlacesFavorite.CloseCursor();
        adapterEventsRecomended.CloseCursor();
        adapterPlacesRecomended.CloseCursor();
        cursorEventsFavorite.close();
        cursorPlacesFavorite.close();
        cursorEventsRecomended.close();
        cursorPlacesRecomended.close();
        db.close();
    }
}
