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

import java.util.Calendar;

public class ProgramFragment extends Fragment implements View.OnClickListener {

    private static final String BUNDLE_DAY = "day";

    private RecyclerView programRecycle;
    private ProgramCardAdapter adapter;
    private View nullCard;

    private SQLiteDatabase db;
    private Cursor cursor;

    private Button btnOld;
    private String title;

    public ProgramFragment() {

    }

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
        nullCard = layout.findViewById(R.id.program_null);

        if(savedInstanceState != null){
            title = savedInstanceState.getString(BUNDLE_DAY);
        } else if (title == null){
            Calendar c = Calendar.getInstance();
            int msc = c.get(Calendar.MONTH);
            if(msc == 7){
                int day = c.get(Calendar.DAY_OF_MONTH);
                if(day == 11){
                    title = getString(R.string.program_day_2);
                } else if(day == 12){
                    title = getString(R.string.program_day_3);
                } else {
                    title = getString(R.string.program_day_1);
                }
            } else {
                title = getString(R.string.program_day_1);
            }
        }

        if(cursor == null) {
            if (title.equals(getString(R.string.program_day_1))){
                btnPiatek.callOnClick();
            } else if (title.equals(getString(R.string.program_day_2))){
                btnSobota.callOnClick();
            } else if (title.equals(getString(R.string.program_day_3))){
                btnNiedziela.callOnClick();
            }
        } else {
            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
            programRecycle.setLayoutManager(layoutManager);
            adapter = new ProgramCardAdapter(cursor, nullCard);
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
        b.setTextColor(getResources().getColor(R.color.colorAccent));
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
                    "TIME_START, TIME_END, NAME");
            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
            programRecycle.setLayoutManager(layoutManager);
            adapter = new ProgramCardAdapter(cursor, nullCard);
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
            Toast.makeText(getActivity(), R.string.error_read_database,Toast.LENGTH_SHORT).show();
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
