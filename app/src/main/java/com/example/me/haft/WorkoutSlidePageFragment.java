package com.example.me.haft;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatDelegate;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class WorkoutSlidePageFragment extends Fragment {

    TextView NameTextView;
    TextView descriptionTextView;
    android.widget.ImageView IconView;
    ImageView ImageView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container
            , @Nullable Bundle savedInstanceState) {



        ViewGroup rootView = (ViewGroup) inflater.inflate
                (R.layout.workout_slide_page, container, false);

        int index=getArguments().getInt("index");


        NameTextView = (TextView) rootView.findViewById(R.id.exercise_name_textView);
        descriptionTextView= (TextView) rootView.findViewById(R.id.desription_textView);
        IconView = (ImageView) rootView.findViewById(R.id.exercise_iconView);
        ImageView = (ImageView) rootView.findViewById(R.id.exercise_Imageview);

        return rootView;
    }

    public void setExerciseViews(String name,String description){
        NameTextView.setText(name);
        descriptionTextView.setText(description);
    }
}
