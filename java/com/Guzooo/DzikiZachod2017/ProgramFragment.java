package com.Guzooo.DzikiZachod2017;

import android.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

public class ProgramFragment extends Fragment implements View.OnClickListener {

    private static final String BUNDLE_DAY = "day";

    private RecyclerView programRecycle;
    private ProgramCardAdapter adapter;

    private SQLiteDatabase db;
    private Cursor cursor;

    private Button btnOld;
    private String title;

    public ProgramFragment() {
        // Required empty public constructor
    }

    //TODO: Tytuł dnia zamiast nazwy aplikacji
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_program, container, false);

        Button btnPiatek = layout.findViewById(R.id.button_pt);
        Button btnSobota = layout.findViewById(R.id.button_sob);
        Button btnNiedziela = layout.findViewById(R.id.button_nd);

        btnPiatek.setOnClickListener(this);
        btnSobota.setOnClickListener(this);
        btnNiedziela.setOnClickListener(this);

        programRecycle = layout.findViewById(R.id.program_recycle);

        if(savedInstanceState != null){
            title = savedInstanceState.getString(BUNDLE_DAY);
        } else if (title == null){
            title = getString(R.string.program_day_1);
        }

        if(cursor == null) {
            if (title.equals(getString(R.string.program_day_1))){
                onClick(btnPiatek);
            } else if (title.equals(getString(R.string.program_day_2))){
                onClick(btnSobota);
            } else if (title.equals(getString(R.string.program_day_3))){
                onClick(btnNiedziela);
            }
        } else {
            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
            programRecycle.setLayoutManager(layoutManager);
            adapter = new ProgramCardAdapter(cursor);
            programRecycle.setAdapter(adapter);

            adapter.setListener(new ProgramCardAdapter.Listener() {
                @Override
                public void onClick(int id) {
                    Intent intent = new Intent(getActivity(), WydarzenieActivity.class);
                    intent.putExtra(WydarzenieActivity.EXTRA_ID, id);
                    getActivity().startActivity(intent);
                }
            });

            if (title.equals(getString(R.string.program_day_1))){
                MarkDay(btnPiatek);
            } else if (title.equals(getString(R.string.program_day_2))){
                MarkDay(btnSobota);
            } else if (title.equals(getString(R.string.program_day_3))){
                MarkDay(btnNiedziela);
            }
        }

        return layout;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_pt:
                onClickPiatek(v);
                break;
            case R.id.button_sob:
                onClickSobota(v);
                break;
            case R.id.button_nd:
                onClickNiedziela(v);
                break;
        }
    }

    public void onClickPiatek(View v){
        ReadingDatabase(R.string.program_day_1);
        MarkDay((Button) v);
        title = getString(R.string.program_day_1);
    }

    public void onClickSobota(View v){
        ReadingDatabase(R.string.program_day_2);
        MarkDay((Button) v);
        title = getString(R.string.program_day_2);
    }

    public void onClickNiedziela(View v){
        ReadingDatabase(R.string.program_day_3);
        MarkDay((Button) v);
        title = getString(R.string.program_day_3);
    }

    private void MarkDay(Button b){
        if(btnOld != null){
            btnOld.setTextColor(b.getTextColors());
        }
        b.setTextColor(getResources().getColor(R.color.pressedText));
        btnOld = b;
    }

    private void ReadingDatabase(int day){
        try {
            SQLiteOpenHelper openHelper = new ProgramHelper(getActivity());
            db = openHelper.getReadableDatabase();
            cursor = db.query("EVENTS",
                    new String[]{"_id", "NAME", "TIME_START", "TIME_END"},
                    "DAY = ?",
                    new String[] {Integer.toString(day)},
                    null, null,
                    "TIME_START, NAME");
            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
            programRecycle.setLayoutManager(layoutManager);
            adapter = new ProgramCardAdapter(cursor);
            programRecycle.setAdapter(adapter);

            adapter.setListener(new ProgramCardAdapter.Listener() {
                @Override
                public void onClick(int id) {
                    Intent intent = new Intent(getActivity(), WydarzenieActivity.class);
                    intent.putExtra(WydarzenieActivity.EXTRA_ID, id);
                    getActivity().startActivity(intent);
                }
            });
        }catch (SQLiteException e){
            Toast.makeText(getActivity(), "Baza danych jest niedostępna",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString(BUNDLE_DAY, title);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if(adapter != null) {
            adapter.CloseCursor();
            cursor.close();
            db.close();
        }
    }
}
