package com.Guzooo.DzikiZachod2017;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

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

    //TODO: FragmentStatePagerAdapter gdy bedzię cieło
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    return new HomeFragment();
                case 1:
                    return new MapFragment();
                case 2:
                    return new ProgramFragment();
                case 3:
                    return new BiletyFragment();
                case 4:
                    return new GwiazdaFragment();
                case 5:
                    return new SpolecznoscFragment();
                case 6:
                    return new InfoFragment();
            }
            return null;
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
