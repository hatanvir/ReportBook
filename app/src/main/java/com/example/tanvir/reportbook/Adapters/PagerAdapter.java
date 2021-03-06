package com.example.tanvir.reportbook.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.tanvir.reportbook.Fragments.MonthlyFragment;
import com.example.tanvir.reportbook.Fragments.TodayFragment;
import com.example.tanvir.reportbook.Fragments.YearlyFragment;

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
               YearlyFragment yearlyFragment = new YearlyFragment();
               return yearlyFragment;
               default:
                   return null;
       }
    }

    @Override
    public int getCount() {
        return totalTab;
    }
}
