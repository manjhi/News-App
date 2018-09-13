package com.example.newstask;

import android.support.v4.app.FragmentStatePagerAdapter;

public class PagerAdapter extends FragmentStatePagerAdapter {

    int noOfTabs;


    public PagerAdapter(android.support.v4.app.FragmentManager fm, int noOfTabs) {
        super(fm);
        this.noOfTabs = noOfTabs;
    }

    @Override
    public android.support.v4.app.Fragment getItem(int position) {
        switch (position){
            case 0:
                TopHandlines topHandlines=new TopHandlines();
                return topHandlines;
            case 1:
                Everthing everthing=new Everthing();
                return everthing;
            case 2:
                SoucesNews soucesNews=new SoucesNews();
                return soucesNews;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return noOfTabs;
    }
}