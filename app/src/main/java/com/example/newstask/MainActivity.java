package com.example.newstask;

import android.graphics.Color;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity implements TopHandlines.OnFragmentInteractionListener,Everthing.OnFragmentInteractionListener,SoucesNews.OnFragmentInteractionListener {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


//        Spannable Head=new SpannableString("Headlines");
//        Head.setSpan(new ForegroundColorSpan(Color.BLACK),0,Head.length(),Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//
//        Spannable Ever=new SpannableString("Everything");
//        Ever.setSpan(new ForegroundColorSpan(Color.BLACK),0,Ever.length(),Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//
//        Spannable Sport=new SpannableString("Sports");
//        Sport.setSpan(new ForegroundColorSpan(Color.BLACK),0,Sport.length(),Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        TabLayout tabLayout=(TabLayout)findViewById(R.id.tablayout);
        tabLayout.addTab(tabLayout.newTab().setText("Headlines"));
        tabLayout.addTab(tabLayout.newTab().setText("Everything"));
        tabLayout.addTab(tabLayout.newTab().setText("Sports"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);


        final ViewPager viewPager=findViewById(R.id.pager);
        com.example.newstask.PagerAdapter pagerAdapter=new com.example.newstask.PagerAdapter(getSupportFragmentManager(),tabLayout.getTabCount());
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

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
