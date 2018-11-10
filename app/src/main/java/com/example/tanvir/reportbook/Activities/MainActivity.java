package com.example.tanvir.reportbook.Activities;

import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import com.example.tanvir.reportbook.Fragments.MonthlyFragment;
import com.example.tanvir.reportbook.Fragments.TodayFragment;
import com.example.tanvir.reportbook.R;

public class MainActivity extends AppCompatActivity implements TodayFragment.OnFragmentInteractionListener,MonthlyFragment.OnFragmentInteractionListener {
    EditText nameEt,emailEt,phoneEt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TabLayout tabLayout = (TabLayout)findViewById(R.id.tablayoutId);
        tabLayout.addTab(tabLayout.newTab().setText("Daily"));
        tabLayout.addTab(tabLayout.newTab().setText("Monthly"));
        tabLayout.addTab(tabLayout.newTab().setText("Yearly"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = findViewById(R.id.viewpagerId);
        PagerAdapter pagerAdapter = new com.example.tanvir.reportbook.Adapters.PagerAdapter(getSupportFragmentManager(),tabLayout.getTabCount());
        viewPager.setAdapter(pagerAdapter);
        viewPager.setOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }
}
