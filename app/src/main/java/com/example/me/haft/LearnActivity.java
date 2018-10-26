package com.example.me.haft;


import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;
import android.widget.Toast;

import java.io.IOException;

public class LearnActivity extends AppCompatActivity {

    int workoutSetIndex=0;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.learn_activity_layout);
        workoutSetIndex=getIntent().getIntExtra("workoutSet_index",0);

        WorkoutDBHandler workoutDBH=new WorkoutDBHandler(this,null,null,1);
        try {
            workoutDBH.createDataBase();
            workoutDBH.openDataBase();

        }
        catch (IOException e){
            e.printStackTrace();
        }

        final WorkoutSet workoutSet=workoutDBH.getWorkoutSet(workoutSetIndex);


        String[] names = workoutSet.getNames();
        int[] iconIds=workoutSet.getIconIds();

        LearnFragment learnFragment= (LearnFragment)
                getFragmentManager().findFragmentById(R.id.fragment);

        learnFragment.setGridViewData(names,iconIds);


        learnFragment.setItemClickListener(new LearnFragment.OnGridItemClickListener() {
            @Override
            public void onGridItemClicked(int position) {
                LearnPagerFragment pagerFragment=new LearnPagerFragment(workoutSet,position);

                FragmentTransaction trans=getSupportFragmentManager().beginTransaction();
                trans.replace(R.id.container,pagerFragment);
                trans.addToBackStack(null);
                trans.commit();

            }
        });
    }


}

//TODO: remove fragments constructors if possible


