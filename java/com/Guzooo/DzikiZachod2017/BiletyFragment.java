package com.Guzooo.DzikiZachod2017;

import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class BiletyFragment extends Fragment {



    public BiletyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_bilety, container, false);

        String[] titles = new String[] {
                getString(R.string.program_day_1),
                getString(R.string.program_day_2),
                getString(R.string.program_day_3),
                getString(R.string.bilety_lochy),
                getString(R.string.bilety_palac),
                getString(R.string.bilety_safari),
                getString(R.string.bilety_pakiet1),
                getString(R.string.bilety_labirynt),
                getString(R.string.bilety_pakiet2),
                getString(R.string.bilety_bukowy)};

        String[] normalPrices = new String[] {
                getString(R.string.bilety_normal_price, 4),
                getString(R.string.bilety_normal_price, 15),
                getString(R.string.bilety_normal_price, 15),
                getString(R.string.bilety_normal_price, 5),
                getString(R.string.bilety_normal_price, 9),
                getString(R.string.bilety_normal_price, 9),
                getString(R.string.bilety_normal_price, 20),
                getString(R.string.bilety_normal_price, 6),
                getString(R.string.bilety_normal_price, 25),
                ""};

        String[] reducedPrices = new String[] {
                getString(R.string.bilety_reduced_price, 3),
                getString(R.string.bilety_reduced_price, 10),
                getString(R.string.bilety_reduced_price, 10),
                getString(R.string.bilety_reduced_price, 4),
                getString(R.string.bilety_reduced_price, 8),
                getString(R.string.bilety_reduced_price, 8),
                getString(R.string.bilety_reduced_price, 16),
                getString(R.string.bilety_reduced_price, 4),
                getString(R.string.bilety_reduced_price, 19),
                getString(R.string.bilety_reduced_price, 3)};

        String[] description = new String[] {
                getString(R.string.bilety_day_info),
                getString(R.string.bilety_day_info),
                getString(R.string.bilety_day_info),
                getString(R.string.bilety_ulga_mlodziez),
                getString(R.string.bilety_ulga_mlodziez),
                getString(R.string.bilety_ulga_mlodziez),
                getString(R.string.bilety_ulga_senior),
                getString(R.string.bilety_ulga_senior),
                getString(R.string.bilety_ulga_senior),
                ""};

        RecyclerView recyclerView = layout.findViewById(R.id.bilety_recycler);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        BiletyCardAdapter adapter = new BiletyCardAdapter(titles, normalPrices, reducedPrices, description);
        recyclerView.setAdapter(adapter);

        return layout;
    }
}
