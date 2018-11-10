package com.example.tanvir.reportbook.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.tanvir.reportbook.Fragments.MonthlyFragment;
import com.example.tanvir.reportbook.Fragments.TodayFragment;

public class PagerAdapter extends FragmentStatePagerAdapter {
    int totalTab;

    public PagerAdapter(FragmentManager fm, int totalTab) {
        super(fm);
        this.totalTab = totalTab;
    }

    @Override
    public Fragment getItem(int i) {
       switch (i){
           case 0:
               TodayFragment todayFragment = new TodayFragment();
               return todayFragment;
           case 1:
               MonthlyFragment monthlyFragment = new MonthlyFragment();
               return monthlyFragment;
           case 2:
               TodayFragment todayFragment3 = new TodayFragment();
               return todayFragment3;
               default:
                   return null;
       }
    }

    @Override
    public int getCount() {
        return totalTab;
    }
}
