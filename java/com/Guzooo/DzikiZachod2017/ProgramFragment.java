package com.Guzooo.DzikiZachod2017;


import android.app.ActionBar;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ProgramFragment extends Fragment implements ActionBar.TabListener {

    private SectionsPagerAdapter sectionsPagerAdapter;
    private ViewPager viewPager;

    public ProgramFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_program, container, false);

        sectionsPagerAdapter = new SectionsPagerAdapter(getFragmentManager());
        viewPager = view.findViewById(R.id.programViewPager);
        viewPager.setAdapter(sectionsPagerAdapter);

        final ActionBar actionBar = getActivity().getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        for (int i = 0; i <sectionsPagerAdapter.getCount(); i++){
            actionBar.addTab(actionBar.newTab()
                    .setText(sectionsPagerAdapter.getPageTitle(i))
                    .setTabListener(this));
            Log.d("Program","Robie właśnie tab " + i);
        }

        return view;
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {

    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }

    public static class PlaceholderFragmnent extends Fragment{
        private static final String  ARG_NUMER = "section_number";

        public static ProgramFragment.PlaceholderFragmnent newInstance (int sectionNumber){
            Log.d("Program","buduje czesc 1");
            ProgramFragment.PlaceholderFragmnent fragmnent = new ProgramFragment.PlaceholderFragmnent();
            Bundle args = new Bundle();
            args.putInt(ARG_NUMER, sectionNumber);
            fragmnent.setArguments(args);
            return fragmnent;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup constainer, Bundle savedInstanceState){
            Log.d("Program","Część druga " + getArguments().getInt(ARG_NUMER));
            switch (getArguments().getInt(ARG_NUMER)){
                case 0:
                    return inflater.inflate(R.layout.fragment_home,constainer,false);
                case 1:
                    return inflater.inflate(R.layout.fragment_home,constainer,false);
                case 2:
                    return inflater.inflate(R.layout.fragment_home,constainer,false);

            }
            return null;
        }
    }
    //TODO: FragmentStatePagerAdapter gdy bedzię cieło
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public Fragment getItem(int position) {
            return PlaceholderFragmnent.newInstance(position);
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position){
            switch (position){
                case 0:
                    return "" + getString(R.string.fragment_home_name);
                case 1:
                    return "" + getString(R.string.fragment_mapa_name);
                case 2:
                    return "" + getString(R.string.fragment_program_name);
            }
            return null;
        }
    }
}
