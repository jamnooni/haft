package com.example.me.haft;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;

public class WorkoutActivity extends FragmentActivity {


    /**
     * The pager widget, which handles animation and allows swiping
     * horizontally to access previous and next wizard steps.
     */
    private ViewPager mPager;

    /**
     * The pager adapter, which provides the pages to the view pager widget.
     */
    private PagerAdapter mPagerAdapter;
    private TextView timerTextView;
    private CountDownTimerPausable timer;

    private Integer dExerciseTime=30000;
    private Integer dRestTime=15000;

    MyDBHandler dbHandler;
    Exercises exercises;

    int finnishedTimes=0;
    View completedDialogLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.workout_screen_slide);


        Log.d("debug","workoutactivity.java>>new handler created");
        dbHandler=new MyDBHandler(this,null,null,1);
        try {
            dbHandler.createDataBase();
            dbHandler.openDataBase();

        }
        catch (IOException e){
            e.printStackTrace();
        }

        String workoutName=getIntent().getStringExtra("workout set");
        exercises=dbHandler.getWorkoutExercises(workoutName);

        // Instantiate a ViewPager and a PagerAdapter.
        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setPageTransformer(true, new DepthPageTransformer());

        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);
        mPager.addOnPageChangeListener(new PagerChangeListener());

        timerTextView = (TextView) findViewById(R.id.timer_text_view);
        timerTextView.setText("" + 10);


        completedDialogLayout=findViewById(R.id.completed_dialog);
        Button restartBtn= (Button) findViewById(R.id.restart_button);
        Button doneBtn=(Button)findViewById(R.id.done_button);

        restartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                restartWorkout();
            }
        });
        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finnishWorkout();
            }
        });


        timer = new CountDownTimerPausable(dExerciseTime+1, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timerTextView.setText("" + (Math.round(millisUntilFinished*0.001f)-1));
                Log.d("sec","second"+millisUntilFinished);
            }

            @Override
            public void onFinish() {
                Handler handler=new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if(mPager.getCurrentItem()+1<mPagerAdapter.getCount()){
                            mPager.setCurrentItem(mPager.getCurrentItem()+1);
                        }
                        else {
                            completedDialogLayout.setVisibility(View.VISIBLE);
                            finnishedTimes++;
                        }
                    }
                },1500);
            }
        }.start();

        findViewById(R.id.pause_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timer.resumePause();
            }
        });





    }

    private void restartWorkout() {
        completedDialogLayout.setVisibility(View.GONE);
        mPager.setCurrentItem(0);
    }

    private void finnishWorkout() {
        Intent returnIntent = new Intent();
        returnIntent.putExtra("result",finnishedTimes);
        setResult(Activity.RESULT_OK,returnIntent);
        finish();
    }

    public class PagerChangeListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }
        @Override
        public void onPageScrollStateChanged(int state) {
        }
        @Override
        public void onPageSelected(int position) {
            if (position%2==0) {
                timerTextView.setText("" + dExerciseTime/1000);
                timer.reset(dExerciseTime+1);
            }
            else{
                timerTextView.setText("" + dRestTime/1000);
                timer.reset(dRestTime+1);
            }
            if (!timer.isPaused()) {
                timer.start();
            }
        }


    }


    /*@Override
    public void onBackPressed() {
        if (mPager.getCurrentItem() == 0) {
            // If the user is currently looking at the first step, allow the system
            // to handle the Back button. This calls finish() on this activity and
            // pops the back stack.
            super.onBackPressed();
        } else {
            // Otherwise, select the previous step.
            mPager.setCurrentItem(mPager.getCurrentItem() - 1);
        }
    }*/

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {

        //number of pages
        private static final int NUM_PAGES = 10;


        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Workout workout=exercises.get(Math.round((position+1)/2));

            Bundle args = new Bundle();
            args.putString("name", workout.getName());
            args.putString("description",workout.getDescription());

            if(position%2==0){
                args.putBoolean("isRest",false);
            }
            else{
                args.putBoolean("isRest",true);
            }

            WorkoutSlidePageFragment fragment = new WorkoutSlidePageFragment();
            fragment.setArguments(args);




            return fragment;
        }

        @Override
        public int getCount() {
            return (2*exercises.size()-1);

        }
    }


}
//TODO: design finnishworkout and block cheating
//TODO: design starting countdown

