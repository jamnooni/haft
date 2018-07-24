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

public class MainActivity extends AppCompatActivity {

    private ViewPager mPager;

    /**
     * The pager adapter, which provides the pages to the view pager widget.
     */
    private PagerAdapter mPagerAdapter;

    public static String workoutSet="full body";


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


    }


    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {

        //number of pages
        private static final int NUM_PAGES = 100;



        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Bundle args = new Bundle();
            args.putInt("index", position);

            switch (position%3){
                case 0:
                    FirstPageFragment firstPageFragment=new FirstPageFragment();
                    firstPageFragment.setArguments(args);
                    Log.d("debug","mainactivity.java>> new fragment created");
                    return firstPageFragment;
                case 1:
                    LearnPageFragment learnPageFragment=new LearnPageFragment();
                    learnPageFragment.setArguments(args);
                    Log.d("debug","mainactivity.java>> new fragment created");
                    return learnPageFragment;
                case 2:
                    TrackPageFragment trackPageFragment=new TrackPageFragment();
                    trackPageFragment.setArguments(args);
                    Log.d("debug","mainactivity.java>> new fragment created");
                    return trackPageFragment;

            }
/*            if (position%2==0){
                FirstPageFragment firstPageFragment=new FirstPageFragment();
                firstPageFragment.setArguments(args);
                Log.d("debug","mainactivity.java>> new fragment created");
                return firstPageFragment;
            }
            else{
                LearnPageFragment learnPageFragment=new LearnPageFragment();
                learnPageFragment.setArguments(args);
                Log.d("debug","mainactivity.java>> new fragment created");
                return learnPageFragment;
            }*/
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
                int result = data.getIntExtra("result", 0);
                Log.d("debug","firstpagefragment.java>>finnished time="+result);
            }
        }
        mPager.setCurrentItem(0);
    }

}
