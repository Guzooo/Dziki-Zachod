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

    private RecyclerView programRecycle;
    private ProgramCardAdapter adapter;

    private SQLiteDatabase db;
    private Cursor cursor;

    private Button btnOld;

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

        if(cursor == null) {
            onClickPiatek(btnPiatek);
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

            btnOld.setTextColor(getResources().getColor(R.color.pressedText));
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

    public void  onClickPiatek(View v){
        ReadingDatabase(R.string.program_day_1);
        ResetButtons((Button) v);
    }

    public void  onClickSobota(View v){
        ReadingDatabase(R.string.program_day_2);
        ResetButtons((Button) v);
    }

    public void  onClickNiedziela(View v){
        ReadingDatabase(R.string.program_day_3);
        ResetButtons((Button) v);
    }

    private void ResetButtons(Button b){
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
    public void onDestroy() {
        super.onDestroy();

        if(adapter != null) {
            adapter.CloseCursor();
            cursor.close();
            db.close();
        }
    }
}
