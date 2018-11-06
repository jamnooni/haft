package com.example.me.haft;


import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;
import android.support.v4.app.FragmentActivity;

import java.io.IOException;

public class LearnActivity extends AppCompatActivity {

    int workoutSetIndex=0;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.learn_activity_layout);

        //gets selected workoutSet index from previous activity
        workoutSetIndex=getIntent().getIntExtra("workoutSet_index",0);

        //calls workout database
        WorkoutDBHandler workoutDBH=new WorkoutDBHandler(this,null,null,1);
        try {
            workoutDBH.createDataBase();
            workoutDBH.openDataBase();

        }
        catch (IOException e){
            e.printStackTrace();
        }

        //gets workoutSet from database
        final WorkoutSet workoutSet=workoutDBH.getWorkoutSet(workoutSetIndex);


        String[] names = workoutSet.getNames();
        int[] iconIds=workoutSet.getIconIds();
        Bundle args=new Bundle();
        args.putStringArray("workoutSets names",names);
        args.putIntArray("workoutSets iconIds",iconIds);

        LearnFragment learnFragment=new LearnFragment();
        learnFragment.setArguments(args);


        final FragmentTransaction trans=getSupportFragmentManager().beginTransaction();
        trans.add(R.id.container,learnFragment).commit();




        //gets fragment view and casts it to learnFragment
/*        LearnFragment learnFragment= (LearnFragment)
                getFragmentManager().findFragmentById(R.id.fragment);

        //sets adapter for the gridView in the fragment's layout and determines the gridView's
        //OnClickListener.onClick() to run the onGridItemClicked() method of the instance of
        //OnGridItemClickListener interface if the instance isn't null;
        learnFragment.setGridViewData(names,iconIds);*/


        //sets the instance of OnGridItemClickListener in learnFragment. onGridItemClicked is a
        //method of interface and should be overridden. here it is determined to create a new
        //instance of LearnPagerFragment and pass the workoutSet called before and selected
        //position to it. then it replace the learnFragment by the new LearnPagerFragment;
        learnFragment.setItemClickListener(new LearnFragment.OnGridItemClickListener() {
            @Override
            public void onGridItemClicked(int position) {
                LearnPagerFragment pagerFragment=new LearnPagerFragment(workoutSet,position);

                final FragmentTransaction trans=getSupportFragmentManager().beginTransaction();
                trans.replace(R.id.container,pagerFragment);
                trans.addToBackStack(null);
                trans.commit();

            }
        });
    }


}

//TODO: remove fragments constructors if possible
//TODO: reduce fragments recreation



