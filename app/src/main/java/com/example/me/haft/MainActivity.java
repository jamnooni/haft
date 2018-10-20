package com.example.me.haft;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.IOException;
import java.util.Date;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private ViewPager mPager;

    /**
     * The pager adapter, which provides the pages to the view pager widget.
     */
    private PagerAdapter mPagerAdapter;

    public static String workoutSet="full body";
    StatsDBHandler statsDBH;

    enum DaysOfWeek {Sun,Mon,Tue,Wed,Thu,Fri,Sat}
    Calendar calendar;
    int[] lineChartY=new int[7];
    String[] strDaysOfWeek =new String[7];



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Instantiate a ViewPager and a PagerAdapter.
        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setPageTransformer(true, new DepthPageTransformer());

        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);

        //Workouts workouts=new Workouts();

        Log.d("debug","statspagefragment.java>>"+"create new statsdbhandler");
        statsDBH=new StatsDBHandler(this,null,null,1);
        try {
            statsDBH.createDataBase();
            statsDBH.openDataBase();
        }
        catch (IOException e){
            e.printStackTrace();
        }

        prepareChartsData();


    }


    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {

        //number of pages
        private static final int NUM_PAGES = 100;



        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {


            switch (position%4){
                case 0:
                    FirstPageFragment firstPageFragment=new FirstPageFragment();
                    //firstPageFragment.setArguments(args);
                    //Log.d("debug","mainactivity.java>> new fragment created");
                    return firstPageFragment;
                case 1:
                    LearnPageFragment learnPageFragment=new LearnPageFragment();
                    //learnPageFragment.setArguments(args);
                    //Log.d("debug","mainactivity.java>> new fragment created");
                    return learnPageFragment;
                case 2:
                    TrackPageFragment trackPageFragment=new TrackPageFragment();

                    Bundle args = new Bundle();
                    args.putInt("index", position);
                    args.putIntArray("lineChartY",lineChartY);
                    args.putStringArray("strDaysOfWeek", strDaysOfWeek);
                    args.putFloat("pieChartPercent",18.5f);
                    trackPageFragment.setArguments(args);
                    //Log.d("debug","mainactivity.java>> new fragment created");
                    return trackPageFragment;
                case 3:
                    AchievementPageFragment achievementPageFragment=new AchievementPageFragment();
                    //achievementPageFragment.setArguments(args);
                    //Log.d("debug","mainactivity.java>> new fragment created");
                    return achievementPageFragment;
            }

            return null;
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                int count = data.getIntExtra("result", 0);
                String workouts="";
                for (int i=0;i<count;i++){
                    workouts+="1";
                }
                Log.d("debug","update stats, date="+new Date()+"  count="+count+
                "   workouts="+workouts);

                statsDBH.updateStats(new Date(),workouts);
                prepareChartsData();
            }
        }
        mPager.setCurrentItem(0);
    }

    private void prepareChartsData(){
        calendar=Calendar.getInstance();
        calendar.getTime();
        calendar.add(Calendar.DATE,-6);
        Date date;

        for (int i=0;i<7;i++){
            date=calendar.getTime();
            lineChartY[i]=statsDBH.getCircuitsCount(date);
            strDaysOfWeek[i]=DaysOfWeek.values()[calendar.get(Calendar.DAY_OF_WEEK)-1].name();
            calendar.add(Calendar.DATE,1);
        }
    }

}

//TODO:settings page, complete learn page & track page, activities of circuits, workouts & calendar
//TODO: Hearts & progress rules, complete database, design graphics