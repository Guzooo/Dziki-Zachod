package com.Guzooo.DzikiZachod2017;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class HomeFragment extends Fragment {

    private SQLiteDatabase db;
    private Cursor cursor;
    private ProgramCardAdapter adapter;

    private View layout;

    public HomeFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        layout = inflater.inflate(R.layout.fragment_home, container, false);
        return layout;
    }

    @Override
    public void onResume() {
        super.onResume();

        RecyclerView eventsFavorite = layout.findViewById(R.id.home_events_favorite_recycler);

        try {
            SQLiteOpenHelper openHelper = new ProgramHelper(getActivity());
            db = openHelper.getReadableDatabase();
            cursor = db.query("EVENTS",
                    new String[]{"_id", "NAME", "TIME_START", "TIME_END", "DAY"},
                    "FAVORITE = ? AND (DAY = ? OR DAY = ? OR DAY = ?)",
                    new String[]{Integer.toString(1), Integer.toString(R.string.program_day_pt), Integer.toString(R.string.program_day_sob), Integer.toString(R.string.program_day_nd)},
                    null, null, null);
            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
            eventsFavorite.setLayoutManager(layoutManager);
            adapter = new ProgramCardAdapter(cursor);
            eventsFavorite.setAdapter(adapter);

            adapter.setListener(new ProgramCardAdapter.Listener() {
                @Override
                public void onClick(int id) {
                    Intent intent = new Intent(getActivity(), WydarzenieActivity.class);
                    intent.putExtra(WydarzenieActivity.EXTRA_ID, id);
                    getActivity().startActivity(intent);
                }
            });
        } catch (SQLiteException e) {
            Toast.makeText(getActivity(), "Baza danych jest niedostępna", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        adapter.CloseCursor();
        cursor.close();
        db.close();
    }
}
