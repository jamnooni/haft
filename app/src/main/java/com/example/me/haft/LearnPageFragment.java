package com.example.me.haft;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import java.io.IOException;


public class LearnPageFragment extends Fragment {

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container
            , @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.learn_page, container, false);

        GridView gridView= (GridView) rootView.findViewById(R.id.gridview);

        Log.d("debug","learnpagefragment.java>>"+"create new wokoutdbhandler");
        WorkoutDBHandler workoutDBH=new WorkoutDBHandler(getContext(),null,null,1);
        try {
            workoutDBH.createDataBase();
            workoutDBH.openDataBase();

        }
        catch (IOException e){
            e.printStackTrace();
        }

        LearnAdapter learnAdapter=new LearnAdapter(getContext(),workoutDBH);
        gridView.setAdapter(learnAdapter);

        Log.d("debug","number of tables="+String.valueOf(workoutDBH.countTables()));
        //Log.d("debug","hi  "+dbHandler.getTestData().getString(1));
        //Log.d("debug","he  "+dbHandler.loadHandler());
        //Log.d("debug","ho"+dbHandler.findWorkoutHandler(1).getName());


        /*Log.d("debug","workouts:"+workouts.size());
        Log.d("debug","adapter:"+learnAdapter.getCount());*/

        gridView.setOnItemClickListener(new itemClickListener());

        return rootView;
    }

    private class itemClickListener implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            Intent intent=new Intent(getActivity(),LearnActivity.class);
            intent.putExtra("workoutSet_index",i);
            getActivity().startActivity(intent);
        }
    }


}
//TODO: move create handler to onAttach()
//TODO: create a new workouts class and reduce buildHandlers
//TODO: create a new activity for workouts learn page. it's layout contains a fragment
//TODO: the fragment is the same as learnpage fragment. set onClick to begin fragment transaction
