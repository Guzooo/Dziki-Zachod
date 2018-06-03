package com.Guzooo.DzikiZachod2017;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class StartActivity extends Activity implements ActionBar.TabListener {

    private SectionsPagerAdapter sectionsPagerAdapter;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);


        sectionsPagerAdapter = new SectionsPagerAdapter(getFragmentManager());
        viewPager = findViewById(R.id.startViewPager);
        viewPager.setAdapter(sectionsPagerAdapter);

        final ActionBar actionBar = getActionBar();
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
        }
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction){
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction){

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction){

    }

    public static class PlaceholderFragmnent extends Fragment{
        private static final String  ARG_NUMER = "section_number";

        public static PlaceholderFragmnent newInstance (int sectionNumber){
            PlaceholderFragmnent fragmnent = new PlaceholderFragmnent();
            Bundle args = new Bundle();
            args.putInt(ARG_NUMER, sectionNumber);
            fragmnent.setArguments(args);
            return fragmnent;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup constainer, Bundle savedInstanceState){
            switch (getArguments().getInt(ARG_NUMER)){
                case 0:
                    return inflater.inflate(R.layout.fragment_home,constainer,false);
            }
            return null;
        }
    }

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
            return 7;
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
                case 3:
                    return "" + getString(R.string.fragment_bilety_name);
                case 4:
                    return "" + getString(R.string.fragment_gwiazdy_szeryfa_name);
                case 5:
                    return "" + getString(R.string.fragment_spolecznosc_name);
                case 6:
                    return "" + getString(R.string.fragment_info_name);
            }
            return null;
        }
    }
}
