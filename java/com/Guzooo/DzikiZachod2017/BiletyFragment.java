package com.Guzooo.DzikiZachod2017;

import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
                getString(R.string.bilety_safari),
                getString(R.string.bilety_palac),
                getString(R.string.bilety_lochy),
                getString(R.string.bilety_labirynt)};


        String[] normalPrices = new String[] {
                getString(R.string.bilety_normal_price, 4),
                getString(R.string.bilety_normal_price,12),
                getString(R.string.bilety_normal_price,12),
                getString(R.string.bilety_normal_price,9),
                getString(R.string.bilety_normal_price, 9),
                getString(R.string.bilety_normal_price,5),
                getString(R.string.bilety_normal_price,6)};

        String[] reducedPrices = new String[] {
                getString(R.string.bilety_reduced_price, 3),
                getString(R.string.bilety_reduced_price, 8),
                getString(R.string.bilety_reduced_price, 8),
                getString(R.string.bilety_reduced_price, 8),
                getString(R.string.bilety_reduced_price, 8),
                getString(R.string.bilety_reduced_price, 4),
                getString(R.string.bilety_reduced_price, 4)};

        RecyclerView recyclerView = layout.findViewById(R.id.bilety_recycler);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        BiletyCardAdapter adapter = new BiletyCardAdapter(titles, normalPrices, reducedPrices);
        recyclerView.setAdapter(adapter);

        return layout;
    }
}
